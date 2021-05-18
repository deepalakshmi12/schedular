package com.bezkoder.spring.files.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bezkoder.spring.files.excel.model.Tutorial;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Title", "Description" };
  static String SHEET = "Tutorials";

  public static ByteArrayInputStream tutorialsToExcel(List<Tutorial> jsonexcel) {

    try (Workbook workbook = new XSSFWorkbook(); 
    		ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Tutorial tutorial : jsonexcel) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(tutorial.getId());
        row.createCell(1).setCellValue(tutorial.getTitle());
        row.createCell(2).setCellValue(tutorial.getDescription());
      
      }
     
      Date date=new Date();
      DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
      String currentDateTime = dateformatter.format(date);
      Row row = sheet.createRow(jsonexcel.size()+1);
     
      Cell cell = row.createCell(0);
     cell.setCellValue(currentDateTime);
     row.createCell(1).setCellValue("The number Product is " +jsonexcel.size());
    
      

      workbook.write(out);
     
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

}
