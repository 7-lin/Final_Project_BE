package com.fc.final7.domain.reservation.dto.response.detail;

import com.fc.final7.domain.product.dto.response.detail.ProductPeriodDTO;
import com.fc.final7.domain.reservation.entity.ReservationPeriod;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationPeriodDTO {

    private ProductPeriodDTO period;
    private Integer amount;

    public ReservationPeriodDTO(ReservationPeriod reservationPeriod) {
        period = new ProductPeriodDTO(reservationPeriod.getProductPeriod());
        amount = reservationPeriod.getReservationAmount();
    }
}
