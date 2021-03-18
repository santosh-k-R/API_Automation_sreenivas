package com.cisco.services.api_automation.tests.assets;

import com.cisco.services.api_automation.pojo.response.assets.APIRecordsCountPojo;
import com.cisco.services.api_automation.pojo.response.assets.CountTypeAPIPojo;
import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.utils.DBUtils;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Feature("Assets Common Test Across APIs")
public class CommonTestAcrossAPIsIT extends BeforeTestSuiteClassIT {

	// Response responseAPI = null;
	Response preReqApiResponse = null;
//	static String customerId = AssetsData.CUSTOMERID;
	Boolean preRequisiteAPIRan = false;
	static SoftAssert softAssert = null;
	String expectedStatusCode = "200";
	long expectedResponseTime = 3000;

	public void apiCallToGetMandatoryField() throws Exception {
		String url = "/inventory/v1/assets";
		try {
			Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
			queryParaMap.put("customerId", customerId);
			queryParaMap.put("page", "1");
			queryParaMap.put("rows", "1");
			preReqApiResponse = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + preReqApiResponse.getBody().asString());
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	}

	public void apiCallToGetMandatoryFieldForDeviceTag() throws Exception {
		String url = "/tags/v1/tag-to-device-api/"+customerId;
		try {
			Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
			preReqApiResponse = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + preReqApiResponse.getBody().asString());
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	}

	public void apiCallToGetTagCountFromNE(String deviceId) throws Exception {
		String url = "/inventory/v1/network-elements";
		try {
			Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
			queryParaMap.put("customerId", customerId);
			queryParaMap.put("neInstanceId", deviceId);
			queryParaMap.put("page", "1");
			queryParaMap.put("rows", "1");

			preReqApiResponse = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + preReqApiResponse.getBody().asString());
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	}

	public static Response apiCallToGetNE() throws Exception {
		String url = "/inventory/v1/network-elements";
		try {
			Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
			queryParaMap.put("customerId", customerId);
			queryParaMap.put("isCollector", "false");
			queryParaMap.put("page", "1");
			queryParaMap.put("rows", "1");
			Response response = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + preReqApiResponse.getBody().asString());
			return response;
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

		return null;
	}

	// Sanity Test 200 Check Get Method
	@Test(description = "200 Response Code and Response Time Validation for GET APIs", dataProvider = "GetAPIsEndPointList", dataProviderClass = AssetsData.class)
	public void invokeGetAPIsAndValidateResponseCodeAndResponseTime(String apiKey, String endPoint) throws Exception {
		System.out.println("******************Running Get APIs Test: " + endPoint);
		Response responseAPI = null;
		String url = endPoint;
		softAssert = new SoftAssert();
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_GET_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_GET_APIS.get(apiKey).getMandatoryParams().split(",");
			if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryField();
			}
			if (mandatoryParamList.length > 1) {
				queryParaMap.put(mandatoryParamList[1],
						preReqApiResponse.jsonPath().get("data[0]." + mandatoryParamList[1]));
			}
			System.out.println("queryParaMap-->" + queryParaMap);
			responseAPI = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + responseAPI.getBody().asString());
			System.out.println("Verifying responseApi status code.... " + responseAPI.getStatusCode());
			System.out.println("Verifying responseApi response time.... " + responseAPI.getTime());
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());

			softAssert.assertTrue(responseAPI.getTime() < expectedResponseTime,
					"Test Case failed as response time is greater than 3 seconds:" + responseAPI.getTime());

			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	// Sanity Test 200 Check Head Method
	@Test(description = "200 Response Code and Response Time Validation for HEAD APIs", dataProvider = "HeadAPIsEndPointList", dataProviderClass = AssetsData.class)
	public void invokeHeadAPIsAndValidateResponseCodeAndResponseTime(String apiKey, String endPoint) throws Exception {
		System.out.println("******************Running Head APIs Test: " + endPoint);
		Response responseAPI = null;
		softAssert = new SoftAssert();
		String url = endPoint;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_HEAD_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_HEAD_APIS.get(apiKey).getMandatoryParams().split(",");
			if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryField();
			}
			if (mandatoryParamList.length > 1) {
				queryParaMap.put(mandatoryParamList[1],
						preReqApiResponse.jsonPath().get("data[0]." + mandatoryParamList[1]));
			}
			System.out.println("queryParaMap-->" + queryParaMap);
			responseAPI = RestUtils.head(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + responseAPI.getBody().asString());
			System.out.println("Verifying responseApi status code.... " + responseAPI.getStatusCode());
			System.out.println("Verifying responseApi response time.... " + responseAPI.getTime());
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());

			softAssert.assertTrue(responseAPI.getTime() < expectedResponseTime,
					"Test Case failed as response time is greater than 3 seconds:" + responseAPI.getTime());

			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}
//
//	@Test(description = "Solution and Use Case Query Param Validation for List APIs", groups = {
//			"es" }, dataProvider = "GetSolutionUseCaseForAllApplicableAPIs", dataProviderClass = AssetsData.class)
	public void solutionUseCaseQueryParamValidation(String apiKey, String solution, String useCase) throws Exception {
		System.out.println("******************Running solution UseCase Query Param Validation Test: " + apiKey);
		softAssert = new SoftAssert();
		String endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		String esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put("solution", solution);
		if (!useCase.equalsIgnoreCase("All")) {
			queryParaMap.put("useCase", useCase);
		}
		System.out.println("queryParaMap -->" + queryParaMap);
		String keyToFetchESSolutionFieldName = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex() + "_solution";
		String esSolutionFieldName = AssetsData.API_TO_ES_FIELDSMAPPING.get(keyToFetchESSolutionFieldName)
				.getEsFieldName();
		String keyToFetchESUseCaseFieldName = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex() + "_useCase";
		String esUseCaseFieldName = AssetsData.API_TO_ES_FIELDSMAPPING.get(keyToFetchESUseCaseFieldName)
				.getEsFieldName();

		System.out.println("corresponding  esSolutionFieldName -->" + esSolutionFieldName);
		System.out.println("corresponding  esUseCaseFieldName -->" + esUseCaseFieldName);

		String esQuery;
		String esQueryNameSolutionAndUseCase;
		String esQueryNameSolutionOnly;

		if (endPoint.contains("field-notices")) {
			esQueryNameSolutionAndUseCase = "solution_useCase_query_field_notices";
			esQueryNameSolutionOnly = "solution_only_query_field_notices";
		} else if (endPoint.contains("security-advisories")) {
			esQueryNameSolutionAndUseCase = "solution_useCase_query_security_advisories";
			esQueryNameSolutionOnly = "solution_only_query_security_advisories";

		} else {
			esQueryNameSolutionAndUseCase = "solution_useCase_query";
			esQueryNameSolutionOnly = "solution_only_query";

		}

		if (!useCase.equalsIgnoreCase("All")) {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(esQueryNameSolutionAndUseCase).getQuery()
					.replace("<solutionKey>", esSolutionFieldName).replace("<solution>", solution)
					.replace("<useCaseKey>", esUseCaseFieldName).replace("<useCase>", useCase);
		} else {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(esQueryNameSolutionOnly).getQuery()
					.replace("<solutionKey>", esSolutionFieldName).replace("<solution>", solution);

		}

		System.out.println("corresponding  esQuery -->" + esQuery);
		try {
			Response responseAPI = RestUtils.get(endPoint, queryParaMap, headers);
//			System.out.println("responseAPI Body --->" + responseAPI.asString());
			System.out.println("responseAPI Status Code ---> " + responseAPI.getStatusCode());
			Response responseES = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery);
//			System.out.println("responseES Body --->" + responseES.asString());
			System.out.println("responseES Status Code ---> " + responseES.getStatusCode());
			apiRecordsCount = apiAndEsRecordsCount(responseAPI, responseES);
			if (responseAPI.getStatusCode() == 200) {

				if (responseES.getStatusCode() == 200) {

					softAssert.assertEquals(apiRecordsCount.getCountOfRecordsFromAPI(),
							apiRecordsCount.getCountOfRecordsFromES(), "Count of API Not Matched with ES Index ");
				} else {
					softAssert.assertEquals(responseES.getStatusCode(), Integer.parseInt("200"),
							"Unable to fetch records from ES ");
				}

			} else {
				softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt("200"),
						"API not working for solution " + solution + " useCase " + useCase);
			}

			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());

		}

	}

	// 200 Response Check Common Method
	public static Response successResponse(String endPoint) throws Exception {
		Response responseAPI = null;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put("page", "1");
		queryParaMap.put("rows", "100");
		System.out.println("queryParaMap -->" + queryParaMap);
		try {
			if(endPoint.contains("<customerId>")){
				endPoint = endPoint.replace("<customerId>", customerId);
			}
			responseAPI = RestUtils.get(endPoint, queryParaMap, headers);
			System.out.println("responseAPI Body --->" + responseAPI.asString());
			System.out.println("responseAPI Status Code ---> " + responseAPI.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseAPI;

	}


	// 200 Response Check Common Method
	public static Response successResponsePutAPI(String endPoint, String body) throws Exception {
		Response responseAPI = null;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			if(endPoint.contains("<customerId>")){
				endPoint = endPoint.replace("<customerId>", customerId);
			}
			responseAPI = RestUtils.put(endPoint, body);
			System.out.println("responseAPI Body --->" + responseAPI.asString());
			System.out.println("responseAPI Status Code ---> " + responseAPI.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseAPI;

	}

	@Test(description = "200 Response Code and Response Time Validation for GET APIs", dataProvider = "GetAPIsEndPointList", dataProviderClass = AssetsData.class)
	public Response invokePolicyToDeviceAPIsAndValidateResponseCodeAndResponseTime(String apiKey, String endPoint) throws Exception {
		System.out.println("******************Running Get APIs Test: " + endPoint);
		Response responseAPI = null;
		String url = endPoint;
		softAssert = new SoftAssert();
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams().split(",");
			/*if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryField();
			}*/
			if (mandatoryParamList.length > 1) {
				queryParaMap.put(mandatoryParamList[1],"PCI");
			}
			System.out.println("queryParaMap-->" + queryParaMap);
			responseAPI = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + responseAPI.getBody().asString());
			System.out.println("Verifying responseApi status code.... " + responseAPI.getStatusCode());
			System.out.println("Verifying responseApi response time.... " + responseAPI.getTime());
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());

			softAssert.assertTrue(responseAPI.getTime() < expectedResponseTime,
					"Test Case failed as response time is greater than 3 seconds:" + responseAPI.getTime());

			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}
	return responseAPI;
	}

	@Test(description = "200 Response Code and Response Time Validation for GET APIs", dataProvider = "GetAPIsEndPointList", dataProviderClass = AssetsData.class)
	public void invokeDeviceTagAPIsAndValidateResponseCodeAndResponseTime(String apiKey, String endPoint) throws Exception {
		System.out.println("******************Running Get APIs Test: " + endPoint);
		Response responseAPI = null;
		if(endPoint.contains("<customerId>")){
			endPoint = endPoint.replace("<customerId>", customerId);
		}
		String url = endPoint;
		softAssert = new SoftAssert();
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams().split(",");
			if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryFieldForDeviceTag();
			}
			if (mandatoryParamList.length > 1 && preReqApiResponse.jsonPath().getList("tags").size() > 0) {
				queryParaMap.put(mandatoryParamList[1],
						preReqApiResponse.jsonPath().get("tags[0].devices[0]"));
			}else if(mandatoryParamList.length > 1){
				queryParaMap.put(mandatoryParamList[1],"Test");
			}
			System.out.println("queryParaMap-->" + queryParaMap);
			responseAPI = RestUtils.get(url, queryParaMap, headers);
//			System.out.println(" Response body --> " + responseAPI.getBody().asString());
			System.out.println("Verifying responseApi status code.... " + responseAPI.getStatusCode());
			System.out.println("Verifying responseApi response time.... " + responseAPI.getTime());
			softAssert.assertEquals(responseAPI.getStatusCode(), Integer.parseInt(expectedStatusCode),
					"Test Case failed as response status is not 200:" + responseAPI.getStatusLine());

			softAssert.assertTrue(responseAPI.getTime() < expectedResponseTime,
					"Test Case failed as response time is greater than 3 seconds:" + responseAPI.getTime());

			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
			Assert.assertFalse(true, "Connection aborted: " + e.getMessage());
		}

	}

	// 200 Response Check Common Method
	public static APIRecordsCountPojo recordsCount(String apiKey, Response responseAPI) {
		String endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		String esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);
		;
		Response responseES;
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		System.out.println("esIndex  -- " + esIndex);
		try {
			if (endPoint.contains("field-notices")) {

				responseES = fieldNoticesESResponse(esIndex);
			} else if (endPoint.contains("security-advisories")) {
				responseES = securityAdvisoriesESResponse(esIndex);

			} else {
				responseES = RestUtils.elasticSearchNoSqlGet(esIndex);
			}
//			System.out.println("responseES Body --->" + responseES.asString());
			System.out.println("responseES Status Code ---> " + responseES.getStatusCode());
			if (responseES.getStatusCode() == 200) {
				apiRecordsCount = apiAndEsRecordsCount(responseAPI, responseES);
				apiRecordsCount.setResponseES(responseES);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;

	}
	
	public static APIRecordsCountPojo recordsCountSQL(String apiKey, Response responseAPI) {
		String sqlQueryName = AssetsData.ASSETS_AMP_GET_APIS.get(apiKey).geteSIndex();
		System.out.println("sqlQueryName  -- " + sqlQueryName);
		String sqlQuery=AssetsData.ASSETS_SQL_QUERIES.get(sqlQueryName).replace("<customerId>", customerId);
		JSONArray result=null;
		int countOfRecordsFromAPI;
		int countOfRecordsFromDB;
		System.out.println("sqlQuery  -- " + sqlQuery);
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		try {
			 result = DBUtils.getMySqlResultSet(sqlQuery);
			 countOfRecordsFromAPI=responseAPI.jsonPath().get("Pagination.total");
			 System.out.println("countOfRecordsFromAPI ---> " + countOfRecordsFromAPI);
			 apiRecordsCount.setCountOfRecordsFromAPI(countOfRecordsFromAPI);
			 countOfRecordsFromDB=result.length();
			 System.out.println("countOfRecordsFromDB ---> " + countOfRecordsFromDB);
			 apiRecordsCount.setCountOfRecordsFromDB(countOfRecordsFromDB);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;

	}

	public static APIRecordsCountPojo recordsCountTaggingSQL(String apiKey, Response responseAPI) {
		String sqlQueryName = AssetsData.ASSETS_TAG_APIS.get(apiKey).geteSIndex();
		System.out.println("sqlQueryName  -- " + sqlQueryName);
		String sqlQuery=AssetsData.ASSETS_SQL_QUERIES.get(sqlQueryName).replace("<customerId>", customerId).replace("<policyName>", "PCI");
		JSONArray result=null;
		int countOfRecordsFromAPI = 0;
		int countOfRecordsFromDB = 0;
		int flag=0;
		System.out.println("sqlQuery  -- " + sqlQuery);
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		try {
			result = DBUtils.getMySqlResultSet(sqlQuery);
			switch(apiKey){
				case "tag_to_device":
					countOfRecordsFromAPI=responseAPI.jsonPath().getList("tags").size();
					break;
				case "tag_policy_mapping":
					countOfRecordsFromAPI=responseAPI.jsonPath().getList("policyGroups").size();
					break;
				case "policy_to_device":
					if(result.getJSONObject(0).get("neid").equals("null")){
						flag=1;
						Response response = apiCallToGetNE();
						countOfRecordsFromDB = response.jsonPath().get("Pagination.total");
					}
					countOfRecordsFromAPI=responseAPI.jsonPath().get("Pagination.total");
					break;
			}
		//	countOfRecordsFromAPI=responseAPI.jsonPath().getList("tags").size();
			System.out.println("countOfRecordsFromAPI ---> " + countOfRecordsFromAPI);
			apiRecordsCount.setCountOfRecordsFromAPI(countOfRecordsFromAPI);
			if(flag==0) {
				countOfRecordsFromDB = result.length();
			}
			System.out.println("countOfRecordsFromDB ---> " + countOfRecordsFromDB);
			apiRecordsCount.setCountOfRecordsFromDB(countOfRecordsFromDB);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;

	}

	public APIRecordsCountPojo recordsCountForDeviceTagAPI(String apiKey, String endPoint) throws Exception {
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		int countOfRecordsFromAPI = 0;
		int countOfRecordsFromDB = 0;
		Response responseAPI = null;
		if(endPoint.contains("<customerId>")){
			endPoint = endPoint.replace("<customerId>", customerId);
		}
		String url = endPoint;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams().split(",");
			if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryFieldForDeviceTag();
			}
			String deviceId = null;
			if (mandatoryParamList.length > 1 && preReqApiResponse.jsonPath().getList("tags").size() > 0) {
				deviceId = preReqApiResponse.jsonPath().get("tags[0].devices[0]");
				queryParaMap.put(mandatoryParamList[1], deviceId);
			}else if(mandatoryParamList.length > 1){
				queryParaMap.put(mandatoryParamList[1],"Test");
			}

			if (preRequisiteAPIRan == true) {
				apiCallToGetTagCountFromNE(deviceId);
			}

			System.out.println("queryParaMap-->" + queryParaMap);
			responseAPI = RestUtils.get(url, queryParaMap, headers);
			countOfRecordsFromAPI = responseAPI.jsonPath().getList("tags").size();
			System.out.println("countOfRecordsFromAPI ---> " + countOfRecordsFromAPI);
			apiRecordsCount.setCountOfRecordsFromAPI(countOfRecordsFromAPI);

			if(preReqApiResponse.jsonPath().getList("data").size()!=0) {
				countOfRecordsFromDB = preReqApiResponse.jsonPath().getList("data[0].tags").size();
			}
			System.out.println("countOfRecordsFromDB ---> " + countOfRecordsFromDB);
			apiRecordsCount.setCountOfRecordsFromDB(countOfRecordsFromDB);

			return apiRecordsCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;
	}

	public APIRecordsCountPojo recordsCountForDeviceTagAPIDataValidation(String apiKey, String endPoint) throws Exception {
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		int countOfRecordsFromAPI = 0;
		int countOfRecordsFromDB = 0;
		Response responseAPI = null;
		if(endPoint.contains("<customerId>")){
			endPoint = endPoint.replace("<customerId>", customerId);
		}
		String url = endPoint;
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		try {
			System.out.println("mandatoryParams for endPoint " + endPoint + " is --->"
					+ AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams());
			String[] mandatoryParamList = AssetsData.ASSETS_TAG_APIS.get(apiKey).getMandatoryParams().split(",");
			if (preRequisiteAPIRan == false) {
				preRequisiteAPIRan = true;
				apiCallToGetMandatoryFieldForDeviceTag();
			}
			String deviceId = null;
			if (mandatoryParamList.length > 1 && preReqApiResponse.jsonPath().getList("tags").size() > 0) {
				deviceId = preReqApiResponse.jsonPath().get("tags[0].devices[0]");
				queryParaMap.put(mandatoryParamList[1], deviceId);
			}

			if (preRequisiteAPIRan == true) {
				apiCallToGetTagCountFromNE(deviceId);
				System.out.println("queryParaMap-->" + queryParaMap);
				responseAPI = RestUtils.get(url, queryParaMap, headers);
				apiRecordsCount.setResponseAPI(responseAPI);
				apiRecordsCount.setResponseES(preReqApiResponse);
			}
			return apiRecordsCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;
	}

	public static APIRecordsCountPojo recordsCountForSaveTagAPI(String apiKey, String body) {
		String sqlQueryName = AssetsData.ASSETS_TAG_APIS.get(apiKey).geteSIndex();
		System.out.println("sqlQueryName  -- " + sqlQueryName);
		String sqlQuery=AssetsData.ASSETS_SQL_QUERIES.get(sqlQueryName).replace("<customerId>", customerId).replace("<policyName>", "PCI");
		JSONArray result=null;
		int countOfRecordsFromAPI = 0;
		int countOfRecordsFromDB = 0;
		int flag=0;
		System.out.println("sqlQuery  -- " + sqlQuery);
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		try {
			result = DBUtils.getMySqlResultSet(sqlQuery);
			if(result.getJSONObject(0).get("tagcount").equals("null")){
				flag=1;
			}

			JSONObject jsonObj = new JSONObject(body);
			countOfRecordsFromAPI = jsonObj.getJSONArray("tags").length();

			System.out.println("countOfRecordsFromAPI ---> " + countOfRecordsFromAPI);
			apiRecordsCount.setCountOfRecordsFromAPI(countOfRecordsFromAPI);
			if(flag==0) {
				countOfRecordsFromDB = result.length();
			}
			System.out.println("countOfRecordsFromDB ---> " + countOfRecordsFromDB);
			apiRecordsCount.setCountOfRecordsFromDB(countOfRecordsFromDB);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiRecordsCount;

	}

	public static Response fieldNoticesESResponse(String esIndex) {

		Response responseES = null;
		String esQuery = AssetsData.ASSETS_ES_QUERIES.get("field-notices_query").getQuery();
		System.out.println("esQuery --->" + esQuery);
		try {
			responseES = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseES;

	}

	public static Response securityAdvisoriesESResponse(String esIndex) {

		Response responseES = null;
		String esQuery = AssetsData.ASSETS_ES_QUERIES.get("security-advisories_query").getQuery();
		System.out.println("esQuery --->" + esQuery);
		try {
			responseES = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseES;

	}

	public static APIRecordsCountPojo recordsCountWithOptionalParam(String apiKey, String optionalParam,
			String optionalParamValue) {
		String endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		String esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		queryParaMap.put("customerId", customerId);
		queryParaMap.put(optionalParam, optionalParamValue);
		System.out.println("queryParaMap -->" + queryParaMap);
		String keyToFetchESFieldName = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex() + "_" + optionalParam;
		String esFieldName = AssetsData.API_TO_ES_FIELDSMAPPING.get(keyToFetchESFieldName).getEsFieldName();
		String esFieldDataType = AssetsData.API_TO_ES_FIELDSMAPPING.get(keyToFetchESFieldName).getDataType();
		System.out.println("corresponding  esFieldName -->" + esFieldName);
		String esQuery;
		String esQueryName;
		if (endPoint.contains("field-notices")) {

			esQueryName = "single_field_search_query_field_notices";
		} else if (endPoint.contains("security-advisories")) {
			esQueryName = "single_field_search_query_security_advisories";

		} else {
			esQueryName = "single_field_search_query";
		}

		if (esFieldDataType.equalsIgnoreCase("String")) {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(esQueryName).getQuery()
					.replace("<fieldName>", esFieldName + ".keyword").replace("<fieldValue>", optionalParamValue);
		} else {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(esQueryName).getQuery().replace("<fieldName>", esFieldName)
					.replace("<fieldValue>", optionalParamValue);

		}

		System.out.println("corresponding  esQuery -->" + esQuery);
		try {
			Response responseAPI = RestUtils.get(endPoint, queryParaMap, headers);
//			System.out.println("responseAPI Body --->" + responseAPI.asString());
			System.out.println("responseAPI Status Code ---> " + responseAPI.getStatusCode());
			Response responseES = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery);
//			System.out.println("responseES Body --->" + responseES.asString());
			System.out.println("responseES Status Code ---> " + responseES.getStatusCode());
			if (responseAPI.getStatusCode() == 200) {

				if (responseES.getStatusCode() == 200) {
					apiRecordsCount = apiAndEsRecordsCount(responseAPI, responseES);
				} else {
					System.out.println("Unable to fetch records from ES ");

				}

			} else {
				System.out.println("Unable to fetch records from API ");

			}
			apiRecordsCount.setResponseAPI(responseAPI);
			apiRecordsCount.setResponseES(responseES);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return apiRecordsCount;

	}

	public static APIRecordsCountPojo apiAndEsRecordsCount(Response responseAPI, Response responseES) {
		APIRecordsCountPojo apiRecordsCount = new APIRecordsCountPojo();
		int countOfRecordsFromAPI;
		int countOfRecordsFromES;
		countOfRecordsFromAPI = responseAPI.jsonPath().get("Pagination.total");
		System.out.println("countOfNEsFromAPI ---> " + countOfRecordsFromAPI);
		apiRecordsCount.setCountOfRecordsFromAPI(countOfRecordsFromAPI);
		countOfRecordsFromES = responseES.jsonPath().get("hits.total.value");
		System.out.println("countOfNEsFromES ---> " + countOfRecordsFromES);
		apiRecordsCount.setCountOfRecordsFromES(countOfRecordsFromES);

		return apiRecordsCount;
	}

	/**
	 * Methods for count type API Starts Here
	 ***********************************************/
	public static CountTypeAPIPojo countAPIValidation(String apiKey, String solution, String useCase) {
		String endPoint = AssetsData.ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		Map<String, String> queryParaMap = new LinkedHashMap<String, String>();
		Map<String, Integer> esResponseObject = new HashMap<String, Integer>();
		Map<String, Integer> apiResponseObject = new HashMap<String, Integer>();
		Response responseAPI;
		Response responseES;
		CountTypeAPIPojo countTypeAPIPojo = new CountTypeAPIPojo();

		try {
			queryParaMap.put("customerId", customerId);
			queryParaMap.put("solution", solution);
			if (!useCase.equalsIgnoreCase("All")) {
				queryParaMap.put("useCase", useCase);
			}
			System.out.println("queryParaMap -->" + queryParaMap);
			responseAPI = RestUtils.get(endPoint, queryParaMap, headers);
//			System.out.println("responseAPI Body --->" + responseAPI.asString());
			apiResponseObject = process_API_response_for_validation(apiKey, responseAPI);
			responseES = invoke_ES_Index_via_Post_API_call(apiKey, solution, useCase);
			esResponseObject = process_ES_response(apiKey, responseES);
			countTypeAPIPojo.setApiResponseObject(apiResponseObject);
			countTypeAPIPojo.setEsResponseObject(esResponseObject);

		} catch (Exception e) {
			System.out.println("Connection aborted!");
			e.printStackTrace();
		}
		return countTypeAPIPojo;
	}

	public static Map<String, Integer> process_API_response_for_validation(String apiKey, Response responseAPI) {
		Map<String, Integer> apiResponseObject = new HashMap<String, Integer>();
		int size = 0;
		String APIType = AssetsData.ASSETS_GET_APIS.get(apiKey).getAPIType();
		String inputType = AssetsData.ASSETS_GET_APIS.get(apiKey).getInputType();
		String apiCountParam = AssetsData.ASSETS_GET_APIS.get(apiKey).getAPICountParam();
		try {
			if (APIType.equals("Count")) {
				size = responseAPI.jsonPath().getList(inputType).size();
				for (int i = 0; i < size; i++) {
					apiResponseObject.put(responseAPI.jsonPath().get(inputType + "[" + i + "]"),
							responseAPI.jsonPath().get(apiCountParam + "[" + i + "]"));
				}
			}
			System.out.println("API response:");
			for (String type : apiResponseObject.keySet()) {
				System.out.println("[" + type + " : " + apiResponseObject.get(type) + "]");
			}
		} catch (Exception e) {
			System.out.println("API Data insertion to map aborted!");
			e.printStackTrace();
		}
		return apiResponseObject;
	}

	public static Response invoke_ES_Index_via_Post_API_call(String apiKey, String solution, String useCase) {
		String esJsonPath = AssetsData.ASSETS_GET_APIS.get(apiKey).getESJsonPath();
		String esQuery = "";
		String inputType = AssetsData.ASSETS_GET_APIS.get(apiKey).getInputType();
		if (solution.equals("None")) {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(inputType + ".aggregations_without_solution").getQuery()
					.replace("<inputType>", esJsonPath);
		} else if (solution.equals("IBN") && useCase.equals("All")) {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(inputType + ".aggregations_with_solution_only").getQuery()
					.replace("<inputType>", esJsonPath).replace("<solution>", solution);

		} else {
			esQuery = AssetsData.ASSETS_ES_QUERIES.get(inputType + ".aggregations_with_solution_and_useCase").getQuery()
					.replace("<inputType>", esJsonPath).replace("<solution>", solution).replace("<useCase>", useCase);

		}
		String esIndex = AssetsData.ASSETS_GET_APIS.get(apiKey).geteSIndex().replace("<customerId>", customerId);
		System.out.println("esIndex ----> " + esIndex);
		System.out.println("esQuery ----> " + esQuery);

		Response responseES = RestUtils.elasticSearchNoSqlPost(esIndex, esQuery);
//		System.out.println("responseES Body --->" + responseES.asString());
		return responseES;

	}

	public static Map<String, Integer> process_ES_response(String apiKey, Response responseES) {
		int size = 0;
		String ESType = AssetsData.ASSETS_GET_APIS.get(apiKey).getESType();
		Map<String, Integer> esResponseObject = new HashMap<String, Integer>();

		try {
			size = responseES.jsonPath().getList(ESType).size();
			for (int i = 0; i < size; i++) {
				// condition to avoid null key insertions into ES Map Object
				if (!(responseES.jsonPath().get(ESType + ".key[" + i + "]") == "")) {
					esResponseObject.put(responseES.jsonPath().get(ESType + ".key[" + i + "]"),
							responseES.jsonPath().get(ESType + ".doc_count[" + i + "]"));
				}
			}
			System.out.println("ES response:");
			for (String type : esResponseObject.keySet()) {
				System.out.println("[" + type + " : " + esResponseObject.get(type) + "]");
			}
		} catch (Exception e) {
			System.out.println("ES Data insertion to map aborted!");
			e.printStackTrace();
		}
		return esResponseObject;
	}

	/**
	 * Methods for count type API Ends Here
	 ***********************************************/

}
