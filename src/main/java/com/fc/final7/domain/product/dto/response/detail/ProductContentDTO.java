package com.fc.final7.domain.product.dto.response.detail;

import com.fc.final7.domain.product.entity.ProductContent;
import com.fc.final7.global.entity.ContentType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductContentDTO {

    private String content;
    private ContentType type;

    private Long priority;


    public ProductContentDTO (ProductContent productContent) {
        this.content = productContent.getContent();
        this.type = productContent.getContentType();
        this.priority = productContent.getPriority();

    }
}
