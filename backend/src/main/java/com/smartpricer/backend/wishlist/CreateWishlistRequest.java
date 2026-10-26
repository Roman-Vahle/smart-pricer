package com.smartpricer.backend.wishlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateWishlistRequest {

        @NotBlank
        @Size(max = 20)
        private String wishlistName;

        public CreateWishlistRequest() {}

        public CreateWishlistRequest(String wishlistName) {
            this.wishlistName = wishlistName;
        }

        public String getWishlistName() {
            return wishlistName;
        }
        public void setWishlistName(String wishlistName) {
            this.wishlistName = wishlistName;
        }

    }


