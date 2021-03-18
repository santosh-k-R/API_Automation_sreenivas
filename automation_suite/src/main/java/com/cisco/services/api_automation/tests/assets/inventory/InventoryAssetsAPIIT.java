package com.cisco.services.api_automation.tests.assets.inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.InventoryAssetsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Inventory APIs")
public class InventoryAssetsAPIIT extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
//	String customerId = AssetsData.CUSTOMERID;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey = "inventory_assets";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}

	@Test(description = "/inventory/v1/assets API 200 Response Validation")
	public void api200ResponseValidation() throws Exception {
		System.out.println("****************** 200 Response Validation for API " + endPoint);
		try {
			responseAPI = CommonTestAcrossAPIsIT.successResponse(endPoint);
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	@Test(dependsOnMethods = {
			"api200ResponseValidation" }, description = "/inventory/v1/assets API Records Count Validation with ES")
	public void apiRecordsCountValidation() {
		System.out.println("****************** Records Count Validation with ES for API " + endPoint);
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		try {
			apiRecordsCount = CommonTestAcrossAPIsIT.recordsCount(apiKey, responseAPI);
			if (apiRecordsCount != null) {
				responseES = apiRecordsCount.getResponseES();

				softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),
						apiRecordsCount.getCountOfRecordsFromES(), "Count of API Not Matched with ES Index ");
			} else
				softAssert.assertFalse(true, "Unable to fetch records from ES ");

			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}

	}

	@Test(dependsOnMethods = {
			"api200ResponseValidation" }, description = "/inventory/v1/assets API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__inventory_assets(String optionalParam) {
		System.out.println(
				"****************** Optional Params Validation for API " + endPoint + " for Param " + optionalParam);
		SoftAssert softAssert = new SoftAssert();
		if (responseAPI.jsonPath().get("data[0]." + optionalParam) == null) {
			Assert.assertFalse(true, "Unable to test this param as first value in the response is coming null");
		}
		String optionalParamValue = responseAPI.jsonPath().get("data[0]." + optionalParam).toString();
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		apiRecordsCount = CommonTestAcrossAPIsIT.recordsCountWithOptionalParam(apiKey, optionalParam,
				optionalParamValue);

		try {

			if (apiRecordsCount.getResponseAPI().getStatusCode() == 200) {

				if (apiRecordsCount.getResponseES().getStatusCode() == 200) {

					softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),
							apiRecordsCount.getCountOfRecordsFromES(), "Count of API Not Matched with ES Index ");
				} else {
					softAssert.assertEquals(responseES.getStatusCode(), Integer.parseInt(expectedStatusCode),
							"Unable to fetch records from ES ");
				}

			} else {
				softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
						"API not working for the query Param " + optionalParam);
			}

			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	@Test(dependsOnMethods = {
			"api200ResponseValidation" }, description = "/inventory/v1/assets API Optional Params Validation", dataProvider = "GetParamsWithValues", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidationWithParamsNotPresentInResponse__inventory_assets(String optionalParam,
			String optionalParamValue) {
		System.out.println("****************** Optional Params Validation for API " + endPoint + " for Param "
				+ optionalParam + " with value " + optionalParamValue);
		SoftAssert softAssert = new SoftAssert();
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		apiRecordsCount = CommonTestAcrossAPIsIT.recordsCountWithOptionalParam(apiKey, optionalParam,
				optionalParamValue);

		try {

			if (apiRecordsCount.getResponseAPI().getStatusCode() == 200) {

				if (apiRecordsCount.getResponseES().getStatusCode() == 200) {

					softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),
							apiRecordsCount.getCountOfRecordsFromES(), "Count of API Not Matched with ES Index ");
				} else {
					softAssert.assertEquals(responseES.getStatusCode(), Integer.parseInt(expectedStatusCode),
							"Unable to fetch records from ES ");
				}

			} else {
				softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
						"API not working for the query Param " + optionalParam);
			}

			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	@Test(dependsOnMethods = {
			"apiRecordsCountValidation" }, description = "Assets API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** Assets API Data Validation with ES");
		int pages = 0;
		int recordCount = 0;
		int rows = 100;
		Map<String, InventoryAssetsAPIPojo> invAssetsAPIDataMap = new HashMap<String, InventoryAssetsAPIPojo>();
		Map<String, InventoryAssetsAPIPojo> invAssetsAPIDataMapES = new HashMap<String, InventoryAssetsAPIPojo>();
		InventoryAssetsAPIPojo invAssetsAPIData;
		InventoryAssetsAPIPojo invAssetsAPIDataES;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put("rows", String.valueOf(rows));
		String key = null;

		try {
			/** Running the API for each page and creating collection */
			pages = responseAPI.jsonPath().get("Pagination.pages");
			System.out.println("Number of Pages in Assets API Response  ---> " + pages);
			for (int pno = 1; pno <= pages; pno++) {
				System.out.println("<---------------------Running Assets API for page number " + pno + " out of "
						+ pages + " --------------------------->");
				queryParaMap.put("page", String.valueOf(pno));
				System.out.println("queryParaMap--> " + queryParaMap);
				responseAPI = RestUtils.get(endPoint, queryParaMap, headers);
				recordCount = responseAPI.jsonPath().getList("data").size();
//				System.out.println("responseAPI Body --->" + responseAPI.asString());
				System.out.println("API Record Count --->  " + recordCount);
				for (int n = 0; n < recordCount; n++) {
					invAssetsAPIData = new InventoryAssetsAPIPojo();
					key = responseAPI.jsonPath().get("data[" + n + "].neId") + "_"
							+ responseAPI.jsonPath().get("data[" + n + "].hwInstanceId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsAPIData.setCollectorId(responseAPI.jsonPath().get("data[" + n + "].collectorId"));
					invAssetsAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
					invAssetsAPIData
							.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
//					invAssetsAPIData.setCoverage(responseAPI.jsonPath().get("data[" + n + "].coverage").toString());
//					invAssetsAPIData.setLastDateOfSupportRange(responseAPI.jsonPath().get("data[" + n + "].lastDateOfSupportRange").toString());
					invAssetsAPIData.setDeviceName(responseAPI.jsonPath().get("data[" + n + "].deviceName").toString());
//					invAssetsAPIData.setLastScan(responseAPI.jsonPath().get("data[" + n + "].lastScan").toString());
//					invAssetsAPIData.setCriticalAdvisories(responseAPI.jsonPath().get("data[" + n + "].criticalAdvisories").toString());
//					invAssetsAPIData.setSupportCovered(responseAPI.jsonPath().get("data[" + n + "].supportCovered").toString());
					invAssetsAPIData.setRole(responseAPI.jsonPath().get("data[" + n + "].role").toString());
					invAssetsAPIData
							.setContractNumber(responseAPI.jsonPath().get("data[" + n + "].contractNumber").toString());
					invAssetsAPIData
							.setEquipmentType(responseAPI.jsonPath().get("data[" + n + "].equipmentType").toString());
					invAssetsAPIData.setReachabilityStatus(
							responseAPI.jsonPath().get("data[" + n + "].reachabilityStatus").toString());
					invAssetsAPIData.setScanStatus(responseAPI.jsonPath().get("data[" + n + "].scanStatus").toString());
					invAssetsAPIData.setSourceNeId(responseAPI.jsonPath().get("data[" + n + "].sourceNeId").toString());
					invAssetsAPIData
							.setIsManagedNE(responseAPI.jsonPath().get("data[" + n + "].isManagedNE").toString());
					invAssetsAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
					invAssetsAPIData.setProductName(responseAPI.jsonPath().get("data[" + n + "].productName"));
					invAssetsAPIData.setOsType(responseAPI.jsonPath().get("data[" + n + "].osType").toString());
					invAssetsAPIData.setOsVersion(responseAPI.jsonPath().get("data[" + n + "].osVersion").toString());
					invAssetsAPIData
							.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					invAssetsAPIData.setWfId(responseAPI.jsonPath().get("data[" + n + "].wfId").toString());
					invAssetsAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					invAssetsAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
//					invAssetsAPIData.setMgmtSystemId(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemId").toString());
//					invAssetsAPIData.setMgmtSystemAddr(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemAddr"));
//					invAssetsAPIData.setMgmtSystemHostname(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemHostname").toString());
//					invAssetsAPIData.setMgmtSystemType(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemType").toString());
					invAssetsAPIData.setIsCollector(responseAPI.jsonPath().get("data[" + n + "].isCollector"));
					invAssetsAPIDataMap.put(key, invAssetsAPIData);

				}
			}
			System.out.println("invAssetsAPIDataMap:" + invAssetsAPIDataMap);
			System.out.println("invAssetsAPIDataMap Size:" + invAssetsAPIDataMap.size());

			/** Parsing ES Response and creating collection */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES = responseES.jsonPath().get("hits.total.value");
				;
				for (int n = 0; n < recordCountES; n++) {
					invAssetsAPIDataES = new InventoryAssetsAPIPojo();
					key = responseES.jsonPath().get("hits.hits[" + n + "]._source.neId") + "_"
							+ responseES.jsonPath().get("hits.hits[" + n + "]._source.hwId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsAPIDataES.setCollectorId(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorId").toString());
					invAssetsAPIDataES.setIpAddress(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.managementAddress").toString());
					invAssetsAPIDataES.setManagedNeId(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
					// invAssetsAPIDataES.setCoverage(responseES.jsonPath().get("hits.hits[" + n +
					// "]._coverage").toString());
					// invAssetsAPIDataES.setLastDateOfSupportRange(responseES.jsonPath().get("hits.hits["
					invAssetsAPIDataES.setDeviceName(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.hostname").toString());
					// invAssetsAPIDataES.setLastScan(responseES.jsonPath().get("hits.hits[" + n +
					// "]._source.lastScanDate").toString());
					// invAssetsAPIDataES.setCriticalAdvisories(responseES.jsonPath().get("hits.hits["
					// + n + "]._source.criticalAdvisories").toString());
					// invAssetsAPIDataES.setSupportCovered(responseES.jsonPath().get("hits.hits[" +
					// n + "]._source.supportCovered").toString());
					invAssetsAPIDataES
							.setRole(responseES.jsonPath().get("hits.hits[" + n + "]._source.role").toString());
					invAssetsAPIDataES.setContractNumber(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.contractNumber").toString());
					invAssetsAPIDataES.setEquipmentType(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.equipmentType").toString());
					invAssetsAPIDataES.setReachabilityStatus(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.reachabilityStatus").toString());
					invAssetsAPIDataES.setScanStatus(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.scanStatus").toString());
					invAssetsAPIDataES.setSourceNeId(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.sourceNeId").toString());
					invAssetsAPIDataES.setIsManagedNE(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.isManagedNe").toString());
					invAssetsAPIDataES.setProductId(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.productId").toString());
					invAssetsAPIDataES
							.setProductName(responseES.jsonPath().get("hits.hits[" + n + "]._source.productName"));
					invAssetsAPIDataES.setOsType(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.softwareType").toString());
					invAssetsAPIDataES.setOsVersion(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.softwareVersion").toString());
					invAssetsAPIDataES.setSerialNumber(
							responseES.jsonPath().get("hits.hits[" + n + "]._source.serialNumber").toString());
					invAssetsAPIDataES
							.setWfId(responseES.jsonPath().get("hits.hits[" + n + "]._source.wfid").toString());
					invAssetsAPIDataES
							.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					invAssetsAPIDataES
							.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
					// invAssetsAPIDataES.setMgmtSystemId(responseES.jsonPath().get("hits.hits[" + n
					// + "]._source.mgmtSystemId").toString());
					// invAssetsAPIDataES.setMgmtSystemAddr(responseES.jsonPath().get("hits.hits[" +
					// n + "]._source.mgmtSystemAddr").toString());
					// invAssetsAPIDataES.setMgmtSystemHostname(responseES.jsonPath().get("hits.hits["
					// + n + "]._source.mgmtSystemHostname").toString());
					// invAssetsAPIDataES.setMgmtSystemType(responseES.jsonPath().get("hits.hits[" +
					// n + "]._source.mgmtSystemType").toString());
					invAssetsAPIDataES
							.setIsCollector(responseES.jsonPath().get("hits.hits[" + n + "]._source.isCollector"));
					invAssetsAPIDataMapES.put(key, invAssetsAPIDataES);

				}

			}
			System.out.println("invAssetsAPIDataMapES:" + invAssetsAPIDataMapES);
			System.out.println("invAssetsAPIDataMapES Size:" + invAssetsAPIDataMapES.size());

			/**
			 * Assertions if each elements in the API Collection and ES Collection are
			 * matching
			 */
			for (String keyname : invAssetsAPIDataMapES.keySet()) {
				if (invAssetsAPIDataMap.containsKey(keyname)) {
					softAssert.assertEquals(invAssetsAPIDataMap.get(keyname).toString(),
							invAssetsAPIDataMapES.get(keyname).toString(),
							"API Data not matched with ES Data for NE: " + keyname);
				} else {
					softAssert.assertFalse(true, "API response doesn't contain : " + keyname);
				}
			}
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}
	}

}
