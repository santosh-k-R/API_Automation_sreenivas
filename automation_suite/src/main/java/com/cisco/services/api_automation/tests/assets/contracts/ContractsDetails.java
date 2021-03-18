package com.cisco.services.api_automation.tests.assets.contracts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.ContractdetailsAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.tests.assets.BeforeTestSuiteClassIT;
import com.cisco.services.api_automation.tests.assets.CommonTestAcrossAPIsIT;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Assets Contracts APIs")
public class ContractsDetails extends BeforeTestSuiteClassIT {

	Response responseAPI = null;
	Response responseES = null;
	Response preReqApiResponse = null;
	Boolean preRequisiteAPIRan = false;
	SoftAssert softAssert = new SoftAssert();
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;
	String apiKey="contracts_details";
	String endPoint;
	String esIndex;

	@BeforeClass
	public void setup() {
		endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);

	}

	@Test(description = "/contracts/v1/details API 200 Response Validation")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "/contracts/v1/details API Records Count Validation with ES")
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

	@Test(dependsOnMethods = { "api200ResponseValidation" }, description = "/contracts/v1/details API Optional Params Validation", dataProvider = "GetOptionalParamsList", dataProviderClass = AssetsData.class)
	public void apiOptionalParamsValidation__contracts_details( String optionalParam) {
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

	@Test(dependsOnMethods = { "api200ResponseValidation" },description = "Contractdetails API Data Validation with ES",priority=3)
	public void apiDataValidation() {
		System.out.println("****************** Contractdetails API Data Validation with ES");
		int pages=0;
		int recordCount=0;
		int rows=100;
		Map<String, ContractdetailsAPIPojo> contractsDetailsAPIDataMap=new HashMap<String, ContractdetailsAPIPojo>();
		Map<String, ContractdetailsAPIPojo> contractsDetailsAPIDataMapES=new HashMap<String, ContractdetailsAPIPojo>();
		ContractdetailsAPIPojo contractsDetailsAPIData;
		ContractdetailsAPIPojo contractsDetailsAPIDataES;
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
					contractsDetailsAPIData = new ContractdetailsAPIPojo();
					key=responseAPI.jsonPath().get("data[" + n + "].serialNumber");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					if(responseAPI.jsonPath().get("data[" + n + "].contractNumber")==null)
						contractsDetailsAPIData.setContractNumber("");
					else
						contractsDetailsAPIData.setContractNumber(responseAPI.jsonPath().get("data[" + n + "].contractNumber").toString());
					contractsDetailsAPIData.setContractStatus(responseAPI.jsonPath().get("data[" + n + "].contractStatus").toString());
//					contractsDetailsAPIData.setContractStartDate(responseAPI.jsonPath().get("data[" + n + "].contractStartDate").toString());
//					contractsDetailsAPIData.setContractEndDate(responseAPI.jsonPath().get("data[" + n + "].contractEndDate").toString());
					contractsDetailsAPIData.setServiceProgram(responseAPI.jsonPath().get("data[" + n + "].serviceProgram").toString());
	//				contractsDetailsAPIData.setServiceLevel(responseAPI.jsonPath().get("data[" + n + "].serviceLevel").toString());
	//				contractsDetailsAPIData.setBilltoSiteId(responseAPI.jsonPath().get("data[" + n + "].billtoSiteId"));
					contractsDetailsAPIData.setBilltoSiteName(responseAPI.jsonPath().get("data[" + n + "].billtoSiteName").toString());
					contractsDetailsAPIData.setBilltoAddressLine1(responseAPI.jsonPath().get("data[" + n + "].billtoAddressLine1"));
					contractsDetailsAPIData.setBilltoAddressLine2(responseAPI.jsonPath().get("data[" + n + "].billtoAddressLine2"));
	//				contractsDetailsAPIData.setBilltoAddressLine3(responseAPI.jsonPath().get("data[" + n + "].billtoAddressLine3").toString());
	//				contractsDetailsAPIData.setBilltoAddressLine4(responseAPI.jsonPath().get("data[" + n + "].billtoAddressLine4").toString());
					contractsDetailsAPIData.setBilltoCity(responseAPI.jsonPath().get("data[" + n + "].billtoCity").toString());
					contractsDetailsAPIData.setCxLevel(responseAPI.jsonPath().get("data[" + n + "].cxLevel").toString());
					contractsDetailsAPIData.setSaId(responseAPI.jsonPath().get("data[" + n + "].saId").toString());
					contractsDetailsAPIData.setBilltoState(responseAPI.jsonPath().get("data[" + n + "].billtoState").toString());
					contractsDetailsAPIData.setBilltoPostalCode(responseAPI.jsonPath().get("data[" + n + "].billtoPostalCode").toString());
					contractsDetailsAPIData.setBilltoProvince(responseAPI.jsonPath().get("data[" + n + "].billtoProvince").toString());
					contractsDetailsAPIData.setBilltoCountry(responseAPI.jsonPath().get("data[" + n + "].billtoCountry").toString());
	//				contractsDetailsAPIData.setBillToGuName(responseAPI.jsonPath().get("data[" + n + "].billToGuName").toString());
					contractsDetailsAPIDataMap.put(key, contractsDetailsAPIData);

				}
			}
			System.out.println("contractsDetailsAPIDataMap Size:"+ contractsDetailsAPIDataMap.size());

			/** Parsing ES Response and creating collection  */
//			System.out.println("responseES Body --->" + responseES.asString());
			if (responseES.getStatusCode() == 200) {
				int recordCountES=responseES.jsonPath().get("hits.total.value");;
				for (int n=0;n<recordCountES;n++) {
					contractsDetailsAPIDataES = new ContractdetailsAPIPojo();
					key=responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.serialNumber");
//					System.out.println("key: "+key);
//					System.out.println("n: "+n);
					if(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.contractNumber")==null)
						contractsDetailsAPIDataES.setContractNumber("");
					else
					contractsDetailsAPIDataES.setContractNumber(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.contractNumber").toString());
					contractsDetailsAPIDataES.setContractStatus(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.contractStatus").toString());
//					contractsDetailsAPIDataES.setContractStartDate(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.contractStartDate").toString());
//					contractsDetailsAPIDataES.setContractEndDate(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.contractEndDate").toString());
					contractsDetailsAPIDataES.setServiceProgram(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.serviceProgram").toString());
		//			contractsDetailsAPIDataES.setServiceLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.serviceLevel").toString());
		//			contractsDetailsAPIDataES.setBilltoSiteId(responseES.jsonPath().get("hits.hits[" + n + "]._source.billtoSiteId").toString());
					contractsDetailsAPIDataES.setBilltoSiteName(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToSiteName"));
					contractsDetailsAPIDataES.setBilltoAddressLine1(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToAddress1"));
					contractsDetailsAPIDataES.setBilltoAddressLine2(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToAddress2"));
	//				contractsDetailsAPIDataES.setBilltoAddressLine3(responseES.jsonPath().get("hits.hits[" + n + "]._source.billtoAddressLine3").toString());
	//				contractsDetailsAPIDataES.setBilltoAddressLine4(responseES.jsonPath().get("hits.hits[" + n + "]._source.billtoAddressLine4").toString());
					contractsDetailsAPIDataES.setBilltoCity(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToCity").toString());
					contractsDetailsAPIDataES.setBilltoState(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToState").toString());
					contractsDetailsAPIDataES.setBilltoPostalCode(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToPostalCode").toString());
					contractsDetailsAPIDataES.setBilltoProvince(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToProvince").toString());
					contractsDetailsAPIDataES.setBilltoCountry(responseES.jsonPath().get("hits.hits[" + n + "]._source.coverageList.billToCountry").toString());
	//				contractsDetailsAPIDataES.setBillToGuName(responseES.jsonPath().get("hits.hits[" + n + "]._source.billToGuName").toString());
					contractsDetailsAPIDataES.setCxLevel(responseES.jsonPath().get("hits.hits[" + n + "]._source.cxLevel").toString());
					contractsDetailsAPIDataES.setSaId(responseES.jsonPath().get("hits.hits[" + n + "]._source.saId").toString());
					contractsDetailsAPIDataMapES.put(key, contractsDetailsAPIDataES);


				}

			}
			System.out.println("contractsDetailsAPIDataMapES Size:"+ contractsDetailsAPIDataMapES.size());

			/** Assertions if each elements in the API Collection and ES Collection are matching  */
			for(String keyName: contractsDetailsAPIDataMapES.keySet()) {
				if(contractsDetailsAPIDataMap.containsKey(keyName)) {
					softAssert.assertEquals(contractsDetailsAPIDataMap.get(keyName).toString(), contractsDetailsAPIDataMapES.get(keyName).toString(), "API Data not matched with ES Data for Key: "+keyName);

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
