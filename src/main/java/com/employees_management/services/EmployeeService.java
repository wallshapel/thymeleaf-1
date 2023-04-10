package com.employees_management.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.employees_management.models.Employee;

public interface EmployeeService {
	public List<Employee> findAll();
	public Page<Employee> findAll(Pageable pageable);
	public void save(Employee empleado);
	public Employee findOne(Long id);
	public void delete(Long id);
}