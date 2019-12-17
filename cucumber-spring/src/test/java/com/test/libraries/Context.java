package com.test.libraries;

import org.openqa.selenium.WebDriver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class Context {
    private WebDriver driver;
    private JdbcTemplate jdbcTemplate;



    public void  setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriverFactory driverFactory) {
               this.driver = driverFactory.getWebDriver();
         }

    public void quitDriver(){
        if(driver!=null)
            driver.quit();


    }





}
