package com.fc.final7.domain.comment.service;

import com.fc.final7.domain.comment.dto.CommentRequestDTO;
import com.fc.final7.domain.comment.dto.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    String createComment(int reviewId, CommentRequestDTO commentRequestDTO);

    List<CommentResponseDTO> findAllComment(int reviewId);

    String updateComment(int commentId, CommentRequestDTO commentRequestDTO);

    String deleteComment(int commentId, CommentRequestDTO commentRequestDTO);
}
