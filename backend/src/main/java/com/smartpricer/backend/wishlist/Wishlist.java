package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @Column(nullable = false)
    private String wishlistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wishlist(){}

    public Wishlist(String wishlistName, User user) {
        this.wishlistName = wishlistName;
        this.user = user;
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
