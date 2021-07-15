package DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import CommonFunctionLibrary.CommonFunction;
import Utilities.ExcelFileUtil;
import Utilities.PropertyFileUtil;

public class DriverScript {
	
	WebDriver driver;
	ExcelFileUtil xl;
	ExtentReports report;
	ExtentTest test;
	ExtentHtmlReporter htmlReporter;
	String inputPath = "C:\\OJTProjectTesting\\OrangeHRMDemo\\TestInput\\OrangeHRMData.xlsx";
	String outpath = "C:\\OJTProjectTesting\\OrangeHRMDemo\\TestOutput\\OrangeHRMResult.xlsx";

	public void startTest() throws Throwable {
		xl = new ExcelFileUtil("C:\\OJTProjectTesting\\OrangeHRMDemo\\TestInput\\OrangeHRMData.xlsx");

		int column = xl.cellCount("MasterTestCases");
		System.out.println("Column count " + column);

		for (int i = 1; i <= xl.rowCount("MasterTestCases"); i++) {

			if (xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
				String TModule = xl.getCellData("MasterTestCases", i, 1);
				htmlReporter = new ExtentHtmlReporter("./Extent_Report/" + TModule + ".html");
				report = new ExtentReports();
				report.attachReporter(htmlReporter);
				int columnCount = xl.cellCount("MasterTestCases");

				for (int j = 1; j <= xl.rowCount(TModule); j++) {    // Modules row count
					String Description = xl.getCellData(TModule, j, 0);
					String ObjectType = xl.getCellData(TModule, j, 1);
					System.out.println(Description);
					String LocatorType = xl.getCellData(TModule, j, 2);
					String LocatorValue = xl.getCellData(TModule, j, 3);
					String TestData = xl.getCellData(TModule, j, 4);

					test = report.createTest(Description); // create test in extent report

					try {
						if (ObjectType.equalsIgnoreCase("launchBrowser")) {
							driver = CommonFunction.launchBrowser();
							test.log(Status.PASS, "Launched Browser");

						} else if (ObjectType.equalsIgnoreCase("lanuchApplication")) {
							CommonFunction.lanuchApplication(driver);
							test.log(Status.PASS, "Application luanched");
						}

						else if (ObjectType.equalsIgnoreCase("waitForElement")) {
							CommonFunction.waitForElement(driver, LocatorType, LocatorValue, TestData);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("typeAction")) {
							CommonFunction.typeAction(driver, LocatorType, LocatorValue, TestData);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("clickAction")) {
							CommonFunction.clickAction(driver, LocatorType, LocatorValue);
							String actualURL = driver.getCurrentUrl();
							System.out.println(actualURL);
							if (actualURL.equalsIgnoreCase(PropertyFileUtil.getKeyValue("dashboardURL"))) {

								//test.log(Status.PASS, "User successfully logged in");
							}

							else if (actualURL.equalsIgnoreCase(PropertyFileUtil.getKeyValue("loginURL"))) {
								String msg = driver.findElement(By.xpath("//span[@id='spanMessage']")).getText();
								Reporter.log("System showed error message " + msg, true);
								//test.log(Status.PASS, msg);
							}
							// String actualURL=driver.getCurrentUrl(); //get actual URL and verify with
							// Expected url
							// Assert.assertEquals(PropertyFileUtil.getKeyValue("dashboardURL"), actualURL);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("mouseHover")) {
							CommonFunction.mouseHover(driver, LocatorType, LocatorValue);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("captureData")) {
							CommonFunction.captureData(driver, LocatorType, LocatorValue);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("validateEmployeeTable")) {
							CommonFunction.validateEmployeetable(driver, LocatorType, LocatorValue, TestData);
							test.log(Status.PASS, Description);
						}

						else if (ObjectType.equalsIgnoreCase("closeBrowser")) {
							CommonFunction.closeBrowser(driver);
							test.log(Status.PASS, Description);

						}

						// xl.setCellData("ApplicationLogin", i, 5, "PASS", outpath);
						xl.setCellData(TModule, j, 5, "PASS", outpath);
						xl.setCellData("MasterTestCases", i, 3, "PASS",outpath);
             
					} // try end brace
					catch (Exception e) {
						// xl.setCellData("ApplicationLogin", i, 5, "FAIL", outpath);
						xl.setCellData(TModule, j, 5, "FAIL", outpath);
						xl.setCellData("MasterTestCases", i, 3, "FAIL", outpath);
						test.log(Status.FAIL, Description + " Fail");
					}

				}//inner for loop
				
				report.flush();
			
			} //if 

			else {
				xl.setCellData("MasterTestCases", i, 3, "BLOCKED", outpath);
			}

			

		} //outer foor loop

	}

}
