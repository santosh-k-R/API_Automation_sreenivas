package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
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

public class SyslogsDetailAPIsIT {
	private SoftAssert softAssert = new SoftAssert();
	private static Map<String, SyslogsInputDataPojo> apiData;	
	private static String method, url, headers, params, body, staticResponse , customerID, userRole, regex, msgType, msgDesc, suggestion;
	private String expectedStatusCode = "200";
	private HashMap<String, Map<String, String>> mapData;
	private JsonPath json;
	private JSONObject obj1;
	private static JSONObject syslogDetails;
	private static JSONArray responseData;
	private static String syslogId;

	@BeforeClass
	public static void readExcel() throws Exception {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		apiData = SyslogsData.SYSLOG_DETAILS;
		customerID = System.getenv("niagara_partyid");
		syslogDetails = (JSONObject) Commons.readJsonFile("syslogdata.json");
		responseData = (JSONArray) syslogDetails.get("responseData");
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Validate API /syslog/Details to get syslog Details")
	public void verifySyslogDetail() {
		softAssert = new SoftAssert();
		getDetails("SyslogDetails");
			json = new JsonPath(staticResponse);
			syslogId=getSysogId(regex);
			params = params.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			softAssert.assertEquals(response.getBody().jsonPath().getString("responseData[0].msgType"),json.get("responseData[0].msgType"));
			softAssert.assertEquals(response.getBody().jsonPath().getString("responseData[0].icDesc"),json.get("responseData[0].icDesc"));
			softAssert.assertEquals(response.getBody().jsonPath().getString("responseData[0].recommendation"),json.get("responseData[0].recommendation"));
			softAssert.assertAll();
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM ")
	public void verifyPushToAFM1() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_Type1");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}

	@Test(description = "Push to AFM")
	public void verifyPushToAFMUpdate() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_SameICDiffDevice");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM")
	public void verifyPushToAFM2() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_Type2");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM")
	public void verifyPushToAFM3() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_Type3");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
    
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM")
	public void verifyPushToAFM4() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_Type4");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM")
	public void verifyPushToAFM5() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_Type5");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Push to AFM")
	public void verifyPushToAFMFaults() throws JSONException {
		softAssert = new SoftAssert();
		getDetails("pushToAFM_AFMFaults");
			syslogId=getSysogId(regex);
			body = body.replace("{syslogId}", syslogId);
			Response response = getAPIResponse(method, url, headers, params, body, userRole);
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200: " + response.getStatusLine());
			JSONAssert.assertEquals(staticResponse, response.jsonPath().prettify(), true);
	}
	
	private String getSysogId(String regex2) {
		String id = null;
		for(int i=0;i < responseData.size();i++)
		{
			obj1 = (JSONObject) responseData.get(i);
			if(obj1.get("regex").equals(regex))
			{
				id = (String) obj1.get("syslogId");
			}
		}
		return id;
	}

	private void getDetails(String apiKey) {
		method = SyslogsData.SYSLOG_DETAILS.get(apiKey).getMethodType();
		url = SyslogsData.SYSLOG_DETAILS.get(apiKey).getEndPointUrl();
		headers = SyslogsData.SYSLOG_DETAILS.get(apiKey).getCxcontext().replace("{customerId}", customerID);
		params = SyslogsData.SYSLOG_DETAILS.get(apiKey).getParams().replace("{customerId}", customerID);
		body = SyslogsData.SYSLOG_DETAILS.get(apiKey).getPayload();
		staticResponse=SyslogsData.SYSLOG_DETAILS.get(apiKey).getResponse();
		userRole = SyslogsData.SYSLOG_DETAILS.get(apiKey).getUserRole();
		regex = SyslogsData.SYSLOG_DETAILS.get(apiKey).getRegex();
	}
}
