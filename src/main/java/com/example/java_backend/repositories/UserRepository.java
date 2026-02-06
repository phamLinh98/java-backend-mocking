package com.example.java_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_backend.entities.User;

// Spring tự động tạo bean - KHÔNG BAO GIỜ dùng "new UserRepository()"
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository cung cấp sẵn: save, findById, findAll, deleteById, etc.

    // Custom query method for authentication
    Optional<User> findByUsername(String username);
}
