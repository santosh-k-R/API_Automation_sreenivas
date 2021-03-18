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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Feature("Success Tips")
public class SuccessTipsIT {

    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "successTipsGet", dataProviderClass = LifeCycleData.class)
    public void verifyGettingSuccessTipsData(String endPoint, String params, String headers, String user, String esQuery, String expSolution, String expUsecase, String expPitstop) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
        response.then().body(matchesJsonSchema(SUCCESS_TIP_SCHEMA)).log().ifValidationFails();


        JsonPath successTipsResponseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successTipsResponseBody.get("solution"), expSolution, "Solution:");
        softAssert.assertEquals(successTipsResponseBody.get("usecase"), expUsecase, "Usecase:");
        softAssert.assertEquals(successTipsResponseBody.get("pitstop"), expPitstop, "Pitstop: ");

        successTipsResponseBody.getList("items.type", String.class).forEach(type -> softAssert.assertTrue("Video".equals(type) || "Web Page".equals(type)));

        softAssert.assertAll("Asserting the Response");

        JsonPath esResponse = RestUtils.elasticSearchNoSqlPost("lfc_success_paths_alias", esQuery).jsonPath();
        softAssert.assertEquals(successTipsResponseBody.getInt("totalCount"), esResponse.getInt("hits.total.value"));
        JsonPath esBody = esResponse.setRoot("hits.hits._source");

        List<String> esSuccessByteId = esBody.getList("successByteId", String.class).stream().sorted().collect(Collectors.toList());
        List<String> successByteIds = successTipsResponseBody.getList("items.successByteId", String.class).stream().sorted().collect(Collectors.toList());

        softAssert.assertEquals(esSuccessByteId, successByteIds, "Success Byte Ids: " + successByteIds);

        List<Map<String, ?>> successTipsResponse = successTipsResponseBody.get("items");
        successTipsResponse.forEach(item -> {
            Map<String, ?> esItem = esBody.getMap("find {it.successByteId == '" + item.get("successByteId") + "'}");
            softAssert.assertEquals(item.get("title"), esItem.get("title"), "Title :" + item.get("title"));
            softAssert.assertEquals(item.get("description"), esItem.get("description"), "Description :" + item.get("description"));
            softAssert.assertEquals(item.get("duration"), esItem.get("duration"), "Duration :" + item.get("duration"));
            softAssert.assertEquals(item.get("type"), esItem.get("type"), "Type :" + item.get("type"));
            softAssert.assertEquals(item.get("url"), esItem.get("url"), "URL :" + item.get("url"));
            softAssert.assertEquals(item.get("archetype"), esItem.get("archetype"), "archetype :" + item.get("archetype"));

        });

        softAssert.assertAll("Asserting the Response with Elastic Search");

    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test()
    public void verifySuccesTipsGetWithInvalidParams() {
        Response response = RestUtils.get(SUCCESS_TIPS_INVALID_PARAMS.get("endPoint"), SUCCESS_TIPS_INVALID_PARAMS.get("headers"), SUCCESS_TIPS_INVALID_PARAMS.get("params"), new User(SUCCESS_TIPS_INVALID_PARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifySuccessTipsGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.get(SUCCESS_TIPS_INVALID_HEADERS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");

    }


}
