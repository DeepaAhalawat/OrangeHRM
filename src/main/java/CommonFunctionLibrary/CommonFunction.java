package CommonFunctionLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.PropertyFileUtil;

public class CommonFunction {

	static WebDriver driver;
	static WebDriverWait wait;
	public static WebDriver launchBrowser() throws Throwable 
	{
	
		if(PropertyFileUtil.getKeyValue("browser").equalsIgnoreCase("chrome"))
		{
		System.setProperty("webdriver.chrome.driver", "C:\\OJTProjectTesting\\OrangeHRMDemo\\CommonDrivers\\chromedriver.exe");
		driver=new ChromeDriver();
		
		}
		else
		System.out.println("browser not found");
		driver.manage().window().maximize();
		
		return driver;
	}
	
	
	public static void lanuchApplication(WebDriver driver) throws Throwable
	{
		driver.get(PropertyFileUtil.getKeyValue("url"));
		
	}
	
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String TestData)
	{
		int waittime= Integer.valueOf(TestData);
		wait= new WebDriverWait(driver,waittime);
		if(LocatorType.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			
			wait.until(ExpectedConditions.elementToBeClickable(By.name(LocatorValue)));
		}
	 }
	
	public static void typeAction(WebDriver driver,String LocatorType, String LocatorValue, String TestData )
	{
		//wait=new WebDriverWait(driver,)
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}
	
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		 if(LocatorType.equalsIgnoreCase("id"))
		 {
			driver.findElement(By.id(LocatorValue)).click();
		 }
		  else if(LocatorType.equalsIgnoreCase("name"))
		 {
			driver.findElement(By.name(LocatorValue)).click();
		 }
    }
}
