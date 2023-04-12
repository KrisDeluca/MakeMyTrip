package flightsMain;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import flightsObject.TravelDetails;
import utilities.BaseClass;
import utilities.ExcelReader;

public class OneWayPgOne extends BaseClass {

	@Test(testName = "OneWay trip Booking", dataProvider = "dp")	
	public void oneWayTrip(String iteration,String from, String to, String fromDate, String adults, String children, String infants, String classType, String fareType) throws Exception
	{		
		
		Assert.assertEquals(driver.getTitle(), "MakeMyTrip - #1 Travel Website 50% OFF on Hotels, Flights & Holiday", "Page Loaded successfully");
	
		test = report.createTest("OneWay Trip Iteration: "+iteration);
		TravelDetails obj = new TravelDetails(driver);
		obj.handleAds();								//This will handle the random ads which popup sometimes
		test.log(Status.INFO, obj.handlePopup());		//This will handle popup and display relevant notification at same time
														//Popup also gets handled with ads, but keeping it just in case
		obj.enterFrom(from);
		obj.enterTo(to);
		obj.handleCalendar(fromDate);
		test.log(Status.INFO, "Flight details", MediaEntityBuilder.createScreenCaptureFromPath(obj.flightSnap(read.getProperty("imgpath")+"/oneway/FlightDetails")).build());
		
		obj.classOpen();
		obj.noOfAdults(Integer.parseInt(adults));
		obj.noOfChildren(Integer.parseInt(children));
		obj.noOfInfants(Integer.parseInt(infants));
		obj.chooseClass(classType);
		test.log(Status.INFO, "Passenger Details", MediaEntityBuilder.createScreenCaptureFromPath(obj.classSnap(read.getProperty("imgpath")+"/oneway/PassengerDetails")).build());
		obj.selectApply();
		
		test.log(Status.INFO, obj.chooseFareType(fareType));	//This will display and select relevant Fare type

		test.log(Status.INFO, "All details");
		obj.selectSearch();

		Assert.assertEquals(obj.pageTitle(), "MakeMyTrip");
		
		obj.writeDetails("OneWay", "OneWay_"+iteration);
	}

	@DataProvider
	public Object[][] dp() throws Exception
	{
		ExcelReader exObj = new ExcelReader();
		Object [][] data=new Object[exObj.rowNum("OneWay")][9];				//exObj.rowNum("OneWay") change this to 1 for single iteration
		for(int i=1;i<=data.length;i++)
		{
			for(int j=0;j<9;j++)
			{
				data[i-1][j]= exObj.readCell("OneWay",i,j);
			}
		}		
		return data;
	}

}
