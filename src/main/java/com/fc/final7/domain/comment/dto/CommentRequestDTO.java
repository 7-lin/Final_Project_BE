package com.fc.final7.domain.comment.dto;

import com.fc.final7.domain.comment.entity.Comment;
import com.fc.final7.domain.review.entity.Review;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {

    private String name;
    private String password;
    private String content;

    public static Comment toEntity(int reviewId, CommentRequestDTO commentRequestDTO){
        return Comment.builder()
                .review(Review.builder().id((long) reviewId).build())
                .name(commentRequestDTO.getName())
                .password(commentRequestDTO.getPassword())
                .content(commentRequestDTO.getContent())
                .build();
    }

    public static Comment toUpdateEntity(int id, int reviewId, CommentRequestDTO commentRequestDTO){
        return Comment.builder()
                .id((long) id)
                .review(Review.builder().id((long) reviewId).build())
                .name(commentRequestDTO.getName())
                .password(commentRequestDTO.getPassword())
                .content(commentRequestDTO.getContent())
                .build();
    }
}
