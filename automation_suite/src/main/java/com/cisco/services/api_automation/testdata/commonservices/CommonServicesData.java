package com.cisco.services.api_automation.testdata.commonservices;

import com.cisco.services.api_automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class CommonServicesData {

    private static final String DIR = getDIR();

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR
                + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR ;
        return DIR;
    }
	
    @DataProvider(name = "entitlementApiTestCases", parallel = false)
    public static final Object[][] loadEntitlementApiTestCases() {
        return ExcelReader.readTestData(DIR + "CommonServices.xlsx", "Entitlement-API", true);
    }
    
    
    @DataProvider(name = "feedbackApiTestCases", parallel = false)
    public static final Object[][] loadFeedbackApiTestCases() {
        return ExcelReader.readTestData(DIR + "CommonServices.xlsx", "Feedback-API", true);
    }

    @DataProvider(name = "notificationsApiTestCases", parallel = false)
    public static final Object[][] loadNotificationsApiTestCases() {
        return ExcelReader.readTestData(DIR + "CommonServices.xlsx", "Notifications-API", true);
    }
    
}
