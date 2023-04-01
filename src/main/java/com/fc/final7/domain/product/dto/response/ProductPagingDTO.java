package com.fc.final7.domain.product.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductPagingDTO {

    private List<ProductResponseDTO> products;
    private Long offset;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private Integer size;
}
