package com.example.java_backend.repositories;

import com.example.java_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring tự động tạo bean - KHÔNG BAO GIỜ dùng "new UserRepository()"
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository cung cấp sẵn: save, findById, findAll, deleteById, etc.
}
