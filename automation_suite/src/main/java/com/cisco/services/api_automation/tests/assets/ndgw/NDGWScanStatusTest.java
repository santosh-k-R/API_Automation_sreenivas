package com.cisco.services.api_automation.tests.assets.ndgw;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;
import com.cisco.services.api_automation.utils.ExcelReader;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.NETWORK_DATA_GW_DATA;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

@Feature("NDGW APIS")
public class NDGWScanStatusTest {
    SoftAssert softAssert;
    String errorInfo = null, message = null, groupId = null;
    Response response = null;
    private static String customerId = System.getenv("niagara_partyid");
    private AssetsDataReader ExcelDataReader;
    private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(NETWORK_DATA_GW_DATA);
    private long expectedResponseTime = 1000;

    /*
     * added by kywin
     */
    @Test(dataProviderClass = AssetsDataReader.class, dataProvider = "NDGWGenericKeys")
    public void validateNdgwRequestStatus__All(String key) throws JSONException {
        softAssert = new SoftAssert();
        AssetsAPIPojo apiObj = data.get(key);
        if(apiObj.getEndPoint().contains("/status/pagination")){
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() , "", apiObj.getParams(),
                    apiObj.getPayLoad(), "MACHINE");
        }else if (apiObj.getEndPoint().contains("/ndgw") && !apiObj.getParams().contains(customerId)) {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() , "", apiObj.getParams(),
                    apiObj.getPayLoad());
        } else {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
                    apiObj.getPayLoad());
        }
        softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
        if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
            errorInfo = response.jsonPath().get("reason.errorInfo");
            message = response.jsonPath().get("message");
            System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
        }else{
            System.out.println("Test Pass");
        }

        softAssert.assertAll();
    }

    @Test(dataProviderClass = AssetsDataReader.class, dataProvider = "NDGWGenericKeys")
    public void validateNdgwData__All(String key) throws JSONException, IOException {
        softAssert = new SoftAssert();
        String expectedResponse = null;
        AssetsAPIPojo apiObj = data.get(key);
        if(apiObj.getEndPoint().contains("/status/pagination")){
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() , "", apiObj.getParams(),
                    apiObj.getPayLoad(), "MACHINE");
        }else if (apiObj.getEndPoint().contains("/ndgw") && !apiObj.getParams().contains(customerId)) {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() , "", apiObj.getParams(),
                    apiObj.getPayLoad());
        } else {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
        }
        if (response.getStatusCode() == 200) {
            expectedResponse = apiObj.getExpectedResponse();
            if(apiObj.getMethod().equalsIgnoreCase("GET")){
                JSONAssert.assertEquals(expectedResponse, response.getBody().asString(), JSONCompareMode.LENIENT);
            }
            System.out.println("Test Pass");
        } else if (response.getStatusCode() != 200) {
            softAssert.assertFalse(true);
            errorInfo = response.jsonPath().get("reason.errorInfo");
            message = response.jsonPath().get("message");
            System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
        }
        softAssert.assertAll();
    }

    @Test(dataProviderClass = AssetsDataReader.class, dataProvider = "NDGWGenericKeys")
    public void validateNDGWRecordsCount__Scan_request_status_paginated(String key) throws JSONException {
        softAssert = new SoftAssert();
        AssetsAPIPojo apiObj = data.get(key);
        int actualRecordsCount = 0;
        if (apiObj.getEndPoint().contains("/ndgw") && !apiObj.getParams().contains(customerId)) {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint() , "", apiObj.getParams(),
                    apiObj.getPayLoad(), "MACHINE");
        } else {
            response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
        }
        if (response.getStatusCode() == 200) {
            actualRecordsCount = response.jsonPath().get("Pagination.total");
            softAssert.assertEquals(actualRecordsCount, Integer.parseInt(apiObj.getRecordCount()),
                    "Records Count Mistmatch");
        } else
            softAssert.assertFalse(true);
        if (response.getStatusCode() != 200) {
            errorInfo = response.jsonPath().get("reason.errorInfo");
            message = response.jsonPath().get("message");
            System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
        }
        softAssert.assertAll();
    }
}
