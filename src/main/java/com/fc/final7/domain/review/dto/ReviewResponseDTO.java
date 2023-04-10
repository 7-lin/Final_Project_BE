package com.fc.final7.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fc.final7.domain.review.entity.Review;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Integer reviewId;
    private Integer productId;
    private Integer reservationId;
    private String reviewThumbnail;
    private String reviewWriter;
    private BigDecimal reviewGrade;
    private Integer viewCount;
    private LocalDateTime createdDate;
    private String reviewTitle;

    private String productThumbnail;
    private String productTitle;

    private List<ReviewReservationPeriodDTO> reviewReservationPeriodDTOList = new ArrayList<>();
    private List<ReviewReservationOptionDTO> reviewReservationOptionDTOList = new ArrayList<>();

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
                .tagList(review.getProduct().getCategories().stream()
                        .map(category -> Optional.ofNullable(category.getSubdivision())
                                .orElseGet(() -> category.getMiddleCategory()))
                        .collect(Collectors.toList())
                )
                .build();
    }

    static public ReviewResponseDTO detail (Review review,
                                            List<ReviewContentDTO> reviewContentDTOList,
                                            List<String> tagList){
        return ReviewResponseDTO.builder()
                .reviewId(review.getId().intValue())
                .productId(review.getProduct().getId().intValue())
                .reservationId(review.getReservation().getId().intValue())
                .reviewWriter(review.getName())
                .reviewGrade(review.getGrade())
                .viewCount(review.getViewCount().intValue())
                .createdDate(review.getCreatedDate())
                .reviewTitle(review.getTitle())

                .productThumbnail(review.getProduct().getThumbnail())
                .productTitle(review.getProduct().getTitle())

                .reviewReservationPeriodDTOList(Optional.ofNullable(review.getReservation().getPeriods())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(reservationPeriod -> Objects.isNull(reservationPeriod) ? null : new ReviewReservationPeriodDTO(reservationPeriod))
                        .collect(Collectors.toList()))
                .reviewReservationOptionDTOList(Optional.ofNullable(review.getReservation().getOptions())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .map(reservationOption -> Objects.isNull(reservationOption) ? null : new ReviewReservationOptionDTO(reservationOption))
                        .collect(Collectors.toList()))

                .reservationPrice(review.getReservation().getPrice().intValue())
                .reservationPeople(review.getReservation().getPeople())

                .reviewContentDTOList(reviewContentDTOList)
                .tagList(tagList)
                .build();
    }
}
