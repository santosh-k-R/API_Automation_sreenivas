package com.cisco.services.api_automation.tests.assets.assetgrouping;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Asset Group")
public class AssetGroup extends BeforeTestSuiteClassIT{

	Response responseAPI = null;
	Response responseES = null;
	int expectedStatusCode = 200;
	String apiKey = "asset_group";
	String endPoint;
	
	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();

	}
	
	@Test
	public void api200ResponseValidation() {
		System.out.println("****************** 200 Response Validation for API " + endPoint);
		try {
			responseAPI = CommonTestAcrossAPIsIT.successResponse(endPoint);
			assertEquals(responseAPI.getStatusCode(), expectedStatusCode,
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
		} catch (Exception e) {
			Assert.fail("Exception Occured :  " + e.getMessage());
		}
	}
}
