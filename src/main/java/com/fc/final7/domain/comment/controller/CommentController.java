package com.fc.final7.domain.comment.controller;

import com.fc.final7.domain.comment.dto.CommentRequestDTO;
import com.fc.final7.domain.comment.dto.CommentResponseDTO;
import com.fc.final7.domain.comment.service.CommentService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/reviews/{review_id}")
    BaseResponse<Null> doCreateComment(@PathVariable(name = "review_id") int reviewId,
                                       @RequestBody CommentRequestDTO commentRequestDTO) {

        System.out.println(reviewId);
        System.out.println(commentRequestDTO);

        return BaseResponse.of(0, commentService.createComment(reviewId, commentRequestDTO), null);
    }

    @GetMapping("/comments/reviews/{review_id}")
    BaseResponse<List<CommentResponseDTO>> doFindAllComment(@PathVariable(name = "review_id") int reviewId){
        List<CommentResponseDTO> commentResponseDTOList = commentService.findAllComment(reviewId);

        return BaseResponse.of(commentResponseDTOList.size(), "success", commentResponseDTOList);
    }

    @PutMapping("/comments/{comment_id}")
    BaseResponse<Null> doUpdateComment(@PathVariable(name = "comment_id") int commentId,
                                       @RequestBody CommentRequestDTO commentRequestDTO){

        return BaseResponse.of(0, commentService.updateComment(commentId, commentRequestDTO), null);
    }

    @DeleteMapping("/comments/{comment_id}")
    BaseResponse<Null> doDeleteComment(@PathVariable(name = "comment_id") int commentId,
                                       @RequestBody CommentRequestDTO commentRequestDTO){

        return BaseResponse.of(0, commentService.deleteComment(commentId, commentRequestDTO), null);
    }
}
