package com.fc.final7.domain.wishlist.controller;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.wishlist.dto.WishlistRequestDTO;
import com.fc.final7.domain.wishlist.service.WishlistService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.List;

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

    @DeleteMapping("/wishlist")
    public BaseResponse<Null> doDeleteWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO,
                                               HttpServletRequest request) {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        return BaseResponse.of(0, wishlistService.deleteWishlist(wishlistRequestDTO, accessToken), null);
    }

    @GetMapping("/wishlist")
    public BaseResponse<List<ProductResponseDTO>> doReadWishlist(@RequestBody List<ProductResponseDTO> productResponseDTOList,
                                                                 HttpServletRequest request) {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        return BaseResponse.of(productResponseDTOList.size(), "success", wishlistService.readWishlist(productResponseDTOList, accessToken));
    }
}
