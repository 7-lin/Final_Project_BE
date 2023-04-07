package com.fc.final7.domain.reservation.dto.response;

import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.domain.reservation.entity.Status;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationResponseDTO {

    private Long reservationId;
    private String date;
    private Status status;
    private List<ProductInfoDTO> productInfo = new ArrayList<>();
    private String reservationCode;
    private Integer people;

    public ReservationResponseDTO(Reservation reservation) {
        reservationId = reservation.getId();
        date = reservation.getCreatedDate().format(DateTimeFormatter.ISO_DATE);
        status = reservation.getStatus();
        productInfo = reservation.getPeriods().stream().map(ProductInfoDTO::new).collect(Collectors.toList());
        reservationCode = reservation.getReservationCode();
        people = reservation.getPeople();
    }
}
