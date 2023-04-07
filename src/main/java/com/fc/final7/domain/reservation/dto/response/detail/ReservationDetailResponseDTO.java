package com.fc.final7.domain.reservation.dto.response.detail;

import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.reservation.dto.response.ProductInfoDTO;
import com.fc.final7.domain.reservation.entity.Reservation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationDetailResponseDTO {

    private Long reservationId;
    private List<ReservationPeriodDTO> periods = new ArrayList<>();
    private List<ReservationOptionDTO> options = new ArrayList<>();
    private ProductResponseDTO product;
    private Integer totalPrice;
    private String name;
    private String phone;
    private String email;
    private String reservationCode;

    public ReservationDetailResponseDTO(Reservation reservation) {
        reservationId = reservation.getId();
        periods = reservation.getPeriods().stream().map(ReservationPeriodDTO::new).collect(Collectors.toList());
        options = reservation.getOptions().stream().map(ReservationOptionDTO::new).collect(Collectors.toList());
        product = reservation.getPeriods().stream().map(ProductInfoDTO::new).findFirst().get().getProduct();
        totalPrice = reservation.getPrice().intValue();
        name = reservation.getName();
        phone = reservation.getPhone();
        email = reservation.getEmail();
        reservationCode = reservation.getReservationCode();
    }
}
