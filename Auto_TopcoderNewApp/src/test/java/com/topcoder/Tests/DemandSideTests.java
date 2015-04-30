package com.topcoder.Tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.topcoder.DemandPageObjects.CreateProjectPage;
import com.topcoder.DemandPageObjects.DemandHomePage;
import com.topcoder.General.GeneralUtility;

public class DemandSideTests {
	private static WebDriver driver;
	private static DemandHomePage homePageObj = null;
	private static CreateProjectPage createProjPage = null;
	java.util.Enumeration<Object> enuKeys =null;
	File file = new File("src/test/resources/userData.properties");
	File file1 = new File("src/test/resources/AWSCredentials.properties");
	FileInputStream fileInput =null;
	Properties properties = new Properties();    
	String browser= "";
	
	@BeforeClass
	public void setUp() {
		 try {
			 /************************Code to access Linux ec2 instance******************************************
			
			 	RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
				        	
				  runInstancesRequest.withImageId("ami-630c3b53")
				                     .withInstanceType("t2.micro")
				                     .withMinCount(1)
				                     .withMaxCount(10)
				                     .withKeyName("linux3");
				                     
				  AWSCredentials awsCredentials = new PropertiesCredentials(new FileInputStream(file1));
				  AmazonEC2Client amazonEC2Client = new AmazonEC2Client(awsCredentials);
				  RunInstancesResult runInstancesResult = amazonEC2Client.runInstances(runInstancesRequest);
				  System.out.println(runInstancesResult.toString());
				  
			***************************************************************************************************/
				  
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
    				ProfilesIni profile = new ProfilesIni();
    				 
    				FirefoxProfile myprofile = profile.getProfile("FFProfile/ucitnccj.NewProfile");
    				 
    				driver = new FirefoxDriver(myprofile);
    				
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
