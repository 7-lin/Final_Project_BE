package com.fc.final7.domain.review.dto;

import com.fc.final7.domain.review.entity.ReviewContent;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewContentDTO {
    private int reviewContentId;
    private String content;
    private String type;
    private int priority;
    private int reviewId;

    public ReviewContentDTO(ReviewContent reviewContent){
        reviewContentId = reviewContent.getId().intValue();
        content = reviewContent.getContent();
        type = reviewContent.getContentType().toString();
        priority = reviewContent.getPriority().intValue();
        reviewId = reviewContent.getReview().getId().intValue();
    }
}
