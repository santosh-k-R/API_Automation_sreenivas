package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

public class StaticTestData {

    @DataProvider(name = "optInDetailAPIData")
    public static final Object[][] optInDetailAPIData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AdminPageAPIData.xlsx", "OptInDetail", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "policyGroupAPIData")
    public static final Object[][] policyGroupAPIData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AdminPageAPIData.xlsx", "Policy Group", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "updateOptInAPIData")
    public static final Object[][] updateOptInAPIData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AdminPageAPIData.xlsx", "Update Opt-In", true, StaticPaths.COMMON_DATA);
    }

    /**
     * Landing Page Filter APIs Static Data
     */

    @DataProvider(name = "violationFiltersAPIData")
    public static final Object[][] violationFiltersAPIData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "FilterAPIsData.xlsx", "Violation-Filters", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "assetFiltersAPIData")
    public static final Object[][] assetFiltersAPIData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "FilterAPIsData.xlsx", "Asset-Filters", true, StaticPaths.COMMON_DATA);
    }

}
