package com.fc.final7.domain.product.entity;

import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.global.entity.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "product_period")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class ProductPeriod extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_period_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "productPeriod")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "start_date", columnDefinition = "DATE")
    private Date startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private Date endDate;

    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'OPEN'")
    @Enumerated(STRING)
    private SalesStatus salesStatus;


    //연관관계 메서드
    public ProductPeriod(Date startDate, Date endDate, SalesStatus salesStatus, Product product) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.salesStatus = salesStatus;
        if(product != null) {
            relation(product);
        }
    }

    public void relation(Product product) {
        this.product = product;
        product.getProductPeriods().add(this);
    }
}
