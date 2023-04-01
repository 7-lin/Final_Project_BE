package com.fc.final7.domain.product.entity;

import com.fc.final7.global.entity.ContentType;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "product_content")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class ProductContent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_content_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "type", columnDefinition = "VARCHAR(20)")
    @Enumerated(STRING)
    private ContentType contentType;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    //연관관계 메서드
    public ProductContent(Long priority, ContentType contentType, String content, Product product) {
        this.priority = priority;
        this.contentType = contentType;
        this.content = content;
        if(product != null) {
            relation(product);
        }
    }
    public void relation(Product product) {
        this.product = product;
        product.getProductContents().add(this);
    }
}
