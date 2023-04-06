package com.fc.final7.domain.review.repository;

import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.domain.review.entity.ReviewContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ReviewContentRepository extends JpaRepository<ReviewContent, Integer> {

    List<ReviewContent> findAllByReview(Review reviewId);

    @Modifying
    void deleteAllByReview(Review reviewId);
}
