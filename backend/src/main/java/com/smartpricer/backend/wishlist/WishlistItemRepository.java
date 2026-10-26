package com.smartpricer.backend.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findAllByWishlistWishlistId(Long wishlistId);

    //check if product already exists in wishlist, true already exists in wishlist or false doesn't exist in wishlist (valid).
    boolean existsByWishlistWishlistIdAndProductProductId(Long wishlistId, Long productId);
}
