package com.fc.final7.domain.reservation.entity;

import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.product.entity.ProductOption;
import com.fc.final7.domain.product.entity.ProductPeriod;
import com.fc.final7.global.entity.Auditing;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "reservation")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_period_id")
    private ProductPeriod productPeriod;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    @Enumerated(STRING)
    private Status status;

    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(40)")
    private String phone;

    @Column(name = "notice", columnDefinition = "TINYINT")
    private Boolean notice;

    @Column(name = "price")
    private Long price;

    @Column(name = "people")
    private Integer people;
}
