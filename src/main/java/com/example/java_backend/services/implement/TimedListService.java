package com.example.java_backend.services.implement;

import com.example.java_backend.DAL.ListDAL;
import com.example.java_backend.services.IListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("timedListService")
public class TimedListService implements IListService {
	@Autowired
	private ListDAL listDAL;

	@Override
	public Map<String, Object> getList(int id) throws Exception {
		long startTime = System.currentTimeMillis();
		
		// Execute the query
		List<Map<String, Object>> data = listDAL.getList();
		
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		// Return object with data and execution time
		Map<String, Object> response = new HashMap<>();
		response.put("data", data);
		response.put("executionTimeMs", executionTime);
		response.put("timestamp", System.currentTimeMillis());
		
		return response;
	}
}
