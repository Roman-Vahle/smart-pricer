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
import com.smartpricer.backend.user.User;
import org.springframework.security.access.AccessDeniedException;
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
        Long authenticatedId = 13L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        Product product = new Product();

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId, authenticatedId));

        verify(wishlistItemRepository, never()).save(any(WishlistItem.class));

    }

    @Test
    public void addWishlistItem_whenValid_savesAndReturnsWishlistItem() {

        Long wishlistId = 1L;
        Long productId = 2L;
        Long authenticatedId = 13L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        Wishlist  wishlist = new Wishlist();
        wishlist.setUser(user);

        Product product = new Product();

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)).thenReturn(false);

        WishlistItem savedItem = new WishlistItem(product, wishlist);

        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(savedItem);

        WishlistItem wishlistItem = wishlistItemService.addWishlistItem(wishlistId, productId, authenticatedId);

        assertSame(savedItem, wishlistItem);

        verify(wishlistItemRepository, times(1)).save(any(WishlistItem.class));

    }

    @Test
    void addWishlistItem_whenWishlistMissing_throwsNotFound() {

        Long authenticatedId = 13L;
        Long wishlistId = 1L;
        Long productId = 2L;

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId, authenticatedId));

        verify(wishlistItemRepository, never()).save(any(WishlistItem.class));

        verifyNoInteractions(productRepository);

    }

    @Test
    void addWishlistItem_whenProductMissing_throwsNotFound() {

        Long wishlistId = 1L;
        Long productId = 2L;
        Long authenticatedId = 13L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        Wishlist  wishlist = new Wishlist();

        wishlist.setUser(user);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId, authenticatedId));

        verifyNoInteractions(wishlistItemRepository);

    }

    @Test
    void deleteWishlistItem_whenItemExistsAndBelongsToWishlist_deletesWishlistItem() {

        Wishlist wishlist = new Wishlist();

        WishlistItem wishlistItem = new WishlistItem();

        Long authenticatedId = 13L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        wishlist.setUser(user);

        wishlist.setWishlistId(1L);

        wishlistItem.setWishlistItemId(10L);

        wishlistItem.setWishlist(wishlist);

        when(wishlistItemRepository.findById(10L)).thenReturn(Optional.of(wishlistItem));

        boolean result = wishlistItemService.deleteWishlistItem(1L, 10L, authenticatedId);

        assertTrue(result);

        verify(wishlistItemRepository, times(1)).delete(wishlistItem);

    }

    @Test
    void deleteWishlistItem_whenItemDoesNotExist_returnsFalse() {

        Long wishlistId = 1L;
        Long wishlistItemId = 10L;
        Long authenticatedId = 13L;

        when(wishlistItemRepository.findById(wishlistItemId)).thenReturn(Optional.empty());

        boolean result = wishlistItemService.deleteWishlistItem(wishlistId, wishlistItemId, authenticatedId);

        assertFalse(result);

        verify(wishlistItemRepository, never()).delete(any(WishlistItem.class));

    }

    @Test
    void deleteWishlistItem_whenItemBelongsToDifferentWishlist_returnsFalse() {

        Long requestedWishlistId = 1L;
        Long actualWishlistId = 2L;
        Long wishlistItemId = 10L;
        Long authenticatedId = 13L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(actualWishlistId);

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setWishlist(wishlist);

        wishlist.setUser(user);

        wishlistItem.setWishlistItemId(wishlistItemId);

        when(wishlistItemRepository.findById(wishlistItemId)).thenReturn(Optional.of(wishlistItem));

        boolean result = wishlistItemService.deleteWishlistItem(requestedWishlistId, wishlistItemId,  authenticatedId);

        assertFalse(result);

        verify(wishlistItemRepository, times(1)).findById(wishlistItemId);

        verify(wishlistItemRepository, never()).delete(any(WishlistItem.class));

    }

    @Test
    void getAllWishlistItems_whenWishlistExists_returnsItems() {

        Long wishlistId = 1L;
        Long authenticatedId = 13L;

        WishlistItem item1 = new WishlistItem();
        WishlistItem item2 = new WishlistItem();

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(authenticatedId);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        when(wishlistItemRepository.findAllByWishlistWishlistId(wishlistId)).thenReturn(List.of(item1, item2));

        List<WishlistItem> result = wishlistItemService.getAllWishlistItems(wishlistId,  authenticatedId);

        assertEquals(2, result.size());

        assertTrue(result.contains(item1));

        assertTrue(result.contains(item2));

        verify(wishlistRepository, times(1)).findById(wishlistId);

        verify(wishlistItemRepository, times(1)).findAllByWishlistWishlistId(wishlistId);

    }

    @Test
    void getAllWishlistItems_whenWishlistDoesNotExist_throwsNotFound() {

        Long wishlistId = 999L;
        Long authenticatedId = 13L;

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistItemService.getAllWishlistItems(wishlistId,  authenticatedId));

        verifyNoInteractions(wishlistItemRepository);
    }

    @Test
    void addWishlistItem_whenUserDoesNotOwnWishlist_throwsAccessDenied() {

        Long wishlistId = 1L;
        Long productId = 2L;
        Long authenticatedId = 13L;
        Long wishlistOwnerId = 14L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(wishlistOwnerId);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        assertThrows(AccessDeniedException.class, () -> wishlistItemService.addWishlistItem(wishlistId, productId, authenticatedId));

        verifyNoInteractions(wishlistItemRepository);

        verifyNoInteractions(productRepository);

    }

    @Test
    void getAllWishlistItems_whenUserDoesNotOwnWishlist_throwsAccessDenied() {

        Long wishlistId = 1L;
        Long authenticatedId = 13L;
        Long wishlistOwnerId = 14L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(wishlistOwnerId);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        assertThrows(AccessDeniedException.class, () -> wishlistItemService.getAllWishlistItems(wishlistId, authenticatedId));

        verifyNoInteractions(wishlistItemRepository);

        }



    @Test
    void deleteWishlistItem_whenUserDoesNotOwnWishlist_throwsAccessDenied() {

        Long wishlistId = 1L;
        Long wishlistItemId = 10L;
        Long authenticatedId = 13L;
        Long wishlistOwnerId = 14L;

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(wishlistOwnerId);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setWishlist(wishlist);

        when(wishlistItemRepository.findById(wishlistItemId)).thenReturn(Optional.of(wishlistItem));

        assertThrows(AccessDeniedException.class, () -> wishlistItemService.deleteWishlistItem(wishlistId, wishlistItemId, authenticatedId));

        verify(wishlistItemRepository, times(1)).findById(wishlistItemId);

        verify(wishlistItemRepository, never()).delete(wishlistItem);

    }




}
