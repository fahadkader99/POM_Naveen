package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	
	public static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpencartTestData.xlsx";
	public static Workbook workBook;
	public static Sheet sheet;	
	
	public static Object[][] getTestData(String sheetName) {
		System.out.println("\nReading the data from sheet: " + sheetName);
		
		Object data[][] = null;
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			workBook = WorkbookFactory.create(ip);
			sheet = workBook.getSheet(sheetName);
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];							// this will dynamically pick up the Row & Column number from each sheet
			
			
			/*
			 * 1st loop for row
			 * 2nd loop for column
			 * with large int/float number in excel use single quote (') to make the int/float number to read them as text or string.
			 */
			
			for(int i = 0; i < sheet.getLastRowNum(); i++) {
				for(int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();										// in java row starts from 0, but in excel row starts with 1 (i+1)
				}
			}
					
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
		} 
		
		return data;
		
	}

}
