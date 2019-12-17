package com.test.stepdefinition;

import com.test.libraries.Context;
import com.test.libraries.ReusableObject;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class Logout extends ReusableObject {


    @When("Enter the text 'amazon'")
    public void dummy1(){

        initBrowser.getDriver().get("http://www.amazon.com");
    }
}
