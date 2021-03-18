package com.cisco.services.api_automation.tests.BasicSecurityTesting;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import org.json.JSONException;
import org.testng.annotations.Test;


import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import com.cisco.services.api_automation.utils.customassert.Assert;

import io.restassured.response.Response;

public class ValidateRBAC {
	
	@Test(dataProviderClass = SecurityTestingData.class, dataProvider = "SuperAdminData")
	public void verifySuperAdmin(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	JSONException {
		Response response = getAPIResponse(method, url, headers, params, body,userRole);
        System.out.println(response.getStatusCode());

        String ActualOutput = response.getBody().prettyPrint();
       // Assert.assertEquals(expectedOutput,ActualOutput);

        int ActualStatusCode = response.getStatusCode();
        Assert.assertEquals(ActualStatusCode,200);
	}
	@Test(dataProviderClass = SecurityTestingData.class, dataProvider = "AdminData")
	public void verifyAdmin(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	JSONException {
		Response response = getAPIResponse(method, url, headers, params, body,userRole);
		System.out.println(response.getStatusCode());
		String ActualOutput = response.getBody().prettyPrint();
		//Assert.assertEquals(expectedOutput,ActualOutput);

		int ActualStatusCode = response.getStatusCode();

		Assert.assertEquals(ActualStatusCode,200);
	}
	@Test(dataProviderClass = SecurityTestingData.class, dataProvider = "StandardUserData")
	public void verifyStandardUser(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	JSONException {

		Response response = getAPIResponse(method, url, headers, params, body,userRole);
		System.out.println(response.getStatusCode());

		String ActualOutput = response.getBody().prettyPrint();
		//Assert.assertEquals(expectedOutput,ActualOutput);

		int ActualStatusCode = response.getStatusCode();

		Assert.assertEquals(ActualStatusCode,200);
	}
	@Test(dataProviderClass = SecurityTestingData.class, dataProvider = "AssetUserData")
	public void verifyAssetUser(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	JSONException {

		Response response = getAPIResponse(method, url, headers, params, body,userRole);
		System.out.println(response.getStatusCode());

		String ActualOutput = response.getBody().prettyPrint();
		//Assert.assertEquals(expectedOutput,ActualOutput);

		int ActualStatusCode = response.getStatusCode();

		Assert.assertEquals(ActualStatusCode,200);
	}
	@Test(dataProviderClass = SecurityTestingData.class, dataProvider = "ReadOnlyUserData")
	public void verifyReadOnlyUser(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	JSONException {

		Response response = getAPIResponse(method, url, headers, params, body,userRole);
		System.out.println(response.getStatusCode());

		String ActualOutput = response.getBody().prettyPrint();
		//Assert.assertEquals(expectedOutput,ActualOutput);

		int ActualStatusCode = response.getStatusCode();

		Assert.assertEquals(ActualStatusCode,200);
	}



}
