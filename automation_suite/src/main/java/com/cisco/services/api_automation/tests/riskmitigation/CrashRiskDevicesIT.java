package com.cisco.services.api_automation.tests.riskmitigation;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.CrashedRiskDevicesPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.DevicesPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.FPDeviceDetailsPojo;
import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData.*;
import static io.qameta.allure.SeverityLevel.*;

@Feature("Crash Risk Devices")
public class CrashRiskDevicesIT {

	@Severity(BLOCKER)
	@Test(groups = {
			"Severity1" }, description = "Verifying Finger print Crash Risk Devices API", dataProvider = "CrashRiskDeviceDetails", dataProviderClass = RiskMitigationData.class, priority = 1)
	public void crashRiskDevices(String endPoint, String headers, String params, String index, String esQuery) {
		Response response = RestUtils.get(endPoint, params, headers);
		System.out.println("API Response----------->"+response.getBody().asString());
		Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
		CrashedRiskDevicesPojo crashedRiskDevicesGrid = response.as(CrashedRiskDevicesPojo.class,
				ObjectMapperType.GSON);
		List<DevicesPojo> devices = crashedRiskDevicesGrid.getDevices();
		if(RestUtils.ES_VALIDATION){
			JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
			Assert.assertEquals(esBody.getInt("hits.total.value"), devices.size(), "ES Number of Rows: ");
			SoftAssert softAssert = new SoftAssert();
			List<String> esDeviceId = esBody.getList("hits.hits._source.deviceId", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert
					.assertTrue(esDeviceId.contains(device.getdeviceId()), "ES_DeviceId " + device.getdeviceId()));
			List<String> esDeviceName = esBody.getList("hits.hits._source.deviceName", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert
					.assertTrue(esDeviceName.contains(device.getDeviceName()), "ES_DeviceName " + device.getDeviceName()));
			List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert
					.assertTrue(esProductId.contains(device.getProductId()), "ES_ProductId " + device.getProductId()));
			List<String> esProductFamily = esBody.getList("hits.hits._source.productFamily", String.class);
			crashedRiskDevicesGrid.getDevices().stream()
					.forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()),
							"ES_ProductFamily " + device.getProductFamily()));
			List<String> esSoftwareVersion = esBody.getList("hits.hits._source.softwareVersion", String.class);
			crashedRiskDevicesGrid.getDevices().stream()
					.forEach(device -> softAssert.assertTrue(esSoftwareVersion.contains(device.getSoftwareVersion()),
							"ES_SoftwareVersion " + device.getSoftwareVersion()));
			List<String> esSoftwareType = esBody.getList("hits.hits._source.softwareType", String.class);
			crashedRiskDevicesGrid.getDevices().stream()
					.forEach(device -> softAssert.assertTrue(esSoftwareType.contains(device.getSoftwareType()),
							"ES_SoftwareType " + device.getSoftwareType()));
			List<String> esSerialNumber = esBody.getList("hits.hits._source.serialNumber", String.class);
			crashedRiskDevicesGrid.getDevices().stream()
					.forEach(device -> softAssert.assertTrue(esSerialNumber.contains(device.getSerialNumber()),
							"ES_SerialNumber " + device.getSerialNumber()));
			List<String> esRiskScore = esBody.getList("hits.hits._source.riskScore", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert
					.assertTrue(esRiskScore.contains(device.getRiskScore()), "ES_RiskScore " + device.getRiskScore()));
			List<String> esGlobalRiskRank = esBody.getList("hits.hits._source.globalRiskRank", String.class);
			crashedRiskDevicesGrid.getDevices().stream()
					.forEach(device -> softAssert.assertTrue(esGlobalRiskRank.contains(device.getGlobalRiskRank()),
							"ES_GlobalRiskRank " + device.getGlobalRiskRank()));
			softAssert.assertAll("Asserting with the ES response");
		}
	}

	@Severity(MINOR)
	@Test(groups = { "Severity3" }, description = "Verifying the Crashed Risk Devices without  Headers", priority = 2)
	public void verifyFPCrashRiskDevicesWithoutHeaderValue() {
		Response response = RestUtils.get(RISK_MITIGATION_FP_WITHOUT_HEADERS.get("endPoint"),
				RISK_MITIGATION_FP_WITHOUT_HEADERS.get("params"));
		Assert.assertEquals(response.getStatusCode(), 401, "Status Code:");

	}

	@Severity(MINOR)

	@Test(groups = { "Severity3" }, description = "Verifying the invalid Customer", priority = 3)
	public void invalidCustomer() {
		Response response = RestUtils.get(RISK_MITIGATION_INVALID_CUSTOMER.get("endPoint"),
				RISK_MITIGATION_INVALID_CUSTOMER.get("headers"), RISK_MITIGATION_INVALID_CUSTOMER.get("params"));
		Assert.assertEquals(response.statusCode(), 404, "Status Code ::");

	}

	@Severity(CRITICAL)
	@Test(groups = { "Severity2" }, description = "Verifying the Device Details API", priority = 4)
	public void deviceDetailsAPI() {
		Response response = RestUtils.get(RISK_MITIGATION_FP_DEVICES_DETAILS.get("endPoint"),
				RISK_MITIGATION_FP_DEVICES_DETAILS.get("headers"), RISK_MITIGATION_FP_DEVICES_DETAILS.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
		if(RestUtils.ES_VALIDATION) {
			FPDeviceDetailsPojo devicesDetails = response.as(FPDeviceDetailsPojo.class, ObjectMapperType.GSON);
			JsonPath esBody = RestUtils.elasticSearchNoSqlPost(RISK_MITIGATION_FP_DEVICES_DETAILS.get("index"),
					RISK_MITIGATION_FP_DEVICES_DETAILS.get("ES_Query")).jsonPath();
			SoftAssert softAssert = new SoftAssert();
			List<String> esCustomerID = esBody.getList("hits.hits._source.customerId", String.class);
			softAssert.assertTrue(esCustomerID.contains(devicesDetails.getCustomerId()),
					"ES_CustomerID " + devicesDetails.getCustomerId());
			List<String> esDeviceId = esBody.getList("hits.hits._source.deviceId", String.class);
			softAssert.assertTrue(esDeviceId.contains(devicesDetails.getDeviceId()),
					"ES_DeviceId " + devicesDetails.getDeviceId());
			List<String> esDeviceName = esBody.getList("hits.hits._source.deviceName", String.class);
			softAssert.assertTrue(esDeviceName.contains(devicesDetails.getDeviceName()),
					"ES_DeviceName " + devicesDetails.getDeviceName());
			List<String> esIpAddress = esBody.getList("hits.hits._source.ipAddress", String.class);
			softAssert.assertTrue(esIpAddress.contains(devicesDetails.getIpAddress()),
					"ES_IpAddress " + devicesDetails.getIpAddress());
			List<String> esSerialNumber = esBody.getList("hits.hits._source.serialNumber", String.class);
			softAssert.assertTrue(esSerialNumber.contains(devicesDetails.getSerialNumber()),
					"ES_serialNumber " + devicesDetails.getSerialNumber());
			List<String> esGlobalRiskRank = esBody.getList("hits.hits._source.globalRiskRank", String.class);
			softAssert.assertTrue(esGlobalRiskRank.contains(devicesDetails.getGlobalRiskRank()),
					"ES_GlobalRiskRank " + devicesDetails.getGlobalRiskRank());
			List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
			softAssert.assertTrue(esProductId.contains(devicesDetails.getProductId()),
					"ES_ProductId " + devicesDetails.getProductId());
			List<String> esSoftwareVersion = esBody.getList("hits.hits._source.softwareVersion", String.class);
			softAssert.assertTrue(esSoftwareVersion.contains(devicesDetails.getSoftwareVersion()),
					"ES_SoftwareVersion " + devicesDetails.getSoftwareVersion());
			List<String> esSoftwareType = esBody.getList("hits.hits._source.softwareType", String.class);
			softAssert.assertTrue(esSoftwareType.contains(devicesDetails.getSoftwareType()),
					"ES_SoftwareType " + devicesDetails.getSoftwareType());
			softAssert.assertAll("Asserting with the ES response");
		}

	}

}
