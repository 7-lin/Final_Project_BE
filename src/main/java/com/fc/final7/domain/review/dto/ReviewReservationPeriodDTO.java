package com.fc.final7.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.reservation.entity.ReservationPeriod;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReservationPeriodDTO {
    private int periodId;
    private String startDate;
    private String endDate;
    private int amount;

    public ReviewReservationPeriodDTO(ReservationPeriod reservationPeriod){
        periodId = reservationPeriod.getProductPeriod().getId().intValue();
        startDate = reservationPeriod.getProductPeriod().getStartDate();
        endDate = reservationPeriod.getProductPeriod().getEndDate();
        amount = reservationPeriod.getReservationAmount();
    }
}
