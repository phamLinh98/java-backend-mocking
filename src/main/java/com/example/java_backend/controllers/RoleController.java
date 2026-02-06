package com.example.java_backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.java_backend.services.RoleService;

/**
 * REST Controller cho Role
 * Cung cấp cả 2 cách: JPA Repository và DAL (XML-like mapping)
 */
@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

        private final RoleService roleService;

        // Constructor Injection
        public RoleController(RoleService roleService) {
                this.roleService = roleService;
        }

        // ==================== JPA Repository Methods ====================

        /**
         * GET /api/roles - Lấy tất cả roles (sử dụng JPA)
         */
        @GetMapping
        public ResponseEntity<Map<String, Object>> getAllRoles() {
                try {
                        List<RoleDTO> roles = roleService.getAllRoles();
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", roles);
                        response.put("message", "Get all roles successfully");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "Error: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * GET /api/roles/{id} - Lấy role theo ID (sử dụng JPA)
         */
        @GetMapping("/{id}")
        public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
                try {
                        RoleDTO role = roleService.getRoleById(id);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", role);
                        response.put("message", "Get role successfully");
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
         * GET /api/roles/name/{roleName} - Lấy role theo tên (sử dụng JPA)
         */
        @GetMapping("/name/{roleName}")
        public ResponseEntity<Map<String, Object>> getRoleByName(@PathVariable String roleName) {
                try {
                        RoleDTO role = roleService.getRoleByName(roleName);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", role);
                        response.put("message", "Get role successfully");
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
         * POST /api/roles - Tạo role mới (sử dụng JPA)
         */
        @PostMapping
        public ResponseEntity<Map<String, Object>> createRole(@RequestBody RoleDTO roleDTO) {
                try {
                        RoleDTO createdRole = roleService.createRole(roleDTO);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", createdRole);
                        response.put("message", "Role created successfully");
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
         * PUT /api/roles/{id} - Cập nhật role (sử dụng JPA)
         */
        @PutMapping("/{id}")
        public ResponseEntity<Map<String, Object>> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
                try {
                        RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("data", updatedRole);
                        response.put("message", "Role updated successfully");
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
         * DELETE /api/roles/{id} - Xóa role (sử dụng JPA)
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
                try {
                        roleService.deleteRole(id);
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("message", "Role deleted successfully");
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

        // ==================== DAL Methods (XML-like mapping) ====================

        /**
         * GET /api/roles/dal/all - Lấy tất cả roles (sử dụng DAL với XML-like mapping)
         */
        // @GetMapping("/dal/all")
        // public ResponseEntity<Map<String, Object>> getAllRolesDAL() {
        // try {
        // List<RoleDTO> roles = roleService.getAllRoles();
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", true);
        // response.put("data", roles);
        // response.put("message", "Get all roles successfully (DAL)");
        // return ResponseEntity.ok(response);
        // } catch (Exception e) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Error: " + e.getMessage());
        // return
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        // }
        // }

        // /**
        // * GET /api/roles/dal/{id} - Lấy role theo ID (sử dụng DAL)
        // */
        // @GetMapping("/dal/{id}")
        // public ResponseEntity<Map<String, Object>> getRoleByIdDAL(@PathVariable Long
        // id) {
        // try {
        // RoleDTO role = roleService.getRoleById(id);
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", true);
        // response.put("data", role);
        // response.put("message", "Get role successfully (DAL)");
        // return ResponseEntity.ok(response);
        // } catch (Exception e) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Error: " + e.getMessage());
        // return
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        // }
        // }

        // /**
        // * POST /api/roles/dal - Tạo role mới (sử dụng DAL)
        // */
        // @PostMapping("/dal")
        // public ResponseEntity<Map<String, Object>> createRoleDAL(@RequestBody
        // Map<String, String> body) {
        // try {
        // String roleName = body.get("role");
        // if (roleName == null || roleName.trim().isEmpty()) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Role name is required");
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        // }

        // RoleDTO roleDTO = new RoleDTO(roleName);
        // RoleDTO result = roleService.createRole(roleDTO);

        // Map<String, Object> response = new HashMap<>();
        // response.put("success", true);
        // response.put("message", "Role created successfully (DAL)");
        // response.put("data", result);
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // } catch (Exception e) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Error: " + e.getMessage());
        // return
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        // }
        // }

        // /**
        // * PUT /api/roles/dal/{id} - Cập nhật role (sử dụng DAL)
        // */
        // @PutMapping("/dal/{id}")
        // public ResponseEntity<Map<String, Object>> updateRoleDAL(@PathVariable Long
        // id,
        // @RequestBody Map<String, String> body) {
        // try {
        // String roleName = body.get("role");
        // if (roleName == null || roleName.trim().isEmpty()) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Role name is required");
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        // }

        // // ✅ Tạo DTO ở Controller (giống UserController)
        // RoleDTO roleDTO = new RoleDTO(roleName);
        // RoleDTO result = roleService.updateRole(id, roleDTO);

        // Map<String, Object> response = new HashMap<>();
        // response.put("success", true);
        // response.put("message", "Role updated successfully (DAL)");
        // response.put("data", result);
        // return ResponseEntity.ok(response);
        // } catch (Exception e) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Error: " + e.getMessage());
        // return
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        // }
        // }

        // /**
        // * DELETE /api/roles/dal/{id} - Xóa role (sử dụng DAL)
        // */
        // @DeleteMapping("/dal/{id}")
        // public ResponseEntity<Map<String, Object>> deleteRoleDAL(@PathVariable Long
        // id) {
        // try {
        // // ✅ Gọi service (không gán vào biến vì void)
        // roleService.deleteRole(id);

        // Map<String, Object> response = new HashMap<>();
        // response.put("success", true);
        // response.put("message", "Role deleted successfully (DAL)");
        // return ResponseEntity.ok(response);
        // } catch (Exception e) {
        // Map<String, Object> response = new HashMap<>();
        // response.put("success", false);
        // response.put("message", "Error: " + e.getMessage());
        // return
        // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        // }
        // }
}