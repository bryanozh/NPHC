package com.NPHC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NPHC.Service.NPHCService;
import com.NPHC.message.ResponseMessage;
import com.NPHC.helper.CSVHelper;
import com.NPHC.model.Employee;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/csv")
public class NPHCController {

	@Autowired
	NPHCService nphcService;
	
	//User Story 1: Upload CSV file to db
	@PostMapping("/users//upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {	
		String message = "";
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				nphcService.save(file);
				message = "Uploaded the file successfully " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		message= "Upload a CSV file.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	
	//User story 2: Get all emps for display
	@GetMapping("all")
	public ResponseEntity<List<Employee>> getAllEmp() {
		try {
			List<Employee> empList = nphcService.getAllEmployees();
			if (empList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(empList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<List<Employee>> getFilteredEmployees(
																		@RequestParam("minSalary") Double minSalary,
																		@RequestParam("maxSalary") Double maxSalary,
																		@RequestParam("offset") Integer offset,
																		@RequestParam("limit") Integer limit
																		) {
		try {
			List<Employee> emps = nphcService.getFilteredEmployees (minSalary, maxSalary);
			if (emps.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} 
				return new ResponseEntity<>(emps, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
}
