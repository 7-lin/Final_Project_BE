package com.fc.final7.domain.product.service.impl;

import com.fc.final7.domain.product.dto.response.ProductPagingDTO;
import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.product.dto.request.SearchConditionListDTO;
import com.fc.final7.domain.product.dto.response.detail.ProductDetailResponseDTO;
import com.fc.final7.domain.product.dto.response.detail.ReviewResponseDTO;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.product.repository.datajpa.CategoryRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductRepository;
import com.fc.final7.domain.product.service.ProductService;
import com.fc.final7.domain.review.repository.ReviewRepository;
import com.fc.final7.global.exception.NoSearchProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;



    //그룹별 테마별 지역별 카테고리를 동적으로 받아 페이징을 처리하는 메서드
    public ProductPagingDTO groupByCategory(SearchConditionListDTO searchConditionListDTO, Pageable pageable) {
        Page<Product> products = categoryRepository.groupByCategorySearch(searchConditionListDTO, pageable);
        List<ProductResponseDTO> responseDTOS = products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        return new ProductPagingDTO(responseDTOS,
                products.getPageable().getOffset(),
                products.getPageable().getPageNumber() + 1,
                products.getPageable().getPageSize(),
                products.getTotalPages(),
                products.getTotalElements(),
                products.getSize());
    }

    //상품 클릭시 나오는 상품 detail 정보를 출력하는 메서드
    @Override
    public ProductDetailResponseDTO selectProductDetail(Long productId) {
        Product product = productRepository.findProductFetchJoinById(productId).orElseThrow(NoSearchProductException::new);
        return new ProductDetailResponseDTO().toDTO(product);
    }

    // 상품 클릭시 해당 상품의 최신리뷰 3개를 반환하는 메서드
    @Override
    public List<ReviewResponseDTO> selectProductDetailInReviews(Long productId, Pageable pageable) {
        return reviewRepository.selectProductDetailReviews(productId, pageable)
                .stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }


    // 상품 검색 관련 api 상품명 + 상품설명에서 검색키워드가 포함 되어있으면 반환한다.
    @Override
    public ProductPagingDTO searchProduct(String keyWord, Pageable pageable) {
        Page<Product> products = productRepository.searchProduct(keyWord, pageable);
        List<ProductResponseDTO> productResponseDTOS = products.getContent().stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        return new ProductPagingDTO(productResponseDTOS,
                products.getPageable().getOffset(),
                products.getPageable().getPageNumber() + 1,
                products.getPageable().getPageSize(),
                products.getTotalPages(),
                products.getTotalElements(),
                products.getSize());
    }
}
