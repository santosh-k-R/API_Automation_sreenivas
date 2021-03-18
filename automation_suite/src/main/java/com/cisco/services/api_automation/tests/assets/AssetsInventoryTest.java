package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

@Feature("Assets APIS")
public class AssetsInventoryTest {
	SoftAssert softAssert;
	String errorInfo = null, message = null, groupId = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsInventoryData")
	public void validateAssetsInventoryTestCases(String apiName, String method, String endPoint, String params,
			String payLoad, String expectedStatusCode, String expectedResponse, String type, String expectedRecordCount,
			String notes) throws JSONException {
		softAssert = new SoftAssert();
		int actualRecordsCount = 0;
		response = getAPIResponse(method, endPoint, "", params, payLoad);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "test failed ");
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("data[*].wfId", (o1, o2) -> true),
				new Customization("data[*].coverageStartDate", (o1, o2) -> true),
				new Customization("data[*].coverageEndDate", (o1, o2) -> true),
				new Customization("data[*].groupInfo", (o1, o2) -> true),
				new Customization("data[*].lastUpdateDate", (o1, o2) -> true));
		if (response.getStatusCode() == 200) {
			if (!(expectedResponse.equalsIgnoreCase("ignore") || expectedResponse.equals(""))) {
				JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), comparator);
			} else {
				actualRecordsCount = response.jsonPath().get("Pagination.total");
				softAssert.assertEquals(actualRecordsCount, Integer.parseInt(expectedRecordCount),
						"Records Count Mistmatch");
			}

		}

		if (response.getStatusCode() != Integer.parseInt(expectedStatusCode)) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

}