package com.topcoder.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.topcoder.CorePageObjects.CoreHomePage;
import com.topcoder.General.GeneralUtility;
import org.testng.asserts.SoftAssert;

public class CoreSideTests {
	WebDriver driver;
	private SoftAssert softAssert = new SoftAssert();
	CoreHomePage homePageObj = null;
	@BeforeClass
	public void setUp()
	{
		//System.setProperty("webdriver.chrome.driver","drivers\\chromedriver.exe");
		driver= new FirefoxDriver();
		// maximize the browser window
		driver.manage().window().maximize();
	}
	
	@Test(priority=1,testName="Scenario1",description="Page loading")
	public void checkPageLoading()
	{
		homePageObj = new CoreHomePage(driver);
		GeneralUtility.openUrl(driver, "http://api.topcoder-dev.com/sample/index2.html");
		GeneralUtility.sleep(10000);
		boolean	result= homePageObj.verifyPageLoading();
		softAssert.assertTrue(result,"Page not loading.");
		softAssert.assertAll();		
	}
	
	@Test(priority=2,testName="Scenario2",description="Login popup")
	public void LoginScenarios()
	{
		    homePageObj = new CoreHomePage(driver);
			boolean result=	homePageObj.verifyLoginPopup();
			softAssert.assertTrue(result,"Login popup not working");
		/*	if(result)
			{
				Reporter.log("Login popup working");
			}
			else
			{
				Reporter.log("Login popup not working");
			}*/
			softAssert.assertAll();	
			
	}
	
	@Test(priority=3,testName="Scenario3",description="Login user")

	public void UserLogin()
	{
			homePageObj = new CoreHomePage(driver);
			boolean	result= homePageObj.loginApp();
			/*if(result)
			{
				Reporter.log("User login successful.");
			}
			else
			{
				Reporter.log("Not able to login the application.");
			}*/
			System.out.println("login result="+result);
			
			softAssert.assertTrue(result,"Not able to login the application.");
			softAssert.assertAll();	
	}
	
  
	@AfterClass
	public void endTest()
	{
		driver.quit();
	}
}
