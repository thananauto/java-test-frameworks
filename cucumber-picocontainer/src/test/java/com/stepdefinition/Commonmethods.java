package com.stepdefinition;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.test.automation.web.client.WebDriverFactory;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.base.BaseUtil;

import com.base.PropertiesLoader;
import com.base.ReusableLibrary;
import com.codeborne.selenide.WebDriverRunner;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;


public class Commonmethods  extends ReusableLibrary{

public Commonmethods(BaseUtil base) {
		super(base);
	}

public File getFile(String fileName){
	
	//Get file from resources folder
	 ClassLoader classLoader = new PropertiesLoader().getClass().getClassLoader();
      File file = new File(classLoader.getResource(fileName).getFile());
	return file;
	
}

@Then("^Log into the RAM portal with admin rights with username '([^\"]*)' and password '([^\"]*)'$")
public void loginUser(String username, String password) throws InterruptedException{
	
	//String username = "automation.unittest";
	//String password = "rules";
	
	if(hasValid(BaseUtil.Driver)){
		System.setProperty("webdriver.chrome.driver",
				properties.getProperty("ram.local.driver.path"));
		boolean flag=Boolean.parseBoolean(properties.getProperty("ram.local.flag"));
		
			if (flag) {
				BaseUtil.Driver = WebDriverFactory.getWebDriver(properties
						.getProperty("ram.local.browser"));
				WebDriverRunner.setWebDriver(BaseUtil.Driver);
			}
			else {
				BaseUtil.Driver = WebDriverFactory.getWebDriverFromProperties(properties.getProperty("ram.remote.url"),
						properties.getProperty("ram.remote.browser"), properties.getProperty("ram.remote.platform"), null);
				WebDriverRunner.setWebDriver(BaseUtil.Driver);
			}
		}

	String url=properties.getProperty("ram.url").trim();
	
	BaseUtil.Driver.get(url);
	
	Thread.sleep(3000);


	Driver.findElement(By.cssSelector("#txtUserID")).sendKeys(username);
	Driver.findElement(By.cssSelector("#txtPassword")).sendKeys(password);
	Driver.findElement(By.cssSelector(".loginButton")).click();
	
	/*WebElement user = Driver.switchTo().activeElement();
	user.sendKeys(username);
	WebElement nextField = Driver.switchTo().activeElement();
	nextField.sendKeys(Keys.TAB);
	WebElement pass = Driver.switchTo().activeElement();
	pass.sendKeys(password);
	WebElement login = Driver.switchTo().activeElement();
	login.sendKeys(Keys.ENTER);*/
}

@Then("^Launch the browser with test cookie '([^\']*)'$")
public void openUrl(String cookie){
	
	if(hasValid(BaseUtil.Driver)){
		System.setProperty("webdriver.chrome.driver",
				properties.getProperty("ram.local.driver.path"));
		boolean flag=Boolean.parseBoolean(properties.getProperty("ram.local.flag"));
		
			if (flag) {
				BaseUtil.Driver = WebDriverFactory.getWebDriver(properties
						.getProperty("ram.local.browser"));
				WebDriverRunner.setWebDriver(BaseUtil.Driver);
			}
			else {
				BaseUtil.Driver = WebDriverFactory.getWebDriverFromProperties(properties.getProperty("ram.remote.url"),
						properties.getProperty("ram.remote.browser"), properties.getProperty("ram.remote.platform"), null);
				WebDriverRunner.setWebDriver(BaseUtil.Driver);
			}
		}

	String url=properties.getProperty("application.url").trim();
	
	//get the cookie
	if(cookie!=null){
		BaseUtil.Driver.get("add the url");
	}
	
	boolean loadFlag=wait.waitForJStoLoad(15);
	if(loadFlag){
	if(url!=null && !url.equals(""))
		BaseUtil.Driver.get(url);
	
	//wait for page to fully loaded
	Assert.assertTrue("Application launched successfully", wait.waitForTitle("Regular Asset Management", 30));
	
	}else
		Assert.assertTrue("Cookie "+cookie+" was not set", loadFlag);
	
	//added the implicit wait timeout
	BaseUtil.Driver.manage().timeouts().implicitlyWait(BaseUtil.timeOut, TimeUnit.SECONDS);
	
}

public boolean hasValid(WebDriver driver) {
	
	if(driver==null)
		return true; 
	else
		return ((RemoteWebDriver)driver).getSessionId() == null;
}


@Given("^I am log off the browser$")
public void i_am_log_off_the_browser() {
	
	if(BaseUtil.Driver!=null)
		BaseUtil.Driver.quit();
		//null the driver value make sure it uses in other scenarios
		//BaseUtil.Driver=null;
		// Write code here that turns the phrase above into concrete actions
}
}
