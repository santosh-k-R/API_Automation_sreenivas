package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.testdata.syslogs.SyslogsData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FaultDetailAPIsIT {
	private static Map<String, SyslogsInputDataPojo> apiData;
	private static String method, url, headers, params, body, staticResponse, customerID, userRole;
	private SoftAssert softAssert;
	private String expectedStatusCode = "200";
	private HashMap<String, HashMap<String, String>> mapData;
	private JsonPath json;
	private HashMap<String, HashMap<String, String>> excelResponseData;
	private HashMap<String, HashMap<String, String>> apiResponseData;
	private int responsesize;
	private Integer signature;
	private HashMap<String, String> data;

	@BeforeMethod
	public static void readExcel() throws IOException, ParseException {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		apiData = SyslogsData.FAULT_DATA;
		customerID = System.getenv("niagara_partyid");
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogDetails to get syslog Details")
	public void verifySyslogDetail() throws JSONException {
		getDetails("AllFault_DetailView");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSys30Days() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_day30");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSys15Days() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_day15");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSys7Days() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_day7");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSys1Days() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_day30");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSysProdFilt() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_ProdFilter");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffHNFilt() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_HNFilt");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSysProdHNFilter() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_ProdHNFilter");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems")
	public void verifyAffSysFilterInvalid() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("AllFault_AffSys_ProdHNFilter");
		json = new JsonPath(staticResponse);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Affected system Days 30 - API /affectedSystems", dataProviderClass = SyslogsData.class, dataProvider = "AffSystemFilterByUC")
	public void verifyAffSysUCFilter(String testname, String method, String url, String headers, String params,
			String body, String expectedOutput, String userRole) throws JSONException {
		softAssert = new SoftAssert();
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		json = new JsonPath(expectedOutput);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		apiResponseData = affSysMap(response.jsonPath());
		excelResponseData = affSysMap(json);
		softAssert.assertEquals(apiResponseData, excelResponseData, "Not MApped");
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count")
	public void verifyFilterData() throws JSONException {
		getDetails("AllFault_FilterData");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), false);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault Count with use case filter - API /global-faults/count ", dataProviderClass = SyslogsData.class, dataProvider = "FilterDataWithUC")
	public void verifyFilterWithUC(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole, ITestContext context) throws JSONException {
		context.setAttribute("testName", testName);
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, usrrole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), false);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count")
	public void verifyEnableCaseAuto() throws JSONException {
		getDetails("EnableCaseAutomation");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count", dependsOnMethods = "verifyEnableCaseAuto")
	public void verifyDetailsAfterEnable() {
		getDetails("FaultSummary_Afterenable");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		responsesize = response.jsonPath().getList("responseData").size();
		for (int i = 0; i < responsesize; i++) {
			if (response.jsonPath().getInt("responseData[" + i + "].signatureId") == signature) {
				softAssert.assertEquals(response.jsonPath().getBoolean("responseData[" + i + "].raiseSr"), true);
			}
		}
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count", dependsOnMethods = "verifyEnableCaseAuto")
	public void verifyDisableCaseAuto() throws JSONException {
		getDetails("DisableCaseAutomation");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate Global Fault count - API /global-faults/count", dependsOnMethods = "verifyDisableCaseAuto")
	public void verifyDetailsAfterDisable() {
		getDetails("FaultSummary_Afterenable");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		responsesize = response.jsonPath().getList("responseData").size();
		for (int i = 0; i < responsesize; i++) {
			if (response.jsonPath().getInt("responseData[" + i + "].signatureId") == signature) {
				softAssert.assertEquals(response.jsonPath().getBoolean("responseData[" + i + "].raiseSr"), false);
			}
		}
		softAssert.assertAll();
	}

	private HashMap<String, HashMap<String, String>> affSysMap(JsonPath responseBody) throws JSONException {
		mapData = new HashMap<String, HashMap<String, String>>();
		responsesize = responseBody.getList("responseData").size();
		for (int i = 0; i < responsesize; i++) {
			data = new HashMap<String, String>();
			data.put("systemName", responseBody.get("responseData[" + i + "].systemName").toString());
			data.put("serialNumber", responseBody.get("responseData[" + i + "].serialNumber").toString());
			data.put("productId", responseBody.get("responseData[" + i + "].productId").toString());
			data.put("softwareType", responseBody.get("responseData[" + i + "].softwareType").toString());
			data.put("srStatus", responseBody.get("responseData[" + i + "].srStatus").toString());
			data.put("tacCaseNo", String.valueOf((Object) responseBody.get("responseData[" + i + "].tacCaseNo")));
			mapData.put(responseBody.get("responseData[" + i + "].systemName").toString(), data);
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
		if (!SyslogsData.FAULT_DATA.get(apiKey).getSign().isEmpty()) {
			signature = Integer.valueOf(SyslogsData.FAULT_DATA.get(apiKey).getSign());
		}
	}

}
