package com.fc.final7.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.review.entity.Review;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Integer reviewId;
    private String reviewThumbnail;
    private String reviewWriter;
    private BigDecimal reviewGrade;
    private Integer viewCount;
    private LocalDateTime createdDate;
    private String reviewTitle;

    private String productThumbnail;
    private String productTitle;
    private String reservationStart;
    private String reservationEnd;
    private String reservationOption;
    private Integer reservationPrice;
    private Integer reservationPeople;

    private List<ReviewContentDTO> reviewContentDTOList;
    private List<String> tagList;

    static public ReviewResponseDTO simple (Review review){
        return ReviewResponseDTO.builder()
                .reviewId(review.getId().intValue())
                .reviewTitle(review.getTitle())
                .reviewThumbnail(review.getThumbnail())
                .reviewGrade(review.getGrade())
                .build();
    }

    static public ReviewResponseDTO detail (Review review,
                                            List<ReviewContentDTO> reviewContentDTOList,
                                            List<String> tagList,
                                            String option){
        return ReviewResponseDTO.builder()
                .reviewId(review.getId().intValue())
                .reviewWriter(review.getName())
                .reviewGrade(review.getGrade())
                .viewCount(review.getViewCount().intValue())
                .createdDate(review.getCreatedDate())
                .reviewTitle(review.getTitle())

                .productThumbnail(review.getProduct().getThumbnail())
                .productTitle(review.getProduct().getTitle())
                .reservationStart(review.getReservation().getProductPeriod().getStartDate())
                .reservationEnd(review.getReservation().getProductPeriod().getEndDate())
                .reservationOption(option)
                .reservationPrice(review.getReservation().getPrice().intValue())
                .reservationPeople(review.getReservation().getPeople())
                .reviewContentDTOList(reviewContentDTOList)
                .tagList(tagList)
                .build();
    }
}
