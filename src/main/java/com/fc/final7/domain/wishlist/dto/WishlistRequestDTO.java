package com.fc.final7.domain.wishlist.dto;

import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.wishlist.entity.Wishlist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequestDTO {
    private int memberId;
    private int productId;

    public static Wishlist toEntity(WishlistRequestDTO wishlistRequestDTO){
        return Wishlist.builder()
                .member(Member.builder().id((long) wishlistRequestDTO.memberId).build())
                .product(Product.builder().id((long) wishlistRequestDTO.productId).build())
                .build();
    }
}
