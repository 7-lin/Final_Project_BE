package com.fc.final7.domain.review.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewPagingDTO {
    private List<ReviewResponseDTO> reviewList;
    private Long offset;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private Integer size;
}
