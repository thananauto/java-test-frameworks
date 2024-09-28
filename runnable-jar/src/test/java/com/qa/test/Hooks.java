package com.qa.test;

import com.qa.utility.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.aeonbits.owner.ConfigFactory;

import java.time.Duration;
import java.util.Objects;

public class Hooks {
    private Context context;
    public Hooks(Context context){
        this.context = context;
    }

    @Before
    public void setUp(Scenario scenario){

        //get the configuration
        context.setConfig(ConfigFactory.create(RunnerConfig.class));
        context.setDriver(BrowserFactory.getBrowser(BrowserType.CHROME));
        context.setSauceHome(context);

        context.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @After
    public void tearDown(){
       if(Objects.nonNull(context.getDriver())){
           context.getDriver().quit();
       }
    }
}
