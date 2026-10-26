package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {

    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;


    public WishlistItemService(WishlistItemRepository wishlistItemRepository, WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public WishlistItem addWishlistItem(Long wishlistId, Long productId, Long authenticatedId){

        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);

        if (wishlistOptional.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        Wishlist wishlist = wishlistOptional.get();

        if (!wishlist.getUser().getUserId().equals(authenticatedId)){
            throw new  AccessDeniedException("Access denied");
        }

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }

        if (wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)){
            throw new ResourceConflictException("Product " + productId + " already exists in wishlist " + wishlistId);
        }

        WishlistItem wishlistItem = new WishlistItem();

        wishlistItem.setWishlist(wishlist);

        wishlistItem.setProduct(productOptional.get());

        return wishlistItemRepository.save(wishlistItem);
    }

    @Transactional(readOnly = true)
    public List <WishlistItem> getAllWishlistItems(Long wishlistId, Long authenticatedId) {

        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);

        if (wishlistOptional.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        if(!wishlistOptional.get().getUser().getUserId().equals(authenticatedId)){
            throw new  AccessDeniedException("Access denied");
        }

        return wishlistItemRepository.findAllByWishlistWishlistId(wishlistId);

    }

    @Transactional
    public boolean deleteWishlistItem(Long wishlistId, Long wishlistItemId, Long  authenticatedId) {

        Optional<WishlistItem> optionalItem = wishlistItemRepository.findById(wishlistItemId);

        if (optionalItem.isEmpty()){
            return false;
        }

        WishlistItem wishlistItem = optionalItem.get();

        if (!wishlistItem.getWishlist().getUser().getUserId().equals(authenticatedId)){
            throw new  AccessDeniedException("Access denied");
        }

        if (!wishlistItem.getWishlist().getWishlistId().equals(wishlistId)){
            return false;
        }

        wishlistItemRepository.delete(wishlistItem);

        return true;

    }

}
