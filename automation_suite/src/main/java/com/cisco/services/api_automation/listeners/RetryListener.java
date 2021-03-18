package com.cisco.services.api_automation.listeners;

import io.qameta.allure.Allure;
import org.testng.IRetryAnalyzer;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer, ITestNGListener {


    private int retryCount;
    private int maxRetryCount;

    public boolean retry(ITestResult result) {
        maxRetryCount = Integer.parseInt(System.getenv("niagara_retrycount"));
        if (retryCount < maxRetryCount) {
            Allure.step("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            System.out.println("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCount++;
            return true;
        }else {
            return false;
        }
    }

    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }
}
