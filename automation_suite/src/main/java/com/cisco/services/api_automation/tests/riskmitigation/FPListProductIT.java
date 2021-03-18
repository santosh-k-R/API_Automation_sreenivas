package com.cisco.services.api_automation.tests.riskmitigation;
import static io.qameta.allure.SeverityLevel.MINOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.FPListProductPojo;
import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Feature("FP LIST product family")
public class FPListProductIT {
	@Severity(MINOR)
	@Test(groups = {"Severity3"}, description = "Verifying FP product list API", dataProvider = "FPList", dataProviderClass = RiskMitigationData.class)
	public void fpListProductFamily(String endPoint, String headers, String params, String index, String esQuery,
	String customerId_exp) {
	Response response = RestUtils.get(endPoint, params, headers);
	System.out.println("API Response----------->" + response.getBody().asString());
	Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
	FPListProductPojo productDetails = response.as(FPListProductPojo.class, ObjectMapperType.GSON);
	boolean crashpredicted = productDetails.getCrashPredicted();

	if(RestUtils.ES_VALIDATION) {
	JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();

	SoftAssert softAssert = new SoftAssert();

	softAssert.assertEquals(productDetails.getCustomerId(), customerId_exp, "customerId : ");

	List<String> esProductFamily = esBody.getList("hits.hits._source.productFamily", String.class);
	productDetails.getProductFamilies().stream()
	.forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()),
	"ES_ProductFamily " + device.getProductFamily()));
	List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
	productDetails.getProductFamilies().stream()
	.forEach(device -> softAssert.assertTrue(esProductId.contains(device.getProductIds()),
	"ES_ProductId " + device.getProductIds()));

	}
	}


	@Severity(MINOR)
	@Test(groups = {"Severity3" }, description = "Timetaken for high risk FP product list api", dataProvider = "FPTimetaken", dataProviderClass = RiskMitigationData.class)
	public void FPListTimetaken(String endPoint, String headers, String params){
	Response response = RestUtils.get(endPoint, params, headers);
	System.out.println("API Response----------->" + response.getTime());
	Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
	}

	@Severity(MINOR)
	@Test(groups = {"Severity3" }, description = "Invalid data check", dataProvider = "FPListinvalid", dataProviderClass = RiskMitigationData.class)
	public void FPListinvalid(String endPoint, String headers, String params,String reponse_expected){
	Response response = RestUtils.get(endPoint,params,headers);
	String responseBody=response.getBody().asString();
	SoftAssert softAssert= new SoftAssert();
	Assert.assertEquals(response.statusCode(), 400,"Status Code ::");
	softAssert.assertAll("Response Validated");
	}

	@Severity(MINOR)
	@Test(groups = {"Severity3" }, description = "API without header")
	public void FPidListwithoutHeader(){
	Response response = RestUtils.get("/fingerprint/v1/list-product-families/100871_0");
	String responseBody=response.getBody().asString();
	SoftAssert softAssert= new SoftAssert();
	Assert.assertEquals(response.statusCode(), 401,"Status Code ::");
	//softAssert.assertAll("Response Validated");
	}

}
