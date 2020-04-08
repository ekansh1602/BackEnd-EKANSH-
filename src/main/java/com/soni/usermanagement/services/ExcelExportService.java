package com.soni.usermanagement.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.soni.usermanagement.model.ContactManagement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportService {
	
	public static String contactListToExcelFile(List<ContactManagement> contacts) {
		try(Workbook workbook = new XSSFWorkbook()){
			Sheet sheet = workbook.createSheet("Contacts");
			
			Row row = sheet.createRow(0);
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
	        // Creating header
	        Cell cell = row.createCell(0);
	        cell.setCellValue("ID");
	        cell.setCellStyle(headerCellStyle);
	        
	        /*
	        cell = row.createCell(1);
	        cell.setCellValue("App Code");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(2);
	        cell.setCellValue("File Code");
	        cell.setCellStyle(headerCellStyle);
	
	*/
	        cell = row.createCell(1);
	        cell.setCellValue("Domain");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(2);
	        cell.setCellValue("File Type Code");
            cell.setCellStyle(headerCellStyle);
            
            cell = row.createCell(3);
	        cell.setCellValue("Contacts");
	        cell.setCellStyle(headerCellStyle);
	        
	        // Creating data rows for each customer
	        for(int i = 0; i < contacts.size(); i++) {
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(contacts.get(i).getId());
	        	//dataRow.createCell(1).setCellValue(contacts.get(i).getApplicationCode());
	        	//dataRow.createCell(2).setCellValue(contacts.get(i).getFileCode());
	        	dataRow.createCell(1).setCellValue(contacts.get(i).getDomain());
                dataRow.createCell(2).setCellValue(contacts.get(i).getFileTypeCode());
                dataRow.createCell(3).setCellValue(contacts.get(i).getContacts());
	        }
	
	        // Making size of column auto resize to fit with data
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            //sheet.autoSizeColumn(4);
	        
	        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        //workbook.write(outputStream);
            //return new ByteArrayInputStream(outputStream.toByteArray());
            
		//} catch (IOException ex) {
			//ex.printStackTrace();
			//return null;
            
			String currDir = Paths.get("").toAbsolutePath().toString();
			String filePath = currDir + "\\contacts.xlsx";
			
			try(FileOutputStream fout = new FileOutputStream(
				new File(filePath))) {
				workbook.write(fout);
			}

			return filePath;
            // return new ByteArrayInputStream(outputStream.toByteArray());
            
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
            
            
	}
}