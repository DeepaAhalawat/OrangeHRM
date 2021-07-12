package DriverFactory;

import org.testng.annotations.Test;

public class AppTest {

	@Test
	public void apptest() throws Throwable
	{
		try
		{
		DriverScript ds= new DriverScript();
		ds.startTest();
		
	    }
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		
		}
		}
}
