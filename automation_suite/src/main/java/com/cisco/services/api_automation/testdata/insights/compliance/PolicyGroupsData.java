package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

public class PolicyGroupsData {

    @DataProvider(name = "getUnAuthorizedRequestData")
    public static final Object[][] UnAuthorizedRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyGroups.xlsx", "Non-AuthorizedRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getBadRequestData")
    public static final Object[][] badRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyGroups.xlsx", "BadRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getPolicyGroupData")
    public static final Object[][] getPolicyGroupData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyGroups.xlsx", "PolicyGroups", true, StaticPaths.COMMON_DATA);
    }

}
