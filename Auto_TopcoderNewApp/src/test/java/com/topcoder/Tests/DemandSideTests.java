package com.topcoder.Tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
    				/*DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("test-type");
                    capabilities.setCapability("chrome.binary","drivers/chromedriver.exe");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);*/
                    driver = new ChromeDriver();
    			}
    	 		//Initialise firefox browser
    			else if  (browser.equalsIgnoreCase("firefox")) {
    				//driver= new FirefoxDriver();
    				DesiredCapabilities capability = DesiredCapabilities.firefox();
    				capability.setCapability("platform", Platform.ANY);
    				capability.setCapability("binary", "/ms/dist/fsf/PROJ/firefox/37.0.1/bin/firefox"); //for linux

    				//capability.setCapability("binary", "C:\\Program Files\\Mozilla  Firefox\\msfirefox.exe"); //for windows                
    				driver = new FirefoxDriver(capability);
    				
    			}
    	 		//Initialise IE browser
    			else if  (browser.equalsIgnoreCase("IE")) {
    				System.setProperty("webdriver.ie.driver","drivers/IEDriverServer.exe");
    				driver= new InternetExplorerDriver();
    			}
    			// Maximize the browser window
    			driver.manage().window().maximize();
    			GeneralUtility.openUrl(driver, properties.getProperty("demandURL"));
    			GeneralUtility.sleep(6000);     
    			// Take snapshot of browser
    	        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	        FileUtils.copyFile(srcFile, new File("ffsnapshot.png"));
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
