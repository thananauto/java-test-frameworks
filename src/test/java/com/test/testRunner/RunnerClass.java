package com.test.testRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = {"com.test.stepdefinition"} ,
        monochrome = true,
        //dryRun = true,
        plugin = {"pretty", "json:target/cucumber-report/cucumber.json"},
        tags = "@Smoke")
public class RunnerClass {


}
