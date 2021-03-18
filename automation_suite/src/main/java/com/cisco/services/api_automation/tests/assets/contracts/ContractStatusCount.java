package com.cisco.services.api_automation.tests.assets.contracts;

import com.cisco.services.api_automation.pojo.response.assets.CountTypeAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

@Feature("Assets Inventory APIs")
public class ContractStatusCount extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	String apiKey = "contracts_status_count";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();

	}

	@Test(description = "/contracts/v1/status/count API 200 Response Validation")
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

	@Test(dependsOnMethods = {
			"api200ResponseValidation" }, description = "/contracts/v1/status/count Data Validation with ES", groups = {
					"es" }, dataProvider = "GetSolutionUseCase", dataProviderClass = AssetsData.class)
	public void apiDataValidation(String solution, String useCase) {
		System.out.println("****************** Data Validation with ES for API " + endPoint);
		Map<String, Integer> esResponseObject;
		Map<String, Integer> apiResponseObject;
		CountTypeAPIPojo countTypeAPIPojo = new CountTypeAPIPojo();

		try {
			countTypeAPIPojo = CommonTestAcrossAPIsIT.countAPIValidation(apiKey, solution, useCase);
			apiResponseObject = countTypeAPIPojo.getApiResponseObject();
			esResponseObject = countTypeAPIPojo.getEsResponseObject();
			softAssert.assertEquals(apiResponseObject, esResponseObject, "ES and API map data not matched\n");
			for (String type : esResponseObject.keySet()) {
				if (apiResponseObject.containsKey(type)) {
					softAssert.assertEquals(apiResponseObject.get(type), esResponseObject.get(type),
							"API count not matched with ES count:" + esResponseObject.get(type));
				} else {
					softAssert.assertFalse(true,"API response doesn't contain : "+type);
				}
			}
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}

	}

}
