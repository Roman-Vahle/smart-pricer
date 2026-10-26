package com.smartpricer.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUserEmail(String userEmail);
    boolean existsByUserEmailAndUserIdNot(String userEmail, Long userId);
    Optional <User> findByUserEmail(String userEmail);

}
