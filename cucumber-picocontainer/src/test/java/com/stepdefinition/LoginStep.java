package com.stepdefinition;



//import pages.LoginPage;

import com.base.BaseUtil;
import com.base.ReusableLibrary;

import cucumber.api.java.en.Given;

/*
 * Created by RajasekaranT
 */
public class LoginStep extends ReusableLibrary{


    public LoginStep(BaseUtil base) {
        super(base);
        
    }
    
   /* @When("^get the browser title$")
    public void get_the_browser_title() throws Throwable {
    	System.out.println(loginPage.getTitle());
    }


    @Then("^I should see the userform page$")
    public void iShouldSeeTheUserformPage() throws Throwable {

        Assert.assertEquals("Its not displayed", Driver.findElement(By.id("Initial")).isDisplayed(), true);
    }*/

    @Given("^I navigate to the login page$")
    public void iNavigateToTheLoginPage() throws Throwable {
        System.out.println("Navigate Login Page");
        Driver.navigate().to(properties.getProperty("ram.env.st.url"));
    }
    
    @Given("^I log in with '([^\']*)'$")
    public void iLogIn() throws Throwable {
        System.out.println("Navigate Login Page");
        Driver.navigate().to(properties.getProperty("ram.env.st.url"));
    }


   /* @And("^I click login button$")
    public void iClickLoginButton() throws Throwable {
        //LoginPage page = new LoginPage(driver);
        loginPage.ClickLogin();
    }


    @And("^I enter the following for Login$")
    public void iEnterTheFollowingForLogin(DataTable table) throws Throwable {
        //Create an ArrayList
        List<User> users =  new ArrayList<User>();
        //Store all the users
        users = table.asList(User.class);

        LoginPage page = new LoginPage(Driver);

        for (User user: users){
           page.Login(user.username, user.password);
        }
    }*/

   


    public class User {
        public String username;
        public String password;

        public User(String userName, String passWord) {
            username= userName;
            password = passWord;
        }
    }

}
