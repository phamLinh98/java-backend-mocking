package com.example.java_backend.DAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.java_backend.entities.Role;
import com.example.java_backend.mappers.RoleMapper;

/**
 * Data Access Layer cho Role sử dụng MyBatis XML Mapper
 * Cung cấp các phương thức truy vấn database với SQL được định nghĩa trong XML
 */
@Repository
public class RoleDAL {

        @Autowired
        private RoleMapper roleMapper;

        /**
         * Lấy tất cả roles với custom mapping
         */
        public List<Map<String, Object>> getAllRoles() throws Exception {
                List<Role> roles = roleMapper.getAllRoles();
                return roles.stream()
                                .map(role -> {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("id", role.getId());
                                        map.put("role", role.getRole());
                                        return map;
                                })
                                .collect(Collectors.toList());
        }

        /**
         * Lấy role theo ID
         */
        public Map<String, Object> getRoleById(Long id) throws Exception {
                Role role = roleMapper.getRoleById(id);
                if (role == null) {
                        return null;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id", role.getId());
                map.put("role", role.getRole());
                return map;
        }

        /**
         * Tìm role theo tên
         */
        public Map<String, Object> getRoleByName(String roleName) throws Exception {
                Role role = roleMapper.getRoleByName(roleName);
                if (role == null) {
                        return null;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id", role.getId());
                map.put("role", role.getRole());
                return map;
        }

        /**
         * Tạo role mới
         */
        public int createRole(String roleName) throws Exception {
                return roleMapper.insertRole(roleName);
        }

        /**
         * Cập nhật role
         */
        public int updateRole(Long id, String roleName) throws Exception {
                return roleMapper.updateRole(id, roleName);
        }

        /**
         * Xóa role
         */
        public int deleteRole(Long id) throws Exception {
                return roleMapper.deleteRole(id);
        }
}
