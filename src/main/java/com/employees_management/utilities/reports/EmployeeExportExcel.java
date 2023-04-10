package com.employees_management.utilities.reports;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.employees_management.models.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmployeeExportExcel {
	private XSSFWorkbook book;
	private XSSFSheet sheet;
	private List<Employee> employeeList;
	public EmployeeExportExcel(List<Employee> employeeList) {
		this.employeeList = employeeList;
		book = new XSSFWorkbook();
		sheet = book.createSheet("Employees");
	}
	private void writeHeadersTable() {
		Row row = sheet.createRow(0);
		CellStyle style = book.createCellStyle();
		XSSFFont font = book.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		Cell cell = row.createCell(0);
		cell.setCellValue("Id");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("Last name");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("Email");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("Date");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("Phone");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("Sex");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("Salary");
		cell.setCellStyle(style);
	}
	private void writeDataTable() {
		int noCells = 1;
		CellStyle style = book.createCellStyle();
		XSSFFont font = book.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		for(Employee employee : employeeList) {
			Row fila = sheet.createRow(noCells ++);
			Cell cell = fila.createCell(0);
			cell.setCellValue(employee.getId());
			sheet.autoSizeColumn(0);
			cell.setCellStyle(style);
			cell = fila.createCell(1);
			cell.setCellValue(employee.getName());
			sheet.autoSizeColumn(1);
			cell.setCellStyle(style);
			cell = fila.createCell(2);
			cell.setCellValue(employee.getLastName());
			sheet.autoSizeColumn(2);
			cell.setCellStyle(style);
			cell = fila.createCell(3);
			cell.setCellValue(employee.getEmail());
			sheet.autoSizeColumn(3);
			cell.setCellStyle(style);
			cell = fila.createCell(4);
			cell.setCellValue(employee.getDate().toString());
			sheet.autoSizeColumn(4);
			cell.setCellStyle(style);
			cell = fila.createCell(5);
			cell.setCellValue(employee.getPhone());
			sheet.autoSizeColumn(5);
			cell.setCellStyle(style);
			cell = fila.createCell(6);
			cell.setCellValue(employee.getSex());
			sheet.autoSizeColumn(6);
			cell.setCellStyle(style);
			cell = fila.createCell(7);
			cell.setCellValue(employee.getSalary());
			sheet.autoSizeColumn(7);
			cell.setCellStyle(style);
		}
	}
	public void export(HttpServletResponse response) throws IOException {
		writeHeadersTable();
		writeDataTable();
		ServletOutputStream outPutStream = response.getOutputStream();
		book.write(outPutStream);
		book.close();
		outPutStream.close();
	}
}