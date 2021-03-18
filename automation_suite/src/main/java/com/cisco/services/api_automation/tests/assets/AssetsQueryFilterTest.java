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

import java.util.ArrayList;
import java.util.Arrays;

@Feature("Assets APIS")
public class AssetsQueryFilterTest {
	String errorInfo = null, message = null, groupId = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsQueryFilterListData")
	public void validateListAPIsQueryFilter(String apiName, String method, String endPoint, String params,
			String payLoad, String expectedStatusCode, String expectedResponse, String type, String expectedRecordCount,
			String notes) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
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
			if (endPoint.contains("export")) {
				actualRecordsCount = AssetsDataReader.getActualRecordCountExportAPIs(response);
			} else {
				actualRecordsCount = response.jsonPath().get("Pagination.total");
			}

			softAssert.assertEquals(actualRecordsCount, Integer.parseInt(expectedRecordCount),
					"Records Count Mistmatch");

		}

		if (response.getStatusCode() != Integer.parseInt(expectedStatusCode)) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsQueryFilterOthersData")
	public void validateNonListAPIsQueryFilter(String apiName, String method, String endPoint, String params,
			String payLoad, String expectedStatusCode, String expectedResponse, String type, String expectedRecordCount,
			String notes) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		response = getAPIResponse(method, endPoint, "", params, payLoad);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "test failed ");
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("data[*].wfId", (o1, o2) -> true),
				new Customization("data[*].coverageStartDate", (o1, o2) -> true),
				new Customization("data[*].coverageEndDate", (o1, o2) -> true),
				new Customization("data[*].groupInfo", (o1, o2) -> true),
				new Customization("expired.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("expired.toTimestampInMillis", (o1, o2) -> true),
				new Customization("lt-180-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("lt-180-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("180-to-365-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("180-to-365-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-365-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-365-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-0-lt-12-months.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-0-lt-12-months.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-12-lt-24-months.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-12-lt-24-months.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-24-lt-36-months.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-24-lt-36-months.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-36-months.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-0-lt-30-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-0-lt-30-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-30-lt-60-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-30-lt-60-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-60-lt-90-days.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("gt-60-lt-90-days.toTimestampInMillis", (o1, o2) -> true),
				new Customization("further-out.fromTimestampInMillis", (o1, o2) -> true),
				new Customization("further-out.toTimestampInMillis", (o1, o2) -> true));
		if (response.getStatusCode() == 200) {
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