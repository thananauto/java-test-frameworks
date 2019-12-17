package com.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.test.automation.web.client.DBconnection;
import com.test.automation.web.client.RestService;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.stepdefinition.Commonmethods;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Created by RajasekaranT on 10/02/2018.
 */
public class Hook extends BaseUtil {

	private BaseUtil base;
	private static boolean skipRestOfScenarios = false;
	static Logger LOG = Logger.getLogger(Hook.class.getName());

	public Hook(BaseUtil base) {
		this.base = base;
	}


	@Before
	public void InitializeTest(Scenario scenario) throws Exception {

		LOG.info("Scenario Name: "+scenario.getName()+" starts here.....");
		//get the env properites file: by default it takes ST as execution environment
				String env=new PropertiesLoader().loadProperties("runner.properties").getProperty("ram.env","st").toLowerCase();
				String exeMode=new PropertiesLoader().loadProperties("runner.properties").getProperty("ram.execution.mode","local").toLowerCase();


				
				//set the properties
		base.properties=new PropertiesLoader().loadProperties("config/"+env+"/application.properties");
		if(exeMode.equalsIgnoreCase("remote")){
			base.properties.setProperty("ram.local.flag", exeMode);
			String remoteUrl=new PropertiesLoader().loadProperties("runner.properties").getProperty("ram.remote.url").toLowerCase();
			base.properties.setProperty("ram.remote.url", remoteUrl);
			
		}
		//initiate the rest service
		String serUserName=base.properties.getProperty("ram.username");
		String serPassword=base.properties.getProperty("ram.password");
		
		
		base.restService=new RestService(serUserName, serPassword);
		
		//set the DB instance
		
		base.dbConnection=new DBconnection();
		base.dbConnection.setDbRAMUrl(base.properties.getProperty("ram.db.url"));
		base.dbConnection.setRamUserName(base.properties.getProperty("ram.db.username"));
		base.dbConnection.setRamPassWord(base.properties.getProperty("ram.db.password"));
		base.dbConnection.setDbDTBUrl(base.properties.getProperty("dtb.db.url"));
		base.dbConnection.setDtbUserName(base.properties.getProperty("dtb.db.username"));
		base.dbConnection.setDtbPassword(base.properties.getProperty("dtb.db.password"));
		base.dbConnection.setDbBIXUrl(base.properties.getProperty("ram.bix.url"));
		base.dbConnection.setBixUserName(base.properties.getProperty("ram.bix.username"));
		base.dbConnection.setBixPassword(base.properties.getProperty("ram.bix.password"));
	
		// Chrome driver
		//&& base.properties.getProperty("ram.env.execution.mode").equals("local")
		
		
		//initialise the commonMethod
		base.comMethod=new Commonmethods(base);

		//Skip the rest of the test methods if previous scenario is failed
		if(skipRestOfScenarios && false)
			throw new RuntimeException("The previous scenarios is failed, so skipping the rest of scenarios for the tag "+scenario.getSourceTagNames());
		
	}

	@After
	public void TearDownTest(Scenario scenario) {
		
		LOG.info(scenario.getName()+" ends here.....");
		if(BaseUtil.Driver!=null){
		if (scenario.isFailed()) {
			 try {
		            File scrFile = ((TakesScreenshot) BaseUtil.Driver)
							.getScreenshotAs(OutputType.FILE);
					byte[] data = FileUtils.readFileToByteArray(scrFile);
					scenario.embed(data, "image/png");
			    } catch (Exception e) {
			        e.printStackTrace();
			    }

			//set the skipRestOfScenarios to 'true', so it would skip the scenarios
			skipRestOfScenarios = true;
		}

		try {
			BaseUtil.Driver.switchTo().defaultContent();
		} catch (Exception e) {
			
		}
		}

	}

	public String getTimeStamp() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}
	
	
	public Properties getProperties(Properties prop){
		InputStream input = null;

		try {

			prop.getProperty("ram.env", "lst");
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;

	  
	}

}
