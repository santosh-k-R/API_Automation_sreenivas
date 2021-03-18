package com.cisco.services.api_automation.testdata.cases;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

import java.util.Map;

public class CasesData {

    private static final String DIR = getDIR();
    public static Map<String, String> COMMON_DATA = Commons.getTestData(DIR + "Common.xlsx", "common_data", false);

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR + "Cases" + PATH_SEPARATOR ;
        return DIR;
    }

    @DataProvider(name = "CasesStaticData", parallel = true)
    public Object[][] CasesStaticData() {
        return Commons.getTestData(DIR + "Cases.xlsx", "cases", true, COMMON_DATA);
    }

    @DataProvider(name = "SupportMetricsStaticData")
    public Object[][] SupportMetricsStaticData() {
        return Commons.getTestData(DIR + "Cases.xlsx", "supportMetrics", true, COMMON_DATA);
    }

    @DataProvider(name = "EntitlementStaticData")
    public Object[][] EntitlementStaticData() {
        return Commons.getTestData(DIR + "Cases.xlsx", "entitlement", true, COMMON_DATA);
    }

    @DataProvider(name = "SearchStaticData", parallel = true)
    public Object[][] SearchStaticData() {
        return Commons.getTestData(DIR + "Cases.xlsx", "search", true, COMMON_DATA);
    }

}
