package com.fc.final7.domain.product.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "priority")
    @GeneratedValue(strategy = IDENTITY)
    private Long priority;

    @Column(name = "type")
    @Enumerated(STRING)
    private ContentType contentType;

    @Column(name = "content")
    private String content;
}
