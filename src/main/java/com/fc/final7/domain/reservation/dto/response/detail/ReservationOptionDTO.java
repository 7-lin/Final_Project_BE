package com.fc.final7.domain.reservation.dto.response.detail;

import com.fc.final7.domain.product.dto.response.detail.ProductOptionDTO;
import com.fc.final7.domain.reservation.entity.ReservationOption;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationOptionDTO {

    private ProductOptionDTO option;
    private Integer amount;

    public ReservationOptionDTO(ReservationOption reservationOption) {
        if(reservationOption == null) {
            this.option = null;
            this.amount = null;
        }else {
            this.option = new ProductOptionDTO(reservationOption.getProductOption());
            amount = reservationOption.getReservationAmount();

        }
    }
}
