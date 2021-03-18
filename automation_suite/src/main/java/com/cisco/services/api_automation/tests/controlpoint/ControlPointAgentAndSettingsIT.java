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
import static com.cisco.services.api_automation.utils.auth.TokenGenerator.getToken;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.testng.annotations.BeforeClass;


import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.utils.Commons.unzip;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static io.restassured.RestAssured.given;

@Feature("HealthStatus")
public class ControlPointAgentAndSettingsIT {

    private static String validString = "valid";
    private static String trueValue = "true";
    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";
    private String replaceScheduleId = "<REPLACE_scheduleId_STRING>";
    private String replaceRemoteNodeID = "<REPLACE_remoteNodID_STRING>";
    private String replacescheduledID = "<REPLACE_scheduledID_STRING>";
    private String replaceCurrentDate = "<REPLACE_CurrentDate_STRING>";
    private String replaceFilePath = "<REPLACE_filePath_STRING>";
    //TODO need to change the file path
    private String filePathForDebugLog = System.getProperty("java.io.tmpdir");


    private String superAdmin = "SUPERADMIN";
    private String admin = "ADMIN";
    private static List<String> remoteNoteID;
    private static String scheduledID;
    List<String> users = Arrays.asList("MACHINE", "INVALID");

    /*@BeforeTest
    public void generateToken() {
        users.forEach(user -> getToken(new User(user)));
        getToken(System.getenv("niagara_username"),System.getenv("niagara_password"));
    }
*/
    /**
     * Test Methods used to validate
     * IE/Agent API's
     *
     * @author Vinay Jengiti
     */

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieReleasesGETDp", dataProviderClass = ControlPointData.class)
    public void verifyIEReleasesGETAPI(ControlPointRequestPojo.data data) {

        SoftAssert softAssert = new SoftAssert();
        Response response = null;
        JsonPath responseBody = null;
        String expectedVersion = "";
        String expectedTitle = "";

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else
        {
            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }
        if(data.getTestType().equalsIgnoreCase(validString)) {

            responseBody = response.getBody().jsonPath();
            expectedVersion = data.getResponseBody();
            expectedTitle = data.getResponseMessage();

            List<String> versionGetList = responseBody.getList("version", String.class);
            List<String> titleGetList = responseBody.getList("title", String.class);
            softAssert.assertTrue(versionGetList.contains(expectedVersion), "Version" + expectedVersion);
            softAssert.assertTrue(titleGetList.contains(expectedTitle), "Title" + expectedTitle);
            softAssert.assertAll("Asserting the Response");

        }
    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieReleaseVersionGETDp", dataProviderClass = ControlPointData.class)
    public void verifyIEReleasesVersionGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        JsonPath responseBody = null;
        String expectedVersion = null;
        String expectedTitle = null;


        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");

        }
        else {
            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }
        if(data.getTestType().equalsIgnoreCase(validString)) {

            responseBody = response.getBody().jsonPath();
            expectedVersion = data.getResponseBody();
            expectedTitle = data.getResponseMessage();

            String versionGet = responseBody.getString("version");
            String titleGet = responseBody.getString("title");
            Assert.assertEquals(versionGet, expectedVersion, "version:");
            Assert.assertEquals(titleGet,expectedTitle,"No value for title");
        }
    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieReleaseReleaseNoteGETDp",
            dataProviderClass = ControlPointData.class)
    public void verifyIEReleasesReleaseNoteGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        JsonPath responseBody = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else
        {
            response = RestUtils.get(data.getEndPoint(),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }
        if(data.getTestType().equalsIgnoreCase(validString)) {

            responseBody = response.getBody().jsonPath();
            String expectedVersion = data.getResponseBody();
            String expectedTitle = data.getResponseMessage();

            SoftAssert softAssert=new SoftAssert();
            String versionGet = responseBody.getString("version");
            String titleGet = responseBody.getString("title");
            String releaseNote = responseBody.getString("releaseNote");
            Assert.assertEquals(versionGet, expectedVersion, "version:");
            Assert.assertEquals(expectedTitle,(titleGet));
            Assert.assertTrue(!releaseNote.equals(""), "releaseNote:");

        }

    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieScheduledUpdatePOSTDp",
            dataProviderClass = ControlPointData.class)
    public void verifyIEScheduledUpdatePOSTDAPI(ControlPointRequestPojo.data data) {

        Response response = null;

                //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                            .replace(replaceRemoteNodeID, remoteNoteID.get(0)),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else {
            response = RestUtils.post(data.getEndPoint().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                            .replace(replaceRemoteNodeID, remoteNoteID.get(0)),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));

            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString)) {
            JsonPath responseBody = response.getBody().jsonPath();
            scheduledID = responseBody.getString("id");
            Assert.assertTrue(!scheduledID.equals(""), "scheduledID:");
        }
    }

    @Test(enabled = true, groups = {"sanity"},
            dataProvider = "ieUpdateGETDp", dependsOnMethods = "verifyIEScheduledUpdatePOSTDAPI",
            dataProviderClass = ControlPointData.class)
    public void verifyIEUpgradeGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(((data.getEndPoint().replace(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId())))
                            .replace(replaceScheduleId, scheduledID),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath responseBody = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(remoteNoteID.get(0).toString(),
                    responseBody.getList("remoteNodeId.flatten()", String.class).get(0).toString(), "remoteNoteID:");
            Assert.assertEquals(controlPointCommonData.getCustomerId(),
                    responseBody.getList("customerId.flatten()", String.class).get(0).toString(), "customerId:");
        }
        else {
            response = RestUtils.get(((data.getEndPoint().replace(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId())))
                            .replace(replaceScheduleId, scheduledID),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));

            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieDeleteScheduledUpdateDELETEDp",
            dependsOnMethods = "verifyIEScheduledUpdatePOSTDAPI", dataProviderClass = ControlPointData.class)
    public void verifyieDeleteScheduledUpdateDELETEAPI(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.delete((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replacescheduledID, scheduledID));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else
        {
            response = RestUtils.delete((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replacescheduledID, scheduledID), new User("invalid"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieUpdateNowPOSTDp",
            dataProviderClass = ControlPointData.class)
    public void verifyIEUpdateNowPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        JsonPath responseBody = null;
                //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                            .replace(replaceRemoteNodeID, remoteNoteID.get(0)),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
             responseBody = response.getBody().jsonPath();
        }
        else
        {
            response = RestUtils.post(data.getEndPoint().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                            .replace(replaceRemoteNodeID, remoteNoteID.get(0)),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            //Validating response with the expected value
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

        }

        if(data.getTestType().equalsIgnoreCase(validString)) {
            scheduledID = responseBody.getString("id");
            Assert.assertTrue(!scheduledID.equals(""), "scheduledID:");
            //cleaning up
            RestUtils.delete((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replacescheduledID, scheduledID));
        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "settingsInsightPOSTAPIDp",
            dataProviderClass = ControlPointData.class)
    public void verifySettingsInsightPOSTAPI(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.post(data.getEndPoint(),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

            JsonPath responseBodyValidate = response.getBody().jsonPath();
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(responseBodyValidate.getString("message"), data.getResponseMessage(), "message : ");

            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else {
            response = RestUtils.post(data.getEndPoint(),
                    data.getBody().replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath oriBody = new JsonPath(data.getBody().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()));

            String originalInsightType = oriBody.get("insightType");
            Response responseGet = RestUtils.get((data.getEndPointTwo().replace(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId())),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            JsonPath responseBodyGet = responseGet.getBody().jsonPath();
            String customerIdGet = responseBodyGet.getString("customerId");

            Assert.assertEquals(originalInsightType, responseBodyGet.getList("insightConfigs.insightType",
                    String.class).get(0), "insightType:");
            Assert.assertEquals(trueValue, responseBodyGet.getList("insightConfigs.mode",
                    String.class).get(0), "Mode:");
            Assert.assertEquals(controlPointCommonData.getCustomerId(), customerIdGet, "customerId:");

        }
    }

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "settingsInsightGETAPIDp",
            dataProviderClass = ControlPointData.class)
    public void verifySettingsInsightGETAPIDp(ControlPointRequestPojo.data data) {

        Response response = null;

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else {
            response = RestUtils.get(data.getEndPoint()
                            .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId()),
                    Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()), new User("invalid"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString))
        {
            JsonPath oriBody = new JsonPath(data.getResponseBody().replaceAll(replaceCustomerIdString,
                    controlPointCommonData.getCustomerId()));
            List<String> insightModeOriList = oriBody.getList("insightConfigs.mode", String.class);
            List<String> insightTypeOriList = oriBody.getList("insightConfigs.insightType", String.class);

            JsonPath responseBody = response.getBody().jsonPath();
            //Validating response with the expected value 
            SoftAssert softAssert = new SoftAssert();

            List<String> insightModeGetList = responseBody.getList("insightConfigs.mode", String.class);
            List<String> insightTypeGetList = responseBody.getList("insightConfigs.insightType", String.class);
            insightModeGetList.forEach(mode -> { softAssert.assertTrue(insightModeOriList.contains(mode),
                    "mode" + mode); });
            insightTypeGetList.forEach(insightType -> { softAssert.assertTrue(insightTypeOriList.contains(insightType),
                    "insightType" + insightType); });

            softAssert.assertAll("Asserting the Response");
        }
    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieLogsFilesGETAPIDp",
            dataProviderClass = ControlPointData.class)
    public void verifyIELogsFilesGETAPI(ControlPointRequestPojo.data data) {

        Response response = null;
        JsonPath responseBody = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        String currentDate = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = format1.format(date);

        //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            if (data.getTestType().equalsIgnoreCase(validString)) {
                response = RestUtils.get((((data.getEndPoint())
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                        .replace(replaceRemoteNodeID, remoteNoteID.get(0)))
                        .replace(replaceCurrentDate, currentDate)), new User("machine"));
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

            } else {
                response = RestUtils.get(((data.getEndPoint())
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                        .replace(replaceRemoteNodeID, remoteNoteID.get(0)))
                        .replace(replaceCurrentDate, currentDate));
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

            }
        }
        else {
            response = RestUtils.get(((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replaceRemoteNodeID, remoteNoteID.get(0))), new User("invalid"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

        }
    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieAuditLogsFilesGETAPIDp",
            dataProviderClass = ControlPointData.class, alwaysRun = true)
    public void verifyIEAuditLogsFilesGETAPI(ControlPointRequestPojo.data data) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        String currentDate = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = format1.format(date);
        Response response = null;
        JsonPath responseBody = null;
        JsonPath responseGetAuditBody = null;


        //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);

        Response responseGetAudit = RestUtils.get((((data.getEndPointThree())
                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                .replace(replaceRemoteNodeID, remoteNoteID.get(0)))
                .replace(replaceCurrentDate, currentDate)), new User("machine"));
        responseGetAuditBody = responseGetAudit.getBody().jsonPath();
        List aAuditList = responseGetAuditBody.get();

        if(data.getTokenType().equalsIgnoreCase(validString)) {

            if (data.getParams().equalsIgnoreCase("machineToken")) {

                 response = RestUtils.get((((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                    .replace(replaceFilePath, aAuditList.get(0).toString()))
                    .replace(replaceCurrentDate, currentDate)), new User("machine"));

                responseBody = response.getBody().jsonPath();
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
                Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                        controlPointCommonData.getcscheader(), "Content-Security-Policy:");
            } else {
                response = RestUtils.get((((data.getEndPoint())
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                        .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                        .replace(replaceFilePath, aAuditList.get(0).toString()))
                        .replace(replaceCurrentDate, currentDate)));
                responseBody = response.getBody().jsonPath();
                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            }
        }
        else {
            response = RestUtils.get((((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                    .replace(replaceFilePath, aAuditList.get(0).toString()))
                    .replace(replaceCurrentDate, currentDate)), new User("invalid"));
            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

        if(data.getTestType().equalsIgnoreCase(validString)) {
            Assert.assertEquals(responseBody.getString("customerId"), controlPointCommonData.getCustomerId(), "customerId");
            Assert.assertEquals(responseBody.getString("remote_id"), remoteNoteID.get(0), "remote_id");
        }

    }

    @Test(enabled = true, groups = {"sanity"}, dataProvider = "ieDebugLogsFilesGETAPIDp",
            dataProviderClass = ControlPointData.class)
    public void verifyIEDebugLogsFilesGETAPI(ControlPointRequestPojo.data data)throws IOException {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        String currentDate = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = format1.format(date);
        Response response = null;
        JsonPath responseBody = null;
        JsonPath responseGetDebugBody = null;

        //getting remotenode
        Response responseGet = RestUtils.get((data.getEndPointTwo().replaceAll(replaceCustomerIdString,
                controlPointCommonData.getCustomerId())),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));

        JsonPath responseBodyGet = responseGet.getBody().jsonPath();
        remoteNoteID = responseBodyGet.getList("remote_id.flatten()", String.class);

        Response responseGetAudit = RestUtils.get((((data.getEndPointThree())
                .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                .replace(replaceRemoteNodeID, remoteNoteID.get(0)))
                .replace(replaceCurrentDate, currentDate)), new User("machine"));
        responseGetDebugBody = responseGetAudit.getBody().jsonPath();
        List aAuditList = responseGetDebugBody.get();

        if (data.getTokenType().equalsIgnoreCase(validString)) {

            if (data.getParams().equalsIgnoreCase("machineToken")) {

                response = RestUtils.get((((data.getEndPoint())
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                        .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                        .replace(replaceFilePath, aAuditList.get(0).toString()))
                        .replace(replaceCurrentDate, currentDate)), new User("machine"));

                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");

                if (data.getResponseCode() == 200) {
                    //TODO need to change the path
                    File outputFile = new File(filePathForDebugLog, "ielogs.zip");

                    if (response.getStatusCode() == 200) {
                        Commons.readZipFile(response, outputFile);
                    }

                    Commons.unzip(filePathForDebugLog + "/ielogs.zip", filePathForDebugLog + "/ielogsUnzipped");

                    File directory = new File(filePathForDebugLog + "/ielogsUnzipped");
                    int fileCount = directory.list().length;
                    Assert.assertTrue(fileCount > 0, "files:");

                    try {
                        //checking if, every file in the folder has some logs
                        for (File file : directory.listFiles()) {
                            if (file.isFile())
                                Assert.assertTrue(file.length() > 0, "files:");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //deleting all the files
                        FileUtils.cleanDirectory(directory);
                    }
                }

            } else {
                response = RestUtils.get((((data.getEndPoint())
                        .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                        .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                        .replace(replaceFilePath, aAuditList.get(0).toString()))
                        .replace(replaceCurrentDate, currentDate)));

                Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
            }

            Assert.assertEquals(response.getHeaders().getList("Content-Security-Policy").get(0).toString(),
                    controlPointCommonData.getcscheader(), "Content-Security-Policy:");
        }
        else
        {
            response = RestUtils.get((((data.getEndPoint())
                    .replaceAll(replaceCustomerIdString, controlPointCommonData.getCustomerId())
                    .replace(replaceRemoteNodeID, remoteNoteID.get(0))
                    .replace(replaceFilePath, aAuditList.get(0).toString()))
                    .replace(replaceCurrentDate, currentDate)), new User("invalid"));

            Assert.assertEquals(response.getStatusCode(), data.getResponseCode(), "Status Code:");
        }

    }
}
