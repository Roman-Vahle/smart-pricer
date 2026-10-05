package com.smartpricer.backend.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findAllByWishlistWishlistId(Long wishlistId);
    boolean existsByWishlistWishlistIdAndProductProductId(Long wishlistId, Long productId);
}
