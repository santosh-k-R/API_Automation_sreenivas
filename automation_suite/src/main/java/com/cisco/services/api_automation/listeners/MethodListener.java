package com.cisco.services.api_automation.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class MethodListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    /**
     * To be implemented if the method needs a handle to contextual information.
     *
     * @param method
     * @param testResult
     * @param context
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
//        context.set
    }

    /**
     * To be implemented if the method needs a handle to contextual information.
     *
     * @param method
     * @param testResult
     * @param context
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        String testName = (String) context.getAttribute("testName");
        if (null != testName) {
            AllureLifecycle allureLifecycle = Allure.getLifecycle();
            allureLifecycle.updateTestCase(res -> res.setName(testName));
        }
    }
}
