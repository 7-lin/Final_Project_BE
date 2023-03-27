package com.fc.final7.domain.product.service.impl;

import com.fc.final7.domain.product.dto.ProductPagingDTO;
import com.fc.final7.domain.product.dto.ProductResponseDTO;
import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.product.repository.CategoryRepository;
import com.fc.final7.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;

    public ProductPagingDTO groupByCategory(SearchConditionListDTO searchConditionListDTO, PageRequest pageRequest) {
        Page<Product> products = categoryRepository.groupByCategorySearch(searchConditionListDTO, pageRequest);
        List<ProductResponseDTO> responseDTOS = products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        return new ProductPagingDTO(responseDTOS,
                products.getPageable().getOffset(),
                products.getPageable().getPageNumber() + 1,
                products.getPageable().getPageSize(),
                products.getTotalPages(),
                products.getTotalElements(),
                products.getSize());
    }
}