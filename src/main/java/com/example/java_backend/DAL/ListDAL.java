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

@Repository
public class ListDAL {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getList() throws Exception {
		String sql =
			"SELECT\n" +
			"    l.id,\n" +
			"    l.user_id,\n" +
			"    l.content,\n" +
			"    l.comment,\n" +
			"    l.like,\n" +
			"    l.shared,\n" +
			"    l.likestatus,\n" +
			"    l.created_at,\n" +
			"    u.name AS user_name,\n" +
			"    u.avatar AS avatar\n" +
			"FROM\n" +
			"    list l\n" +
			"JOIN\n" +
			"    \"public\".\"user\" u ON l.user_id = u.id;";

		List<Map<String, Object>> rows = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, Object> row = new HashMap<>();
				row.put("id", rs.getObject("id"));
				row.put("user_id", rs.getObject("user_id"));
				Object content = rs.getObject("content");
				// Replace null content with empty map
				if (content == null) {
					row.put("content", new HashMap<>());
				} else {
					row.put("content", content);
				}
				row.put("comment", rs.getObject("comment"));
				row.put("like", rs.getObject("like"));
				row.put("shared", rs.getObject("shared"));
				row.put("likestatus", rs.getObject("likestatus"));
				row.put("created_at", rs.getObject("created_at"));
				row.put("user_name", rs.getObject("user_name"));
				row.put("avatar", rs.getObject("avatar"));
				return row;
			}
		});
		return rows;
	}
}