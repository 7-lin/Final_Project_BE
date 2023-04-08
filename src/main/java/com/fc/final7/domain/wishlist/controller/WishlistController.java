package com.fc.final7.domain.wishlist.controller;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.wishlist.dto.WishlistRequestDTO;
import com.fc.final7.domain.wishlist.service.WishlistService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    public BaseResponse<Null> doCreateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO,
                                               HttpServletRequest request) {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        return BaseResponse.of(0, wishlistService.createWishlist(wishlistRequestDTO, accessToken), null);
    }
}
