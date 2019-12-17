package com.base;

import com.test.automation.web.client.WebDriverFactory;

import org.openqa.selenium.WebDriver;

public class RunnerClass {

	private static RunnerClass runnerClass;
	
	private RunnerClass(){}
	
	public static RunnerClass getInstance(){
	    if(runnerClass == null){
	        synchronized (RunnerClass.class) {
	            if(runnerClass == null){
	            	runnerClass = new RunnerClass();
	            }
	        }
	    }
	    return runnerClass;
	}
	
	public static WebDriver getWebDriver(String browserName){
		return WebDriverFactory.getWebDriver(browserName);
	}
	
	public static WebDriver getRemoteWebDriver(String url, String browserName){
		return WebDriverFactory.getRemoteWebDriver(url, browserName);
	}
	
	
	
}
