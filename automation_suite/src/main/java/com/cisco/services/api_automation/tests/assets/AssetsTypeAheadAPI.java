package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsDataReader;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.services.api_automation.testdata.assets.AssetsDataReader.ASSETS_All_ASSETS_TYPEAHEAD;
import static com.cisco.services.api_automation.utils.Commons.getAPIResponse;

import java.util.Map;

@Feature("Assets APIS")
public class AssetsTypeAheadAPI {
	String errorInfo = null, message = null, groupId = null;
	Response response = null;
	private static String customerId = System.getenv("niagara_partyid");
	private AssetsDataReader ExcelDataReader;
	private Map<String, AssetsAPIPojo> data = ExcelDataReader.dataSetterUI(ASSETS_All_ASSETS_TYPEAHEAD);

	/*
	 * added by shsunder
	 */
	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AllAssetsTypeAheadData")
	public void validateTest__inventoryAllAssetsTypeAheadAPI(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(),
				apiObj.getPayLoad());
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("data[*].lastUpdateDate", (o1, o2) -> true));
		if (response.getStatusCode() == Integer.parseInt(apiObj.getStatusCode())) {
			JSONAssert.assertEquals(apiObj.getExpectedResponse(), response.getBody().asString(), comparator);
		}

		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

	@Test(dataProviderClass = AssetsDataReader.class, dataProvider = "AllAssetsTypeAheadData")
	public void validateTest__inventoryAllAssetsTypeAheadAPI_RBAC(String key) throws JSONException {
		SoftAssert softAssert = new SoftAssert();
		AssetsAPIPojo apiObj = data.get(key);
		response = getAPIResponse(apiObj.getMethod(), apiObj.getEndPoint(), "", apiObj.getParams(), apiObj.getPayLoad(),
				apiObj.getUserRole());
		softAssert.assertEquals(response.getStatusCode(), Integer.parseInt(apiObj.getStatusCode()), "test failed ");
		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
				new Customization("data[*].lastUpdateDate", (o1, o2) -> true));
		if (response.getStatusCode() == Integer.parseInt(apiObj.getStatusCode())) {
			JSONAssert.assertEquals(apiObj.getExpectedResponse(), response.getBody().asString(), comparator);
		}

		if (response.getStatusCode() != Integer.parseInt(apiObj.getStatusCode())) {
			errorInfo = response.jsonPath().get("reason.errorInfo");
			message = response.jsonPath().get("message");
			System.out.println("Failure Info: " + errorInfo + "\nMessage: " + message);
		}
		softAssert.assertAll();
	}

}