package com.example.java_backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.DTO.RoleDTO;
import com.example.java_backend.services.RoleServiceMyBatis;

/**
 * REST Controller cho Role sử dụng MyBatis XML Mapper
 * Endpoints: /api/mybatis/roles/*
 */
@RestController
@RequestMapping("/api/mybatis/roles")
@CrossOrigin(origins = "*")
public class RoleMyBatisController {

        @Autowired
        private RoleServiceMyBatis roleServiceMyBatis;

        /**
         * GET /api/mybatis/roles - Lấy tất cả roles (sử dụng MyBatis)
         */
        @GetMapping
        public ResponseEntity<Map<String, Object>> getAllRoles() {
                try {
                        List<RoleDTO> roles = roleServiceMyBatis.getAllRoles();
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", roles);
                        response.put("message", "Get all roles successfully (MyBatis XML)");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * GET /api/mybatis/roles/{id} - Lấy role theo ID (sử dụng MyBatis)
         */
        @GetMapping("/{id}")
        public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
                try {
                        RoleDTO role = roleServiceMyBatis.getRoleById(id);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", role);
                        response.put("message", "Get role successfully (MyBatis XML)");
                        return ResponseEntity.ok(response);
                } catch (RuntimeException e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * GET /api/mybatis/roles/name/{roleName} - Lấy role theo tên (sử dụng MyBatis)
         */
        @GetMapping("/name/{roleName}")
        public ResponseEntity<Map<String, Object>> getRoleByName(@PathVariable String roleName) {
                try {
                        RoleDTO role = roleServiceMyBatis.getRoleByName(roleName);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", role);
                        response.put("message", "Get role successfully (MyBatis XML)");
                        return ResponseEntity.ok(response);
                } catch (RuntimeException e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * POST /api/mybatis/roles - Tạo role mới (sử dụng MyBatis)
         */
        @PostMapping
        public ResponseEntity<Map<String, Object>> createRole(@RequestBody RoleDTO roleDTO) {
                try {
                        RoleDTO createdRole = roleServiceMyBatis.createRole(roleDTO);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", createdRole);
                        response.put("message", "Role created successfully (MyBatis XML)");
                        return ResponseEntity.status(HttpStatus.CREATED).body(response);
                } catch (RuntimeException e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * PUT /api/mybatis/roles/{id} - Cập nhật role (sử dụng MyBatis)
         */
        @PutMapping("/{id}")
        public ResponseEntity<Map<String, Object>> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
                try {
                        RoleDTO updatedRole = roleServiceMyBatis.updateRole(id, roleDTO);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", updatedRole);
                        response.put("message", "Role updated successfully (MyBatis XML)");
                        return ResponseEntity.ok(response);
                } catch (RuntimeException e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * DELETE /api/mybatis/roles/{id} - Xóa role (sử dụng MyBatis)
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
                try {
                        roleServiceMyBatis.deleteRole(id);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("message", "Role deleted successfully (MyBatis XML)");
                        return ResponseEntity.ok(response);
                } catch (RuntimeException e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }
}
