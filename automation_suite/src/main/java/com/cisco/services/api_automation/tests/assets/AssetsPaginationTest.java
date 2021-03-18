package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.pojo.response.assets.ExpectedAndActualPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;

import io.qameta.allure.Feature;
import io.restassured.response.Response;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_PAGINATION_DATA;
import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_META_DATA;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.*;

@Feature("Assets APIS")
public class AssetsPaginationTest {
	String errorInfo = null, message = null;
	Response response = null;
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_PAGINATION_DATA);
	private Map<String, AssetsAPIPojo> metadata = ExcelDataReader.metaDataSetter(ASSETS_META_DATA);

	/*
	 * added by shsunder
	 */
	public Response getExpectedAPIResponse(AssetsAPIPojo metaDatAPIObj) {
		System.out.println("Running Pre-requiste API Call");
		Response expectedResponse = getAPIResponse(metaDatAPIObj.getMethod(), metaDatAPIObj.getEndPoint(), "",
				metaDatAPIObj.getPaginationParams(), "");
		return expectedResponse;
	}

	public ExpectedAndActualPojo getExpectedAndActualSearchCount(AssetsAPIPojo apiObj, AssetsAPIPojo metaDatAPIObj) {
		System.out.println("**********Running Test for Endpoint: " + apiObj.getEndPoint() + " and param: "
				+ apiObj.getParams() + " *******************");
		ExpectedAndActualPojo expectedActual = new ExpectedAndActualPojo();
		Response expectedResponse = getExpectedAPIResponse(metaDatAPIObj);
		List<String> expectedSortedList = new ArrayList<String>();
		List<String> actualSortedList = new ArrayList<String>();
		String fieldToBeSorted = StringUtils.substringBetween(apiObj.getParams(), "sort=", ":");
		int page = Integer.parseInt(apiObj.getExpectedResponse().split(",")[1]);
		int rows = Integer.parseInt(apiObj.getExpectedResponse().split(",")[0]);
		int start = 0;
		int end = 0;

		/* Build Expected Data */
		if (expectedResponse.getStatusCode() == 200) {
			int expectedPaginationTotal = expectedResponse.jsonPath().getList("data").size();
			expectedActual.setExpectedPaginationTotal(expectedPaginationTotal);
			int recordCount = 0;
			if (expectedPaginationTotal < rows) {
				recordCount = expectedPaginationTotal;
			} else {
				recordCount = rows;
			}

			start = rows * (page - 1);
			end = recordCount * page;

			for (int n = start; n < end; n++) {
				if (expectedResponse.jsonPath().get("data[" + n + "]." + fieldToBeSorted) == null) {
					expectedSortedList.add("");
				} else {
					expectedSortedList.add(expectedResponse.jsonPath().get("data[" + n + "]." + fieldToBeSorted));
				}
			}
			System.out.println("expectedSortedList: " + expectedSortedList);
			expectedActual.setExpectedSortedList(expectedSortedList);
			expectedActual.setExpectedPaginationPage(page);
			expectedActual.setExpectedPaginationPages(getPaginationPages(expectedPaginationTotal, rows));
			expectedActual.setExpectedPaginationRows(rows);
		}

		/* Get Actual Data */
		Response response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		if (response.getStatusCode() == 200) {
			int recordCountActual = response.jsonPath().getList("data").size();
			for (int n = 0; n < recordCountActual; n++) {
				if (response.jsonPath().get("data[" + n + "]." + fieldToBeSorted) == null) {
					actualSortedList.add("");
				} else {
					actualSortedList.add(response.jsonPath().get("data[" + n + "]." + fieldToBeSorted));
				}
			}
			System.out.println("actualSortedList: " + actualSortedList);
			expectedActual.setActualSortedList(actualSortedList);
			expectedActual.setActualPaginationPage(response.jsonPath().getInt("Pagination.page"));
			expectedActual.setActualPaginationPages(response.jsonPath().getInt("Pagination.pages"));
			expectedActual.setActualPaginationRows(response.jsonPath().getInt("Pagination.rows"));
			expectedActual.setActualPaginationTotal(response.jsonPath().getInt("Pagination.total"));
		}

		/* Print failure message if API call fails */
		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		return expectedActual;
	}

	public Response getActualResponse(AssetsAPIPojo apiObj) {
		Response response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), "");
		return response;

	}

	public int getPaginationPages(int expectedPaginationTotal, int rows) {
		if (expectedPaginationTotal % rows > 0) {
			return Math.round(expectedPaginationTotal / rows) + 1;
		} else {
			return expectedPaginationTotal / rows;
		}
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "PaginationData")
	public void validatePagination__All(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		if (apiObj.getStatusCode().equals("200")) {
			AssetsAPIPojo metaDatAPIObj = metadata.get(apiObj.getType());
			ExpectedAndActualPojo expectedAndActual = getExpectedAndActualSearchCount(apiObj, metaDatAPIObj);
			/* Compare Expected and Actual */
			softAssert.assertEquals(expectedAndActual.getActualSortedList(), expectedAndActual.getExpectedSortedList(),
					"mismtach in expected and actual data ");
			softAssert.assertEquals(expectedAndActual.getActualPaginationPage(),
					expectedAndActual.getExpectedPaginationPage(), "mismtach in pagination.page ");
			softAssert.assertEquals(expectedAndActual.getActualPaginationPages(),
					expectedAndActual.getExpectedPaginationPages(), "mismtach in pagination.pages ");
			softAssert.assertEquals(expectedAndActual.getActualPaginationRows(),
					expectedAndActual.getExpectedPaginationRows(), "mismtach in pagination.rows ");
			softAssert.assertEquals(expectedAndActual.getActualPaginationTotal(),
					expectedAndActual.getExpectedPaginationTotal(), "mismtach in pagination.total ");
		} else {
			Response response = getActualResponse(apiObj);
			softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
			JSONAssert.assertEquals(apiObj.getExpectedResponse(), response.getBody().asString(),
					JSONCompareMode.STRICT);
		}

		softAssert.assertAll();
	}

}