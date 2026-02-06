package com.example.java_backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.java_backend.DTO.RoleDTO;
import com.example.java_backend.entities.Role;
import com.example.java_backend.repositories.RoleRepository;

/**
 * Service layer cho Role
 * Xử lý business logic và chuyển đổi giữa Entity và DTO
 */
@Service
public class RoleService {

        private final RoleRepository roleRepository;

        // Constructor Injection - cách DI được khuyên dùng
        public RoleService(RoleRepository roleRepository) {
                this.roleRepository = roleRepository;
        }

        /**
         * CREATE - Tạo role mới
         */
        public RoleDTO createRole(RoleDTO roleDTO) {
                // Kiểm tra role đã tồn tại chưa
                if (roleRepository.existsByRole(roleDTO.getRole())) {
                        throw new RuntimeException("Role already exists: " + roleDTO.getRole());
                }

                Role role = new Role(roleDTO.getRole());
                Role savedRole = roleRepository.save(role);
                return toDTO(savedRole);
        }

        /**
         * READ - Lấy tất cả roles
         */
        public List<RoleDTO> getAllRoles() {
                return roleRepository.findAll()
                                .stream()
                                .map(this::toDTO)
                                .collect(Collectors.toList());
        }

        /**
         * READ - Lấy role theo ID
         */
        public RoleDTO getRoleById(Long id) {
                Role role = roleRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
                return toDTO(role);
        }

        /**
         * READ - Lấy role theo tên
         */
        public RoleDTO getRoleByName(String roleName) {
                Role role = roleRepository.findByRole(roleName)
                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                return toDTO(role);
        }

        /**
         * UPDATE - Cập nhật role
         */
        public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
                Role role = roleRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

                // Kiểm tra tên role mới có trùng với role khác không
                if (!role.getRole().equals(roleDTO.getRole()) &&
                                roleRepository.existsByRole(roleDTO.getRole())) {
                        throw new RuntimeException("Role name already exists: " + roleDTO.getRole());
                }

                role.setRole(roleDTO.getRole());
                Role updatedRole = roleRepository.save(role);
                return toDTO(updatedRole);
        }

        /**
         * DELETE - Xóa role
         */
        public void deleteRole(Long id) {
                if (!roleRepository.existsById(id)) {
                        throw new RuntimeException("Role not found with id: " + id);
                }
                roleRepository.deleteById(id);
        }

        /**
         * Helper: Entity -> DTO
         */
        private RoleDTO toDTO(Role role) {
                return new RoleDTO(role.getId(), role.getRole());
        }
}
