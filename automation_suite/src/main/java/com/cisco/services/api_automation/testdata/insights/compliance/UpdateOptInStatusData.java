package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

public class UpdateOptInStatusData {

    @DataProvider(name = "getBadRequestData")
    public static final Object[][] badRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "UpdateOptInStatus.xlsx", "BadRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getUnAuthorizedRequestData")
    public static final Object[][] UnAuthorizedRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "UpdateOptInStatus.xlsx", "Non-AuthorizedRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getOptInData")
    public static final Object[][] optInData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "UpdateOptInStatus.xlsx", "Opt-In", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getOptOutData")
    public static final Object[][] optOutData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "UpdateOptInStatus.xlsx", "Opt-Out", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getExistingCustomerData")
    public static final Object[][] getExistingCustomerData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "UpdateOptInStatus.xlsx", "Opt-InExistingCustomer", true, StaticPaths.COMMON_DATA);
    }

}
