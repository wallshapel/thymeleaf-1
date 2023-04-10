package com.employees_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employees_management.models.Employee;
import com.employees_management.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepository.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Page<Employee> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}
	@Override
	@Transactional
	public void save(Employee empleado) {
		employeeRepository.save(empleado);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}
	@Override
	@Transactional(readOnly = true)
	public Employee findOne(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}
}