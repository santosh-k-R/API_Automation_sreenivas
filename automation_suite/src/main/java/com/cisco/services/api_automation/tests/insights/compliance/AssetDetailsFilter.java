package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.AssetDetailsFilterPOJO;
import com.cisco.services.api_automation.testdata.insights.compliance.AssetDetailsFilterData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AssetDetailsFilter {

    @Test(dataProviderClass = AssetDetailsFilterData.class, dataProvider = "getUnAuthorizedRequestData")
    public void validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = AssetDetailsFilterData.class, dataProvider = "getBadRequestData")
    public void validateBadRequest(String endPoint, String headers, String params, String user, String errMsg) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
        Assert.assertEquals(response.jsonPath().getString("error.message"), errMsg, "Error Message : ");
    }

    @Test(dataProviderClass = AssetDetailsFilterData.class, dataProvider = "getAllData")
    public void validateUseCaseAllResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = AssetDetailsFilterData.class, dataProvider = "getUseCaseData")
    public void validateUseCaseResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = AssetDetailsFilterData.class, dataProvider = "getEmptyResponseData")
    public void validateEmptyAPIResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    public void validateResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        System.out.println("******TC Validation Start*************");
        Response response = RestUtils.get(endPoint, headers, params, new User(user));

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        AssetDetailsFilterPOJO responseBody = response.as(AssetDetailsFilterPOJO.class, ObjectMapperType.JACKSON_2);

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery).jsonPath();

        SoftAssert softAssert = new SoftAssert();

        List<String> actPGList = responseBody.getData().getPolicyGroupName();
        List<String> actSevList = responseBody.getData().getRuleHighSeverity();
        List<String> expPGList = esBody.get("aggregations.uniquePGName.buckets.key");
        List<String> expSevList = esBody.get("aggregations.uniqueSevName.buckets.key");
        System.out.println(actPGList);
        System.out.println(actSevList);
        System.out.println(expPGList);
        System.out.println(expSevList);

        softAssert.assertEquals(actPGList.size(), expPGList.size());
        if (actPGList.size() > 0) {
            expPGList.replaceAll(String::toUpperCase);
            for (int i = 0; i < expPGList.size(); i++) {
                softAssert.assertEquals(actPGList.get(i), expPGList.get(i));
            }
        }

        softAssert.assertEquals(actSevList.size(), expSevList.size());
        if (actSevList.size() > 0) {
            String exp;
            for (int j = 0; j < expSevList.size(); j++) {
                exp = expSevList.get(j);
                exp = exp.substring(0, 1).toUpperCase() + exp.substring(1, exp.length());
                softAssert.assertEquals(actSevList.get(j), exp);
            }
        }
        softAssert.assertAll();
        System.out.println("******TC Validation Ends*************");
    }

}
