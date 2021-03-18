package com.cisco.services.api_automation.tests.assets.alerts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.AlertsSecurityAdvisoriesAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Product Alerts APIs")
public class AlertsSecurityAdvisories extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String  apiKey="product_alerts_security_advisories";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}


	@Test(description = "/product-alerts/v1/security-advisories API 200 Response Validation")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/product-alerts/v1/security-advisories API Records Count Validation with ES")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/product-alerts/v1/security-advisories API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__product_alerts_security_advisories( String optionalParam) {
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

	@Test(dependsOnMethods = { "apiRecordsCountValidation" },description = "AlertSecurityAdvisories API Data Validation with ES")
	public void apiDataValidation() {
		System.out.println("****************** AlertSecurityAdvisories API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, AlertsSecurityAdvisoriesAPIPojo> alertSecAdvAPIDataMap=new HashMap<String, AlertsSecurityAdvisoriesAPIPojo>();
		Map<String, AlertsSecurityAdvisoriesAPIPojo> alertSecAdvAPIDataMapES=new HashMap<String, AlertsSecurityAdvisoriesAPIPojo>();
		AlertsSecurityAdvisoriesAPIPojo alertSecAdvAPIData;
		AlertsSecurityAdvisoriesAPIPojo alertSecAdvAPIDataES;
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
					alertSecAdvAPIData = new AlertsSecurityAdvisoriesAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].neInstanceId")+"_"+responseAPI.jsonPath().get("data[" + n + "].advisoryId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					alertSecAdvAPIData.setIpAddress(responseAPI.jsonPath().get("data[" + n + "].ipAddress").toString());
					alertSecAdvAPIData.setManagedNeId(responseAPI.jsonPath().get("data[" + n + "].managedNeId").toString());
	//				alertSecAdvAPIData.setHwInstanceId(responseAPI.jsonPath().get("data[" + n + "].hwInstanceId").toString())
					alertSecAdvAPIData.setHostname(responseAPI.jsonPath().get("data[" + n + "].hostname").toString());
	//				alertSecAdvAPIData.setSupportCovered(responseAPI.jsonPath().get("data[" + n + "].supportCovered").toString());
					alertSecAdvAPIData.setEquipmentType(responseAPI.jsonPath().get("data[" + n + "].equipmentType").toString());
					alertSecAdvAPIData.setProductFamily(responseAPI.jsonPath().get("data[" + n + "].productFamily").toString());
	//				alertSecAdvAPIData.setProductType(responseAPI.jsonPath().get("data[" + n + "].productType").toString());
					alertSecAdvAPIData.setProductId(responseAPI.jsonPath().get("data[" + n + "].productId").toString());
	     			alertSecAdvAPIData.setSwType(responseAPI.jsonPath().get("data[" + n + "].swType").toString());
					alertSecAdvAPIData.setSwVersion(responseAPI.jsonPath().get("data[" + n + "].swVersion").toString());
	//				alertSecAdvAPIData.setSerialNumber(responseAPI.jsonPath().get("data[" + n + "].serialNumber").toString());
					alertSecAdvAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					alertSecAdvAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
	//				alertSecAdvAPIData.setBulletinName(responseAPI.jsonPath().get("data[" + n + "].bulletinName").toString());
					alertSecAdvAPIData.setVulnerabilityStatus(responseAPI.jsonPath().get("data[" + n + "].vulnerabilityStatus").toString());
					alertSecAdvAPIData.setVulnerabilityReason(responseAPI.jsonPath().get("data[" + n + "].vulnerabilityReason").toString());
					alertSecAdvAPIData.setPublicReleaseIndicator(responseAPI.jsonPath().get("data[" + n + "].publicReleaseIndicator").toString());
					alertSecAdvAPIDataMap.put(key, alertSecAdvAPIData);

				}
			}
			System.out.println("alertSecAdvAPIDataMap Size:"+ alertSecAdvAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					alertSecAdvAPIDataES = new AlertsSecurityAdvisoriesAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.neId")+"_"+responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.psirtId");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					alertSecAdvAPIDataES.setIpAddress(responseES.jsonPath().get("hits.hits[" + n + "]._source.ipAddress").toString());
		     		alertSecAdvAPIDataES.setManagedNeId(responseES.jsonPath().get("hits.hits[" + n + "]._source.managedNeId").toString());
	//	     		alertSecAdvAPIDataES.setHwInstanceId(responseES.jsonPath().get("hits.hits[" + n + "]._source.hwInstanceId").toString());
					alertSecAdvAPIDataES.setHostname(responseES.jsonPath().get("hits.hits[" + n + "]._source.hostname").toString());
	 //           	alertSecAdvAPIDataES.setSupportCovered(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.supportCovered").toString());
					alertSecAdvAPIDataES.setEquipmentType(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.equipmentType").toString());
					alertSecAdvAPIDataES.setProductFamily(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.productFamily").toString());
	//				alertSecAdvAPIDataES.setProductType(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.productType").toString());
					alertSecAdvAPIDataES.setProductId(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.productId").toString());
					alertSecAdvAPIDataES.setSwType(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.softwareType").toString());
					alertSecAdvAPIDataES.setSwVersion(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.softwareVersion").toString());
	//				alertSecAdvAPIDataES.setSerialNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.serialNumber").toString());
					alertSecAdvAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					alertSecAdvAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
	//				alertSecAdvAPIDataES.setBulletinName(responseES.jsonPath().get("hits.hits[" + n + "]._source.bulletinName").toString());
					alertSecAdvAPIDataES.setVulnerabilityStatus(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.vulnerabilityStatus").toString());
					alertSecAdvAPIDataES.setVulnerabilityReason(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.vulnerabilityReason").toString());
					alertSecAdvAPIDataES.setPublicReleaseIndicator(responseES.jsonPath().get("hits.hits[" + n + "]._source.advisory.publicReleaseInd").toString());
					alertSecAdvAPIDataMapES.put(key, alertSecAdvAPIDataES);

				}

			}
			System.out.println("alertSecAdvAPIDataMapES Size:"+ alertSecAdvAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyName: alertSecAdvAPIDataMapES.keySet()) {
				if(alertSecAdvAPIDataMap.containsKey(keyName)) {
					softAssert.assertEquals(alertSecAdvAPIDataMap.get(keyName).toString(), alertSecAdvAPIDataMapES.get(keyName).toString(), "API Data not matched with ES Data for Key: " + keyName);
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
