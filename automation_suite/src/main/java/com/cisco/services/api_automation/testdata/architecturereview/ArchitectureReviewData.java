package com.cisco.services.api_automation.testdata.architecturereview;

import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;

import java.util.Map;

public class ArchitectureReviewData {

    private static final String DIR = getDIR();

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR
                + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR ;
        return DIR;
    }

    //*************************************Architecture Review Data******************************************************//
    
    public static final Map<String, String> AR_DNAC_COUNT_API = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DnacCountAPI", false);
    public static final Map<String, String> AR_DNAC_DETAILS_API = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DnacDetailsAPI", false);
    public static final Map<String, String> AR_DEVICES_COUNT_API = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DevicesCountAPI", false);
    public static final Map<String, String> AR_SDA_TREND_API = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "SDATrendsAPI", false);
    public static final Map<String, String> AR_DEVICE_COMPLIANCE = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DeviceComplianceAPI", false);
    public static final Map<String, String> AR_DEVICE_DETAILS = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DeviceDetails", false);
    public static final Map<String, String> AR_DEVICE_INSIGHT = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DeviceInsight", false);
    public static final Map<String, String> AR_DEVICE_NONOPTIMALLINK = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "DeviceNonOptimalLink", false);

    public static final Map<String, String> CONFIGURATION_CBP_RULE_COUNT = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "CBPRuleCount", false);
    public static final Map<String, String> CONFIGURATION_CBP_RULE = Commons.getTestData(DIR + "ArchitectureReview.xlsx", "CBPRule", false);

    
    @DataProvider(name = "invalidCustomer")
    public static final Object[][] searchTerms() {
        return ExcelReader.readTestData(DIR + "ArchitectureReview.xlsx", "invalidCustomer", true);
    }
}
