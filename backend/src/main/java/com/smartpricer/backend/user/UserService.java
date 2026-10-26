package com.smartpricer.backend.user;

import com.smartpricer.backend.priceAlert.PriceAlert;
import com.smartpricer.backend.priceAlert.PriceAlertRepository;
import com.smartpricer.backend.wishlist.Wishlist;
import com.smartpricer.backend.wishlist.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PriceAlertRepository priceAlertRepository;
    private final WishlistRepository wishlistRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PriceAlertRepository priceAlertRepository, WishlistRepository wishlistRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.priceAlertRepository = priceAlertRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public User createUser(CreateUserRequest request) {

        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("Email already exist.");
        }

        String hashedPassword = passwordEncoder.encode(request.getUserPassword());

        User user = new User(request.getUserName(), request.getUserEmail(), hashedPassword);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Long authenticatedUserId) {

        Optional<User> user = userRepository.findById(authenticatedUserId);
        if (user.isEmpty()) {
            return false;
        }

        List<PriceAlert> priceAlerts = priceAlertRepository.findAllByUserUserId(authenticatedUserId);

        priceAlertRepository.deleteAll(priceAlerts);

        List<Wishlist>  wishlists = wishlistRepository.findAllByUserUserId(authenticatedUserId);

        wishlistRepository.deleteAll(wishlists);

        userRepository.delete(user.get());

        return true;
    }

    public Optional<User> updateUser(Long userId, User updatedUser) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (updatedUser.getUserEmail() != null) {

                if (!user.getUserEmail().equals(updatedUser.getUserEmail())) {

                    if (userRepository.existsByUserEmailAndUserIdNot(updatedUser.getUserEmail(),userId)) {
                        throw new IllegalArgumentException("Email already exists.");
                    }

                    user.setUserEmail(updatedUser.getUserEmail());
                }

            }

            if (updatedUser.getUserName() != null) {
                user.setUserName(updatedUser.getUserName());
            }

            if (updatedUser.getUserPassword() != null) {
                user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
            }

            return Optional.of(userRepository.save(user));
        }

        return Optional.empty();
    }

    public Optional<User> loginUser(LoginRequest request) {

        Optional <User> optionalUser = userRepository.findByUserEmail(request.getUserEmail());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
                return Optional.of(user);
            }

        }

        return Optional.empty();
    }

}
