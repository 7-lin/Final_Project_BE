package com.fc.final7.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.comment.entity.Comment;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDTO {

    private Integer commentId;
    private String name;
    private String content;

    public CommentResponseDTO(Comment comment){
        commentId = comment.getId().intValue();
        name = comment.getName();
        content = comment.getContent();
    }
}
