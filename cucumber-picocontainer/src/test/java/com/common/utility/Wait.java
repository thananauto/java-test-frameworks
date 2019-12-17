package com.common.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.BaseUtil;

public class Wait {
	
	/**
	 * 
	 * @param driver
	 * @return
	 */
	public boolean waitForJStoLoad(int sec) {

		WebDriverWait wait = new WebDriverWait(BaseUtil.Driver, sec);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					return ((Long) js.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				return js.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
	
	
	public boolean waitForTitle(String title,int sec){
		WebDriverWait wait=new WebDriverWait(BaseUtil.Driver, sec);
		return wait.until(ExpectedConditions.titleContains(title));
	}
	
	public boolean waitForElementVisible(int sec, By by){
		
		try {
			WebDriverWait wait = new WebDriverWait(BaseUtil.Driver, sec);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
