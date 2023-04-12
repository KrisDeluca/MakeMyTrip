package utilities;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserStart {
	
	WebDriver driver;

	public WebDriver selectBrowser(String browserName)
	{
		
		if(browserName.equalsIgnoreCase("Chrome"))
		{
			WebDriverManager.chromedriver().setup();
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--disable-notifications");			
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--incognito");
			options.addExtensions(new File("resources/AdsBlocker.crx"));			
			driver = new ChromeDriver(options);
		}
		
		if(browserName.equalsIgnoreCase("Firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options=new FirefoxOptions();
			options.addArguments("--disable-notifications");			
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--incognito");
			options.addArguments("--headless");			
			driver = new FirefoxDriver(options);
		}		
		
		return driver;		
		
	}
	
}
