package com.base;

import java.util.Properties;

import com.test.automation.web.client.DBconnection;
import com.test.automation.web.client.RestService;

import org.openqa.selenium.WebDriver;

import com.stepdefinition.Commonmethods;

/**
 * Created by Rajasekaran
 */
public class BaseUtil {

    public static WebDriver Driver;
    public Properties properties;
    public RestService restService;
    public DBconnection dbConnection;
	public Commonmethods comMethod;
	public static int timeOut=20;

}
