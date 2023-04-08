package com.fc.final7.domain.reservation.dto.response;

import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.reservation.entity.ReservationPeriod;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductInfoDTO {

    private ProductResponseDTO product;

    public ProductInfoDTO(ReservationPeriod reservationPeriod) {
        product = new ProductResponseDTO(reservationPeriod.getProductPeriod().getProduct());
    }
}
