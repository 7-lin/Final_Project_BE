package com.fc.final7.domain.comment.service.impl;

import com.fc.final7.domain.comment.dto.CommentRequestDTO;
import com.fc.final7.domain.comment.dto.CommentResponseDTO;
import com.fc.final7.domain.comment.entity.Comment;
import com.fc.final7.domain.comment.repository.CommentRepository;
import com.fc.final7.domain.comment.service.CommentService;
import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.global.exception.CommentPasswordMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public String createComment(int reviewId, CommentRequestDTO commentRequestDTO) {
        Comment requestComment = CommentRequestDTO.toEntity(reviewId, commentRequestDTO);

        commentRepository.save(requestComment);

        return "success";
    }

    @Override
    @Transactional
    public List<CommentResponseDTO> findAllComment(int reviewId) {
        return commentRepository.findAllByReview(Review.builder().id((long) reviewId).build())
                .stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String updateComment(int commentId, CommentRequestDTO commentRequestDTO) {
        Comment responseComment = commentRepository.findByIdAndPassword((long) commentId, commentRequestDTO.getPassword()).orElseThrow(CommentPasswordMismatchException::new);

        Comment comment = CommentRequestDTO.toUpdateEntity(commentId, responseComment.getReview().getId().intValue(), commentRequestDTO);
        commentRepository.save(comment);

        return "success";
    }

    @Override
    @Transactional
    public String deleteComment(int commentId, CommentRequestDTO commentRequestDTO) {
        Comment responseComment = commentRepository.findByIdAndPassword((long) commentId, commentRequestDTO.getPassword()).orElseThrow(CommentPasswordMismatchException::new);

        commentRepository.deleteById((long) commentId);

        return "success";
    }
}
