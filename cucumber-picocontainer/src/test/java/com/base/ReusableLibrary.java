package com.base;

import java.util.Properties;

import com.test.automation.web.client.DBconnection;
import com.test.automation.web.client.RestService;

import org.openqa.selenium.WebDriver;

import com.common.utility.ReusableFunctions;
import com.common.utility.Wait;
import com.stepdefinition.Commonmethods;

public abstract class ReusableLibrary extends BaseUtil{
	
	protected Properties properties;
	protected DBconnection dbConnection;
	protected RestService restService;
	protected Commonmethods comMethod;
	protected Wait wait;
	protected ReusableFunctions rf;
	protected WebDriver driver;
	
	public ReusableLibrary(BaseUtil base){
		this.properties=base.properties;
		this.restService=base.restService;
		this.comMethod=base.comMethod;
		this.dbConnection=base.dbConnection;
		this.wait=new Wait();
		this.rf=new ReusableFunctions();
		this.driver=BaseUtil.Driver;
	}
	
}
