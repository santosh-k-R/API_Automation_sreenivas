package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.testdata.insights.compliance.StaticTestData;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.*;

public class AdminPageAPITestsIT {

    @Feature("Opt-In-Detail API")
    @Test(dataProviderClass = StaticTestData.class, dataProvider = "optInDetailAPIData")
    public void optInDetailAPI(String scenario, String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ scenario + "</em></h4>");
        Response response = getAPIResponse(method, url, headers, params, body);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
        compareResponse(expectedOutput, response.asString());
    }

    @Feature("Policy Group API")
    @Test(dataProviderClass = StaticTestData.class, dataProvider = "policyGroupAPIData")
    public void policyGroupAPI(String scenario, String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ scenario + "</em></h4>");
        Response response = getAPIResponse(method, url, headers, params, body);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
        compareResponse(expectedOutput, response.asString());
    }

    @Feature("Uptdae Opt-In API")
    @Test(dataProviderClass = StaticTestData.class, dataProvider = "updateOptInAPIData")
    public void updateOptInAPI(String scenario, String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput, String user) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ scenario + "</em></h4>");
        Response response = getAPIResponse(method, url, headers, params, body, user);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
        compareResponse(expectedOutput, response.asString());

    }

    private void compareResponse(String expResponse, String actResponse) throws JSONException {
        if(expResponse.equals(""))
            expResponse = null;
        if(actResponse.equals(""))
            actResponse = null;

        JSONAssert.assertEquals(expResponse, actResponse,false);
    }

}