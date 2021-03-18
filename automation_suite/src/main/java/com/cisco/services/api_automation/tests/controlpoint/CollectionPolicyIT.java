package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
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

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.controlPointCommonData;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static io.restassured.RestAssured.given;

@Feature("HealthStatus")
public class CollectionPolicyIT {

    private static String validString = "valid";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private String replaceCollectorIdString = "<REPLACE_collectorId_STRING>";
    private String replacePolicyIdString ="<REPLACE_policyId_STRING>";
    private String replaceMgmtSystemAddrString = "<REPLACE_mgmtSystemAddr_STRING>";
    private String replaceMgmtSystemIdString = "<REPLACE_mgmtSystemId_STRING>";
    private String replaceDNACNameString = "<REPLACE_dnacName_STRING>";
    private static List<String> remoteNoteID;
    private static List<String> collectorId;
    private static String scheduledID;

    /**
     * Test Methods used to validate
     * collectionPolicy API's
     *
     * @author Vinay Jengiti
     */


    @Test(enabled = true, groups = {"sanity"}, dataProvider = "collectionPolicyNextScheduleGETDp",
            dataProviderClass = ControlPointData.class)
    public void verifyCollectionPolicyNextScheduleGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        JsonPath responseBody = null;
                SoftAssert softAssert = new SoftAssert();

        //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);
        collectorId = responseBodyGet.getList("collectorId.flatten()", String.class);

        if(data.getTokenType().equalsIgnoreCase(validString)) {
            response = RestUtils.get((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()));
             responseBody = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }
        else
        {
            response = RestUtils.get((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()), new User("invalid"));
             responseBody = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString)) {

            List<String> aCustomerIDList = responseBody.getList("data.customerId.flatten()",String.class);
            List<String> aRemoteNodeIdList = responseBody.getList("data.remoteNodeId.flatten()",String.class);
            collectorId = responseBody.getList("data.collectorId.flatten()",String.class);

            softAssert.assertTrue(aCustomerIDList.contains(controlPointCommonData.getCustomerId()),
                    "CustomerID" + controlPointCommonData.getCustomerId());
            softAssert.assertTrue(aRemoteNodeIdList.contains(remoteNoteID.get(0).toString()), "remoteNoteID" + remoteNoteID);

        }

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");



        softAssert.assertAll("Asserting the Response");
    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "nextScheduleForCollectorIDGETDp",
            dependsOnMethods = "verifyCollectionPolicyNextScheduleGETAPI",
            dataProviderClass = ControlPointData.class)
    public void verifyNextScheduleForCollectorIDGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        SoftAssert softAssert = new SoftAssert();
        List<String> collectorIdFromResponse;

        if(data.getTokenType().equalsIgnoreCase(validString)) {
            response = RestUtils.get((data.getEndPoint()
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()))
                    .replace(replaceCollectorIdString, collectorId.get(0)));
        }
        else {
            response = RestUtils.get((data.getEndPoint()
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()))
                    .replace(replaceCollectorIdString, collectorId.get(0)), new User("invalid"));
        }
        Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

        if(data.getTestType().equalsIgnoreCase(validString)) {

            JsonPath responseBody = response.getBody().jsonPath();
            List<String> aCustomerIDList = responseBody.getList("data.customerId.flatten()",String.class);
            List<String> aRemoteNodeIdList = responseBody.getList("data.remoteNodeId.flatten()",String.class);

            collectorIdFromResponse = responseBody.getList("data.collectorId.flatten()",String.class);

            softAssert.assertTrue(aCustomerIDList.contains(controlPointCommonData.getCustomerId()),
                    "CustomerID" + controlPointCommonData.getCustomerId());
            softAssert.assertTrue(aRemoteNodeIdList.contains(remoteNoteID.get(0)), "remoteNoteID" + remoteNoteID);
            softAssert.assertTrue(collectorIdFromResponse
                    .contains(collectorId.get(0)), "collectorId" + collectorId.get(0));
            softAssert.assertAll("Asserting the Response");

        }

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

    }


    @Test(enabled = true, groups = {"sanity"}, dataProvider = "collectionPolicyPATCHAPIDp",
            dataProviderClass = ControlPointData.class)
    public void verifyCollectionPolicyPATCHAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        SoftAssert softAssert = new SoftAssert();
        String collectionPolicyID = "";
        String collectionMgmtSysAdr = "";
        String collectionMgtSysID = "";
        String collectionDNACName = "";
        List<String> aPolicyIdPatchList,aMgmtSystemAddrPatchList,aMgmtSystemIdPatchList,aDnacNamePatchList, aSchedulePatchList, aTimeZonePatchList = null;


                Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        List<String> collectionPolicyType = responseBodyGet.getList("policyType");

        JsonPath oriBody = new JsonPath(data.getBody()
                .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                .replace(replacePolicyIdString, collectionPolicyID)
                .replace(replaceMgmtSystemAddrString, collectionMgmtSysAdr)
                .replace(replaceMgmtSystemIdString, collectionMgtSysID)
                .replace(replaceDNACNameString, collectionDNACName));

        for (int i=0; i<collectionPolicyType.size();i++)
        {
            if(collectionPolicyType.get(i).equalsIgnoreCase("COLLECTION"))
            {
                collectionPolicyID = responseBodyGet.getList("policyId.flatten()").get(i).toString();
                collectionMgmtSysAdr = responseBodyGet.getList("dnacInfo.mgmtSystemAddr.flatten()").get(i).toString();
                collectionMgtSysID = responseBodyGet.getList("dnacInfo.mgmtSystemId.flatten()").get(i).toString();
                collectionDNACName = responseBodyGet.getList("dnacInfo.dnacName.flatten()").get(i).toString();
            }
        }

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.patch(data.getEndPoint().replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    (data.getBody()
                            .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                            .replace(replacePolicyIdString, collectionPolicyID)
                            .replace(replaceMgmtSystemAddrString, collectionMgmtSysAdr)
                            .replace(replaceMgmtSystemIdString, collectionMgtSysID)
                            .replace(replaceDNACNameString, collectionDNACName)),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath responseBody = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

            Response responseGetAfterPatch = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId())),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath responseBodyAfterPatch = responseGetAfterPatch.getBody().jsonPath();
            List<String> collectionPolicyTypeAfterPatch = responseBodyGet.getList("policyType");

            if (data.getTestType().equalsIgnoreCase(validString)) {

                Assert.assertEquals(controlPointCommonData.getCustomerId(), responseBody.getString("customerId"), "customerId");
                Assert.assertEquals(collectionPolicyID, responseBody.getString("policyId"), "policyId");

                for (int i = 0; i < collectionPolicyTypeAfterPatch.size(); i++) {

                    if (collectionPolicyTypeAfterPatch.get(i).equalsIgnoreCase("COLLECTION")) {

                        aPolicyIdPatchList = responseBodyAfterPatch.getList("policyId.flatten()", String.class);
                        aMgmtSystemAddrPatchList = responseBodyAfterPatch.getList("dnacInfo.mgmtSystemAddr.flatten()", String.class);
                        aMgmtSystemIdPatchList = responseBodyAfterPatch.getList("dnacInfo.mgmtSystemId.flatten()", String.class);
                        aDnacNamePatchList = responseBodyAfterPatch.getList("dnacInfo.dnacName.flatten()", String.class);
                        aSchedulePatchList = responseBodyAfterPatch.getList("schedule.flatten()", String.class);
                        aTimeZonePatchList = responseBodyAfterPatch.getList("timeZone.flatten()", String.class);

                        softAssert.assertTrue(aPolicyIdPatchList.contains(collectionPolicyID),
                                "PolicyID::" + collectionPolicyID);
                        softAssert.assertTrue(aMgmtSystemAddrPatchList.contains(collectionMgmtSysAdr),
                                "MgmtSysAdr::" + collectionMgmtSysAdr);
                        softAssert.assertTrue(aMgmtSystemIdPatchList.contains(collectionMgtSysID),
                                "aMgmtSystemId::" + collectionMgtSysID);
                        softAssert.assertTrue(aDnacNamePatchList.contains(collectionDNACName),
                                "DNACName::" + collectionDNACName);
                        softAssert.assertTrue(aSchedulePatchList.contains(oriBody.get("schedule")),
                                "schedule::" + oriBody.get("schedule"));
                        softAssert.assertTrue(aTimeZonePatchList.contains(oriBody.get("timeZone")),
                                "timeZone::" + oriBody.get("timeZone"));
                        break;
                    }
                }
            }
            softAssert.assertAll("Asserting the Response");
        }
       else
       {
           response = RestUtils.patch(data.getEndPoint().replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                   (data.getBody()
                           .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                           .replace(replacePolicyIdString, collectionPolicyID)
                           .replace(replaceMgmtSystemAddrString, collectionMgmtSysAdr)
                           .replace(replaceMgmtSystemIdString, collectionMgtSysID)
                           .replace(replaceDNACNameString, collectionDNACName)),
                   Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

           Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
       }

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

    }

}
