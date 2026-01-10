package com.example.java_backend.controllers;

import com.example.java_backend.services.ListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@RestController
public class ListController {
	@Autowired
	private ListService listService;

	@GetMapping("/list")
	public ResponseEntity<List<Map<String, Object>>> getList() {
		try {
			List<Map<String, Object>> result = listService.getList();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			System.err.println("Error querying the database: " + e.getMessage());
			return ResponseEntity.status(500).body(null);
		}
	}
}
