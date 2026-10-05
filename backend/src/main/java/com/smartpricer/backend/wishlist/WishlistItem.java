package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.product.Product;
import jakarta.persistence.*;
@Entity
@Table (name = "wishlist_item",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "wishlist_id"})
        })

public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Long wishlistItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    public WishlistItem() {}

    public WishlistItem(Product product, Wishlist wishlist) {
        this.product = product;
        this.wishlist = wishlist;
    }

    public Long getWishlistItemId() {
        return wishlistItemId;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }
    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

}
