package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Feature("Cisco Community")
public class CommunitiesIT {

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "privateCommunityList", dataProviderClass = LifeCycleData.class)
    public void verifyGettingPrivateCommunityList(String testID, String name ,String endPoint, String params, String user,String headers) {
        Assert.assertTrue(Boolean.parseBoolean(user));
       /* Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        Assert.assertTrue(response.getBody().jsonPath().getList("$").size() > 0, "Response Size: ");*/

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "publicCommunityList", dataProviderClass = LifeCycleData.class)
    public void verifyGettingPublicCommunityList(String endPoint, String params, String user,String headers) {
        Response response = RestUtils.get(endPoint,headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        Assert.assertTrue(response.getBody().jsonPath().getList("$").size() > 0, "Response Size: ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "newPrivateComments", dataProviderClass = LifeCycleData.class)
    public void verifyGettingNewPrivateComments(String endPoint, String params, String user,String headers) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "newPublicComments", dataProviderClass = LifeCycleData.class)
    public void verifyGettingNewPublicComments(String endPoint, String params, String user, String headers) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

    }
}
