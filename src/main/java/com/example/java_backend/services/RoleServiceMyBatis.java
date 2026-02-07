package com.example.java_backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_backend.DTO.RoleDTO;
import com.example.java_backend.entities.Role;
import com.example.java_backend.mappers.RoleMapper;

/**
 * Service layer cho Role sử dụng MyBatis Mapper
 * Xử lý business logic với MyBatis XML mapping
 */
@Service
public class RoleServiceMyBatis {

        @Autowired
        private RoleMapper roleMapper;

        /**
         * CREATE - Tạo role mới
         */
        public RoleDTO createRole(RoleDTO roleDTO) {
                // Kiểm tra role đã tồn tại chưa
                int exists = roleMapper.existsByRole(roleDTO.getRole());
                if (exists > 0) {
                        throw new RuntimeException("Role already exists: " + roleDTO.getRole());
                }

                roleMapper.insertRole(roleDTO.getRole());
                Role role = roleMapper.getRoleByName(roleDTO.getRole());
                return toDTO(role);
        }

        /**
         * READ - Lấy tất cả roles
         */
        public List<RoleDTO> getAllRoles() {
                return roleMapper.getAllRoles()
                                .stream()
                                .map(this::toDTO)
                                .collect(Collectors.toList());
        }

        /**
         * READ - Lấy role theo ID
         */
        public RoleDTO getRoleById(Long id) {
                Role role = roleMapper.getRoleById(id);
                if (role == null) {
                        throw new RuntimeException("Role not found with id: " + id);
                }
                return toDTO(role);
        }

        /**
         * READ - Lấy role theo tên
         */
        public RoleDTO getRoleByName(String roleName) {
                Role role = roleMapper.getRoleByName(roleName);
                if (role == null) {
                        throw new RuntimeException("Role not found: " + roleName);
                }
                return toDTO(role);
        }

        /**
         * UPDATE - Cập nhật role
         */
        public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
                Role existingRole = roleMapper.getRoleById(id);
                if (existingRole == null) {
                        throw new RuntimeException("Role not found with id: " + id);
                }

                // Kiểm tra tên role mới có trùng với role khác không
                if (!existingRole.getRole().equals(roleDTO.getRole())) {
                        int exists = roleMapper.existsByRole(roleDTO.getRole());
                        if (exists > 0) {
                                throw new RuntimeException("Role name already exists: " + roleDTO.getRole());
                        }
                }

                roleMapper.updateRole(id, roleDTO.getRole());
                Role updatedRole = roleMapper.getRoleById(id);
                return toDTO(updatedRole);
        }

        /**
         * DELETE - Xóa role
         */
        public void deleteRole(Long id) {
                Role role = roleMapper.getRoleById(id);
                if (role == null) {
                        throw new RuntimeException("Role not found with id: " + id);
                }
                roleMapper.deleteRole(id);
        }

        /**
         * Helper: Entity -> DTO
         */
        private RoleDTO toDTO(Role role) {
                return new RoleDTO(role.getId(), role.getRole());
        }
}
