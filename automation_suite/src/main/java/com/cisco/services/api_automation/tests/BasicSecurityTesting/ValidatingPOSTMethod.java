package com.cisco.services.api_automation.tests.BasicSecurityTesting;

import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

public class ValidatingPOSTMethod {
    @Severity(SeverityLevel.BLOCKER)

    @Test(dataProviderClass = SecurityTestingData.class, dataProvider = "SecurityDataPOST")
    public void verifyPOSTMethod(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput) throws
            JSONException {

        Response response = getAPIResponse(method, url, headers, params, body);
        System.out.println(response.getStatusCode());
        
  
        System.out.println(response.body().prettyPrint());
        
        
        

    }
}
