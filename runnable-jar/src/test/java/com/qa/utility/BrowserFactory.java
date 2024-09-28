package com.qa.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

    public static WebDriver getBrowser(BrowserType browserType){

        if(browserType.equals(BrowserType.CHROME)){
            return new ChromeDriver();
        }else if(browserType.equals(BrowserType.FIREFOX)){
            return new FirefoxDriver();
        }
        else if(browserType.equals(BrowserType.EDGE)){
            return new EdgeDriver();
        }

        return null;

    }
}
