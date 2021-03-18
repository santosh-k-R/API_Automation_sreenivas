package com.cisco.services.api_automation.testdata.lifecycle;

import com.cisco.services.api_automation.pojo.request.LifecycleDataPojo;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.ExcelReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LifeCycleData {

    private static final String DIR = getDIR();

    public static final Map<String, String> COMMON_DATA = Commons.getTestData(DIR + "Lifecycle.xlsx", "common_data", false);
    public static final Map<String, String> ACC_BOOKMARK = Commons.getTestData(DIR + "Acc.xlsx", "ACC_BookmarkMandatoryParams", COMMON_DATA);
    public static final Map<String, String> ACC_BOOKMARK_INVALID_PARAMS = Commons.getTestData(DIR + "Acc.xlsx", "ACC_BookmarkInvalidParams", COMMON_DATA);
    public static final Map<String, String> ACC_GET_INVALID_PARAMS = Commons.getTestData(DIR + "Acc.xlsx", "ACC_GetInvalidParams", COMMON_DATA);
    public static final Map<String, String> ACC_REGISTRATION_INVALID_PARAMS = Commons.getTestData(DIR + "Acc.xlsx", "ACC_Registration_InvalidParams", COMMON_DATA);
    public static final Map<String, String> ACC_NOTIFICATION = Commons.getTestData(DIR + "Acc.xlsx", "ACC_Notification", COMMON_DATA);

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        return "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR + "Lifecycle" + PATH_SEPARATOR;
    }

    @DataProvider(name = "getAllAcc")
    public static Object[][] getAllACC() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_GetAll", true, COMMON_DATA);
    }

    @DataProvider(name = "getAllAccWithOptionalParams")
    public static Object[][] getAllAccWithOptionalParams() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_GETOptionalParams", true, COMMON_DATA);
    }

    @DataProvider(name = "getACCByStatusFilter")
    public static Object[][] getACCByStatusFilter() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_FilterByStatus", true, COMMON_DATA);
    }

    @DataProvider(name = "getACCByProviderFilter")
    public static Object[][] getACCByProviderFilter() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_FilterByProvider", true, COMMON_DATA);
    }

    @DataProvider(name = "getACCEntitlment")
    public static Object[][] getACCEntitlment() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_Entitlement", true, COMMON_DATA);
    }

    @DataProvider(name = "accRegistration")
    public static Object[][] accRegistration() {
        return Commons.getTestData(DIR + "Acc.xlsx", "ACC_Registration", true, COMMON_DATA);
    }

    public static final InputStream ACC_GETALL_SCHEMA = Commons.getResourceFile(DIR + "accGetAll_Schema.json");
    public static final InputStream BOOKMARK_SCHEMA = Commons.getResourceFile(DIR + "bookmark.json");

    //**************************************************** ATX  *********************************************************************

    public static final Map<String, String> ATX_REGISTRATION = Commons.getTestData(DIR + "Atx.xlsx", "ATX_Registration", COMMON_DATA);
    public static final Map<String, String> ATX_INVALIDPARAMS = Commons.getTestData(DIR + "Atx.xlsx", "ATX_InvalidParams", COMMON_DATA);
    public static final Map<String, String> ATX_INVALIDHEADERS = Commons.getTestData(DIR + "Atx.xlsx", "ATX_InvalidHeaders", COMMON_DATA);

    public static final InputStream ATX_REG_CANCELLED_SCHEMA = Commons.getResourceFile(DIR + "atxRegCancellation.json");

    @DataProvider(name = "atxGet")
    public static Object[][] atxGet() {
        return Commons.getTestData(DIR + "Atx.xlsx", "ATXGet", true, COMMON_DATA);
    }

    //**************************************************  E-Learning  *********************************************************
    @DataProvider(name = "eLearningGet")
    public static Object[][] eLearningGet() {
        return Commons.getTestData(DIR + "Elearning.xlsx", "ELearning_Get", true, COMMON_DATA);
    }

    public static final Map<String, String> ELEARNING_INVALIDPARAMS = Commons.getTestData(DIR + "Elearning.xlsx", "ElearningGetInvalidParams", COMMON_DATA);
    public static final Map<String, String> ELEARNING_INVALIDHEADERS = Commons.getTestData(DIR + "Elearning.xlsx", "ElearningGetInvalidHeaders", COMMON_DATA);
    public static final Map<String, String> REMOTE_LAB_LAUNCHED = Commons.getTestData(DIR + "Elearning.xlsx", "remoteLabLaunched", COMMON_DATA);

    //************************************************** Success Tips ****************************************
    @DataProvider(name = "successTipsGet")
    public static Object[][] successTipsGet() {
        return Commons.getTestData(DIR + "SuccessTips.xlsx", "successTipsGet", true, COMMON_DATA);
    }

    public static final Map<String, String> SUCCESS_TIPS_INVALID_PARAMS = Commons.getTestData(DIR + "SuccessTips.xlsx", "successTipsGetInvalidParams", COMMON_DATA);
    public static final Map<String, String> SUCCESS_TIPS_INVALID_HEADERS = Commons.getTestData(DIR + "SuccessTips.xlsx", "successTipsGetInvalidHeaders", COMMON_DATA);
    public static final InputStream SUCCESS_TIP_SCHEMA = Commons.getResourceFile(DIR + "success_tip.json");

    //*****************************************************  Racetrack ********************************************************************************************

    @DataProvider(name = "raceTrackGet")
    public static Object[][] raceTrackGet() {
        return Commons.getTestData(DIR + "Racetrack.xlsx", "raceTrackGet", true, COMMON_DATA);
    }

    public static final Map<String, String> RACETRACK_INVALID_PARAMS = Commons.getTestData(DIR + "Racetrack.xlsx", "raceTrackGetInvalidParams", COMMON_DATA);
    public static final Map<String, String> RACETRACK_INVALID_HEADERS = Commons.getTestData(DIR + "Racetrack.xlsx", "raceTrackGetInvalidHeaders", COMMON_DATA);
    public static final Map<String, String> RACETRACK_UPDATE_PITSTOP_ACTION = Commons.getTestData(DIR + "Racetrack.xlsx", "updatePitstopActions", COMMON_DATA);

    //*****************************************************  CGT ********************************************************************************************

    @DataProvider(name = "cgtQuota")
    public static Object[][] cgtQuota() {
        return Commons.getTestData(DIR + "Cgt.xlsx", "cgtQuota", true, COMMON_DATA);
    }

    @DataProvider(name = "cgtCompletedTraining")
    public static Object[][] cgtCompletedTraining() {
        return Commons.getTestData(DIR + "Cgt.xlsx", "completedTraining", true, COMMON_DATA);
    }

    @DataProvider(name = "trainingRequest")
    public static Object[][] cgtTrainingRequest() {
        return Commons.getTestData(DIR + "Cgt.xlsx", "trainingRequest", true, COMMON_DATA);
    }

    public static final Map<String, String> CGTREQUEST_INVALID_PARAMS = Commons.getTestData(DIR + "Cgt.xlsx", "trainingRequestInvalidParams", COMMON_DATA);
    public static final Map<String, String> CGTREQUEST_INVALID_HEADERS = Commons.getTestData(DIR + "Cgt.xlsx", "trainingRequestInvalidhHeaders", COMMON_DATA);

    //*****************************************************  Cisco Community ********************************************************************************************

    @DataProvider(name = "privateCommunityList")
    public static Object[][] privateCommunityList() {
        return Commons.getTestData(DIR + "Community.xlsx", "privateCommunityList", true, COMMON_DATA);
    }

    @DataProvider(name = "publicCommunityList")
    public static Object[][] publicCommunityList() {
        return Commons.getTestData(DIR + "Community.xlsx", "publicCommunityList", true, COMMON_DATA);
    }

    @DataProvider(name = "newPrivateComments")
    public static Object[][] newPrivateComments() {
        return Commons.getTestData(DIR + "Community.xlsx", "newPrivateComments", true, COMMON_DATA);
    }

    @DataProvider(name = "newPublicComments")
    public static Object[][] newPublicComments() {
        return Commons.getTestData(DIR + "Community.xlsx", "newPublicComments", true, COMMON_DATA);
    }

    //**************************************************** Common *********************************************************************************************************

    public static final Map<String, String> PARTNER_LIST = Commons.getTestData(DIR + "Common.xlsx", "partnerList", COMMON_DATA);
    public static final Map<String, String> USER_INFO = Commons.getTestData(DIR + "Common.xlsx", "userInfo", COMMON_DATA);

    //***************************************************************************************************************************************

    @DataProvider(name = "LifeCycleStaticData")
    public static Object[][] LifeCycleStaticData() {
        return Commons.getTestData(DIR + "Lifecycle.xlsx", "lifecycle", true, COMMON_DATA);
    }

    //*****************************************************JSON Exmaple******************************************************************

    public static InputStream getSchemaFromResourceDir(String fileName) {
        return Commons.getResourceFile(DIR + "schema/" + fileName);
    }

    //Read the Json file
    private static List<LifecycleDataPojo> lifecycleTestData;
    private static Map<String, String> commonData;

    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            lifecycleTestData = objectMapper.readValue(Commons.getResourceFile(DIR + "LifecycleData.json"), new TypeReference<List<LifecycleDataPojo>>() {});
            commonData = objectMapper.convertValue(lifecycleTestData.get(0), Map.class);
            lifecycleTestData = lifecycleTestData.stream().skip(1).map(data -> {
                commonData.keySet().forEach(cData -> {
                    data.setParams(data.getParams().replace(cData, commonData.get(cData)));
                    data.setHeaders(data.getHeaders().replace(cData, commonData.get(cData)));
                    data.setBody(data.getBody().replace(cData, commonData.get(cData)));
                });
                return data;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("JSON File not found in the specified path");
            e.printStackTrace();
        }
    }

    @DataProvider(name = "lifecycleJsonTestData", parallel = true)
    public static Iterator<LifecycleDataPojo> lifecycleData(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> null == dataPojo.getScenario())
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ACC_REG")
    public static Iterator<LifecycleDataPojo> accReg(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ACC_REG".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ATX_REG")
    public static Iterator<LifecycleDataPojo> atxReg(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ATX_REG".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ATX_OPTIONAL_PARAMS")
    public static Iterator<LifecycleDataPojo> atxOptionalParams(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ATX_OPTIONAL_PARAMS".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ATX_FILTER")
    public static Iterator<LifecycleDataPojo> atxFilter(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ATX_FILTER".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ACC_OPTIONAL_PARAMS")
    public static Iterator<LifecycleDataPojo> accOptionalParams(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ACC_OPTIONAL_PARAMS".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "ACC_FILTER")
    public static Iterator<LifecycleDataPojo> accFilter(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "ACC_FILTER".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "feedback")
    public static Iterator<LifecycleDataPojo> feedback(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "feedback".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "email")
    public static Iterator<LifecycleDataPojo> email(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "email".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    @DataProvider(name = "notification")
    public static Iterator<LifecycleDataPojo> notification(ITestContext context) {
        List<LifecycleDataPojo> data = lifecycleTestData.stream()
                .filter(dataPojo -> "notification".equals(dataPojo.getScenario()))
                .collect(Collectors.toList());
        data.forEach(dataPojo -> dataPojo.setContext(context));
        return data.iterator();
    }

    //    public static final List<Map<String, String>> privateComments = ExcelReader.getDataAsMap(DIR + "Community.xlsx", "newPrivateComments");
    public static final List<Map<String, String>> successTips = ExcelReader.getDataAsMap(DIR + "Lifecycle.xlsx", "successTips");
    //data.stream().filter(row -> row.get("Tag").equals("Campus_DeviceOn") && row.get("Pitstop").equals("Optimize")).toArray()

}
