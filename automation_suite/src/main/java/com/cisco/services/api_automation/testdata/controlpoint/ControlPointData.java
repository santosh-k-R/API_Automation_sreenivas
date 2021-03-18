package com.cisco.services.api_automation.testdata.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointCommonPojo;
import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.utils.Commons;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ControlPointData {

    private static final String DIR = getDIR();

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR
                + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR ;
        return DIR;
    }


    //*****************************************************JSON Exmaple******************************************************************
    //Read the Json file
    private static List<ControlPointRequestPojo> controlPointTestData;
    public static ControlPointCommonPojo controlPointCommonData;

    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectMapper objectMapperCommon = new ObjectMapper();
            controlPointTestData = objectMapper.readValue(Commons.getResourceFile(DIR + "ControlPoint.json"), new TypeReference<List<ControlPointRequestPojo>>() {
            });

            controlPointCommonData = objectMapperCommon.readValue(Commons.getResourceFile(DIR + "ControlPointCommon.json"), new TypeReference<ControlPointCommonPojo>() {
            });

        } catch (IOException e) {
            System.out.println("JSON File not found in the specified path");
            e.printStackTrace();
        }
    }



    @DataProvider(name = "healthStatusDp")
    public static final Iterator<ControlPointRequestPojo.data> accBookMarkDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("API_HealthStatus")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data healthStatusDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("API_HealthStatus")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieRegistrationDp")
    public static final Iterator<ControlPointRequestPojo.data> ieRegistrationDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("IERegistration")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieRegistrationDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("IERegistration")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieRegistrationGETDp")
    public static final Iterator<ControlPointRequestPojo.data> ieRegistrationGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("IERegistrationGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieRegistrationGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("IERegistrationGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "dnacStatusGETDp")
    public static final Iterator<ControlPointRequestPojo.data> dnacStatusGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("DNACStatusGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data dnacStatusGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("DNACStatusGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "policyDevicesGETDp")
    public static final Iterator<ControlPointRequestPojo.data> policyDevicesGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("PolicyDevicesGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data policyDevicesGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("PolicyDevicesGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getDNACsGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getDNACsGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getDNACsGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getDNACsGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getDNACsGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getPolicyDevicesWithPolicyIDGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getPolicyDevicesWithPolicyIDGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("PolicyDevicesWithPolicyIDGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getPolicyDevicesWithPolicyIDGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("PolicyDevicesWithPolicyIDGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getIgnorePolicyDevicesGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getIgnorePolicyDevicesGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getIgnorePolicyDevicesGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getIgnorePolicyDevicesExistingGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getIgnorePolicyDevicesExistingGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesExistingGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getIgnorePolicyDevicesExistingGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesExistingGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getIgnorePolicyDevicesEligibleGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getIgnorePolicyDevicesEligibleGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesEligibleGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getIgnorePolicyDevicesEligibleGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getIgnorePolicyDevicesEligibleGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getPoliciesGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getPoliciesGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getPoliciesGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getPoliciesGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getPoliciesGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getPoliciesMonthYearGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getPoliciesMonthYearGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getPoliciesMonthYearGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getPoliciesMonthYearGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getPoliciesMonthYearGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getSettingsInsightGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getSettingsInsightGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getSettingsInsightGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getSettingsInsightGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getSettingsInsightGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getUsersRolesGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getUsersRolesGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getUsersRolesGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getUsersRolesGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getUsersRolesGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getNextScheduleCollectionPolicyGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getNextScheduleCollectionPolicyGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getNextScheduleCollectionPolicyGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getNextScheduleCollectionPolicyGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getNextScheduleCollectionPolicyGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getNextScheduleCollectionPolicyCollectorIdGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getNextScheduleCollectionPolicyCollectorIdGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getNextScheduleCollectionPolicyCollectorIdGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getNextScheduleCollectionPolicyCollectorIdGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getNextScheduleCollectionPolicyCollectorIdGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "getSAUsersGETDp")
    public static final Iterator<ControlPointRequestPojo.data> getSAUsersGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("getSAUsersGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data getSAUsersGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("getSAUsersGET")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "deleteUserPOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> deleteUserPOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("deleteUserPOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data deleteUserPOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("deleteUserPOST")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "settingsInsightPOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> settingsInsightPOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightPOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data settingsInsightPOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightPOST")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieSetupStatusDNACPOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> ieSetupStatusDNACPOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieSetupStatusDNACPOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieSetupStatusDNACPOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieSetupStatusDNACPOST")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "policyCreatePOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> policyCreatePOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("policyCreatePOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data policyCreatePOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("policyCreatePOST")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieUpdateGETDp")
    public static final Iterator<ControlPointRequestPojo.data> ieUpdateGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieUpdateGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieUpdateGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieUpdateGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieReleasesGETDp")
    public static final Iterator<ControlPointRequestPojo.data> ieReleasesGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieReleasesGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieReleasesGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieReleasesGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieReleaseReleaseNoteGETDp")
    public static final Iterator<ControlPointRequestPojo.data> ieReleaseReleaseNoteGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieReleaseReleaseNoteGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieReleaseReleaseNoteGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieReleaseReleaseNoteGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieReleaseVersionGETDp")
    public static final Iterator<ControlPointRequestPojo.data> ieReleaseVersionGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieReleaseVersionGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieReleaseVersionGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieReleaseVersionGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieScheduledUpdatePOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> ieScheduledUpdatePOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieScheduledUpdatePOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieScheduledUpdatePOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieScheduledUpdatePOST")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieDeleteScheduledUpdateDELETEDp")
    public static final Iterator<ControlPointRequestPojo.data> ieDeleteScheduledUpdateDELETEDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieDeleteScheduledUpdateDELETE")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieDeleteScheduledUpdateDELETEDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieDeleteScheduledUpdateDELETE")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "ieUpdateNowPOSTDp")
    public static final Iterator<ControlPointRequestPojo.data> ieUpdateNowPOSTDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieUpdateNowPOST")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieUpdateNowPOSTDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieUpdateNowPOST")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "customerOnboardStatusGETDp")
    public static final Iterator<ControlPointRequestPojo.data> customerOnboardStatusGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("customerOnboardStatusGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data customerOnboardStatusGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("customerOnboardStatusGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "deletePolicyDELETEDp")
    public static final Iterator<ControlPointRequestPojo.data> deletePolicyDELETEDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("deletePolicyDELETE")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data deletePolicyDELETEDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("deletePolicyDELETE")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "collectionPolicyPATCHDp")
    public static final Iterator<ControlPointRequestPojo.data> collectionPolicyPATCHDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyPATCH")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data collectionPolicyPATCHDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyPATCH")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "createUserGroupDp")
    public static final Iterator<ControlPointRequestPojo.data> createUserGroupDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("createUserGroup")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data createUserGroupDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("createUserGroup")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "updateUserGroupDp")
    public static final Iterator<ControlPointRequestPojo.data> updateUserGroupDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("updateUserGroup")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data updateUserGroupDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("updateUserGroup")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "validateUserGroupDp")
    public static final Iterator<ControlPointRequestPojo.data> validateUserGroupDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("validateUserGroup")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data validateUserGroupDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("validateUserGroup")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "listUserGroupDp")
    public static final Iterator<ControlPointRequestPojo.data> listUserGroupDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("listUserGroup")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data listUserGroupDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("listUserGroup")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "deleteUserGroupDp")
    public static final Iterator<ControlPointRequestPojo.data> deleteUserGroupDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("deleteUserGroup")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data deleteUserGroupDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("deleteUserGroup")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "addUserDp")
    public static final Iterator<ControlPointRequestPojo.data> addUserDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("addUser")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data addUserDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("addUser")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "listUserDp")
    public static final Iterator<ControlPointRequestPojo.data> listUserDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("listUser")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data listUserDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("listUser")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "userGroupMarkedDp")
    public static final Iterator<ControlPointRequestPojo.data> userGroupMarkedDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("userGroupMarked")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data userGroupMarkedDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("userGroupMarked")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "userViewDp")
    public static final Iterator<ControlPointRequestPojo.data> userViewDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("userView")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data userViewDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("userView")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "userEmailDp")
    public static final Iterator<ControlPointRequestPojo.data> userEmailDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("userEmail")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data userEmailDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("userEmail")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "cloudRemoveUserDp")
    public static final Iterator<ControlPointRequestPojo.data>  cloudRemoveUserDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("cloudRemoveUser")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data cloudRemoveUserDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("cloudRemoveUser")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "userGroupRemoveDp")
    public static final Iterator<ControlPointRequestPojo.data>  userGroupRemoveDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("userGroupRemove")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data userGroupRemoveDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("userGroupRemove")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "collectionPolicyNextScheduleGETDp")
    public static final Iterator<ControlPointRequestPojo.data>  collectionPolicyNextScheduleGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyNextScheduleGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data collectionPolicyNextScheduleGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyNextScheduleGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "nextScheduleForCollectorIDGETDp")
    public static final Iterator<ControlPointRequestPojo.data>  nextScheduleForCollectorIDGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("nextScheduleForCollectorIDGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data nextScheduleForCollectorIDGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("nextScheduleForCollectorIDGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "collectionPolicyPATCHAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  collectionPolicyPATCHAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyPATCHAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data collectionPolicyPATCHAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("collectionPolicyPATCHAPI")).collect(Collectors.toList()).get(0).getData().get(0);



    public static ControlPointRequestPojo.data partnerListDataDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(0);

    public static ControlPointRequestPojo.data approvedPartnerDataDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(1);

    public static ControlPointRequestPojo.data invalidPartnerDataDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(2);

    public static ControlPointRequestPojo.data grantPartnerAccessDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(3);

    public static ControlPointRequestPojo.data searchPartnerDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(4);

    public static ControlPointRequestPojo.data rejectPartnerDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(5);

    public static ControlPointRequestPojo.data revokePartnerDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(6);

    public static ControlPointRequestPojo.data restorePartnerDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(7);

    public static ControlPointRequestPojo.data showPastPartnerUsersDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(8);

    public static ControlPointRequestPojo.data dontShowPastPartnerUsersDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(9);

    //Uses same endpoint as showPastUsers=false
    public static ControlPointRequestPojo.data  getPartnerUsersListDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(9);

    public static ControlPointRequestPojo.data approvedPartnerListDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(10);

    public static ControlPointRequestPojo.data grantPartnerUserAccessDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(11);

    public static ControlPointRequestPojo.data partnerUserAccessCheckDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(12);

    public static ControlPointRequestPojo.data revokePartnerUserAccessDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(13);

    public static ControlPointRequestPojo.data rejectPartnerUserAccessDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(14);

    public static ControlPointRequestPojo.data restorePartnerUserAccessDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("partnerData")).collect(Collectors.toList()).get(0).getData().get(15);

    //PP:showPastUsers
    @DataProvider(name = "showPastPartnerUsersDp")
    public static final Iterator<ControlPointRequestPojo.data> showPastPartnerUsersDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("PPshowPastPartnerUsers")).collect(Collectors.toList()).get(0).getData().iterator();
    }

      //  Data providers for customer API's

    @DataProvider(name = "VerifyCustomersGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCustomersGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyCustomersGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "VerifyCustomersGETInvalidCustomerIdDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCustomersGETInvalidCustomerIdDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETInvalidCustomerId")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyCustomersGETInvalidCustomerIdDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETInvalidCustomerId")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyCustomersGETInvalidccoIdDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCustomersGETInvalidccoIdDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETInvalidccoId")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyCustomersGETInvalidccoIdDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCustomersGETInvalidccoId")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAllCustomersGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAllCustomersGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyAllCustomersGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAllCustomersCAVIDGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAllCustomersCAVIDGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersCAVIDGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyAllCustomersCAVIDGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersCAVIDGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAllCustomersInvalidCAVIDGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAllCustomersInvalidCAVIDGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersInvalidCAVIDGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyAllCustomersInvalidCAVIDGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersInvalidCAVIDGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAllCustomersCavBuIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAllCustomersCavBuIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersCavBuIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyAllCustomersCavBuIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersCavBuIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAllCustomersInvalidCavBuIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAllCustomersInvalidCavBuIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersInvalidCavBuIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyAllCustomersInvalidCavBuIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAllCustomersInvalidCavBuIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyCreateAccessPolicyPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCreateAccessPolicyPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateAccessPolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data VerifyCreateAccessPolicyPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateAccessPolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAccessPolicyGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAccessPolicyGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyAccessPolicyGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyCreatedAccessPolicyInvalidCustomerIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCreatedAccessPolicyInvalidCustomerIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreatedAccessPolicyInvalidCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyCreatedAccessPolicyInvalidCustomerIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreatedAccessPolicyInvalidCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);
    @DataProvider(name = "VerifyAccessPolicyUserGroupNameGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAccessPolicyUserGroupNameGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyUserGroupNameGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyAccessPolicyUserGroupNameGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyUserGroupNameGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAccessPolicyDiscoveryGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAccessPolicyDiscoveryGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyDiscoveryGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyAccessPolicyDiscoveryGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyDiscoveryGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAccessPolicyGETPolicyIdAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAccessPolicyGETPolicyIdAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyGETPolicyIdAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyAccessPolicyGETPolicyIdAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicyGETPolicyIdAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "UpdateAccessPolicyPUTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> UpdateAccessPolicyPUTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("UpdateAccessPolicyPUTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data UpdateAccessPolicyPUTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("UpdateAccessPolicyPUTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "UpdateInvalidPolicyIdPUTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> UpdateInvalidPolicyIdPUTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("UpdateInvalidPolicyIdPUTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data UpdateInvalidPolicyIdPUTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("UpdateInvalidPolicyIdPUTAPI")).collect(Collectors.toList()).get(0).getData().get(0);
    @DataProvider(name = "UpdateAccessPolicyPUTAPIInvalidRequestDp")
    public static final Iterator<ControlPointRequestPojo.data> UpdateAccessPolicyPUTAPIInvalidRequestDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("UpdateAccessPolicyPUTAPIInvalidRequest")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data UpdateAccessPolicyPUTAPIInvalidRequestDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("UpdateAccessPolicyPUTAPIInvalidRequest")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "DeleteAccessPolicyAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> DeleteAccessPolicyAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("DeleteAccessPolicyAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data DeleteAccessPolicyAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("DeleteAccessPolicyAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "DeleteAccessPolicyInvalidPolicyIdAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> DeleteAccessPolicyInvalidPolicyIdAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("DeleteAccessPolicyInvalidPolicyIdAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data DeleteAccessPolicyInvalidPolicyIdAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("DeleteAccessPolicyInvalidPolicyIdAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    // DP for Policies API's

    @DataProvider(name = "VerifyALLPoliciesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyALLPoliciesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyALLPoliciesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyALLPoliciesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyALLPoliciesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "VerifyALLPoliciesInvalidCustomerIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyALLPoliciesInvalidCustomerIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyALLPoliciesInvalidCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyALLPoliciesInvalidCustomerIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyALLPoliciesInvalidCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyPoliciesInvalidPathGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyPoliciesInvalidPathGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesInvalidPathGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyPoliciesInvalidPathGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesInvalidPathGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyPoliciesMonthYearGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyPoliciesMonthYearGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesMonthYearGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyPoliciesMonthYearGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesMonthYearGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyAccessPolicypolicyNameGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyAccessPolicypolicyNameGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicypolicyNameGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyAccessPolicypolicyNameGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyAccessPolicypolicyNameGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "VerifyPoliciesMonthNoYearGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyPoliciesMonthNoYearGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesMonthNoYearGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyPoliciesMonthNoYearGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPoliciesMonthNoYearGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyDNACGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyDNACGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyDNACGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyDnacWithoutCustomerIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyDnacWithoutCustomerIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDnacWithoutCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyDnacWithoutCustomerIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDnacWithoutCustomerIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "VerifyDNACPolicyIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyDNACPolicyIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACPolicyIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyDNACPolicyIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACPolicyIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyDNACAssetPresentGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyDNACAssetPresentGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACAssetPresentGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyDNACAssetPresentGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDNACAssetPresentGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyCreateDeviceScanPolicyPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCreateDeviceScanPolicyPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateDeviceScanPolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyCreateDeviceScanPolicyPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateDeviceScanPolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyUpdateDeviceScanPolicyPATCHAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyUpdateDeviceScanPolicyPATCHAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyUpdateDeviceScanPolicyPATCHAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyUpdateDeviceScanPolicyPATCHAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyUpdateDeviceScanPolicyPATCHAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyPolicyDevicesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyPolicyDevicesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPolicyDevicesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyPolicyDevicesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPolicyDevicesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "VerifyPolicyWithPolicyIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyPolicyWithPolicyIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPolicyWithPolicyIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyPolicyWithPolicyIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyPolicyWithPolicyIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyDeletePolicyAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyDeletePolicyAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDeletePolicyAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyDeletePolicyAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyDeletePolicyAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyCreateIgnorePolicyPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyCreateIgnorePolicyPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateIgnorePolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyCreateIgnorePolicyPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyCreateIgnorePolicyPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyIgnorePolicyDevicesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyIgnorePolicyDevicesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyIgnorePolicyDevicesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyIgnorePolicyDevicesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyIgnorePolicyDevicesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportStatusGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportStatusGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportStatusGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportStatusGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportStatusGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportStatusWithoutRefIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportStatusWithoutRefIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportStatusWithoutRefIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportStatusWithoutRefIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportStatusWithoutRefIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportReportGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportReportGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportReportGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportReportGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportReportGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportReportWithoutRefIdGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportReportWithoutRefIdGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportReportWithoutRefIdGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportReportWithoutRefIdGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportReportWithoutRefIdGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportPayloadPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportPayloadPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportPayloadPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportPayloadWithoutCustomerPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportPayloadWithoutCustomerPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadWithoutCustomerPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportPayloadWithoutCustomerPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadWithoutCustomerPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "VerifyBulkImportPayloadWithoutRefIdPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data> VerifyBulkImportPayloadWithoutRefIdPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadWithoutRefIdPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data VerifyBulkImportPayloadWithoutRefIdPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("VerifyBulkImportPayloadWithoutRefIdPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    //Search for partner
    @DataProvider(name = "searchPartnersDp")
    public static final Iterator<ControlPointRequestPojo.data> searchPartnersDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("searchPartners")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    //Display PP tab
    @DataProvider(name = "displayPartnersTabDp")
    public static final Iterator<ControlPointRequestPojo.data> displayPartnersTabDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("displayPartnersTab")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    //Roles
    @DataProvider(name = "verifyRolesDp")
    public static final Iterator<ControlPointRequestPojo.data> verifyRolesDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("verifyRoles")).collect(Collectors.toList()).get(0).getData().iterator();
    }

    public static ControlPointRequestPojo.data allRolesDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("verifyRoles")).collect(Collectors.toList()).get(0).getData().get(0);
    // Roles data
    public static ControlPointRequestPojo.data dataForRolesDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("dataForRoles")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieUpgradeGETDp")
    public static final Iterator<ControlPointRequestPojo.data>  ieUpgradeGETDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieUpgradeGET")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieUpgradeGETDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieUpgradeGET")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "DNACStatusGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  DNACStatusGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("DNACStatusGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data DNACStatusGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("DNACStatusGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "settingsInsightPOSTAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  settingsInsightPOSTAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightPOSTAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data settingsInsightPOSTAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightPOSTAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "settingsInsightGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  settingsInsightGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data settingsInsightGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("settingsInsightGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "ieLogsFilesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  ieLogsFilesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieLogsFilesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);


    @DataProvider(name = "ieAuditLogsFilesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  ieAuditLogsFilesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieAuditLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieAuditLogsFilesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieAuditLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    @DataProvider(name = "ieDebugLogsFilesGETAPIDp")
    public static final Iterator<ControlPointRequestPojo.data>  ieDebugLogsFilesGETAPIDp() {
        return controlPointTestData.stream().filter(data -> data.getName().equals("ieDebugLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().iterator();
    }
    public static ControlPointRequestPojo.data ieDebugLogsFilesGETAPIDp =
            controlPointTestData.stream().filter(data -> data.getName().equals("ieDebugLogsFilesGETAPI")).collect(Collectors.toList()).get(0).getData().get(0);

    //Otp
    public static ControlPointRequestPojo.data otpDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("otpData")).collect(Collectors.toList()).get(0).getData().get(0);
    //registration
    public static ControlPointRequestPojo.data registrationDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("registrationData")).collect(Collectors.toList()).get(0).getData().get(0);
    //registrationData
    public static ControlPointRequestPojo.data healthStatusDataDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("healthStatusData")).collect(Collectors.toList()).get(0).getData().get(0);

    //ignorePolicyCreation
    public static ControlPointRequestPojo.data ignorePolicyDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(0);

    //ignorePolicyDeletion
    public static ControlPointRequestPojo.data invalidPayloadIgnorePolicyDDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(1);

    //ignorePolicyDeletion
    public static ControlPointRequestPojo.data invalidEndPointIgnorePolicyDDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(2);

    //ignorePolicyDeletion
    public static ControlPointRequestPojo.data ignorePolicyDeleteDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(3);
    //UpdateIgnorePolicy
    public static ControlPointRequestPojo.data updateIgnorePolicyDp=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(4);

    //IgnorePolicyExistingDevices
    public static ControlPointRequestPojo.data ignorePolicyExistingDevices=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(5);

    //IgnorePolicyEligibleDevices
    public static ControlPointRequestPojo.data ignorePolicyEligibleDevices=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(6);

    //IgnorePolicyAll(Existing+Eligible)Devices
    public static ControlPointRequestPojo.data allDvicesForIgnorePolicy=
            controlPointTestData.stream().filter(data -> data.getName().equals("ignorePolicyData")).collect(Collectors.toList()).get(0).getData().get(7);

    //IgnoryPolicyGetAllDevicesInvalidScenarios
    @DataProvider(name="IgnoryPolicyGetAllDevicesInvalidScenariosDp")
    public static final Iterator<ControlPointRequestPojo.data> IgnoryPolicyGetAllDevicesInvalidScenariosDp(){
        return controlPointTestData.stream().filter(data -> data.getName().equals("IgnoryPolicyGetAllDevicesInvalidScenarios")).collect(Collectors.toList()).get(0).getData().iterator();
    }

}

