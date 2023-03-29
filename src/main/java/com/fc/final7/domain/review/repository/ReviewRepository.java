package com.fc.final7.domain.review.repository;

import com.fc.final7.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 상품 클릭시 해당 상품의 최신리뷰 3개를 반환하는 메서드
    // pageable 인터페이스는 limit 3 을 걸기 위한 용도임
    @Query(value = "select r from Review r" +
            " where r.product.id = :productId" +
            " order by r.createdDate desc")
    List<Review> selectProductDetailReviews(@Param("productId") Long productId, Pageable pageable);
}
