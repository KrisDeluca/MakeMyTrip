package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenCapture {

	public WebDriver driver;

	public ScreenCapture(WebDriver driver) {
		this.driver=driver;
	}

	public String takeFullSnap(String fileNameWithLocation)
	{
		String date = new Date().toString().replaceAll(" ", "_").replaceAll(":","_");
		fileNameWithLocation = fileNameWithLocation + date + ".png";
		File img = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(img, new File(fileNameWithLocation));
		} catch (IOException e) {
			System.out.println("Failed to take Screenshot");
		}
		return fileNameWithLocation;
	}

	public String takePartialSnap(String fileNameWithLocation, int x, int y, int width,int height) throws Exception
	{
		String date = new Date().toString().replaceAll(" ", "_").replaceAll(":","_");
		fileNameWithLocation = fileNameWithLocation + date + ".png";
		File img = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullScreen = ImageIO.read(img);
		BufferedImage elementImage = fullScreen.getSubimage(x, y, width, height);
		ImageIO.write(elementImage, "png", img);
		
		try {
			FileHandler.copy(img, new File(fileNameWithLocation));
		} catch (IOException e) {
			System.out.println("Failed to take Screenshot");
		}
		return fileNameWithLocation;
	}
}
