package com.example.java_backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.services.IListService;

@RestController
public class ListController {
	
	// Basic implementation - returns only the data
	@Autowired
	@Qualifier("basicListService")
	private IListService basicListService;
	
	// Timed implementation - returns data with execution time
	@Autowired
	@Qualifier("timedListService")
	private IListService timedListService;

	/**
	 * API 1: Basic List - Returns only the data without timing information
	 * Endpoint: GET /api/list/basic
	 */
	@GetMapping("/api/list/basic")
	public ResponseEntity<?> getBasicList() {
		try {
			List<Map<String, Object>> result = (List<Map<String, Object>>) basicListService.getList(123);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			System.err.println("Error querying the database: " + e.getMessage());
			return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
		}
	}
	
	/**
	 * API 2: Timed List - Returns data with execution time information
	 * Endpoint: GET /api/list/timed
	 */
	@GetMapping("/api/list/timed")
	public ResponseEntity<?> getTimedList() {
		try {
			Map<String, Object> result = (Map<String, Object>) timedListService.getList(123);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			System.err.println("Error querying the database: " + e.getMessage());
			return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
		}
	}
}
