package com.fc.final7.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.product.entity.Product;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private boolean wishlist;

    public ProductResponseDTO(Product product) {
        productId = product.getId();
        thumbnail = product.getThumbnail();
        productName = product.getTitle();
        productPrice = product.getPrice();
        briefExplanation = product.getDescription().replace("\n", "</br>").replace("\r", "");
        period = product.getTerm();
    }

    public static ProductResponseDTO toWishlist(ProductResponseDTO productResponseDTO, boolean wishlist){
        productResponseDTO.setWishlist(wishlist);

        return productResponseDTO;
    }
}
