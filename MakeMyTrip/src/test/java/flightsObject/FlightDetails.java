package flightsObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelReader;

public class FlightDetails {

	public WebDriver driver;
	public WebDriverWait wait;
	ExcelReader ex = new ExcelReader();

	public FlightDetails(WebDriver driver) throws FileNotFoundException, IOException
	{
		Properties read=new Properties();
		read.load(new FileInputStream("resources/settings.property"));
		this.driver = driver;
		wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(read.getProperty("explicit.wait"))));
	}

	By popupCross = By.xpath("//span[@class='bgProperties icon20 overlayCrossIcon']");
	By refresh = By.xpath("//button[contains(text(),'Refresh')]");

	By oneFlight = By.xpath("//p[@class='boldFont blackText airlineName']");
	By roundFlight = By.xpath("//span[@class='boldFont blackText']");
	By multiFlight = By.xpath("//span[@class='boldFont blackText airlineName']");

	By time = By.xpath("//p[@class='appendBottom2 flightTimeInfo']");

	By money = By.xpath("//p[@class='blackText fontSize18 blackFont white-space-no-wrap']");
	By roundMoney = By.xpath("//p[@class='blackText fontSize16 blackFont']");

	public String pageTitle() throws InterruptedException
	{
		try 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(popupCross));
		}
		catch(Exception e)
		{
			try
			{				
				driver.findElement(refresh).click();										//Sometimes page shows refresh button due to internal error
				System.out.println("Need to refresh the page");
				wait.until(ExpectedConditions.visibilityOfElementLocated(popupCross));		//Offers might not appear for certain set of testdata
				driver.findElement(popupCross).click();
			}
			catch(Exception ex)
			{
				System.out.println("Offer popup didn't appear");
			}			
		}
		Thread.sleep(2000);;
		return driver.getTitle();
	}

	public void getTime(String sheetName)
	{
		List <WebElement> departTime = new ArrayList<WebElement>();
		departTime = driver.findElements(time);
		for (int i=0,j = 1;i<departTime.size();i+=2,j++)
		{
			ex.writeCell(sheetName, j, 2, departTime.get(i).getText());
		}

		List <WebElement> returnTime = new ArrayList<WebElement>();
		returnTime = driver.findElements(time);
		for (int i=1,j = 1;i<returnTime.size();i+=2,j++)
		{
			ex.writeCell(sheetName, j, 3, returnTime.get(i).getText());
		}
	}

	public void getFlight(String trip, String sheetName)
	{
		List <WebElement> flights = new ArrayList<WebElement>();

		if (trip=="OneWay")
		{
			flights = driver.findElements(oneFlight);
		}
		else if (trip=="RoundWay")
		{
			flights = driver.findElements(roundFlight);
		}
		else
		{
			flights = driver.findElements(multiFlight);
		}

		for (int i=0;i<flights.size();i++)
		{
			ex.writeCell(sheetName, i, 1, flights.get(i).getText());
		}
	}

	public void getPrice(String trip, String sheetName)
	{
		List <WebElement> price = new ArrayList<WebElement>();

		if (trip=="RoundWay")
		{
			price = driver.findElements(roundMoney);
		}
		else
		{
			price = driver.findElements(money);
		}

		for (int i=0;i<price.size();i++)
		{
			ex.writeCell(sheetName, i, 1, price.get(i).getText());
		}
	}


}
