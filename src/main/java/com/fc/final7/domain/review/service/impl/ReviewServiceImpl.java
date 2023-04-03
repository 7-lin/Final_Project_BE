package com.fc.final7.domain.review.service.impl;

import com.fc.final7.domain.review.dto.ReviewPagingDTO;
import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import com.fc.final7.domain.review.dto.ReviewResponseDTO;
import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.domain.review.entity.ReviewContent;
import com.fc.final7.domain.review.repository.ReviewContentRepository;
import com.fc.final7.domain.review.repository.ReviewRepository;
import com.fc.final7.domain.review.service.ReviewService;
import com.fc.final7.global.entity.ContentType;
import com.fc.final7.global.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ImageHandler imageHandler;
    private final ReviewRepository reviewRepository;
    private final ReviewContentRepository reviewContentRepository;

    @Override
    @Transactional
    public String createReview(ReviewRequestDTO reviewRequestDTO,
                               List<MultipartFile> multipartFileList,
                               String text) throws IOException {


        Review requestReview = ReviewRequestDTO.toEntity(reviewRequestDTO);
        Review responseReview = reviewRepository.save(requestReview);
        Long reviewId = responseReview.getId();

        imageHandler.createFolder("review", reviewId.intValue());

        createReviewText(text, reviewId.intValue());
        String thumbnail = createReviewImages(multipartFileList, reviewId.intValue());

        requestReview = ReviewRequestDTO.toUpdateEntity(reviewRequestDTO, reviewId, thumbnail);
        reviewRepository.save(requestReview);

        return "success";
    }

    @Override
    @Transactional
    public ReviewPagingDTO findAllReview(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        List<ReviewResponseDTO> reviewResponseDTOList = reviews.getContent().stream().map(ReviewResponseDTO::simple).collect(Collectors.toList());

        return ReviewPagingDTO.builder()
                .offset(reviews.getPageable().getOffset())
                .pageNumber(reviews.getPageable().getPageNumber())
                .pageSize(reviews.getPageable().getPageSize())
                .totalPages(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .size(reviews.getSize())
                .build();

    }

    /**
     * Image 서버 저장 및 URL Review Content Table 저장 수행 Method
     * @param multipartFileList 입력받은 이미지 파일 List
     * @param reviewId 저장하기 위한 Review ID
     * @return thumbnail URL
     * @throws IOException
     */
    public String createReviewImages(List<MultipartFile> multipartFileList, int reviewId) throws IOException {
        int sequence = 1;
        List<String> imageList = new ArrayList<>();
        String thumbnail = null;

        for (MultipartFile multipartFile : multipartFileList) {
            if (sequence == 1) {
                thumbnail = imageHandler.saveImage("review", "content", reviewId, sequence, multipartFile);
                imageList.add(thumbnail);
            } else {
                imageList.add(imageHandler.saveImage("review", "content", reviewId, sequence, multipartFile));
            }
            sequence++;
        }

        int count = 20;
        for (String url : imageList) {
            reviewContentRepository.save(ReviewContent.builder()
                    .review(Review.builder().id(Integer.toUnsignedLong(reviewId)).build())
                    .content(url)
                    .contentType(ContentType.IMAGE)
                    .priority(Integer.toUnsignedLong(count))
                    .build());
            count+=10;
        }

        return thumbnail;
    }

    /**
     * Text Review Content Table 저장 수행 Method
     * @param stringList 입력받은 Text 내용
     * @param reviewId 저장하기 위한 Review ID
     */
    public void createReviewText(String text, int reviewId){
        int count = 10;
        reviewContentRepository.save(ReviewContent.builder()
                .review(Review.builder().id(Integer.toUnsignedLong(reviewId)).build())
                .content(text)
                .contentType(ContentType.TEXT)
                .priority(Integer.toUnsignedLong(count))
                .build());
    }
}
