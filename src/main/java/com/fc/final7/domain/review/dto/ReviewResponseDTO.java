package com.fc.final7.domain.review.dto;

import com.fc.final7.domain.review.entity.Review;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {


    private String reviewWriter;
    private BigDecimal reviewGrade;
    private int viewCount;
    private LocalDateTime createdDate;
    private String reviewTitle;

    private String productTitle;
    private String reservationStart;
    private String reservationEnd;
    private String reservationOption;
    private int reservationPrice;
    private int reservationPeople;

    @Builder.Default
    private List<ReviewContentDTO> reviewContentDTOList = new ArrayList<>();

    static public ReviewResponseDTO simple (Review review){
        return ReviewResponseDTO.builder()
                .reviewTitle(review.getTitle())
                .reviewGrade(review.getGrade())
                .build();
    }
}
