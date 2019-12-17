package com.stepdefinition;

import org.junit.Assert;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.page.objects.ObjDashBoard;

import cucumber.api.java.en.Then;

public class Beslag extends ReusableLibrary{

	public Beslag(BaseUtil base) {
		super(base);
		// TODO Auto-generated constructor stub
	}
	
	
	@Then("^I select the radio button as '([^\']*)'$")
	public void selectBeslagType(String radioButton)  {

	if(radioButton.equals("Executoriaal"))
		Assert.assertTrue("Click type beslag",
				rf.clickButtonLink(ObjDashBoard.executoriaal));
	else if(radioButton.equals("Conservatoir"))
		Assert.assertTrue("Click type beslag",
				rf.clickButtonLink(ObjDashBoard.conservatoir));
	else if(radioButton.equals("Verkeerd signaal"))
		Assert.assertTrue("Click type beslag",
				rf.clickButtonLink(ObjDashBoard.verkeerdSignaal));
	
	}
	
	
	@Then("^I enter the claim amount as '([^\']*)'$")
	public void enterClaimAmount(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(2000);
		Assert.assertTrue("Search the case Id" + amount
				+ " in filter search box",
				rf.typeInEditBox(ObjDashBoard.claimAmount, amount));
	}
	
	
}
