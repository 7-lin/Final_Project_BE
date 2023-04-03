package com.fc.final7.domain.product.dto.response;

import com.fc.final7.domain.product.dto.response.detail.CategoryDTO;
import com.fc.final7.domain.product.entity.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductInCategoryResponseDTO {

    private Long productId;
    private String thumbnail;
    private String productName;
    private Long productPrice;
    private String briefExplanation;
    private Integer period;
    private List<CategoryDTO> categories;

    public ProductInCategoryResponseDTO (Product product) {
        productId = product.getId();
        thumbnail = product.getThumbnail();
        productName = product.getTitle();
        productPrice = product.getPrice();
        briefExplanation = product.getDescription().replace("\n", "</br>");
        period = product.getTerm();
        categories = product.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }
}
