package com.cisco.services.api_automation.tests.riskmitigation;
import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData.*;
import static com.cisco.services.api_automation.utils.Commons.constructHeader;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Feature("RiskMitigation APIS")
public class RiskMitigationIT {

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = RiskMitigationData.class, dataProvider = "RiskMitigationStaticData")
    public void verifyRiskmitigationAPIs(String TestID,String name,String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput) throws JSONException {
    	//context.setAttribute("testName",testName);
        Response response = getAPIResponse(method, url, headers, params, body);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
        JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(),true);
    }


    @Severity(SeverityLevel. NORMAL)
    @Test(dataProviderClass = RiskMitigationData.class, dataProvider = "RiskMitigationInvalidStaticData")
    public void verifyRiskmitigationInvalidAPIs(String TestID,String name,String method, String url, String headers, String params, String body, String expectedStatusCode) throws JSONException {
    	//context.setAttribute("testName",testName);
        Response response = getAPIResponse(method, url, headers, params, body);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
    }
}
