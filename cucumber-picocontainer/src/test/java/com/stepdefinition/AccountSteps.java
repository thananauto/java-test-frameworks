package com.stepdefinition;

import org.openqa.selenium.By;

import com.base.BaseUtil;
import com.base.ReusableLibrary;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class AccountSteps extends ReusableLibrary {
	

	public AccountSteps(BaseUtil base) {
		super(base);
	}
	
	 @And("^I enter ([^\"]*) and ([^\"]*)$")
	    public void iEnterUsernameAndPassword(String userName, String password) throws Throwable {
	        System.out.println("UserName is : " + userName);
	        System.out.println("Password is : " + password);
	    }

	    @Then("^I should see the userform page wrongly$")
	    public void iShouldSeeTheUserformPageWrongly() throws Throwable {
	    	if(Driver.findElement(By.id("sdfgdsfsd")).isDisplayed()){
	    		return;
	    	}else
	    		throw new PendingException("THis is fo check");
	    		
	    	
	    	//Assert.assertEquals("Its not displayed", driver.findElement(By.id("sdfgdsfsd")).isDisplayed(), true);
	    }

}
