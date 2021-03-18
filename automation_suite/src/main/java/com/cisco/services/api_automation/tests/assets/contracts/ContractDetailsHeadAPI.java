package com.cisco.services.api_automation.tests.assets.contracts;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Contract Details Head API")
public class ContractDetailsHeadAPI {

	Response responseAPI = null;
	Response responseES = null;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	String apiKey = "contract_details";
	String endPoint;
	
	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_HEAD_APIS.get(apiKey).getEndPointUrl();

	}
	
	@Test
	public void api200ResponseValidation() throws Exception {
		System.out.println("****************** 200 Response Validation for API " + endPoint);
		try {
			responseAPI = CommonTestAcrossAPIsIT.successResponse(endPoint);
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	}
}
