package com.cisco.services.api_automation.utils.auth;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.http.Header;

import org.springframework.http.HttpHeaders;
import java.util.Base64;

import static com.cisco.services.api_automation.utils.Commons.encode;
import static io.restassured.RestAssured.given;

public class AuthUtils {

    private static String authURL = System.getenv("authURL");
    private static String client_id = System.getenv("clientId");
    private static String api_client_secret = System.getenv("apiClientSecret");
    private static String api_party_id = System.getenv("niagara_partyid");
    private static String userName = System.getenv("niagara_username");
    private static String XMashseryHandshake;
    private static Header bearerToken;
    private static long tokenCreatedTime;
    private static Header esToken;

    private static String rbacAuthURL = System.getenv("rbac_token_url");;
    private static String rbacClientId = System.getenv("rbac_client_id");
    private static String rbacClientSecret = System.getenv("rbac_client_secret");
    private static String rbacScope = System.getenv("rbac_scope");
    private static String rbacGrantType = System.getenv("rbac_grant_type");


    @Step("Generate Bearer Token")
    public static Header getBearerToken() {
        if (isTokenExpired()) {
            bearerToken = new Header("Authorization", "Bearer " +
                    given()
                            .param("grant_type", "client_credentials").param("client_id", client_id).param("client_secret", api_client_secret)
                            .when()
                            .post(authURL)
                            .then()
                            .statusCode(200).extract().path("access_token"));
            tokenCreatedTime = System.currentTimeMillis();
            System.out.println("Bearer Token Created at ::: " + tokenCreatedTime);

        } else {
            System.out.println("Reusing the existing Bearer token");
        }
        return bearerToken;

    }

    @Step("Generate RBAC Bearer Token")
    public static Header getRBACBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Accept", "application/json");
        headers.add("Cache-Control", "no-cache");

        if (isTokenExpired()) {
            bearerToken = new Header("Authorization", "Bearer " +
                    given()
                            .param("grant_type", rbacGrantType).param("client_id", rbacClientId).param("client_secret", rbacClientSecret)
                            .param("scope", rbacScope).headers(headers)
                            .when()
                            .post(rbacAuthURL)
                            .then()
                            .statusCode(200).extract().path("access_token"));
            tokenCreatedTime = System.currentTimeMillis();
            System.out.println("Bearer Token Created at ::: " + tokenCreatedTime);

        } else {
            System.out.println("Reusing the existing Bearer token");
        }
        return bearerToken;
    }

    @Step("Getting xmasheryhandshake")
    public static Header getXMasheryHandshake() {
        if (XMashseryHandshake == null) {
            System.out.println("Generating the X-Mashery-Handshake");
            JsonObject body = new JsonObject();
            body.addProperty("client_cco_id", userName);
            body.addProperty("party_id", api_party_id);
            body.addProperty("access_token_uid", userName);
            body.addProperty("access_token_accesslevel", "4");
            XMashseryHandshake = Base64.getEncoder().encodeToString(body.toString().getBytes());
        }
        return new Header("X-Mashery-Handshake", XMashseryHandshake);
    }

    @Step("Getting xmasheryhandshake for user {0}")
    public static Header getXMasheryHandshake(User user) {
        String partyId = user.getPartyId() == null ? api_party_id : user.getPartyId();
        String accessLevel = user.getAccessLevel() == null ? "4" : user.getAccessLevel();
        JsonObject body = new JsonObject();
        body.addProperty("client_cco_id", user.getUserName());
        body.addProperty("party_id", partyId);
        body.addProperty("access_token_uid", user.getUserName());
        body.addProperty("access_token_accesslevel", accessLevel);
        return new Header("X-Mashery-Handshake", Base64.getEncoder().encodeToString(body.toString().getBytes()));
    }

    @Step("Generate ES Basic Authorization Token")
    public static Header getESToken() {
        if (esToken == null) {
            System.out.println("Generating ES token");
            String es_username = System.getenv("es_username");
            String es_password = System.getenv("es_password");
            esToken = new Header("Authorization", "Basic " + encode(es_username + ":" + es_password));
        } else {
            System.out.println("Reusing the existing ES token");
        }
        return esToken;
    }

    private static boolean isTokenExpired() {
        if (bearerToken == null) return true;
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - tokenCreatedTime > 3000000;
    }

}
