package com.topcoder.General;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeneralUtility {
	
	/**
	* Forces the thread to sleep for time specified in the variable
	**/		
	public static void sleep() {
	    	try 
	    	{
				Thread.sleep(10000);
			} 
	    	catch(Exception e) {
				e.printStackTrace();
			}
		 }
		
	/**
	 *  Open a URL in the browser launched
	**/
	public static boolean openUrl(WebDriver driver, String url){
		try{
		if (url.startsWith("/"))	
			url=driver.getCurrentUrl()+url.substring(1);
	    driver.get(url);
	    Thread.sleep(5000);
	   }
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return true;
	}
	
	/**
	* Forces the thread to sleep for time specified in the variable 
	**/	
	public static void sleep(int sleeptime) {
			try {
				Thread.sleep(sleeptime);
			} 
	    	catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	/**
	* Takes screenshot of a page
	**/	
	public static void TakeScreenshot(String filePath, WebDriver driver) throws FileNotFoundException {
		filePath = filePath + "_" + GenerateDateString() + ".png";
		try {
			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShot, new File(filePath));
		}
		catch (IOException e) {
	        throw new FileNotFoundException(
	                "Error in processing path for image files");
		}
	}
	
	/**
	 * Switches from one browser window to another on the basis of the specified title
	**/
	public static boolean switchToWindowUsingTitle(WebDriver driver, String title) { 
	     String currentWindow = driver.getWindowHandle(); 
	     Set<String> availableWindows = driver.getWindowHandles(); 
	     if (!availableWindows.isEmpty())  { 
	         for (String windowId : availableWindows) { 
	        	 if (driver.switchTo().window(windowId).getTitle().contains(title) || driver.switchTo().window(windowId).getCurrentUrl().contains(title)) {   
	        		 return true; 
	             } 
	        	 else { 
	                     driver.switchTo().window(currentWindow); 
	             } 
	         } 
	     } 
	     return false; 
	 } 
		
	/**
	 * Closes a window with the specified title
	 **/
	public static boolean verifyBrowserWindowUsingTitleOrURL(WebDriver driver, String title) { 
	     boolean windowExist=false;
	     String currentWindow = driver.getWindowHandle(); 
	     Set<String> availableWindows = driver.getWindowHandles(); 
	     if (!availableWindows.isEmpty()) { 
	         for (String windowId : availableWindows) { 
	        	 driver.switchTo().window(windowId);
	             if (driver.getTitle().contains(title) || driver.getCurrentUrl().contains(title)) { 
	            	 windowExist=true; 
	            	 driver.close();
	            	 break;
	             } 
	             
	         } 
	     } 
	     driver.switchTo().window(currentWindow);
	     return windowExist; 
	 } 
		
	/**
	 * Generates date in string format 
	 **/
	public static String GenerateDateString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		String dateStr = sdf.format(cal.getTime());
		return dateStr;
	}	
		
	/**
	 *  Gets current date in the specified timezone 
	 **/
	public static Date getCurrentDateInATimezone(String timezone, String strFormat) {
		try {
		 Date date = new Date();  
		 DateFormat formatter = new SimpleDateFormat(strFormat);
		 // Set the formatter to use a different timezone  
		 formatter.setTimeZone(TimeZone.getTimeZone(timezone));  
	     return formatter.parse(formatter.format(date));
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;       
	}
	
	/**
	 *  Converts a string value to date 
	**/
	public static Date convertStringtoDate(String dateInString,String format,String timezone) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			   TimeZone obj = TimeZone.getTimeZone(timezone);
	           formatter.setTimeZone(obj);
	           Date theResult = formatter.parse(dateInString);
			   return formatter.parse(formatter.format(theResult));
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  Method to remove a character from a string from a particular position in the string 
	 **/
	 public static String removeCharAt(String s, int pos) {
		 if(pos>=0)
			 return s.substring(0, pos) + s.substring(pos + 1);
		 else
			 return s;
	 }
	
	/**
	 * Browse and upload a file from local machine 
	**/
	public static boolean uploadFile(WebDriver driver, String fileToUpload) {
		 StringSelection ss= new StringSelection(fileToUpload);
		 Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
		 Robot r;
			try {	 
			r = new Robot();
			 r.keyPress(KeyEvent.VK_ENTER);    
			 r.keyRelease(KeyEvent.VK_ENTER);
			 r.keyPress(KeyEvent.VK_CONTROL);
			 r.keyPress(KeyEvent.VK_V);
			 r.keyRelease(KeyEvent.VK_V);
			 r.keyRelease(KeyEvent.VK_CONTROL);
			 r.keyPress(KeyEvent.VK_ENTER);
			 r.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		 return true;
	}
	
	/**
	 *  Closes current browser tab 
	 **/
	public static WebDriver closeCurrentBrowserTab(WebDriver driver, String title) { 
	     String currentWindow = driver.getWindowHandle(); 
	     Set<String> availableWindows = driver.getWindowHandles(); 
	     if (!availableWindows.isEmpty()) { 
	         for (String windowId : availableWindows) { 
	        	 if (driver.switchTo().window(windowId).getTitle().contains(title)|| driver.switchTo().window(windowId).getCurrentUrl().contains(title)) { 
	            	driver.close();
	                break;
	             } 
	        }
	         driver.switchTo().window(currentWindow); 
	     } 
	     return driver; 
	} 
	
	/**
	* Takes to previous page 
	**/
	public static boolean goToPreviousPage(WebDriver driver) {
		try {
		driver.navigate().back();
		Thread.sleep(5000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
		
	/**
	 * Given a web driver checks if more than one window present or not, If present shift controls to popUp WIndow
	 */
	public ArrayList<String> popUpWindowHandler(WebDriver driver){
		ArrayList<String> tabsHandles = new ArrayList<String> (driver.getWindowHandles());
		if(tabsHandles.size() > 1){
			driver.switchTo().window(tabsHandles.get(1));
		}
		return tabsHandles;	
	}
		
	/**
	* Given a web driver and list of window handles, checks if more than one windows present or not, 
	* If present close the popUp window and shift controls to main Window
	**/
	public void popUpWindowClose(WebDriver driver, ArrayList<String> TabsHandles) {
		if(TabsHandles.size() > 1){
			driver.close();
			driver.switchTo().window(TabsHandles.get(0));
			}
	}
	
	/**
	 * @param The parameter is of type string. The name of file whose existence is to be verified, 
	 * with type extension.
	 * Given the file name check whether its presents or not.
	 */
	public static boolean isFilePresent(String fileName){
		File fileToCheck = new File(fileName);
		return (fileToCheck.exists() && !fileToCheck.isDirectory())?true:false;
	}
	
	/**
	 *  Verifies title of the page 
	**/
	protected boolean verifyPageTitle(WebDriver driver,String pagetitle) {
		try{
			if(driver.getTitle().equals(pagetitle))
				return true;
			else
				return false;
			}
			catch(Exception e){
				return false;
			}
	}	
	
}
	
	


