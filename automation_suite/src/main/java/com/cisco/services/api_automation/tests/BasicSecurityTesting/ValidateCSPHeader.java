package com.cisco.services.api_automation.tests.BasicSecurityTesting;

import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.json.JSONException;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

public class ValidateCSPHeader {
    @Severity(SeverityLevel.BLOCKER)

    @Test(dataProviderClass = SecurityTestingData.class, dataProvider = "SecurityData")
    public void verifyCSPHeaders(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput) throws
            JSONException {

        Response response = getAPIResponse(method, url, headers, params, body);
        System.out.println(response.getStatusCode());
        String CSP = response.header("Content-Security-Policy");
        System.out.println(CSP);
        Assert.assertEquals(CSP,"default-src 'self'; base-uri 'self'; frame-ancestors 'self'; block-all-mixed-content");


    }
}


