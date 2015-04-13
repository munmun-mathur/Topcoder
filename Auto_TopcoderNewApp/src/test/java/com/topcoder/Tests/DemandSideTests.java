package com.topcoder.Tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.topcoder.DemandPageObjects.CreateProjectPage;
import com.topcoder.DemandPageObjects.DemandHomePage;
import com.topcoder.General.GeneralUtility;

public class DemandSideTests {
	private static WebDriver driver;
	private static DemandHomePage homePageObj = null;
	private static CreateProjectPage createProjPage = null;
	java.util.Enumeration<Object> enuKeys =null;
	File file = new File("src/test/resources/userData.properties");
	FileInputStream fileInput =null;
	Properties properties = new Properties();    
	String browser= "";
	
	@BeforeClass
	public void setUp() {
		 try {
	    	 	//Read properties file
    	 		fileInput = new FileInputStream(file);
    	 		properties.load(fileInput);
    	 		fileInput.close();
    	 		
				//Initialise driver with specified browser
    	 		browser = properties.getProperty("browser");
    	 		
    	 		//Initialise chrome browser
    	 		if (browser.equalsIgnoreCase("chrome")) {
    				System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
                    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("test-type");
                    capabilities.setCapability("chrome.binary","drivers/chromedriver.exe");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    driver = new ChromeDriver(capabilities);
    			}
    	 		//Initialise firefox browser
    			else if  (browser.equalsIgnoreCase("firefox")) {
    				driver= new FirefoxDriver();
    			}
    			// Maximize the browser window
    			driver.manage().window().maximize();
    			GeneralUtility.openUrl(driver, properties.getProperty("demandURL"));
    			GeneralUtility.sleep(6000);     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	@Test(priority=1,testName="Scenario1",description="Login popup")

	public void verifyLoginPopup() {
		homePageObj = new DemandHomePage(driver);
		homePageObj.verifyLoginPopup();
	}
	
	@Test(priority=2,testName="Scenario2",description="User login")

	public void userLogin() {
		homePageObj = new DemandHomePage(driver);
		createProjPage = homePageObj.loginApp(properties.getProperty("username"), properties.getProperty("password"));
	}
	
	@Test(priority=3,testName="Scenario3",description="Create project")

	public void createProject() {
		createProjPage.createAProject(properties);
	}
	
	@AfterClass
	public void endTest() {
		driver.quit();
	}
	
}