package com.fc.final7.domain.product.entity;

import com.fc.final7.global.entity.Auditing;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;
}
