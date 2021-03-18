package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.testdata.insights.compliance.OptInDetailData;
import com.cisco.services.api_automation.utils.DBUtils;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class OptInDetail {


    @Test(dataProviderClass = OptInDetailData.class, dataProvider = "getUnAuthorizedRequestData")
    public void test1_validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = OptInDetailData.class, dataProvider = "getBadRequestData")
    public void test2_validateBadRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Test(dataProviderClass = OptInDetailData.class, dataProvider = "getNotEnrolledCustomerData")
    public void test3_validateNotEnrolledCustomer(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws SQLException {
        validateResponse(endPoint, headers, params, user, OptInStatus, runOnceStatus);
    }

    @Test(dataProviderClass = OptInDetailData.class, dataProvider = "getOptInData")
    public void test4_validateOptIn(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws SQLException {
        String[] query = pgQuery.split(";");
        DBUtils.executeQuery(query[0] + ";" + query[1]);
        validateResponse(endPoint, headers, params, user, OptInStatus, runOnceStatus);
    }

    @Test(dataProviderClass = OptInDetailData.class, dataProvider = "getOptOutData")
    public void test5_validateOptOut(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws Exception {
        String[] query = pgQuery.split(";");
        System.out.println("query[0] : " + query[0]);
        System.out.println("query[1] : " + query[1]);
        System.out.println("query[2] : " + query[2]);
        DBUtils.executeQuery(query[0] + ";" + query[2]);
        validateResponse(endPoint, headers, params, user, OptInStatus, runOnceStatus);
        DBUtils.executeQuery(query[1] + ";" + query[2]);
    }

    public void validateResponse(String endPoint, String headers, String params, String user, String OptInStatus, String runOnceStatus) throws SQLException {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Boolean.valueOf(response.jsonPath().getString("data.rccOptInStatus")), Boolean.valueOf(OptInStatus));
        softAssert.assertEquals(Boolean.valueOf(response.jsonPath().getString("data.runOnce")), Boolean.valueOf(runOnceStatus));
        softAssert.assertAll();
    }

}
