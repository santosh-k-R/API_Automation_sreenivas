package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.pojo.request.UserRolesPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.Test;
import java.io.FileReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;

@Feature("HealthStatus")

public class BulkUserIT {

    private static final String DIR = ControlPointData.getDIR();
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";


    // Verification of bulk import status
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportStatusGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/status/Valid customerId/valid ref Id")
    public void VerifyBulkImportStatusGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyBulkImportStatusGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }
    //with valid customer and without refId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportStatusWithoutRefIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/status/Valid customerId/no refId")
    public void VerifyBulkImportStatusWithoutRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportStatusWithoutRefIdGETAPIDp.getEndPointTwo()
                .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        softAssert.assertEquals(response.getStatusCode(),400);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Missing 'refId'");
        System.out.println("API response is :- " + response.then().log().all());


    }
    //with valid customerId and blank refId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportStatusWithoutRefIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/status/Valid customerId/no refId")
    public void VerifyBulkImportStatusBlankRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportStatusWithoutRefIdGETAPIDp.getEndPoint()
                .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        softAssert.assertEquals(response.getStatusCode(),400);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"refId:must not be blank");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //with valid customerId and Invalid refId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportStatusWithoutRefIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/status/Valid customerId/Invalid refId")
    public void VerifyBulkImportStatusInvalidRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportStatusWithoutRefIdGETAPIDp.getEndPointThree()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),404);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"No Records found");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //with Invalid customerId and valid refId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportStatusGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/status/InValid customerId/validrefId")
    public void VerifyBulkImportStatusInvalidCustIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportStatusGETAPIDp.getEndPointTwo()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),403);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"403 FORBIDDEN");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //Verify BulkImport Report API
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportReportGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/report/Valid customerId/validrefId")
    public void VerifyBulkImportReportGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportReportGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),200);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Error,Email doesn't match");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //Verify BulkImport Report API
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportReportGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/report/Valid customerId/InvalidrefId")
    public void VerifyBulkImportReportInvalidRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportReportGETAPIDp.getEndPointTwo()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),404);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"No Records found");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //Verify BulkImport Report API
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportReportGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/report/Valid customerId/BlankrefId")
    public void VerifyBulkImportReportBlankRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportReportGETAPIDp.getEndPointThree()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),400);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"refId:must not be blank");
        System.out.println("API response is :- " + response.then().log().all());
    }

    //Verify BulkImport Report API with valid customerId and without refId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportReportWithoutRefIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/report/Valid customerId/WithoutrefId")
    public void VerifyBulkImportReportWithoutRefIdGETAPI(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyBulkImportReportWithoutRefIdGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        softAssert.assertEquals(response.getStatusCode(),400);
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Missing 'refId'");
        System.out.println("API response is :- " + response.then().log().all());


    }

    //Verify BulkImport Post API with Valid Payload
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportPayloadPOSTAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/")
    public void VerifyBulkImportPayloadPOSTAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        JsonPath js = new JsonPath(data.getBody());
        Response response = RestUtils.post(data.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Users Imported Successfully");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    //Verify BulkImport Post API without CustomerId in  Payload
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportPayloadWithoutCustomerPOSTAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/")
    public void VerifyBulkImportPayloadWithoutCustomerPOSTAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        JsonPath js = new JsonPath(data.getBody());
        Response response = RestUtils.post(data.getEndPoint(),
                data.getBody(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(), "customerId is mandatory");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    //Verify BulkImport Post API without CustomerId in  Payload
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyBulkImportPayloadWithoutRefIdPOSTAPIDp",
            dataProviderClass = ControlPointData.class, description = "rbac/v1/user/bulkImport/")
    public void VerifyBulkImportPayloadWithoutRefIdPOSTAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        JsonPath js = new JsonPath(data.getBody());
        Response response = RestUtils.post(data.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(), "refId is mandatory");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }


}
