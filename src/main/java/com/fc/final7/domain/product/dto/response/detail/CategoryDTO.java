package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.product.entity.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private String mainCategory;
    private String middleCategory;
    private String subdivision;

    public CategoryDTO (Category category) {
        mainCategory = category.getMainCategory();
        middleCategory = category.getMiddleCategory();
        subdivision = category.getSubdivision();
    }
}
