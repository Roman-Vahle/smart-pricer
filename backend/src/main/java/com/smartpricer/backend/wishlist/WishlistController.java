package com.smartpricer.backend.wishlist;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishlistDTO> createWishlist(@Valid @RequestBody CreateWishlistRequest request, @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        Wishlist createdWishlist = wishlistService.createWishlist(request, authenticatedId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new WishlistDTO(createdWishlist));

    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistDTO> getWishlistById(@PathVariable Long wishlistId, @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId, authenticatedId);

        WishlistDTO wishlistDTO = new WishlistDTO(wishlist);

        return ResponseEntity.ok(wishlistDTO);
    }

    @GetMapping("/me")
    public List<WishlistDTO> getAllWishlistsByUserId(@AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(authenticatedId);

        List<WishlistDTO> wishlistDTOs = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            WishlistDTO wishlistDTO = new WishlistDTO(wishlist);
            wishlistDTOs.add(wishlistDTO);
        }
        return wishlistDTOs;
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlistByWishlistId(@AuthenticationPrincipal Jwt jwt, @PathVariable Long wishlistId) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        boolean deleted = wishlistService.deleteWishlist(authenticatedId, wishlistId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
