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
