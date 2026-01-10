package com.example.java_backend.services;

import com.example.java_backend.DAL.ListDAL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ListService {
	@Autowired
	private ListDAL listDAL;

	public List<Map<String, Object>> getList() throws Exception {
		return listDAL.getList();
	}
}