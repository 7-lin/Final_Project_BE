package com.fc.final7.domain.product.controller;

import com.fc.final7.domain.product.dto.response.ProductPagingDTO;
import com.fc.final7.domain.product.dto.request.SearchConditionListDTO;
import com.fc.final7.domain.product.dto.response.detail.ProductDetailResponseDTO;
import com.fc.final7.domain.product.dto.response.detail.ReviewResponseDTO;
import com.fc.final7.domain.product.service.ProductService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    //카테고리별 상품조회 메서드
    @PostMapping("/products")
    private BaseResponse<ProductPagingDTO> selectProductListGroupByCategory(@RequestBody SearchConditionListDTO searchConditionDTOS,
                                                                            @PageableDefault(size = 12) Pageable pageable) {
        ProductPagingDTO productPagingDTO = productService.groupByCategory(searchConditionDTOS, pageable);
        return BaseResponse.of(productPagingDTO.getProducts().size(), "성공", productPagingDTO);
    }

    //상품 상세 페이지 상품디테일 정보 반환 메서드
    @GetMapping("/products/{productId}")
    public BaseResponse<ProductDetailResponseDTO> productDetail(@PathVariable Long productId) {
        ProductDetailResponseDTO productDetailResponseDTO = productService.selectProductDetail(productId);
        return BaseResponse.of(1, "성공", productDetailResponseDTO);
    }

    //상품 상세 페이지 상품에 관련된 후기 3개 내보내는 메서드
    @GetMapping("/products/reviews/{productId}")
    public BaseResponse<List<ReviewResponseDTO>> productDetailInReviews(@PathVariable Long productId,
                                                                        @PageableDefault(size = 3) Pageable pageable) {

        List<ReviewResponseDTO> reviewResponseDTOS = productService.selectProductDetailInReviews(productId, pageable);
        return BaseResponse.of(reviewResponseDTOS.size(), "성공", reviewResponseDTOS);
    }

}
