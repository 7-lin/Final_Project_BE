package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.product.entity.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDetailResponseDTO {

    private Long productId;
    private String thumbnail;
    private String productName;
    private Long price;
    private String briefExplanation;
    private String region;
    private String feature;
    private String flight;
    private List<ProductPeriodDTO> period = new ArrayList<>();
    private List<ProductContentDTO> contents = new ArrayList<>();
    private List<ProductOptionDTO> options = new ArrayList<>();

    public ProductDetailResponseDTO toDTO(Product product) {
        this.productId = product.getId();
        this.thumbnail = product.getThumbnail();
        this.productName = product.getTitle();
        this.price = product.getPrice();
        this.briefExplanation = product.getDescription();
        this.region = product.getRegion();
        this.feature = product.getFeature();
        this.flight = product.getFlight();


        //Set으로 받기때문에 순서가 보장이 되지않아 애플리케이션단에서 한번 정렬을 했습니다.
        this.period = product.getProductPeriods().stream()
                .map(ProductPeriodDTO::new)
                        .sorted(Comparator.comparing(ProductPeriodDTO::getStartDate))
                .collect(Collectors.toList());

        //Set으로 받기때문에 순서가 보장이 되지않아 애플리케이션단에서 한번 정렬을 했습니다.
        this.contents = product.getProductContents().stream()
                .map(ProductContentDTO::new)
                .sorted(Comparator.comparing(ProductContentDTO::getPriority))
                .collect(Collectors.toList());

        this.options = product.getOptions().stream()
                .map(ProductOptionDTO::new).collect(Collectors.toList());
        return this;
    }
}
