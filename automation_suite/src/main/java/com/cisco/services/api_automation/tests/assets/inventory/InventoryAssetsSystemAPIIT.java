package com.cisco.services.api_automation.tests.assets.inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.InventoryAssetsSystemAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Inventory APIs")
public class InventoryAssetsSystemAPIIT extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey = "inventory_assets_system";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}

	@Test(description = "inventory/v1/assets/system API 200 Response Validation")
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
			"api200ResponseValidation" }, description = "inventory/v1/assets/system API Records Count Validation with ES")
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
			"api200ResponseValidation" }, description = "/inventory/v1/assets/system API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__inventory_assets_system(String optionalParam) {
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

	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "Assets System API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** Assets System API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, InventoryAssetsSystemAPIPojo> invAssetsSystemAPIDataMap=new HashMap<String, InventoryAssetsSystemAPIPojo>();
		Map<String, InventoryAssetsSystemAPIPojo> invAssetsSystemAPIDataMapES=new HashMap<String, InventoryAssetsSystemAPIPojo>();
		InventoryAssetsSystemAPIPojo invAssetsSystemAPIData;
		InventoryAssetsSystemAPIPojo invAssetsSystemAPIDataES;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put("rows",String.valueOf(rows));
		String key=null;

		try {
			/** Running the API for each page and creating collection  */
			pages=responseAPI.jsonPath().get("Pagination.pages");
			System.out.println("Number of Pages in Assets API Response  ---> "+pages);
			for (int pno=1;pno<=pages;pno++) {
				System.out.println("<---------------------Running Assets API for page number "+pno+ " out of "+pages+" --------------------------->" );
				queryParaMap.put("page",String.valueOf(pno) );
				System.out.println("queryParaMap--> "+queryParaMap);
				responseAPI = RestUtils.get(endPoint, queryParaMap,headers);
				recordCount=responseAPI.jsonPath().getList("data").size();
//				System.out.println("responseAPI Body --->" + responseAPI.asString());
				System.out.println("API Record Count --->  "+ recordCount);
				for (int n=0;n<recordCount;n++) {
					invAssetsSystemAPIData = new InventoryAssetsSystemAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].neId")+"_"+responseAPI.jsonPath().get("data[" + n + "].serialNumber");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsSystemAPIData.setCollectorId(responseAPI.jsonPath().get("data[" + n + "].collectorId").toString());
					invAssetsSystemAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
					invAssetsSystemAPIData.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
					invAssetsSystemAPIData.setHasBugs(responseAPI.jsonPath().get("data[" + n + "].hasBugs").toString());
					invAssetsSystemAPIData.setHasSecurityAdvisories(responseAPI.jsonPath().get("data[" + n + "].hasSecurityAdvisories").toString());
					invAssetsSystemAPIData.setDeviceName(responseAPI.jsonPath().get("data[" + n + "].deviceName").toString());
					invAssetsSystemAPIData.setCriticalAdvisories(responseAPI.jsonPath().get("data[" + n + "].criticalAdvisories").toString());
					invAssetsSystemAPIData.setRole(responseAPI.jsonPath().get("data[" + n + "].role"));
					invAssetsSystemAPIData.setSourceNeId(responseAPI.jsonPath().get("data[" + n + "].sourceNeId"));
					invAssetsSystemAPIData.setIsManagedNE(responseAPI.jsonPath().get("data[" + n + "].isManagedNE").toString());
					invAssetsSystemAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
					invAssetsSystemAPIData.setProductType(responseAPI.jsonPath().get("data[" + n + "].productType").toString());
					invAssetsSystemAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
					invAssetsSystemAPIData.setProductName(responseAPI.jsonPath().get("data[" + n + "].productName"));
					invAssetsSystemAPIData.setOsType(responseAPI.jsonPath().get("data[" + n + "].osType").toString());
					invAssetsSystemAPIData.setOsVersion(responseAPI.jsonPath().get("data[" + n + "].osVersion").toString());
					invAssetsSystemAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					invAssetsSystemAPIData.setWfId(responseAPI.jsonPath().get("data[" + n + "].wfId").toString());
					invAssetsSystemAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					invAssetsSystemAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
					invAssetsSystemAPIData.setMgmtSystemId(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemId").toString());
					invAssetsSystemAPIData.setMgmtSystemAddr(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemAddr"));
					invAssetsSystemAPIData.setMgmtSystemHostname(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemHostname").toString());
					invAssetsSystemAPIData.setMgmtSystemType(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemType").toString());
					invAssetsSystemAPIData.setIsCollector(responseAPI.jsonPath().get("data[" + n + "].isCollector").toString());
					invAssetsSystemAPIData.setIsScanCapable(responseAPI.jsonPath().get("data[" + n + "].isScanCapable").toString());
					invAssetsSystemAPIData.setScanStatus(responseAPI.jsonPath().get("data[" + n + "].scanStatus").toString());
//					invAssetsSystemAPIData.setLastScan(responseAPI.jsonPath().get("data[" + n + "].lastScan").toString());
					invAssetsSystemAPIDataMap.put(key, invAssetsSystemAPIData);

				}
			}
			System.out.println("invAssetsSystemAPIDataMap:"+ invAssetsSystemAPIDataMap);
			System.out.println("invAssetsSystemAPIDataMap Size:"+ invAssetsSystemAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					invAssetsSystemAPIDataES = new InventoryAssetsSystemAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.neId")+"_"+responseES.jsonPath().get("hits.hits[" + n + "]._source.serialNumber");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsSystemAPIDataES.setCollectorId(responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorId").toString());
					invAssetsSystemAPIDataES.setIpAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.ipAddress").toString());
					invAssetsSystemAPIDataES.setManagedNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
					invAssetsSystemAPIDataES.setHasBugs(responseES.jsonPath().get("hits.hits[" + n + "]._source.hasBug").toString());
					invAssetsSystemAPIDataES.setHasSecurityAdvisories(responseES.jsonPath().get("hits.hits[" + n + "]._source.hasSecurityAdvisory").toString());
					invAssetsSystemAPIDataES.setDeviceName(responseES.jsonPath().get("hits.hits[" + n + "]._source.hostname").toString());
					invAssetsSystemAPIDataES.setCriticalAdvisories(responseES.jsonPath().get("hits.hits[" + n + "]._source.criticalAdvisoryCount").toString());
		        	invAssetsSystemAPIDataES.setRole(responseES.jsonPath().get("hits.hits[" + n + "]._source.role"));
					invAssetsSystemAPIDataES.setIsManagedNE(responseES.jsonPath().get("hits.hits[" + n + "]._source.isManagedNe").toString());
					invAssetsSystemAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.productFamily").toString());
					invAssetsSystemAPIDataES.setProductType(responseES.jsonPath().get("hits.hits[" + n + "]._source.productType").toString());
					invAssetsSystemAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.productId").toString());
					invAssetsSystemAPIDataES.setProductName(responseES.jsonPath().get("hits.hits[" + n + "]._source.productName").toString());
					invAssetsSystemAPIDataES.setOsType(responseES.jsonPath().get("hits.hits[" + n + "]._source.softwareType").toString());
					invAssetsSystemAPIDataES.setOsVersion(responseES.jsonPath().get("hits.hits[" + n + "]._source.softwareVersion").toString());
					invAssetsSystemAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.serialNumber").toString());
					invAssetsSystemAPIDataES.setWfId(responseES.jsonPath().get("hits.hits[" + n + "]._source.wfid").toString());
					invAssetsSystemAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					invAssetsSystemAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
					invAssetsSystemAPIDataES.setMgmtSystemId(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemId").toString());
					invAssetsSystemAPIDataES.setMgmtSystemAddr(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemAddr").toString());
					invAssetsSystemAPIDataES.setMgmtSystemHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemHostname").toString());
					invAssetsSystemAPIDataES.setMgmtSystemType(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemType").toString());
					invAssetsSystemAPIDataES.setSourceNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.sourceNeId"));
					invAssetsSystemAPIDataES.setIsCollector(responseES.jsonPath().get("hits.hits[" + n + "]._source.isCollector").toString());
					invAssetsSystemAPIDataES.setIsScanCapable(responseES.jsonPath().get("hits.hits[" + n + "]._source.isScanCapable").toString());
					invAssetsSystemAPIDataES.setScanStatus(responseES.jsonPath().get("hits.hits[" + n + "]._source.scanStatus").toString());
//					invAssetsSystemAPIDataES.setLastScan(responseES.jsonPath().get("hits.hits[" + n + "]._source.lastScanDate").toString());
	       			invAssetsSystemAPIDataMapES.put(key, invAssetsSystemAPIDataES);

				}

			}
			System.out.println("invAssetsSystemAPIDataMapES:"+ invAssetsSystemAPIDataMapES);
			System.out.println("invAssetsSystemAPIDataMapES Size:"+ invAssetsSystemAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyname: invAssetsSystemAPIDataMapES.keySet()) {
				if(invAssetsSystemAPIDataMap.containsKey(keyname)) {
					softAssert.assertEquals(invAssetsSystemAPIDataMap.get(keyname).toString(),
							invAssetsSystemAPIDataMapES.get(keyname).toString(),
							"API Data not matched with ES Data for NE: " + keyname);
					
				}
				else {
					softAssert.assertFalse(true,"API response doesn't contain : "+keyname);
				}
			}
			softAssert.assertAll();


		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}
	}
}
