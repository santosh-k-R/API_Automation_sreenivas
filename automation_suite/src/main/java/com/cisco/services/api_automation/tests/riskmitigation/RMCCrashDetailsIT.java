package com.cisco.services.api_automation.tests.riskmitigation;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.CrashDetailsPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.DeviceDetailsPojo;
import com.cisco.services.api_automation.pojo.response.RiskMitigation.FrequentDeviceListPojo;
import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData.RISK_MITIGATION_RMC_INVALID_TIMEPERIOD;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.MINOR;

@Feature("Crash Details")
public class RMCCrashDetailsIT {
	
	List<DeviceDetailsPojo> deviceDetails= null;
	String formatedDate=null;

    public String getOldDate(Integer noOfDays){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oldDate = now.minusDays(noOfDays);
		return oldDate.toString().split("T")[0];
	}
	public String dateFormatter(String fetchedDate){
		String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		String outputPattern = "MMMM dd, yyyy HH:mm:ss";
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, Locale.ENGLISH);
		LocalDateTime inputDate = LocalDateTime.parse(fetchedDate, inputFormatter);
		return outputFormatter.format(inputDate);
	}
    
   @Severity(BLOCKER)
	@Test(groups= {"Severity1"},dataProvider="CrashDetails",dataProviderClass = RiskMitigationData.class)
	public void verifyCrashDetailsAPI(String endPoint,String params,String headers,String index,String esQuery,String customerId_exp,String timePeriod_exp) {
		Response response = RestUtils.get(endPoint,headers,params);
		Assert.assertEquals(response.getStatusCode(), 200,"Status code is not  matched");
		String days =getOldDate(Integer.parseInt(timePeriod_exp));
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now(); 
		String today= date.format(now); 
		esQuery =esQuery.replace("${timePeriod}", days);
		esQuery =esQuery.replace("${today}", today);
		if(RestUtils.ES_VALIDATION){
			JsonPath esBody=RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
			CrashDetailsPojo crashDetails = response.as(CrashDetailsPojo.class, ObjectMapperType.GSON);
			deviceDetails = crashDetails.getDeviceDetails();
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertEquals(crashDetails.getCustomerId(),customerId_exp, "customerId : ");
			softAssert.assertEquals(crashDetails.getTimePeriod(),timePeriod_exp, "timePeriod : ");
			if (deviceDetails.isEmpty()) {
				System.out.println("Device Details in Crash Details API Response is Empty  for Time Period : "+timePeriod_exp + " Days Time Range filter");
			}
			else {
				List<String> esNeInstanceId = esBody.getList("hits.hits._source.neInstanceId", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esNeInstanceId.contains(device.getNeInstanceId()), "ES_neInstanceId is not Matched with API neInstanceId " + device.getNeInstanceId()));
				List<String> esNeName = esBody.getList("hits.hits._source.neName", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esNeName.contains(device.getNeName()), "ES_neName is not Matched with API neName :- " + device.getNeName()));
				List<String> esIpAddress = esBody.getList("hits.hits._source.ipAddress", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esIpAddress.contains(device.getIpAddress()), "ES_ipAddress is not Matched with API IP Address" + device.getIpAddress()));
				List<String> esProductFamily = esBody.getList("hits.hits._source.productFamily", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductFamily.contains(device.getProductFamily()), "ES_productFamily is not Matched with API Product Family" + device.getProductFamily()));
				List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esProductId.contains(device.getProductId()), "ES_productId is not Matched with API Product ID" + device.getProductId()));
				List<String> esSwVersion = esBody.getList("hits.hits._source.swVersion", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwVersion.contains(device.getSwVersion()) ,"ES_swVersion is not Matched with API Software Version " + device.getSwVersion()));
				List<String> esSwType = esBody.getList("hits.hits._source.swType", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSwType.contains(device.getSwType()), "ES_swType is not Matched with API SW Type" + device.getSwType()));
				List<String> esSerialNumber = esBody.getList("hits.hits._source.serialNumber", String.class);
				crashDetails.getDeviceDetails().stream().forEach(device -> softAssert.assertTrue(esSerialNumber.contains(device.getSerialNumber()), "ES_serialNumber is not Matched with API Serial Number " + device.getSerialNumber()));
			}
			softAssert.assertAll("Asserting with the ES response");
		}
}
    
   @Severity(MINOR)
    @Test(dataProvider="FrequentCrashDetails",dataProviderClass = RiskMitigationData.class)
    public void verifyFrequentCrashDetails360API(String endPoint,String params,String headers,String index,String esQuery,String customerId_exp,String deviceId_exp){
    	Response response = RestUtils.get(endPoint,headers,params);
		Assert.assertEquals(response.getStatusCode(), 200,"Status code is not  matched");
		FrequentDeviceListPojo crashDetails = response.as(FrequentDeviceListPojo.class, ObjectMapperType.GSON);
		JsonPath esBody=RestUtils.elasticSearchNoSqlPost(index, esQuery).jsonPath();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(crashDetails.getCustomerId(),customerId_exp, "customerId : ");
		softAssert.assertEquals(crashDetails.getDeviceId(),deviceId_exp, "customerId : ");
		List<String> esResetReason = esBody.getList("hits.hits._source.resetReason", String.class);
		crashDetails.getCrashes().stream().forEach(device -> softAssert.assertTrue(esResetReason.contains(device.getResetReason()), "ES_ResetReason is not Matched with API ResetReason " + device.getResetReason()));
		List<String> esLastReset = esBody.getList("hits.hits._source.lastReset", String.class);
		int esLastResetCount=esLastReset.size();
		if(esLastResetCount>0) {
			for(int i=0;i<esLastResetCount;i++) {
			formatedDate=dateFormatter(esLastReset.get(i).toString());
				
				crashDetails.getCrashes().stream().forEach(device -> softAssert.assertTrue(formatedDate.contains(device.getTimeStamp()), "ES_LastReset is not Matched with API LastReset " + device.getTimeStamp()));
			}	
		}
		softAssert.assertAll("Asserting with the ES response");
    }
   @Severity(MINOR)
   @Test()
   public void verifyInvalidTimePeriod() {
	   Response response = RestUtils.get(RISK_MITIGATION_RMC_INVALID_TIMEPERIOD.get("endPoint"), RISK_MITIGATION_RMC_INVALID_TIMEPERIOD.get("headers"), RISK_MITIGATION_RMC_INVALID_TIMEPERIOD.get("params"));
	   Assert.assertEquals(response.getStatusCode(), 400,"Status code is not  matched");
   }
   
    
}
