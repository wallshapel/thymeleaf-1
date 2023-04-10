package com.employees_management.utilities.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.employees_management.models.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class EmployeeExportPDF {
	private List<Employee> employeeList;
	public EmployeeExportPDF(List<Employee> employeeList) {
		super();
		this.employeeList = employeeList;
	}
	private void writeHeadersTable(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("Id", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Last name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Date", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Phone", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Sex", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Salary", font));
		table.addCell(cell);
	}
	private void writeDataTable(PdfPTable table) {
		for (Employee employee : employeeList) {
			table.addCell(String.valueOf(employee.getId()));
			table.addCell(employee.getName());
			table.addCell(employee.getLastName());
			table.addCell(employee.getEmail());
			table.addCell(employee.getDate().toString());
			table.addCell(String.valueOf(employee.getPhone()));
			table.addCell(employee.getSex());
			table.addCell(String.valueOf(employee.getSalary()));
		}
	}
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.BLUE);
		font.setSize(18);
		Paragraph title = new Paragraph("Employee list", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] { 1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f, 2.2f });
		table.setWidthPercentage(110);
		writeHeadersTable(table);
		writeDataTable(table);
		document.add(table);
		document.close();
	}
}