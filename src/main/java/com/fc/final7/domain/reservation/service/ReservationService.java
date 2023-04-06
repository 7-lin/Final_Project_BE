package com.fc.final7.domain.reservation.service;

import com.fc.final7.domain.reservation.dto.request.ReservationRequestDTO;
import com.fc.final7.domain.reservation.dto.response.ReservationResponseDTO;
import com.fc.final7.domain.reservation.dto.response.detail.ReservationDetailResponseDTO;


import java.util.List;

public interface ReservationService {
    String createReservation(ReservationRequestDTO requestDTO, String header);

    List<ReservationResponseDTO> reservationInquiryByMember(String header);

    ReservationDetailResponseDTO reservationDetail(Long reservationId, String header);
}
