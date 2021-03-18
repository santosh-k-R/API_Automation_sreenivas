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
public class AssetsTaggingTest {
	SoftAssert softAssert;
	String errorInfo = null, message = null, groupId = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsTaggingData")
	public void validateAssetTaggingTestCases(String apiName, String method, String endPoint, String params, String payLoad,
			String expectedStatusCode, String expectedResponse, String type, String expectedRecordCount, String notes)
			throws JSONException {
		softAssert = new SoftAssert();
		response = getAPIResponse(method, endPoint, "", params, payLoad, "MACHINE");
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "test failed ");
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("devices[*].wfId", (o1, o2) -> true),
				new Customization("devices[*].s3ObjectDetails.fileName", (o1, o2) -> true),
				new Customization("devices[*].s3ObjectDetails.filePath", (o1, o2) -> true),
				new Customization("devices[*].configUpdateCollTime", (o1, o2) -> true),
				new Customization("timeStamp", (o1, o2) -> true));
		if (endPoint.contains("active-policy-group")) {
			JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), JSONCompareMode.LENIENT);
		} else if (!expectedResponse.equalsIgnoreCase("ignore")) {
			JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), comparator);
		}

		if (response.getStatusCode() != Integer.parseInt(expectedStatusCode)) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

}