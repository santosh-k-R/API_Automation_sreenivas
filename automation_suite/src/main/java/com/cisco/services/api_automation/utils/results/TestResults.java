package com.cisco.services.api_automation.utils.results;

import org.testng.ITestResult;

import java.util.*;
import java.util.stream.Collectors;

public interface TestResults {
    Set<ResultBody> RESULT_BODIES = Collections.synchronizedSet(new HashSet<>());
    boolean POST_TEST_RESULTS = Boolean.parseBoolean(System.getenv("postTestResults"));

    enum Results {
        PASSED("Passed"),
        FAILED("Failed");

        Results(String status) {}
    }

    void post();

    static void add(String id, Results status, String title) {
        RESULT_BODIES.add(new ResultBody(id, status, title));
    }

    static boolean isPostResult() {
        return POST_TEST_RESULTS;
    }

    static void addIfTestIDPassThroughExcel(ITestResult result) {
        try {
            if (result.getParameters().length > 0 && isPostResult()) {
                TestResults.add(String.valueOf(result.getParameters()[0]), getResultStatus(result.getStatus()), String.valueOf(result.getParameters()[1]));
            }
        } catch (Exception e) {
            System.out.println("Not able to Read test ID from Excel");
        }
    }

    static void addIfTestIDPassThroughJSON(ITestResult result) {
        try {
            if (result.getMethod().getDataProviderMethod().getMethod().getReturnType().toString()
                    .equalsIgnoreCase("interface java.util.Iterator") && isPostResult()) {
                String testParams = result.getParameters()[0].toString();
                if (null != testParams) {
                    String[] testIDs = testParams.split(":");
                    TestResults.add(testIDs[0], getResultStatus(result.getStatus()), testIDs[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Not able to Read test ID from JSON");
        }
    }

    static void addIfTestIDIsInAnnotation(ITestResult result) {
        try {
            ID testIds = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(ID.class);
            if (null != testIds && isPostResult()) {
                String[] values = testIds.values();
                if (values.length == 1) {
                    TestResults.add(values[0], getResultStatus(result.getStatus()), result.getMethod().getDescription());
                } else if (values.length > 1 && getResultStatus(result.getStatus()).toString().equalsIgnoreCase("Failed")) {
                    List<String> tests = TestResults.RESULT_BODIES.stream().map(ResultBody::getId).collect(Collectors.toList());
                    for (String value : values) {
                        if (!tests.contains(value))
                            TestResults.add(value, getResultStatus(result.getStatus()), result.getMethod().getDescription());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Not able to Read test ID from ID Annotation");
        }
    }

    static Results getResultStatus(int status) {
        Results resultName = null;
        if (status == 1)
            resultName = Results.PASSED;
        if (status == 2)
            resultName = Results.FAILED;
        return resultName;
    }


}
