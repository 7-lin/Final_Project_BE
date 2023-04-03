package com.fc.final7.domain.product.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilteringDTO {

    private List<CategoryConditionDTO> categories = new ArrayList<>();
    private Integer maxPeriod;
    private Integer minPeriod;
    private Integer maxPrice;
    private Integer minPrice;
    private String sort;
}
