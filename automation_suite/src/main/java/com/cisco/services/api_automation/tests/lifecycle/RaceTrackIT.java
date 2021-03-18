package com.cisco.services.api_automation.tests.lifecycle;

import com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Allure;
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

@Feature("Race Track")
public class RaceTrackIT {

    @Severity(SeverityLevel.BLOCKER)
    @Test(dataProvider = "raceTrackGet", dataProviderClass = LifeCycleData.class)
    public void verifyGettingRaceTrackData(String endPoint, String params, String user, String headers, String esQuery, String expSolution, String expUseCase, String expPartId, String expBuid) {
        Response response = RestUtils.get(endPoint, headers, params, new User(user));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
//        response.then().body(matchesJsonSchema(SUCCESS_TIP_SCHEMA)).log().ifValidationFails();
        JsonPath raceTrackResponseBody = response.getBody().jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(raceTrackResponseBody.get("solutions.items[0].name"), expSolution, "Solution:");
        softAssert.assertEquals(raceTrackResponseBody.get("partyId"), expPartId, "PartyID:");
        softAssert.assertEquals(raceTrackResponseBody.get("buId"), expBuid, "BUID:");

        List<String> expUseCases = Arrays.stream(expUseCase.split(",")).sorted().collect(Collectors.toList());
        List<String> useCases = raceTrackResponseBody.getList("solutions.items.technologies.name.flatten()", String.class).stream().sorted().collect(Collectors.toList());
        softAssert.assertEquals(useCases, expUseCases, "Use Cases : " + useCases);

        softAssert.assertAll("Asserting the Response");

        JsonPath esResponse = RestUtils.elasticSearchNoSqlPost("lfc_racetrack", esQuery).jsonPath();
        softAssert.assertEquals(raceTrackResponseBody.getInt("solutions.items.size()"), esResponse.getInt("hits.total.value"));
        JsonPath esBody = esResponse.setRoot("hits.hits._source.solutions.items.technologies.flatten()");

        List<Map<String, ?>> raceTrackResponse = raceTrackResponseBody.get("solutions.items.technologies.flatten()");
        raceTrackResponse.forEach(item -> {
            Allure.step("Use Case Name:" + item.get("name"));
            Map<String, ?> esItem = esBody.getMap("find {it.name == '" + item.get("name") + "'}");
            softAssert.assertEquals(((Integer) item.get("adoptionPercentage")).floatValue(), esItem.get("adoptionPercentage"), "Adoption Percentage :" + item.get("usecase_adoption_percentage"));
            softAssert.assertEquals(item.get("useCaseId"), esItem.get("useCaseId").toString(), "Use Case ID :" + esItem.get("useCaseId"));

            ((List<Map<String, ?>>) item.get("pitstops")).forEach(pitstop -> {
                Map<String, ?> esPitstop = ((List<Map<String, ?>>) esItem.get("pitstops")).stream().filter(p -> p.get("name").equals(pitstop.get("name"))).collect(Collectors.toList()).get(0);
                Allure.step("Pitstop Name:" + pitstop.get("name"));
                softAssert.assertEquals(pitstop.get("isPitstopComplete"), esPitstop.get("isPitstopComplete"), "Pitstop Complete :" + esPitstop.get("isPitstopComplete"));
                softAssert.assertEquals(pitstop.get("description"), esPitstop.get("description"), "Pitstop Description :" + esPitstop.get("description"));
                if (!pitstop.get("name").equals("Onboard")) {
                    softAssert.assertEquals(pitstop.get("completionPercentage"), ((Float) esPitstop.get("completionPercentage")).intValue(), "Pitstop complettion :" + esPitstop.get("completionPercentage"));
                }
                ((List<Map<String, ?>>) pitstop.get("pitstopActions")).forEach(pitstopAction -> {
                    Map<String, ?> esPitstopAction = ((List<Map<String, ?>>) esPitstop.get("pitstopActions")).stream().filter(p -> p.get("name").equals(pitstopAction.get("name"))).collect(Collectors.toList()).get(0);
                    Allure.step("PitstopAction Name:" + pitstopAction.get("name"));
                    softAssert.assertEquals(pitstopAction.get("description"), esPitstopAction.get("description"), "PitstopAction Description :" + esPitstopAction.get("description"));
                    softAssert.assertEquals(pitstopAction.get("isCompleteManual"), esPitstopAction.get("isCompleteManual"), "Is complete Manual Description :" + esPitstopAction.get("isCompleteManual"));
                    softAssert.assertEquals(pitstopAction.get("isManualCheckAllowed"), esPitstopAction.get("isManualCheckAllowed"), "Is Manual Check Allowed :" + esPitstopAction.get("isManualCheckAllowed"));
                    softAssert.assertEquals(pitstopAction.get("isComplete"), esPitstopAction.get("isComplete"), "Is complete :" + esPitstopAction.get("isComplete"));
                    softAssert.assertEquals(pitstopAction.get("isManualOverride"), esPitstopAction.get("isManualOverride"), "Is manual Override :" + esPitstopAction.get("description"));
                });
            });
        });

        softAssert.assertAll("Asserting the Response with Elastic Search");

    }

    @Severity(SeverityLevel.TRIVIAL)
    @Test()
    public void verifyRaceTrackGetWithInvalidParams() {
        Response response = RestUtils.get(RACETRACK_INVALID_PARAMS.get("endPoint"), RACETRACK_INVALID_PARAMS.get("headers"), RACETRACK_INVALID_PARAMS.get("params"), new User(RACETRACK_INVALID_PARAMS.get("user")));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code: ");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyRaceTrackGetWithInvalidHeaders() {
        RequestSpecification spec = given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(new Header("Authorization", "Bearer ldslfjdldsjfldsf"))
                .header(getXMasheryHandshake()).contentType(ContentType.JSON).config(RestAssured.config());
        Response response = RestUtils.get(RACETRACK_INVALID_HEADERS.get("endPoint"), spec);
        Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyUpdatingPitstopActions() {
        Response response = RestUtils.put(RACETRACK_UPDATE_PITSTOP_ACTION.get("endPoint"), RACETRACK_UPDATE_PITSTOP_ACTION.get("body"), RACETRACK_UPDATE_PITSTOP_ACTION.get("headers"), RACETRACK_UPDATE_PITSTOP_ACTION.get("params"), new User(RACETRACK_UPDATE_PITSTOP_ACTION.get("user")));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code :");
    }


}
