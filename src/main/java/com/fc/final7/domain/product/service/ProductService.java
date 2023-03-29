package com.fc.final7.domain.product.service;

import com.fc.final7.domain.product.dto.response.ProductPagingDTO;
import com.fc.final7.domain.product.dto.request.SearchConditionListDTO;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    public ProductPagingDTO groupByCategory(SearchConditionListDTO searchConditionListDTO, Pageable pageable);

}
