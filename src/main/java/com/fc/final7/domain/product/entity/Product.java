package com.fc.final7.domain.product.entity;

import com.fc.final7.domain.member.entity.WishList;
import com.fc.final7.domain.review.entity.Review;
import com.fc.final7.global.entity.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Product extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<WishList> wishLists = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<Category> categories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<ProductPeriod> productPeriods = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<ProductContent> productContents = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<ProductOption> options = new HashSet<>();

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "region", columnDefinition = "VARCHAR(60)")
    private String region;

    @Column(name = "feature", columnDefinition = "VARCHAR(60)")
    private String feature;

    @Column(name = "flight", columnDefinition = "VARCHAR(60)")
    private String flight;

    @Column(name = "price")
    private Long price;

    @Column(name = "term")
    private Integer term;

    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'OPEN'")
    @Enumerated(STRING)
    private SalesStatus salesStatus;

}
