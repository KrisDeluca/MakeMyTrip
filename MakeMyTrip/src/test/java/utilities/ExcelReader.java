package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	XSSFWorkbook readWB, writeWB;

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

	public void writeCell(String sheetName, int col, String data)
	{
		Sheet sh = writeWB.getSheet(sheetName);
		sh.createRow(sh.getLastRowNum()+1).createCell(col).setCellValue(data);
		try {
			writeWB.write(new FileOutputStream("resources/FlightData.xlsx"));
		} catch (Exception e) {}
	}

}
