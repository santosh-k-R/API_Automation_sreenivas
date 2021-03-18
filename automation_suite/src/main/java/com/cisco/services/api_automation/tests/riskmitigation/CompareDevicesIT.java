package com.cisco.services.api_automation.tests.riskmitigation;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;
import static com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData.*;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("FP Compare Devices")
public class CompareDevicesIT {

	@Test(groups= {"Severity3"},description = "Verifying Fingerprint Compare Devices API", dataProvider = "CompareDevicesFP", dataProviderClass = RiskMitigationData.class)
	public void compareDevicesValidDevice(String endPoint,String headers,String params) {
		Response response = RestUtils.get(endPoint,params,headers);
		//System.out.println("API Response-------------->"+response.getBody().prettyPrint());
		Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
		
		
	}
	@Test(groups= {"Severity3"},description = "Verifying Fingerprint Compare Devices API with Invalid Devices")
	public void compareDevicesInValidDevice() {
		Response response = RestUtils.get(RISK_MITIGATION_FP_COMPARE_IN_VALID_DEVICES.get("endPoint"),RISK_MITIGATION_FP_COMPARE_IN_VALID_DEVICES.get("headers"),RISK_MITIGATION_FP_COMPARE_IN_VALID_DEVICES.get("params"));
		System.out.println("API Response-------------->"+response.getBody().asString());
		Assert.assertEquals(response.statusCode(), 400, "Status Code ::");
		
		
	}
	@Test(groups= {"Severity3"},description = "Verifying Fingerprint Compare Devices API without SaId")
	public void compareDevicesWithOutSaId() {
		Response response = RestUtils.get("https://api-test.us.csco.cloud/rmc/fingerprint/v1/compare-devices/100871_0");
		Assert.assertEquals(response.statusCode(), 400, "Status Code ::");
		
		
	}
	@Test(groups= {"Severity3"},description = "Verifying Fingerprint Compare Devices API Without Headers")
	public void compareDevicesWithOutHeaders() {
		Response response = RestUtils.get("https://api-test.us.csco.cloud/rmc/fingerprint/v1/compare-devices/100871_0");
		Assert.assertEquals(response.statusCode(), 400, "Status Code ::");
		
		
	}
	
	
}

