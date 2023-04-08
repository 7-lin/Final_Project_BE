package com.fc.final7.domain.wishlist.service;

import com.fc.final7.domain.wishlist.dto.WishlistRequestDTO;

public interface WishlistService {

    String createWishlist(WishlistRequestDTO wishlistRequestDTO, String accessToken);

}
