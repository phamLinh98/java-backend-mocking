package com.example.java_backend.services.implement;

import com.example.java_backend.DAL.ListDAL;
import com.example.java_backend.services.IListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("basicListService")
public class BasicListService implements IListService {
	@Autowired
	private ListDAL listDAL;

	@Override
	public List<Map<String, Object>> getList(int id) throws Exception {
		// Simply return the list without timing information
		return listDAL.getList();
	}
}
