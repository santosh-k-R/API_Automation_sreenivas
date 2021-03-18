package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.pojo.response.assets.ExpectedAndActualPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_META_DATA;
import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_SORT_FIELDS_DATA;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Feature("Assets APIS")
public class AssetsSortTest {
	String errorInfo = null, message = null;
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_SORT_FIELDS_DATA);
	private Map<String, AssetsAPIPojo> metadata = ExcelDataReader.metaDataSetter(ASSETS_META_DATA);
	
	/*
	 * added by shsunder
	 */
	public Response getExpectedAPIResponse(AssetsAPIPojo metaDatAPIObj) {
		System.out.println("Running Pre-requiste API Call");
		Response expectedResponse = getAPIResponse(metaDatAPIObj.getMethod(), metaDatAPIObj.getEndPoint(), "",
				metaDatAPIObj.getParams(), "");
		return expectedResponse;
	}

	public ExpectedAndActualPojo setExpectedAndActualSortedFieldList(AssetsAPIPojo apiObj,
			AssetsAPIPojo metaDatAPIObj) {
		System.out.println("**********Running Test for Endpoint: " + apiObj.getEndPoint() + " and param: "
				+ apiObj.getParams() + " *******************");
		ExpectedAndActualPojo expectedActual = new ExpectedAndActualPojo();
		Response expectedResponse = getExpectedAPIResponse(metaDatAPIObj);
		List<String> expectedSortedList = new ArrayList<String>();
		List<String> actualSortedList = new ArrayList<String>();
		String fieldToBeSorted = StringUtils.substringBetween(apiObj.getParams(), "sort=", ":");

		/* Build Expected Sort List */
		int recordCount = expectedResponse.jsonPath().getList("data").size();
		for (int n = 0; n < recordCount; n++) {

			if (expectedResponse.jsonPath().get("data[" + n + "]." + fieldToBeSorted) == null) {
				expectedSortedList.add("");
			} else {
				expectedSortedList.add(expectedResponse.jsonPath().get("data[" + n + "]." + fieldToBeSorted));

			}
		}
		if (apiObj.getParams().contains("ASC")) {
			Collections.sort(expectedSortedList, String.CASE_INSENSITIVE_ORDER);
		} else {
			Collections.sort(expectedSortedList, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(expectedSortedList);

		}

		/* Get Actual Sorted List */
		Response response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		for (int n = 0; n < recordCount; n++) {

			if (response.jsonPath().get("data[" + n + "]." + fieldToBeSorted) == null) {
				actualSortedList.add("");
			} else {
				actualSortedList.add(response.jsonPath().get("data[" + n + "]." + fieldToBeSorted));

			}
		}
		expectedActual.setExpectedSortedList(expectedSortedList);
		expectedActual.setActualSortedList(actualSortedList);

		/* Print failure message if API call fails */
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		return expectedActual;

	}

	public ExpectedAndActualPojo setExpectedAndActualSortedFieldListInteger(AssetsAPIPojo apiObj,
			AssetsAPIPojo metaDatAPIObj) {
		System.out.println("**********Running Test for Endpoint: " + apiObj.getEndPoint() + " and param: "
				+ apiObj.getParams() + " *******************");
		ExpectedAndActualPojo expectedActual = new ExpectedAndActualPojo();
		Response expectedResponse = getExpectedAPIResponse(metaDatAPIObj);
		List<Integer> expectedSortedListInteger = new ArrayList<Integer>();
		List<Integer> actualSortedListInteger = new ArrayList<Integer>();
		String fieldToBeSorted = StringUtils.substringBetween(apiObj.getParams(), "sort=", ":");

		/* Build Expected Sort List */
		expectedSortedListInteger = expectedResponse.jsonPath().getList("data." + fieldToBeSorted);
		if (apiObj.getParams().contains("ASC")) {
			Collections.sort(expectedSortedListInteger);
		} else {
			Collections.sort(expectedSortedListInteger);
			Collections.reverse(expectedSortedListInteger);
		}

		/* Get Actual Sorted List */
		Response response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		actualSortedListInteger = response.jsonPath().getList("data." + fieldToBeSorted);
		expectedActual.setExpectedSortedListInteger(expectedSortedListInteger);
		expectedActual.setActualSortedListInteger(actualSortedListInteger);

		/* Print failure message if API call fails */
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		return expectedActual;

	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__allAssetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__allAssetsAPIInteger(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldListInteger(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(
				expectedActual.getExpectedSortedListInteger().equals(expectedActual.getActualSortedListInteger()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__assetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__assetsAPIInteger(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldListInteger(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(
				expectedActual.getExpectedSortedListInteger().equals(expectedActual.getActualSortedListInteger()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__assetGroupAllAssetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__assetGroupGroupsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__contractsDetailsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__securityAdvisoriesAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__securityAdvisoriesAPIInteger(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldListInteger(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(
				expectedActual.getExpectedSortedListInteger().equals(expectedActual.getActualSortedListInteger()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__fieldNoticesAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__fieldNoticesAPIInteger(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldListInteger(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(
				expectedActual.getExpectedSortedListInteger().equals(expectedActual.getActualSortedListInteger()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__crticalBugsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldList(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(expectedActual.getExpectedSortedList().equals(expectedActual.getActualSortedList()));
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSortFieldsData")
	public void validateSorting__crticalBugsAPIInteger(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedActual = setExpectedAndActualSortedFieldListInteger(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Sorted List */
		softAssert.assertTrue(
				expectedActual.getExpectedSortedListInteger().equals(expectedActual.getActualSortedListInteger()));
		softAssert.assertAll();
	}
}