package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;


public class AssetDetailsFilterData {

    @DataProvider(name = "getBadRequestData")
    public static final Object[][] badRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AssetDetailsFilter.xlsx", "BadRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getUnAuthorizedRequestData")
    public static final Object[][] UnAuthorizedRequestData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AssetDetailsFilter.xlsx", "UnAuthorizedRequest", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getAllData")
    public static final Object[][] getUseCaseAllData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AssetDetailsFilter.xlsx", "All", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getUseCaseData")
    public static final Object[][] getEmptyData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AssetDetailsFilter.xlsx", "UseCase", true, StaticPaths.COMMON_DATA);
    }

    @DataProvider(name = "getEmptyResponseData")
    public static final Object[][] getUseCaseData() {
        System.out.println("Inside Data Provider : ");
        return Commons.getTestData(StaticPaths.getDir() + "AssetDetailsFilter.xlsx", "EmptyResponse", true, StaticPaths.COMMON_DATA);
    }

}
