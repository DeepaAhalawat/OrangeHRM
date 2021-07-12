package DriverFactory;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import CommonFunctionLibrary.CommonFunction;
import Utilities.ExcelFileUtil;

public class DriverScript {

	WebDriver driver;
	ExcelFileUtil xl;
	ExtentReports report;
	ExtentTest test;
	ExtentHtmlReporter htmlReporter;
	String inputPath="C:\\OJTProjectTesting\\OrangeHRMDemo\\TestInput\\OrangeHRMData.xlsx";
    String outpath="C:\\OJTProjectTesting\\OrangeHRMDemo\\TestOutput\\OrangeHRMResult.xlsx";
	
	public void startTest() throws Throwable
	 {
		xl= new ExcelFileUtil("C:\\OJTProjectTesting\\OrangeHRMDemo\\TestInput\\OrangeHRMData.xlsx");
		int rowCount=xl.rowCount("ApplicationLogin");
		System.out.println("row count "+rowCount);
		int column=xl.cellCount("ApplicationLogin");
		System.out.println("Column count "+column);
		htmlReporter= new ExtentHtmlReporter("./Extent_Report/"+"AppLoginModule.html");
		report= new ExtentReports();
		report.attachReporter(htmlReporter);
		
		for(int i=1;i<=rowCount;i++)
		{
			
		String  Description=xl.getCellData("ApplicationLogin", i, 0);
		String  ObjectType=xl.getCellData("ApplicationLogin", i, 1);
		System.out.println("object type "+ObjectType);
		String  LocatorType=xl.getCellData("ApplicationLogin", i, 2);
		String  LocatorValue=xl.getCellData("ApplicationLogin", i, 3);
		String  TestData=xl.getCellData("ApplicationLogin", i, 4);
		
		test=report.createTest(ObjectType);   //create test in extent report
		
		try
		{
		if(ObjectType.equalsIgnoreCase("launchBrowser"))
		{
			driver=CommonFunction.launchBrowser();
			System.out.println("launched browser");
			test.log(Status.PASS,"Launched Browser");
			
		}
		else if(ObjectType.equalsIgnoreCase("lanuchApplication"))
		{
			CommonFunction.lanuchApplication(driver);
			System.out.println("application launched");
			test.log(Status.PASS,"Application luanched");
		}
		
		else if(ObjectType.equalsIgnoreCase("waitForElement"))
		{
		    CommonFunction.waitForElement(driver, LocatorType,LocatorValue,TestData);
		    test.log(Status.PASS,"wait for element");
		}
		
		else if(ObjectType.equalsIgnoreCase("typeAction"))
		{
			CommonFunction.typeAction(driver, LocatorType, LocatorValue, TestData);
			test.log(Status.PASS, "data entered");
		}
		
		else if(ObjectType.equalsIgnoreCase("clickAction"))
		{
			CommonFunction.clickAction(driver, LocatorType, LocatorValue);
			test.log(Status.PASS, "button clicked");
		}
		
		xl.setCellData("ApplicationLogin", i, 5, "PASS", outpath);
	
		
	    }// try end brace
		
		catch(Exception e) 
		{
			xl.setCellData("ApplicationLogin", i, 5, "FAIL", outpath);
			test.log(Status.FAIL, ObjectType+" Fail");
		}
		
		}//for loop
		report.flush();
		driver.close();
		
		
		
		
	}
}
