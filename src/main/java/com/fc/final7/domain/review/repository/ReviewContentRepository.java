package com.fc.final7.domain.review.repository;

import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.domain.review.entity.ReviewContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewContentRepository extends JpaRepository<ReviewContent, Integer> {

    List<ReviewContent> findAllByReview(Review reviewId);

    void deleteAllByReview(Review reviewId);
}
