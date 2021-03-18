package com.cisco.services.api_automation.tests.insights.compliance;

import com.cisco.services.api_automation.pojo.response.compliance.ViolationSummaryPOJO;
import com.cisco.services.api_automation.testdata.insights.compliance.ViolationSummaryData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ViolationSummary {

	@Test(dataProviderClass = ViolationSummaryData.class, dataProvider = "getUnAuthorizedRequestData")
	public void test1_validateUnauthorizedErrorRequest(String endPoint, String headers, String params, String user) {
		Response response = RestUtils.getWithOutAuth(endPoint, headers, params, new User(user));
		Assert.assertEquals(response.getStatusCode(), 403, "Status Code: ");
	}

	@Test(dataProviderClass = ViolationSummaryData.class, dataProvider = "getBadRequestData")
	public void test2_validateBadRequest(String endPoint, String headers, String params, String user, String errMsg) {
		Response response = RestUtils.get(endPoint, headers, params, new User(user));
		Assert.assertEquals(response.getStatusCode(), 400, "Status Code: ");
		Assert.assertEquals(response.jsonPath().getString("error.message"), errMsg, "Error Message : ");
	}
	
	@Test(dataProviderClass = ViolationSummaryData.class, dataProvider = "getDefaultResponseData")
	public void test3_validateResponse(String endPoint, String headers, String params, String user, String esSummaryQuery, String summaryIndex, String asstCountQuery, String asstIndex) {
		Response response = RestUtils.get(endPoint, headers, params, new User(user));
		
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		validateDefaultResponse(response, esSummaryQuery, summaryIndex, asstCountQuery, asstIndex);
	}
	
	@Test(dataProviderClass = ViolationSummaryData.class, dataProvider = "getDefaultResponseData")
	public void test4_validateSort(String endPoint, String headers, String params, String user, String esSummaryQuery, String summaryIndex, String asstCountQuery, String asstIndex) {
		Response response = RestUtils.get(endPoint, headers, params, new User(user));
		
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code: ");
		validateDefaultResponse(response, esSummaryQuery, summaryIndex, asstCountQuery, asstIndex);
	}
	
	public void validateDefaultResponse(Response response, String esSummaryQuery, String summaryIndex, String asstCountQuery, String asstIndex) {
		System.out.println("Entered validateDefaultResponse");
		System.out.println("Response as : " + response.asString());
		ViolationSummaryPOJO responseBody = response.as(ViolationSummaryPOJO.class, ObjectMapperType.JACKSON_2);
		JsonPath esBody = RestUtils.elasticSearchNoSqlPost(summaryIndex, esSummaryQuery).jsonPath();
		System.out.println("esBody as : " + esBody.toString());
		int impAssetCount = RestUtils.elasticSearchNoSqlPost(asstIndex, asstCountQuery).jsonPath().getInt("aggregations.impAssetCount.value");
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(responseBody.getData().getImpactedAssetCount(), impAssetCount);
		softAssert.assertEquals(responseBody.getData().getViolationCount(), (int)esBody.getFloat("aggregations.violationCount.value"));
		softAssert.assertEquals(responseBody.getData().getTotalCount(), esBody.getInt("hits.total.value"));
		System.out.println(":::::::::: Validated Count ::::::::::");
		
		List<String> expmgmtSystemAddr = esBody.getList("hits.hits._source.mgmtSystemAddr");
		List<String> actmgmtSystemAddr = responseBody.getData().getSummary().stream().map(e->e.getMgmtSystemAddr()).collect(Collectors.toList());
		softAssert.assertEquals(expmgmtSystemAddr, actmgmtSystemAddr);
		softAssert.assertAll("Validate Mgmt System Address");
		
		List<String> expruleSeverity = esBody.getList("hits.hits._source.ruleSeverity");
		List<String> actruleSeverity = responseBody.getData().getSummary().stream().map(e->e.getRuleSeverity()).collect(Collectors.toList());
		softAssert.assertEquals(expruleSeverity, actruleSeverity);
		softAssert.assertAll("Validate Severity");
		
		List<String> expPGName = esBody.getList("hits.hits._source.policyGroupName");
		List<String> actPGName = responseBody.getData().getSummary().stream().map(e->e.getPolicyGroupName()).collect(Collectors.toList());
		softAssert.assertEquals(expPGName, actPGName);
		softAssert.assertAll("Validate Polict Group");
		
		List<String> expolicyName = esBody.getList("hits.hits._source.policyName");
		List<String> actpolicyName = responseBody.getData().getSummary().stream().map(e->e.getPolicyName()).collect(Collectors.toList());
		softAssert.assertEquals(expolicyName, actpolicyName);
		softAssert.assertAll("Validate Policy Name ");
		
		List<String> expRuleTitle = esBody.getList("hits.hits._source.ruleTitle");
		List<String> actRuleTitle = responseBody.getData().getSummary().stream().map(e->e.getRuleTitle()).collect(Collectors.toList());
		softAssert.assertEquals(expRuleTitle, actRuleTitle);
		softAssert.assertAll("Validate Rule Title ");
		
		List<Integer> expViolationCount = esBody.getList("hits.hits._source.violationCount");
		List<Integer> actViolationCount = responseBody.getData().getSummary().stream().map(e->e.getViolationCount()).collect(Collectors.toList());
		softAssert.assertEquals(expViolationCount, actViolationCount);
		softAssert.assertAll("Validate Violation Count ");
		
		List<Integer> expImpactedAssetCount = esBody.getList("hits.hits._source.impactedAssetCount");
		List<Integer> actImpactedAssetCount = responseBody.getData().getSummary().stream().map(e->e.getImpactedAssetCount()).collect(Collectors.toList());
		softAssert.assertEquals(expImpactedAssetCount, actImpactedAssetCount);
		softAssert.assertAll("Validate Impacted Assets Count ");
		
		List<String> expPolicyCategory = esBody.getList("hits.hits._source.policyCategory");
		List<String> actPolicyCategory = responseBody.getData().getSummary().stream().map(e->e.getPolicyCategory()).collect(Collectors.toList());
		softAssert.assertEquals(expPolicyCategory, actPolicyCategory);
		softAssert.assertAll("Validate Policy Category");
		
		System.out.println("Finished validateDefaultResponse");
		
	}

}
