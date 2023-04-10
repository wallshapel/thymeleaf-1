package com.employees_management.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.employees_management.models.Employee;
import com.employees_management.services.EmployeeService;
import com.employees_management.utilities.pagination.PageRender;
import com.employees_management.utilities.reports.EmployeeExportExcel;
import com.employees_management.utilities.reports.EmployeeExportPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping({"/","/toList",""})
	public String findAll(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Employee> employees = employeeService.findAll(pageRequest);
		PageRender<Employee> pageRender = new PageRender<>("toList", employees);
		model.addAttribute("title","Employee list");
		model.addAttribute("employees",employees);
		model.addAttribute("page", pageRender);
		return "toList";
	}
	@GetMapping("/details/{id}")
	public String details(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Employee employee = employeeService.findOne(id);
		if(employee == null) {
			flash.addFlashAttribute("error", "The employee does not exists");
			return "redirect:/toList";
		}
		model.put("employee", employee);
		model.put("title", "Details of the employee " + employee.getName());
		return "details";
	}
	@GetMapping("/form")
	public String form(Map<String, Object> model) {
		Employee employee = new Employee();
		model.put("employee", employee);
		model.put("title", "Employee Registering");
		return "form";
	}
	@PostMapping("/form")
	public String save(@Valid Employee employee, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Employee register");
			return "form";
		}
		String message = (employee.getId() != null) ? "Employee edited successfully" : "Employee registered successfully";
		employeeService.save(employee);
		status.setComplete();
		flash.addFlashAttribute("success", message);
		return "redirect:/toList";
	}
	@GetMapping("/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Employee employee = null;
		if(id > 0) {
			employee = employeeService.findOne(id);
			if(employee == null) {
				flash.addFlashAttribute("error", "Employee Id does not exists");
				return "redirect:/toList";
			}
		} else {
			flash.addFlashAttribute("error", "Employee Id cannot be 0");
			return "redirect:/toList";
		}
		model.put("employee", employee);
		model.put("title", "Employee editing");
		return "form";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			employeeService.delete(id);
			flash.addFlashAttribute("success", "Employee deleted successfully");
		}
		return "redirect:/toList";
	}
	
	@GetMapping("/pdf")
	public void pdf(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormatter.format(new Date());
		String header = "Content-Disposition";
		String value = "attachment; filename=Empleados_" + currentDate + ".pdf";
		response.setHeader(header, value);
		List<Employee> employees = employeeService.findAll();
		EmployeeExportPDF exporter = new EmployeeExportPDF(employees);
		exporter.export(response);
	}
	@GetMapping("/excel")
	public void excel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormatter.format(new Date());
		String header = "Content-Disposition";
		String value = "attachment; filename=Empleados_" + currentDate + ".xlsx";
		response.setHeader(header, value);
		List<Employee> employees = employeeService.findAll();
		EmployeeExportExcel exporter = new EmployeeExportExcel(employees);
		exporter.export(response);
	}
}
