package com.smartpricer.backend.wishlist;

public class WishlistItemDTO {

    private Long wishlistItemId;
    private Long wishlistId;
    private Long productId;
    private String productName;

    public WishlistItemDTO() {}

    public WishlistItemDTO(WishlistItem wishlistItem) {
        this.wishlistItemId = wishlistItem.getWishlistItemId();
        this.wishlistId = wishlistItem.getWishlist().getWishlistId();
        this.productId = wishlistItem.getProduct().getProductId();
        this.productName = wishlistItem.getProduct().getProductName();
    }

    public Long getWishlistItemId() {
        return wishlistItemId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }


}
