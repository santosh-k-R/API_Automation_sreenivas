package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.testdata.insights.compliance.RBACTestData;
import com.cisco.services.api_automation.testdata.insights.compliance.TestData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

public class RBACTestsIT {

    @Test(priority = 1, dataProviderClass = RBACTestData.class, dataProvider = "ReadOnlyUserData")
    public void validateReadOnlyUser(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 2, dataProviderClass = RBACTestData.class, dataProvider = "AssetUserData")
    public void validateAssetUserData(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 3, dataProviderClass = RBACTestData.class, dataProvider = "standardUserData")
    public void validatestandardUserData(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 4, dataProviderClass = RBACTestData.class, dataProvider = "AdminUserData")
    public void validateAdminUser(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 5, dataProviderClass = RBACTestData.class, dataProvider = "Std_ReadOnlyUserData")
    public void validateStdAndReadOnlyUser(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 6, dataProviderClass = RBACTestData.class, dataProvider = "Asset_ReadOnlyUserData")
    public void validateAssetAndReadOnlyUserData(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());
        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getScenario(), td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    private void validateResponseCode(int acutalResponseCode, int expResponseCode){
        Assert.assertEquals(acutalResponseCode, expResponseCode, "Status Code: ");
    }

    private void validateAPIResponse(String scenario, String expResponse, String actResponse, String sortName, String sortOrder) throws Exception {
        String scenarioLC = scenario.toLowerCase();
        if(scenarioLC.contains("violation summary")){
            ViolationSummaryAPITestsIT vs = new ViolationSummaryAPITestsIT();
            vs.validateAPIResponse(expResponse, actResponse, sortName, sortOrder);
        }else if(scenarioLC.contains("asset summary")){
            AssetSummaryTestsIT as = new AssetSummaryTestsIT();
            as.validateAPIResponse(expResponse, actResponse, sortName, sortOrder);
        }else
            JSONAssert.assertEquals(expResponse, actResponse, false);

    }
}
