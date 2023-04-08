package com.fc.final7.domain.wishlist.repository;

import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByMemberAndProduct(Member member, Product Product);

    void deleteAllByMember(Member member);

}
