package flightsMain;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import flightsObject.TravelDetails;
import utilities.BaseClass;
import utilities.ExcelReader;

public class RoundWayPgOne extends BaseClass {

	@Test(testName = "Round trip Booking", dataProvider = "dp")	
	public void oneWayTrip(String iteration,String from, String to, String fromDate, String toDate, String adults, String children, String infants, String classType, String fareType) throws Exception
	{		
		//Thread.sleep(5000);
		//MakeMyTrip - #1 Travel Website 50% OFF on Hotels, Flights & Holiday
		test = report.createTest("Round Way Trip Iteration: "+iteration);
		TravelDetails obj = new TravelDetails(driver);

		test.log(Status.INFO, obj.handlePopup());		//This will handle popup and display relevant notification at same time

		obj.enterFrom(from);
		obj.enterTo(to);
		obj.handleCalendar(fromDate);
		obj.calendarOpen();
		obj.handleCalendar(toDate);
		test.log(Status.INFO, "Flight details");
		test.addScreenCaptureFromPath(obj.flightSnap(read.getProperty("imgpath")+"/roundway/FlightDetails"),"Flight details");

		obj.classOpen();
		obj.noOfAdults(Integer.parseInt(adults));
		obj.noOfChildren(Integer.parseInt(children));
		obj.noOfInfants(Integer.parseInt(infants));
		obj.chooseClass(classType);
		test.log(Status.INFO, "Passenger Details");
		test.addScreenCaptureFromPath(obj.classSnap(read.getProperty("imgpath")+"/roundway/PassengerDetails"),"Passenger Details");
		obj.selectApply();		

		test.log(Status.INFO, obj.chooseFareType(fareType));	//This will display and select relevant Fare type

		test.log(Status.INFO, "All details");
		obj.selectSearch();

		Assert.assertEquals(obj.pageTitle(), "MakeMyTrip");
	}

	@DataProvider
	public Object[][] dp() throws Exception
	{
		ExcelReader exObj = new ExcelReader();
		Object [][] data=new Object[exObj.rowNum("RoundWay")][10];
		for(int i=1;i<=data.length;i++)
		{
			for(int j=0;j<10;j++)
			{
				data[i-1][j]= exObj.readCell("RoundWay",i,j);
			}
		}		
		return data;
	}

}
