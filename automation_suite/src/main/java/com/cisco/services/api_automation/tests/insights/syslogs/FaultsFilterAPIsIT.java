package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.testdata.syslogs.SyslogsData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Feature("Syslogs APIS")
public class FaultsFilterAPIsIT {
	private static String method, url, headers, params, body, staticResponse, customerID, userRole;
	private SoftAssert softAssert = new SoftAssert();
	private String expectedStatusCode = "200";
	private JsonPath json;
	private int apiValue, excelValue;
	private Map<String, String> apiValueMap;
	private Map<String, String> excelValueMap;
	private HashMap<String, HashMap<String, String>> mapData;
	private Map<String, HashMap<String, String>> apiResponseData;
	private Map<String, HashMap<String, String>> excelResponseData;
	private JSONObject excelResponse;
	private HashMap<String, String> data;
	private JSONObject jsonEntry;
	private static Map<String, SyslogsInputDataPojo> apiData;

	@BeforeClass
	public static void triggerSyslogs() throws InterruptedException {
		SendSyslogMessages.executeCommand("Faults.txt");
	}

	@BeforeMethod
	public static void readExcel() {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		apiData = SyslogsData.FAULT_DATA;
		customerID = System.getenv("niagara_partyid");
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count")
	public void verifyGlbFaultCnt() throws JSONException {
		getDetails("FaultGlobalCount_NoUseCase");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault Count with use case filter - API /global-faults/count ", dataProviderClass = SyslogsData.class, dataProvider = "FaultGlobalCountWithUC")
	public void verifyGlbFaultCntWithUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole, ITestContext context) throws JSONException {
		context.setAttribute("testName", testName);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate All Fault Filter Count - API /tac-faults/count")
	public void verifyAllFaultCnt(ITestContext context) {
		getDetails("AllFaultsCounts");
		json = new JsonPath(staticResponse);
		context.setAttribute("faultexceResponse", json);
		softAssert = new SoftAssert();
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JsonPath responseBody = response.jsonPath();
		context.setAttribute("faultapiResponse", responseBody);
		apiValue = responseBody.getInt("faultCatalogCount");
		excelValue = json.getInt("faultCatalogCount");
		softAssert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
		Reporter.log("All Fault count from API response is :" + apiValue);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Severity Filter in All-Faults - API /tac-faults/count")
	public void verifyAllFaultCntSeverity(ITestContext context) {
		apiValueMap = new HashMap<String, String>();
		excelValueMap = new HashMap<String, String>();
		JsonPath responseExcel = (JsonPath) context.getAttribute("faultexceResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("faultapiResponse");
		apiValueMap = responseApi.getJsonObject("filterCounts");
		excelValueMap = responseExcel.getJsonObject("filterCounts");
		Assert.assertEquals(apiValueMap, excelValueMap,
				"Value From API " + apiValueMap + " doesnt match with Value from Excel " + excelValueMap);

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Time Range Filter in All-Faults - API /tac-faults/count")
	public void verifyAllFaultCntTimeRange(ITestContext context) {
		apiValueMap = new HashMap<String, String>();
		excelValueMap = new HashMap<String, String>();
		JsonPath responseExcel = (JsonPath) context.getAttribute("faultexceResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("faultapiResponse");
		apiValueMap = responseApi.getJsonObject("faultDayCounts");
		excelValueMap = responseExcel.getJsonObject("faultDayCounts");
		Assert.assertEquals(apiValueMap, excelValueMap,
				"Value From API " + apiValueMap + " doesnt match with Value from Excel " + excelValueMap);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate All Fault Filter Count - API /tac-faults/count", dataProviderClass = SyslogsData.class, dataProvider = "AllFaultCountsWithUC")
	public void verifyAllFaultCntWithUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole, ITestContext context) {
		softAssert = new SoftAssert();
		json = new JsonPath(expectedOutput);
		context.setAttribute("testName", testName);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JsonPath responseBody = response.jsonPath();
		apiValue = responseBody.getInt("faultCatalogCount");
		excelValue = json.getInt("faultCatalogCount");
		softAssert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate All Fault Time RangeFilter Count - API /tac-faults/count", dataProviderClass = SyslogsData.class, dataProvider = "AllFaultCountsWithUC")
	public void verifyAllFaultTimeCntWithUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole, ITestContext context) {
		softAssert = new SoftAssert();
		json = new JsonPath(expectedOutput);
		apiValueMap = new HashMap<String, String>();
		excelValueMap = new HashMap<String, String>();
		context.setAttribute("testName", testName);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		apiValueMap = response.jsonPath().getJsonObject("faultDayCounts");
		excelValueMap = json.get("faultDayCounts");
		softAssert.assertEquals(apiValueMap, excelValueMap,
				"Value From API " + apiValueMap + " doesnt match with Value from Excel " + excelValueMap);
		Reporter.log("All Fault count from API response is :" + apiValueMap);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate All Fault Severity Filter Count - API /tac-faults/count", dataProviderClass = SyslogsData.class, dataProvider = "AllFaultCountsWithUC")
	public void verifyAllFaultSevCntWithUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole, ITestContext context) {
		softAssert = new SoftAssert();
		json = new JsonPath(expectedOutput);
		apiValueMap = new HashMap<String, String>();
		excelValueMap = new HashMap<String, String>();
		context.setAttribute("testName", testName);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		apiValueMap = response.jsonPath().getJsonObject("filterCounts");
		excelValueMap = json.getJsonObject("filterCounts");
		softAssert.assertEquals(apiValueMap, excelValueMap,
				"Value From API " + apiValueMap + " doesnt match with Value from Excel " + excelValueMap);
		Reporter.log("All Fault count from API response is :" + apiValue);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid 30 days - API /message/faults")
	public void verifyAllFaultGridData() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_All");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parser = new JSONParser();
		System.out.println(response.jsonPath().prettify());
		JSONObject responsebody = (JSONObject) parser.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(responsebody);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid 15 days - API /message/faults")
	public void verifyAllFaultGrid15Days() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_All");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		params = params.replace("30", "15");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid 7 days - API /message/faults")
	public void verifyAllFaultGrid7Days() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_All");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		params = params.replace("30", "7");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid 15 days - API /message/faults")
	public void verifyAllFaultGrid1Day() throws JSONException, IOException, ParseException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_All");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		params = params.replace("30", "1");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid Severity Criticals - API /message/faults")
	public void verifyAllFaultGridCritical() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_Critical");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid Severity High - API /message/faults")
	public void verifyAllFaultGridHigh() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_High");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid Severity Medium - API /message/faults")
	public void verifyAllFaultGridMedium() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_Medium");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid Severity Low - API /message/faults")
	public void verifyAllFaultGridLow() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_Low");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid Severity Info - API /message/faults")
	public void verifyAllFaultGridInfo() throws IOException, ParseException, JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFaultsGrid_Info");
		excelResponse = (JSONObject) SyslogsData.readJson(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate fault grid With Use Case - API /message/faults", dataProviderClass = SyslogsData.class, dataProvider = "AllFaultGridWithUC")
	public void verifyAllFaultGridUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole) throws ParseException, JSONException, IOException {
		softAssert = new SoftAssert();
		excelResponse = (JSONObject) SyslogsData.readJson(expectedOutput);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(response.jsonPath().prettify());
		apiResponseData = faultDataMap(jobj);
		excelResponseData = faultDataMap(excelResponse);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		Reporter.log(
				"Global Fault count from API response is :" + response.getBody().jsonPath().getString("faultsCount"));
		softAssert.assertAll();
	}

	private HashMap<String, HashMap<String, String>> faultDataMap(JSONObject excelResponse) throws JSONException {
		mapData = new HashMap<String, HashMap<String, String>>();
		JSONArray jarr = (JSONArray) excelResponse.get("responseData");
		for (int i = 0; i < jarr.size(); i++) {
			data = new HashMap<String, String>();
			jsonEntry = (JSONObject) jarr.get(i);
			if (jsonEntry.containsKey("faultUpdatedTime")) {
				data.put("category", jsonEntry.get("category").toString());
				data.put("faultSeverity", jsonEntry.get("faultSeverity").toString());
				data.put("title", jsonEntry.get("title").toString());
				data.put("systemCount", jsonEntry.get("systemCount").toString());
				mapData.put(jsonEntry.get("signatureId").toString(), data);
			}
		}
		return mapData;
	}

	private void getDetails(String apiKey) {
		method = SyslogsData.FAULT_DATA.get(apiKey).getMethodType();
		url = SyslogsData.FAULT_DATA.get(apiKey).getEndPointUrl();
		headers = SyslogsData.FAULT_DATA.get(apiKey).getCxcontext().replace("{customerId}", customerID);
		params = SyslogsData.FAULT_DATA.get(apiKey).getParams().replace("{customerId}", customerID);
		body = SyslogsData.FAULT_DATA.get(apiKey).getPayload();
		staticResponse = SyslogsData.FAULT_DATA.get(apiKey).getResponse();
		userRole = SyslogsData.FAULT_DATA.get(apiKey).getUserRole();
		// System.out.println(method + " " + url + " " + headers + " " + params + " " +
		// body + " " + staticResponse + " "
		// + customerID);
	}

}
