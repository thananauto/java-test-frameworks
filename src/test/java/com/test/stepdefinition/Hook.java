package com.test.stepdefinition;

import com.test.libraries.Context;
import com.test.libraries.MyListener;
import com.test.libraries.ReusableObject;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook extends ReusableObject {


    @Before
    public void loadDefinitions(){
       // initBrowser.getDriver();


    }

    @After
    public void exit(){


    }



}
