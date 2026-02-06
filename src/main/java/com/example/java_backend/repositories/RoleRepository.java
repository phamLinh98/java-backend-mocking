package com.example.java_backend.repositories;

import com.example.java_backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring tự động tạo bean - KHÔNG BAO GIỜ dùng "new RoleRepository()"
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // JpaRepository cung cấp sẵn: save, findById, findAll, deleteById, etc.
    
    // Custom query method - tìm role theo tên
    Optional<Role> findByRole(String role);
    
    // Kiểm tra role có tồn tại không
    boolean existsByRole(String role);
}
