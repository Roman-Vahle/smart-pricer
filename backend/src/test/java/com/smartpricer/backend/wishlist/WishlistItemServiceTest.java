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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}
