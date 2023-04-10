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
import com.fc.final7.domain.reservation.dto.request.ReservationCheckRequestDTO;
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
import com.fc.final7.global.exception.NoSearchReservationException;
import com.fc.final7.global.exception.UnusualAccessRouteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.fc.final7.domain.reservation.entity.Status.CANCEL;
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
        String token = parsing(header);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
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
        String token = parsing(header);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
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

        String token = parsing(header);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
        Member member = memberRepository.findByEmail(email).get();
        Long memberId = member.getId();

        //존재하지 않은 reservationId에 접근하면 예외처리
        Reservation reservation = reservationRepository
                .selectReservationDetailByReservationId(reservationId)
                .orElseThrow(NoSearchReservationException::new);

        Member findMember = reservation.getMember();

        //토큰은 있지만 다른 비회원의 리뷰 상세페이지에 접근했을떄
        if(findMember == null) {
            throw new UnusualAccessRouteException();
        }

        Long id = findMember.getId();
        //토큰은 있지만 다른 회원의 리뷰 상세페이지에 접근했을때
        if(!Objects.equals(memberId, id)) {
            throw new UnusualAccessRouteException();
        }
        return new ReservationDetailResponseDTO(reservation);
    }

    private String parsing(String header) {
        return header.substring(7);
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationDetailResponseDTO reservationCheck(ReservationCheckRequestDTO requestDTO) {
        String reservationCode = requestDTO.getReservationCode();
        String phone = requestDTO.getPhone();
        //존재하지 않는 예약 번호를 입력했을 경우
        Reservation reservation = reservationRepository
                .findReservationByReservationCode(reservationCode)
                .orElseThrow(NoSearchReservationException::new);

        //예약 번호는 존재했으나 전화번호가 틀렸을 경우
        if(!reservation.getPhone().equals(phone)) {
            throw new NoSearchReservationException();
        }

        //이미 취소된 예약일 경우
        if(reservation.getStatus().equals(CANCEL)) {
            throw new NoSearchReservationException();
        }
        return new ReservationDetailResponseDTO(reservation);
    }


    //예약 취소
    @Override
    public String cancelReservation(String reservationCode) {
        Reservation reservation = reservationRepository
                .findReservationByReservationCode(reservationCode)
                .orElseThrow(NoSearchReservationException::new);
        if(reservation.getStatus().equals(CANCEL)) {
            return "이미 취소된 예약입니다.";
        }
        reservation.changeState(CANCEL);
        return "예약이 취소 되었습니다.";
    }

    @Override
    public String undoReservation(String reservationCode) {
        Reservation reservation = reservationRepository
                .findReservationByReservationCode(reservationCode)
                .orElseThrow(NoSearchReservationException::new);
        if(reservation.getStatus().equals(WAITING)) {
            return "취소 할 수 없습니다.";
        }
        reservation.changeState(WAITING);
        return "다시 예약 되었습니다." + reservation.getReservationCode();
    }
}
