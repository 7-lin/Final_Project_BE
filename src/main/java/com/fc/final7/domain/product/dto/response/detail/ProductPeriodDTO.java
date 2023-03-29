package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.product.entity.ProductPeriod;
import lombok.*;

import java.text.SimpleDateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductPeriodDTO {

    private String startDate;
    private String endDate;

    public ProductPeriodDTO (ProductPeriod productPeriod) {

        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(productPeriod.getStartDate());
        this.endDate = new SimpleDateFormat("yyyy-MM-dd").format(productPeriod.getEndDate());
    }
}
