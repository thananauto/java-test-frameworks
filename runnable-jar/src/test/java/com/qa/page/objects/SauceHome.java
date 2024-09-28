package com.qa.page.objects;

import com.qa.utility.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceHome {
  private WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id="login-button")
    private WebElement loginButton;

    @FindBy(className = "app_logo")
    private WebElement appLogo;

    public SauceHome(Context driver) {
        this.driver = driver.getDriver();
        PageFactory.initElements(driver.getDriver(),this);
    }

    public void launchUrl(String url){
        driver.get(url);
    }

    public void enterUser(String user){
        username.sendKeys(user);
    }
    public void enterPassword(String pwd){
        password.sendKeys(pwd);
    }

    public void clickLogin(){
        loginButton.click();
    }

    public boolean validateHeader(){
        return appLogo.isDisplayed();
    }
}
