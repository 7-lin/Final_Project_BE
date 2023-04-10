package com.fc.final7.domain.review.service.impl;

import com.fc.final7.domain.product.entity.Category;
import com.fc.final7.domain.product.repository.datajpa.CategoryRepository;
import com.fc.final7.domain.review.dto.*;
import com.fc.final7.domain.review.entity.Posting;
import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.domain.review.entity.ReviewContent;
import com.fc.final7.domain.review.repository.ReviewContentRepository;
import com.fc.final7.domain.review.repository.ReviewRepository;
import com.fc.final7.domain.review.service.ReviewService;
import com.fc.final7.global.entity.ContentType;
import com.fc.final7.global.exception.ReviewNotFoundException;
import com.fc.final7.global.exception.ReviewPasswordMismatchException;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ImageHandler imageHandler;
    private final ReviewRepository reviewRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final CategoryRepository categoryRepository;

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

        String thumbnail;
        if (Objects.isNull(multipartFileList)) thumbnail = responseReview.getProduct().getThumbnail();
        else thumbnail = createReviewImages(multipartFileList, reviewId.intValue());

        requestReview = ReviewRequestDTO.toUpdateEntity(reviewRequestDTO, reviewId, thumbnail);
        reviewRepository.save(requestReview);

        return "success";
    }

    @Override
    @Transactional
    public ReviewPagingDTO findAllReview(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByPosting(Posting.POSTING, pageable);
        List<ReviewResponseDTO> reviewResponseDTOList = reviews.getContent().stream().map(ReviewResponseDTO::simple).collect(Collectors.toList());

        return ReviewPagingDTO.builder()
                .reviewList(reviewResponseDTOList)
                .offset(reviews.getPageable().getOffset())
                .pageNumber(reviews.getPageable().getPageNumber())
                .pageSize(reviews.getPageable().getPageSize())
                .totalPages(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .size(reviews.getSize())
                .build();

    }

    @Override
    @Transactional
    public ReviewResponseDTO findDetailReview(int reviewId) {


        // 사용할 Review 엔티티
        Review responseReview = reviewRepository.findAllByIdAndPosting((long) reviewId, Posting.POSTING).orElseThrow(ReviewNotFoundException::new);
        // 조회수 증가 적용
        reviewRepository.setViewCount((long) reviewId);

        // 리뷰 내용 List ( 이미지 + 텍스트 )
        List<ReviewContentDTO> reviewContentDTOList =
                reviewContentRepository.findAllByReview(responseReview).stream().map(ReviewContentDTO::new).collect(Collectors.toList());

        // 리뷰 Tag List 를 위한 category List
        List<Category> categoryList = categoryRepository.findAllByProduct(responseReview.getProduct());
        // 리뷰 Tag List 저장
        List<String> tagList = new ArrayList<>();

        // Category 가장 하단 / 중단 구분하여 저장
        for (Category category : categoryList){
            if (!Objects.isNull(category.getSubdivision())) tagList.add(category.getSubdivision());
            else if (!Objects.isNull(category.getMiddleCategory())) tagList.add(category.getMiddleCategory());
        }

        return ReviewResponseDTO.detail(responseReview, reviewContentDTOList, tagList);
    }

    @Override
    @Transactional
    public Boolean matchReviewPassword(int reviewId, String password) {
        Review responseReview = reviewRepository.findByIdAndPassword((long) reviewId, password).orElseThrow(ReviewPasswordMismatchException::new);

        return true;
    }


    @Override
    @Transactional
    public String updateReview(int reviewId,
                                          ReviewRequestDTO reviewRequestDTO,
                                          List<MultipartFile> multipartFileList,
                                          String text) throws IOException {
        // 기존 리뷰 내용 삭제
        Review responseReview = reviewRepository.findById((long) reviewId).get();
        reviewContentRepository.deleteAllByReview(responseReview);

        imageHandler.createFolder("review", reviewId);

        // 리뷰 저장
        createReviewText(text, reviewId);
        String thumbnail = createReviewImages(multipartFileList, reviewId);

        Review requestReview = ReviewRequestDTO.toUpdateEntity(reviewRequestDTO, (long) reviewId, thumbnail);
        reviewRepository.save(requestReview);

        return "success";
    }

    @Override
    @Transactional
    public String deleteReview(int reviewId) {

        reviewRepository.updateReviewDelete((long) reviewId, Posting.DELETE);

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
     * @param text 입력받은 Text 내용
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
