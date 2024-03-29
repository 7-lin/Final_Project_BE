package com.fc.final7.domain.review.service;

import com.fc.final7.domain.review.dto.ReviewPagingDTO;
import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import com.fc.final7.domain.review.dto.ReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    String createReview(ReviewRequestDTO reviewRequestDTO,
                        List<MultipartFile> multipartFileList,
                        String text) throws IOException;

    ReviewPagingDTO findAllReview(Pageable pageable);

    ReviewResponseDTO findDetailReview(int reviewId);

    Boolean matchReviewPassword(int reviewId, String password);

    String updateReview(int reviewId,
                                   ReviewRequestDTO reviewRequestDTO,
                                   List<MultipartFile> multipartFileList,
                                   String text) throws IOException;

    String deleteReview(int reviewId);
}
