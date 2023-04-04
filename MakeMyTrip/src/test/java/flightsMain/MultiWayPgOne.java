package flightsMain;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import flightsObject.TravelDetails;
import utilities.BaseClass;
import utilities.ExcelReader;

public class MultiWayPgOne extends BaseClass {

	@Test(testName = "Multi trip Booking", dataProvider = "dp")	
	public void oneWayTrip(String iteration,String from1, String to1, String from2, String to2, String fromDate1, String fromDate2, String adults, String children, String infants, String classType, String fareType) throws Exception
	{		
		//Thread.sleep(5000);
		//MakeMyTrip - #1 Travel Website 50% OFF on Hotels, Flights & Holiday
		test = report.createTest("Multi Way Trip Iteration: "+iteration);
		TravelDetails obj = new TravelDetails(driver);

		test.log(Status.INFO, obj.handlePopup());		//This will handle popup and display relevant notification at same time

		obj.enterMultiFromOne(from1);
		obj.enterMultiToOne(to1);
		obj.handleCalendar(fromDate1);
		
		obj.enterMultiFromTwo(from1);
		obj.enterMultiToTwo(to1);
		obj.handleCalendar(fromDate2);
		test.log(Status.INFO, "Flight details", MediaEntityBuilder.createScreenCaptureFromPath(obj.multiFlightSnap(read.getProperty("imgpath")+"/roundway/FlightDetails")).build());

		obj.classOpen();
		obj.noOfAdults(Integer.parseInt(adults));
		obj.noOfChildren(Integer.parseInt(children));
		obj.noOfInfants(Integer.parseInt(infants));
		obj.chooseClass(classType);
		test.log(Status.INFO, "Passenger Details", MediaEntityBuilder.createScreenCaptureFromPath(obj.multiClassSnap(read.getProperty("imgpath")+"/oneway/PassengerDetails")).build());
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
		Object [][] data=new Object[exObj.rowNum("MultiWay")][12];
		for(int i=1;i<=data.length;i++)
		{
			for(int j=0;j<12;j++)
			{
				data[i-1][j]= exObj.readCell("MultiWay",i,j);
			}
		}		
		return data;
	}

}
