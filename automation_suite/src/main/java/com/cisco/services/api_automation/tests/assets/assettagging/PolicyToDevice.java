package com.cisco.services.api_automation.tests.assets.assettagging;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Asset Tagging APIs")
public class PolicyToDevice extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="policy_to_device";
	String endPoint;
	String table;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_TAG_APIS.get(apiKey).getEndPointUrl();
	}

	@Test(description = "/tags/v1/policy-to-device-api API 200 Response Validation")
	public void api200ResponseValidation() throws Exception {
		System.out.println("****************** 200 Response Validation for API "+endPoint );
		try {
			CommonTestAcrossAPIsIT commonTestAcrossAPIsIT = new CommonTestAcrossAPIsIT();
			responseAPI = commonTestAcrossAPIsIT.invokePolicyToDeviceAPIsAndValidateResponseCodeAndResponseTime(apiKey, endPoint);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	}

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/tags/v1/policy-to-device-api Records Count Validation with DB")
	public void apiRecordsCountValidation( ) {
		System.out.println("****************** Records Count Validation with DB for API "+endPoint);
		APIRecordsCountPojo apiRecordsCount= new APIRecordsCountPojo();
		try {
			apiRecordsCount=CommonTestAcrossAPIsIT.recordsCountTaggingSQL(apiKey, responseAPI);
			if (apiRecordsCount!=null)
			{
				softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),apiRecordsCount.getCountOfRecordsFromDB(),"Count of API Not Matched with DB ");
			}
			else
				softAssert.assertFalse(true,"Unable to fetch records from DB ");

			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}
	}
}
