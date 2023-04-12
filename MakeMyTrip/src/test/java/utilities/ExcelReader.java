package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	XSSFWorkbook readWB, writeWB;
	Sheet sh;
	int lastRow;

	public ExcelReader() throws FileNotFoundException, IOException
	{
		readWB=new XSSFWorkbook(new FileInputStream("resources/UserData.xlsx"));
	}

	public ExcelReader(String sheetName) throws FileNotFoundException, IOException
	{
		writeWB=new XSSFWorkbook(new FileInputStream("resources/FlightData.xlsx"));
		sh = writeWB.getSheet(sheetName);
		lastRow = sh.getLastRowNum()+1;
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

	public void writeNewCell(int row, int col, String data) throws Exception
	{
		sh.createRow(lastRow + row).createCell(col).setCellValue(data);
		FileOutputStream fileout = new FileOutputStream("resources/FlightData.xlsx");
		writeWB.write(fileout);
		fileout.close();
	}
	
	public void writeOldCell(int row, int col, String data) throws Exception
	{
		sh.getRow(lastRow + row).createCell(col).setCellValue(data);
		FileOutputStream fileout = new FileOutputStream("resources/FlightData.xlsx");
		writeWB.write(fileout);
		fileout.close();
	}

}
