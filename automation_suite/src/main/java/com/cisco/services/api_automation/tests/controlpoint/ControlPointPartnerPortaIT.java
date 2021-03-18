package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.pojo.request.UserRolesPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.ACC_GET_INVALID_PARAMS;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;


@Feature("HealthStatus")
public class ControlPointPartnerPortaIT {
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private String replaceBegeoIdString = "<REPLACE_begeoId_STRING>";
    private String replaceShowPastUsersIdBoolean= "<REPLACE_showPastUsers_BOOLEAN>";
    private String replaceModifiedAfterString="<REPLACE_timeStamp_STRING>";
    private String replaceCcoIdString="<REPLACE_ccoId_STRING>";
    private static String invalidString = "/invalid";
    private static String invalidStringTwo = "invalid";
    private static String validString = "valid";


    @Severity(SeverityLevel.BLOCKER)
    @Test(enabled = true, groups = {"regression"},description = "controlpoint/ctrlpt/v1/nrs/settings/{customerId}",dataProviderClass =ControlPointData.class,dataProvider="displayPartnersTabDp")
    public void verifyPartnersTabForDisplay(ControlPointRequestPojo.data data){
        SoftAssert softAssert = new SoftAssert();
        if(data.getTestType().equals("valid")){

            Response res= RestUtils.get(data.getEndPoint().replace(replaceCustomerIdString,grantPartnerAccessDataDp.getCustomerId()),new User("machine"));
            softAssert.assertEquals(res.getStatusCode(),data.getResponseCode());
            JsonPath resJsonPath=res.getBody().jsonPath();
            boolean b= resJsonPath.get("PARTNERS_AUTHORIZATION");
            softAssert.assertEquals(b,true,"partner didn't met criteria to be seen on CXCP ");
            softAssert.assertEquals(res.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else{
            Response res= RestUtils.get(data.getEndPoint().replace(replaceCustomerIdString,data.getCustomerId()),new User("machine"));
            softAssert.assertEquals(res.getStatusCode(),data.getResponseCode());
            JsonPath resJsonPath=res.getBody().jsonPath();
            softAssert.assertEquals(res.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }

        softAssert.assertAll("Partners tab display validated ");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/v1/nrs/partner/partners")
    public void verifyPendingPartnersAccessRequestsList() {

        Response response = RestUtils.get(partnerListDataDp.getEndPoint()+partnerListDataDp.getPathParams(),new User("machine"));
        JsonPath responseBody = response.getBody().jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getList("accessOrder").get(0) ,1,"Partner access is not pending");
        softAssert.assertAll("Pending Partner Access evaluated");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"})
    public void verifyApprovedPartnersAccess() {

        Response response = RestUtils.get(approvedPartnerDataDp.getEndPoint()+approvedPartnerDataDp.getPathParams(),new User("machine"));


        JsonPath responseBody = response.getBody().jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getList("accessOrder").get(1) ,2,"Partner access is pending");
        softAssert.assertAll("Approved Partner Access evaluated");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"})
    public void verifyInvalidPartnersAccessRequest() {

        Response response = RestUtils.get(invalidPartnerDataDp.getEndPoint()+invalidPartnerDataDp.getPathParams(),new User("machine"));
        JsonPath responseBody = response.getBody().jsonPath();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 404, "API working for Invalid customerId have data");
        softAssert.assertEquals(responseBody.getString("message"),"No partners matching the search criteria could be found!!","Error message not matching when custoemrId not found");
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"})
    public void verifyPartnerSearch() {

        Response partnersListResponse = RestUtils.get(searchPartnerDataDp.getEndPoint()+searchPartnerDataDp.getPathParams(),new User("machine"));
        JsonPath partnersListResponseJson = partnersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(partnersListResponse.getStatusCode(),200,"Partners List not available for given customer");

        Response searchResponse = RestUtils.get(searchPartnerDataDp.getEndPointTwo()+searchPartnerDataDp.getPathParams()+partnersListResponseJson.getList("partnerName").get(0),new User("machine"));
        JsonPath searchResponseJSON = searchResponse.getBody().jsonPath();
        softAssert.assertTrue(searchResponseJSON.getList("$").size()==1,"No Results found");
        softAssert.assertEquals(searchResponseJSON.getList("partnerName").get(0).toString(),partnersListResponseJson.getList("partnerName").get(0),"Partners not found");
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"})
    public void verifyInvalidPartnerSearch() {

        SoftAssert softAssert = new SoftAssert();
        Response searchResponse = RestUtils.get(searchPartnerDataDp.getEndPointTwo()+searchPartnerDataDp.getPathParams()+"ABCDPartner",new User("machine"));
        JsonPath searchResponseJSON = searchResponse.getBody().jsonPath();
        softAssert.assertEquals(searchResponseJSON.get("message").toString(),"No partners matching the search criteria could be found!!","o results found Message  not matching");
        softAssert.assertEquals(searchResponse.getStatusCode(),404,"Partner found with name as ABCDPartner");
        softAssert.assertAll("verifyInvalidPartnerSearch");

    }

    public Map<String,String> getPartnerDetails(){
        Response partnersListResponse = RestUtils.get(partnerListDataDp.getEndPoint()+partnerListDataDp.getPathParams(),new User("machine"));
        JsonPath partnersListResponseJson = partnersListResponse.getBody().jsonPath();
        String currentAccessOrder= partnersListResponseJson.getList("accessOrder").get(1).toString();
        String currentAccessStatus=partnersListResponseJson.getList("accessStatus").get(1).toString();

        Map <String,String>details = new HashMap<String,String>() ;
        details.put("accessOrder",currentAccessOrder);
        details.put("accessStatus",currentAccessStatus);

        return details;
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/v1/nrs/partner/access")
    public void verifyGrantPartnersAccess() {

        Map<String,String> partnerDetails=getPartnerDetails();
        SoftAssert softAssert = new SoftAssert();
        if(partnerDetails.get("accessOrder").equals("1") && partnerDetails.get("accessStatus").equals("Pending")){

            Response response = RestUtils.patch(grantPartnerAccessDataDp.getEndPoint() , grantPartnerAccessDataDp.getBody(), Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath responseBody = response.getBody().jsonPath();

            softAssert.assertEquals(response.getStatusCode(),204,"Granting access to partner is failed");
            Response partnersListPostAccessGrant = RestUtils.get(partnerListDataDp.getEndPoint()+partnerListDataDp.getPathParams(),new User("machine"));
            JsonPath partnersListPostAccessGrantJson = partnersListPostAccessGrant.getBody().jsonPath();

            Map<String,String> partnerDetails2=getPartnerDetails();
            if(partnerDetails2.get("accessOrder").equals("2") && partnerDetails2.get("accessStatus").equals("Granted")){
                softAssert.assertTrue(true,"Grant Access Updated records successfully");
            }else{
                softAssert.assertTrue(false,"Grant Access  record not Updated successfully");
            }
        }
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "RevokePartnersAccess /v1/nrs/partner/access")
    public void verifyRevokePartnersAccess(){

        SoftAssert softAssert = new SoftAssert();
        Map<String,String> currentPartnerDetails=getPartnerDetails();
        String ao=currentPartnerDetails.get("accessOrder");
        String as=currentPartnerDetails.get("accessStatus");
        if(ao.equals("2")&&as.equals("Granted")) {
            Response revokeAccessResponse = RestUtils.patch(revokePartnerDataDp.getEndPoint(), revokePartnerDataDp.getBody(), Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath revokeAccessResponseJson = revokeAccessResponse.getBody().jsonPath();
            softAssert.assertEquals(revokeAccessResponse.getStatusCode(), 204, "successfully revoked access");
        }


        Map<String,String> currentPartnerDetailsPostRevoke=getPartnerDetails();
        String ao2=currentPartnerDetailsPostRevoke.get("accessOrder");
        String as2=currentPartnerDetailsPostRevoke.get("accessStatus");

        if(ao2.equals("3") && as2.equals("Revoked")) {
            softAssert.assertTrue(true,"Post revoke Partner Status updated correctly");
        }else{
            softAssert.assertTrue(false,"Revoke Partner not happened");
        }

        softAssert.assertAll("RevokePartnersAccess executed");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/v1/nrs/partner/access")
    public void verifyRestorePartnersAccess(){

        SoftAssert softAssert = new SoftAssert();
        Map<String,String> currentPartnerDetails=getPartnerDetails();
        String ao=currentPartnerDetails.get("accessOrder");
        String as=currentPartnerDetails.get("accessStatus");

        if(ao.equals("3")&&as.equals("Revoked")) {

            Response restoreAccessResponse = RestUtils.patch(restorePartnerDataDp.getEndPoint(), restorePartnerDataDp.getBody(), Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath restoreAccessResponseJson = restoreAccessResponse.getBody().jsonPath();
            softAssert.assertEquals(restoreAccessResponse.getStatusCode(), 204, "successfully revoked access");
        }else{

        }
        softAssert.assertAll("RestorePartnersAccess completed");

    }

/*
    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "partner/partnerusers/{customerId}/{begeoId}?showPastUsers")
    public void verifyPastPartnerUsersList(){

      Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

      JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
      SoftAssert softAssert = new SoftAssert();
      softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
      int totalPastUsers=pastPartnerUsersListResponseJson.getList("data.accessOrder").stream().filter(accessOrder->(Integer.parseInt(accessOrder.toString())==3)).collect(Collectors.toList()).size();
      int pastUsersCount=pastPartnerUsersListResponseJson.get("pastUsersCount");
      softAssert.assertEquals(totalPastUsers,pastUsersCount,"PastUsersCount not matching");
      softAssert.assertAll("PastPartnerUsersList validation completed");;

    }
*/

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "partner/partnerusers/{customerId}/{begeoId}?showPastUsers")
    public void verifyNoPastPartnerUsersList(){

        Response  pastPartnerUsersListResponse=RestUtils.get(dontShowPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, dontShowPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,dontShowPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,dontShowPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        int totalPastUsers=pastPartnerUsersListResponseJson.getList("data.accessOrder").stream().filter(accessOrder->(Integer.parseInt(accessOrder.toString())==3)).collect(Collectors.toList()).size();
        int pastUsersCount=pastPartnerUsersListResponseJson.get("pastUsersCount");
        softAssert.assertTrue(totalPastUsers==0 && pastUsersCount>0,"PastUsersCount not matching");
        softAssert.assertAll("PastPartnerUsersList validation completed");;

    }

/*
    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "partner/partnerusers/{customerId}/{begeoId}?showPastUsers")
    public void verifyPastPartnerUsersListInvalidPartner(){

        Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()+"test123").replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        int totalRows=pastPartnerUsersListResponseJson.getInt ("pagination.totalRows");
        int pastUsersCount=pastPartnerUsersListResponseJson.get("pastUsersCount");
        softAssert.assertTrue(totalRows==0,"P");
        softAssert.assertAll("PastPartnerUsersList validation completed");;

    }
*/

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "nrs/partner/access/requests/completed?modifiedAfter")
    public void verifyApprovedPartnersList() {

        Response approvedPartnersListResponse = RestUtils.get(approvedPartnerListDataDp.getEndPoint().replace(replaceModifiedAfterString, approvedPartnerListDataDp.getModifiedAfter()).replace(replaceBegeoIdString, showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean, showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

        JsonPath approvedPartnersListResponseJson=approvedPartnersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
      
        int count=approvedPartnersListResponseJson.getList("data.customerId").stream().filter(customerId->customerId.equals(partnerListDataDp.getPathParams().substring(1))).collect(Collectors.toList()).size();
        softAssert.assertTrue(count>0,"Partner not granterd access");
        softAssert.assertAll("Partner present in Approved Partners List");


    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled=true,groups={"regression"},description="nrs/partner/useraccess")
    public void verifyGrantPartnerUserAccess(){

        Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        System.out.println("pastPartnerUsersListResponse::"+pastPartnerUsersListResponse.getBody().asString());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        String pendingCcoId=   pastPartnerUsersListResponseJson.getList("data.findAll{it.accessOrder==1}.ccoId").stream().collect(Collectors.toList()).get(0).toString();
        System.out.println("pendingCcoId::"+pendingCcoId);
        String finalBody=grantPartnerUserAccessDataDp.getBody().replace(replaceCustomerIdString,showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceCcoIdString,pendingCcoId);
        System.out.println("payLoad to grant User Access::"+finalBody);
        Response grantAccessToPartnerUserResponse= RestUtils.patch(grantPartnerUserAccessDataDp.getEndPoint(),finalBody,Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        System.out.println("grantAccessToPartnerUserResponse::"+grantAccessToPartnerUserResponse.getBody().asString());
        softAssert.assertEquals(grantAccessToPartnerUserResponse.getStatusCode(),204);

        Response res= RestUtils.get(partnerUserAccessCheckDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceCcoIdString,pendingCcoId),new User("machine"));
        softAssert.assertEquals(res.getBody().jsonPath().get("hasAccess").toString(),"true","Partner user is not authorised");
        softAssert.assertAll("Partner user is authorised and verified successfully");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled=true,groups={"regression"},description="nrs/partner/useraccess")
    public void verifyGrantPartnerUserAccessForAlreadyGrantedUser(){

        Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        String pendingCcoId=   pastPartnerUsersListResponseJson.getList("data.findAll{it.accessOrder==2}.ccoId").stream().collect(Collectors.toList()).get(0).toString();
        String finalBody=grantPartnerUserAccessDataDp.getBody().replace(replaceCustomerIdString,showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceCcoIdString,pendingCcoId);

        Response grantAccessToPartnerUserResponse= RestUtils.patch(grantPartnerUserAccessDataDp.getEndPoint(),finalBody,Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        softAssert.assertEquals(grantAccessToPartnerUserResponse.getStatusCode(),409);
        String msg=grantAccessToPartnerUserResponse.getBody().jsonPath().get("message").toString();
        softAssert.assertEquals(msg,"User already has access");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled=true,groups={"regression"},description="nrs/partner/useraccess")
    public void verifyGrantPartnerUserAccessForInvalidUser(){

        Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        String pendingCcoId=   pastPartnerUsersListResponseJson.getList("data.findAll{it.accessOrder==1}.ccoId").stream().collect(Collectors.toList()).get(0).toString();
        String finalBody=grantPartnerUserAccessDataDp.getBody().replace(replaceCustomerIdString,showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceCcoIdString,pendingCcoId+"invalid");

        Response grantAccessToPartnerUserResponse= RestUtils.patch(grantPartnerUserAccessDataDp.getEndPoint(),finalBody,Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        softAssert.assertEquals(grantAccessToPartnerUserResponse.getStatusCode(),404);
        String msg=grantAccessToPartnerUserResponse.getBody().jsonPath().get("message").toString();
        softAssert.assertEquals(msg,"User does not exist!");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled=true,groups={"regression"},description="nrs/partner/user/access-check/{customerId}}/{begeoId}}/{ccoId}}")
    public void verifyPartnerUserAccessCheck(){

        Response  pastPartnerUsersListResponse=RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
        JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        String pendingCcoId=   pastPartnerUsersListResponseJson.getList("data.findAll{it.accessOrder==1}.ccoId").stream().collect(Collectors.toList()).get(0).toString();
        String finalBody=grantPartnerUserAccessDataDp.getBody().replace(replaceCustomerIdString,showPastPartnerUsersDataDp.getCustomerId()).replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).replace(replaceCcoIdString,pendingCcoId+"invalid");

        Response grantAccessToPartnerUserResponse= RestUtils.patch(grantPartnerUserAccessDataDp.getEndPoint(),finalBody,Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        softAssert.assertEquals(grantAccessToPartnerUserResponse.getStatusCode(),404);
        String msg=grantAccessToPartnerUserResponse.getBody().jsonPath().get("message").toString();
        softAssert.assertEquals(msg,"User does not exist!");

    }

    public String getUserWithAccess(String accessStatus){
        String ccoId="";
        Response response = RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().
                replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).
                replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).
                replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()));
         List<Map> lm=  response.getBody().jsonPath().getList("data");
        for(Map m: lm){
            if(m.get("accessStatus").equals(accessStatus)){
                ccoId=  m.get("ccoId").toString();
                System.out.println(accessStatus+" ccoId::"+ccoId);
                break;
            }
        }
        return ccoId;
    }

    public List<String> getUsersWithSpecificStatus(String accessStatus) {
        List<String> ccoIds = new ArrayList<>();
        Response response = RestUtils.get(showPastPartnerUsersDataDp.getEndPoint().
                replace(replaceCustomerIdString, showPastPartnerUsersDataDp.getCustomerId()).
                replace(replaceBegeoIdString,showPastPartnerUsersDataDp.getBegeoId()).
                replace(replaceShowPastUsersIdBoolean,showPastPartnerUsersDataDp.getShowPastPartners()));
        List<Map> usersData=  response.getBody().jsonPath().getList("data");
        for(Map eachUserData: usersData){
            if(eachUserData.get("accessStatus").equals(accessStatus)){
                ccoIds.add(eachUserData.get("ccoId").toString());
            }
        }
        System.out.println("All ccoIds with status as "+accessStatus+" "+ccoIds);
    return ccoIds;
    }

    //getUsersWithSpecificStatus


    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},dependsOnMethods = {"verifyGrantPartnerUserAccess"},description = "Revoke user access with /ctrlpt/v1/nrs/partner/useraccess")
    public void verifyRevokePartnerUsers() {
        String ccoId=getUserWithAccess("Granted");
        System.out.println("Payload::"+revokePartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString,ccoId));
        Response response=RestUtils.patch(revokePartnerUserAccessDp.getEndPoint(),revokePartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString,ccoId),Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(),204);
        softAssert.assertTrue(getUsersWithSpecificStatus("Revoked").contains(ccoId));
        softAssert.assertAll();
    }


    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "Revoke user access with /ctrlpt/v1/nrs/partner/useraccess")
    public void verifyRestorePartnerUsers() {
        String ccoId = getUserWithAccess("Revoked");
        System.out.println("EndPoint::"+restorePartnerUserAccessDp.getEndPoint());
        System.out.println("Payload::" + restorePartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString, ccoId));
        Response response=RestUtils.patch(restorePartnerUserAccessDp.getEndPoint(),restorePartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString,ccoId),Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(),204);
        softAssert.assertTrue(getUsersWithSpecificStatus("Restored").contains(ccoId));
        softAssert.assertAll();


    }





        @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"})
    public void verifyRejectPartnerUsers() {
        String ccoId=getUserWithAccess("Pending");
        System.out.println("Payload::"+rejectPartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString,ccoId));
        Response response=RestUtils.patch(rejectPartnerUserAccessDp.getEndPoint(),rejectPartnerUserAccessDp.getBody().replaceAll(replaceCcoIdString,ccoId),Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        System.out.println("Response::"+response.getBody().asString());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(),204);
        softAssert.assertTrue(getUsersWithSpecificStatus("Rejected").contains(ccoId));
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},dataProvider = "showPastPartnerUsersDp",dataProviderClass = ControlPointData.class,description = "partner/partnerusers/{customerId}/{begeoId}?showPastUsers")
    public void verifyShowPastPartnerUsersList(ControlPointRequestPojo.data data){
        Response  pastPartnerUsersListResponse=RestUtils.get(data.getEndPoint().replace(replaceCustomerIdString, data.getCustomerId()).replace(replaceBegeoIdString,data.getBegeoId()).replace(replaceShowPastUsersIdBoolean,data.getShowPastPartners()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("machine"));

            JsonPath pastPartnerUsersListResponseJson= pastPartnerUsersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();

        if(data.getTestType().equalsIgnoreCase(validString)){

            softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
            int totalPastUsers=pastPartnerUsersListResponseJson.getList("data.accessOrder").stream().filter(accessOrder->(Integer.parseInt(accessOrder.toString())==3)).collect(Collectors.toList()).size();
            int pastUsersCount=pastPartnerUsersListResponseJson.get("pastUsersCount");
            softAssert.assertEquals(totalPastUsers, pastUsersCount,"PastUsers count not matching customerId");
            softAssert.assertTrue(Integer.parseInt(pastPartnerUsersListResponseJson.get("pagination.totalRows").toString())>0,"Partner Users data is coming as 0");
        }
        else{

            softAssert.assertEquals(pastPartnerUsersListResponse.getStatusCode(),200,"Partners users not found");
            int totalPastUsers=pastPartnerUsersListResponseJson.getList("data.accessOrder").stream().filter(accessOrder->(Integer.parseInt(accessOrder.toString())==3)).collect(Collectors.toList()).size();
            int pastUsersCount=pastPartnerUsersListResponseJson.get("pastUsersCount");
            softAssert.assertTrue(totalPastUsers==0 && pastUsersCount>=0,"PastUsers are shown for wrong customerId");
            softAssert.assertTrue(Integer.parseInt(pastPartnerUsersListResponseJson.get("pagination.totalRows").toString())==0,"Partner Users data is displayed for wrong input");
        }
        softAssert.assertAll("PastPartnerUsersList validation completed");;

    }


    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},dataProvider = "searchPartnersDp",dataProviderClass = ControlPointData.class,description = "/v1/nrs/partner/searchpartner/{customerId}/{partnerName}")
    public void verifyPartnerSearchList(ControlPointRequestPojo.data data) {

        Response partnersListResponse = RestUtils.get(data.getEndPoint() + data.getPathParams(),new User("machine"));
        JsonPath partnersListResponseJson = partnersListResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(partnersListResponse.getStatusCode(), 200, "Partners List not available for given customer");

        if(data.getTestType().equalsIgnoreCase(validString)) {

            Response searchResponse = RestUtils.get(data.getEndPointTwo() + data.getPathParams() + partnersListResponseJson.getList("partnerName").get(0),new User("machine"));
            JsonPath searchResponseJSON = searchResponse.getBody().jsonPath();
            softAssert.assertTrue(searchResponseJSON.getList("$").size() > 0, "No Results found");

            softAssert.assertEquals(searchResponseJSON.getList("$").size() > 0, "No Results found");
            softAssert.assertEquals(partnersListResponseJson.get("partnerName").toString(), searchResponseJSON.get("partnerName").toString(), "Partners not found");
        }else{
            Response searchResponse = RestUtils.get(data.getEndPointTwo() + data.getPathParams() + data.getSearchTerm(),new User("machine"));
            JsonPath searchResponseJSON = searchResponse.getBody().jsonPath();

            softAssert.assertEquals(searchResponse.getStatusCode(),data.getResponseCode(),"Results found for wrong input");

        }
    }
}
