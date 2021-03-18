package com.cisco.services.api_automation.tests.riskmitigation;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.CrashedRiskDevicesPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.DevicesPojo;
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

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Feature("FP DNAC And All Search")
public class FPDnacSearchIT {

	
	@Severity(CRITICAL)
	@Test(groups= {"Severity2"},description = "Verifying Finger print Multi DNAC Search API", dataProvider = "FPMultiDnacSearch", dataProviderClass = RiskMitigationData.class)
	public void fpDnacSearchWithoutSQL(String endPoint,String headers,String params,String index, String esQuery) {
		Response response = RestUtils.get(endPoint,params,headers);
		Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
		if(RestUtils.ES_VALIDATION){
			CrashedRiskDevicesPojo crashedRiskDevicesGrid = response.as(CrashedRiskDevicesPojo.class,
					ObjectMapperType.GSON);
			List<DevicesPojo> devices = crashedRiskDevicesGrid.getDevices();
			JsonPath esBody =RestUtils.elasticSearchNoSqlPost(index,esQuery).jsonPath();
			Assert.assertEquals(esBody.getInt("hits.total.value"), devices.size(), "ES Number of Rows: ");
			 SoftAssert softAssert = new SoftAssert();
			List<String> esDeviceId = esBody.getList("hits.hits._source.deviceId", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esDeviceId.contains(device.getdeviceId()), "ES_DeviceId" + device.getdeviceId()));
			List<String> esDeviceName = esBody.getList("hits.hits._source.deviceName", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esDeviceName.contains(device.getDeviceName()), "ES_DeviceName" + device.getDeviceName()));
			List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esProductId.contains(device.getProductId()), "ES_ProductId" + device.getProductId()));
			List<String> esProductFamily = esBody.getList("hits.hits._source.productFamily", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()), "ES_ProductFamily" + device.getProductFamily()));
			List<String> esSoftwareVersion = esBody.getList("hits.hits._source.softwareVersion", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esSoftwareVersion.contains(device.getSoftwareVersion()), "ES_SoftwareVersion" + device.getSoftwareVersion()));
			List<String> esSoftwareType = esBody.getList("hits.hits._source.softwareType", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esSoftwareType.contains(device.getSoftwareType()), "ES_SoftwareType" + device.getSoftwareType()));
			List<String> esSerialNumber = esBody.getList("hits.hits._source.serialNumber", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esSerialNumber.contains(device.getSerialNumber()), "ES_SerialNumber" + device.getSerialNumber()));
			List<String> esRiskScore = esBody.getList("hits.hits._source.riskScore", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esRiskScore.contains(device.getRiskScore()), "ES_RiskScore" + device.getRiskScore()));
			List<String> esGlobalRiskRank = esBody.getList("hits.hits._source.globalRiskRank", String.class);
			crashedRiskDevicesGrid.getDevices().stream().forEach(device -> softAssert.assertTrue(esGlobalRiskRank.contains(device.getGlobalRiskRank()), "ES_GlobalRiskRank" + device.getGlobalRiskRank()));
			softAssert.assertAll("Asserting with the ES response");
		}
	}
}
