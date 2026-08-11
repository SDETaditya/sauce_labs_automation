package com.aditya.test_components;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aditya.resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aditya.BaseTest;

public class Listeners extends BaseTest implements ITestListener {
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTest= new ThreadLocal<ExtentTest>();

     @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        WebDriver localDriver = null;
        try {
            @SuppressWarnings("unchecked")
            ThreadLocal<WebDriver> threadLocalDriver = (ThreadLocal<WebDriver>) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            localDriver = threadLocalDriver.get();
        } catch (Exception e) {

            e.printStackTrace();

        }
        // taking screenshot and attaching it to report

        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), localDriver);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

   

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

}
