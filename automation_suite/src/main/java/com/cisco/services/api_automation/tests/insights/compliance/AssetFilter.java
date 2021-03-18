package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.AssetFiltersPOJO;
import com.cisco.services.api_automation.pojo.response.compliance.AssetFiltersPOJO.Data.SeverityList;
import com.cisco.services.api_automation.testdata.insights.compliance.AssetFiltersData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AssetFilter {

    @Test(dataProviderClass = AssetFiltersData.class, dataProvider = "getUnAuthorizedRequestData")
    public void validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Test(dataProviderClass = AssetFiltersData.class, dataProvider = "getBadRequestData")
    public void validateBadRequest(String endPoint, String headers, String params, String user) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
        Assert.assertEquals(response.jsonPath().getString("error.message"), "Customer ID is null or empty", "Error Message : ");
    }

    @Test(dataProviderClass = AssetFiltersData.class, dataProvider = "getAllData")
    public void validateUseCaseAllResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = AssetFiltersData.class, dataProvider = "getUseCaseData")
    public void validateUseCaseResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    @Test(dataProviderClass = AssetFiltersData.class, dataProvider = "getEmptyResponseData")
    public void validateEmptyAPIResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        validateResponse(endPoint, headers, params, user, esQuery, esIndex);
    }

    public void validateResponse(String endPoint, String headers, String params, String user, String esQuery, String esIndex) {
        System.out.println("******TC Validation Start*************");
        Response response = RestUtils.get(endPoint, headers, params, new User(user));

        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        AssetFiltersPOJO responseBody = response.as(AssetFiltersPOJO.class, ObjectMapperType.JACKSON_2);

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery).jsonPath();

        SoftAssert softAssert = new SoftAssert();

        List<SeverityList> actSevList = responseBody.getData().getSeverityList();
        List<String> expSevFilter = esBody.get("aggregations.highSev_Count.buckets.key");
        List<Integer> expSevFilterValue = esBody.get("aggregations.highSev_Count.buckets.doc_count");
        System.out.println(expSevFilter.toString());
        System.out.println(expSevFilterValue.toString());

        int totalSevCount = esBody.getInt("hits.total.value");

        softAssert.assertEquals(actSevList.size(), expSevFilter.size());

        if (actSevList.size() > 0) {
            int i = 0;
            for (SeverityList severity : actSevList) {
                String expSevName = expSevFilter.get(i);
                expSevName = expSevName.substring(0, 1).toUpperCase() + expSevName.substring(1, expSevName.length());
                softAssert.assertEquals(severity.getFilter(), expSevName, "Severity Filter value is incorrect");
                softAssert.assertEquals(severity.getLabel(), expSevName, "Severity Filter Label is incorrect");
                softAssert.assertEquals(severity.getValue(), java.util.Optional.ofNullable(expSevFilterValue.get(i)), "Severity Value count is incorrect");
                float expPercentage = (expSevFilterValue.get(i) / totalSevCount) * 100;
                System.out.println("expPercentage : " + expPercentage);
                System.out.println("actPercentage : " + severity.getPercentage());
                softAssert.assertEquals(severity.getPercentage(), expPercentage, "Severity Filter Percentage is incorrect");
                i++;
            }

        }
        softAssert.assertAll();

        System.out.println("******TC Validation Ends*************");
    }
}
