package com.smartpricer.backend.user;

import com.smartpricer.backend.security.JwtService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {

        List<User> users = userService.getAllUsers();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }

        return userDTOs;

    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {

        try {
            User createdUser = userService.createUser(request);
            UserDTO userDTO = new UserDTO(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){

        Optional<User> user = userService.getUserById(userId);

        if(user.isPresent()){
            return ResponseEntity.ok(new UserDTO(user.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUserById(@AuthenticationPrincipal Jwt jwt){

        Long authenticatedUserId = Long.valueOf(jwt.getSubject());

        boolean deleted = userService.deleteById(authenticatedUserId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {

        try {

            Optional<User> user = userService.updateUser(userId, updatedUser);

            if (user.isPresent()) {
                return ResponseEntity.ok(new UserDTO(user.get()));
            }

            return ResponseEntity.notFound().build();

        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

            Optional<User> user = userService.loginUser(loginRequest);

            if (user.isPresent()) {

                String token = jwtService.generateToken(user.get());

                return ResponseEntity.ok(new LoginResponse(token, new UserDTO(user.get())));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}
