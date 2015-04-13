package com.topcoder.DemandPageObjects;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import com.topcoder.General.GeneralUtility;
import com.topcoder.General.WebPageObjectsActions;

public class CreateProjectPage {
	private static final By btnNewProject = By.cssSelector("body > main > a");
	private static final By txtProjectName = By.name("name");
	private static final By lblNameProj = By.cssSelector("body > main > main > article.state.name.ng-scope.state-active > h3");
	private static final By lblProjType	= By.cssSelector("body > main > main > article.state.type.ng-scope.state-active > h3");
	private static final By btnProjTypeCode = By.id("project-type-code"); 
	private static final By btnProjTypeDesign = By.id("project-type-design");
	private static final By btnProjTypeDesignCode = By.id("project-type-design-code");
	private static final By btnProjNameNext = By.cssSelector("body > main > main > article.state.name.ng-scope.state-active > form > next-state > button"); 
	private static final By btnProjTypeNext = By.cssSelector("body > main > main > article.state.type.ng-scope.state-active > form > next-state > button"); 
	private static final SoftAssert softAssert = new SoftAssert();
	private static WebPageObjectsActions webPageObj = null;
	boolean result = false;
	private WebElement projectPageElement= null; 
		
	/**
	 * Class constructor
	 * @param driver
	 */
	public CreateProjectPage(WebDriver driver) {
		webPageObj = new WebPageObjectsActions(driver);
	}
	
	/**
	 * Checks whether the user is able to login the application
	 */
	public void createAProject(Properties properties) {
		//Verifies if correct page is displayed
		softAssert.assertTrue(webPageObj.verifyElementExists(btnNewProject, ""),"Project creation page not displayed.");
		projectPageElement = webPageObj.findElement(btnNewProject);
		projectPageElement.click();
		GeneralUtility.sleep(5000);
		softAssert.assertTrue(webPageObj.verifyElementExists(lblNameProj, "Name your project"),"Project creation page not displayed.");
		projectPageElement = webPageObj.findElement(txtProjectName);
		projectPageElement.sendKeys(properties.getProperty("projectName"));
		projectPageElement = webPageObj.findElement(btnProjNameNext);
		projectPageElement.click();
		GeneralUtility.sleep(5000);
		//Verifies if control moves to the right section on the page
		softAssert.assertTrue(webPageObj.verifyElementExists(lblProjType, "What type of project do you need?"),"Page does not moves to project type section.");
		projectPageElement = webPageObj.findElement(btnProjTypeCode);
		projectPageElement.click();
		GeneralUtility.sleep(5000);
		projectPageElement = webPageObj.findElement(btnProjTypeNext);
		projectPageElement.click();
		softAssert.assertAll();
	}
			
}
