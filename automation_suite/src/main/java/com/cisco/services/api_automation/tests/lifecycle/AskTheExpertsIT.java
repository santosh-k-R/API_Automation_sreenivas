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
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Feature("Ask The Experts: ATX")
public class AskTheExpertsIT {

    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void verifyATXRegistration() {
        //Since the api does not allow to register the atx more than once, hence we will cancelled the one(if registered), then re-register
        Response response = RestUtils.elasticSearchNoSqlPost("lfc_atx_user_transactions", ATX_REGISTRATION.get("esQuery"));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        JsonPath jsonPath = response.getBody().jsonPath();
        if (jsonPath.get("hits.hits[0]._source.transactionType").equals("Registered")) {
            Response atxCancelledResponse = RestUtils.delete(ATX_REGISTRATION.get("endPoint"), ATX_REGISTRATION.get("headers"), ATX_REGISTRATION.get("params"), new User(ATX_REGISTRATION.get("user")));
            if (atxCancelledResponse.getStatusCode() != 200)
                throw new SkipException("Unable to cancelled the existing session");
        }
        //Registers a Session for the ATX
        Headers headers = new Headers(new Header("saId", ATX_REGISTRATION.get("headers").split(",")[1]));
        Response atxRegistrationResponse = RestUtils.post(ATX_REGISTRATION.get("endPoint"), headers, ATX_REGISTRATION.get("params"), new User(ATX_REGISTRATION.get("user")));
        if (atxRegistrationResponse.getStatusCode() == 500)
            throw new SkipException("Session is of the Past date");
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        atxRegistrationResponse.then().body(matchesJsonSchema(ATX_REG_CANCELLED_SCHEMA)).log().ifError();

        JsonPath atxRegistrationJsonResponse = atxRegistrationResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(atxRegistrationJsonResponse.get("atxId"), ATX_REGISTRATION.get("exp_atxID"), "ATX ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("ccoId"), ATX_REGISTRATION.get("exp_ccoId"), "CCOID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("sessionId"), ATX_REGISTRATION.get("exp_sessionId"), "Session ID: ");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("customerId"), ATX_REGISTRATION.get("exp_customerId"), "Customer ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("transactionType"), ATX_REGISTRATION.get("transactionType"), "Transaction Type:");
        softAssert.assertAll("Asserting the Response");

        Response esResponse = RestUtils.elasticSearchNoSqlPost("lfc_atx_user_transactions", ATX_REGISTRATION.get("esQuery"));
        JsonPath esBody = esResponse.getBody().jsonPath().setRoot("hits.hits[0]._source");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("atxId"), esBody.getString("atxId"), "ATX ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("ccoId"), esBody.getString("ccoId"), "CCOID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("sessionId"), esBody.getString("sessionId"), "Session ID: ");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("customerId"), esBody.getString("customerId"), "Customer ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("transactionType"), esBody.getString("transactionType"), "Transaction Type:");
        softAssert.assertAll("Asserting the Response");

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = {"verifyATXRegistration"})
    public void verifyATXCancellation() {
        Response atxCancelledResponse = RestUtils.delete(ATX_REGISTRATION.get("endPoint"), ATX_REGISTRATION.get("headers"), ATX_REGISTRATION.get("params"), new User(ATX_REGISTRATION.get("user")));
        Assert.assertEquals(atxCancelledResponse.getStatusCode(), 200, "Status Code: ");
        atxCancelledResponse.then().body(matchesJsonSchema(ATX_REG_CANCELLED_SCHEMA)).log().ifError();

        JsonPath atxRegistrationJsonResponse = atxCancelledResponse.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(atxRegistrationJsonResponse.get("atxId"), ATX_REGISTRATION.get("exp_atxID"), "ATX ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("ccoId"), ATX_REGISTRATION.get("exp_ccoId"), "CCOID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("sessionId"), ATX_REGISTRATION.get("exp_sessionId"), "Session ID: ");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("customerId"), ATX_REGISTRATION.get("exp_customerId"), "Customer ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("transactionType"), "Canceled", "Transaction Type:");
        softAssert.assertAll("Asserting the Response");

        Response esResponse = RestUtils.elasticSearchNoSqlPost("lfc_atx_user_transactions", ATX_REGISTRATION.get("esQuery"));
        JsonPath esBody = esResponse.getBody().jsonPath().setRoot("hits.hits[0]._source");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("atxId"), esBody.getString("atxId"), "ATX ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("ccoId"), esBody.getString("ccoId"), "CCOID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("sessionId"), esBody.getString("sessionId"), "Session ID: ");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("customerId"), esBody.getString("customerId"), "Customer ID:");
        softAssert.assertEquals(atxRegistrationJsonResponse.get("transactionType"), esBody.getString("transactionType"), "Transaction Type:");
        softAssert.assertAll("Asserting the Response");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyAtxGetWithInvalidParams() {
        Headers headers = new Headers(new Header("saId", ATX_INVALIDPARAMS.get("headers").split(",")[1]));
        Response response = RestUtils.post(ATX_INVALIDPARAMS.get("endPoint"), ATX_INVALIDPARAMS.get("body"),ATX_INVALIDPARAMS.get("headers"), ATX_INVALIDPARAMS.get("params"), new User(ATX_INVALIDPARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyAtxGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.post(ATX_INVALIDHEADERS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");

    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "atxGet")
    public void verifyAtxGet(String endPoint, String params, String headers, String user, String esQuery, String expSolution, String expUseCase, String expPitstop) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status code: ");

        JsonPath atxResponse = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(atxResponse.get("solution"), expSolution, "Soultion ");
        softAssert.assertEquals(atxResponse.get("usecase"), expUseCase, "usecase ");
        softAssert.assertEquals(atxResponse.get("pitstop"), expPitstop, "Pitstop ");

        softAssert.assertAll("Asserting the response with the expected value");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost("lfc_atx_alias", esQuery).jsonPath();
        Assert.assertTrue(atxResponse.getList("items").size() >= esBody.getInt("hits.total.value"), "ES Number of Rows: ");
        esBody.setRoot("hits.hits._source");

        List<String> esTitles = esBody.getList("title", String.class).stream().sorted().collect(Collectors.toList());
        List<String> titles = atxResponse.getList("items.title", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(titles, esTitles, "Titles :");

        List<String> esDescription = esBody.getList("description", String.class).stream().sorted().collect(Collectors.toList());
        List<String> descriptions = atxResponse.getList("items.description", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(descriptions, esDescription, "Descriptions :");

        List<String> esImageURLs = esBody.getList("imageURL", String.class).stream().sorted().collect(Collectors.toList());
        List<String> imageURLs = atxResponse.getList("items.imageURL", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(imageURLs, esImageURLs, "Image URLs :");

        List<String> esRecordingURLs = esBody.getList("recordingURL", String.class).stream().sorted().collect(Collectors.toList());
        List<String> recordingURLs = atxResponse.getList("items.recordingURL", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(recordingURLs, esRecordingURLs, "recordingURL :");

        List<String> esDurations = esBody.getList("duration", String.class).stream().sorted().collect(Collectors.toList());
        List<String> durations = atxResponse.getList("items.duration", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(durations, esDurations, "duration :");

        List<String> esTechnologyAreas = esBody.getList("technologyArea", String.class).stream().sorted().collect(Collectors.toList());
        List<String> technologyAreas = atxResponse.getList("items.technologyArea", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(technologyAreas, esTechnologyAreas, "technologyArea :");

        List<String> esAtxIds = esBody.getList("atxId", String.class).stream().sorted().collect(Collectors.toList());
        List<String> atxIds = atxResponse.getList("items.atxId", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(atxIds, esAtxIds, "atxId :");

        softAssert.assertAll("Asserting the response with the Elastic Search");

    }
}
