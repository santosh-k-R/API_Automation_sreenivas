package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

public class OptInDetailData {

    @DataProvider(name = "getUnAuthorizedRequestData")
    public static final Object[][] UnAuthorizedRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "OptInDetail.xlsx", "Non-AuthorizedRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getBadRequestData")
    public static final Object[][] badRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "OptInDetail.xlsx", "BadRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getNotEnrolledCustomerData")
    public static final Object[][] getNotEnrolledCustomerData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "OptInDetail.xlsx", "NotEnrolledCustomer", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getOptInData")
    public static final Object[][] getOptInData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "OptInDetail.xlsx", "Opt-In", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getOptOutData")
    public static final Object[][] getOptOutData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "OptInDetail.xlsx", "Opt-Out", true, StaticPaths.COMMON_DATA);
    }


}
