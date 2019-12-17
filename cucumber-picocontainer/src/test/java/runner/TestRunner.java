package runner;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Created by RajasekaranT
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"} , 
glue = {"com.test.automation", "com.base", "com.test.automation.gict.web.client","pages"},
tags={"@WIP"},// use junit runner for executing specific tags
//dryRun = true,
monochrome=true,
plugin={"pretty","json:target/cucumber-report/cucumber.json"})
public class TestRunner {

	
	
	
}
