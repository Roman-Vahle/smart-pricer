package com.smartpricer.backend.wishlist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddWishlistItemRequest {

    @NotNull
    @Positive
    private Long productId;

    public AddWishlistItemRequest () {}

    public AddWishlistItemRequest (Long productId) {
        this.productId = productId;
    }

    public Long getProductId () {
        return productId;
    }
    public void setProductId (Long productId) {
        this.productId = productId;
    }

}
