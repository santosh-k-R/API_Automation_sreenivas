package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
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
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;

@Feature("HealthStatus")
public class IgnorePolicyIT {

    private static String validString = "valid";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private static String ignorePolicyID,applianceId,mgmtSystemId;
    private String replaceIgnorePolicyId="<REPLACE_IgnoryPolicyId_STRING>";
    private String replaceApplianceId="<REPLACE_applianceId_STRING>";
    private String replaceMgmtSystemId="<REPLACE_mgmtSystemId_STRING>";

    private static String invalidIgnorePolicyID="1234567890909987654321";
    private static String invalidApplianceId="ABCD1234XYZ";
    private static String invalidMgmtSystemId="ab123-cd456-ef789-gh000";
    private static String invalidCustomerId="ABCDXYZEFGKLM";



    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"sanity"})
    public void validateIgnorePolicyCreation(){

        SoftAssert softAssert=new SoftAssert();
        Response response = RestUtils.post(ignorePolicyDp.getEndPoint(),
                ignorePolicyDp.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath jsonPath=response.getBody().jsonPath();

        //System.out.println("ResponseBody As String::"+response.getBody().asString());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        softAssert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        softAssert.assertEquals(jsonPath.get("customerId"),controlPointCommonData.getCustomerId());
        softAssert.assertTrue(null!=jsonPath.get("policyId"),"Policy Id not generated");
        if(null!=jsonPath.get("policyId")){        ignorePolicyID=jsonPath.get("policyId"); }

        softAssert.assertAll("ignorePolicyCreation");
    }

    //@Severity(SeverityLevel.NORMAL)
    //@Test(enabled = true, groups = {"sanity"})
    public void validateIgnorePolicyCreationInvalidPayload(){
        SoftAssert softAssert=new SoftAssert();
        Response response = RestUtils.post(invalidPayloadIgnorePolicyDDp.getEndPoint(),
                invalidPayloadIgnorePolicyDDp.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        //System.out.println("ResponseBody As String::"+response.getBody().asString());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        softAssert.assertEquals(response.getStatusCode(), 404, "Status Code not matching");
        softAssert.assertAll("ignorePolicyCreation");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"sanity"})
    public void validateIgnorePolicyCreationInvalidEndpoint() {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestUtils.post(invalidEndPointIgnorePolicyDDp.getEndPoint(),
                invalidEndPointIgnorePolicyDDp.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        //System.out.println("StatusCode::"+response.getStatusCode());
        softAssert.assertEquals(response.getStatusCode(),invalidEndPointIgnorePolicyDDp.getResponseCode(),"Create Ignore policy is working with wrong endpoint");
        softAssert.assertAll("IgnorePolicyCreationInvalidEndpoint");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"sanity"},dependsOnMethods = {"validateIgnorePolicyCreation"})
    public void validateSecondIgnorePolicyCreation(){
        SoftAssert softAssert=new SoftAssert();
        Response response = RestUtils.post(ignorePolicyDp.getEndPoint(),
                ignorePolicyDp.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        //System.out.println("Response Body::"+response.getBody().asString());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
         softAssert.assertEquals(response.getStatusCode(), 500, "Status Code is not 500");
         softAssert.assertAll("SecondIgnorePolicyCreation");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"sanity"},dependsOnMethods ={"validateSecondIgnorePolicyCreation"})
    public void validateUpdateIgnorePolicy() {
        Response response = RestUtils.patch(updateIgnorePolicyDp.getEndPoint(), updateIgnorePolicyDp.getBody().replaceAll(replaceIgnorePolicyId,ignorePolicyID), Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath jsonPath = response.getBody().jsonPath();
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        SoftAssert softAssert=new SoftAssert();
        //System.out.println("ResponseAsString::"+response.getBody().asString());
        softAssert.assertEquals(response.getStatusCode(),updateIgnorePolicyDp.getResponseCode());
        softAssert.assertAll("validateUpdateIgnorePolicy");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"sanity"},dependsOnMethods = {"validateUpdateIgnorePolicy"})
    public void validateIgnorePolicyExistingDevices() {
        getAppliacneIdAndMnmtSystemId();
        Response response=RestUtils.get(ignorePolicyExistingDevices.getEndPoint().replaceAll(replaceApplianceId,applianceId).replaceAll(replaceCustomerIdString,controlPointCommonData.getCustomerId()).replaceAll(replaceIgnorePolicyId,ignorePolicyID));
        System.out.println("ExistingDevicesData:::"+response.getBody().jsonPath().getList("data").get(0).toString()+" --Size::"+response.getBody().jsonPath().getList("data").size());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        Assert.assertTrue(((int)response.getBody().jsonPath().getList("data").size())==1,"Ignore Policy do not have device in it");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(alwaysRun = true,enabled = true, groups = {"sanity"},dependsOnMethods = {"validateIgnorePolicyExistingDevices"})
    public void validateIgnorePolicyEligibleDevices() {
        getAppliacneIdAndMnmtSystemId();
        Response response=RestUtils.get(ignorePolicyEligibleDevices.getEndPoint().replaceAll(replaceApplianceId,applianceId).replaceAll(replaceCustomerIdString,controlPointCommonData.getCustomerId()).replaceAll(replaceMgmtSystemId,mgmtSystemId).replaceAll(replaceIgnorePolicyId,ignorePolicyID));
        //System.out.println("EligibleDevicesData:::"+response.getBody().jsonPath().getList("data").get(0).toString());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        Assert.assertTrue(((int)response.getBody().jsonPath().getList("data").size())>0,"Ignore Policy do not have device in it");
    }

    public String getIgnorePolicyId(){
        Response response=RestUtils.get(getPoliciesGETDp.getEndPoint());
        String ignorePolicyId="";
        JsonPath jsonPath = JsonPath.with(response.getBody().asString());
        List<Map> lm= response.getBody().jsonPath().get("$");
        for(Map jo: lm){
            if(jo.get("policyType").equals("IGNORE")){
                System.out.println("IgnorePolicyId::"+jo.get("policyId"));
                ignorePolicyId=jo.get("policyId").toString();
            }
          }
        return ignorePolicyId;
    }

    public void getAppliacneIdAndMnmtSystemId(){
        Response response=RestUtils.get(getDNACsGETDp.getEndPoint());
        applianceId=response.getBody().jsonPath().get("applianceId");
        mgmtSystemId=response.getBody().jsonPath().getList("dnacInfo.mgmtSystemId").get(0).toString();
    /*    System.out.println("DnacInfoAPIResponse::"+response.getBody().asString());
        System.out.println("applianceId::"+applianceId);
        System.out.println("dnacInfo.mgmtSystemId"+mgmtSystemId);*/
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(alwaysRun = true,enabled = true, groups = {"sanity"},dependsOnMethods = {"validateIgnorePolicyEligibleDevices"})
    public void validateIgnorePolicyDeletion() {
        Response response = RestUtils.delete(ignorePolicyDeleteDp.getEndPoint().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                .replaceAll(replaceIgnorePolicyId,ignorePolicyID));
        JsonPath jsonPath=response.getBody().jsonPath();
        //System.out.println("Delete IgnorePolicy Response::"+response.getBody().asString());
        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(response.getBody().jsonPath().get("message"),"Policy deleted","Policy not deleted/exist");
        softAssert.assertTrue(response.getStatusCode()==200,"Status code is  not Success (200)");
        softAssert.assertAll("validateIgnorePolicyDeletion");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(alwaysRun = true,enabled = true, groups = {"sanity"})//,dependsOnMethods = {"validateIgnorePolicyEligibleDevices"}
    public void validateAllDevicesForIgnorePolicy() {
        getAppliacneIdAndMnmtSystemId();
        Response response = RestUtils.get(allDvicesForIgnorePolicy.getEndPoint().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()).replaceAll(replaceMgmtSystemId,mgmtSystemId).replaceAll(replaceApplianceId,applianceId));
        JsonPath jsonPath = response.getBody().jsonPath();
        System.out.println("ResponseData::"+jsonPath.getList("data"));
        System.out.println("Size::"+jsonPath.getList("data").size());
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},dataProvider = "IgnoryPolicyGetAllDevicesInvalidScenariosDp",dataProviderClass = ControlPointData.class)
    public void validateAllDevicesForIgnorePolicyInvalidScenarios(ControlPointRequestPojo.data data){
        getAppliacneIdAndMnmtSystemId();
        SoftAssert softAssert = new SoftAssert();
        switch(data.getComment()){

            case "customerIdWrong":
                Response response1 =RestUtils.get(data.getEndPoint().replaceAll(replaceCustomerIdString,invalidCustomerId).replaceAll(replaceApplianceId,applianceId).replaceAll(replaceMgmtSystemId,mgmtSystemId));
                softAssert.assertEquals(response1.getStatusCode(),data.getResponseCode());
                break;

            case "customerIdMissing":
                Response response2 =RestUtils.get(data.getEndPoint().replaceAll(replaceApplianceId,applianceId).replaceAll(replaceMgmtSystemId,mgmtSystemId));
                softAssert.assertEquals(response2.getStatusCode(),data.getResponseCode());
                break;

            case "mgmtSystemIdMissing":
                Response response3 =RestUtils.get(data.getEndPoint().replaceAll(replaceApplianceId,applianceId).replaceAll(replaceCustomerIdString,controlPointCommonData.getCustomerId()));
                softAssert.assertEquals(response3.getStatusCode(),data.getResponseCode());
                break;

            case "applianceIdMissing":
                //System.out.println(data.getComment());
                //System.out.println("Endpoint::"+data.getEndPoint().replaceAll(replaceCustomerIdString,controlPointCommonData.getCustomerId()).replaceAll(replaceMgmtSystemId,mgmtSystemId));
                Response response4 =RestUtils.get(data.getEndPoint().replaceAll(replaceCustomerIdString,controlPointCommonData.getCustomerId()).replaceAll(replaceMgmtSystemId,mgmtSystemId));
                //System.out.println("Expected 400:: Actual::"+response4.getStatusCode());
                softAssert.assertEquals(response4.getStatusCode(),data.getResponseCode());
                break;
        }
        softAssert.assertAll("IgnoryPolicy GetAllDevices API Invalid Scenarios Completed");
    }

}
