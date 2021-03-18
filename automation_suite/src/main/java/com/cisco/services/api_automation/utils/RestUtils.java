package com.cisco.services.api_automation.utils;

import com.cisco.services.api_automation.utils.auth.TokenGenerator;
import com.cisco.services.api_automation.utils.auth.User;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cisco.services.api_automation.utils.Commons.constructHeader;
import static com.cisco.services.api_automation.utils.Commons.constructParams;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.*;
import static io.restassured.RestAssured.given;

public class RestUtils {

    public static final String BASE_URI = System.getenv("baseURI");
    public static final String CLUSTER_URI = System.getenv("clusterURI");
    public static final String ES_HOST_URL = System.getenv("esHostURL");
    public static final boolean ES_VALIDATION = Boolean.parseBoolean(System.getenv("esValidation"));
    public static final String userName = System.getenv("niagara_username");
    public static final String password = System.getenv("niagara_password");

    /**
     * <p>GET request </p>
     * Ex:Response res =  RestUtils.get("notifications/v1/list","param1=value1,param2=value2..");
     *
     * @param URL    appends to the base URI
     * @param params query params
     * @return returns the response of the get method
     * @author mohnasir
     */
    @Step("Get Request with the URL {0} and params {1}")
    public static Response get(String URL, String params) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .when()
                        .get(URL + "?" + params)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0}")
    public static Response get(String URL) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0}")
    public static Response getAuthorization(String URL, Headers headers) {
        return
                given()
                        .spec(getSpec(CLUSTER_URI))
                        .headers(headers)
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0} and headers {1}")
    public static Response get(String URL, Headers headers) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .headers(headers)
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0} and header {1} and params{2}")
    public static Response get(String URL, String headers, String params) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(constructHeader(headers))
                        .when()
                        .get(URL + "?" + params)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0} and params as Map {1})")
    public static Response get(String URL, Map<String, String> headers) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(headers)
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    /**
     * @author shsunder
     */
    @Step("Get Request with the URL {0} and params as Map {1})")
    public static Response get(String URL, Map<String, String> parametersMap, String headers) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(constructHeader(headers))
                        .when()
                        .queryParams(parametersMap)
                        .get(URL)
                        .then()
                        .extract().response();

    }

    /**
     * @author shsunder
     */
    @Step("Head Request with the URL {0} and params as Map {1})")
    public static Response head(String URL, Map<String, String> parametersMap, String headers) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(constructHeader(headers))
                        .when()
                        .queryParams(parametersMap)
                        .head(URL)
                        .then()
                        .extract().response();

    }

    /**
     * @author shsunder
     */
    @Step("Search elastic Index Get Method")
    public static Response elasticSearchNoSqlGet(String index) {
        System.out.println("Fetching the records from ES Index");
        return
                given()
                        .header(getESToken()).filter(new AllureRestAssured()).contentType(ContentType.JSON)
                        .when()
                        .get(ES_HOST_URL + "/" + index + "/_search?size=1000").
                        then()
                        .extract().response();

    }


    /**
     * <p>Post Method</p>
     * Ex:Response res = RestUtils.post("/racetrack/v1/bookmarks", {"test1":value}, "param1=value1,param2=value2....");
     *
     * @param URL    appends to the base URI
     * @param body   body of the post method in JSON format
     * @param params the query params if any, else pass ""(empty string)
     * @return returns the response of the post method.
     * @author mohnasir
     */
    @Step("Post Request with the URL {0} , body {1} and params {2}")
    public static Response post(String URL, String body, String params) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .when()
                        .post(URL + "?" + params).
                        then()
                        .extract().response();
    }


    @Step("Post Request with the URL {0} , body {1}")
    public static Response post(String URL, String body) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , form Params {1}")
    public static Response post(String URL, Map<String, String> formParams, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpecPost(user))
                        .formParams(formParams)
                        .when()
                        .post(URL)
                        .then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and headers{2}")
    public static Response post(String URL, String body, Headers headers) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .headers(headers)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and headers{2}")
    public static Response post(String URL, String body, Map<String, String> headers) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .headers(headers)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and params {2}")
    public static Response post(String URL, String body, String headers, String params) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(constructHeader(headers))
                        .body(body)
                        .when()
                        .post(URL + "?" + params)
                        .then()
                        .extract().response();
    }


    @Step("Get Request Specification")
    private static RequestSpecification getSpec(String URL) {
        return given().baseUri(URL).filter(new AllureRestAssured()).header(getBearerToken())
                .header(getXMasheryHandshake()).header("Content-Type", "application/json")
                .config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

    }

    @Step("Put Request with the URL {0} , body {1}")
    public static Response put(String URL, String body) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }

    @Step("Put Request with the URL {0} , body {1} and headers{2}")
    public static Response put(String URL, String body, Headers headers) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .headers(headers)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }


    @Step("Put Request with the URL {0} , body {1} and params {2}")
    public static Response put(String URL, String body, String params) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .when()
                        .put(URL + "?" + params).
                        then()
                        .extract().response();
    }


    @Step("Put Request with the URL {0} , body {1} and params {2}")
    public static Response put(String URL, String body, String headers, String params) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec()).headers(constructHeader(headers))
                        .body(body)
                        .when()
                        .put(URL + "?" + params)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0}")
    public static Response delete(String URL) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0} and headers{2}")
    public static Response delete(String URL, Headers headers) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .headers(headers)
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Get Request Specification")
    public static RequestSpecification getSpec() {
        return given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(TokenGenerator.getToken(userName, password))
                .header(getXMasheryHandshake()).header("Content-Type", "application/json")
                .config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

    }

    @Step("Get Request Specification")
    public static RequestSpecification getSpecPost(User user) {
        return given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(TokenGenerator.getToken(user))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded",
                                        ContentType.URLENC)));
    }

    @Step("Search elastic DB with the query: {0}")
    public static List<ArrayList> elasticSearchSqlPost(String query) {
        System.out.println("Fetching the records from DB");
        return
                given()
                        .header(getESToken()).filter(new AllureRestAssured()).contentType(ContentType.JSON).body(query).
                        when()
                        .post(ES_HOST_URL + "/_xpack/sql").
                        then()
                        .statusCode(200)
                        .extract().response().jsonPath().getList("rows");

    }

    @Step("Search elastic DB with the query: {0}")
    public static Response elasticSearchNoSqlPost(String table, String query) {
        System.out.println("Fetching the records from DB");
        return
                given()
                        .header(getESToken()).filter(new AllureRestAssured()).contentType(ContentType.JSON).body(query)
                        .when()
                        .post(ES_HOST_URL + "/" + table + "/_search").
                        then()
                        .statusCode(200)
                        .extract().response();

    }


    //**************************************With User***************************************************

    @Step("Get Request with the URL {0} and params {1}")
    public static Response get(String URL, String params, User user) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .when()
                        .get(URL + "?" + params)
                        .then()
                        .extract().response();

    }


    @Step("Get Request with the URL {0}")
    public static Response get(String URL, User user) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0} and header {1}")
    public static Response get(String URL, Headers headers, User user) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .headers(headers)
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();

    }

    @Step("Get Request with the URL {0} and header {1} and params{2}")
    public static Response get(String URL, String headers, String params, User user) {
        System.out.println("Running Get Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user)).headers(constructHeader(headers))
                        .when()
                        .get(URL + "?" + params)
                        .then()
                        .extract().response();

    }

    @Step("Post Request with the URL {0} , body {1} and params {2}")
    public static Response post(String URL, String body, String params, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .when()
                        .post(URL + "?" + params).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1}")
    public static Response post(String URL, String body, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and Headers {2}")
    public static Response post(String URL, String body, Headers headers, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .headers(headers)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and params {2}")
    public static Response post(String URL, String body, String headers, String params, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user)).headers(constructHeader(headers))
                        .body(body)
                        .when()
                        .post(URL + "?" + params)
                        .then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , headers {1} and params {2}")
    public static Response post(String URL, Headers headers, String params, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user)).headers(headers)
                        .when()
                        .post(URL + "?" + params)
                        .then()
                        .extract().response();
    }


    @Step("Put Request with the URL {0} , body {1}")
    public static Response put(String URL, String body, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }

    @Step("Put Request with the URL {0} , body {1} and Headers {2}")
    public static Response put(String URL, String body, Headers headers, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .headers(headers)
                        .body(body)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }


    @Step("Put Request with the URL {0} , body {1} and params {2}")
    public static Response put(String URL, String body, String params, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .when()
                        .put(URL + "?" + params).
                        then()
                        .extract().response();
    }


    @Step("Put Request with the URL {0} , body {1} and params {2}")
    public static Response put(String URL, String body, String headers, String params, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user)).headers(constructHeader(headers))
                        .body(body)
                        .when()
                        .put(URL + "?" + params)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0}")
    public static Response delete(String URL, User user) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0}")
    public static Response delete(String URL, String header, String params, User user) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .headers(constructHeader(header))
                        .queryParams(constructParams(params))
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0} and headers{1}")
    public static Response delete(String URL, Headers headers, User user) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .headers(headers)
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Get Request Specification")
    public static RequestSpecification getSpec(User user) {
        return given().baseUri(BASE_URI).filter(new AllureRestAssured()).header(TokenGenerator.getToken(user))
                .header(getXMasheryHandshake(user)).header("Content-Type", "application/json")
                .config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

    }

    //************************************** With Specs***************************************************
    @Step("Post Request with the URL {0} and spec {1}")
    public static Response post(String URL, RequestSpecification spec) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(spec)
                        .when()
                        .post(URL)
                        .then()
                        .extract().response();
    }

    @Step("Get Request with the URL {0} and spec {1}")
    public static Response get(String URL, RequestSpecification spec) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(spec)
                        .when()
                        .get(URL)
                        .then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and headers{2}")
    public static Response postAuthorization(String URL, String body, Headers headers) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(CLUSTER_URI))
                        .body(body)
                        .headers(headers)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Put Request with the URL {0} , headers {1} and params {2}")
    public static Response putWithoutAuth(String URL, String headers, String params, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpecWithoutAuth(user)).headers(constructHeader(headers))
                        .when()
                        .put(URL + "?" + params)
                        .then()
                        .extract().response();
    }

    @Step("Get Request Specification wihtout Bearer Token")
    public static RequestSpecification getSpecWithoutAuth(User user) {
        return given().baseUri(BASE_URI).filter(new AllureRestAssured())
                .header(getXMasheryHandshake(user)).header("Content-Type", "application/json")
                .config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));
    }

    public static Response getWithOutAuth(String URL, String headers, String params, User user) {
        System.out.println("Running Get Request on the End Point without Auth. " + URL);
        return given()
                .spec(getSpecWithoutAuth(user)).headers(constructHeader(headers))
                .when()
                .get(URL + "?" + params)
                .then()
                .extract().response();

    }

    @Step("Patch Request with the URL {0} , body {1} and headers{2}")
    public static Response patch(String URL, String body, Headers headers) {
        System.out.println("Running Patch Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .headers(headers)
                        .when()
                        .patch(URL).
                        then()
                        .extract().response();
    }

    @Step("Patch Request with the URL {0} , body {1} and headers{2}")
    public static Response patchAuthorization(String URL, String body, Headers headers) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(CLUSTER_URI))
                        .body(body)
                        .headers(headers)
                        .when()
                        .patch(URL).
                        then()
                        .extract().response();
    }

    @Step("Patch Request with the URL {0} , body {1} and headers{2} and Uwer{3}")
    public static Response patch(String URL, String body, Headers headers, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .headers(headers)
                        .when()
                        .patch(URL).
                        then()
                        .extract().response();
    }

    @Step("Get Request with the URL{0}, headers{1} and user{3}")
    public static Response get(String url, Map<String, String> headers, User user) {
        System.out.println("Running Get Request on the End Point " + url);
        return
                given()
                        .spec(getSpec(user)).headers(headers)
                        .when()
                        .get(url)
                        .then()
                        .extract().response();
    }

    @Step("Post Request with the URL {0} , body {1} and headers{2}, user{3}")
    public static Response post(String URL, String body, Map<String, String> headers, User user) {
        System.out.println("Running Post Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .headers(headers)
                        .when()
                        .post(URL).
                        then()
                        .extract().response();
    }

    @Step("Put Request with the URL {0} , body {1} and headers {2}")
    public static Response put(String URL, String body, Map<String, String> headers, User user) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .body(body)
                        .headers(headers)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }

    @Step("Put Request with the URL {0} , body {1} and headers {2}")
    public static Response put(String URL, String body, Map<String, String> headers) {
        System.out.println("Running Put Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .headers(headers)
                        .when()
                        .put(URL).
                        then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0} and headers{1}")
    public static Response delete(String URL, Map<String, String> headers, User user) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .headers(headers)
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }

    @Step("Delete Request with the URL {0} and headers{1}")
    public static Response delete(String URL, String params, User user) {
        System.out.println("Running Delete Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec(user))
                        .when()
                        .delete(URL + "?" + params)
                        .then()
                        .extract().response();
    }




    @Step("Delete Request with the URL {0} , body {1}")
    public static Response delete(String URL, String body) {
        System.out.println("Running Patch Request on the End Point " + URL);
        return
                given()
                        .spec(getSpec())
                        .body(body)
                        .when()
                        .delete(URL).
                        then()
                        .extract().response();
    }


}