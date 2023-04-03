package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	Workbook wb;
	
	public ExcelReader() throws FileNotFoundException, IOException
	{
		wb=new XSSFWorkbook(new FileInputStream("resources/UserData.xlsx"));
	}
	
	public String readCell(String sheetName, int row, int col)
	{
		String data = wb.getSheet(sheetName).getRow(row).getCell(col).toString();
		return data;
	}
	
	public int rowNum(String sheetName)
	{
		return wb.getSheet(sheetName).getLastRowNum();
	}
	
}
