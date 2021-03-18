package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.pojo.request.LifecycleDataPojo;
import com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.results.ID;
import com.cisco.services.api_automation.utils.results.TestResults;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.getSchemaFromResourceDir;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;
import static com.cisco.services.api_automation.utils.auth.TokenGenerator.getToken;
import static com.cisco.services.api_automation.utils.customassert.Assert.assertEquals;
import static com.cisco.services.api_automation.utils.customassert.Assert.assertTrue;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Link("")
@Feature("LifeCycle APIS")
public class LifeCycleIT {

        List<String> users = Arrays.asList("standardUserL2", "standardUserL2UI", "MACHINE", "INVALID", "assetUserL2", "superAdminL2", "readOnlyUserL2", "standardUserL1", "adminL2");
//    List<String> users = Arrays.asList("MACHINE", "INVALID", "standardUserL2", "standardUserL2UI");

    @BeforeClass
    public void generateToken() {
        users.forEach(user -> getToken(new User(user)));
    }

    /*@ID(values = "Tm5661185c")
    @Test(description = "Test TIMS results with single test ID",enabled = false)
    public void singleTestID() {
        assertTrue(false);
    }

    @ID(values = {"Tm5661186c","Tm5661187c","Tm5661188c","Tm5661189c"})
    @Test(description = "Test TIMS results with Multiple test ID",enabled = false)
    public void multipleTestID() {
        assertTrue(true);
        System.out.println("First Test case Ends here");
        TestResults.add("Tm5661186c", TestResults.Results.PASSED, "First Testcase");

        assertTrue(true);
        System.out.println("Second Test case Ends here");
        TestResults.add("Tm5661187c", TestResults.Results.PASSED, "Second Testcase");

        assertTrue(false);
        System.out.println("Third Test case Ends here");
        TestResults.add("Tm5661188c", TestResults.Results.PASSED, "Third Testcase");

        assertTrue(false);
        System.out.println("Fourth Test case Ends here");
        TestResults.add("Tm5661189c", TestResults.Results.PASSED, "Fourth Testcase");
    }*/


    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "lifecycleJsonTestData")
    public void verifyLifecycleAPIs(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());

        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");
        String actualResponseBody = response.getBody().asString();

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");


    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ACC_REG")
    public void verifyACCPostRequest(LifecycleDataPojo data) throws JSONException {
        try {
            Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
            deleteACCRequest("G5P8lsEowYcKNZ", "IBN", "standardUserL2");
            deleteACCRequest("G5P8lsEowYcKNZ", "IBN", "standardUserL2UI");
            data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
            Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
            String actualResponseBody = response.getBody().asString();
            assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

            if (null != data.getSchema()) {
                response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
                return;
            }

            if (data.getResponseFormat().equals("json"))
                JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
            else if (data.getResponseFormat().equals("text"))
                assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
        } finally {
            deleteACCRequest("G5P8lsEowYcKNZ", "IBN", "standardUserL2");
        }
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ATX_REG")
    public void verifyATXPostRequest(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ATX_OPTIONAL_PARAMS")
    public void verifyAtxOptionalParams(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            int items = response.jsonPath().getList("items").size();
            assertEquals(items, 1);
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ATX_FILTER")
    public void verifyATXFilter(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            response.jsonPath().getList("items.providerInfo").forEach(Assert::assertNull);
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ACC_OPTIONAL_PARAMS")
    public void verifyOptionalParams(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            response.jsonPath().getList("items.status").forEach(status -> assertEquals(status, "recommended"));
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "ACC_FILTER")
    public void verifyACCFilter(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            response.jsonPath().getList("items.status").forEach(status -> assertEquals(status, "recommended"));
            response.jsonPath().getList("items.providerInfo").forEach(Assert::assertNull);
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "feedback")
    public void verifyFeedback(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "email")
    public void verifyEmail(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "notification")
    public void verifyNotification(LifecycleDataPojo data) throws JSONException {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>" + data.getComponent() + " :: " + data.getName() + "</em></h4>");
        data.getContext().setAttribute("testName", data.getComponent() + " :: " + data.getName());
        Response response = getAPIResponse(data.getMethod(), data.getUrl(), data.getHeaders(), data.getParams(), data.getBody(), data.getUserRole());
        String actualResponseBody = response.getBody().asString();
        assertEquals(response.getStatusCode(), Integer.parseInt(data.getStatusCode()), "Status Code: ");

        if (null != data.getSchema()) {
            response.then().body(matchesJsonSchema(getSchemaFromResourceDir(data.getSchema()))).log().ifValidationFails();
            return;
        }

        if (data.getResponseFormat().equals("json"))
            JSONAssert.assertEquals(JSONParser.parseJSON(data.getOutput()).toString(), JSONParser.parseJSON(actualResponseBody).toString(), Boolean.parseBoolean(data.getMode()));
        else if (data.getResponseFormat().equals("text"))
            assertEquals(actualResponseBody, data.getOutput(), "Comparing Actual and Expected Response");
    }

    public void deleteACCRequest(String customerId, String solution, String userRole) {

        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            Response response = getAPIResponse("GET", "lifecycle/racetrack/v2/acc/", "",
                    "solution=" + solution + "&pitstop=" + currentPitStops.get(useCase) + "&usecase=" + useCase + "&customerId=" + customerId, "",
                    userRole);

            List<Map<String, String>> items = response.jsonPath().getList("items");
            items.stream()
                    .filter(item -> item.get("status").equals("requested"))
                    .forEach(item -> {
                        System.out.println("Running Delete ACC Request...");
                        Response resp = getAPIResponse("DELETE", "lifecycle/racetrack/v2/acc/" + item.get("requestId") + "/delete", "",
                                "customerId=" + customerId,
                                "", userRole);

                        if (resp.getStatusCode() == 200)
                            System.out.println("Requested ACC Deleted Successfully");
                    });
        }
    }

    public Map<String, String> getUserCurrentPitStopForAllUseCase(String customerId, String userRole) {
        Map<String, String> currentPitStop = new HashMap<>();
        String params = "customerId=" + customerId;
        Response response = getAPIResponse("GET", "lifecycle/racetrack/v2/pitstop/info", "", params, "", userRole);
        List<Map<String, String>> data = response.jsonPath().getList("items.usecases.flatten()");
        data.forEach(item -> currentPitStop.put(item.get("name"), item.get("currentPitstop")));
        return currentPitStop;
    }

}

