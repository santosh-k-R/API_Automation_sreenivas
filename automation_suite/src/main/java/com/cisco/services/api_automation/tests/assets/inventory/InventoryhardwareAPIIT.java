package com.cisco.services.api_automation.tests.assets.inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.InventoryhardwareAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Inventory APIs")
public class InventoryhardwareAPIIT extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
//	String customerId = AssetsData.CUSTOMERID;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="inventory_hardware";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}
	


	@Test(description = "/inventory/v1/hardware API 200 Response Validation")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/inventory/v1/hardware API Records Count Validation with ES")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/inventory/v1/hardware API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__inventory_hardware( String optionalParam) {
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
	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "Inventoryharware API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** Inventoryharware API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, InventoryhardwareAPIPojo> invhardwareAPIDataMap=new HashMap<String, InventoryhardwareAPIPojo>();
		Map<String, InventoryhardwareAPIPojo> invhardwareAPIDataMapES=new HashMap<String, InventoryhardwareAPIPojo>();
		InventoryhardwareAPIPojo invhardwareAPIData;
		InventoryhardwareAPIPojo invhardwareAPIDataES;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put("rows",String.valueOf(rows));
		String key=null;

		try {
			/** Running the API for each page and creating collection  */
			pages=responseAPI.jsonPath().get("Pagination.pages");
			System.out.println("Number of Pages in Inventoryhardware API Response  ---> "+pages);
			for (int pno=1;pno<=pages;pno++) {
				System.out.println("<---------------------Running NE API for page number "+pno+ " out of "+pages+" --------------------------->" );
				queryParaMap.put("page",String.valueOf(pno) );
				System.out.println("queryParaMap--> "+queryParaMap);
				responseAPI = RestUtils.get(endPoint, queryParaMap,headers);
				recordCount=responseAPI.jsonPath().getList("data").size();
//				System.out.println("responseAPI Body --->" + responseAPI.asString());
				System.out.println("API Record Count --->  "+ recordCount);
				for (int n=0;n<recordCount;n++) {
					invhardwareAPIData = new InventoryhardwareAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].managedNeId")+"_"+responseAPI.jsonPath().get("data[" + n + "].hwInstanceId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invhardwareAPIData.setHostname(responseAPI.jsonPath().get("data[" + n + "].hostname").toString());
					invhardwareAPIData.setCollectorId(responseAPI.jsonPath().get("data[" + n + "].collectorId").toString());
					invhardwareAPIData.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
					invhardwareAPIData.setManagementAddress(responseAPI.jsonPath().get("data[" + n + "].managementAddress").toString());
					invhardwareAPIData.setContainingHwId(responseAPI.jsonPath().get("data[" + n + "].containingHwId"));
					invhardwareAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
					invhardwareAPIData.setProductType(responseAPI.jsonPath().get("data[" + n + "].productType").toString());
					invhardwareAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
					invhardwareAPIData.setProductName(responseAPI.jsonPath().get("data[" + n + "].productName"));
					invhardwareAPIData.setEquipmentType(responseAPI.jsonPath().get("data[" + n + "].equipmentType").toString());
					invhardwareAPIData.setProductDescription(responseAPI.jsonPath().get("data[" + n + "].productDescription").toString());
					invhardwareAPIData.setSwType(responseAPI.jsonPath().get("data[" + n + "].swType").toString());
					invhardwareAPIData.setSwVersion(responseAPI.jsonPath().get("data[" + n + "].swVersion").toString());
					invhardwareAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					invhardwareAPIData.setWfId(responseAPI.jsonPath().get("data[" + n + "].wfId").toString());
					invhardwareAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					invhardwareAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId"));
					invhardwareAPIData.setMgmtSystemId(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemId").toString());
					invhardwareAPIData.setMgmtSystemAddr(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemAddr").toString());
					invhardwareAPIData.setMgmtSystemHostname(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemHostname").toString());
					invhardwareAPIData.setMgmtSystemType(responseAPI.jsonPath().get("data[" + n + "].mgmtSystemType").toString());
					invhardwareAPIData.setIsCollector(responseAPI.jsonPath().get("data[" + n + "].isCollector").toString());
					invhardwareAPIData.setSourceNeId(responseAPI.jsonPath().get("data[" + n + "].sourceNeId").toString());
		//			invhardwareAPIData.setTags(responseAPI.jsonPath().get("data[" + n + "].tags").toString());
					invhardwareAPIDataMap.put(key, invhardwareAPIData);

				}
			}
			System.out.println("invhardwareAPIDataMap:"+ invhardwareAPIDataMap);
			System.out.println("invhardwareAPIDataMap Size:"+ invhardwareAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					invhardwareAPIDataES = new InventoryhardwareAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.neId")+"_"+responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.hwId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					invhardwareAPIDataES.setHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.hostname").toString());
					invhardwareAPIDataES.setCollectorId(responseES.jsonPath().get("hits.hits[" + n + "]._source.collectorId").toString());
					invhardwareAPIDataES.setManagedNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
					invhardwareAPIDataES.setManagementAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.managementAddress").toString());
					invhardwareAPIDataES.setContainingHwId(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.containingHwId"));
					invhardwareAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.productFamily").toString());
					invhardwareAPIDataES.setProductType(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.productType").toString());
					invhardwareAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.productId").toString());
					invhardwareAPIDataES.setProductName(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.productName"));
					invhardwareAPIDataES.setEquipmentType(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.equipmentType"));
					invhardwareAPIDataES.setProductDescription(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.productDescription"));
					invhardwareAPIDataES.setSwType(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.softwareType").toString());
					invhardwareAPIDataES.setSwVersion(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.softwareVersion").toString());
					invhardwareAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.serialNumber").toString());
					invhardwareAPIDataES.setWfId(responseES.jsonPath().get("hits.hits[" + n + "]._source.wfid").toString());
					invhardwareAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					invhardwareAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId"));
					invhardwareAPIDataES.setMgmtSystemId(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemId").toString());
					invhardwareAPIDataES.setMgmtSystemAddr(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemAddr").toString());
					invhardwareAPIDataES.setMgmtSystemHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemHostname").toString());
					invhardwareAPIDataES.setMgmtSystemType(responseES.jsonPath().get("hits.hits[" + n + "]._source.mgmtSystemType").toString());
					invhardwareAPIDataES.setIsCollector(responseES.jsonPath().get("hits.hits[" + n + "]._source.isCollector").toString());
					invhardwareAPIDataES.setSourceNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.sourceNeId"));
//					invhardwareAPIDataES.setTags(responseES.jsonPath().get("hits.hits[" + n + "]._source.Equipment.tags"));
					invhardwareAPIDataMapES.put(key, invhardwareAPIDataES);

				}

			}
			System.out.println("invhardwareAPIDataMapES:"+ invhardwareAPIDataMapES);
			System.out.println("invhardwareAPIDataMapES Size:"+ invhardwareAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyname: invhardwareAPIDataMapES.keySet()) {
				if(invhardwareAPIDataMap.containsKey(keyname)) {
					softAssert.assertEquals(invhardwareAPIDataMap.get(keyname).toString(),
							invhardwareAPIDataMapES.get(keyname).toString(),
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
