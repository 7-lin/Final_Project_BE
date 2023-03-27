package com.fc.final7.domain.product.controller;

import com.fc.final7.domain.product.dto.ProductPagingDTO;
import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import com.fc.final7.domain.product.service.ProductService;
import com.fc.final7.global.response.GlobalSuccessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/products")
    private GlobalSuccessDTO<ProductPagingDTO> test(@RequestBody SearchConditionListDTO searchConditionDTOS) {
        PageRequest pageRequest = PageRequest.of(searchConditionDTOS.getPage(), 12);
        ProductPagingDTO productPagingDTO = productService.groupByCategory(searchConditionDTOS, pageRequest);
        return new GlobalSuccessDTO<>(productPagingDTO.getProducts().size(), "성공", productPagingDTO);
    }
}
