package com.fc.final7.domain.review.controller;

import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import com.fc.final7.domain.review.service.ReviewService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
                                             @RequestParam(name = "text")List<String> stringList) throws IOException {

        return BaseResponse.of(0, reviewService.createReview(reviewRequestDTO, multipartFileList, stringList), null);
    }


}
