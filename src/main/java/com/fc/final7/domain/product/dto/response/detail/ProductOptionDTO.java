package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.product.entity.ProductOption;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductOptionDTO {

    private Long productOptionId;
    private String content;
    private Integer price;
    private String type;

    public ProductOptionDTO(ProductOption productOption) {

        this.productOptionId = productOption.getId();
        this.content = productOption.getContent();
        this.price = productOption.getPrice().intValue();
        this.type = productOption.getType();
    }
}
