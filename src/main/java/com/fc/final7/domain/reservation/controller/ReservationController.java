package com.fc.final7.domain.reservation.controller;

import com.fc.final7.domain.reservation.dto.request.ReservationRequestDTO;
import com.fc.final7.domain.reservation.service.ReservationService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
