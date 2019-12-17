package com.test.automation.web.client;
import com.google.common.base.Strings;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory
{
  
  public static WebDriver getWebDriverFromProperties(String url, String browser, String platformName, DesiredCapabilities passedCapabilities)
  {
    DesiredCapabilities desiredCapabilities = getDesiredCapabilities(passedCapabilities);
    String remoteWebDriverUrl = url;//WebDriverProperties.getWebDriverProperty("remoteWebDriverUrl");
    String browserType = browser;//WebDriverProperties.getWebDriverProperty("browserType");
    String browserVersion = "";//WebDriverProperties.getWebDriverProperty("browserVersion");
    String platform = platformName;//WebDriverProperties.getWebDriverProperty("platform").toUpperCase();
    if (Strings.isNullOrEmpty(remoteWebDriverUrl)) {
      return getWebDriver(browserType, desiredCapabilities);
    }
    if (!Strings.isNullOrEmpty(platform))
    {
      Platform system = Platform.valueOf(platform);

      desiredCapabilities.setPlatform(system);
    }
    if (Strings.isNullOrEmpty(browserVersion)) {
      return getRemoteWebDriver(remoteWebDriverUrl, browserType, desiredCapabilities);
    }
    return getRemoteWebDriver(remoteWebDriverUrl, browserType, browserVersion, desiredCapabilities);
  }
  
  public static WebDriver getRemoteWebDriver(String remoteWebDriverUrl, String browserType, String browserVersion, DesiredCapabilities passedCapabilities)
  {
    DesiredCapabilities desiredCapabilities = getDesiredCapabilities(passedCapabilities);
    desiredCapabilities.setBrowserName(browserType);
    if (!Strings.isNullOrEmpty(browserVersion)) {
      desiredCapabilities.setVersion(browserVersion);
    }
    try
    {
      return new RemoteWebDriver(new URL(remoteWebDriverUrl), desiredCapabilities);
    }
    catch (MalformedURLException e)
    {
      throw new WebDriverException("Could not create remote webdriver. MalformedURLException thrown.", e);
    }
  }
  
  public static WebDriver getWebDriver(String browserType)
  {
    DesiredCapabilities capabilities = getDesiredCapabilities(null);
    WebDriver webDriver;
    switch (browserType)
    {
    case "chrome": 
      webDriver = new ChromeDriver(capabilities);
      break;
    case "safari": 
      webDriver = new SafariDriver(capabilities);
      break;
    case "iexplore": 
    case "internet explorer": 
      webDriver = new InternetExplorerDriver(capabilities);
      break;
    case "firefox": 
    default: 
      System.setProperty("webdriver.firefox.profile", "default");
      webDriver = new FirefoxDriver(capabilities);
    }
    webDriver.manage().window().maximize();
    return webDriver;
  }
  
  public static WebDriver getWebDriver(String browserType, DesiredCapabilities passedCapabilities)
  {
    DesiredCapabilities capabilities = getDesiredCapabilities(passedCapabilities);
    WebDriver webDriver;
    switch (browserType)
    {
    case "chrome": 
      webDriver = new ChromeDriver(capabilities);
      break;
    case "safari": 
      webDriver = new SafariDriver(capabilities);
      break;
    case "iexplore": 
    case "internet explorer": 
      webDriver = new InternetExplorerDriver(capabilities);
      break;
    case "firefox": 
    default: 
      System.setProperty("webdriver.firefox.profile", "default");
      webDriver = new FirefoxDriver(capabilities);
    }
    return webDriver;
  }
  
  public static WebDriver getRemoteWebDriver(String remoteWebDriverUrl, String browserType, DesiredCapabilities passedCapabilities)
  {
    return getRemoteWebDriver(remoteWebDriverUrl, browserType, null, passedCapabilities);
  }
  
  public static WebDriver getWebDriverFromProperties()
  {
    return getWebDriverFromProperties(null, null, null, null);
  }

  
  public static WebDriver getRemoteWebDriver(String url, String browserType)
  {
    return getRemoteWebDriver(url, browserType, null);
  }
  
  private static DesiredCapabilities getDesiredCapabilities(DesiredCapabilities passedCapabilities)
  {
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    if (passedCapabilities != null) {
      desiredCapabilities = passedCapabilities;
    }
    return desiredCapabilities;
  }
  
  public static String getProperty(String key)
  {
    String systemPropertyValue = System.getProperty(key);
    String propertyValue;
    if (systemPropertyValue != null) {
      propertyValue = systemPropertyValue;
    } else {
      propertyValue = getProperties().getProperty(key);
    }
    if (propertyValue == null) {
      throw new NotFoundException(String.format("Could not find property %s in webdriver.properties", new Object[] { key }));
    }
    return propertyValue;
  }
  
  public static Properties getProperties()
  {
    Properties properties = new Properties();
    URL props = WebDriverFactory.class.getResource("/webdriver.properties");
    if (props == null) {
      throw new NotFoundException("Could not find webdriver.properties in classpath");
    }
    try
    {
      properties.load(props.openStream());
    }
    catch (IOException e)
    {
      throw new RuntimeException("Could not load webdriver.properties from classpath. IO exception thrown", e);
    }
    return properties;
  }
}
