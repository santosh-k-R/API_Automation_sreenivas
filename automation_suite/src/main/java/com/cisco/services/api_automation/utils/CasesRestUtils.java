package com.cisco.services.api_automation.utils;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CasesRestUtils {

    private static final String USERNAME = System.getenv("niagara_username");
    private static String bearerToken = null;
    private static long tokenCreatedTime;

    public static Map<String, String> constructCaseParams(String params) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramArray = params.split("&");

        for (String param : paramArray)
            parameters.put(param.split("=")[0], param.split("=")[1]);
        return parameters;
    }

    /**
     * <p> Post Api method to get bearer token</p>
     *
     * @return bearer token for getRepsonseBody()
     */
    @Step()
    public static Header getCaseBearerToken() {
        String authURL = "https://cloudsso-test.cisco.com/as/token.oauth2";

        if (isTokenExpired()) {
            tokenCreatedTime = System.currentTimeMillis();
            System.out.println("Case api Bearer Token Created at ::: " + tokenCreatedTime);
            bearerToken = given()
                    .param("grant_type", "client_credentials")
                    .param("client_id", System.getenv("case_client_id"))
                    .param("client_secret", System.getenv("case_api_client_secret"))
                    .when()
                    .post(authURL)
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("access_token");
        }
        return new Header("Authorization", "Bearer " + bearerToken);
    }

    /**
     * @param params
     * @param extractValue
     * @return Object
     * @author ankumalv
     */
    @Step("API Response")
    public static Object getCaseListData(String userRole, String params, String extractValue) {
        String baseURI = "https://apx-test.cisco.com/custcare/cm/v1.0-stage4/cases";

        return given()
                .header(getCaseBearerToken())
                .param("loggedId", System.getenv("niagara_" + userRole + "_username"))
                .when()
                .get(baseURI + "?" + params)
                .then()
                .statusCode(200)
                .extract()
                .path(extractValue);
    }

    public static Response getCaseData(String userRole, String caseNumber) {
        String baseURL = "https://apx-test.cisco.com/custcare/cm/v1.0-stage4/cases/case/" + caseNumber;

        return given()
                .header(getCaseBearerToken())
                .param("loggedId", System.getenv("niagara_" + userRole + "_username"))
                .when()
                .get(baseURL)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private static boolean isTokenExpired() {
        if (bearerToken == null) return true;
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - tokenCreatedTime > 600000;
    }
}
