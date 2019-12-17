package com.test.libraries;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {

    private Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);

    private String browserName;

    public String getRemoteWebdriverUrl() {
        return remoteWebdriverUrl;
    }

    public void setRemoteWebdriverUrl(String remoteWebdriverUrl) {
        this.remoteWebdriverUrl = remoteWebdriverUrl;
    }

    private String remoteWebdriverUrl;

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }



    public WebDriver getWebDriver(){

        if(getBrowserName().equalsIgnoreCase("chrome")){
          //  WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.driver","chromedriver.exe");
            return new ChromeDriver();
        }

        else
            return new FirefoxDriver();
    }
}
