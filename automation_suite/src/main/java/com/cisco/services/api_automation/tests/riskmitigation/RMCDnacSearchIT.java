package com.cisco.services.api_automation.tests.riskmitigation;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.CrashDetailsPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.DeviceDetailsPojo;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Feature("RMC Dnac and All Search")
public class RMCDnacSearchIT {

	String neInstanceId=null;
	
	 public static String getOldDate(Integer noOfDays){
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime oldDate = now.minusDays(noOfDays);
			return oldDate.toString().split("T")[0];
		}
	 public static String getToday(){
		 DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
			LocalDateTime now = LocalDateTime.now(); 
			String today= date.format(now); 
			return today;
		}
	 
	@Severity(CRITICAL)
	@Test(groups= {"Severity2"},description = "Verifying RMC All Search API", dataProvider = "RMCMultiDnacSearch", dataProviderClass = RiskMitigationData.class)
	public void rmcAllSearch(String endPoint, String headers, String params,String index,String esQuery,String customerId_exp,String timePeriod_exp ) {
		Response response = RestUtils.get(endPoint, params, headers);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
		JsonPath responseBody = response.getBody().jsonPath();
		System.out.println("API response:-"+response.getBody().asString());
		// Validating response with the expected value from excel sheet
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(responseBody.get("customerId"), customerId_exp, "customerId : ");
		softAssert.assertEquals(responseBody.get("timePeriod"), timePeriod_exp, "timePeriod_exp : ");
		softAssert.assertAll("Validating Response from Excel");
		if(RestUtils.ES_VALIDATION){
			String days =getOldDate(Integer.parseInt(timePeriod_exp));
			esQuery =esQuery.replace("${timePeriod}", days);
			esQuery =esQuery.replace("${today}", getToday());
			JsonPath esResponse=RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
			
			System.out.println("ES ROW Count------>"+esResponse.getInt("hits.total.value"));
			CrashDetailsPojo crashedDevicesGrid = response.as(CrashDetailsPojo.class,
					ObjectMapperType.GSON);
			List<DeviceDetailsPojo> devices = crashedDevicesGrid.getDeviceDetails();
			Assert.assertEquals(esResponse.getInt("hits.total.value"), devices.size(), "ES Number of Rows: ");
			List<String> esneInstanceId = esResponse.getList("hits.hits._source.neInstanceId", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esneInstanceId.contains(device.getNeInstanceId()), "ES_NeInstanceId is not Matched with API NeInstanceId :- " + device.getNeInstanceId()));
			List<String> esNeName = esResponse.getList("hits.hits._source.neName", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esNeName.contains(device.getNeName()), "ES_neName is not Matched with API neName :- " + device.getNeName()));
			List<String> esIpAddress = esResponse.getList("hits.hits._source.ipAddress", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esIpAddress.contains(device.getIpAddress()), "ES_ipAddress is not Matched with API IP Address" + device.getIpAddress()));
			List<String> esProductFamily = esResponse.getList("hits.hits._source.productFamily", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()), "ES_productFamily is not Matched with API Product Family" + device.getProductFamily()));
			List<String> esProductId = esResponse.getList("hits.hits._source.productId", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductId.contains(device.getProductId()), "ES_productId is not Matched with API Product ID" + device.getProductId()));
			List<String> esSwVersion = esResponse.getList("hits.hits._source.swVersion", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwVersion.contains(device.getSwVersion()) ,"ES_swVersion is not Matched with API Software Version " + device.getSwVersion()));
			List<String> esSwType = esResponse.getList("hits.hits._source.swType", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwType.contains(device.getSwType()), "ES_swType is not Matched with API SW Type" + device.getSwType()));
			List<String> esSerialNumber = esResponse.getList("hits.hits._source.serialNumber", String.class);
			crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSerialNumber.contains(device.getSerialNumber()), "ES_serialNumber is not Matched with API Serial Number " + device.getSerialNumber()));
			
			softAssert.assertAll("Asserting with the ES response");

		}
}
	
	@Test(groups= {"Severity2"},description = "Verifying RMC Multi DNAC Search API", dataProvider = "RMCDNACSearch", dataProviderClass = RiskMitigationData.class)
	public void rmcDnacSearch(String endPoint, String headers,String params,String index,String esQuery,String dnacQuery,String customerId_exp,String timePeriod_exp) {
		Response response = RestUtils.get(endPoint, params, headers);
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
		JsonPath responseBody = response.getBody().jsonPath();
		// Validating response with the expected value from excel sheet
				SoftAssert softAssert = new SoftAssert();
				softAssert.assertEquals(responseBody.get("customerId"), customerId_exp, "customerId : ");
				softAssert.assertEquals(responseBody.get("timePeriod"), timePeriod_exp, "timePeriod_exp : ");
				softAssert.assertAll("Validating Response from Excel");
				if(RestUtils.ES_VALIDATION){
					CrashDetailsPojo crashedDevicesGrid = response.as(CrashDetailsPojo.class,
							ObjectMapperType.GSON);
					List<DeviceDetailsPojo> devices = crashedDevicesGrid.getDeviceDetails();
					for(DeviceDetailsPojo device1 : devices)
					{
					neInstanceId=device1.getNeInstanceId();
					}
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device ->device.getNeInstanceId());
					String days =getOldDate(Integer.parseInt(timePeriod_exp)); 
					esQuery =esQuery.replace("${timePeriod}", days);
					esQuery =esQuery.replace("${today}", getToday());
					esQuery=esQuery.replace("{$neInstanceId}",neInstanceId);
					JsonPath esResponse=RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
					Assert.assertEquals(esResponse.getInt("hits.total.value"), devices.size(), "ES Number of Rows: ");
					List<String> esneInstanceId = esResponse.getList("hits.hits._source.neInstanceId", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esneInstanceId.contains(device.getNeInstanceId()), "ES_NeInstanceId is not Matched with API NeInstanceId :- " + device.getNeInstanceId()));
					List<String> esNeName = esResponse.getList("hits.hits._source.neName", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esNeName.contains(device.getNeName()), "ES_neName is not Matched with API neName :- " + device.getNeName()));
					List<String> esIpAddress = esResponse.getList("hits.hits._source.ipAddress", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esIpAddress.contains(device.getIpAddress()), "ES_ipAddress is not Matched with API IP Address" + device.getIpAddress()));
					List<String> esProductFamily = esResponse.getList("hits.hits._source.productFamily", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()), "ES_productFamily is not Matched with API Product Family" + device.getProductFamily()));
					List<String> esProductId = esResponse.getList("hits.hits._source.productId", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductId.contains(device.getProductId()), "ES_productId is not Matched with API Product ID" + device.getProductId()));
					List<String> esSwVersion = esResponse.getList("hits.hits._source.swVersion", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwVersion.contains(device.getSwVersion()) ,"ES_swVersion is not Matched with API Software Version " + device.getSwVersion()));
					List<String> esSwType = esResponse.getList("hits.hits._source.swType", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwType.contains(device.getSwType()), "ES_swType is not Matched with API SW Type" + device.getSwType()));
					List<String> esSerialNumber = esResponse.getList("hits.hits._source.serialNumber", String.class);
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSerialNumber.contains(device.getSerialNumber()), "ES_serialNumber is not Matched with API Serial Number " + device.getSerialNumber()));
					softAssert.assertAll("Asserting with the ES response");
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device ->{
					JsonPath esDnacResponse=RestUtils.elasticSearchNoSqlPost("risk_device_dnac_mapping", dnacQuery.replace("{$neInstanceId}", neInstanceId)).jsonPath();
					Assert.assertEquals(esDnacResponse.getInt("hits.total.value"), devices.size(), "ES Number of Rows: ");
					String esDnacNeInstanceId=	esDnacResponse.get("hits.hits[0]._source.neInstanceId");
					crashedDevicesGrid.getDeviceDetails().stream().forEach(device1 -> softAssert.assertTrue(esDnacNeInstanceId.contains(device.getNeInstanceId()), "ES_Dnac_NeInstanceId is not Matched with API NeInstanceId :- " + device.getNeInstanceId()));
					softAssert.assertAll("Asserting with the Dnac ES response");
					
					});

				}
				
	}
}
