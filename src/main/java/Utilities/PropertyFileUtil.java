package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class PropertyFileUtil {

	WebDriver driver;
	static FileInputStream fis;
	static Properties p;
	
	
	public static String getKeyValue(String key) throws Throwable
	{
		
		fis= new FileInputStream("C:\\OJTProjectTesting\\OrangeHRMDemo\\PropertyFiles\\Environment.properties");
		p= new Properties();
		p.load(fis);
		return p.getProperty(key);
		
	}
}
