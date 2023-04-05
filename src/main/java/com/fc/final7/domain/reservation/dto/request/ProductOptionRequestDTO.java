package com.fc.final7.domain.reservation.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductOptionRequestDTO {

    private Long optionId;
    private Integer amount;
}
