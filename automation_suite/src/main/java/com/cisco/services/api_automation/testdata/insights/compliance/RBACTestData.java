package com.cisco.services.api_automation.testdata.insights.compliance;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Iterator;

public class RBACTestData {

    private static String PATH_SEPARATOR(){
        return System.getProperty("file.separator");
    }

    @DataProvider(name="ReadOnlyUserData")
    public static Iterator<TestData> readOnlyUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "ReadOnly.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }

    @DataProvider(name="AssetUserData")
    public static Iterator<TestData> assetUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "AssetUser.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }

    @DataProvider(name="standardUserData")
    public static Iterator<TestData> standardUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "StandardUser.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }


    @DataProvider(name="AdminUserData")
    public static Iterator<TestData> adminUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "AdminUser.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }

    @DataProvider(name="Std_ReadOnlyUserData")
    public static Iterator<TestData> stdReadOnlyUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "StdReadOnlyUser.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }

    @DataProvider(name="Asset_ReadOnlyUserData")
    public static Iterator<TestData> assetReadOnlyUserData() throws IOException {
        String fileName = "RBAC" + PATH_SEPARATOR() + "AssetReadOnlyUser.json";
        return StaticPaths.getJSONData(fileName).iterator();
    }
}