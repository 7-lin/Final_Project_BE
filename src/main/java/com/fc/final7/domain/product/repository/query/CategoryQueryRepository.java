package com.fc.final7.domain.product.repository.query;

import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import com.fc.final7.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryQueryRepository {

    Page<Product> groupByCategorySearch(SearchConditionListDTO searchConditionListDTO, PageRequest pageRequest);
}
