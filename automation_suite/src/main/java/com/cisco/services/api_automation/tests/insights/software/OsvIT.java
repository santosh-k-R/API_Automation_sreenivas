package com.cisco.services.api_automation.tests.insights.software;

import com.cisco.services.api_automation.testdata.insights.software.SoftwareData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.Test;
import springfox.documentation.spring.web.json.Json;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import org.testng.ITestContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.*;
import static com.cisco.services.api_automation.utils.Commons.constructHeader;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

@Feature("OSV APIS")
public class OsvIT {

	private String profileID;
	private String productFamily;
	private String productId;
	private String mgmtSystemId;
	private String swType;
	private String profileName;
	private String dnacID;
	private String recommID;
	Response response;

		@Severity(SeverityLevel.BLOCKER)
	@Test(dataProviderClass = SoftwareData.class, dataProvider = "ProfilesRecomm", priority = 1)
	public void verifyProfilesIDAPI(String method, String url, String headers, String params, String body,
			String expectedStatusCode, String expectedOutput) throws JSONException {
		if (url.equalsIgnoreCase("/osv-ui/v1/profiles")) {
			response = getAPIResponse(method, url, headers, params, body);
			System.out.println("Status Code :->>>" + response.getStatusCode());

			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
			if (response.getStatusCode() == 200) {
				Map<String, String> uiProfileList = response.jsonPath().getMap("uiProfileList[0]");
				profileID = uiProfileList.get("id");
				productFamily = uiProfileList.get("productFamily");
				productId = uiProfileList.get("productId");
				mgmtSystemId = uiProfileList.get("dnacSystemID");
				swType = uiProfileList.get("swType");
				profileName = uiProfileList.get("profileName");
				dnacID = uiProfileList.get("dnacID");
				System.out.println("PFID----->" + uiProfileList.get("id"));

			} else {
				System.out.println("response is not 200" + response.getStatusCode());
			}
		} else

		{
			String recommParams = params + "&profileName=" + profileName + "&productFamily=" + productFamily
					+ "&profileId=" + profileID + "&productId=" + productId + "&mgmtSystemId=" + mgmtSystemId
					+ "&swType=" + swType;
			System.out.println("endPointURL----->" + recommParams);
			Response response = getAPIResponse(method, url, headers, recommParams, body);
			System.out.println("recommParams :->>>" + response.getStatusCode());
			String responseBody = response.getBody().asString();

			Map<Object, Object> recommendationSummaries = response.jsonPath().getMap("recommendationSummaries[0]");
			System.out.println("recommendationSummaries.get(\"recommId\")----->"
					+ recommendationSummaries.get("recommId").toString());
			recommID = recommendationSummaries.get("recommId").toString();
			

		}

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProviderClass = SoftwareData.class, dataProvider = "Recomm", priority = 2)
	public void verifyOSVAPIs(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		if (url.equalsIgnoreCase("/osv-ui/v1/profileRecommendations")){
			String recommParams = params + "&profileName=" + profileName + "&productFamily=" + productFamily
					+ "&profileId=" + profileID + "&productId=" + productId + "&mgmtSystemId=" + mgmtSystemId
					+ "&swType=" + swType;
			System.out.println("endPointURL----->" + recommParams);
			response = getAPIResponse(method, url, headers, recommParams, body);
			System.out.println("recommParams :->>>" + response.getStatusCode());

		} else if (url.equalsIgnoreCase("/osv-ui/v1/profileVersions")) {

			String profileVersions = params + "&id=" + profileID + "&profileName=" + profileName + "&dnacID=" + dnacID;
			System.out.println("profileVersions----->" + profileVersions);

			response = getAPIResponse(method, url, headers, profileVersions, body);
			System.out.println("Response Code :->>>" + response.getBody().asString());
			System.out.println("profileVersions :->>>" + response.getStatusCode());
			if (response.getStatusCode() == 200) {
				JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(),
						new CustomComparator(JSONCompareMode.LENIENT,					               
		                new Customization("uiProfileSwVersion[*].optimalVersion", (o1, o2) -> true)));
			}

		}else
		{
			String profileAsset = params + "&id=" + profileID + "&profileName=" + profileName + "&dnacID=" + dnacID;
			System.out.println("profileAsset----->" + profileAsset);

			response = getAPIResponse(method, url, headers, profileAsset, body);
			System.out.println("Response Code :->>>" + response.getBody().asString());
			System.out.println("profileAsset :->>>" + response.getStatusCode());
			if (response.getStatusCode() == 200) {
				JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(),
						 new CustomComparator(JSONCompareMode.LENIENT,					               
					                new Customization("uiAssetList[*].optimalVersion", (o1, o2) -> true)));
			}

		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "affectedAssets", priority = 3)
	public void verifyAffectedAssetsAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		String recommIDParam = params + "&recommId=" + recommID;
		response = getAPIResponse(method, url, headers, recommIDParam, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "adminSettings")
	public void verifyAdminSettingsAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "Summary")
	public void verifySummaryAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(),
		            new CustomComparator(JSONCompareMode.LENIENT,
		                new Customization("riskCategory.HIGH", (o1, o2) -> true),
		                new Customization("riskCategory.LOW", (o1, o2) -> true),
		                new Customization("riskCategory.MEDIUM", (o1, o2) -> true),
		                new Customization("riskCategory.NA", (o1, o2) -> true)  ));
		}

	} 

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "Profiles")
	public void verifyProfilesAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		System.out.println("Response Code :->>>" + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(),
		            new CustomComparator(JSONCompareMode.LENIENT,
		                new Customization("uiProfileList[*].riskScore", (o1, o2) -> true),
		                new Customization("uiProfileList[*].optimalVersion", (o1, o2) -> true),
		                new Customization("uiProfileList[*].id", (o1, o2) -> true),
		                new Customization("uiProfileList[*].riskLevel", (o1, o2) -> true),
		                new Customization("pagination.page", (o1, o2) -> true), 
		                new Customization("pagination.rows", (o1, o2) -> true),
		                new Customization("pagination.total", (o1, o2) -> true)));
		}
	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "Details")
	public void verifyDetailsAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
		}
	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "Export")
	public void verifyExportAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		System.out.println("Response Code :->>>" + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			assertEquals(expectedOutput.trim(), response.getBody().asString().trim());
		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "versions")
	public void verifyVersionsAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			JSONAssert.assertEquals(expectedOutput, response.jsonPath().prettify(), true);
		}
	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "updateCancelAcceptedVersions")
	public void verifyUpdateCancelAcceptedVersionsAPI(ITestContext context, String method, String url, String headers,
			String params, String body, String expectedStatusCode, String expectedOutput, String testCase)
			throws JSONException {
		context.setAttribute("testName", testCase);
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "inactivetoken")
	public void verifyInactiveTokenAPI(String method, String url, String headers, String params, String body,
			String expectedStatusCode, String userRole) throws JSONException {
		
		response = getAPIResponse(method, url, headers, params, body, userRole);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		 System.out.println("Response Code :->>>" + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");

	} 
	
	@Test(dataProviderClass = SoftwareData.class, dataProvider = "feature")
	public void verifyFeaturesAPI(ITestContext context, String method, String url, String headers, String params,
			String body, String expectedStatusCode, String expectedOutput, String testCase) throws JSONException {
		context.setAttribute("testName", testCase);
		String ignoreParam ="recommId";
		response = getAPIResponse(method, url, headers, params, body);
		System.out.println("Status Code :->>>" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status Code: ");
		if (response.getStatusCode() == 200) {
			
			JSONAssert.assertEquals(expectedOutput, response.getBody().asString(),
		            new CustomComparator(JSONCompareMode.LENIENT,
		                        new Customization("recommId", (o1, o2) -> true)));
			
		}
	}

}