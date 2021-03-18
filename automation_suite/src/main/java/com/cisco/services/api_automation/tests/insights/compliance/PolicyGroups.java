package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.testdata.insights.compliance.PolicyGroupsData;
import com.cisco.services.api_automation.utils.DBUtils;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicyGroups {

    @Test(dataProviderClass = PolicyGroupsData.class, dataProvider = "getUnAuthorizedRequestData")
    public void test1_validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = PolicyGroupsData.class, dataProvider = "getBadRequestData")
    public void test2_validateBadRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Test(dataProviderClass = PolicyGroupsData.class, dataProvider = "getPolicyGroupData")
    public void test3_validatePolicyGroups(String endPoint, String headers, String params, String user, String pgQuery) throws SQLException {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        List<String> exp = new ArrayList<>();
        ResultSet rs = DBUtils.getResultSet(pgQuery);

        while (rs.next())
            exp.add(rs.getString("policy_group"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.jsonPath().getList("policyGroup").toString(), exp.toString());
        softAssert.assertAll();
    }

}
