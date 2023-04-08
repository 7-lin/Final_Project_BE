package com.fc.final7.domain.wishlist.entity;

import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "wishlist")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Wishlist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
