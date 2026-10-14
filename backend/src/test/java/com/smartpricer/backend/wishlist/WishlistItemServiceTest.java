package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class WishlistItemServiceTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishlistItemService wishlistItemService;

    @Test
    public void addWishlistItem_whenProductAlreadyExists_throwsConflict() {

        Long wishlistId = 1L;
        Long productId = 2L;

        Wishlist wishlist = new Wishlist();
        Product product = new Product();

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId));

        verify(wishlistItemRepository, never()).save(any(WishlistItem.class));

    }

    @Test
    public void addWishlistItem_whenValid_savesAndReturnsWishlistItem() {

        Long wishlistId = 1L;
        Long productId = 2L;

        Wishlist  wishlist = new Wishlist();
        Product product = new Product();

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)).thenReturn(false);

        WishlistItem savedItem = new WishlistItem(product, wishlist);

        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(savedItem);

        WishlistItem wishlistItem = wishlistItemService.addWishlistItem(wishlistId, productId);

        assertSame(savedItem, wishlistItem);

        verify(wishlistItemRepository, times(1)).save(any(WishlistItem.class));

    }

    @Test
    void addWishlistItem_whenWishlistMissing_throwsNotFound() {

        Long wishlistId = 1L;
        Long productId = 2L;

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId));

        verify(wishlistItemRepository, never()).save(any(WishlistItem.class));

        verifyNoInteractions(productRepository);

    }

    @Test
    void addWishlistItem_whenProductMissing_throwsNotFound() {

        Long wishlistId = 1L;
        Long productId = 2L;

        Wishlist  wishlist = new Wishlist();

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId));

        verifyNoInteractions(wishlistItemRepository);

    }

    @Test
    void deleteWishlistItem_whenItemExistsAndBelongsToWishlist_deletesWishlistItem() {

        Wishlist wishlist = new Wishlist();
        WishlistItem wishlistItem = new WishlistItem();

        wishlist.setWishlistId(1L);
        wishlistItem.setWishlistItemId(10L);

        wishlistItem.setWishlist(wishlist);

        when(wishlistItemRepository.findById(10L)).thenReturn(Optional.of(wishlistItem));

        boolean result = wishlistItemService.deleteWishlistItem(1L, 10L);

        assertTrue(result);

        verify(wishlistItemRepository, times(1)).delete(wishlistItem);

    }

    @Test
    void deleteWishlistItem_whenItemDoesNotExist_returnsFalse() {

        Long wishlistId = 1L;
        Long wishlistItemId = 10L;

        when(wishlistItemRepository.findById(wishlistItemId)).thenReturn(Optional.empty());

        boolean result = wishlistItemService.deleteWishlistItem(wishlistId, wishlistItemId);

        assertFalse(result);

        verify(wishlistItemRepository, never()).delete(any(WishlistItem.class));

    }

    @Test
    void deleteWishlistItem_whenItemBelongsToDifferentWishlist_returnsFalse() {

        Long requestedWishlistId = 1L;
        Long actualWishlistId = 2L;
        Long wishlistItemId = 10L;

        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(actualWishlistId);

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setWishlist(wishlist);

        wishlistItem.setWishlistItemId(wishlistItemId);

        when(wishlistItemRepository.findById(wishlistItemId)).thenReturn(Optional.of(wishlistItem));

        boolean result = wishlistItemService.deleteWishlistItem(requestedWishlistId, wishlistItemId);

        assertFalse(result);

        verify(wishlistItemRepository, times(1)).findById(wishlistItemId);

        verify(wishlistItemRepository, never()).delete(any(WishlistItem.class));

    }

    @Test
    void getAllWishlistItems_whenWishlistExists_returnsItems() {

        Long wishlistId = 1L;

        WishlistItem item1 = new WishlistItem();
        WishlistItem item2 = new WishlistItem();

        when(wishlistRepository.existsById(wishlistId)).thenReturn(true);

        when(wishlistItemRepository.findAllByWishlistWishlistId(wishlistId)).thenReturn(List.of(item1, item2));

        List<WishlistItem> result = wishlistItemService.getAllWishlistItems(wishlistId);

        assertEquals(2, result.size());

        assertTrue(result.contains(item1));

        assertTrue(result.contains(item2));

        verify(wishlistRepository, times(1)).existsById(wishlistId);

        verify(wishlistItemRepository, times(1)).findAllByWishlistWishlistId(wishlistId);

    }

}
