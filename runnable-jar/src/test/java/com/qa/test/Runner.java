package com.qa.test;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        dryRun = false,
        glue = {"com.qa.test", "com.qa.steps", "com.qa.utility", "com.qa.page.objects"},
        monochrome = true,
        features = "classpath:features",
        tags = "@all",
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/report/cucumber.json", "html:target/report/summary.html",
                "testng:target/report/ouput.xml"}
)
public class Runner extends AbstractTestNGCucumberTests {
}
