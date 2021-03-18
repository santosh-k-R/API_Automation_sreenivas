package com.cisco.services.api_automation.tests.assets.feature;

import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Feature Assets APIs")
public class FeatureAssetViewDetails extends BeforeTestSuiteClassIT {
    Response responseAPI = null;
    Response responseES = null;
    Response preReqApiResponse = null;
    Boolean preRequisiteAPIRan = false;
    SoftAssert softAssert = new SoftAssert();
    String expectedStatusCode = "200";
    long expectedResponseTime = 3000;
    String apiKey="feature_asset_view";
    String endPoint;

    @BeforeClass
    public void setup() {
        endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
    }

    @Test(description = "/features/v1/features API 200 Response Validation")
    public void api200ResponseValidation() throws Exception {
        System.out.println("****************** 200 Response Validation for API "+endPoint );
        try {
        	System.out.println("testing");
        	System.out.println(headers);
            responseAPI= CommonTestAcrossAPIsIT.successResponse(endPoint);
            softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
                    "Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
            softAssert.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
        }
    }
}
