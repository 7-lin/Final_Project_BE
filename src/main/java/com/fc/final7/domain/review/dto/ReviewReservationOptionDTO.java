package com.fc.final7.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.reservation.entity.ReservationOption;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReservationOptionDTO {
    private int optionId;
    private String type;
    private String content;
    private int price;
    private int amount;

    public ReviewReservationOptionDTO(ReservationOption reservationOption){
        optionId = reservationOption.getProductOption().getId().intValue();
        type = reservationOption.getProductOption().getType();
        content = reservationOption.getProductOption().getContent();
        price = reservationOption.getProductOption().getPrice().intValue();
        amount = reservationOption.getReservationAmount();
    }
}
