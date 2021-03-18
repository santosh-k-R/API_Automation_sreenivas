package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static io.restassured.RestAssured.given;

@Feature("HealthStatus")
public class PoliciesIT {

    private static final String DIR = ControlPointData.getDIR();
    private static String validString = "valid";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private static List<String> remoteNoteID;
    private static String scheduledID;
    private static List<String> policyID;
    private static String DevicePolicyID;
    private static String collectorId;
    private static List<String> mgmntSystemId;
    private String replaceAccessPolicyId = "<REPLACE_policyID_STRING>";
    private String replaceMgmntSystemId="<REPLACE_mgmntSystemId_STRING>";
    private String replaceDevicepolicyId="<REPLACE_DevicePolicyID_STRING>";
    private String replaceCollectorIdString="<REPLACE_collectorId_STRING>";



    //  Get policies list for given customerId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyALLPoliciesGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID")
    public void VerifyALLPoliciesGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyALLPoliciesGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
        policyID = responseBody.getList("policyId", String.class);
        if (policyID == null)
            System.out.println("There is no policy created for given CustomerId");
        else System.out.println("The PolicyId is :-" + policyID);

    }

    // Get policies for Invalid customerId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyALLPoliciesInvalidCustomerIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{InValidCustomerID")
    public void VerifyALLPoliciesInvalidCustomerIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyALLPoliciesInvalidCustomerIdGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Invalid Path

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPoliciesInvalidPathGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/{validCustomerId}")
    public void VerifyPoliciesInvalidPathGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPoliciesInvalidPathGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get policies for the given month & Year
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyALLPoliciesInvalidCustomerIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID}/{month}/{year}")
    public void VerifyPoliciesMonthYearGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPoliciesMonthYearGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get Policies with month and without Year in path param
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPoliciesMonthNoYearGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID}/{month}/No Year")
    public void VerifyPoliciesMonthNoYearGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPoliciesMonthNoYearGETAPIDp.getEndPoint()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get Policies without month and with Year in path param
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPoliciesMonthNoYearGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID}/{month}/No Year")
    public void VerifyPoliciesNoMonthYearGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPoliciesMonthNoYearGETAPIDp.getEndPointTwo()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get Policies with invalid month and with Year in path param
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPoliciesMonthNoYearGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID}/InvalidMonth13/validYear")
    public void VerifyPoliciesInvalidmonthGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyPoliciesMonthNoYearGETAPIDp.getEndPointThree()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Month : 13 is not a valid number");
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get Policies with valid month and Invalid Year in path param
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPoliciesMonthYearGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/policies/{ValidCustomerID}/validMonth/InvalidYear20201")
    public void VerifyPoliciesInvalidYearGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyPoliciesMonthYearGETAPIDp.getEndPointTwo()
                        .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Year : 20201 is not a valid number");
        System.out.println("API response is :- " + response.then().log().all());
    }
    // Getting DNACs for given customerId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDNACGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/dnacs/validCustomerId")
    public void VerifyDNACGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyDNACGETAPIDp.getEndPoint()
                .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        collectorId=responseBody.getString("applianceId");
        System.out.println("API response is :- " + response.then().log().all());
        System.out.println("CollectorId is  :- " + collectorId);


    }
    // Getting dnac's for Valid CustomerId & Collector Id

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDNACGETAPIDp",
            dependsOnMethods = { "VerifyDNACGETAPI"},
            dataProviderClass = ControlPointData.class, description = "/v1/dnacs/validCustomerId/ValidCollectorId")
    public void VerifyDnacCollectorIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyDNACGETAPIDp.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()).replaceAll(replaceCollectorIdString,collectorId),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        JsonPath responseBodyGet = response.getBody().jsonPath();
        mgmntSystemId = responseBodyGet.getList("dnacInfo.mgmtSystemId.flatten()", String.class);

        System.out.println("API response is :- " + response.then().log().all());
        System.out.println("management System Id is :-" +mgmntSystemId);
    }

//    // Try to get Dnacs with invalid customerId and Valid CollectorId
//
//    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDnacWithoutCustomerIdGETAPIDp",
//            dataProviderClass = ControlPointData.class, description = "/v1/dnacs/InvalidCustomerId/ValidCollectorId")
//    public void VerifyDnacWithoutCustomerIdGETAPI(ControlPointRequestPojo.data data) {
//
//        SoftAssert softAssert = new SoftAssert();
//        Response response = RestUtils.get(VerifyDnacWithoutCustomerIdGETAPIDp.getEndPoint(),
//                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
//        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
//        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"No Dnacs configured for this CustomerId");
//        JsonPath responseBody = response.getBody().jsonPath();
//        System.out.println("API response is :- " + response.then().log().all());
//
//    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDNACPolicyIdGETAPIDp",
            dependsOnMethods = { "VerifyALLPoliciesGETAPI"},
            dataProviderClass = ControlPointData.class, description = "/v1/dnacs/validCustomerId/ValidPolicyId")
    public void VerifyDNACPolicyIdGETAPI(ControlPointRequestPojo.data data) {

     Response responseGet = RestUtils.get(VerifyDNACPolicyIdGETAPIDp.getEndPoint().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()).replaceAll(replaceAccessPolicyId,policyID.get(0)),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(responseGet.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = responseGet.getBody().jsonPath();
        System.out.println("API response is :- " + responseGet.then().log().all());
    }

    // Verification with Assets present or Not

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDNACAssetPresentGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/dnacs/validCustomerId?AssetPresent")
    public void VerifyDNACAssetPresentGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyDNACAssetPresentGETAPIDp.getEndPoint()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Creation of device policy

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyCreateDeviceScanPolicyPOSTAPIDp",
            dataProviderClass = ControlPointData.class, description = "v1/policies/{ValidcustomerId}")
    public void VerifyCreateDeviceScanPolicyPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.post(VerifyCreateDeviceScanPolicyPOSTAPIDp.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");


        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        DevicePolicyID= responseBody.getString("policyId");
        System.out.println("API response is :- " + response.then().log().all());
        System.out.println("DevicePolicyId is :- " +DevicePolicyID );

    }
     // Update Device Policy

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyUpdateDeviceScanPolicyPATCHAPIDp",
            dependsOnMethods = ("VerifyCreateDeviceScanPolicyPOSTAPI"),
            dataProviderClass = ControlPointData.class, description = "v1/policies/{ValidcustomerId}")
    public void VerifyUpdateDeviceScanPolicyPATCHAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.patch(VerifyUpdateDeviceScanPolicyPATCHAPIDp.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        DevicePolicyID= responseBody.getString("policyId");
        System.out.println("API response is :- " + response.then().log().all());
        System.out.println("DevicePolicyId is :- " +DevicePolicyID );


    }

    // Get device scan policy for Valid CustomerId ,Solution,CollectorId & mgmtSystemId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPolicyDevicesGETAPIDp",
            dependsOnMethods ={"VerifyDNACGETAPI","VerifyDnacCollectorIdGETAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/devices/CustomerId?solution&mgmtSystemId&collectorId")
    public void VerifyPolicyDevicesGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPolicyDevicesGETAPIDp.getEndPoint()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId().replaceAll(replaceMgmntSystemId,mgmntSystemId.get(0))
                                .replaceAll(replaceCollectorIdString,collectorId)),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get device scan policy for Valid CustomerId ,Solution, mgmtSystemId and missing CollectorId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPolicyDevicesGETAPIDp",
            dependsOnMethods ={"VerifyDnacCollectorIdGETAPI","VerifyDNACGETAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/devices/CustomerId?solution&mgmtSystemId&collectorId")
    public void VerifyPolicyDevicesMissingCollectorIdGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyPolicyDevicesGETAPIDp.getEndPointTwo()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId().replaceAll(replaceMgmntSystemId,mgmntSystemId.get(0))),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Missing 'collectorId'");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Get device scan policy for Valid CustomerId ,Solution, mgmtSystemId,CollectorId & Pagination

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPolicyDevicesGETAPIDp",
            dependsOnMethods ={"VerifyDNACGETAPI","VerifyDnacCollectorIdGETAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/devices/CustomerId?solution&mgmtSystemId&collectorId&page&rows")
    public void VerifyPolicyDevicesPaginationGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPolicyDevicesGETAPIDp.getEndPointThree()
                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId().replaceAll(replaceMgmntSystemId,mgmntSystemId.get(0))
                        .replaceAll(replaceCollectorIdString,collectorId)),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }


    // Get Policy devices with PolicyId as query param

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPolicyWithPolicyIdGETAPIDp",
            dependsOnMethods = { "VerifyCreateDeviceScanPolicyPOSTAPI","VerifyDnacCollectorIdGETAPI" },
            dataProviderClass = ControlPointData.class, description = "v1/policy/devices/CustomerId?solution&mgmtSystemId&collectorId&policyId&page&rows")
    public void VerifyPolicyWithPolicyIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyPolicyWithPolicyIdGETAPIDp.getEndPoint()
                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId().replaceAll(replaceDevicepolicyId,DevicePolicyID).
        replaceAll(replaceMgmntSystemId,mgmntSystemId.get(0))),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }


    // Try to get device policy without mgmntSystemId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyPolicyWithPolicyIdGETAPIDp",
            dependsOnMethods = { "VerifyCreateDeviceScanPolicyPOSTAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/devices/CustomerId?solution&collectorId&page&rows")
    public void VerifyPolicyMissingMgmntSystemIdIdGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyPolicyWithPolicyIdGETAPIDp.getEndPointTwo()
                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()).replaceAll(replaceDevicepolicyId,DevicePolicyID),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Missing 'mgmtSystemId'");
        System.out.println("API response is :- " + response.then().log().all());
    }


    //Try to delete policy without policyId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDeletePolicyAPIDp",
           // dependsOnMethods = { "VerifyCreateDeviceScanPolicyPOSTAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/CustomerId/?")
    public void VerifyDeletePolicyWithoutPolicyIdAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.delete(VerifyDeletePolicyAPIDp.getEndPointTwo()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    //Try to delete policy with Invalid policyId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDeletePolicyAPIDp",
            // dependsOnMethods = { "VerifyCreateDeviceScanPolicyPOSTAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/CustomerId/InvalidPolicyId?")
    public void VerifyDeletePolicyWithInvalidPolicyIdAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.delete(VerifyDeletePolicyAPIDp.getEndPointThree()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Policy doesn't exist. Please delete a valid policy");
        System.out.println("API response is :- " + response.then().log().all());
    }


    // Delete device Policy

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyDeletePolicyAPIDp",
            dependsOnMethods = { "VerifyCreateDeviceScanPolicyPOSTAPI"},
            dataProviderClass = ControlPointData.class, description = "v1/policy/CustomerId/policyId?")
    public void VerifyDeletePolicyAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.delete(VerifyDeletePolicyAPIDp.getEndPoint()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()).replaceAll(replaceDevicepolicyId,DevicePolicyID),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Policy deleted");
        System.out.println("API response is :- " + response.then().log().all());
    }


}
