package utilities;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserStart {
	
	WebDriver driver;
	DesiredCapabilities capabilities;

	public WebDriver selectBrowser(String browserName)
	{
		capabilities = new DesiredCapabilities();
		
		if(browserName.equalsIgnoreCase("Chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--disable-notifications");			
			options.addArguments("--disable-popup-blocking");
			options.addExtensions(new File("resources/AdsBlocker.crx"));			
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		}
		
		if(browserName.equalsIgnoreCase("Firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			FirefoxOptions options=new FirefoxOptions();
			options.addArguments("--disable-notifications");			
			options.addArguments("--disable-popup-blocking");
			//FirefoxProfile profile = new FirefoxProfile();
			//profile.addExtension(new File("resources/AdsBlocker.crx"));			//in-case you need to add extension
			//options.setProfile(profile);
			capabilities.merge(options);
		}		
		
		return driver;		
		
	}
	
}
