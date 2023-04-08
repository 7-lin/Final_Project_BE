package com.fc.final7.domain.wishlist.service.Impl;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.wishlist.dto.WishlistRequestDTO;
import com.fc.final7.domain.wishlist.entity.Wishlist;
import com.fc.final7.domain.wishlist.repository.WishlistRepository;
import com.fc.final7.domain.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public String createWishlist(WishlistRequestDTO wishlistRequestDTO, String accessToken) {

        String email = jwtProvider.getClaimsFromToken(accessToken).get("email", String.class);
        Member member = memberRepository.findByEmail(email).get();

        wishlistRequestDTO.setMemberId(member.getId().intValue());
        Wishlist wishlist = WishlistRequestDTO.toEntity(wishlistRequestDTO);
        wishlistRepository.save(wishlist);

        return "success";
    }

    @Override
    public String deleteWishlist(WishlistRequestDTO wishlistRequestDTO, String accessToken) {

        String email = jwtProvider.getClaimsFromToken(accessToken).get("email", String.class);
        Member member = memberRepository.findByEmail(email).get();

        Wishlist wishlist = wishlistRepository.findByMemberAndProduct(member, Product.builder().id((long) wishlistRequestDTO.getProductId()).build()).get();
        wishlistRepository.delete(wishlist);

        return "success";
    }
}
