package com.fc.final7.domain.product.dto;

import com.fc.final7.domain.product.entity.Product;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductResponseDTO {

    private Long productId;
    private String thumbnail;
    private String productName;
    private Long productPrice;
    private String briefExplanation;
    private Integer period;

    public ProductResponseDTO(Product product) {
        productId = product.getId();
        thumbnail = product.getThumbnail();
        productName = product.getTitle();
        productPrice = product.getPrice();
        briefExplanation = product.getDescription();
        period = product.getTerm();
    }
}
