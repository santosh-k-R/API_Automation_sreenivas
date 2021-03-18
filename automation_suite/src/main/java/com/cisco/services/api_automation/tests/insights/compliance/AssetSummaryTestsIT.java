package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.AssetSummaryPOJO;
import com.cisco.services.api_automation.testdata.insights.compliance.AssetSummaryData;
import com.cisco.services.api_automation.testdata.insights.compliance.TestData;
import com.cisco.services.api_automation.testdata.insights.compliance.ViolationSummaryData;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

@Feature("Asset Summary API")
public class AssetSummaryTestsIT {

    @Test(priority = 1, dataProviderClass = AssetSummaryData.class, dataProvider = "NoFiltersAndSearchData")
    public void validateWithoutFiltersAndSearch(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 2, dataProviderClass = AssetSummaryData.class, dataProvider = "HighSevFilterData")
    public void validateHighSevFilter(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 3, dataProviderClass = AssetSummaryData.class, dataProvider = "SearchAllData")
    public void validateSearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 4, dataProviderClass = AssetSummaryData.class, dataProvider = "SearchDNACData")
    public void validateDNACSearch(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
            validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 5, dataProviderClass = AssetSummaryData.class, dataProvider = "Sev_SearchAllData")
    public void validateSevFilter_SearchAll(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 6, dataProviderClass = AssetSummaryData.class, dataProvider = "Sev_SearchDNACData")
    public void validateSev_SearchDNACData(TestData td) throws Exception {
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        Response response = getAPIResponse(td.getMethod(), td.getUrl(), td.getHeaders(),
                td.getParams(), td.getBody());

        validateResponseCode(response.getStatusCode(), Integer.parseInt(td.getStatusCode()));
        validateAPIResponse(td.getExpResponse(), response.asString(), td.getSortName(), td.getSortOrder());
    }

    @Test(priority = 7, dataProviderClass = AssetSummaryData.class, dataProvider = "ErrorScenarios")
    public void validateAssetSummaryErrorScenarios(ITestContext context, TestData td){
        Allure.descriptionHtml("<h4 style=\"color:blue;\"><em>"+ td.getScenario() + "</em></h4>");
        context.setAttribute("testName", td.getScenario());
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
            JSONAssert.assertEquals(expResponse, actResponse, true);
        }catch (AssertionError | Exception e){
            JSONAssert.assertEquals(expResponse, actResponse,
                    new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("data.assetList[*].lastScan", (o1, o2) -> true)));
        }
    }

    private void compareSortKeyValues(String expResponse, String actResponse, String sortName, String sortOrder) throws JsonProcessingException {
        List expList = getValueListForKey(expResponse, sortName);

        if (expList.size() > 0){
            if(sortName.toLowerCase().equals("lastscan"))
                expList = getValueListForKey(actResponse, sortName);

            sortList(expList, sortOrder);
            Assert.assertEquals(getValueListForKey(actResponse, sortName),
                    expList, "Sort Order is incorrect");
        }else
            Assert.assertEquals(getValueListForKey(actResponse, sortName).size(), 0, "Asset List size is incorrect");

    }

    private List getValueListForKey(String response, String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AssetSummaryPOJO pojo = mapper.readValue(response, AssetSummaryPOJO.class);

        switch (key){
            case "hostName" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getHostName).collect(Collectors.toList());

            case "ipAddress" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getIpAddress).collect(Collectors.toList());

            case "lastScan" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getLastScan).collect(Collectors.toList());

            case "osType" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getOsType).collect(Collectors.toList());

            case "osVersion" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getOsVersion).collect(Collectors.toList());

            case "ruleSeverityId": return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getRuleSeverityId).collect(Collectors.toList());

            case "violationCount" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getViolationCount).collect(Collectors.toList());

            case "mgmtSystemAddr" : return pojo.getData().getAssetList().stream().map(AssetSummaryPOJO.Data.AssetList::getMgmtSystemAddr).collect(Collectors.toList());

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

}
