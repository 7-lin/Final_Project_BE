package com.fc.final7.domain.review.service;

import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    String createReview(ReviewRequestDTO reviewRequestDTO,
                        List<MultipartFile> multipartFileList,
                        List<String> stringList) throws IOException;

}
