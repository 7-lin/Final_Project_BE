package com.fc.final7.domain.product.service;

import com.fc.final7.domain.product.dto.ProductPagingDTO;
import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    public ProductPagingDTO groupByCategory(SearchConditionListDTO searchConditionListDTO, PageRequest pageRequest);

}
