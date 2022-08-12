package com.NPHC.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.NPHC.model.Employee;

@Repository
public interface NPHCRepo extends JpaRepository<Employee, String> {

	@Query(value = "select e from Employee u where e.salary>=:minSalary and e.salary<=:maxSalary")
	List<Employee> getFirst30FilteredUsers(@Param("minSalary") double minSalary, @Param("maxSalary") double maxSalary);
	
}
