package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.PARTNER_LIST;
import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.USER_INFO;

@Feature("Common Apis Lifecycle")
public class CommonsIT {

    @Severity(SeverityLevel.MINOR)
    @Test
    public void verifyGettingPartnerList() {
        Headers headers = new Headers(new Header("cx-context", PARTNER_LIST.get("headers")));
        Response response = RestUtils.get(PARTNER_LIST.get("endPoint"), headers);
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
    }

    @Severity(SeverityLevel.MINOR)
    @Test
    public void getUserInfo() {
        Headers headers = new Headers(new Header("cx-context", PARTNER_LIST.get("headers")));
        Response response = RestUtils.get(USER_INFO.get("endPoint"), headers, new User(USER_INFO.get("user")));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        JsonPath responseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.get("companyName"), USER_INFO.get("expCompanyName"), "companyName ");
        softAssert.assertEquals(responseBody.get("accessLevel"), USER_INFO.get("expAccessLevel"), "accessLevel ");
        softAssert.assertEquals(responseBody.get("userEmail"), USER_INFO.get("expEmail"), "userEmail ");
        softAssert.assertEquals(responseBody.get("ciscoContact"), USER_INFO.get("expciscoContact"), "ciscoContact ");
        softAssert.assertEquals(responseBody.get("ccoId"), USER_INFO.get("expCcoId"), "ccoId ");
        softAssert.assertEquals(responseBody.get("country"), USER_INFO.get("expCountry"), "country ");
        softAssert.assertEquals(responseBody.get("userFullName"), USER_INFO.get("expFullName"), "userFullName ");
        softAssert.assertEquals(responseBody.getBoolean("valid"), Boolean.parseBoolean(USER_INFO.get("expValid")), "valid ");

        softAssert.assertAll("Asserting the Response with the Expected Value");

    }

   /* @Test
    public void testJsonPath() {
        JsonPath jsonPath = new JsonPath(new File("userRoles.json"));
        System.out.println("Roles " + jsonPath.getList("[0].saRoles.role").get(0));
    }*/


}
