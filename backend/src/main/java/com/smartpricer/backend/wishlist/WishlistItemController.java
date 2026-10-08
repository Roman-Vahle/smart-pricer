package com.smartpricer.backend.wishlist;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<WishlistItemDTO> addWishlistItem (@PathVariable Long wishlistId, @Valid @RequestBody AddWishlistItemRequest wishlistItemRequest) {

        WishlistItem createdWishlistItem =  wishlistItemService.addWishlistItem(wishlistId, wishlistItemRequest.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new WishlistItemDTO(createdWishlistItem));

    }

    @GetMapping
    public List<WishlistItemDTO> getAllWishlistItems(@PathVariable Long wishlistId) {

        List<WishlistItem> WishlistItems = wishlistItemService.getAllWishlistItems(wishlistId);
        List<WishlistItemDTO> WishlistItemDTOS = new ArrayList<>();

        for (WishlistItem WishlistItem : WishlistItems) {
            WishlistItemDTOS.add(new WishlistItemDTO(WishlistItem));
        }

        return WishlistItemDTOS;

    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<Void> deleteWishlistItem (@PathVariable Long wishlistId, @PathVariable Long wishlistItemId) {

        boolean deleted = wishlistItemService.deleteWishlistItem(wishlistId,wishlistItemId);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();

    }

}
