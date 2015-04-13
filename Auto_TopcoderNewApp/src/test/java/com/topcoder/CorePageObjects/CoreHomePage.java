package com.topcoder.CorePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.topcoder.General.GeneralUtility;
import com.topcoder.General.WebPageObjectsActions;

public class CoreHomePage 
{
	
	private static final By loginLink = By.id("btn-login");
	private static final By logoutLink = By.id("btn-logout");
	private static final By userNameTxt = By.id("a0-signin_easy_email");
	private static final By passwordTxt = By.id("a0-signin_easy_password");
	private static final By accessBtn = By.cssSelector("#a0-onestep > div.a0-mode-container > div > form > div.a0-emailPassword > div.a0-action > button");
	private WebDriver wDriver;
	private WebElement homePageElement= null;
	private WebPageObjectsActions webPageObj=new WebPageObjectsActions(wDriver);
	
	//Class constructer
	public CoreHomePage(WebDriver driver) {	
		wDriver=driver;
		//webPageObj = new WebPageObjects(wDriver);
	}
	
	public boolean verifyPageLoading() {		
		boolean result= webPageObj.verifyElementExists(loginLink, "Log in");
		return result;
	}
	
	public boolean verifyLoginPopup() {
		homePageElement = webPageObj.findElement(loginLink);
		homePageElement.click();
		boolean result= webPageObj.verifyElementExists(accessBtn, "Access");
		return result;
	}
	
	public boolean loginApp() {
		homePageElement = webPageObj.findElement(loginLink);
		homePageElement.click();
		homePageElement = webPageObj.findElement(userNameTxt);
		homePageElement.sendKeys("Auto_User1");
		homePageElement = webPageObj.findElement(passwordTxt);
		homePageElement.sendKeys("appirio123$");
		homePageElement = webPageObj.findElement(accessBtn);
		homePageElement.click();
		GeneralUtility.sleep(10000);
		boolean result= webPageObj.verifyElementExists(logoutLink, "Log out");
		return result;
	}
}
