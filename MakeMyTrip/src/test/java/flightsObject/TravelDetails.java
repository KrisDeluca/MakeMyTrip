package flightsObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ScreenCapture;

public class TravelDetails {

	public WebDriver driver;
	public WebDriverWait wait;

	public TravelDetails(WebDriver driver) throws FileNotFoundException, IOException
	{
		Properties read=new Properties();
		read.load(new FileInputStream("resources/settings.property"));
		this.driver = driver;
		wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(read.getProperty("explicit.wait"))));
	}

	By popup = By.xpath("//span[@class='appSprite icAppDownload']");
	
	By multiSelect = By.xpath("//li[@data-cy='mulitiCityTrip']");
	By scroll = By.xpath("(//span[text()='From'])[2]");
		
	By from = By.xpath("//input[@id='fromCity']");	
	By firstFrom = By.xpath("//input[@id='fromAnotherCity0']");		//Found in Multi-way
	By secondFrom = By.xpath("//input[@id='fromAnotherCity1']");	//Found in Multi-way
	By fromTextbox = By.xpath("//input[@placeholder='From']");

	By to = By.xpath("//input[@id='toCity']");
	By firstTo = By.xpath("//input[@id='toAnotherCity0']");			//Found in Multi-way
	By secondTo = By.xpath("//input[@id='toAnotherCity1']");		//Found in Multi-way
	By toTextbox = By.xpath("//input[@placeholder='To']");

	By calLoad = By.xpath("//div[@class='DayPicker-Caption'][1]");
	By nextMonth = By.xpath("//span[@aria-label='Next Month']");
	By day = By.xpath("//div[@class='dateInnerCell']");
	By retCal = By.xpath("//span[text()='Return']");

	By armedOpt = By.xpath("(//p[text()='Armed'][1]");
	By studentOpt = By.xpath("(//p[text()='Student'][1]");
	By regularOpt = By.xpath("(//p[text()='Regular'][1]");

	By classBox = By.xpath("(//span[contains(text(),'Traveller')])[1]");
	By adult = By.xpath("(//ul[@class='guestCounter font12 darkText gbCounter'])[1]//li");		//all <li> elements under adult tab
	By child = By.xpath("(//ul[@class='guestCounter font12 darkText gbCounter'])[2]//li");		//all <li> elements under child tab
	By infant = By.xpath("(//ul[@class='guestCounter font12 darkText gbCounter'])[3]//li");		//all <li> elements under infant tab
	By classType = By.xpath("//ul[@class='guestCounter classSelect font12 darkText']//li");
	
	By multiAdult = By.xpath("(//ul[@class='guestCounter font12 darkText '])[1]//li");		//all <li> elements under adult tab
	By multiChild = By.xpath("(//ul[@class='guestCounter font12 darkText '])[2]//li");		//all <li> elements under child tab
	By multiInfant = By.xpath("(//ul[@class='guestCounter font12 darkText '])[3]//li");		//all <li> elements under infant tab
	
	By flightElement = By.xpath("//div[@class='fsw_inner returnPersuasion']");
	By multiFlightElement = By.xpath("//div[@class='anotherChild']");
	By classElement = By.xpath("//div[@class='travellers gbTravellers']");
	By multiClassElement = By.xpath("//div[@class='travellers ']");

	By applyClass = By.xpath("//button[text()='APPLY']");
	By faretype = By.xpath("(//ul[@class='specialFareNew'])/li/p");
	By offers = By.xpath("//li[@class=' '][2]");
	By search = By.xpath("//a[text()='Search']");

	public void handleAds()
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.tagName("body")), 0, 0);
		actions.moveByOffset(90, 150).click().build().perform();					//this should click at the corner of the page for ads
	}
	
	public String handlePopup()
	{
		String popupStatus="Popup appeared";
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(popup));		//this will handle the popup if it appears
		}
		catch(NoSuchElementException e)
		{
			popupStatus ="Popup didn't appeared";
		}
		return popupStatus;
	}
	
	public void selectMulti()
	{
		driver.findElement(multiSelect).click();
	}

	public void enterFrom(String fromPlace) throws InterruptedException
	{
		driver.findElement(from).click();
		driver.findElement(fromTextbox).sendKeys(fromPlace);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(fromTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}

	public void enterTo(String toPlace) throws InterruptedException
	{
		driver.findElement(to).click();
		driver.findElement(toTextbox).sendKeys(toPlace);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(toTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}

	public void enterMultiFromOne(String fromPlaceOne) throws InterruptedException
	{
		driver.findElement(firstFrom).click();
		driver.findElement(fromTextbox).sendKeys(fromPlaceOne);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(fromTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}

	public void enterMultiToOne(String toPlaceOne) throws InterruptedException
	{
		driver.findElement(toTextbox).sendKeys(toPlaceOne);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(toTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}
	
	public void enterMultiFromTwo(String fromPlaceTwo) throws InterruptedException
	{	
		WebElement scrollele = driver.findElement(scroll);
		JavascriptExecutor executor = (JavascriptExecutor) driver;					// 2nd Calendar is getting intercepted, hence scrolling it to view
		executor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", scrollele);
		Thread.sleep(3000);	
		
		driver.findElement(secondFrom).click();
		driver.findElement(fromTextbox).sendKeys(fromPlaceTwo);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(fromTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}

	public void enterMultiToTwo(String toPlaceTwo) throws InterruptedException
	{
		driver.findElement(toTextbox).sendKeys(toPlaceTwo);
		Thread.sleep(2000);															//This will allow suggestions to load
		driver.findElement(toTextbox).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}
	
	public void calendarOpen()
	{
		driver.findElement(retCal).click();
	}

	public void handleCalendar(String userDate)
	{
		int userDay = Integer.parseInt(userDate.split("-")[0]);
		String userMonthYear = userDate.split("-")[1]+userDate.split("-")[2];
		wait.until(ExpectedConditions.visibilityOfElementLocated(calLoad));
		String sysDate = driver.findElement(calLoad).getText().trim().replace(" ", "");			//This will remove any extra spaces from getText

		while(!userMonthYear.equalsIgnoreCase(sysDate))
		{
			driver.findElement(nextMonth).click();
			sysDate = driver.findElement(calLoad).getText().trim().replace(" ", "");			//This will update the month and year after every click for next month
		}

		List <WebElement> days = new ArrayList<WebElement>();
		days = driver.findElements(day);					//stores all options for no.of adults
		days.get(userDay-1).click();
	}
	
	public String flightSnap(String filePath) throws Exception
	{
		WebElement flight = driver.findElement(flightElement);
        int x = flight.getLocation().getX();
        int y = flight.getLocation().getY();
        int width = flight.getSize().getWidth();
        int height = flight.getSize().getHeight();
        ScreenCapture obj = new ScreenCapture(driver);
        return obj.takePartialSnap(filePath, x, y, width, height);
	}
	
	public String multiFlightSnap(String filePath) throws Exception
	{
		WebElement flight = driver.findElement(multiFlightElement);
        int x = flight.getLocation().getX();
        int y = flight.getLocation().getY();
        int width = flight.getSize().getWidth();
        int height = flight.getSize().getHeight();
        ScreenCapture obj = new ScreenCapture(driver);
        return obj.takePartialSnap(filePath, x, y, width, height);
	}

	public void classOpen()
	{
		driver.findElement(classBox).click();
	}	

	public void noOfAdults(int adultCount)
	{
		List <WebElement> adultlist = new ArrayList<WebElement>();
		adultlist = driver.findElements(adult);					//stores all options for no.of adults
		adultlist.get(adultCount-1).click();					//selects the relevant option for no.of adults
	}

	public void noOfChildren(int childCount)
	{
		List <WebElement> childlist = new ArrayList<WebElement>();
		childlist = driver.findElements(child);					//stores all options for no.of children
		childlist.get(childCount).click();						//selects the relevant option for no.of children
	}

	public void noOfInfants(int infantCount)
	{
		List <WebElement> infantlist = new ArrayList<WebElement>();
		infantlist = driver.findElements(infant);				//stores all options for no.of infants
		infantlist.get(infantCount).click();					//selects the relevant option for no.of infants
	}
	
	public void multiNoOfAdults(int adultCount)
	{
		List <WebElement> adultlist = new ArrayList<WebElement>();
		adultlist = driver.findElements(multiAdult);					//stores all options for no.of adults
		adultlist.get(adultCount-1).click();					//selects the relevant option for no.of adults
	}

	public void multiNoOfChildren(int childCount)
	{
		List <WebElement> childlist = new ArrayList<WebElement>();
		childlist = driver.findElements(multiChild);					//stores all options for no.of children
		childlist.get(childCount).click();						//selects the relevant option for no.of children
	}

	public void multiNoOfInfants(int infantCount)
	{
		List <WebElement> infantlist = new ArrayList<WebElement>();
		infantlist = driver.findElements(multiInfant);				//stores all options for no.of infants
		infantlist.get(infantCount).click();					//selects the relevant option for no.of infants
	}

	public void chooseClass(String travelClass)
	{
		List <WebElement> tclass = new ArrayList<WebElement>();
		tclass = driver.findElements(classType);				//stores all options for all 3 classes
		if(travelClass.equalsIgnoreCase("Flexible"))			//Flexible --> Economy/Premium Economy
		{
			tclass.get(0).click();
		}
		else if(travelClass.equalsIgnoreCase("Premium"))
		{
			tclass.get(1).click();
		}
		else
		{
			tclass.get(2).click();
		}
	}

	public String classSnap(String filePath) throws Exception
	{
		
		WebElement classType = driver.findElement(classElement);
        int x = classType.getLocation().getX();
        int y = classType.getLocation().getY();
        int width = classType.getSize().getWidth();
        int height = classType.getSize().getHeight();
        ScreenCapture obj = new ScreenCapture(driver);
        return obj.takePartialSnap(filePath, x, y, width, height);
	}
	
	public String multiClassSnap(String filePath) throws Exception
	{
		
		WebElement classType = driver.findElement(multiClassElement);
        int x = classType.getLocation().getX();
        int y = classType.getLocation().getY();
        int width = classType.getSize().getWidth();
        int height = classType.getSize().getHeight();
        ScreenCapture obj = new ScreenCapture(driver);
        return obj.takePartialSnap(filePath, x, y, width, height);
	}
	
	public void selectApply() throws Exception
	{	
		driver.findElement(applyClass).click();
	}

	public String chooseFareType(String fare)
	{
		String typeOfFare = "Fare type selected is: "+fare;

		List <WebElement> ftype = new ArrayList<WebElement>();
		ftype = driver.findElements(faretype);				//stores all options for all fare Types

		switch(fare.toLowerCase())
		{
		case "armed forces":ftype.get(1).click();
		break;

		case "student":ftype.get(2).click();
		break;

		case "senior citizens":ftype.get(3).click();
		break;

		case "doctors & nurses":ftype.get(4).click();
		break;

		case "double seat":ftype.get(5).click();
		break;

		default: typeOfFare = "Fare Type selected is by default: Regular";
		}

		return typeOfFare;
	}

	public void selectSearch() throws Exception
	{
		Thread.sleep(2000);
		driver.findElement(offers).click();				// search button was getting intercepted when fare-type was Doctors & nurses or Double seat
		driver.findElement(search).click();
	}

}
