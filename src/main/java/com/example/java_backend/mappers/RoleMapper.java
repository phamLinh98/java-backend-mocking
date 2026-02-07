package com.example.java_backend.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.java_backend.entities.Role;

/**
 * MyBatis Mapper interface cho Role
 * Các SQL queries được định nghĩa trong file XML tương ứng
 */
@Mapper
public interface RoleMapper {

        /**
         * Lấy tất cả roles
         */
        List<Role> getAllRoles();

        /**
         * Lấy role theo ID
         */
        Role getRoleById(@Param("id") Long id);

        /**
         * Lấy role theo tên
         */
        Role getRoleByName(@Param("roleName") String roleName);

        /**
         * Tạo role mới
         */
        int insertRole(@Param("roleName") String roleName);

        /**
         * Cập nhật role
         */
        int updateRole(@Param("id") Long id, @Param("roleName") String roleName);

        /**
         * Xóa role
         */
        int deleteRole(@Param("id") Long id);

        /**
         * Kiểm tra role có tồn tại không
         */
        int existsByRole(@Param("roleName") String roleName);
}
