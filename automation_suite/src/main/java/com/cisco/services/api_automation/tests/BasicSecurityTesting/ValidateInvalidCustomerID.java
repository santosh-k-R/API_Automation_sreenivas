package com.cisco.services.api_automation.tests.BasicSecurityTesting;


import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
//import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
//import com.cisco.services.api_automation.testdata.SecurityTesting.SecurityTestingData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.json.JSONException;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

public class ValidateInvalidCustomerID {
    @Severity(SeverityLevel.BLOCKER)

    @Test(dataProviderClass = SecurityTestingData.class, dataProvider = "SecurityDataInvalid")
    public void verifyNegativeMethods(String method, String url, String params, String headers, String body, String expectedStatusCode, String expectedOutput) throws
            JSONException {

        Response response = getAPIResponse(method, url, headers, params, body);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        //System.out.println(response.body().prettyPrint());
        Assert.assertEquals(statusCode,403,"The validation is completed for wrong customerId");



    }
}



