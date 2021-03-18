package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.testdata.insights.compliance.UpdateOptInStatusData;
import com.cisco.services.api_automation.utils.DBUtils;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateOptInStatus {

    @Test(dataProviderClass = UpdateOptInStatusData.class, dataProvider = "getUnAuthorizedRequestData")
    public void test1_validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.putWithoutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = UpdateOptInStatusData.class, dataProvider = "getBadRequestData")
    public void test2_validateBadRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.put(endPoint, "", headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Test(dataProviderClass = UpdateOptInStatusData.class, dataProvider = "getOptInData")
    public void test3_validateOptIn(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws SQLException {
        validateResponse(endPoint, headers, params, user, pgQuery, OptInStatus, runOnceStatus);
    }

    @Test(dataProviderClass = UpdateOptInStatusData.class, dataProvider = "getOptOutData")
    public void test4_validateOptOut(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws SQLException {
        String[] query = pgQuery.split(";");
        DBUtils.executeQuery(query[0] + ";" + query[1]);
        validateResponse(endPoint, headers, params, user, query[2], OptInStatus, runOnceStatus);
    }

    @Test(dataProviderClass = UpdateOptInStatusData.class, dataProvider = "getExistingCustomerData")
    public void test5_validateOptInForExistingCustomer(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws Exception {
        String[] query = pgQuery.split(";");
        validateResponse(endPoint, headers, params, user, query[0], OptInStatus, runOnceStatus);
        System.out.println("query[1] : " + query[1]);
        System.out.println("query[2] : " + query[2]);
        Thread.sleep(2000);
        System.out.println(DBUtils.executeQuery(query[1] + ";" + query[2]));
    }

    public void validateResponse(String endPoint, String headers, String params, String user, String pgQuery, String OptInStatus, String runOnceStatus) throws SQLException {
        Response response = RestUtils.put(endPoint, "", headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        SoftAssert softAssert = new SoftAssert();
        ResultSet rs = DBUtils.getResultSet(pgQuery);

        int i = 0;
        while (rs.next()) {
            System.out.println("optin ..." + rs.getBoolean("opt_in_flag"));
            System.out.println("runOnce ..." + rs.getBoolean("run_once"));
            softAssert.assertEquals(rs.getBoolean("opt_in_flag"), java.util.Optional.of(Boolean.valueOf(OptInStatus)));
            softAssert.assertEquals(rs.getBoolean("run_once"), java.util.Optional.of(Boolean.valueOf(runOnceStatus)));
            i++;
        }
        Assert.assertEquals(i, 1);
        softAssert.assertAll();
    }


}
