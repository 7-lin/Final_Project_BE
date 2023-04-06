package com.fc.final7.domain.comment.repository;

import com.fc.final7.domain.comment.entity.Comment;
import com.fc.final7.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByReview(Review review);

    Optional<Comment> findByIdAndPassword(Long id, String password);

}
