package com.cisco.services.api_automation.tests.BasicSecurityTesting;

import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.json.JSONException;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

public class ValidateAllowMethods {
    @Severity(SeverityLevel.BLOCKER)

    @Test(dataProviderClass = SecurityTestingData.class, dataProvider = "SecurityData")
    public void verifyAllowMethods(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput) throws
            JSONException {

        Response response = getAPIResponse(method, url, headers, params, body);
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders().getValue("access-control-allow-methods"));
        //String allowedMethods = response.getHeaders().getValue("access-control-allow-methods");



    }
}


