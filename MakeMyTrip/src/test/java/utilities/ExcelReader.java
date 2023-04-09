package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	Workbook readWB, writeWB;
	
	public ExcelReader() throws FileNotFoundException, IOException
	{
		readWB=new XSSFWorkbook(new FileInputStream("resources/UserData.xlsx"));
		writeWB=new XSSFWorkbook(new FileInputStream("resources/FlightData.xlsx"));
	}
	
	public String readCell(String sheetName, int row, int col)
	{
		String data = readWB.getSheet(sheetName).getRow(row).getCell(col).toString();
		return data;
	}
	
	public int rowNum(String sheetName)
	{
		return readWB.getSheet(sheetName).getLastRowNum();
	}
	
	public void writeCell(String sheetName, int row, int col, String data)
	{
		writeWB.createSheet(sheetName);
		writeWB.getSheet(sheetName).getRow(row).createCell(col).setCellValue(data);
	}
	
}
