package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.ITestContext;
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
public class SyslogsCountAPIsIT {
	private SoftAssert softAssert = new SoftAssert();
	private static Map<String, SyslogsInputDataPojo> apiData;
	private static String method, url, headers, params, body, staticResponse, customerID, userRole;
	private String expectedStatusCode = "200";
	private JSONObject json;
	private int apiValue;
	private int excelValue;

	
	/*
	 * @BeforeClass public static void triggerSyslogs() throws InterruptedException
	 * { SendSyslogMessages.executeCommand("syslogs.txt"); }
	 */
	 

	@BeforeMethod
	public static void readExcel() {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		apiData = SyslogsData.SYSLOG_DATA;
		customerID = System.getenv("niagara_partyid");
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /global-syslogs/count")
	public void verifyGlbSyslogCnt() throws JSONException {
		getDetails("SyslogGlobalCount_NoUseCase");
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /global-syslogs/count with use case filter", dataProviderClass = SyslogsData.class, dataProvider = "SyslogGlobalCountWithUC")
	public void verifyGlbSyslogCntWithUC(String method, String url, String headers, String params, String body,
			String expectedOutput, String userRole) throws JSONException {
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/counts - time Range filter 14 Days Count")
	public void verifyTimeRangefilter14days(ITestContext context) throws JSONException {
		softAssert = new SoftAssert();
		getDetails("SyslogTimeRangeCount");
		json = new JSONObject(staticResponse);
		context.setAttribute("exceResponse", json);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JsonPath responseBody = response.jsonPath();
		context.setAttribute("apiResponse", responseBody);
		apiValue = responseBody.get("faultDayCounts.days_14");
		excelValue = json.getJSONObject("faultDayCounts").getInt("days_14");
		softAssert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/counts - time Range filter 7 Days Count", dependsOnMethods = "verifyTimeRangefilter14days")
	public void verifyTimeRangefilter7days(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("exceResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("apiResponse");
		apiValue = responseApi.get("faultDayCounts.days_7");
		excelValue = responseExcel.getJSONObject("faultDayCounts").getInt("days_7");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/counts - time Range filter 3 Days Count", dependsOnMethods = "verifyTimeRangefilter14days")
	public void verifyTimeRangefilter3days(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("exceResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("apiResponse");
		apiValue = responseApi.get("faultDayCounts.days_3");
		excelValue = responseExcel.getJSONObject("faultDayCounts").getInt("days_3");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/counts - time Range filter 1 Days Count", dependsOnMethods = "verifyTimeRangefilter14days")
	public void verifyTimeRangefilter1day(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("exceResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("apiResponse");
		apiValue = responseApi.get("faultDayCounts.days_1");
		excelValue = responseExcel.getJSONObject("faultDayCounts").getInt("days_1");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs/counts - time Range filter Count With UseCase", dataProviderClass = SyslogsData.class, dataProvider = "SyslogTimeFilterCountWithUC")
	public void verifySyslogtimeCntWithUC(String method, String url, String headers, String params, String body,
			String expectedOutput, String userRole) throws JSONException {
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 1")
	public void verifySeverityFilterSev1(ITestContext context) throws JSONException {
		softAssert = new SoftAssert();
		getDetails("SyslogSeverityFilterCounts");
		json = new JSONObject(staticResponse);
		context.setAttribute("sevExcelResponse", json);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JsonPath responseBody = response.jsonPath();
		context.setAttribute("sevApiResponse", responseBody);
		apiValue = responseBody.get("filterCounts.syslogSeverity1");
		excelValue = json.getJSONObject("filterCounts").getInt("syslogSeverity1");
		softAssert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
		softAssert.assertAll();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 2", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev2(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity2");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity2");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 3", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev3(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity3");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity3");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 4", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev4(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity4");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity4");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 5", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev5(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity5");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity5");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 6", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev6(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity6");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity6");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 7", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSev7(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity7");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity7");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs/count for Severity filter - Severity 0", dependsOnMethods = "verifySeverityFilterSev1")
	public void verifySeverityFilterSevZero(ITestContext context) throws JSONException, ParseException {
		JSONObject responseExcel = (JSONObject) context.getAttribute("sevExcelResponse");
		JsonPath responseApi = (JsonPath) context.getAttribute("sevApiResponse");
		apiValue = responseApi.get("filterCounts.syslogSeverity0");
		excelValue = responseExcel.getJSONObject("filterCounts").getInt("syslogSeverity0");
		Assert.assertEquals(apiValue, excelValue,
				"Value From API " + apiValue + " doesnt match with Value from Excel " + excelValue);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslogs/counts - Severity filter Count With UseCase", dataProviderClass = SyslogsData.class, dataProvider = "SyslogSevFilterCountWithUC")
	public void verifySyslogSevCntWithUC(String method, String url, String headers, String params, String body,
			String expectedOutput, String userRole) throws JSONException {
		headers = headers.replace("{customerId}", customerID);
		params = params.replace("{customerId}", customerID);
		Response response = getAPIResponse(method, url, headers, params, body, userRole);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
				"Test Case failed as response status is not 200: " + response.getStatusLine());
		JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
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
