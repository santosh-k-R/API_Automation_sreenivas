package com.cisco.services.api_automation.tests.BasicSecurityTesting;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import org.json.JSONException;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import com.cisco.services.api_automation.utils.customassert.Assert;

import io.restassured.response.Response;


public class ValidateAuthorizationCheck {
	
	 @Test(dataProviderClass = SecurityTestingData.class, dataProvider = "AuthzChkVerifyData")
	    public void verifyAuthzCheck(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput,String userRole) throws
	            JSONException {

	        Response response = getAPIResponse(method, url, headers, params, body,userRole);
	        System.out.println(response.getStatusCode());
	        
	        response.getBody().prettyPrint();
	       
	        int ActualStatusCode = response.getStatusCode();

	        Assert.assertEquals(ActualStatusCode,403);

	        
	   }


}
