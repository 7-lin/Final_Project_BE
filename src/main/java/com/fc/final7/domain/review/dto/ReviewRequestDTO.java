package com.fc.final7.domain.review.dto;

import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.domain.review.entity.Review;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequestDTO {
    private int productId;
    private int reservationId;
    private String reviewTitle;
    private String name;
    private String password;
    private BigDecimal grade;

    public static Review toEntity(ReviewRequestDTO reviewRequestDTO){
        return Review.builder()
                .product(Product.builder().id((long) reviewRequestDTO.getProductId()).build())
                .reservation(Reservation.builder().id((long) reviewRequestDTO.getReservationId()).build())
                .title(reviewRequestDTO.getReviewTitle())
                .grade(reviewRequestDTO.getGrade())
                .thumbnail(null)
                .name(reviewRequestDTO.getName())
                .viewCount(0L)
                .password(reviewRequestDTO.getPassword())
                .build();
    }

    public static Review toUpdateEntity(ReviewRequestDTO reviewRequestDTO, Long id, String thumbnail){
        return Review.builder()
                .id(id)
                .product(Product.builder().id((long) reviewRequestDTO.getProductId()).build())
                .reservation(Reservation.builder().id((long) reviewRequestDTO.getReservationId()).build())
                .title(reviewRequestDTO.getReviewTitle())
                .grade(reviewRequestDTO.getGrade())
                .thumbnail(null)
                .name(reviewRequestDTO.getName())
                .viewCount(0L)
                .password(reviewRequestDTO.getPassword())
                .thumbnail(thumbnail)
                .build();
    }
}
