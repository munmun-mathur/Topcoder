package com.topcoder.General;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
public class WebPageObjectsActions 
{
	private static WebDriver fDriver;
	
	/**
	 *  Class Constructor 
	**/
	public WebPageObjectsActions(WebDriver driver) {
		fDriver=driver;
	}
	
	/**
	 *  Finds a list of elements 
	**/
	public static List<WebElement> findElements(By by) {
		try {
			List<WebElement> varEle = fDriver.findElements(by);
			return varEle;
		}
		catch(NoSuchElementException err) {
			return null;
		}
	}
	
	/**
	 *  Finds an element 
	**/
	public WebElement findElement(By by) {
		try {
			WebElement varEle = null;
			varEle = fDriver.findElement(by);
			return varEle;
		}	catch(NoSuchElementException err) {
				System.out.println(err.getMessage());
				System.out.println("Element not found");
				return null;
		} catch(ElementNotFoundException e)	{
			return null;
		}
	}
	
	/**
	 *  Finds, verifies and enters value in a textbox 
	**/
	protected boolean enterValueInTextBox(By Field,String Value) {
		 WebElement varWElement=findElement(Field);
		 if(varWElement.isDisplayed() && varWElement.getAttribute("readonly")==null) {
			 varWElement.clear();
			 varWElement.sendKeys(Value);
			 return true;
		 }
		 else if(varWElement.getAttribute("readonly")!=null) {
			if(varWElement.getAttribute("readonly").equalsIgnoreCase("readonly") || varWElement.getAttribute("readonly").equalsIgnoreCase("") || varWElement.getAttribute("readonly").equalsIgnoreCase("true")) {
			return true;
			}
		 }
		 else {
			return true;
		 }
         return false;     
	}
	
	/**
	 *  Finds and verifies text in a field 
	**/
	protected boolean verifyTextOfField(By Field,String verifyText) {
		try{
			GeneralUtility.sleep(5000);
			WebElement textField=findElement(Field);
			if(textField!=null) {
					if(textField.getText()!=null && textField.getText().contains(verifyText))
						return true;
					else 
						return false;
				 }
			}
			catch(Exception e) {
				return false;
			}
		return false;
	}	
	
	/**
	 *  Verifies if element is available on a page
	**/
	public boolean verifyElementExists(By fieldBy, String Label) {
		boolean finalResult=false;
		List<WebElement> varElements=findElements(fieldBy);
		if (varElements.size()>0) {
			for (WebElement ele : varElements) {
				if (ele.getText().equalsIgnoreCase(Label)) {
					finalResult=true;
					break;
				}
			}
			// To include condition for blank label
			if (Label.trim().equalsIgnoreCase(""))
				finalResult=true;
		}
		else 
			finalResult=false;
		return finalResult;
	}	
}
