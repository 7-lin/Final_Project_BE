package com.fc.final7.domain.review.controller;

import com.fc.final7.domain.review.dto.ReviewPagingDTO;
import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import com.fc.final7.domain.review.dto.ReviewResponseDTO;
import com.fc.final7.domain.review.service.ReviewService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public BaseResponse<Null> doCreateReview(@RequestPart(name = "json") ReviewRequestDTO reviewRequestDTO,
                                             @RequestPart(name = "image")List<MultipartFile> multipartFileList,
                                             @RequestParam(name = "text")String text) throws IOException {

        return BaseResponse.of(0, reviewService.createReview(reviewRequestDTO, multipartFileList, text), null);
    }

    @GetMapping("/reviews")
    public BaseResponse<ReviewPagingDTO> doFindAllReview(@PageableDefault(size=12) Pageable pageable){

        ReviewPagingDTO reviewPagingDTO = reviewService.findAllReview(pageable);

        return BaseResponse.of(reviewPagingDTO.getTotalElements().intValue(), "success", reviewPagingDTO);

    }

    @GetMapping("/reviews/{review_id}")
    public BaseResponse<ReviewResponseDTO> doFindDetailReview(@PathVariable(name = "review_id") int reviewId){
        ReviewResponseDTO reviewResponseDTO = reviewService.findDetailReview(reviewId);

        return BaseResponse.of(1, "success", reviewResponseDTO);
    }

    @PostMapping("/reviews/{review_id}")
    public BaseResponse<Boolean> doMatchReviewPassword(@PathVariable(name = "review_id") int reviewId,
                                                       @RequestBody Map<String,String> passwordMap){
        return BaseResponse.of(1, "success", reviewService.matchReviewPassword(reviewId, passwordMap.get("password")));
    }

    @PutMapping("/reviews/{review_id}")
    public BaseResponse<ReviewResponseDTO> doUpdateReview(@PathVariable(name = "review_id") int reviewId,
                                                          @RequestPart(name = "json") ReviewRequestDTO reviewRequestDTO,
                                                          @RequestPart(name = "image")List<MultipartFile> multipartFileList,
                                                          @RequestParam(name = "text")String text) throws IOException {

        return BaseResponse.of(0, reviewService.updateReview(reviewId, reviewRequestDTO, multipartFileList, text), null);

    }

    @DeleteMapping("/reviews/{review_id}")
    public BaseResponse<ReviewResponseDTO> doDeleteReview(@PathVariable(name = "review_id") int reviewId){
        return BaseResponse.of(0,reviewService.deleteReview(reviewId),null);
    }
}
