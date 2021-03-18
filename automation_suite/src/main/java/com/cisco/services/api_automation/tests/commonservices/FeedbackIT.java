package com.cisco.services.api_automation.tests.commonservices;

import com.cisco.services.api_automation.testdata.commonservices.CommonServicesData;
import com.cisco.services.api_automation.tests.commonservices.utils.CommonUtil;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Feature("Feedback (FDB)")
public class FeedbackIT {
	
	@Test(dataProviderClass = CommonServicesData.class, dataProvider = "feedbackApiTestCases")
	public void verifyFeedbackAPI(String testCaseDescription, String awsApiEndpoint, String awsApiRequestType, String awsApiParams, String awsApiHeaders, String awsApiBody, String awsEsIndex) {

		String testCase = testCaseDescription;
		
		try {
				
	        Response awsApiResponse = CommonUtil.invokeAWSApi(awsApiEndpoint, awsApiRequestType, awsApiParams, awsApiHeaders, awsApiBody);
	        Response awsEsResponse = CommonUtil.invokeAWSElasticSearch(awsEsIndex, "GET", "");
	        Assert.assertEquals(awsApiResponse.getStatusCode(), awsEsResponse.getStatusCode(), testCase + " falied");
		}catch(AssertionError ae) {
			//log
			Assert.fail(ae.getMessage());
		}catch(Exception ex) {
			//log
			Assert.fail(testCase + " => Exception occured: " + ex.toString());
		}
	}

}
