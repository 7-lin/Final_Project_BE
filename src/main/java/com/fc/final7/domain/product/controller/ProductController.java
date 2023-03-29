package com.fc.final7.domain.product.controller;

import com.fc.final7.domain.product.dto.ProductPagingDTO;
import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import com.fc.final7.domain.product.service.ProductService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/products")
    private BaseResponse<ProductPagingDTO> selectProductListGroupByCategory(@RequestBody SearchConditionListDTO searchConditionDTOS,
                                                                            @PageableDefault(size = 12) Pageable pageable) {
        ProductPagingDTO productPagingDTO = productService.groupByCategory(searchConditionDTOS, pageable);
        return BaseResponse.of(productPagingDTO.getProducts().size(), "성공", productPagingDTO);
    }
}
