package com.cisco.services.api_automation.tests.cases;

import com.cisco.services.api_automation.testdata.cases.CasesData;
import com.cisco.services.api_automation.utils.CasesRestUtils;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.auth.TokenGenerator;
import com.cisco.services.api_automation.utils.auth.User;
import com.google.common.collect.Ordering;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;
import static com.cisco.services.api_automation.utils.customassert.Assert.assertEquals;
import static com.cisco.services.api_automation.utils.customassert.Assert.assertTrue;
import static java.util.Collections.sort;

@Feature("Cases API")
public class CasesIT {

    private static final String USER_ROLE = "superAdmin";
    private static List<String> awsResponseHeaderList = new ArrayList<>();

    @BeforeClass
    public void getToken() {
        TokenGenerator.getToken(new User(USER_ROLE));
        CasesRestUtils.getCaseBearerToken();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = CasesData.class, dataProvider = "CasesStaticData")
    public void verifyCasesAPIs(String type, String method, String url, String headers, String params, String body, String expectedStatusCode) {
        List<String> apiCaseList;
        ArrayList caseList;
        ArrayList sortList = new ArrayList();

        if (params.contains("1day")) {
            params = params.replace("1day", Commons.date(1));
        }
        if (params.contains("1week")) {
            params = params.replace("1week", Commons.date(7));
        }

        Response response = getAPIResponse(method, url, headers, params, body, USER_ROLE);
        awsResponseHeaderList.add("url=" + url + ",responseStatusCode=" + response.getStatusCode() + ",x-amzn-RequestId=" + response.getHeader("x-amzn-RequestId") + "");
        assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

        caseList = (ArrayList) CasesRestUtils.getCaseListData(USER_ROLE, params, "responseDetails.cases.caseNumber");
        apiCaseList = response.path("responseDetails.content.caseNumber");

        if (type.equalsIgnoreCase("filter")) {
            sort(caseList);
            sort(apiCaseList);
            assertEquals(caseList, apiCaseList, "Case numbers are not proper");
        } else {
            if (type.contains("status")) {
                sortList = response.path("responseDetails.content.status");
            } else if (type.contains("lastModified")) {
                sortList = response.path("responseDetails.content.lastModifiedDate");
            } else if (type.contains("contact")) {
                sortList = response.path("responseDetails.content.contactId");
            } else if (type.contains("summary")) {
                sortList = response.path("responseDetails.content.summary");
            } else if (type.contains("caseNumber")) {
                sortList = response.path("responseDetails.content.caseNumber");
            } else if (type.contains("priority")) {
                sortList = response.path("responseDetails.content.priority");
            } else if (type.contains("createdDate")) {
                sortList = response.path("responseDetails.content.createdDate");
            }
            System.out.println(Arrays.toString(sortList.toArray()));
            if (params.contains("DESC")) {
                assertTrue(Ordering.allEqual().reverse().isOrdered(sortList), "Not in descending order");
            } else {
                assertTrue(Ordering.allEqual().isOrdered(sortList), "Not in ascending order");
            }

        }

    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = CasesData.class, dataProvider = "SupportMetricsStaticData")
    public void verifySupportMetricsAPIs(String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput) throws JSONException {

        Response response = getAPIResponse(method, url, headers, params, body, USER_ROLE);
        awsResponseHeaderList.add("url=" + url + ",responseStatusCode=" + response.getStatusCode() + ",x-amzn-RequestId=" + response.getHeader("x-amzn-RequestId") + "");
        assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
        JSONAssert.assertEquals(expectedOutput, response.getBody().prettyPrint(), JSONCompareMode.LENIENT);

    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = CasesData.class, dataProvider = "EntitlementStaticData")
    public void verifyEntitlementAPIs(String method, String url, String headers, String params, String body, String expectedStatusCode, String expectedOutput) throws JSONException {

        Response response = getAPIResponse(method, url, headers, params, body, USER_ROLE);
        awsResponseHeaderList.add("url=" + url + ",responseStatusCode=" + response.getStatusCode() + ",x-amzn-RequestId=" + response.getHeader("x-amzn-RequestId") + "");
        assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

        JSONAssert.assertEquals(expectedOutput, response.getBody().prettyPrint(), JSONCompareMode.LENIENT);
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = CasesData.class, dataProvider = "SearchStaticData")
    public void verifySearchAPIs(String method, String url, String headers, String params, String body, String expectedStatusCode) {

        Response response = getAPIResponse(method, url, headers, params, body, USER_ROLE);
        System.out.println();
        awsResponseHeaderList.add("url=" + url + ",responseStatusCode=" + response.getStatusCode() + ",x-amzn-RequestId=" + response.getHeader("x-amzn-RequestId") + "");
        assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

    }

    @AfterSuite
    public void printResponseList() {
        System.out.println("********************************************************");
        System.out.println("Printing the list of response headers");
        awsResponseHeaderList.forEach(System.out::println);
    }

}
