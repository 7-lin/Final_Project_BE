package com.fc.final7.domain.product.entity;

import lombok.*;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // TODO: 2023-03-21 카테고리 타입을 ENUM 으로 할지 말지 고민

    @Column(name = "content")
    private String content;
}
