package com.fc.final7.domain.review.controller;

import com.fc.final7.domain.review.dto.ReviewPagingDTO;
import com.fc.final7.domain.review.dto.ReviewRequestDTO;
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

        return BaseResponse.of(reviewPagingDTO.getSize(), "success", reviewPagingDTO);

    }
}
