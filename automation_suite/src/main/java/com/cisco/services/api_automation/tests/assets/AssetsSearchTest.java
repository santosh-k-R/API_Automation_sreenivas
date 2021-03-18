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

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_SEARCH_FIELDS_DATA;
import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_META_DATA;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.*;

@Feature("Assets APIS")
public class AssetsSearchTest {
	String errorInfo = null, message = null;
	Response response = null;
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_SEARCH_FIELDS_DATA);
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

	public ExpectedAndActualPojo getExpectedAndActualSearchCount(AssetsAPIPojo apiObj, AssetsAPIPojo metaDatAPIObj) {
		System.out.println("**********Running Test for Endpoint: " + apiObj.getEndPoint() + " and param: "
				+ apiObj.getParams() + " *******************");
		Response expectedResponse = getExpectedAPIResponse(metaDatAPIObj);
		String stringToBeSearched = StringUtils.substringBetween(apiObj.getParams(), "search=", "&");
		String[] searchList = metaDatAPIObj.getSearchFields().split(",");
//		System.out.println("searchList: " + Arrays.toString(searchList));
		String searchFieldValue = null;
		int expectedSearchRecordCount = 0;
		int actualSearchRecordCount = 0;
		ExpectedAndActualPojo expectedAndActual = new ExpectedAndActualPojo();
		/* Build Expected Record Count */
		int recordCount = expectedResponse.jsonPath().getList("data").size();
		for (int n = 0; n < recordCount; n++) {
//			System.out.println("Running " + (n + 1) + " out of " + recordCount + " times");
			for (String searchField : searchList) {
				Boolean foundValue = false;
//				System.out.println("Finding for field " + searchField);
				if (searchField.equals("tags")) {
					if (expectedResponse.jsonPath().getList("data[" + n + "].tags").size() > 0) {
						for (int t = 0; t < expectedResponse.jsonPath().getList("data[" + n + "].tags").size(); t++) {
							searchFieldValue = expectedResponse.jsonPath()
									.get("data[" + n + "].tags[" + t + "].tagName");
							if (searchFieldValue.toLowerCase().contains(stringToBeSearched.toLowerCase())) {
								expectedSearchRecordCount++;
								foundValue = true;
								break;
							}
						}
					}
				} else if (searchField.equals("groupInfo")) {
					if (expectedResponse.jsonPath().getList("data[" + n + "].groupInfo").size() > 0) {
						for (int g = 0; g < expectedResponse.jsonPath().getList("data[" + n + "].groupInfo")
								.size(); g++) {
							searchFieldValue = expectedResponse.jsonPath()
									.get("data[" + n + "].groupInfo[" + g + "].groupName");
							if (searchFieldValue.toLowerCase().contains(stringToBeSearched.toLowerCase())) {
								expectedSearchRecordCount++;
								foundValue = true;
								break;
							}
						}
					}
				} else if (!(expectedResponse.jsonPath().get("data[" + n + "]." + searchField) == null)) {
					searchFieldValue = expectedResponse.jsonPath().get("data[" + n + "]." + searchField);

					if (searchFieldValue.toLowerCase().contains(stringToBeSearched.toLowerCase())) {
						expectedSearchRecordCount++;
						foundValue = true;
						break;
					}
				}
				if (foundValue) {
					break;
				}
			}
		}
//		System.out.println("expectedSearchRecordCount: " + expectedSearchRecordCount);
		/* Get Actual Record Count */
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		if (apiObj.getEndPoint().contains("export")) {
			actualSearchRecordCount = AssetsDataReader.getActualRecordCountExportAPIs(response);
		} else {
			actualSearchRecordCount = response.jsonPath().getList("data").size();
		}
//		System.out.println("actualSearchRecordCount: " + actualSearchRecordCount);

		/* Set expected and actual values in pojo to be returned */
		expectedAndActual.setExpectedRecordCount(expectedSearchRecordCount);
		expectedAndActual.setActualRecordCount(actualSearchRecordCount);

		/* Print failure message if API call fails */
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		return expectedAndActual;

	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__allAssetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__assetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__contractsDetailsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__securityAdvisoriesAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__fieldNoticesAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__crticalBugsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__assetGroupAllAssetsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__assetGroupGroupsAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AssetsSearchFieldsData")
	public void validateSearch__assetsAPIWithSearchContextEqualsDNAC(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
		ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
		/* Compare Expected and Actual Search Count */
		softAssert.assertEquals(expectedAndActual.getActualRecordCount(), expectedAndActual.getExpectedRecordCount(),
				"test failed as mismtach in expected and actual ");
		softAssert.assertAll();
	}

}