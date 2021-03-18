package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.testdata.syslogs.SyslogsData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SyslogsGridAPIsIT {

	private SoftAssert softAssert = new SoftAssert();
	private static Map<String, SyslogsInputDataPojo> apiData;
	private static String method, url, headers, params, body, staticResponse, customerID, userRole;
	private String expectedStatusCode = "200";
	private JsonPath json;
	private Map<String, Map<String, String>> apiResponseData;
	private Map<String, Map<String, String>> excelResponseData;
	private int excelValue, responsesize, apiValue;
	private HashMap<String, Map<String, String>> mapData;
	private HashMap<String, String> data;

	@BeforeMethod
	public static void readExcel() {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		apiData = SyslogsData.SYSLOG_DATA;
		customerID = System.getenv("niagara_partyid");
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid count All")
	public void verifySyslogGridCount(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_All");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntAll", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			Commons.writeJsonFile("syslogdata.json", response.jsonPath().prettify());
			context.setAttribute("apiGridCntAll", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid Data All", alwaysRun = true, dependsOnMethods = "verifySyslogGridCount")
	public void verifySyslogGridDataAll(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntAll");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntAll"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 1")
	public void verifySyslogGridCountSev1(ITestContext context) throws JSONException, ParseException {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev1");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev1", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev1", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 1", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev1")
	public void verifySyslogGridDataSev1(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev1");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev1"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 2")
	public void verifySyslogGridCountSev2(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev2");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev2", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev2", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 2", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev2")
	public void verifySyslogGridDataSev2(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev2");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev2"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 3")
	public void verifySyslogGridCountSev3(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev3");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev3", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev3", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 3", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev3")
	public void verifySyslogGridDataSev3(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev3");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev3"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 4")
	public void verifySyslogGridCountSev4(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev4");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev4", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev4", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 4", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev4")
	public void verifySyslogGridDataSev4(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev4");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev4"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 5")
	public void verifySyslogGridCountSev5(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev5");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev5", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev5", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 5", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev5")
	public void verifySyslogGridDataSev5(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev5");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev5"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 6")
	public void verifySyslogGridCountSev6(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev6");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev6", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev6", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 6", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev6")
	public void verifySyslogGridDataSev6(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev6");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev6"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 7")
	public void verifySyslogGridCountSev7(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev7");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev7", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev7", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 7", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev7")
	public void verifySyslogGridDataSev7(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev7");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev7"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid count Severity 0")
	public void verifySyslogGridCountSev0(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_Day14Sev0");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntSev0", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntSev0", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs to get Grid Data Severity 0", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountSev0")
	public void verifySyslogGridDataSev0(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntSev0");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntSev0"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid count Day 7")
	public void verifySyslogGridCountDay7(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_AllDay7");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntday7", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntday7", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid Data Day 7", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountDay7")
	public void verifySyslogGridDataDay7(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntday7");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntday7"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid count Day 3")
	public void verifySyslogGridCountDay3(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_AllDay3");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntday3", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntday3", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid Data Day 3", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountDay3")
	public void verifySyslogGridDataDay3(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntday3");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntday3"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid count Day 1")
	public void verifySyslogGridCountDay1(ITestContext context) {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails_AllDay1");
			json = new JsonPath(staticResponse);
			context.setAttribute("exGridCntday1", json);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			context.setAttribute("apiGridCntday1", responseBody);
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid Data Day 1", alwaysRun = true, dependsOnMethods = "verifySyslogGridCountDay1")
	public void verifySyslogGridDataDay1(ITestContext context) throws JSONException, ParseException {
		apiResponseData = new HashMap<String, Map<String, String>>();
		excelResponseData = new HashMap<String, Map<String, String>>();
		JsonPath responseBody = (JsonPath) context.getAttribute("apiGridCntday1");
		apiResponseData = syslogDataMap(responseBody);
		excelResponseData = syslogDataMap((JsonPath) context.getAttribute("exGridCntday1"));
		Assert.assertEquals(apiResponseData, excelResponseData);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs to get Grid Data by Use Casefilter", dataProviderClass = SyslogsData.class, dataProvider = "SyslogDataFilterByUC")
	public void verifySyslogGridDataUCfilter(String method, String url, String headers, String params, String body,
			String expectedOutput, String userRole) {
		softAssert = new SoftAssert();
		json = new JsonPath(expectedOutput);
			headers = headers.replace("{customerId}", customerID);
			params = params.replace("{customerId}", customerID);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JsonPath responseBody = response.jsonPath();
			apiValue = responseBody.get("count");
			excelValue = json.getInt("count");
			softAssert.assertEquals(apiValue, excelValue,
					"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
			apiResponseData = syslogDataMap(responseBody);
			excelResponseData = syslogDataMap(json);
			softAssert.assertEquals(apiResponseData, excelResponseData);
			softAssert.assertAll();
	}

	private Map<String, Map<String, String>> syslogDataMap(JsonPath responseBody) {
		mapData = new HashMap<String, Map<String, String>>();
		responsesize = responseBody.getList("responseData").size();
		for (int i = 0; i < responsesize; i++) {
			data = new HashMap<String, String>();
			data.put("syslogSeverity", responseBody.get("responseData[" + i + "].syslogSeverity").toString());
			data.put("deviceHost", responseBody.get("responseData[" + i + "].deviceHost"));
			data.put("msgDesc", responseBody.get("responseData[" + i + "].msgDesc"));
			data.put("neInstanceId", responseBody.get("responseData[" + i + "].neInstanceId"));
			data.put("msgType", responseBody.get("responseData[" + i + "].msgType"));
			data.put("serialnumber", responseBody.get("responseData[" + i + "].serialnumber"));
			mapData.put(responseBody.get("responseData[" + i + "].regex"), data);
		}
		return mapData;
	}

	private void getDetails(String apiKey) {
		method = SyslogsData.SYSLOG_DATA.get(apiKey).getMethodType();
		url = SyslogsData.SYSLOG_DATA.get(apiKey).getEndPointUrl();
		headers = SyslogsData.SYSLOG_DATA.get(apiKey).getCxcontext().replace("{customerId}", customerID);
		params = SyslogsData.SYSLOG_DATA.get(apiKey).getParams().replace("{customerId}", customerID);
		body = SyslogsData.SYSLOG_DATA.get(apiKey).getPayload();
		staticResponse = SyslogsData.SYSLOG_DATA.get(apiKey).getResponse();
		userRole = SyslogsData.SYSLOG_DATA.get(apiKey).getUserRole();
	}
}
