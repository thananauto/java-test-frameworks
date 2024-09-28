package com.qa.steps;

import com.qa.page.objects.SauceHome;
import com.qa.utility.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class UIDefinition {
    private Context reusableLibrary;
    private SauceHome sauceHome ;
   public UIDefinition(Context reusableLibrary) {
        this.reusableLibrary = reusableLibrary;
        this.sauceHome = reusableLibrary.getHome();
    }

    @Given("Launch the application")
    public void launch_the_application() {
        sauceHome.launchUrl(reusableLibrary.getConfig().url());
    }
    @Given("Enter the username as {string}")
    public void enter_the_username_as(String string) {
        sauceHome.enterUser(string);
    }
    @Given("Enter the password as {string}")
    public void enter_the_password_as(String string) {
        sauceHome.enterPassword(string);
    }
    @When("Click on Login button")
    public void click_on_login_button() {
        sauceHome.clickLogin();
    }
    @Then("User should be in {string} page")
    public void user_should_be_in_page(String string) {
        Assert.assertEquals(sauceHome.validateHeader(), true, "Page should be in landing page");
    }

}
