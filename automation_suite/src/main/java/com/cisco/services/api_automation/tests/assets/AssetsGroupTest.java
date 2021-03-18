package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;

import io.qameta.allure.Feature;
import io.restassured.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_GROUPS_DATA;

import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Feature("Assets APIS")
public class AssetsGroupTest {
	SoftAssert softAssert;
	String errorInfo = null, message = null, groupId = null, groupName = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_GROUPS_DATA);

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsGroupData")
	public void validateAssetsGroup__create(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo apiAGGroupsObj = data.get("_assetgroup_groups");
		AssetsAPIPojo apiAllAssetsObj = data.get("_inventory_all_assets_wireless");
		Response responseAGGroupsAPI = null;
		Response responseAllAssetsAPI = null;
		String payload = null;
		String expectedResonseGroupAPI = null;
		String assetCount = null;
		groupName = "TestAutomation" + AssetsDataReader.currentDataTime();
		payload = apiObj.getPayLoad().replace("<groupName>", groupName);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), payload);
		softAssert.assertEquals(response.getStatusCode(), 200, "Test Failed");
		if (response.getStatusCode() == 200) {
			groupId = response.jsonPath().get("id");
			responseAGGroupsAPI = getAPIResponse(apiAGGroupsObj.getMethod(), apiAGGroupsObj.getEndPoint(), "",
					apiAGGroupsObj.getParams().replace("<groupId>", groupId), "");
			responseAllAssetsAPI = getAPIResponse(apiAllAssetsObj.getMethod(), apiAllAssetsObj.getEndPoint(), "",
					apiAllAssetsObj.getParams(), "");

			if (responseAGGroupsAPI.getStatusCode() == 200 && responseAllAssetsAPI.getStatusCode() == 200) {
				assetCount = Integer.toString(responseAllAssetsAPI.jsonPath().get("Pagination.total"));
				expectedResonseGroupAPI = apiAGGroupsObj.getExpectedResponse().replace("<groupId>", groupId)
						.replace("<groupName>", groupName).replaceAll("<assetCount>", assetCount);
				JSONAssert.assertEquals(expectedResonseGroupAPI, responseAGGroupsAPI.getBody().asString(),
						JSONCompareMode.STRICT);
			}
		}
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
//
	@Test(dependsOnMethods = {
			"validateAssetsGroup__create" }, dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsGroupData")
	public void validateAssetsGroup__update(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo apiAGGroupsObj = data.get("_assetgroup_groups");
		AssetsAPIPojo apiAllAssetsObj = data.get("_inventory_all_assets_routers");
		Response responseAGGroupsAPI = null;
		Response responseAllAssetsAPI = null;
		String payload = null;
		String expectedResonseGroupAPI = null;
		String assetCount = null;
		groupName = groupName + "_updated";
		payload = apiObj.getPayLoad().replace("<groupName>", groupName).replace("<groupId>", groupId);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), payload);
		softAssert.assertEquals(response.getStatusCode(), 200, "Test Failed");
		if (response.getStatusCode() == 200) {
			responseAGGroupsAPI = getAPIResponse(apiAGGroupsObj.getMethod(), apiAGGroupsObj.getEndPoint(), "",
					apiAGGroupsObj.getParams().replace("<groupId>", groupId), "");
			responseAllAssetsAPI = getAPIResponse(apiAllAssetsObj.getMethod(), apiAllAssetsObj.getEndPoint(), "",
					apiAllAssetsObj.getParams(), "");

			if (responseAGGroupsAPI.getStatusCode() == 200 && responseAllAssetsAPI.getStatusCode() == 200) {
				assetCount = Integer.toString(responseAllAssetsAPI.jsonPath().get("Pagination.total"));
				expectedResonseGroupAPI = apiAGGroupsObj.getExpectedResponse().replace("<groupId>", groupId)
						.replace("<groupName>", groupName).replaceAll("<assetCount>", assetCount);
				JSONAssert.assertEquals(expectedResonseGroupAPI, responseAGGroupsAPI.getBody().asString(),
						JSONCompareMode.STRICT);
			}
		}
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dependsOnMethods = {
			"validateAssetsGroup__update" }, dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsGroupData")
	public void validateAssetsGroup__delete(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo apiAGGroupsObj = data.get("_assetgroup_groups");
		Response responseAGGroupsAPI = null;
		String payload = null;
		payload = apiObj.getPayLoad().replace("<groupId>", groupId);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), payload);
		softAssert.assertEquals(response.getStatusCode(), 200, "Test Failed");
		if (response.getStatusCode() == 200) {
			responseAGGroupsAPI = getAPIResponse(apiAGGroupsObj.getMethod(), apiAGGroupsObj.getEndPoint(), "",
					apiAGGroupsObj.getParams().replace("<groupId>", groupId), "");
//			if (responseAGGroupsAPI.getStatusCode() == 200) {
//				softAssert.assertEquals(responseAGGroupsAPI.jsonPath().get("Pagination.total"), 0,
//						"Records Count Mistmatch");
//			}

			if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
				errorInfo = response.jsonPath().get("reason.errorInfo");
				message = response.jsonPath().get("message");
				System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
			}
			softAssert.assertAll();
		}
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsGroupData")
	public void validateAssetsGroup__edit(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo apigroupsByAsset = data.get("_assetgroup_groupsByAsset");
		Response responseGroupsByAsset = null;
		JSONObject obj = new JSONObject(apiObj.getPayLoad());
		List<Integer> expectedGroupIdslist = new ArrayList<Integer>();
		List<Integer> actualGroupIdslist = null;
		List<Integer> actualGroupIdslistGroupsByAsset = null;
		JSONArray jsonArray = obj.getJSONArray("groupIds");
		System.out.println("Printing jsonArray: " + jsonArray);
		for (int i = 0; i < jsonArray.length(); i++) {
			expectedGroupIdslist.add(jsonArray.getInt(i));
		}
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), apiObj.getPayLoad());
		softAssert.assertEquals(response.getStatusCode(), 200, "Test Failed Due to difference in Status Code");
		if (response.getStatusCode() == 200) {
			actualGroupIdslist = response.jsonPath().getList("data.groupId");
			softAssert.assertEquals(actualGroupIdslist, expectedGroupIdslist,
					"Difference in response of device/edit api");
			responseGroupsByAsset = getAPIResponse(apigroupsByAsset.getMethod(), apigroupsByAsset.getEndPoint(), "",
					apigroupsByAsset.getParams(), "");
			actualGroupIdslistGroupsByAsset = responseGroupsByAsset.jsonPath().getList("data.groupId");
			softAssert.assertEquals(actualGroupIdslistGroupsByAsset, expectedGroupIdslist,
					"Difference in response of groupsByAsset API");

		}
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsGroupData")
	public void validateAssetsGroup__deviceEditErrorResponses(String key) throws JSONException {
		softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), apiObj.getPayLoad());
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
		JSONAssert.assertEquals(apiObj.getExpectedResponse(), response.getBody().asString(), JSONCompareMode.LENIENT);

		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

}