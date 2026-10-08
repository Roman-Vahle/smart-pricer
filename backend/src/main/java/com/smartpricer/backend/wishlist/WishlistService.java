package com.smartpricer.backend.wishlist;

import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.user.User;
import com.smartpricer.backend.user.UserRepository;
import org.springframework.stereotype.Service;

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

    public Wishlist createWishlist(CreateWishlistRequest request) {

        Optional<User> user = userRepository.findById(request.getUserId());

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User id not found with id " + request.getUserId());
        }

        Wishlist newWishlist = new Wishlist(request.getWishlistName(), user.get());
        Wishlist savedWishlist = wishlistRepository.save(newWishlist);

        return savedWishlist;
    }

    public List<Wishlist> getWishlistsByUserId(Long userId){

        if (!userRepository.existsById(userId)){
            throw new  ResourceNotFoundException("User id not found with id " + userId);
        }

        return wishlistRepository.findAllByUserUserId(userId);
    }

    public Wishlist getWishlistById(Long wishlistId){

        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);

        if (optionalWishlist.isEmpty()){
            throw new  ResourceNotFoundException("Wishlist not found with id " + wishlistId);
        }

        return optionalWishlist.get();
    }

    public boolean deleteWishlist(Long wishlistId){

        if (wishlistRepository.existsById(wishlistId)){
            wishlistRepository.deleteById(wishlistId);
            return true;
        }
        return false;
    }

}
