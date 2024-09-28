package com.qa.testng;

import lombok.SneakyThrows;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TestLog implements ITestListener {

    private Logger logger = Logger.getLogger(TestLog.class.getName());

    @SneakyThrows
    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {
        logger.info(result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    @SneakyThrows
    public void onStart(ITestContext context) {
        FileHandler fileHandler = new FileHandler("target/app.log", true);
        logger.addHandler(fileHandler);
    }

    public void onFinish(ITestContext context) {

    }
}
