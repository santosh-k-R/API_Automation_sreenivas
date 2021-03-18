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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;


/**
 * Test Methods used to validate
 * Users Groups & Users API's
 *
 * @author Vinay Jengiti
 */


@Feature("HealthStatus")
public class ControlPointUserGroupsIT {

    private static final String DIR = ControlPointData.getDIR();
    private static String validString = "valid";
    private static String invalidString = "INVALID";
    private static String trueValue = "true";
    private static String falseValue = "false";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private String replaceGroupName = "<REPLACE_groupName_STRING>";
    private static String commonUserGroupName;
    String groupNameForValidation = "";



    @Test(enabled=true, groups = {"sanity"}, dataProvider = "createUserGroupDp",
            dataProviderClass = ControlPointData.class)
    public void verifyCreateUserGroupPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        if(data.getTokenType().equalsIgnoreCase(validString)) {

                 response = RestUtils.post(data.getEndPoint(),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath oriBody = new JsonPath(data.getBody());
            commonUserGroupName = oriBody.get("groupName");
            List<String> orignalCCOIdList = oriBody.getList("users.ccoId", String.class);
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");


            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(validString)) {
                JsonPath responseBody = response.getBody().jsonPath();
                //Validating response with the expected value 
                SoftAssert softAssert = new SoftAssert();

                Response responseGet = RestUtils.get((data.getEndPointTwo().replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()))
                                .replace(replaceGroupName, commonUserGroupName),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

                JsonPath responseBodyGet = responseGet.getBody().jsonPath();
                List<String> userGroupList = responseBodyGet.getList("users.userGroups", String.class);
                List<String> ccoIdList = responseBodyGet.getList("users.ccoId.flatten()", String.class);

                userGroupList.forEach(userGroup -> {
                    softAssert.assertTrue(userGroup.contains(commonUserGroupName), "CCOID" + userGroup);
                });

                ccoIdList.forEach(item -> {
                    softAssert.assertTrue(orignalCCOIdList.contains(item), "CCOID" + item);
                });
                softAssert.assertAll("Asserting the Response");
                System.out.println("API response is :- " + response.then().log().all());
            }
            else
            {
                 response = RestUtils.post(data.getEndPoint(),
                        data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("MACHINE"));

                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
                System.out.println("API response is :- " + response.then().log().all());

            }
        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "updateUserGroupDp",
            dependsOnMethods = { "verifyCreateUserGroupPOSTAPI" }, dataProviderClass = ControlPointData.class)
    public void verifyUpdateUserGroupPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

        response = RestUtils.patch(data.getEndPoint()
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        data.getBody(),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath oriBody = new JsonPath(data.getBody());
        String existingGroupName = oriBody.get("groupName");
        String newGroupName = oriBody.get("newUserGroup");

        if(newGroupName == null)
            groupNameForValidation = existingGroupName;
        else
            groupNameForValidation = newGroupName;

        //String orignalUserGroupName = oriBody.get("groupName");
        List<String> orignalCCOIdList = oriBody.getList("users.ccoId", String.class);
        Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

        Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                controlPointCommonData.getcscheader(), "Content-Security-Policy:");

       if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            SoftAssert softAssert = new SoftAssert();

            Response responseGet = RestUtils.get((data.getEndPointTwo().replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()))
                            .replace(replaceGroupName, groupNameForValidation),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath responseBodyGet = responseGet.getBody().jsonPath();
            List<String> userGroupList = responseBodyGet.getList("users.userGroups", String.class);
            List<String> ccoIdList = responseBodyGet.getList("users.ccoId.flatten()", String.class);

            userGroupList.forEach(userGroup -> {
                softAssert.assertTrue(userGroup.contains(groupNameForValidation), "CCOID" + userGroup); });

            softAssert.assertAll("Asserting the Response");
            System.out.println("API response is :- " + response.then().log().all());
        }
       else
       {
           response = RestUtils.patch(data.getEndPoint()
                           .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                   data.getBody(),
                   Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("MACHINE"));
           Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
           System.out.println("API response is :- " + response.then().log().all());
       }

       }
    }


    @Test(enabled=true, groups = {"sanity"}, dataProvider = "validateUserGroupDp",
            dependsOnMethods = { "verifyCreateUserGroupPOSTAPI", "verifyUpdateUserGroupPOSTAPI" }, dataProviderClass = ControlPointData.class)
    public void verifyValidateUserGroupGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = null;
        JsonPath responseBody = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

                response = RestUtils.get(data.getEndPoint()
                                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

                 responseBody = response.getBody().jsonPath();
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

                Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                        controlPointCommonData.getcscheader(), "Content-Security-Policy:");
                      System.out.println("API response is :- " + response.then().log().all());
            }
            else
            {
                response = RestUtils.get(data.getEndPoint()
                                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()),new User("MACHINE"));
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
                System.out.println("API response is :- " + response.then().log().all());
            }

        if(data.getTestType().equalsIgnoreCase(validString)) {
                softAssert.assertEquals(responseBody.getString("message"), data.getResponseMessage(), "message : ");
                softAssert.assertEquals(responseBody.getString("isUserGroupValid"), falseValue, "isUserGroupValid : ");
            }
            else
            {
                softAssert.assertEquals(responseBody.getString("isUserGroupValid"), trueValue, "isUserGroupValid : ");
            }

        softAssert.assertAll("Asserting the Response");
        System.out.println("API response is :- " + response.then().log().all());
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "listUserGroupDp",
            dataProviderClass = ControlPointData.class)
    public void verifyListUserGroupGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        List<String> expectedUserGroups = null;
        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            StringTokenizer st = new StringTokenizer(data.getParams());

            expectedUserGroups = new ArrayList<String>();
            while (st.hasMoreElements()) {
                expectedUserGroups.add(st.nextToken(":"));
            }

            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(validString))
            {
                SoftAssert softAssert = new SoftAssert();
                JsonPath responseBody = response.getBody().jsonPath();
                List<String> userGroupList = responseBody.getList("userGroupData.groupName", String.class);
                expectedUserGroups.forEach(item -> { softAssert.assertTrue(userGroupList.contains(item), "CCOID" + item); });
                softAssert.assertAll("Asserting the Response");
            }
        }
        else
        {
            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("MACHINE"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

        }


    }

    @Test(enabled = true, groups = {"sanity"},
            dataProvider = "deleteUserGroupDp", dataProviderClass = ControlPointData.class)
    public void verifyDeleteUserGroupAPI(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            SoftAssert softAssert = new SoftAssert();
             response = RestUtils.delete((data.getEndPoint())
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody());
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            softAssert.assertEquals(responseBody.getString("message"), data.getResponseMessage(), "message : ");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            Response responseTwo = RestUtils.get(data.getEndPointTwo()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath responseBodyTwo = responseTwo.getBody().jsonPath();
            if (data.getTestType().equalsIgnoreCase(validString)) {
                List<String> userGroupList = responseBodyTwo.getList("userGroupData.groupName", String.class);
                softAssert.assertTrue(userGroupList.size() == 0);

            }
            softAssert.assertAll("Asserting the Response");
        }
        else
        {
            response = RestUtils.delete((data.getEndPoint())
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody(), new User("INVALID"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "addUserDp",
            dataProviderClass = ControlPointData.class)
    public void verifyAddUserPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        String originalCCOID = "";
        String originalEmail = "";
        List<String> originalUserGroups = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint()
                            .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath responseBodyValidate = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(responseBodyValidate.getString("message"), data.getResponseMessage(), "message : ");


            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            JsonPath oriBody = new JsonPath(data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()));
             originalCCOID = oriBody.get("ccoId");
             originalEmail = oriBody.get("email");
             originalUserGroups = oriBody.getList("userGroups", String.class);
        }
        else
        {
            response = RestUtils.post(data.getEndPoint()
                            .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("INVALID"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            SoftAssert softAssert = new SoftAssert();

            Response responseGet = RestUtils.get((data.getEndPointTwo().replace(replaceCustomerIdString, controlPointCommonData.getCustomerId())),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath responseBodyGet = responseGet.getBody().jsonPath();
           String customerIdGet = responseBodyGet.getString("users.customerId.get(0)");

            String ccoIdGet = responseBodyGet.getString("users.ccoId.get(0)");
            String emailGet = responseBodyGet.getString("users.emailId.get(0)");
            List<String> userGroupsGet = responseBodyGet.getList("users[0].userGroups", String.class);

            Assert.assertEquals(originalCCOID, ccoIdGet, "CCOID:");
            Assert.assertEquals(originalEmail, emailGet, "Email:");
            Assert.assertEquals(controlPointCommonData.getCustomerId(), customerIdGet, "customerId:");

            originalUserGroups.forEach(item -> {
                softAssert.assertTrue(userGroupsGet.contains(item), "User Groups" + item); });

            softAssert.assertAll("Asserting the Response");
        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "listUserDp",
            dependsOnMethods = "verifyAddUserPOSTAPI", dataProviderClass = ControlPointData.class)
    public void verifyListUserGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        String originalCCOID = "";
        String originalEmail = "";
        String originalUserGroups = "";

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            JsonPath oriBody = new JsonPath(data.getResponseBody().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()));
            originalCCOID = oriBody.get("ccoId");
            originalEmail = oriBody.get("email");
            originalUserGroups = oriBody.getString("userGroups");
        }
        else
        {
            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("INVALID"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            SoftAssert softAssert = new SoftAssert();

            List<String> customerIdGetList = responseBody.getList("users.customerId", String.class);
            List<String> ccoIdGetList = responseBody.getList("users.ccoId", String.class);
            List<String> emailIdGetList = responseBody.getList("users.emailId", String.class);
            List<String> userGroupsGet = responseBody.getList("users.userGroups.flatten()", String.class);

            softAssert.assertTrue(ccoIdGetList.contains(originalCCOID), "CCOID" + originalCCOID);
            softAssert.assertTrue(customerIdGetList.contains(controlPointCommonData.getCustomerId()), "CustomerID" +
                    controlPointCommonData.getCustomerId());

            softAssert.assertTrue(emailIdGetList.contains(originalEmail), "EmailID" + originalEmail);
            softAssert.assertTrue(userGroupsGet.contains(originalUserGroups), "User Groups" + originalUserGroups);
            softAssert.assertAll("Asserting the Response");
        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "userGroupMarkedDp",
            dataProviderClass = ControlPointData.class)
    public void verifyListUserGroupMarkedGETAPI(ControlPointRequestPojo.data data) {

        Response userGroupMarkedResponse = null;
                //Creating a User Group
        Response response = RestUtils.post(data.getEndPoint(),
                data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath oriBody = new JsonPath(data.getBody());
        String orignalUserGroupName = oriBody.get("groupName");
        List<String> orignalCCOIdList = oriBody.getList("users.ccoId", String.class);

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            userGroupMarkedResponse = RestUtils.get(data.getEndPointTwo()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            Assert.assertEquals(userGroupMarkedResponse.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(userGroupMarkedResponse.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(validString))
            {
                SoftAssert softAssert = new SoftAssert();
                JsonPath userGroupMarkedResponseBody = userGroupMarkedResponse.getBody().jsonPath();
                List<String> userGroupList = userGroupMarkedResponseBody.getList("users.userGroups.flatten()");

                userGroupList.removeAll(Collections.singletonList(null));

                List<String> isSelectedList = userGroupMarkedResponseBody.getList("users.isSelected");
                isSelectedList.removeAll(Collections.singletonList(null));
                List<String> isSelectedListStr = Collections.singletonList(isSelectedList.toString());

                List<String> ccoIDList = userGroupMarkedResponseBody.getList("users.ccoId");
                ccoIDList.removeAll(Collections.singletonList(null));

                Assert.assertTrue(userGroupList.contains(orignalUserGroupName), "userGroup::"+orignalUserGroupName );

                orignalCCOIdList.forEach(ccoIDItem -> { softAssert.assertTrue(ccoIDList.contains(ccoIDItem),
                        "CCOID" + ccoIDItem); });

                isSelectedListStr.forEach(isSelectedItem -> {
                    softAssert.assertTrue(isSelectedItem.contains(trueValue), "isSelected" + isSelectedItem); });

                //validating against listUsers
                Response userListResponse = RestUtils.get(data.getEndPointThree()
                                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

                JsonPath userListResponseBody = userListResponse.getBody().jsonPath();
                List<String> userListList = userListResponseBody.getList("users.userGroups.flatten()");

                softAssert.assertEquals(userGroupMarkedResponseBody.getInt("pagination.totalRows"),
                        userListResponseBody.getInt("pagination.totalRows"), "customerId : ");

                softAssert.assertAll("Asserting the Response");
            }

        }
        else
        {
            userGroupMarkedResponse = RestUtils.get(data.getEndPointTwo()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("INVALID"));
            Assert.assertEquals(userGroupMarkedResponse.getStatusCode(), data.getResponseCode(), "Status Code:");
        }


    }


   @Test(enabled=true, groups = {"sanity"}, dataProvider = "userViewDp",
           dataProviderClass = ControlPointData.class)
    public void verifyUserViewGETAPI(ControlPointRequestPojo.data data) {

       Response response = null;
       String originalCCOID = "";
       String originalEmail = "";
       List originalUserGroups = null;

       if(data.getTokenType().equalsIgnoreCase(validString)) {

           response = RestUtils.get(data.getEndPoint()
                           .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                   Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

           Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
           Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                   controlPointCommonData.getcscheader(), "Content-Security-Policy:");

           JsonPath oriBody = new JsonPath(data.getResponseBody().replaceAll(replaceCustomerIdString,
                   controlPointCommonData.getCustomerId()));
           originalCCOID = oriBody.get("ccoId");
           originalEmail = oriBody.get("emailId");
           originalUserGroups = oriBody.getList("userDetailsAccessPolicyMapping.userGroup.flatten()");
       }
       else
       {
           response = RestUtils.get(data.getEndPoint()
                           .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                   Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("INVALID"));
           Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
       }

        if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            SoftAssert softAssert = new SoftAssert();

            String ccoIdGet = responseBody.getString("ccoId");
            String emailIdGet = responseBody.getString("emailId");
            List<String> userGroupsGet = responseBody.getList(
                    "userDetailsAccessPolicyMapping.userGroup.flatten()", String.class);

            softAssert.assertTrue(ccoIdGet.equals(originalCCOID), "CCOID" + originalCCOID);
            softAssert.assertTrue(emailIdGet.equals(originalEmail), "EmailID" + originalEmail);

            softAssert.assertAll("Asserting the Response");
        }
    }


    @Test(enabled=true, groups = {"sanity"}, dataProvider = "userEmailDp",
            dataProviderClass = ControlPointData.class)
    public void verifyUserEmailPOSTAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint()
                            .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(invalidString))
            {
                JsonPath responseBody = response.getBody().jsonPath();
                //Validating response with the expected value
                softAssert.assertEquals(responseBody.getString("message"), data.getResponseMessage(), "message : ");
                softAssert.assertAll("Asserting the Response");
            }
        }
        else
        {
            response = RestUtils.post(data.getEndPoint()
                            .replace(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("INVALID"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }


    }


    @Test(enabled = true, groups = {"sanity"},dataProvider = "cloudRemoveUserDp",
            dataProviderClass = ControlPointData.class)
    public void verifyDeleteUserAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.delete((data.getEndPoint())
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()));

            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value
            softAssert.assertEquals(responseBody.getString("message"), data.getResponseMessage(), "message : ");

            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(validString))
            {
                Response responseTwo = RestUtils.get(data.getEndPointTwo()
                                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
                JsonPath responseBodyTwo = responseTwo.getBody().jsonPath();
                List<String> userList = responseBodyTwo.getList("users.flatten()", String.class);
                softAssert.assertTrue(userList.size() == 0);

            }
        }
        else
        {
               response = RestUtils.delete((data.getEndPoint())
                               .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    new User("INVALID"));
                System.out.println("API Body is :- "+data.getBody());
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            System.out.println("API response is :- " + response.then().log().all());
        }



        softAssert.assertAll("Asserting the Response");
    }

    @Test(enabled = true, groups = {"sanity"},dataProvider = "userGroupRemoveDp",
            dataProviderClass = ControlPointData.class)
    public void verifyUserGroupRemoveAPI(ControlPointRequestPojo.data data) {
        Response response = null;
        SoftAssert softAssert = new SoftAssert();

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint(),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            Response responseTwo = RestUtils.delete((data.getEndPointTwo())
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBodyTwo().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()));

            Assert.assertEquals(responseTwo.getStatusCode(), data.getResponseCode(), "Status Code:");
            JsonPath responseBody = responseTwo.getBody().jsonPath();
            //Validating response with the expected value
            softAssert.assertEquals(responseBody.getString("message"), data.getResponseMessage(), "message : ");
            Assert.assertEquals(responseTwo.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

            if(data.getTestType().equalsIgnoreCase(validString))
            {
                Response responseThree = RestUtils.get(data.getEndPointThree()
                                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                        Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
                JsonPath responseBodyTwo = responseThree.getBody().jsonPath();

                List<String> userList = responseBodyTwo.getList("userDetailsAccessPolicyMapping");
                softAssert.assertTrue(userList.size() == 0);
            }
        }
        else
        {
            Response responseTwo = RestUtils.delete((data.getEndPointTwo())
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    data.getBodyTwo().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()));
            Assert.assertEquals(responseTwo.getStatusCode(), data.getResponseCode(), "Status Code:");
            softAssert.assertEquals(response.getStatusCode(),data.getResponseCode(),"Not a valid user");

        }
        softAssert.assertAll("Asserting the Response");
    }
}