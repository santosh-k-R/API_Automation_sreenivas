package com.cisco.services.api_automation.tests.assets.inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import com.cisco.services.api_automation.pojo.response.assets.InventoryAssetsHardwareAPIPojo;


@Feature("Assets Inventory APIs")
public class InventoryAssetsHardwareAPIIT extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
//	String customerId = AssetsData.CUSTOMERID;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="inventory_assets_hardware";
	String endPoint;
	String esIndex;
	String endPointHead;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);
		endPointHead =  AssetsData.ASSETS_HEAD_APIS.get(apiKey).getEndPointUrl();
	}



	@Test(description = "/inventory/v1/assets/hardware API 200 Response Validation")
	public void api200ResponseValidation() throws Exception {
		System.out.println("****************** 200 Response Validation for API "+endPoint );
		try {
			responseAPI=CommonTestAcrossAPIsIT.successResponse(endPoint);
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}
	
	@Test(description = "/inventory/v1/assets/hardware HEAD API 200 Response Validation")
	public void headApi200ResponseValidation() throws Exception {
		System.out.println("****************** 200 Response Validation for API "+endPointHead );
		try {
			responseAPI=CommonTestAcrossAPIsIT.successResponse(endPointHead);
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/inventory/v1/assets/hardware API Records Count Validation with ES")
	public void apiRecordsCountValidation( ) {
		System.out.println("****************** Records Count Validation with ES for API "+endPoint);
		APIRecordsCountPojo apiRecordsCount= new APIRecordsCountPojo();
		try { 
			apiRecordsCount=CommonTestAcrossAPIsIT.recordsCount(apiKey, responseAPI);
			if (apiRecordsCount!=null)
			{
			responseES = apiRecordsCount.getResponseES();
			
				softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),apiRecordsCount.getCountOfRecordsFromES(),"Count of API Not Matched with ES Index ");
			}
			 else
				softAssert.assertFalse(true,"Unable to fetch records from ES ");

			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}


	}


	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/inventory/v1/assets/hardware API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__inventory_assets_hardware( String optionalParam) {
		System.out.println("****************** Optional Params Validation for API "+ endPoint + " for Param "+ optionalParam);
		SoftAssert softAssert = new SoftAssert();
		if(responseAPI.jsonPath().get("data[0]."+optionalParam)==null) {
			Assert.assertFalse(true,"Unable to test this param as first value in the response is coming null" );
		}
		String optionalParamValue=responseAPI.jsonPath().get("data[0]."+optionalParam).toString();
		APIRecordsCountPojo apiRecordsCount= new APIRecordsCountPojo();
		apiRecordsCount=CommonTestAcrossAPIsIT.recordsCountWithOptionalParam(apiKey,optionalParam,optionalParamValue);

		try {
			
			
			if (apiRecordsCount.getResponseAPI().getStatusCode()==200) {
				
				if (apiRecordsCount.getResponseES().getStatusCode() == 200) {
					
					softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),apiRecordsCount.getCountOfRecordsFromES(),"Count of API Not Matched with ES Index ");
				}
				else {
					softAssert.assertEquals(responseES.getStatusCode(),Integer.parseInt(expectedStatusCode),"Unable to fetch records from ES ");
				}
				
			}
			else {
				softAssert.assertEquals(responseAPI.getStatusCode(),Integer.parseInt(expectedStatusCode),"API not working for the query Param "+ optionalParam);
			}
			
			
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
		


	}

	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "Assets API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** Assets API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, InventoryAssetsHardwareAPIPojo> invAssetsHardwareAPIDataMap=new HashMap<String, InventoryAssetsHardwareAPIPojo>();
		Map<String, InventoryAssetsHardwareAPIPojo> invAssetsHardwareAPIDataMapES=new HashMap<String, InventoryAssetsHardwareAPIPojo>();
		InventoryAssetsHardwareAPIPojo invAssetsHardwareAPIData;
		InventoryAssetsHardwareAPIPojo invAssetsHardwareAPIDataES;
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
					invAssetsHardwareAPIData = new InventoryAssetsHardwareAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].neId")+"_"+responseAPI.jsonPath().get("data[" + n + "].hwInstanceId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsHardwareAPIData.setCollectorId(responseAPI.jsonPath().get("data[" + n + "].collectorId").toString());
					invAssetsHardwareAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
					invAssetsHardwareAPIData.setHasFieldNotices(responseAPI.jsonPath().get("data[" + n + "].hasFieldNotices").toString()); 
					invAssetsHardwareAPIData.setDeviceName(responseAPI.jsonPath().get("data[" + n + "].deviceName").toString());
					invAssetsHardwareAPIData.setCriticalAdvisories(responseAPI.jsonPath().get("data[" + n + "].criticalAdvisories").toString());
			//		invAssetsHardwareAPIData.setSupportCovered(responseAPI.jsonPath().get("data[" + n + "].supportCovered").toString());
					invAssetsHardwareAPIData.setContractNumber(responseAPI.jsonPath().get("data[" + n + "].contractNumber").toString());
					invAssetsHardwareAPIData.setEquipmentType(responseAPI.jsonPath().get("data[" + n + "].equipmentType").toString());
					invAssetsHardwareAPIData.setSourceNeId(responseAPI.jsonPath().get("data[" + n + "].sourceNeId").toString());
					invAssetsHardwareAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
					invAssetsHardwareAPIData.setProductType(responseAPI.jsonPath().get("data[" + n + "].productType"));
					invAssetsHardwareAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
					invAssetsHardwareAPIData.setProductName(responseAPI.jsonPath().get("data[" + n + "].productName"));
					invAssetsHardwareAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					invAssetsHardwareAPIData.setWfId(responseAPI.jsonPath().get("data[" + n + "].wfId").toString());
					invAssetsHardwareAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					invAssetsHardwareAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
					invAssetsHardwareAPIData.setMgmtSystemId(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemId").toString());
					invAssetsHardwareAPIData.setMgmtSystemAddr(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemAddr"));
					invAssetsHardwareAPIData.setMgmtSystemHostname(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemHostname").toString());
					invAssetsHardwareAPIData.setMgmtSystemType(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemType").toString());
					invAssetsHardwareAPIData.setIsCollector(responseAPI.jsonPath().get("data[" + n + "].isCollector").toString());
					invAssetsHardwareAPIData.setContainingHwId(responseAPI.jsonPath().get("data[" + n + "].containingHwId"));
					invAssetsHardwareAPIDataMap.put(key, invAssetsHardwareAPIData);

				}
			}
			System.out.println("invAssetsHardwareAPIDataMap:"+ invAssetsHardwareAPIDataMap);
			System.out.println("invAssetsHardwareAPIDataMap Size:"+ invAssetsHardwareAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					invAssetsHardwareAPIDataES = new InventoryAssetsHardwareAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.neId")+"_"+responseES.jsonPath().get("hits.hits[" + n + "]._source.hwId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invAssetsHardwareAPIDataES.setCollectorId(responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorId").toString());
					invAssetsHardwareAPIDataES.setIpAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.managementAddress").toString());
					invAssetsHardwareAPIDataES.setHasFieldNotices(responseES.jsonPath().get("hits.hits[" + n + "]._source.hasFieldNotice").toString());
					invAssetsHardwareAPIDataES.setDeviceName(responseES.jsonPath().get("hits.hits[" + n + "]._source.hostname").toString());
					invAssetsHardwareAPIDataES.setCriticalAdvisories(responseES.jsonPath().get("hits.hits[" + n + "]._source.criticalAdvisoryCount").toString());
				//	invAssetsHardwareAPIDataES.setSupportCovered(responseES.jsonPath().get("hits.hits[" + n + "]._source.supportCovered").toString());
					invAssetsHardwareAPIDataES.setContractNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.contractNumber").toString());
					invAssetsHardwareAPIDataES.setEquipmentType(responseES.jsonPath().get("hits.hits[" + n + "]._source.equipmentType").toString());
					invAssetsHardwareAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.productFamily").toString());
					invAssetsHardwareAPIDataES.setProductType(responseES.jsonPath().get("hits.hits[" + n + "]._source.productType").toString());
					invAssetsHardwareAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.productId").toString());
					invAssetsHardwareAPIDataES.setProductName(responseES.jsonPath().get("hits.hits[" + n + "]._source.productName"));
					invAssetsHardwareAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.serialNumber").toString());
					invAssetsHardwareAPIDataES.setWfId(responseES.jsonPath().get("hits.hits[" + n + "]._source.wfid").toString());
					invAssetsHardwareAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					invAssetsHardwareAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
					invAssetsHardwareAPIDataES.setMgmtSystemId(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemId").toString());
					invAssetsHardwareAPIDataES.setMgmtSystemAddr(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemAddr").toString());
					invAssetsHardwareAPIDataES.setMgmtSystemHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemHostname").toString());
					invAssetsHardwareAPIDataES.setMgmtSystemType(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemType").toString());
					invAssetsHardwareAPIDataES.setSourceNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.sourceNeId").toString());
					invAssetsHardwareAPIDataES.setContainingHwId(responseES.jsonPath().get("hits.hits[" + n + "]._source.containingHwId"));
					invAssetsHardwareAPIDataES.setIsCollector(responseES.jsonPath().get("hits.hits[" + n + "]._source.isCollector").toString());
	       			invAssetsHardwareAPIDataMapES.put(key, invAssetsHardwareAPIDataES);

				}

			}
			System.out.println("invAssetsHardwareAPIDataMapES:"+ invAssetsHardwareAPIDataMapES);
			System.out.println("invAssetsHardwareAPIDataMapES Size:"+ invAssetsHardwareAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyname: invAssetsHardwareAPIDataMapES.keySet()) {
				if(invAssetsHardwareAPIDataMap.containsKey(keyname)) {
					softAssert.assertEquals(invAssetsHardwareAPIDataMap.get(keyname).toString(),
							invAssetsHardwareAPIDataMapES.get(keyname).toString(),
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
