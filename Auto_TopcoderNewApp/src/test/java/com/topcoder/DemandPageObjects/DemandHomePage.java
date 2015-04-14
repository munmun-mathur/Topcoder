package com.topcoder.DemandPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.topcoder.General.GeneralUtility;
import com.topcoder.General.WebPageObjectsActions;

public class DemandHomePage {
	private static final By loginLink = By.cssSelector("body > header > a.login.ng-scope > div:nth-child(2)");
	private static final By getStartedBtn = By.cssSelector("body > header > a.get-started");
	private static final By createDesignProj = By.cssSelector("body > main > main > ul > li:nth-child(1) > a > div.icon > svg > image");
	private static final By createDevProj = By.cssSelector("body > main > main > ul > li:nth-child(2) > a > div.icon > svg > image");
	private static final By createCustomProj = By.cssSelector("body > main > main > ul > li:nth-child(3) > a > div.icon > svg > image");
	private static final By signInLink = By.cssSelector("body > footer > ul > li:nth-child(1) > a");
	private static final By helpLink = By.cssSelector("body > footer > ul > li:nth-child(2) > a");
	private static final By aboutLink = By.cssSelector("body > footer > ul > li:nth-child(3) > a");
	private static final By userNameTxt = By.id("a0-signin_easy_email");
	private static final By passwordTxt = By.id("a0-signin_easy_password");
	private static final By accessBtn = By.cssSelector("#a0-onestep > div.a0-mode-container > div > form > div.a0-emailPassword > div.a0-action > button");
	private static final By showAllBtn = By.cssSelector("#a0-onestep > div.a0-mode-container > div > form > a");
	private static final By userLoggedInLink = By.cssSelector("body > header > a.login.ng-scope > div.label.ng-binding");
	private static final SoftAssert softAssert = new SoftAssert();
	private WebPageObjectsActions webPageObj=null;
	private WebElement homePageElement= null;
	private WebDriver wDriver = null;
	
	/**
	 * Class constructor
	 * @param driver
	 */
	public DemandHomePage(WebDriver driver) {
		wDriver=driver;
		webPageObj = new WebPageObjectsActions(driver);
	}
	
	/**
	 * Verifies whether the login popup shows up when login link is clicked
	 */
	public void verifyLoginPopup() {
		homePageElement = webPageObj.findElement(loginLink);
		homePageElement.click();
		GeneralUtility.sleep(5000);
		softAssert.assertTrue(webPageObj.verifyElementExists(accessBtn, "Access"),"Login popup not showing up.");
		softAssert.assertAll();
	}
	
	/**
	 * Checks whether the user is able to login the application
	 * @return
	 */
	public CreateProjectPage loginApp(String userName, String password) {
		//homePageElement = webPageObj.findElement(loginLink);
		//homePageElement.click();
		homePageElement = webPageObj.findElement(userNameTxt);
		homePageElement.sendKeys(userName);
		homePageElement = webPageObj.findElement(passwordTxt);
		homePageElement.sendKeys(password);
		homePageElement = webPageObj.findElement(accessBtn);
		homePageElement.click();
		GeneralUtility.sleep(12000);
		softAssert.assertTrue(webPageObj.verifyElementExists(userLoggedInLink, "munmun_mathur"), "User is not able to login the application.");
		softAssert.assertAll();
		return new CreateProjectPage(wDriver);
	}
}
