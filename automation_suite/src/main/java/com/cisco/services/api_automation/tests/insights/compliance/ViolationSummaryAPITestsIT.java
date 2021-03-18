package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.ViolationSummaryPOJO;
import com.cisco.services.api_automation.testdata.insights.compliance.TestData;
import com.cisco.services.api_automation.testdata.insights.compliance.ViolationSummaryData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

@Feature("Violation Summary API")
public class ViolationSummaryAPITestsIT {

    @Test(priority = 1, dataProviderClass = ViolationSummaryData.class, dataProvider = "NoFiltersAndSearchData")
    public void validateWithoutFiltersAndSearch(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 2, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegTypeFilterData")
    public void validateRegTypeFilter(ITestContext context, TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }



    @Test(priority = 3, dataProviderClass = ViolationSummaryData.class, dataProvider = "HighSevFilterData")
    public void validateHighSevFilter(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 4, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegTypeAndSevFilterData")
    public void validateRegTypeAndSevFilter(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 5, dataProviderClass = ViolationSummaryData.class, dataProvider = "SearchAllData")
    public void validateSearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 6, dataProviderClass = ViolationSummaryData.class, dataProvider = "SearchDNACData")
    public void validateSearchDNAC(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 7, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegType_SearchAllData")
    public void validateRegType_SearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 8, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegType_SearchDNACData")
    public void validateRegType_SearchDNAC(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 9, dataProviderClass = ViolationSummaryData.class, dataProvider = "Sev_SearchAllData")
    public void validateSev_SearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 10, dataProviderClass = ViolationSummaryData.class, dataProvider = "Sev_SearchDNACData")
    public void validateSev_SearchDNAC(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 11, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegType_Sev_Search")
    public void validateRegType_Sev_SearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 12, dataProviderClass = ViolationSummaryData.class, dataProvider = "RegType_Sev_DNACSearch")
    public void validateRegType_Sev_DNACSearch(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 13, dataProviderClass = ViolationSummaryData.class, dataProvider = "ErrorScenarios")
    public void validateViolationSummaryErrorScenarios(TestData td){
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody(), td.getUserRole());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
    }


    private void validateResponseCode(int acutalResponseCode, int expResponseCode){
        Assert.assertEquals(acutalResponseCode, expResponseCode, "Status Code: ");
    }

    public void validateAPIResponse(String expResponse, String actResponse, String sortName, String sortOrder) throws Exception {
        compareSortKeyValues(expResponse, actResponse, sortName, sortOrder);
        try {
            compareExpVsActResponse(expResponse, actResponse, true);
        }catch (AssertionError | Exception e){
            compareExpVsActResponse(expResponse, actResponse, false);
        }
    }

    private void compareSortKeyValues(String expResponse, String actResponse, String sortName, String sortOrder) throws JsonProcessingException {
        List expList = getValueListForKey(expResponse, sortName);
        if (expList.size() > 0) {
            sortList(expList, sortOrder);
            Assert.assertEquals(getValueListForKey(actResponse, sortName),
                    expList, "Sort Order is incorrect");
        }else
            Assert.assertEquals(getValueListForKey(actResponse, sortName).size(), 0, "Asset List size is incorrect");
    }

    private List getValueListForKey(String response , String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ViolationSummaryPOJO pojo = mapper.readValue(response, ViolationSummaryPOJO.class);

        switch (key){
            case "ruleSeverityId" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getRuleSeverityId).collect(Collectors.toList());

            case "policyGroupName" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getPolicyGroupName).collect(Collectors.toList());

            case "policyCategory" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getPolicyCategory).collect(Collectors.toList());

            case "ruleTitle" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getRuleTitle).collect(Collectors.toList());

            case "violationCount" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getViolationCount).collect(Collectors.toList());

            case "impactedAssetCount" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getImpactedAssetCount).collect(Collectors.toList());

            case "mgmtSystemAddr" : return pojo.getData().getSummary().stream().map(ViolationSummaryPOJO.Data.Summary::getMgmtSystemAddr).collect(Collectors.toList());

        }
        return new ArrayList();
    }

    private void sortList(List list, String sortOrder){
        if(list.get(0) instanceof String){
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            if(isDescending(sortOrder))
                Collections.reverse(list);
        }else{
            Collections.sort(list);
            if(isDescending(sortOrder))
                Collections.reverse(list);
        }
    }

    private boolean isDescending(String sortOrder){
        return sortOrder.toLowerCase().equals("desc");
    }

    private void compareExpVsActResponse(String expResponse, String actResponse, boolean isLenient) throws Exception {
        JSONAssert.assertEquals(expResponse, actResponse, isLenient);
    }

}
