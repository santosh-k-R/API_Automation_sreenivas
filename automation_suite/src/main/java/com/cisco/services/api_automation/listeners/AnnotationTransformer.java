package com.cisco.services.api_automation.listeners;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
        int maxRetryCount = Integer.parseInt(System.getenv("niagara_retrycount"));
        String[] groups = iTestAnnotation.getGroups();
        if (maxRetryCount > 0) {
            IRetryAnalyzer retry = iTestAnnotation.getRetryAnalyzer();

            if (retry == null) {
                iTestAnnotation.setRetryAnalyzer(RetryListener.class);
            }
        }
        if (groups.length == 0)
            iTestAnnotation.setGroups(new String[]{"regression"});
        else {
            String[] mgroups = Arrays.copyOf(groups, groups.length + 1);
            mgroups[groups.length] = "regression";
            iTestAnnotation.setGroups(mgroups);
        }

    }

}
