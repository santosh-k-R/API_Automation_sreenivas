package com.cisco.services.api_automation.tests.commonservices;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.annotations.Test;
import com.cisco.services.api_automation.testdata.commonservices.CommonServicesData;
import com.cisco.services.api_automation.tests.commonservices.utils.CommonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Entitlement (ENT)")
public class EntitlementIT {

	@Test(dataProviderClass = CommonServicesData.class, dataProvider = "entitlementApiTestCases")
	public void verifyEntitlementApi(String testCaseDescription, String awsApiEndpoint, String awsApiRequestType, String awsApiParams, String awsApiHeaders, String awsApiBody,
											String sdpApiEndpoint, String sdpApiRequestType, String sdpApiParams, String sdpApiHeaders, String sdpApiBody) {

		String testCase = testCaseDescription;
		
		JsonElement sdpJson = null;
		JsonElement awsJson = null;
		String errorMsg = "";
		String jsonStr = null;

		//System.out.println("awsApiEndpoint: " + awsApiEndpoint);
		//System.out.println("sdpApiEndpoint: " + sdpApiEndpoint);
		
		try {
			
			Response sdpApiResponse = CommonUtil.invokeSDPApi(sdpApiEndpoint, sdpApiRequestType, sdpApiParams, sdpApiHeaders, sdpApiBody);
	        Response awsApiResponse = CommonUtil.invokeAWSApi(awsApiEndpoint, awsApiRequestType, awsApiParams, awsApiHeaders, awsApiBody);
			
	        Assert.assertEquals(awsApiResponse.getStatusCode(), sdpApiResponse.getStatusCode(), " " + testCase + " => AWS-API Http status code: " + awsApiResponse.getStatusCode() + " from endpoint: " + awsApiEndpoint + "\n SDP-API status code: " + sdpApiResponse.getStatusCode() + " from endpoint: " + sdpApiEndpoint);
	        
	        
	        jsonStr = sdpApiResponse.body().asString();

	        sdpJson = JsonParser.parseString(jsonStr);
				
		    jsonStr = awsApiResponse.body().asString();

		    
		    awsJson = JsonParser.parseString(jsonStr);
		    
		    if(CommonUtil.isOfDifferentStructure(sdpJson, awsJson)) {
		        Assert.fail(" " + "AWS actual json and SDP expected json is of different structure");
		    }else {
		        JSONAssert.assertEquals(String.valueOf(sdpJson), String.valueOf(awsJson), JSONCompareMode.LENIENT);
		        // log for testCase success
		    }
		   
		}catch(AssertionError ae) {
			if(ae.getMessage().startsWith(" ")) {
				errorMsg = testCase + " => " + ae.getMessage().substring(1);
				// log
				Assert.fail(errorMsg);
			}else {
				try {
					JSONCompareResult comparisonResult = JSONCompare.compareJSON(String.valueOf(sdpJson), String.valueOf(awsJson), JSONCompareMode.LENIENT);
					errorMsg = CommonUtil.prepareDescriptiveText(comparisonResult);
					//log
					Assert.fail(testCase + " => " + errorMsg);
				}catch(JSONException je) {
					errorMsg = testCase + "=> Mismatch with json, exception occured while comparing them";
					//log
					Assert.fail(errorMsg);
				}
			}
		}catch(JsonSyntaxException | JSONException je) {
			errorMsg = testCase + " => Received json from endpoint in not valid json";
			//log
			Assert.fail(errorMsg);
		}catch(Exception ex) {
			errorMsg = testCase + " => Exception occured: " + ex.toString();
			//log
			Assert.fail(errorMsg);
		}
	}
	
	
	
}
