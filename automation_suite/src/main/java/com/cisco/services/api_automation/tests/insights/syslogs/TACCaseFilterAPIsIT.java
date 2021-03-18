package com.cisco.services.api_automation.tests.insights.syslogs;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.testdata.syslogs.SyslogsData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TACCaseFilterAPIsIT {
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
	private int responsesize;
	private static Map<String, SyslogsInputDataPojo> apiData;

	@BeforeMethod
	public static void readExcel() {
		apiData = new HashMap<String, SyslogsInputDataPojo>();
		//apiData = SyslogsData.TAC_DATA;
		customerID = System.getenv("niagara_partyid");
	}

	@Test(dataProviderClass = SyslogsData.class, dataProvider = "EnableCaseAutomation")
	public void enableAndSendFaults(String testName, String method, String url, String headers, String params,
			String body, String expectedOutput, String usrrole) throws InterruptedException {
		try {
			Response response = getAPIResponse(method, url, headers, params, body, usrrole);
			softAssert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
		SendSyslogMessages.executeCommand("TACView.txt");
		Thread.sleep(300000);
	}

	private void getDetails(String apiKey) {
		method = SyslogsData.FAULT_DATA.get(apiKey).getMethodType();
		url = SyslogsData.FAULT_DATA.get(apiKey).getEndPointUrl();
		headers = SyslogsData.FAULT_DATA.get(apiKey).getCxcontext().replace("{customerId}", customerID);
		params = SyslogsData.FAULT_DATA.get(apiKey).getParams().replace("{customerId}", customerID);
		body = SyslogsData.FAULT_DATA.get(apiKey).getPayload();
		staticResponse = SyslogsData.FAULT_DATA.get(apiKey).getResponse();
		userRole = SyslogsData.FAULT_DATA.get(apiKey).getUserRole();
	}
}
