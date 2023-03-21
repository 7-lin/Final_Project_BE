package com.fc.final7.domain.product.entity;

import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.global.entity.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    @OneToOne(mappedBy = "product")
    private Reservation reservation;


    @OneToMany()

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "region")
    private String region;

    @Column(name = "feature")
    private String feature;

    @Column(name = "flight")
    private String flight;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'OPEN'")
    @Enumerated(STRING)
    private SalesStatus salesStatus;

}
