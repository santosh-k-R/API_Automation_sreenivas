package com.cisco.services.api_automation.tests.riskmitigation;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import static io.qameta.allure.SeverityLevel.MINOR;

@Feature("FP Similar Devices")
public class SimilarDevicesIT {
	@Severity(MINOR)
	@Test(groups = {"Severity3"}, description = "Verifying FP Similar devices API", dataProvider = "Similardevices", dataProviderClass = RiskMitigationData.class)
	public void similarDevices(String endPoint, String headers, String params, String index, String esQuery) {
	Response response = RestUtils.get(endPoint, params, headers);
	System.out.println("API Response----------->" + response.getBody().asString());
	Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
	}

	@Severity(MINOR)
	@Test(groups = {"Severity3" }, description = "Verifying FP similar devicesInvalid data check", dataProvider = "SimilardeviceInvalid", dataProviderClass = RiskMitigationData.class)
	public void similarDeviceInvalid(String endPoint, String headers, String params,String reponse_expected){
	Response response = RestUtils.get(endPoint,params,headers);
	String responseBody=response.getBody().asString();
	SoftAssert softAssert= new SoftAssert();
	Assert.assertEquals(response.statusCode(), 404,"Status Code ::");
	softAssert.assertAll("Response Validated");
	}
}
