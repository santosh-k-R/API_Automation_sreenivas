package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

@Feature("Assets APIS")
public class AssetsTest {
	SoftAssert softAssert;
	String errorInfo = null, message = null, groupId = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA);
	private long expectedResponseTime = 1000;

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateStatusCode__All(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					apiObj.getPayLoad());
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
					apiObj.getPayLoad());
		}
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateResponseTime__All(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					apiObj.getPayLoad());
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
					apiObj.getPayLoad());
		}
		softAssert.assertTrue(response.getTime() < expectedResponseTime,
				"Test Case failed as response time is greater than 1 second:" + response.getTime());
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by vbollimu
	 */
//    @Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateStatusCode__AssetGroupWriteAPIs(String key) {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		if (key.contains("create")) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
					apiObj.getPayLoad());
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test");
			groupId = response.jsonPath().get("id");
		}
		if (key.contains("update") || key.contains("deviceEdit") || key.contains("delete")) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
					apiObj.getPayLoad().replace("<groupId>", groupId));
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()),
					apiObj.getApiName() + "test failed ");
		}
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateRecordsCount__PaginatedListAPIs(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		int actualRecordsCount = 0;
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		if (response.getStatusCode() == 200) {
			actualRecordsCount = response.jsonPath().get("Pagination.total");
			softAssert.assertEquals(actualRecordsCount, Integer.parseInt(apiObj.getRecordCount()),
					"Records Count Mistmatch");
		} else
			softAssert.assertFalse(true);
		if (response.getStatusCode() != 200) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateRecordsCount__NonPaginatedListAPIs(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		int actualRecordsCount = 0;
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		if (response.getStatusCode() == 200) {
			if (!apiObj.getEndPoint().contains("tags"))
				actualRecordsCount = response.jsonPath().getList("").size();
			else
				actualRecordsCount = response.jsonPath().getList("tags").size();
			softAssert.assertEquals(actualRecordsCount, Integer.parseInt(apiObj.getRecordCount()),
					"Records Count Mistmatch");
		} else
			softAssert.assertFalse(true);
		if (response.getStatusCode() != 200) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by vbollimu
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateRecordsCount__ExportAPIs(String key) throws IOException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
				apiObj.getPayLoad());
		int actualRowCount = AssetsDataReader.getActualRecordCountExportAPIs(response);
		softAssert.assertEquals(actualRowCount, Integer.parseInt(apiObj.getRecordCount()), key + " test failed");
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__AggregationAPIs(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		if (response.getStatusCode() == 200) {
			JSONAssert.assertEquals(apiObj.getExpectedResponse().toString(), response.getBody().asString(),
					JSONCompareMode.LENIENT);
		} else
			softAssert.assertFalse(true);
		if (response.getStatusCode() != 200) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__DateRangeAggregationAPIs(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		Iterator<String> jsonKeys;
		JSONObject json;
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		json = new JSONObject(apiObj.getExpectedResponse().toString());
		jsonKeys = json.keys();
		if (response.getStatusCode() == 200) {
			while (jsonKeys.hasNext()) {
				String dateRangeKey = jsonKeys.next();
				int actualValue = response.jsonPath().get(dateRangeKey + ".numericValue");
				int expectedValue = json.getJSONObject(dateRangeKey).getInt("numericValue");
				softAssert.assertEquals(actualValue, expectedValue, dateRangeKey + " test failed");
			}
		} else
			softAssert.assertFalse(true);
		if (response.getStatusCode() != 200) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			System.out.println("Failure Info:" + errorInfo);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__360ViewAPIs(String key) throws JSONException, IOException {
		softAssert = new SoftAssert();
		String expectedResponse = null;
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		if (response.getStatusCode() == 200) {
			expectedResponse = apiObj.getExpectedResponse();
			JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), JSONCompareMode.LENIENT);
		} else if (response.getStatusCode() != 200) {
			softAssert.assertFalse(true);
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by vbollimu
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__ExportAPIs(String key) throws IOException {
		softAssert = new SoftAssert();
		String actualResponse = null, expectedResponse = null;
		AssetsAPIPojo apiObj = data.get(key);
		// reading response from file and saving to a String variable.
		expectedResponse = new String(Files.readAllBytes(Paths.get(apiObj.getExpectedResponsePath())));
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
				apiObj.getPayLoad());
		actualResponse = response.getBody().asString();
		softAssert.assertEquals(actualResponse, expectedResponse, key + " test failed");
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__PaginatedListAPIs(String key) throws JSONException, IOException {
		softAssert = new SoftAssert();
		int actualPageCount = 1, totalCount = 0;
		String expectedResponse = null;
		AssetsAPIPojo apiObj = data.get(key);
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("data[*].wfId", (o1, o2) -> true),
				new Customization("data[*].coverageStartDate", (o1, o2) -> true),
				new Customization("data[*].coverageEndDate", (o1, o2) -> true),
				new Customization("data[*].groupInfo", (o1, o2) -> true),
				new Customization("data[*].mgmtSystemAddr", (o1, o2) -> true),
				new Customization("data[*].mgmtSystemHostname", (o1, o2) -> true),
				new Customization("data[*].mgmtSystemId", (o1, o2) -> true),
				new Customization("data[*].lastUpdateDate", (o1, o2) -> true));
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		if (response.getStatusCode() == 200) {
			totalCount = response.jsonPath().get("Pagination.total");
			if (totalCount > 0) {
				actualPageCount = response.jsonPath().get("Pagination.pages");
				for (int i = 1; i <= actualPageCount; i++) {
					response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "",
							apiObj.getParams().replace("page=1", "page=" + i), "");
					expectedResponse = new String(Files.readAllBytes(
							Paths.get(apiObj.getExpectedResponsePath() + "file" + key + "_" + i + ".json")));
					JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), comparator);
					System.out.println("Validated data for " + key + "API on page " + i);
				}
			}
		} else if (response.getStatusCode() != 200) {
			softAssert.assertFalse(true);
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "GenericKeys")
	public void validateData__NonPaginatedListAPIs(String key) throws JSONException, IOException {
		softAssert = new SoftAssert();
		String expectedResponse = null;
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getEndPoint().contains("/tags") && !apiObj.getParams().contains(customerId)) {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() + customerId, "", apiObj.getParams(),
					"");
		} else {
			response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		}
		if (response.getStatusCode() == 200) {
			expectedResponse = new String(
					Files.readAllBytes(Paths.get(apiObj.getExpectedResponsePath() + "file" + key + ".json")));
			JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), JSONCompareMode.LENIENT);
		} else if (response.getStatusCode() != 200) {
			softAssert.assertFalse(true);
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

}