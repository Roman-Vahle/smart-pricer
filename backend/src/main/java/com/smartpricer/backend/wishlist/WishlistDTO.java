package com.smartpricer.backend.wishlist;

public class WishlistDTO {

    private Long wishlistId;
    private String wishlistName;
    private Long userId;

    public WishlistDTO() {}

    public WishlistDTO(Wishlist wishlist) {
        this.wishlistId = wishlist.getWishlistId();
        this.wishlistName = wishlist.getWishlistName();
        this.userId = wishlist.getUser().getUserId();
    }

    public Long getWishlistId() {
        return wishlistId;
    }
    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }
    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
