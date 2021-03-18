package com.cisco.services.api_automation.tests.assets.alerts;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.AlertsFieldNoticesAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Feature("Assets Product Alerts APIs")
public class AlertsFieldNotices extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="product_alerts_field_notices";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}


	@Test(description = "/product-alerts/v1/field-notices API 200 Response Validation")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/product-alerts/v1/field-notices API Records Count Validation with ES")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/product-alerts/v1/field-notices API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__product_alerts_field_notices( String optionalParam) {
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
	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "AlertsFieldNotices API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** AlertsFieldNotices API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, AlertsFieldNoticesAPIPojo> alertFieldNotAPIDataMap=new HashMap<String, AlertsFieldNoticesAPIPojo>();
		Map<String, AlertsFieldNoticesAPIPojo> alertFieldNotAPIDataMapES=new HashMap<String, AlertsFieldNoticesAPIPojo>();
		AlertsFieldNoticesAPIPojo alertFieldNotAPIData;
		AlertsFieldNoticesAPIPojo alertFieldNotAPIDataES;
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
					alertFieldNotAPIData = new AlertsFieldNoticesAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].neInstanceId")+"_"+responseAPI.jsonPath().get("data[" + n + "].hwInstanceId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					alertFieldNotAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
					alertFieldNotAPIData.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
					alertFieldNotAPIData.setHostname(responseAPI.jsonPath().get("data[" + n + "].hostname").toString());
					alertFieldNotAPIData.setEquipmentType(responseAPI.jsonPath().get("data[" + n + "].equipmentType").toString());
					alertFieldNotAPIData.setFieldNoticeId(responseAPI.jsonPath().get("data[" + n + "].fieldNoticeId").toString());
					alertFieldNotAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
					alertFieldNotAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
	     			alertFieldNotAPIData.setSwType(responseAPI.jsonPath().get("data[" + n + "].swType").toString());
					alertFieldNotAPIData.setSwVersion(responseAPI.jsonPath().get("data[" + n + "].swVersion").toString());
					alertFieldNotAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					alertFieldNotAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					alertFieldNotAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
					alertFieldNotAPIData.setCaveat(responseAPI.jsonPath().get("data[" + n + "].caveat").toString());
//					alertFieldNotAPIData.setBulletinTitle(responseAPI.jsonPath().get("data[" + n + "].bulletinTitle").toString());
					alertFieldNotAPIData.setVulnerabilityStatus(responseAPI.jsonPath().get("data[" + n + "].vulnerabilityStatus").toString());
					alertFieldNotAPIData.setVulnerabilityReason(responseAPI.jsonPath().get("data[" + n + "].vulnerabilityReason").toString());
					alertFieldNotAPIData.setDistributionCode(responseAPI.jsonPath().get("data[" + n + "].distributionCode").toString());
					alertFieldNotAPIDataMap.put(key, alertFieldNotAPIData);

				}
			}
			System.out.println("alertFieldNotAPIDataMap Size:"+ alertFieldNotAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					alertFieldNotAPIDataES = new AlertsFieldNoticesAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.neId")+"_"+responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.hwId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					alertFieldNotAPIDataES.setIpAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.ipAddress").toString());
		     		alertFieldNotAPIDataES.setManagedNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
					alertFieldNotAPIDataES.setHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.hostname").toString());
					alertFieldNotAPIDataES.setEquipmentType(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.equipmentType").toString());
					alertFieldNotAPIDataES.setFieldNoticeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.fieldNoticeId").toString());
					alertFieldNotAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.productFamily").toString());
					alertFieldNotAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.productId").toString());
					alertFieldNotAPIDataES.setSwType(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.softwareType").toString());
					alertFieldNotAPIDataES.setSwVersion(responseES.jsonPath().get("hits.hits[" + n + "]._source.softwareVersion").toString());
					alertFieldNotAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.serialNumber").toString());
					alertFieldNotAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					alertFieldNotAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
					alertFieldNotAPIDataES.setCaveat(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.caveat").toString());
	//				alertFieldNotAPIDataES.setBulletinTitle(responseES.jsonPath().get("hits.hits[" + n + "]._source.bulletinTitle").toString());
					alertFieldNotAPIDataES.setVulnerabilityStatus(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.vulnerabilityStatus").toString());
					alertFieldNotAPIDataES.setVulnerabilityReason(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.vulnerabilityReason").toString());
					alertFieldNotAPIDataES.setDistributionCode(responseES.jsonPath().get("hits.hits[" + n + "]._source.fieldNotice.distributionCode").toString());
					alertFieldNotAPIDataMapES.put(key, alertFieldNotAPIDataES);



				}

			}
			System.out.println("alertFieldNotAPIDataMapES Size:"+ alertFieldNotAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyName: alertFieldNotAPIDataMapES.keySet()) {
				if(alertFieldNotAPIDataMap.containsKey(keyName)) {
					softAssert.assertEquals(alertFieldNotAPIDataMap.get(keyName).toString(), alertFieldNotAPIDataMapES.get(keyName).toString(), "API Data not matched with ES Data for Key: " + keyName);
				}
				else {
					softAssert.assertFalse(true,"API response doesn't contain : "+keyName);
				}
			}
			softAssert.assertAll();


		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}
	}
	

}
