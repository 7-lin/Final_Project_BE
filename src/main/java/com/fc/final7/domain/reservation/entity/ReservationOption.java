package com.fc.final7.domain.reservation.entity;

import com.fc.final7.domain.product.entity.ProductOption;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "reservation_product_option")
@Builder
public class ReservationOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_option_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(name = "reservation_amount")
    private Integer reservationAmount;

    //연관관계 편의 메서드
    public ReservationOption(Reservation reservation, ProductOption productOption, Integer reservationAmount) {
        if(reservation != null) {
            relation(reservation);
        }
        this.productOption = productOption;
        this.reservationAmount = reservationAmount;
    }

    private void relation(Reservation reservation) {
        this.reservation = reservation;
        reservation.getOptions().add(this);
    }
}
