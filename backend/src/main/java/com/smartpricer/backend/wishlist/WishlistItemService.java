package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {

    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistService wishlistService;

    public WishlistItemService(WishlistItemRepository wishlistItemRepository, WishlistRepository wishlistRepository, ProductRepository productRepository, WishlistService wishlistService) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.wishlistService = wishlistService;
    }

    public WishlistItem addWishlistItem(Long wishlistId, Long productId){

        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);

        if (wishlistOptional.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }

        if (wishlistItemRepository.existsByWishlistWishlistIdAndProductProductId(wishlistId, productId)){
            throw new ResourceConflictException("Product " + productId + " already exists in wishlist " + wishlistId);
        }

        WishlistItem wishlistItem = new WishlistItem();

        wishlistItem.setWishlist(wishlistOptional.get());

        wishlistItem.setProduct(productOptional.get());

        return wishlistItemRepository.save(wishlistItem);

    }

    public List <WishlistItem> getAllWishlistItems(Long wishlistId) {

        if (!wishlistRepository.existsById(wishlistId)){
            throw new ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        return wishlistItemRepository.findAllByWishlistWishlistId(wishlistId);

    }

    public boolean deleteWishlistItem(Long wishlistId, Long wishlistItemId){

        Optional<WishlistItem> optionalItem = wishlistItemRepository.findById(wishlistItemId);

        if (optionalItem.isEmpty()){
            return false;
        }

        WishlistItem wishlistItem = optionalItem.get();

        if (!wishlistId.equals(wishlistItem.getWishlist().getWishlistId())){
            return false;
        }

        wishlistItemRepository.delete(wishlistItem);

        return true;

    }

}
