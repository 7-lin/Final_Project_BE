package com.fc.final7.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //대분류
    @Column(name = "main_category", columnDefinition = "VARCHAR(40)")
    private String mainCategory;

    //중분류
    @Column(name = "middle_category", columnDefinition = "VARCHAR(40)")
    private String middleCategory;

    @Column(name = "subdivision", columnDefinition = "VARCHAR(40)")
    private String subdivision;



    //연관관계 편의 메서드
    public Category (String mainCategory, String middleCategory, String subdivision, Product product) {
        this.mainCategory = mainCategory;
        this.middleCategory = middleCategory;
        this.subdivision = subdivision;
        if(product != null) {
            relation(product);
        }
    }

    public void relation(Product product) {
        this.product = product;
        product.getCategories().add(this);
    }
}
