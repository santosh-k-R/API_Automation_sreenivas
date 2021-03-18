package com.cisco.services.api_automation.tests.commonservices.utils;

import com.cisco.services.api_automation.utils.RestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.*;

import static io.restassured.RestAssured.given;

public class CommonUtil {


    public static String getBearerTokenForSDP() throws Exception {
        String bearerToken = null;
        try {
            String url = "https://cloudsso-test.cisco.com/as/token.oauth2";
            Response response = RestUtils.post(url, "", "grant_type=client_credentials&client_id=" + System.getenv("api_client_id") + "&client_secret=" + System.getenv("api_client_secret"));
            if (response.getStatusCode() == 200) {
                String jsonStr = response.body().asString();
                JsonElement responseJson = JsonParser.parseString(jsonStr);
                bearerToken = responseJson.getAsJsonObject().get("access_token").getAsString();
            }
        } catch (Exception ex) {
            throw ex;
        }
        return bearerToken;
    }


    public static Response invokeSDPApi(String endpointUrl, String requestType, String params, String headers, String body) throws Exception {

        Response sdpApiResponse = null;
        try {
            String sdpApiBaseUrl = endpointUrl.substring(0, endpointUrl.indexOf("customerportal") + 14);
            RequestSpecification requestSpec = new RequestSpecBuilder().build();
            requestSpec.baseUri(sdpApiBaseUrl);

            String bearerToken = CommonUtil.getBearerTokenForSDP();

            JsonObject tempJson = new JsonObject();
            tempJson.addProperty("client_cco_id", System.getenv("username"));
            tempJson.addProperty("party_id", System.getenv("api_party_id"));
            tempJson.addProperty("access_token_uid", System.getenv("username"));
            tempJson.addProperty("access_token_accesslevel", "4");
            String xMasheryHandshake = Base64.getEncoder().encodeToString(tempJson.toString().getBytes());

            String cxContext = generateCxContext();

            Map<String, String> headerMap = new HashMap<String, String>();
            String headerNames[] = {"Authorization", "X-Mashery-Handshake", "cx-context"};
            String headerValues[] = {"Bearer " + bearerToken, xMasheryHandshake, cxContext};
            for (int i = 0; i < headerNames.length; i++) {
                headerMap.put(headerNames[i], headerValues[i]);
            }

            String headerNameValues[] = null;
            if (headers.length() > 0) {
                headerNameValues = headers.split(";");
                for (int i = 0; i < headerNameValues.length; i += 2) {
                    headerMap.put(headerNameValues[i], headerNameValues[i + 1]);
                }
            }


            requestSpec.headers(headerMap);

            Map<String, String> paramMap = new HashMap<String, String>();
            if (params.length() > 0) {
                if (params.contains("&")) {
                    String paramNameValues[] = params.split("&");
                    for (int i = 0; i < paramNameValues.length; i++) {
                        String nameValue[] = paramNameValues[i].split("=");
                        paramMap.put(nameValue[0], nameValue[1]);
                    }
                } else {
                    String nameValue[] = params.split("=");
                    paramMap.put(nameValue[0], nameValue[1]);

                }
            }


            String sdpApiEndpointUrl = endpointUrl.substring(endpointUrl.indexOf("customerportal") + 14);


            if (requestType.equalsIgnoreCase("GET")) {
                sdpApiResponse = given().spec(requestSpec).get(sdpApiEndpointUrl, paramMap);
            } else if (requestType.equalsIgnoreCase("POST")) {
                requestSpec.body(body);
                sdpApiResponse = given().spec(requestSpec).post(sdpApiEndpointUrl, paramMap);
            }

        } catch (Exception ex) {
            throw ex;
        }
        return sdpApiResponse;
    }

    public static Response invokeAWSApi(String endpointUrl, String requestType, String params, String headers, String body) throws Exception {

        Response response = null;
        String cxContext = generateCxContext();

        List<Header> headerList = new ArrayList<Header>();
        if (headers.length() > 0) {
            String headerNameValue[] = headers.split(",");
            for (int i = 0; i < headerNameValue.length; i += 2) {
                Header header = new Header(headerNameValue[i], headerNameValue[i + 1]);
                headerList.add(header);
            }
        }


        headerList.add(new Header("cx-context", cxContext));

        if (params.length() > 0) {
            endpointUrl = endpointUrl + "?" + params;
        }


        try {
            if (requestType.equalsIgnoreCase("GET")) {
                if (params.length() == 0) {
                    response = RestUtils.get(endpointUrl, new Headers(headerList));
                } else {
                    response = RestUtils.get(endpointUrl, headers, params);
                }
            } else if (requestType.equalsIgnoreCase("POST")) {
                if (params.length() == 0) {
                    RestUtils.post(endpointUrl, body, new Headers(headerList));
                } else {
                    response = RestUtils.post(endpointUrl, body, headers, params);
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return response;
    }

    public static Response invokeAWSElasticSearch(String indexName, String requestType, String body) throws Exception {

        Response response = null;

        try {

            if (requestType.equalsIgnoreCase("GET")) {
                response = RestUtils.elasticSearchNoSqlGet(indexName);
            } else if (requestType.equalsIgnoreCase("POST")) {
                response = RestUtils.elasticSearchNoSqlPost(indexName, body);
            }

        } catch (Exception ex) {
            throw ex;
        }
        return response;
    }


    public static String generateCxContext() throws Exception {
        String cxContext = null;
        try {
            String partyId = System.getenv("api_party_id");
            JsonArray tempJsonArr = new JsonArray();
            JsonObject tempObj = new JsonObject();
            tempObj.addProperty("saId", Long.parseLong(partyId.substring(0, partyId.indexOf("_"))));
            tempObj.addProperty("vaId", 0);
            tempJsonArr.add(tempObj);
            cxContext = tempJsonArr.toString();
        } catch (Exception ex) {
            throw ex;
        }
        return cxContext;
    }


    public static String prepareDescriptiveText(JSONCompareResult comparisonResult) {

        String descriptiveText = "";
        try {

            if (comparisonResult.getFieldFailures().size() > 0) {
                descriptiveText = descriptiveText + "Failed Fields: ";
            }

            for (FieldComparisonFailure failedField : comparisonResult.getFieldFailures()) {
                descriptiveText = descriptiveText + failedField.getField() + ", ";
            }

            if (descriptiveText.endsWith(", ")) {
                descriptiveText = descriptiveText.substring(0, descriptiveText.length() - 2);
            }


            if (comparisonResult.getFieldMissing().size() > 0) {
                descriptiveText = descriptiveText + "\n Missing Fields: ";
            }

            for (FieldComparisonFailure missedField : comparisonResult.getFieldMissing()) {
                descriptiveText = descriptiveText + missedField.getExpected() + ", ";
            }

            if (descriptiveText.endsWith(", ")) {
                descriptiveText = descriptiveText.substring(0, descriptiveText.length() - 2);
            }


        } catch (Exception ex) {
            descriptiveText = "Exception in extracting the descriptiveText: " + ex.getMessage();
        }
        return descriptiveText;
    }

    /**
     * This method is used to check whether the two json's are of
     * different structure
     *
     * @param json1 the json data1
     * @param json2 the json data2
     * @return true if they are of different format, false otherwise
     */
    public static boolean isOfDifferentStructure(JsonElement json1, JsonElement json2) {
        boolean isJsonFormatDifferent = false;
        if ((json1.isJsonObject() && json2.isJsonArray())
                || json1.isJsonArray() && json2.isJsonObject()) {
            isJsonFormatDifferent = true;
        }
        return isJsonFormatDifferent;
    }


}
