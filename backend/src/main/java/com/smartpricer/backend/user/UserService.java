package com.smartpricer.backend.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new IllegalArgumentException("Email already exist.");
        }
        String hashedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(hashedPassword);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUserById(Long userId){
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public Optional<User> updateUser(Long userId, User updatedUser) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (updatedUser.getUserEmail() != null) {
                if (!user.getUserEmail().equals(updatedUser.getUserEmail())) {
                    if (userRepository.existsByUserEmail(updatedUser.getUserEmail())) {
                        throw new IllegalArgumentException("Email already exist.");
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

}
