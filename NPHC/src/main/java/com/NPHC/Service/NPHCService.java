package com.NPHC.Service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.NPHC.helper.CSVHelper;
import com.NPHC.model.Employee;
import com.NPHC.repo.NPHCRepo;

@Service
public class NPHCService {
	
	@Autowired
	NPHCRepo repo;
	
	public void save(MultipartFile file) {
		try {
			List<Employee> empList = CSVHelper.csvToEmployeeList(file.getInputStream());
			repo.saveAll(empList);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
	@Transactional
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}
}
