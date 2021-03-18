package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.ViolationFiltersPOJO;
import com.cisco.services.api_automation.pojo.response.compliance.ViolationFiltersPOJO.Data.PolicyFilters;
import com.cisco.services.api_automation.pojo.response.compliance.ViolationFiltersPOJO.Data.SeverityFilters;
import com.cisco.services.api_automation.testdata.insights.compliance.ViolationFiltersData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ViolationFilter {

    @Test(dataProviderClass = ViolationFiltersData.class, dataProvider = "getUnAuthorizedRequestData")
    public void validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = ViolationFiltersData.class, dataProvider = "getBadRequestData")
    public void validateBadRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
        Assert.assertEquals(response.jsonPath().getString("error.message"), "Customer ID is null or empty", "Error Message : ");
    }

    @Test(dataProviderClass = ViolationFiltersData.class, dataProvider = "getAllData")
    public void validateUseCaseAllResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = ViolationFiltersData.class, dataProvider = "getUseCaseData")
    public void validateUseCaseResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = ViolationFiltersData.class, dataProvider = "getEmptyResponseData")
    public void validateEmptyAPIResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    public void validateResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        ViolationFiltersPOJO responseBody = response.as(ViolationFiltersPOJO.class, ObjectMapperType.JACKSON_2);
        String[] query = esQuery.split(";");
        String[] index = esIndex.split(";");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index[0], query[0]).jsonPath();

        SoftAssert softAssert = new SoftAssert();

        List<PolicyFilters> actPolicyFilter = responseBody.getData().getPolicyFilters();
        List<String> expPolicyFilter = esBody.get("aggregations.filter_PG.buckets.key");


        List<SeverityFilters> actSeverityFilter = responseBody.getData().getSeverityFilters();
        List<String> expSevFilter = esBody.getList("aggregations.filter_Severity.buckets.key");


        softAssert.assertEquals(responseBody.getData().getPolicyViolationCount(), (int) esBody.getFloat("aggregations.violationSum.value"));
        softAssert.assertEquals(actPolicyFilter.size(), expPolicyFilter.size());

        if (actPolicyFilter.size() > 0) {
            expPolicyFilter.replaceAll(String::toUpperCase);
            actPolicyFilter.stream().forEach(policy -> {
                JsonPath esResponse = RestUtils.elasticSearchNoSqlPost(index[1], query[1].replace("${policyGroup}", policy.getFilter())).jsonPath();
                softAssert.assertEquals(policy.getValue(), (int) esResponse.getFloat("aggregations.violationSum.value"));
                softAssert.assertTrue(expPolicyFilter.contains(policy.getFilter()), "Policy Filter value is incorrect");
                softAssert.assertTrue(expPolicyFilter.contains(policy.getLabel()), "Policy Label value is incorrect");
            });
        }

        softAssert.assertEquals(responseBody.getData().getSeverityFilters().size(), expSevFilter.size());

        if (actSeverityFilter.size() > 0) {
            expSevFilter.replaceAll(String::toUpperCase);
            actSeverityFilter.stream().forEach(severity -> {
                JsonPath esResponse = RestUtils.elasticSearchNoSqlPost(index[2], query[2].replace("${ruleSeverity}", severity.getFilter())).jsonPath();
                softAssert.assertEquals(severity.getValue(), (int) esResponse.getFloat("aggregations.violationSum.value"));
                softAssert.assertTrue(expSevFilter.contains(severity.getFilter().toUpperCase()), "Severity Filter value is incorrect");
                softAssert.assertTrue(expSevFilter.contains(severity.getLabel().toUpperCase()), "Severity Label value is incorrect");
            });
        }

        esBody = RestUtils.elasticSearchNoSqlPost(index[3], query[3]).jsonPath();
        softAssert.assertEquals(responseBody.getData().getAssetCount(), esBody.getInt("aggregations.uniqueAsstCount.value"));

        softAssert.assertAll();

    }

}
