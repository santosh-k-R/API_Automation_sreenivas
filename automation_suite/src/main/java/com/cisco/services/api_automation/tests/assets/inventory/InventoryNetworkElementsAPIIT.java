package com.cisco.services.api_automation.tests.assets.inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.InventoryNetworkElementsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Inventory APIs")
public class InventoryNetworkElementsAPIIT extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="inventory_network_elements";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}

	@Test(description = "/inventory/v1/network-elements API 200 Response Validation")
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

//	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/inventory/v1/network-elements Records Count Validation with ES")
//	public void apiRecordsCountValidation( ) {
//		System.out.println("****************** Records Count Validation with ES for API "+endPoint);
//		APIRecordsCountPojo apiRecordsCount= new APIRecordsCountPojo();
//		try { 
//			apiRecordsCount=CommonTestAcrossAPIsIT.recordsCount(apiKey, responseAPI);
//			if (apiRecordsCount!=null)
//			{
//			responseES = apiRecordsCount.getResponseES();
//			
//				softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),apiRecordsCount.getCountOfRecordsFromES(),"Count of API Not Matched with ES Index ");
//			}
//			 else
//				softAssert.assertFalse(true,"Unable to fetch records from ES ");
//
//			softAssert.assertAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
//
//		}
//
//
//	}
//
//	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "Network Elements API Data Validation with ES")
//	public void apiDataValidation() {
//		System.out.println("****************** Network Elements API Data Validation with ES");
//		int pages=0;
//		int recordCount=0;
//		int rows=100;
//		Map<String, InventoryNetworkElementsAPIPojo> invNEAPIDataMap=new HashMap<String, InventoryNetworkElementsAPIPojo>();
//		Map<String, InventoryNetworkElementsAPIPojo> invNEAPIDataMapES=new HashMap<String, InventoryNetworkElementsAPIPojo>();
//		InventoryNetworkElementsAPIPojo invNEAPIData;
//		InventoryNetworkElementsAPIPojo invNEAPIDataES;
//		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
//		queryParaMap.put("customerId", customerId);
//		queryParaMap.put("rows",String.valueOf(rows));
//		String key=null;
//
//		try {
//			/** Running the API for each page and creating collection  */
//			pages=responseAPI.jsonPath().get("Pagination.pages");
//			System.out.println("Number of Pages in NE API Response  ---> "+pages);
//			for (int pno=1;pno<=pages;pno++) {
//				System.out.println("<---------------------Running NE API for page number "+pno+ " out of "+pages+" --------------------------->" );
//				queryParaMap.put("page",String.valueOf(pno) );
//				System.out.println("queryParaMap--> "+queryParaMap);
//				responseAPI = RestUtils.get(endPoint, queryParaMap,headers);
//				recordCount=responseAPI.jsonPath().getList("data").size();
////				System.out.println("responseAPI Body --->" + responseAPI.asString());
//				System.out.println("API Record Count --->  "+ recordCount);
//				for (int n=0;n<recordCount;n++) {
//					invNEAPIData = new InventoryNetworkElementsAPIPojo();
//					key=responseAPI.jsonPath().get("data[" + n + "].neInstanceId");
////					System.out.println("key: "+key);
////					System.out.println("n: "+n);
//					invNEAPIData.setHostName(responseAPI.jsonPath().get("data[" + n + "].hostName").toString());
//					invNEAPIData.setCollectorId(responseAPI.jsonPath().get("data[" + n + "].collectorId").toString());
//					invNEAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
//					invNEAPIData.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
//					invNEAPIData.setNeName(responseAPI.jsonPath().get("data[" + n + "].neName").toString());
//					invNEAPIData.setManagementAddress(responseAPI.jsonPath().get("data[" + n + "].managementAddress").toString());
//					invNEAPIData.setNeRegistrationStatus(responseAPI.jsonPath().get("data[" + n + "].neRegistrationStatus").toString());
//					invNEAPIData.setIsManagedNE(responseAPI.jsonPath().get("data[" + n + "].isManagedNE").toString());
////					invNEAPIData.setLastUpdateDate(responseAPI.jsonPath().get("data[" + n + "].lastUpdateDate").toString());
//					invNEAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
//					invNEAPIData.setProductType(responseAPI.jsonPath().get("data[" + n + "].productType").toString());
//					invNEAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
//					invNEAPIData.setSwType(responseAPI.jsonPath().get("data[" + n + "].swType").toString());
//					invNEAPIData.setSwVersion(responseAPI.jsonPath().get("data[" + n + "].swVersion").toString());
//					invNEAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
////					invNEAPIData.setSystemUptime(responseAPI.jsonPath().get("data[" + n + "].systemUptime"));
//					invNEAPIData.setUdiProductIdentifier(responseAPI.jsonPath().get("data[" + n + "].udiProductIdentifier").toString());
//					invNEAPIData.setSmartLicenseProductInstanceIdentifier(responseAPI.jsonPath().get("data[" + n + "].smartLicenseProductInstanceIdentifier").toString());
//					invNEAPIData.setSmartLicenseVirtualAccountName(responseAPI.jsonPath().get("data[" + n + "].smartLicenseVirtualAccountName").toString());
//					invNEAPIData.setInstalledMemory(responseAPI.jsonPath().get("data[" + n + "].installedMemory").toString());
//					invNEAPIData.setTimeOfLastReset(responseAPI.jsonPath().get("data[" + n + "].timeOfLastReset").toString());
//					invNEAPIData.setLastResetReason(responseAPI.jsonPath().get("data[" + n + "].lastResetReason").toString());
//					invNEAPIData.setSysObjectId(responseAPI.jsonPath().get("data[" + n + "].sysObjectId").toString());
//					invNEAPIData.setImageName(responseAPI.jsonPath().get("data[" + n + "].imageName").toString());
//					invNEAPIData.setWfId(responseAPI.jsonPath().get("data[" + n + "].wfId").toString());
//					//invNEAPIData.setSolutionInfo(responseAPI.jsonPath().get("data[" + n + "].solutionInfo"));
//					invNEAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
//					//invNEAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId"));
//					invNEAPIData.setIsScanCapable(responseAPI.jsonPath().get("data[" + n + "].isScanCapable").toString());
//					invNEAPIData.setMgmtSystemId(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemId").toString());
//					invNEAPIData.setMgmtSystemAddr(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemAddr"));
//					invNEAPIData.setMgmtSystemHostname(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemHostname").toString());
//					invNEAPIData.setMgmtSystemType(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemType").toString());
//					invNEAPIData.setIsCollector(responseAPI.jsonPath().get("data[" + n + "].isCollector").toString());
////					invNEAPIData.setCollectorType(responseAPI.jsonPath().get("data[" + n + "].collectorType").toString());
//					invNEAPIDataMap.put(key, invNEAPIData);
//
//				}
//			}
//			System.out.println("invNEAPIDataMap Size:"+ invNEAPIDataMap.size());
//
//			/** Parsing ES Response and creating collection  */
////			System.out.println("responseES Body --->" + responseES.asString());
//			if (responseES.getStatusCode() == 200) {
//				int recordCountES=responseES.jsonPath().get("hits.total.value");;
//				for (int n=0;n<recordCountES;n++) {
//					invNEAPIDataES = new InventoryNetworkElementsAPIPojo();
//					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.neId");
////					System.out.println("key: "+key);
////					System.out.println("n: "+n);
//					invNEAPIDataES.setHostName(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.hostname").toString());
//					invNEAPIDataES.setCollectorId(responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorId").toString());
//					invNEAPIDataES.setIpAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.ipAddress").toString());
//					invNEAPIDataES.setManagedNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
//					invNEAPIDataES.setNeName(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.neName").toString());
//					invNEAPIDataES.setManagementAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.managementAddress").toString());
//					invNEAPIDataES.setNeRegistrationStatus(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.neRegistrationStatus").toString());
//					invNEAPIDataES.setIsManagedNE(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.isManagedNe").toString());
////					invNEAPIDataES.setLastUpdateDate(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.lastUpdateDate").toString());
//					invNEAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.productFamily").toString());
//					invNEAPIDataES.setProductType(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.productType").toString());
//					invNEAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.productId").toString());
//					//invNEAPIDataES.setTags(responseES.jsonPath().get("hits.hits[" + n + "]._source.tags"));
//					invNEAPIDataES.setSwType(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.softwareType").toString());
//					invNEAPIDataES.setSwVersion(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.softwareVersion").toString());
//					invNEAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.serialNumber").toString());
////					invNEAPIDataES.setSystemUptime(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.systemUptime").toString());
//					invNEAPIDataES.setUdiProductIdentifier(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.udiProductIdentifier").toString());
//					invNEAPIDataES.setSmartLicenseProductInstanceIdentifier(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.smartLicenseProductInstanceIdentifier").toString());
//					invNEAPIDataES.setSmartLicenseVirtualAccountName(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.smartLicenseVirtualAccountName").toString());
//					invNEAPIDataES.setInstalledMemory(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.installedMemory").toString());
//					invNEAPIDataES.setTimeOfLastReset(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.timeOfLastReset").toString());
//					invNEAPIDataES.setLastResetReason(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.lastResetReason").toString());
//					invNEAPIDataES.setSysObjectId(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.sysObjectId").toString());
//					invNEAPIDataES.setImageName(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.imageName").toString());
//					invNEAPIDataES.setWfId(responseES.jsonPath().get("hits.hits[" + n + "]._source.wfid").toString());
//					//invNEAPIDataES.setCollectorId(responseES.jsonPath().get("hits.hits[" + n + "]._source.solutionInfo"));
//					invNEAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
//					//invNEAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId"));
//					invNEAPIDataES.setIsScanCapable(responseES.jsonPath().get("hits.hits[" + n + "]._source.NetworkElement.isScanCapable").toString());
//					invNEAPIDataES.setMgmtSystemId(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemId").toString());
//					invNEAPIDataES.setMgmtSystemAddr(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemAddr").toString());
//					invNEAPIDataES.setMgmtSystemHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemHostname").toString());
//					invNEAPIDataES.setMgmtSystemType(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemType").toString());
//					invNEAPIDataES.setIsCollector(responseES.jsonPath().get("hits.hits[" + n + "]._source.isCollector").toString());
//					//invNEAPIDataES.setCollectorType(responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorType"));
//					invNEAPIDataMapES.put(key, invNEAPIDataES);
//
//				}
//
//			}
//			System.out.println("invNEAPIDataMapES Size:"+ invNEAPIDataMapES.size());
//
//			/** Assertions if each elements in the API Collection and ES Collection are matching  */
//			for(String ne: invNEAPIDataMapES.keySet()) {
//				if(invNEAPIDataMap.containsKey(ne)) {
//					softAssert.assertEquals(invNEAPIDataMap.get(ne).toString(), invNEAPIDataMapES.get(ne).toString(), "API Data not matched with ES Data for NE: " + ne);
//				}
//				else {
//					softAssert.assertFalse(true,"API response doesn't contain : "+ne);
//				}
//			}
//			softAssert.assertAll();
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
//
//		}
//	}
//	
//	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/inventory/v1/network-elements API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
//	public void apiOptionalParamsValidation__inventory_network_elements( String optionalParam) {
//		System.out.println("****************** Optional Params Validation for API "+ endPoint + " for Param "+ optionalParam);
//		SoftAssert softAssert = new SoftAssert();
//		if(responseAPI.jsonPath().get("data[0]."+optionalParam)==null) {
//			Assert.assertFalse(true,"Unable to test this param as first value in the response is coming null" );
//		}
//		String optionalParamValue=responseAPI.jsonPath().get("data[0]."+optionalParam).toString();
//		APIRecordsCountPojo apiRecordsCount= new APIRecordsCountPojo();
//		apiRecordsCount=CommonTestAcrossAPIsIT.recordsCountWithOptionalParam(apiKey,optionalParam,optionalParamValue);
//
//		try {
//			
//			
//			if (apiRecordsCount.getResponseAPI().getStatusCode()==200) {
//				
//				if (apiRecordsCount.getResponseES().getStatusCode() == 200) {
//					
//					softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),apiRecordsCount.getCountOfRecordsFromES(),"Count of API Not Matched with ES Index ");
//				}
//				else {
//					softAssert.assertEquals(responseES.getStatusCode(),Integer.parseInt(expectedStatusCode),"Unable to fetch records from ES ");
//				}
//				
//			}
//			else {
//				softAssert.assertEquals(responseAPI.getStatusCode(),Integer.parseInt(expectedStatusCode),"API not working for the query Param "+ optionalParam);
//			}
//			
//			
//			softAssert.assertAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
//		}
//		
//
//
//	}

}
