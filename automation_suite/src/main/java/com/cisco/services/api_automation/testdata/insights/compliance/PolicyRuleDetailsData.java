package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

public class PolicyRuleDetailsData {

    @DataProvider(name = "getUnAuthorizedRequestData")
    public static final Object[][] UnAuthorizedRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyRuleDetail.xlsx", "UnAuthorizedRequest", true, StaticPaths.COMMON_DATA);
    }
    
    @DataProvider(name = "getBadRequestData")
    public static final Object[][] badRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyRuleDetail.xlsx", "BadRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getAllData")
    public static final Object[][] getUseCaseAllData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyRuleDetail.xlsx", "All", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getUseCaseData")
    public static final Object[][] getEmptyData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyRuleDetail.xlsx", "UseCase", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getEmptyResponseData")
    public static final Object[][] getUseCaseData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "PolicyRuleDetail.xlsx", "EmptyResponse", true, StaticPaths.COMMON_DATA);
    }
}
