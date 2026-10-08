package com.smartpricer.backend.wishlist;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishlistDTO> createWishlist(@Valid @RequestBody CreateWishlistRequest request) {

        Wishlist createdWishlist = wishlistService.createWishlist(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new WishlistDTO(createdWishlist));

    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistDTO> getWishlistById(@PathVariable Long wishlistId) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        WishlistDTO wishlistDTO = new WishlistDTO(wishlist);

        return ResponseEntity.ok(wishlistDTO);
    }

    @GetMapping("/user/{userId}")
    public List<WishlistDTO> getAllWishlistsByUserId(@PathVariable Long userId) {

        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(userId);

        List<WishlistDTO> wishlistDTOs = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            WishlistDTO wishlistDTO = new WishlistDTO(wishlist);
            wishlistDTOs.add(wishlistDTO);
        }
        return wishlistDTOs;
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlistByWishlistId(@PathVariable Long wishlistId) {

        boolean deleted = wishlistService.deleteWishlist(wishlistId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

}
