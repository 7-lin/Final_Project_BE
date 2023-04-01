package com.fc.final7.domain.review.service.impl;

import com.fc.final7.domain.review.dto.ReviewRequestDTO;
import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.domain.review.entity.ReviewContent;
import com.fc.final7.domain.review.repository.ReviewContentRepository;
import com.fc.final7.domain.review.repository.ReviewRepository;
import com.fc.final7.domain.review.service.ReviewService;
import com.fc.final7.global.entity.ContentType;
import com.fc.final7.global.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ImageHandler imageHandler;
    private final ReviewRepository reviewRepository;
    private final ReviewContentRepository reviewContentRepository;

    @Override
    public String createReview(ReviewRequestDTO reviewRequestDTO,
                               List<MultipartFile> multipartFileList,
                               List<String> stringList) throws IOException {


        Review requestReview = ReviewRequestDTO.toEntity(reviewRequestDTO);
        Review responseReview = reviewRepository.save(requestReview);
        Long reviewId = responseReview.getId();

        imageHandler.createFolder("review", reviewId.intValue());

        String thumbnail = createReviewImages(multipartFileList, reviewId.intValue());
        createReviewText(stringList, reviewId.intValue());

        requestReview = ReviewRequestDTO.toUpdateEntity(reviewRequestDTO, reviewId, thumbnail);
        reviewRepository.save(requestReview);

        return "success";
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

        int count = 10;
        for (String url : imageList) {
            reviewContentRepository.save(ReviewContent.builder()
                    .review(Review.builder().id(Integer.toUnsignedLong(reviewId)).build())
                    .content(url)
                    .contentType(ContentType.IMAGE)
                    .priority(Integer.toUnsignedLong(count))
                    .build());
        }

        return thumbnail;
    }

    /**
     * Text Review Content Table 저장 수행 Method
     * @param stringList 입력받은 Text 내용
     * @param reviewId 저장하기 위한 Review ID
     */
    public void createReviewText(List<String> stringList, int reviewId){
        int count = 10;
        for (String text : stringList){
            reviewContentRepository.save(ReviewContent.builder()
                    .review(Review.builder().id(Integer.toUnsignedLong(reviewId)).build())
                    .content(text)
                    .contentType(ContentType.TEXT)
                    .priority(Integer.toUnsignedLong(count))
                    .build());
        }
    }
}
