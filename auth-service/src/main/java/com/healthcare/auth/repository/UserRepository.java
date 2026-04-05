package com.healthcare.auth.repository;

import com.healthcare.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
