package com.fc.final7.domain.wishlist.service;

import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.wishlist.dto.WishlistRequestDTO;

import java.util.List;

public interface WishlistService {

    String createWishlist(WishlistRequestDTO wishlistRequestDTO, String accessToken);

    String deleteWishlist(WishlistRequestDTO wishlistRequestDTO, String accessToken);

    List<ProductResponseDTO> readWishlist(List<ProductResponseDTO> productResponseDTOList, String accessToken);
}
