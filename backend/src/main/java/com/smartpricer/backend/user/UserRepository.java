package com.smartpricer.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserEmail(String userEmail);
}
