package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.CGTREQUEST_INVALID_HEADERS;
import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.CGTREQUEST_INVALID_PARAMS;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;

@Feature("Customize Group Training(CGT)")
public class CgtIT {

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "cgtQuota", dataProviderClass = LifeCycleData.class)
    public void verifyCgtQuota(String endPoint, String params, String headers, String user, String expContractNo,
                               String expContractStartDate, String expContractEndDate) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        JsonPath responseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.get("tsa_contract_no[0]"), expContractNo, " Contract No: " + responseBody.get("tsa_contract_no[0]"));
        softAssert.assertEquals(responseBody.get("contract_start_date[0]"), expContractStartDate, "Contract Start Date: " + responseBody.get("contract_start_date[0]"));
        softAssert.assertEquals(responseBody.get("contract_end_date[0]"), expContractEndDate, "Contract End Date" + responseBody.get("contract_end_date[0]"));


        softAssert.assertAll("Asserting the Response with the Expected Value");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "cgtCompletedTraining", dataProviderClass = LifeCycleData.class)
    public void verifyCgtCompletedTraining(String endPoint, String params, String headers, String user) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProvider = "trainingRequest", dataProviderClass = LifeCycleData.class)
    public void verifyCgtTrainingRequest(String endPoint, String params, String headers, String body, String user, String esQuery, String expContractNo, String expTimeZone, String expPreferredSlot,
                                         String expPreferredLanguage, String expTrainingGoal, String expSolution, String expUseCase, String expPitStop, String expStatus, String expCcoId, String expCustomerId) throws InterruptedException {
        Response response = RestUtils.post(endPoint, body, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        JsonPath cgtResponseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cgtResponseBody.get("contract"), expContractNo, "Contract:");
        softAssert.assertEquals(cgtResponseBody.get("timezone"), expTimeZone, "timezone:");
        softAssert.assertEquals(cgtResponseBody.get("preferredSlot"), expPreferredSlot, "preferredSlot: ");
        softAssert.assertEquals(cgtResponseBody.get("preferredLanguage"), expPreferredLanguage, "preferredLanguage: ");
        softAssert.assertEquals(cgtResponseBody.get("trainingSessionGoal"), expTrainingGoal, "trainingSessionGoal: ");
        softAssert.assertEquals(cgtResponseBody.get("solution"), expSolution, "solution: ");
        softAssert.assertEquals(cgtResponseBody.get("usecase"), expUseCase, "usecase: ");
        softAssert.assertEquals(cgtResponseBody.get("pitstop"), expPitStop, "pitstop: ");
        softAssert.assertNotNull(cgtResponseBody.get("trainingRequestId"));
        softAssert.assertEquals(cgtResponseBody.get("status"), expStatus, "status: ");
        softAssert.assertEquals(cgtResponseBody.get("ccoId"), expCcoId, "ccoId: ");
        softAssert.assertEquals(cgtResponseBody.get("customerId"), expCustomerId, "customerId: ");

        cgtResponseBody.getList("items.archetype", String.class).forEach(type -> softAssert.assertTrue("Getting Started".equals(type) || "Project Planning".equals(type) || "Architecture Transition Planning".equals(type) || "Use Cases".equals(type)));

        softAssert.assertAll("Asserting the Response");

        Thread.sleep(3000);
        JsonPath esResponse = RestUtils.elasticSearchNoSqlPost("lfc_group-training-requests", esQuery).jsonPath();
        JsonPath esBody = esResponse.setRoot("hits.hits[0]._source");
        softAssert.assertEquals(cgtResponseBody.get("contract"), esBody.getString("contract"), "ES_Contract:");
        softAssert.assertEquals(cgtResponseBody.get("timezone"), esBody.getString("timezone"), "ES_timezone:");
        softAssert.assertEquals(cgtResponseBody.get("preferredSlot"), esBody.getString("preferredSlot"), "ES_preferredSlot: ");
        softAssert.assertEquals(cgtResponseBody.get("preferredLanguage"), esBody.getString("preferredLanguage"), "ES_preferredLanguage: ");
        softAssert.assertEquals(cgtResponseBody.get("trainingSessionGoal"), esBody.getString("trainingSessionGoal"), "ES_trainingSessionGoal: ");
        softAssert.assertEquals(cgtResponseBody.get("solution"), esBody.getString("solution"), "ES_solution: ");
        softAssert.assertEquals(cgtResponseBody.get("usecase"), esBody.getString("usecase"), "ES_usecase: ");
        softAssert.assertEquals(cgtResponseBody.get("pitstop"), esBody.getString("pitstop"), "ES_pitstop: ");
        softAssert.assertEquals(cgtResponseBody.get("trainingRequestId"), esBody.getString("trainingRequestId"), "ES_trainingRequestId: ");
        softAssert.assertEquals(cgtResponseBody.get("status"), esBody.getString("status"), "ES_status: ");
        softAssert.assertEquals(cgtResponseBody.get("ccoId"), esBody.getString("ccoId"), "ES_ccoId: ");
        softAssert.assertEquals(cgtResponseBody.get("customerId"), esBody.getString("customerId"), "ES_customerId: ");

        softAssert.assertAll("Asserting the Response with Elastic Search");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test()
    public void verifyCgtTrainingRequestWithInvalidParams() {
        Response response = RestUtils.post(CGTREQUEST_INVALID_PARAMS.get("endPoint"), CGTREQUEST_INVALID_PARAMS.get("body"), CGTREQUEST_INVALID_PARAMS.get("headers"), CGTREQUEST_INVALID_PARAMS.get("params"), new User(CGTREQUEST_INVALID_PARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void verifyCGTGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.post(CGTREQUEST_INVALID_HEADERS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");

    }


}
