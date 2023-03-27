package com.fc.final7.domain.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SearchConditionDTO {

    private String mainCategory;
    private String middleCategory;
}
