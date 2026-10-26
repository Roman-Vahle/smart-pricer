package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.user.User;
import com.smartpricer.backend.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    public Wishlist createWishlist(CreateWishlistRequest request, Long authenticatedId) {

        Optional<User> user = userRepository.findById(authenticatedId);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User id not found with id " + authenticatedId);
        }

        Wishlist newWishlist = new Wishlist(request.getWishlistName(), user.get());
        Wishlist savedWishlist = wishlistRepository.save(newWishlist);

        return savedWishlist;
    }

    @Transactional(readOnly = true)
    public Wishlist getWishlistById(Long wishlistId, Long authenticatedId) throws AccessDeniedException {

        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);

        if (optionalWishlist.isEmpty()){
            throw new  ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        Wishlist wishlist = optionalWishlist.get();

        if (!wishlist.getUser().getUserId().equals(authenticatedId)){
            throw new  AccessDeniedException("Access denied");
        }

        return wishlist;
    }

    public List<Wishlist> getWishlistsByUserId(Long userId){

        if (!userRepository.existsById(userId)){
            throw new  ResourceNotFoundException("User id not found with id " + userId);
        }

        return wishlistRepository.findAllByUserUserId(userId);
    }

    @Transactional
    public boolean deleteWishlist(Long id, Long wishlistId) throws AccessDeniedException {

        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);

        if (optionalWishlist.isEmpty()){
            return false;
        }

        Wishlist wishlist = optionalWishlist.get();

        if (!wishlist.getUser().getUserId().equals(id)){
            throw new  AccessDeniedException("Access denied");
        }

        wishlistRepository.delete(wishlist);

        return true;
    }

}
