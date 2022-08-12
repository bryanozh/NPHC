package com.NPHC.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.NPHC.exception.IncorrectNumOfColException;
import com.NPHC.exception.SalaryLessThanZeroException;
import com.NPHC.model.Employee;

public class CSVHelper {
	
	public static String type = "text/csv";
	static String[] headers = {"id", "login", "name", "salary"};
	
	public static boolean hasCSVFormat(MultipartFile file) {
		if (!type.equals(file.getContentType())) {
			return false;
		} 
		return true;
	}
	
	public static List<Employee> csvToEmployeeList(InputStream is) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			CSVParser CSVParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			List<Employee> empList = new ArrayList<Employee>();
			Iterable<CSVRecord> csvRecords = CSVParser.getRecords();
			LocalDate empStartDate;
			for (CSVRecord csvRecord : csvRecords) {
				String userId = csvRecord.get("id");
				Double userSalary = Double.parseDouble(csvRecord.get("salary"));
				String startDate = csvRecord.get("startDate");
				
				if (startDate.length() == "yyyy-mm-dd".length()) {
					empStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} else {
					empStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MMM-yy"));
				}
				
				if (userId.startsWith("#")) {
					continue;
				} else if (csvRecord.size() != headers.length) {
					throw new IncorrectNumOfColException ("Wrong number of columns");
				} else if (userSalary < 0) {
					throw new SalaryLessThanZeroException("Row(s) with salary less than 0 detected");
				} else {				
					Employee emp = new Employee(
							csvRecord.get("id"),
							csvRecord.get("login"),
							csvRecord.get("name"),
							Double.parseDouble(csvRecord.get("salary")),
							Date.valueOf(empStartDate)
							);
							empList.add(emp);
				}
			}
			return empList;
		} catch (IncorrectNumOfColException e1) {
			throw new RuntimeException(e1.getMessage());
		} catch (SalaryLessThanZeroException e2) {
			throw new RuntimeException(e2.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV" + e.getMessage());
		}
	}
	
	
	
}
