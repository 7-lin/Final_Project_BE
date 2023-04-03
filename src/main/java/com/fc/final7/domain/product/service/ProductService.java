package com.fc.final7.domain.product.service;

import com.fc.final7.domain.product.dto.response.ProductInCategoryResponseDTO;
import com.fc.final7.domain.product.dto.response.ProductPagingDTO;
import com.fc.final7.domain.product.dto.request.FilteringDTO;
import com.fc.final7.domain.product.dto.response.detail.ProductDetailResponseDTO;
import com.fc.final7.domain.product.dto.response.detail.ReviewResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public ProductPagingDTO groupByCategory(FilteringDTO searchConditionListDTO, Pageable pageable);

    ProductDetailResponseDTO selectProductDetail(Long productId);

    List<ReviewResponseDTO> selectProductDetailInReviews (Long productId, Pageable pageable);

    List<ProductInCategoryResponseDTO> searchProduct(String keyWord);
}
