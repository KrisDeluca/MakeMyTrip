package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass{

	public WebDriver driver;
	public Properties read;
	public ExtentSparkReporter htmlReporter;
	public ExtentReports report;
	public ExtentTest test;

	@BeforeTest
	public void beforeTest() throws FileNotFoundException, IOException
	{
		read=new Properties();
		read.load(new FileInputStream("resources/settings.property"));
		String className = this.getClass().getSimpleName();
		htmlReporter = new ExtentSparkReporter("target/reports/"+className+".html");
		
		report=new ExtentReports();
		report.attachReporter(htmlReporter);
		
		report.setSystemInfo("User", "KrisDeluca");
		report.setSystemInfo("OS", "Windows 11");
		report.setSystemInfo("Browser", read.getProperty("browserName"));
		
		htmlReporter.config().setDocumentTitle("MakeMyTrip");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE, MMM dd, yyyy, hh:mm a '('zzz')'");
	}

	@BeforeMethod
	public void beforeMethod()
	{
		BrowserStart obj=new BrowserStart();
		driver=obj.selectBrowser(read.getProperty("browserName"));
		driver.manage().window().maximize();
		driver.get(read.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(read.getProperty("implicit.wait"))));
	}

	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		ScreenCapture obj=new ScreenCapture(driver);
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+": Test has Failed", ExtentColor.RED));
			test.addScreenCaptureFromPath(obj.takeFullSnap(read.getProperty("imgpath")+"/result/"+result.getName()),"Final Results");
		}
		
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+": Test has Passed", ExtentColor.GREEN));
			test.addScreenCaptureFromPath(obj.takeFullSnap(read.getProperty("imgpath")+"/result/"+result.getName()),"Final Results");
		}
		
		if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+": Test has Skipped", ExtentColor.YELLOW));
			test.addScreenCaptureFromPath(obj.takeFullSnap(read.getProperty("imgpath")+"/result/"+result.getName()),"Final Results");
		}
		driver.close();
	}

	@AfterTest
	public void afterTest()
	{
		driver.quit();
		report.flush();
	}	

}
