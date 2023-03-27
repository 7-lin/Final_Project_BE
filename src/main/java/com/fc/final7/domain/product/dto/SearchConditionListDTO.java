package com.fc.final7.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SearchConditionListDTO {

    private List<SearchConditionDTO> searchConditions = new ArrayList<>();
    private Integer page = 0;


    public void setSearchConditions(List<SearchConditionDTO> searchConditions) {
        this.searchConditions = searchConditions;
    }

    public void setPage(Integer page) {
        this.page = page - 1;
    }
}
