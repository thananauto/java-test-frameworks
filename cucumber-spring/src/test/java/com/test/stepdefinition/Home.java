package com.test.stepdefinition;

import com.test.libraries.Context;
import com.test.libraries.ReusableObject;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class Home extends ReusableObject {


    @When("Enter the text 'Selenium'")
    public void dummy(){
        initBrowser.getDriver().get("http://www.google.com");
    }
}
