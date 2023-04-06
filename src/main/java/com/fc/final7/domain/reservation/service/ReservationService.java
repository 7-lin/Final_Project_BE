package com.fc.final7.domain.reservation.service;

import com.fc.final7.domain.reservation.dto.request.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface ReservationService {
    String createReservation(ReservationRequestDTO requestDTO, String header);
}
