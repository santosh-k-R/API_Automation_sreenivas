package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.pojo.response.lifecycle.AccGetPojo;
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
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.*;
import static com.cisco.services.api_automation.utils.Commons.constructHeader;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Feature("Acceleartor (ACC)")
public class AcceleratorsIT {

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "accRegistration")
    public void verifyACCRegistration(String endPoint, String body, String headers, String params, String esQuery, String user) {
        Response response = RestUtils.post(endPoint, body, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost("lfc_acc_user_transactions", esQuery).jsonPath();
        Assert.assertEquals(esBody.get("hits.hits[0]._source.status"), "requested", "Status : ");
    }

    @Severity(SeverityLevel.MINOR)
    @Test()
    public void verifyACCRegistrationWithInvalidParams() {
        Response response = RestUtils.post(ACC_REGISTRATION_INVALID_PARAMS.get("endPoint"), ACC_REGISTRATION_INVALID_PARAMS.get("body"), ACC_REGISTRATION_INVALID_PARAMS.get("headers"), ACC_REGISTRATION_INVALID_PARAMS.get("params"), new User(ACC_REGISTRATION_INVALID_PARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Severity(SeverityLevel.MINOR)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "accRegistration")
    public void verifyACCRegistrationWithInvalidHeaders(String endPoint, String body, String headers, String params, String esQuery, String user) {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.post(endPoint, spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "getAllAcc")
    public void verifyAccGetRequestWithMandatoryParams(String endPoint, String headers, String params, String index, String esQuery, String esAccTransactionQuery, String esFeedbackQuery, String expSolution, String expUseCase, String expPitStop) {
        Response accResponse = RestUtils.get(endPoint, headers, params);

        Assert.assertEquals(accResponse.getStatusCode(), 200, "Status Code: ");
        AccGetPojo responseBody = accResponse.as(AccGetPojo.class, ObjectMapperType.JACKSON_2);
        //accResponse.then().body(matchesJsonSchema(ACC_GETALL_SCHEMA)).log().ifValidationFails();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getSolution(), expSolution, "Soultion : ");
        softAssert.assertEquals(responseBody.getUsecase(), expUseCase, "Usecase:");
        softAssert.assertEquals(responseBody.getPitstop(), expPitStop, "Pitstop : ");
        softAssert.assertTrue(responseBody.getItems().size() > 0, "Item Array size  : ");

        softAssert.assertAll("Asserting the response");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
        Assert.assertTrue(responseBody.getItems().size() >= esBody.getInt("hits.total.value"), "ES Number of Rows: ");

        List<String> esTitles = esBody.getList("hits.hits._source.title", String.class);
        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> softAssert.assertTrue(esTitles.contains(item.getTitle()), "ES_Title" + item.getTitle()));

        List<String> esDescriptions = esBody.getList("hits.hits._source.description", String.class);
        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> softAssert.assertTrue(esDescriptions.contains(item.getDescription()), "ES Description " + item.getDescription()));

        List<String> accIds = esBody.getList("hits.hits._source.accId", String.class);
        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> softAssert.assertTrue(accIds.contains(item.getAccId()), "Acc IDs : " + item.getAccId()));

        List<String> datasheetURLs = esBody.getList("hits.hits._source.datasheetURL", String.class);
        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> softAssert.assertTrue(datasheetURLs.contains(item.getDatasheetURL()), "Data Sheet URLs : " + item.getDatasheetURL()));


        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> {
            JsonPath esAccTransactionBody = RestUtils.elasticSearchNoSqlPost("lfc_acc_user_transactions", esAccTransactionQuery.replace("${accId}", item.getAccId())).jsonPath();
            String esStatus = esAccTransactionBody.get("hits.hits[0]._source.status");
            long deliveredDate;
            try {
                deliveredDate = esAccTransactionBody.getLong("hits.hits[0]._source.deliveredDate");
            } catch (NullPointerException e) {
                deliveredDate = 0;
            }
            if (esStatus == null || deliveredDate < System.currentTimeMillis()) {
                softAssert.assertEquals(item.getStatus(), "recommended", "ES Status: " + item.getStatus());
                softAssert.assertEquals(item.getRequestId(), "", "ES RequestedID: " + item.getStatus());

            } else {
                softAssert.assertEquals(item.getStatus(), esStatus, "ES Status: " + item.getStatus());
                softAssert.assertEquals(item.getRequestId(), esAccTransactionBody.get("hits.hits[0]._source.transactionId"), "ES RequestedID: " + item.getStatus());
            }
        });
        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().filter(item -> item.getProviderInfo() == null).forEach(item -> {
            JsonPath esAccFeedbackBody = RestUtils.elasticSearchNoSqlPost("lfc_user_feedback", esFeedbackQuery.replace("${transactionID}", item.getRequestId())).jsonPath();
            AccGetPojo.Items.FeedbackInfo feedbackInfo = item.getFeedbackInfo();
            if (esAccFeedbackBody.getList("hits.hits").size() == 0) {
                softAssert.assertEquals(feedbackInfo.getAvailable(), false, "ES Available: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), "", "ES Feedback ID: " + feedbackInfo.getFeedbackId());

            } else {
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), esAccFeedbackBody.get("hits.hits[0]._source.feedbackId"), "ES Feedback ID: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getThumbs(), esAccFeedbackBody.get("hits.hits[0]._source.thumbs"), "ES Status: " + feedbackInfo.getFeedbackId());
            }
        });

        softAssert.assertAll("Asserting with the ES response");


    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "getAllAccWithOptionalParams")
    public void verifyAccGetRequestWithOptionalParams(String endPoint, String headers, String params, String index, String esQuery, String esAccTransactionQuery, String esFeedbackQuery, String user, String expSolution, String expUseCase, String expPitStop) {

        Response accResponse = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(accResponse.getStatusCode(), 200, "Status Code: ");
        AccGetPojo responseBody = accResponse.as(AccGetPojo.class, ObjectMapperType.JACKSON_2);
        //accResponse.then().body(matchesJsonSchema(ACC_GETALL_SCHEMA)).log().ifValidationFails();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getSolution(), expSolution, "Soultion : ");
        softAssert.assertEquals(responseBody.getUsecase(), expUseCase, "Usecase:");
        softAssert.assertEquals(responseBody.getPitstop(), expPitStop, "Pitstop : ");
        //softAssert.assertTrue(responseBody.getItems().size() > 0, "Item Array size  : ");

        softAssert.assertAll("Asserting the response");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
        Assert.assertEquals(esBody.getInt("hits.total.value"), responseBody.getItems().size(), "ES Number of Rows: ");

        List<String> esTitles = esBody.getList("hits.hits._source.title", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(esTitles.contains(item.getTitle()), "ES_Title" + item.getTitle()));

        List<String> esDescriptions = esBody.getList("hits.hits._source.description", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(esDescriptions.contains(item.getDescription()), "ES Description " + item.getDescription()));

        List<String> accIds = esBody.getList("hits.hits._source.accId", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(accIds.contains(item.getAccId()), "Acc IDs : " + item.getAccId()));

        List<String> datasheetURLs = esBody.getList("hits.hits._source.datasheetURL", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(datasheetURLs.contains(item.getDatasheetURL()), "Data Sheet URLs : " + item.getDatasheetURL()));


        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().forEach(item -> {
            JsonPath esAccTransactionBody = RestUtils.elasticSearchNoSqlPost("lfc_acc_user_transactions", esAccTransactionQuery.replace("${accId}", item.getAccId())).jsonPath();
            String esStatus = esAccTransactionBody.get("hits.hits[0]._source.status");
            if (esStatus == null) {
                softAssert.assertEquals(item.getStatus(), "recommended", "ES Status: " + item.getStatus());
                softAssert.assertEquals(item.getRequestId(), "", "ES RequestedID: " + item.getStatus());

            } else {
                softAssert.assertEquals(item.getStatus(), esStatus, "ES Status: " + item.getStatus());
                softAssert.assertEquals(item.getRequestId(), esAccTransactionBody.get("hits.hits[0]._source.transactionId"), "ES RequestedID: " + item.getStatus());
            }
        });
        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().forEach(item -> {
            JsonPath esAccFeedbackBody = RestUtils.elasticSearchNoSqlPost("lfc_user_feedback", esFeedbackQuery.replace("${transactionID}", item.getRequestId())).jsonPath();
            AccGetPojo.Items.FeedbackInfo feedbackInfo = item.getFeedbackInfo();
            if (esAccFeedbackBody.getList("hits.hits").size() == 0) {
                softAssert.assertEquals(feedbackInfo.getAvailable(), false, "ES Available: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), "", "ES Feedback ID: " + feedbackInfo.getFeedbackId());

            } else {
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), esAccFeedbackBody.get("hits.hits[0]._source.feedbackId"), "ES Feedback ID: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getThumbs(), esAccFeedbackBody.get("hits.hits[0]._source.thumbs"), "ES Status: " + feedbackInfo.getFeedbackId());
            }
        });

        softAssert.assertAll("Asserting with the ES response");


    }

    @Severity(SeverityLevel.MINOR)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "getACCByStatusFilter")
    public void verifyFilteringACCByStatus(String endPoint, String headers, String params, String esAccTransactionQuery, String esFeedbackQuery, String status, String user, String expSolution, String expUseCase, String expPitStop) {

        Response accResponse = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(accResponse.getStatusCode(), 200, "Status Code: ");
        AccGetPojo responseBody = accResponse.as(AccGetPojo.class, ObjectMapperType.JACKSON_2);
        //accResponse.then().body(matchesJsonSchema(ACC_GETALL_SCHEMA)).log().ifValidationFails();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getSolution(), expSolution, "Soultion : ");
        softAssert.assertEquals(responseBody.getUsecase(), expUseCase, "Usecase:");
        softAssert.assertEquals(responseBody.getPitstop(), expPitStop, "Pitstop : ");
        //softAssert.assertTrue(responseBody.getItems().size() > 0, "Item Array size  : ");

        softAssert.assertAll("Asserting the response");

        JsonPath esBody = RestUtils.elasticSearchNoSqlPost("lfc_acc_user_transactions", esAccTransactionQuery).jsonPath();
        //int esBodySize = esBody.getList("hits.hits._source.accId", String.class).stream().collect(Collectors.toSet()).size();
        //Assert.assertEquals(responseBody.getItems().size(), esBodySize, "ES Number of Rows: ");

        List<String> esTitles = esBody.getList("hits.hits._source.accTitle", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(esTitles.contains(item.getTitle()), "ES_Title" + item.getTitle()));

        List<String> accIds = esBody.getList("hits.hits._source.accId", String.class);
        responseBody.getItems().stream().forEach(item -> softAssert.assertTrue(accIds.contains(item.getAccId()), "Acc IDs : " + item.getAccId()));

        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().forEach(item -> {
            int statusSize = Arrays.stream(status.split(",")).filter(status1 -> status1.equals(item.getStatus())).collect(Collectors.toList()).size();
            softAssert.assertEquals(statusSize, 1, "ES Status: " + item.getStatus());
        });
        softAssert.assertAll("Asserting with the ES response");

        responseBody.getItems().stream().forEach(item -> {
            JsonPath esAccFeedbackBody = RestUtils.elasticSearchNoSqlPost("lfc_user_feedback", esFeedbackQuery.replace("${transactionID}", item.getRequestId())).jsonPath();
            AccGetPojo.Items.FeedbackInfo feedbackInfo = item.getFeedbackInfo();
            if (esAccFeedbackBody.getList("hits.hits").size() == 0) {
                softAssert.assertEquals(feedbackInfo.getAvailable(), false, "ES Available: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), "", "ES Feedback ID: " + feedbackInfo.getFeedbackId());

            } else {
                softAssert.assertEquals(feedbackInfo.getFeedbackId(), esAccFeedbackBody.get("hits.hits[0]._source.feedbackId"), "ES Feedback ID: " + feedbackInfo.getAvailable());
                softAssert.assertEquals(feedbackInfo.getThumbs(), esAccFeedbackBody.get("hits.hits[0]._source.thumbs"), "ES Status: " + feedbackInfo.getFeedbackId());
            }
        });

        softAssert.assertAll("Asserting with the ES response");


    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "getACCByProviderFilter")
    public void verifyFilteringACCByProvider(String endPoint, String headers, String params, String esAccTransactionQuery, String esFeedbackQuery, String expProvider, String user, String expSolution, String expUseCase, String expPitStop) {

        Response accResponse = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(accResponse.getStatusCode(), 200, "Status Code: ");
        AccGetPojo responseBody = accResponse.as(AccGetPojo.class, ObjectMapperType.JACKSON_2);
        //accResponse.then().body(matchesJsonSchema(ACC_GETALL_SCHEMA)).log().ifValidationFails();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getSolution(), expSolution, "Soultion : ");
        softAssert.assertEquals(responseBody.getUsecase(), expUseCase, "Usecase:");
        softAssert.assertEquals(responseBody.getPitstop(), expPitStop, "Pitstop : ");

        if (expProvider.equals("cisco")) {
            List<Object> providerInfo = responseBody.getItems().stream().map(item -> item.getProviderInfo()).collect(Collectors.toList());
            providerInfo.forEach(provider -> softAssert.assertNull(provider, "Provider Info: " + provider));
        } else {
            List<String> providerInfo = responseBody.getItems().stream().map(item -> item.getProviderInfo().getId()).collect(Collectors.toList());
            providerInfo.forEach(provider -> softAssert.assertEquals(provider, expProvider, "Provider Info: " + provider));
        }

        softAssert.assertAll("Asserting with the  response");


    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProviderClass = LifeCycleData.class, dataProvider = "getACCEntitlment")
    public void verifyACCEntitlement(String endPoint, String headers, String params, String expCXLevel, String user, String expSolution, String expUseCase, String expPitStop) {

        Response accResponse = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(accResponse.getStatusCode(), 200, "Status Code: ");
        AccGetPojo responseBody = accResponse.as(AccGetPojo.class, ObjectMapperType.JACKSON_2);
        //accResponse.then().body(matchesJsonSchema(ACC_GETALL_SCHEMA)).log().ifValidationFails();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getSolution(), expSolution, "Soultion : ");
        softAssert.assertEquals(responseBody.getUsecase(), expUseCase, "Usecase:");
        softAssert.assertEquals(responseBody.getPitstop(), expPitStop, "Pitstop : ");

        if (expCXLevel.equals("1")) {
            softAssert.assertEquals(responseBody.getItems().size(), 0, "Itemsarray size");
        } else {
            softAssert.assertTrue(responseBody.getItems().size() >= 0, "Items array size");
        }

        softAssert.assertAll("Asserting with the  response");

    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test()
    public void verifyAccGetWithInvalidParams() {
        Response accResponse = RestUtils.get(ACC_GET_INVALID_PARAMS.get("endPoint"), ACC_GET_INVALID_PARAMS.get("headers"), ACC_GET_INVALID_PARAMS.get("params"), new User(ACC_GET_INVALID_PARAMS.get("user")));
        Assert.assertEquals(accResponse.getStatusCode(), 400, "Status Code: ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test()
    public void verifyAccGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake(new User(ACC_GET_INVALID_PARAMS.get("user")))).contentType(ContentType.JSON).config(RestAssured.config());
        Response accResponse = RestUtils.get(ACC_GET_INVALID_PARAMS.get("endPoint"), spec);
        Assert.assertEquals(accResponse.getStatusCode(), 403, "Status Code: ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test()
    public void verifyBookMarkAPIWithMandatoryParams() throws InterruptedException {

        RequestSpecification spec = RestUtils.getSpec(new User(ACC_BOOKMARK.get("user")));
        spec.body(ACC_BOOKMARK.get("body"));
        spec.headers(constructHeader(ACC_BOOKMARK.get("headers")));
        Response response = RestUtils.post(ACC_BOOKMARK.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        response.then().body(matchesJsonSchema(BOOKMARK_SCHEMA)).log().ifValidationFails();

        JsonPath responseBody = response.getBody().jsonPath();

        //Validating response with the expected value from excel sheet
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getBoolean("bookmark"), Boolean.parseBoolean(ACC_BOOKMARK.get("bookmark_expVal")), "Bookmark : ");
        softAssert.assertEquals(responseBody.get("id"), ACC_BOOKMARK.get("accId_expVal"), "ID : ");
        softAssert.assertEquals(responseBody.get("lifecycleCategory"), ACC_BOOKMARK.get("lifecycleCategory_expVal"), "Lifecycle Category : ");
        softAssert.assertEquals(responseBody.get("pitstop"), ACC_BOOKMARK.get("pitstop_expVal"), "Pit Stop : ");
        softAssert.assertEquals(responseBody.get("usecase"), ACC_BOOKMARK.get("usecase_expVal"), "UseCase : ");
        softAssert.assertEquals(responseBody.get("solution"), ACC_BOOKMARK.get("solution_expVal"), "UseCase : ");
        softAssert.assertEquals(responseBody.get("ccoId"), ACC_BOOKMARK.get("ccoid_expVal"), "ccoId : ");
        softAssert.assertEquals(responseBody.get("customerId"), ACC_BOOKMARK.get("customerId_exp"), "customerId : ");

        //Es Validation
        Thread.sleep(2000);
        JsonPath esBody = RestUtils.elasticSearchNoSqlPost(ACC_BOOKMARK.get("Table"), ACC_BOOKMARK.get("ES_Query").replace("${RequestId}", responseBody.get("bookmarkRequestId"))).jsonPath();
        Assert.assertEquals(esBody.getInt("hits.total.value"), 1, "Number of Rows: ");

        JsonPath esBodyFirstRow = esBody.setRoot("hits.hits[0]._source");
        softAssert.assertEquals(esBodyFirstRow.getString("id"), responseBody.get("id"), "ES_ID : ");
        softAssert.assertEquals(esBodyFirstRow.getBoolean("bookmark"), responseBody.getBoolean("bookmark"), "ES_Bookmark : ");
        softAssert.assertEquals(esBodyFirstRow.getString("pitstop"), responseBody.get("pitstop"), "ES_pitstop");
        softAssert.assertEquals(esBodyFirstRow.getString("solution"), responseBody.get("solution"), "ES_solution");
        softAssert.assertEquals(esBodyFirstRow.getString("usecase"), responseBody.get("usecase"), "ES_usecase");
        softAssert.assertEquals(esBodyFirstRow.getString("bookmarkRequestId"), responseBody.get("bookmarkRequestId"), "ES_bookmarkRequestId");
        softAssert.assertEquals(esBodyFirstRow.getLong("created"), responseBody.getLong("created"), "ES_created");
        softAssert.assertEquals(esBodyFirstRow.getLong("updated"), responseBody.getLong("updated"), "ES_updated");
        softAssert.assertEquals(esBodyFirstRow.getString("ccoId"), responseBody.get("ccoId"), "ES_ccoId");
        softAssert.assertEquals(esBodyFirstRow.getString("customerId"), responseBody.get("customerId"), "ES_customerId");
        softAssert.assertEquals(esBodyFirstRow.getString("lifecycleCategory"), responseBody.get("lifecycleCategory"), "ES_lifecycleCategory");

        softAssert.assertAll("Asserting the Response");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyBookMarkAPIWithInvalidParams() {
        RequestSpecification spec = RestUtils.getSpec(new User(ACC_BOOKMARK_INVALID_PARAMS.get("user")));
        spec.body(ACC_BOOKMARK_INVALID_PARAMS.get("body"));
        spec.headers(constructHeader(ACC_BOOKMARK_INVALID_PARAMS.get("headers")));
        Response response = RestUtils.post(ACC_BOOKMARK_INVALID_PARAMS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyBookMarkAPIWithInvalidHeader() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Headers headers = new Headers(new Header("Authorization", "Bearer ldslfjdldsjfldsf"));
        Response response = RestUtils.post(ACC_BOOKMARK.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code:");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyListOfNotifications() {
        Response notificationsList = RestUtils.get(ACC_NOTIFICATION.get("endPoint"), ACC_NOTIFICATION.get("headers"), ACC_NOTIFICATION.get("params"), new User(ACC_NOTIFICATION.get("user")));
        Assert.assertEquals(notificationsList.getStatusCode(), 200, "Status: ");

        JsonPath responseBody = notificationsList.getBody().jsonPath();
        JsonPath esBody = RestUtils.elasticSearchNoSqlPost("lfc_user_notifications", ACC_NOTIFICATION.get("ES_Query")).jsonPath();
        Assert.assertEquals(esBody.getInt("hits.total.value"), responseBody.getInt("count"), "Number of Rows: ");

        responseBody.getList("items.context.assetType", String.class).forEach(assetType -> Assert.assertEquals(assetType, "ACC", "Asset Type"));

    }

}
