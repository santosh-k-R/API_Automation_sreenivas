package com.cisco.services.api_automation.listeners;

import com.cisco.services.api_automation.utils.results.ID;
import com.cisco.services.api_automation.utils.results.ResultBody;
import com.cisco.services.api_automation.utils.results.TestResults;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;
import java.util.stream.Collectors;

public class TestListener implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
        //unused
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has passed");
        TestResults.addIfTestIDIsInAnnotation(result);
        TestResults.addIfTestIDPassThroughJSON(result);
        TestResults.addIfTestIDPassThroughExcel(result);
    }


    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has failed");
        TestResults.addIfTestIDIsInAnnotation(result);
        TestResults.addIfTestIDPassThroughJSON(result);
        TestResults.addIfTestIDPassThroughExcel(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has been skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //unused
    }

    @Override
    public void onStart(ITestContext context) {
        //unused
    }

    @Override
    public void onFinish(ITestContext context) {
        //unused
    }
}
