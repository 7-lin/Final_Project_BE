package com.fc.final7.domain.reservation.entity;

import com.fc.final7.domain.product.entity.ProductPeriod;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "reservation_product_period")
@Builder
public class ReservationPeriod {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_period_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_period_id")
    private ProductPeriod productPeriod;

    @Column(name = "reservation_amount")
    private Integer reservationAmount;

    //연관관계 편의 메서드
    public ReservationPeriod(Reservation reservation, ProductPeriod productPeriod, Integer reservationAmount) {
        if(reservation != null) {
            relation(reservation);
        }
        this.productPeriod = productPeriod;
        this.reservationAmount = reservationAmount;
    }

    private void relation(Reservation reservation) {
        this.reservation = reservation;
        reservation.getPeriods().add(this);
    }
}
