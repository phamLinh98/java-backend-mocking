package com.example.java_backend.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Data Access Layer cho Role sử dụng JdbcTemplate
 * Cung cấp các phương thức truy vấn database với SQL thuần
 */
@Repository
public class RoleDAL {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        /**
         * Lấy tất cả roles với custom mapping
         */
        public List<Map<String, Object>> getAllRoles() throws Exception {
                String sql = "SELECT id, role FROM role ORDER BY id";

                List<Map<String, Object>> rows = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, Object> row = new HashMap<>();
                                row.put("id", rs.getLong("id"));
                                row.put("role", rs.getString("role"));
                                return row;
                        }
                });
                return rows;
        }

        /**
         * Lấy role theo ID
         */
        public Map<String, Object> getRoleById(Long id) throws Exception {
                String sql = "SELECT id, role FROM role WHERE id = ?";

                return jdbcTemplate.queryForObject(sql, new RowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, Object> row = new HashMap<>();
                                row.put("id", rs.getLong("id"));
                                row.put("role", rs.getString("role"));
                                return row;
                        }
                }, id);
        }

        /**
         * Tìm role theo tên
         */
        public Map<String, Object> getRoleByName(String roleName) throws Exception {
                String sql = "SELECT id, role FROM role WHERE role = ?";

                List<Map<String, Object>> rows = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, Object> row = new HashMap<>();
                                row.put("id", rs.getLong("id"));
                                row.put("role", rs.getString("role"));
                                return row;
                        }
                }, roleName);

                return rows.isEmpty() ? null : rows.get(0);
        }

        /**
         * Tạo role mới
         */
        public int createRole(String roleName) throws Exception {
                String sql = "INSERT INTO role (role) VALUES (?)";
                return jdbcTemplate.update(sql, roleName);
        }

        /**
         * Cập nhật role
         */
        public int updateRole(Long id, String roleName) throws Exception {
                String sql = "UPDATE role SET role = ? WHERE id = ?";
                return jdbcTemplate.update(sql, roleName, id);
        }

        /**
         * Xóa role
         */
        public int deleteRole(Long id) throws Exception {
                String sql = "DELETE FROM role WHERE id = ?";
                return jdbcTemplate.update(sql, id);
        }
}
