package com.fc.final7.domain.reservation.service.impl;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import com.fc.final7.domain.product.entity.ProductOption;
import com.fc.final7.domain.product.entity.ProductPeriod;
import com.fc.final7.domain.product.repository.datajpa.ProductOptionRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductPeriodRepository;
import com.fc.final7.domain.reservation.dto.request.ProductOptionRequestDTO;
import com.fc.final7.domain.reservation.dto.request.ProductPeriodRequestDTO;
import com.fc.final7.domain.reservation.dto.request.ReservationRequestDTO;
import com.fc.final7.domain.reservation.dto.response.ReservationResponseDTO;
import com.fc.final7.domain.reservation.dto.response.detail.ReservationDetailResponseDTO;
import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.domain.reservation.entity.ReservationOption;
import com.fc.final7.domain.reservation.entity.ReservationPeriod;
import com.fc.final7.domain.reservation.repository.ReservationOptionRepository;
import com.fc.final7.domain.reservation.repository.ReservationPeriodRepository;
import com.fc.final7.domain.reservation.repository.ReservationRepository;
import com.fc.final7.domain.reservation.service.ReservationService;
import com.fc.final7.global.exception.UnusualAccessRouteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.fc.final7.domain.reservation.entity.Status.WAITING;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final ProductPeriodRepository productPeriodRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ReservationOptionRepository reservationOptionRepository;
    private final ReservationPeriodRepository reservationPeriodRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String createReservation(ReservationRequestDTO requestDTO, String header) {

        Reservation reservation;

        if(header == null) {
            reservation = makeNonMemberReservation(requestDTO);
        }else {
            reservation = makeMemberReservation(requestDTO, header);
        }
        reservationRepository.save(reservation);
        createReservationPeriod(requestDTO, reservation);
        createReservationOption(requestDTO, reservation);

        return "예약에 성공하셨습니다. 예약번호: " + reservation.getReservationCode();
    }

    //비회원 예약 생성
    private Reservation makeNonMemberReservation(ReservationRequestDTO requestDTO) {
        return Reservation.builder()
                .status(WAITING)
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .notice(false)
                .price(requestDTO.getTotalPrice().longValue())
                .people(requestDTO.getPeople())
                .reservationCode(makeReservationCode())
                .build();
    }

    //회원 예약 생성
    private Reservation makeMemberReservation(ReservationRequestDTO requestDTO, String header) {
        String email = jwtProvider.getSubjectFromToken(header);
        Member member = memberRepository.findByEmail(email).get();

        return Reservation.builder()
                .member(member)
                .status(WAITING)
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .notice(false)
                .price(requestDTO.getTotalPrice().longValue())
                .people(requestDTO.getPeople())
                .reservationCode(makeReservationCode())
                .build();
    }

    private void createReservationPeriod(ReservationRequestDTO requestDTO, Reservation reservation) {
        List<ProductPeriodRequestDTO> periods = requestDTO.getPeriods();
        for (ProductPeriodRequestDTO period : periods) {
            Long periodId = period.getPeriodId();
            ProductPeriod productPeriod = productPeriodRepository.findById(periodId).get();
            ReservationPeriod reservationPeriod = new ReservationPeriod(reservation, productPeriod, period.getAmount());
            reservationPeriodRepository.save(reservationPeriod);
        }
    }

    private void createReservationOption(ReservationRequestDTO requestDTO, Reservation reservation) {
        List<ProductOptionRequestDTO> options = requestDTO.getOptions();
        for (ProductOptionRequestDTO option : options) {
            Long optionId = option.getOptionId();
            ProductOption productOption = productOptionRepository.findById(optionId).get();
            ReservationOption reservationOption = new ReservationOption(reservation, productOption, option.getAmount());
            reservationOptionRepository.save(reservationOption);
        }
    }
    public String makeReservationCode() {
        return now().format(ISO_LOCAL_DATE).replace("-", "") + new Random().nextInt(10000);
    }

    //멤버가 마이페이지에 들어갔을때 보여지는 예약 리스트 반환 메서드
    @Transactional(readOnly = true)
    @Override
    public List<ReservationResponseDTO> reservationInquiryByMember(String header) {
        String email = jwtProvider.getSubjectFromToken(header);
        Member member = memberRepository.findByEmail(email).get();
        List<Reservation> reservations = reservationRepository.selectReservations(member.getId());
        return reservations.stream().map(ReservationResponseDTO::new).collect(Collectors.toList());
    }


    //회원 예약 상세정보 페이지 반환 메서드
    @Transactional(readOnly = true)
    @Override
    public ReservationDetailResponseDTO reservationDetail(Long reservationId, String header) {
        //토큰이 없는 즉 로그인 되지 않은 사용자가 리뷰 상세페이지에 접근했을때
        if(header == null) {
            throw new UnusualAccessRouteException();
        }

        String email = jwtProvider.getSubjectFromToken(header);
        Member member = memberRepository.findByEmail(email).get();
        Long memberId = member.getId();
        Reservation reservation = reservationRepository.selectReservationDetailByReservationId(reservationId).get();
        Long id = reservation.getMember().getId();

        //토큰은 있지만 다른 회원의 리뷰 상세페이지에 접근했을때
        if(!Objects.equals(memberId, id)) {
            throw new UnusualAccessRouteException();
        }
        return new ReservationDetailResponseDTO(reservation);
    }
}
