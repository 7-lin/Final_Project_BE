package com.fc.final7.domain.product.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CategoryConditionDTO {

    private String mainCategory;
    private String middleCategory;
}
