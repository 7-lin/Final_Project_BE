package com.fc.final7.domain.review.repository;

import com.fc.final7.domain.review.entity.Posting;
import com.fc.final7.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 상품 클릭시 해당 상품의 최신리뷰 3개를 반환하는 메서드
    // pageable 인터페이스는 limit 3 을 걸기 위한 용도임
    @Query(value = "select r from Review r" +
            " where r.product.id = :productId" +
            " order by r.createdDate desc")
    List<Review> selectProductDetailReviews(@Param("productId") Long productId, Pageable pageable);

    @Modifying
    @Query("update Review r set r.viewCount=r.viewCount+1 WHERE r.id=:reviewId")
    void setViewCount(@Param("reviewId") Long reviewId);

    Optional<Review> findByIdAndPassword(@Param("reviewId") Long reviewId, @Param("password") String password);

    Page<Review> findAllByPosting(Posting posting, Pageable pageable);

    Optional<Review> findAllByIdAndPosting(Long reviewId, Posting posting);

    @Modifying
    @Query("update Review r set r.posting = :posting WHERE r.id=:reviewId")
    void updateReviewDelete(@Param("reviewId") Long reviewId, @Param("posting") Posting posting);
}
