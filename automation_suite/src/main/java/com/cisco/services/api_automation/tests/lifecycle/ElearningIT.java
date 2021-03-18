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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.*;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;

@Feature("E_Learning")
public class ElearningIT {

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProvider = "eLearningGet", dataProviderClass = LifeCycleData.class)
    public void verifyGettingElearningData(String endPoint, String params, String headers, String user, String esQuery, String expSolution, String expUsecase, String expPitstop, String expLevel) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        JsonPath eLearningResponseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(eLearningResponseBody.get("solution"), expSolution, "Solution:");
        softAssert.assertEquals(eLearningResponseBody.get("usecase"), expUsecase, "Usecase:");
        softAssert.assertEquals(eLearningResponseBody.get("pitstop"), expPitstop, "Pitstop: ");
        List<String> expLevels = Arrays.stream(expLevel.split(",")).collect(Collectors.toList());
        eLearningResponseBody.getList("items.cxlevel", String.class).forEach(level -> softAssert.assertTrue(expLevels.contains(level)));
        eLearningResponseBody.getList("items.type", String.class).forEach(type -> softAssert.assertTrue("E-Learning".equals(type) || "Certification".equals(type) || "Remote Learning Lab".equals(type)));

        softAssert.assertAll("Asserting the Response");

        JsonPath esResponse = RestUtils.elasticSearchNoSqlPost("lfc_elearningcatalog", esQuery).jsonPath();
        softAssert.assertEquals(eLearningResponseBody.getList("items.cxlevel").size(), esResponse.getInt("hits.total.value"));
        JsonPath esBody = esResponse.setRoot("hits.hits._source");

        List<String> esCourseIds = esBody.getList("courseId", String.class).stream().sorted().collect(Collectors.toList());
        List<String> courseIds = eLearningResponseBody.getList("items.courseId", String.class).stream().sorted().collect(Collectors.toList());

        softAssert.assertEquals(esCourseIds, courseIds, "Course Ids: " + courseIds);

        List<Map<String, ?>> eLearningResponseItems = eLearningResponseBody.get("items");
        eLearningResponseItems.forEach(item -> {
            Map<String, ?> esItem = esBody.getMap("find {it.courseId == '" + item.get("courseId") + "'}");
            softAssert.assertEquals(item.get("cxlevel"), Integer.parseInt((String) esItem.get("level")), "CxLevel :" + item.get("cxlevel"));
            softAssert.assertEquals(item.get("title"), esItem.get("title"), "Title :" + item.get("title"));
            softAssert.assertEquals(item.get("description"), esItem.get("description"), "Description :" + item.get("description"));
            softAssert.assertEquals(item.get("url"), esItem.get("url"), "URL :" + item.get("url"));
            softAssert.assertEquals(item.get("type"), esItem.get("type"), "Type :" + item.get("type"));
            softAssert.assertEquals(item.get("launchUrl"), esItem.get("launch_url"), "Launch URL :" + item.get("launchUrl"));
            softAssert.assertEquals(item.get("ranking"), esItem.get("order"), "Ranking :" + item.get("ranking"));

        });

        softAssert.assertAll("Asserting the Response with Elastic Search");

    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void verifyElearningGetWithInvalidParams() {
        Response response = RestUtils.get(ELEARNING_INVALIDPARAMS.get("endPoint"), ELEARNING_INVALIDPARAMS.get("headers"), ELEARNING_INVALIDPARAMS.get("params"), new User(ELEARNING_INVALIDPARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 400, "Status Code : ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyElearningGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.get(ELEARNING_INVALIDHEADERS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code : ");

    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyRemoteLabLaunchAPI() throws InterruptedException {
        long expUpdatedTime = System.currentTimeMillis();
        RequestSpecification spec = RestUtils.getSpec(new User(REMOTE_LAB_LAUNCHED.get("user")));
        spec.header("cx-context",REMOTE_LAB_LAUNCHED.get("headers"));
        spec.body(REMOTE_LAB_LAUNCHED.get("body"));
        Response response = RestUtils.post(REMOTE_LAB_LAUNCHED.get("endPoint"),spec);
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");

        JsonPath responseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getBoolean("launched"), true, "launched ");
        softAssert.assertEquals(responseBody.get("usecase"), REMOTE_LAB_LAUNCHED.get("exp_usecase"), "usecase ");
        softAssert.assertEquals(responseBody.get("pitstop"), REMOTE_LAB_LAUNCHED.get("exp_pitstop"), "pitstop ");
        softAssert.assertEquals(responseBody.get("solution"), REMOTE_LAB_LAUNCHED.get("exp_solution"), "solution ");
        softAssert.assertEquals(responseBody.get("ccoId"), REMOTE_LAB_LAUNCHED.get("exp_ccoId"), "ccoId ");
        softAssert.assertTrue(responseBody.getLong("updated") > expUpdatedTime, "updated ");
        softAssert.assertNotNull(responseBody.get("elearningLabsRequestId"), "elearningLabsRequestId ");

        softAssert.assertAll("Asserting the response with the expected value");

        Thread.sleep(3000);
        Response esResponse = RestUtils.elasticSearchNoSqlPost("lfc_elearning_labs_launch", REMOTE_LAB_LAUNCHED.get("esQuery")
                .replace("${RequestID}", responseBody.get("elearningLabsRequestId")));

        JsonPath esBody = esResponse.getBody().jsonPath().setRoot("hits.hits[0]._source");

        softAssert.assertEquals(responseBody.getBoolean("launched"), esBody.getBoolean("launched"), "es_launched ");
        softAssert.assertEquals(responseBody.getString("id"), esBody.get("id"), "es_ID ");
        softAssert.assertEquals(responseBody.getString("usecase"), esBody.get("usecase"), "es_usecase ");
        softAssert.assertEquals(responseBody.getString("pitstop"), esBody.get("pitstop"), "es_pitstop ");
        softAssert.assertEquals(responseBody.getString("solution"), esBody.get("solution"), "es_solution ");
        softAssert.assertEquals(responseBody.getString("ccoId"), esBody.get("ccoId"), "es_ccoId ");
        softAssert.assertEquals(responseBody.getString("elearningLabsRequestId"), esBody.get("elearningLabsRequestId"), "es_elearningLabsRequestId ");
        softAssert.assertEquals(responseBody.getLong("updated"), esBody.getLong("updated"), "es_updated ");

        softAssert.assertAll("Asserting the response with the Elastic Search");


    }


}
