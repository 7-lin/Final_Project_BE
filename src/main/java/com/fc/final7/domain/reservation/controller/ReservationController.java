package com.fc.final7.domain.reservation.controller;

import com.fc.final7.domain.reservation.dto.request.ReservationCheckRequestDTO;
import com.fc.final7.domain.reservation.dto.request.ReservationCodeRequestDTO;
import com.fc.final7.domain.reservation.dto.request.ReservationRequestDTO;
import com.fc.final7.domain.reservation.dto.response.ReservationResponseDTO;
import com.fc.final7.domain.reservation.dto.response.detail.ReservationDetailResponseDTO;
import com.fc.final7.domain.reservation.service.ReservationService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //예약 메서드
    @PostMapping("/reservation")
    public BaseResponse<String> createReservation (HttpServletRequest request, @RequestBody ReservationRequestDTO requestDTO) {
        String header = request.getHeader(AUTHORIZATION);
        String response = reservationService.createReservation(requestDTO, header);
        return BaseResponse.of(1, "성공", response);
    }

    // 회원 예약 리스트 반환 컨트롤러
    @GetMapping("/my/reservation")
    public BaseResponse<List<ReservationResponseDTO>> reservationInquiryByMember(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        List<ReservationResponseDTO> responseDTOs = reservationService.reservationInquiryByMember(header);
        return BaseResponse.of(responseDTOs.size(), "성공", responseDTOs);
    }

    //회원 예약 상세페이지 접근 컨트롤러
    @GetMapping("/my/reservation/{reservationId}")
    public BaseResponse<ReservationDetailResponseDTO> reservationDetail(@PathVariable Long reservationId,
                                                                        HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        ReservationDetailResponseDTO responseDTO = reservationService.reservationDetail(reservationId, header);
        return BaseResponse.of(1, "성공", responseDTO);
    }

    //예약 단건 조회 (예약번호 + 연락처로) 조회하는 메서드
    @PostMapping("/reservation/check")
    public BaseResponse<ReservationDetailResponseDTO> reservationCheck(@RequestBody ReservationCheckRequestDTO requestDTO) {
        ReservationDetailResponseDTO responseDTO = reservationService.reservationCheck(requestDTO);
        return BaseResponse.of(1, "성공", responseDTO);
    }

    //예약 취소 메서드
    @DeleteMapping("/reservation")
    public BaseResponse<String> cancelReservation(@RequestBody ReservationCodeRequestDTO requestDTO) {
        String reservationCode = requestDTO.getReservationCode();
        String response = reservationService.cancelReservation(reservationCode);
        return BaseResponse.of(1, "성공", response);
    }

    //취소된 예약 다시 되돌리는 메서드
    @PostMapping("/reservation/undo")
    public BaseResponse<String> undoReservation(@RequestBody ReservationCodeRequestDTO requestDTO) {
        String reservationCode = requestDTO.getReservationCode();
        String response = reservationService.undoReservation(reservationCode);
        return BaseResponse.of(1,"성공", response);
    }
}
