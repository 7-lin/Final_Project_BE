package com.fc.final7.domain.product.entity;

import lombok.*;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "product_option")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_option_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;

    @Column(name = "content", columnDefinition = "VARCHAR(40)")
    private String content;

    @Column(name = "price")
    private Long price;

    //연관관계 편의 메서드
    public ProductOption(String type, String content, Long price, Product product) {
        this.type = type;
        this.content = content;
        this.price = price;
        if(product != null) {
            relation(product);
        }
    }

    private void relation(Product product) {
        this.product = product;
        product.getOptions().add(this);
    }
}
