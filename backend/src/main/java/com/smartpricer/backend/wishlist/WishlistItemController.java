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
@RequestMapping("/api/wishlists/{wishlistId}/items")
public class WishlistItemController {

    private final WishlistItemService  wishlistItemService;


    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;

    }

    @PostMapping
    public ResponseEntity<WishlistItemDTO> addWishlistItem (@PathVariable Long wishlistId
            , @Valid @RequestBody AddWishlistItemRequest wishlistItemRequest
                , @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        WishlistItem createdWishlistItem =  wishlistItemService.addWishlistItem(wishlistId, wishlistItemRequest.getProductId(), authenticatedId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new WishlistItemDTO(createdWishlistItem));

    }

    @GetMapping
    public List<WishlistItemDTO> getAllWishlistItems(@PathVariable Long wishlistId, @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        List<WishlistItem> WishlistItems = wishlistItemService.getAllWishlistItems(wishlistId, authenticatedId);
        List<WishlistItemDTO> WishlistItemDTOS = new ArrayList<>();

        for (WishlistItem WishlistItem : WishlistItems) {
            WishlistItemDTOS.add(new WishlistItemDTO(WishlistItem));
        }

        return WishlistItemDTOS;

    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<Void> deleteWishlistItem (@PathVariable Long wishlistId, @PathVariable Long wishlistItemId, @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        boolean deleted = wishlistItemService.deleteWishlistItem(wishlistId,wishlistItemId, authenticatedId);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();

    }

}
