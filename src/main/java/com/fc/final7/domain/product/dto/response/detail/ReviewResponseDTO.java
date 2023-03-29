package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.review.entity.Review;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewResponseDTO {

    private Long reviewId;
    private String createdDate;
    private String thumbnail;
    private Long viewCount;
    private String title;

    public ReviewResponseDTO(Review review) {
        this.reviewId = review.getId();
        this.createdDate = review.getCreatedDate().format(DateTimeFormatter.ISO_DATE);
        this.thumbnail = review.getThumbnail();
        this.viewCount = review.getViewCount();
        this.title = review.getName();
    }
}
