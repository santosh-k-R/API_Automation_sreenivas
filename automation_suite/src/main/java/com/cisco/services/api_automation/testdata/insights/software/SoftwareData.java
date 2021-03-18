package com.cisco.services.api_automation.testdata.insights.software;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

import java.util.Map;

public class SoftwareData {
    private static final String DIR = getDIR();

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR + "Software" + PATH_SEPARATOR;
        return DIR;
    }

    //**********************************************************Software Data*************************************************************************
    public static final Map<String, String> OSV_COMMON_DATA = Commons.getTestData(DIR + "Common.xlsx", "Common_Data", false);
   @DataProvider(name = "updateProfile", parallel=true)
    public static final Object[][] updateProfile() {
        return Commons.getTestData(DIR + "Software.xlsx", "Update_Profile", true ,OSV_COMMON_DATA);
    }
     @DataProvider(name = "cancelProfile", parallel=true)
    public static final Object[][] cancelProfile() {
        return Commons.getTestData(DIR + "Software.xlsx", "Cancel_profile", true , OSV_COMMON_DATA);
    }
      @DataProvider(name = "updateAdminSettings", parallel=true)
    public static final Object[][] updateAdminSettings() {
        return Commons.getTestData(DIR + "Software.xlsx", "Update_Admin_Settings", true , OSV_COMMON_DATA);
    }
  /*  @DataProvider(name = "adminSettings", parallel=true)
    public static final Object[][] adminSettings() {
        return Commons.getTestData(DIR + "Software.xlsx", "Admin_Settings", true , OSV_COMMON_DATA);
    }*/
    @DataProvider(name = "fnDetails", parallel=true)
    public static final Object[][] fnDetail() {
        return Commons.getTestData(DIR + "Software.xlsx", "OSV_fnDetail", true , OSV_COMMON_DATA);
    }
    @DataProvider(name = "bugDetails")
    public static final Object[][] bugDetail() {
        return Commons.getTestData(DIR + "Software.xlsx", "OSV_BugDetails", true , OSV_COMMON_DATA);
    }
    @DataProvider(name = "PsirtDetails",  parallel=true)
    public static final Object[][] PsirtDetail() {
        return Commons.getTestData(DIR + "Software.xlsx", "OSV_PsirtDetail", true, OSV_COMMON_DATA);
    }
    /*-----------------------------------Static Data------------------------------------------*/
    @DataProvider(name = "ProfilesRecomm",  parallel=false)
    public static final Object[][] profilesRecomm() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Profiles_recomm", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "Recomm",  parallel=false)
    public static final Object[][] recomm() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Recomm", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "Summary",  parallel=false)
    public static final Object[][] summary() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Summary", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "Profiles",  parallel=false)
    public static final Object[][] profiles() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Profiles", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "Details",  parallel=false)
    public static final Object[][] details() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Details", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "Export",  parallel=false)
    public static final Object[][] export() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Export", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "versions",  parallel=false)
    public static final Object[][] versions() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "versions", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "adminSettings",  parallel=false)
    public static final Object[][] adminSettings() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "admin_settings", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "updateCancelAcceptedVersions",  parallel=false)
    public static final Object[][] updateCancelAcceptedVersions() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "update_cancel", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "affectedAssets",  parallel=false)
    public static final Object[][] affectedAssets() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "Affected_Assets", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "inactivetoken",  parallel=false)
    public static final Object[][] inactivetoken() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "OSVAPI_Failures", true, OSV_COMMON_DATA);
    }
    @DataProvider(name = "feature",  parallel=false)
    public static final Object[][] feature() {
        return Commons.getTestData(DIR + "Software_new.xlsx", "features", true, OSV_COMMON_DATA);
    }
}
