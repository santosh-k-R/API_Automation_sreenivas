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
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;

@Feature("HealthStatus")
public class AccessPoliciesIT {

    private static final String DIR = ControlPointData.getDIR();
    private static String invalidString = "/invalid";
    private static String invalidStringTwo = "invalid";
    private static String validString = "valid";
    private static String trueValue = "true";
    private static String falseValue = "false";
    private static String successValue = "success";
    private static String invalidBearerToken = "Bearer ldslfjdldsjfldasfasdfsf";
    private static String scheduleID;
    private static String ieVersion;
    private String mgmtSystemId, policyId = "";
    private String replaceSystemIdString = "<REPLACE_mgmtSystemId_STRING>";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private String replacePolicyId = "<REPLACE_policyId_STRING>";
    private String replaceMonthValue = "<REPLACE_Month_STRING>";
    private String replaceInsightType = "<REPLACE_InsightType_STRING>";
    private String replaceSAID = "<REPLACE_saId_STRING>";
    private List<UserRolesPojo> userRolesPojoList;
    private static List aMgmtSystemIdList;
    private static List releaseVersionListFromES;
    private static String commonUserGroupName;
    private static String AccessPolicyName;
    private static String policyRoles;
    private static String AssetGroupId;
    private static String AssetGroupName;
    private static List<String> policyID;
    private String replaceAccessPolicyId = "<REPLACE_policyID_STRING>";

    private static int indexOfMgmtSystemID;
    String groupNameForValidation = "";
    private String superAdmin = "SUPERADMIN";
    private String admin = "ADMIN";

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "VerifyCreateAccessPolicyPOSTAPIDp",
            dataProviderClass = ControlPointData.class, description = "access-policies/{ValidcustomerId}")
    public void VerifyCreateAccessPolicyPOSTAPI(ControlPointRequestPojo.data data) {

        JsonPath js = new JsonPath(data.getBody());
        AccessPolicyName = js.get("name");
        List<String> commonUserGroupName = js.getList("userGroups", String.class);
        policyRoles = js.get("roles");

        List<String> AssetIdDetails = js.getList("assetGroups.id", String.class);
        List<String> AssetGrpNameDetails = js.getList("assetGroups.name", String.class);

        Response response = RestUtils.post(data.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");


        Assert.assertEquals(response.getStatusCode(), 201, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }
    // Verification of created policy
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/validate/{customerId}?policy-name")
    public void VerifyCreatedAccessPolicyGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyGETAPIDp.getEndPointTwo(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 409, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    // Verification of created policy with valid policyname and invalid customerId
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/validate/{InvalidcustomerId}?policy-name")
    public void VerifyCreatedAccessPolicyInvalidCustomerIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyCreatedAccessPolicyInvalidCustomerIdGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }




    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?")
    public void VerifyAccessPolicyGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?policy-name")
    public void VerifyAccessPolicyPolicyNameGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyGETAPIDp.getEndPointThree(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyUserGroupNameGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?userGroups")
    public void VerifyAccessPolicyUserGroupNameGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyUserGroupNameGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyUserGroupNameGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?assetGroups")
    public void VerifyAccessPolicyAssetGroupNameGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyUserGroupNameGETAPIDp.getEndPointTwo(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }
    // GET Policies based on roles
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyUserGroupNameGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?roles")
    public void VerifyAccessPolicyRolesGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyUserGroupNameGETAPIDp.getEndPointThree(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }
    // GET Policies based on policyName
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicypolicyNameGETAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?roles")
    public void VerifyAccessPolicypolicyNameGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyUserGroupNameGETAPIDp.getEndPoint()
                .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    // Access-policy-discovery

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyDiscoveryGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/access-policy-discovery/{ccoid}")
    public void VerifyAccessPolicyDiscoveryGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyDiscoveryGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    // Access-policy-discovery with invalid ccoid

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyDiscoveryGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/access-policy-discovery/{ccoid}")
    public void VerifyAccessPolicyDiscoveryInvalidCCOIDGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.get(VerifyAccessPolicyDiscoveryGETAPIDp.getEndPointTwo(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"User not found");
        System.out.println("API response is :- " + response.then().log().all());

    }
      // Search Access Policy API
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyGETPolicyIdAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI" },
            dataProviderClass = ControlPointData.class, description = "/v1/access-policies/searchKey")
    public void VerifyAccessPolicyGETPolicyIdAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyGETPolicyIdAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        policyID = responseBody.getList("data.accessPolicyId", String.class);
        System.out.println("API response is :- " + response.then().log().all());
        System.out.println(policyID);
    }

      // update Access Policy PUT Req
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "UpdateAccessPolicyPUTAPIDp",
            dependsOnMethods = { "VerifyCreateAccessPolicyPOSTAPI","VerifyAccessPolicyGETPolicyIdAPI"},
            dataProviderClass = ControlPointData.class, description = "access-policies/{customerId/ValidPolicyId}")
    public void UpdateAccessPolicyPUTAPI(ControlPointRequestPojo.data data) {

        JsonPath js = new JsonPath(data.getBody());
        AccessPolicyName = js.get("name");
        List<String> commonUserGroupName = js.getList("userGroups", String.class);
        policyRoles = js.get("roles");

        Response responseGet = RestUtils.put(UpdateAccessPolicyPUTAPIDp.getEndPoint().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()).replaceAll(replaceAccessPolicyId,policyID.get(0)),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(responseGet.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        Assert.assertEquals(responseGet.getStatusCode(), 204, "Status Code:");
        System.out.println("API response is :- " + responseGet.then().log().all());

    }

    // Invalid policyId

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "UpdateAccessPolicyPUTAPIInvalidRequestDp",
            dataProviderClass = ControlPointData.class, description = "access-policies/{customerId/InvalidPolicyId}")
    public void UpdateAccessPolicyPUTAPIInvalidRequest(ControlPointRequestPojo.data data) {

        JsonPath js = new JsonPath(data.getBody());
        AccessPolicyName = js.get("name");
        List<String> commonUserGroupName = js.getList("userGroups", String.class);
        policyRoles = js.get("roles");

        Response responseGet = RestUtils.put(UpdateAccessPolicyPUTAPIInvalidRequestDp.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(responseGet.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(responseGet.getStatusCode(), 400, "Status Code:");
    }

    // When policyId doesn't match for given customerId
    @Test(enabled = true, groups = {"sanity"}, dataProvider = "UpdateAccessPolicyPUTAPIDp",
            dataProviderClass = ControlPointData.class, description = "access-policies/{customerId/{PolicyId}mismatch from customerId")
        public void UpdateInvalidPolicyIdPUTAPI(ControlPointRequestPojo.data data) {

        JsonPath js = new JsonPath(data.getBody());
        AccessPolicyName = js.get("name");
        List<String> commonUserGroupName = js.getList("userGroups", String.class);
        policyRoles = js.get("roles");

        Response responseGet = RestUtils.put(UpdateInvalidPolicyIdPUTAPIDp.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        Assert.assertEquals(responseGet.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(responseGet.getStatusCode(), 404, "Status Code:");


    }

    // GET Access Policy by User Group
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAccessPolicyUserGroupNameGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "/v1/nrs/access-policies/{customerId}/page/rows?policy")
    public void VerifyAccessPolicyPolicyIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAccessPolicyUserGroupNameGETAPIDp.getEndPointThree(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }
    // Delete Access Policy
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "DeleteAccessPolicyInvalidPolicyIdAPIDp",
            dataProviderClass = ControlPointData.class, description = "access-policies/{customerId}/{InValidPolicyId}")
    public void DeleteAccessPolicyInvalidPolicyIdAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.delete(DeleteAccessPolicyInvalidPolicyIdAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());

    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "DeleteAccessPolicyAPIDp",
            dependsOnMethods = { "VerifyAccessPolicyGETPolicyIdAPI","UpdateAccessPolicyPUTAPI" },
            dataProviderClass = ControlPointData.class, description = "access-policies/{customerId}/{ValidPolicyId}")
    public void DeleteAccessPolicyAPI(ControlPointRequestPojo.data data) {

        Response responseGet = RestUtils.delete(DeleteAccessPolicyAPIDp.getEndPoint().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId()).replaceAll(replaceAccessPolicyId,policyID.get(0)),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(responseGet.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        Assert.assertEquals(responseGet.getStatusCode(), 200, "Status Code:");



}}