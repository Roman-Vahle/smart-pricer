package com.smartpricer.backend.wishlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateWishlistRequest {

        @NotNull
        @Positive
        private Long userId;

        @NotBlank
        @Size(max = 20)
        private String wishlistName;

        public CreateWishlistRequest() {}

        public CreateWishlistRequest(Long userId, String wishlistName) {
            this.userId = userId;
            this.wishlistName = wishlistName;
        }

        public Long getUserId() {
            return userId;
        }
        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getWishlistName() {
            return wishlistName;
        }
        public void setWishlistName(String wishlistName) {
            this.wishlistName = wishlistName;
        }

    }


