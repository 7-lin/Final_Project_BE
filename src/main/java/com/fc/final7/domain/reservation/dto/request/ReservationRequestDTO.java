package com.fc.final7.domain.reservation.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationRequestDTO {

    private List<ProductPeriodRequestDTO> periods = new ArrayList<>();
    private List<ProductOptionRequestDTO> options = new ArrayList<>();
    private String name;
    private String phone;
    private String email;
    private Integer totalPrice;
    private Integer people;
}
