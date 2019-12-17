package com.common.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.github.bonigarcia.wdm.WebDriverManagerException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.base.BaseUtil;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableFunctions {
	
	//private WebDriver driver;
	
	/*public ReusableFunctions(WebDriver driver){
		this.driver=driver;
	}
	*/
	static Logger LOG = Logger.getLogger(ReusableFunctions.class.getName());
	public boolean clickButtonLink( By by){
		
		try {
			BaseUtil.Driver.findElement(by).click();
			return true;
		} catch (Exception e) {
			LOG.error("Clicking on button for the properties "+by,e);
			return false;
		}
	}
	
	public boolean typeInEditBox( By by, String text){
			
			try {
				BaseUtil.Driver.findElement(by).sendKeys(text);
				return true;
			} catch (Exception e) {
				LOG.error("edit in text box for the properties "+by,e);
				return false;
			}
		}
	
	
	public boolean selectByText( By by, String text){
		try {
			Select select=new Select(BaseUtil.Driver.findElement(by));
			select.selectByVisibleText(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean selectByValue( By by, String text){
		try {
			Select select=new Select(BaseUtil.Driver.findElement(by));
			select.selectByValue(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean WaitForSpinner(WebDriver driver)
		{
		WebDriverWait spinnerWait = new WebDriverWait(driver,15);
		try {
			spinnerWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#pega_ui_load")));
			spinnerWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#pega_ui_load")));
			return true;
		} catch (Exception e) {
			try {
				spinnerWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#pega_ui_load")));
					return true;
			} catch (Exception e1) {
				return false;
			}

		}

	}
	
	
	public List<WebElement> getAllValuesInList(By by){
		
		if(isElementDisplayed(by)){
		Select select=new Select(BaseUtil.Driver.findElement(by));
			return select.getOptions();
		}else
			return null;
	}
	
	public String getcaseIDusingFacId(HashMap<String, String> map, String key){
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
	        
	        if(pair.getKey().equals(key))
	        	return pair.getValue();
	    }
		
		return null;
		
	}
	
	
	public String getText(By by){
		if(isElementDisplayed(by))
			return BaseUtil.Driver.findElement(by).getText();
		else
			return "element not displayed......";
	}
	
	
	public boolean isElementDisplayed(By by){
		
		
		try {
			BaseUtil.Driver.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
