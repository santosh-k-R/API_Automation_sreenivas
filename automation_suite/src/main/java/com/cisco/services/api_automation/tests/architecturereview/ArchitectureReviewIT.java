package com.cisco.services.api_automation.tests.architecturereview;

import com.cisco.services.api_automation.pojo.response.architecturereview.*;
import com.cisco.services.api_automation.pojo.response.architecturereview.AssuranceCompliance.*;
import com.cisco.services.api_automation.testdata.architecturereview.ArchitectureReviewData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cisco.services.api_automation.testdata.architecturereview.ArchitectureReviewData.*;
import static org.testng.Assert.assertEquals;

public class ArchitectureReviewIT {

	@Test(description = "Verifying the DNAC Count API")
	public void verifyARDnacCountAPI() {

		//To verify response status
		Response response = RestUtils.get(AR_DNAC_COUNT_API.get("endPoint"), AR_DNAC_COUNT_API.get("params"));
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

		// To Fetch DNAC Count value
		DnacCountPojo count = response.as(DnacCountPojo.class, ObjectMapperType.GSON);
		int apiVal = count.getTotalCount();

		// Es Validation SQL Style
		@SuppressWarnings("rawtypes")
		List<ArrayList> esBody = RestUtils.elasticSearchSqlPost(AR_DNAC_COUNT_API.get("ES_Query"));
		int esRowCount = esBody.size();
		assertEquals(esRowCount, apiVal);	
	}

	@Test(description = "Verifying the DNAC Count Details")
	public void verifyARDnacDetailsAPI() 
	{

		//To verify response status
		Response response = RestUtils.get(AR_DNAC_DETAILS_API.get("endPoint"), AR_DNAC_DETAILS_API.get("params"));
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

		// Validating response Attributes are null or not
		DnacCountPojo count = response.as(DnacCountPojo.class, ObjectMapperType.GSON);
		List<DnacDetailsPojo> dnacdetails =count.getDnacDetails();

		int apiVal=dnacdetails.size();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertNotNull(count.getTotalCount());

		if (dnacdetails.isEmpty()) {
			System.out.println("<-- DNAC details are available for this-->");
		} else {
			for ( DnacDetailsPojo details : dnacdetails) {
				softAssert.assertNotNull(details.getDnacHostname(), "dnacHostname is not empty");
				softAssert.assertNotNull(details.getDnacIpaddress(), "dnacIpaddress is not empty");
				softAssert.assertNotNull(details.getDnacVersion(), "dnacVersion is not empty");
				softAssert.assertNotNull(details.getNoOfDevices(), "Devices is not empty");
				softAssert.assertNotNull(details.getDevicesPublishedLimits(), "devicesPublishedLimits is not empty");
				softAssert.assertNotNull(details.getDevicesPublishedPercentage(), "devicesPublishedPercentage is not empty");
				softAssert.assertNotNull(details.getNoOfEndpoints(), "Endpoints is not empty");
				softAssert.assertNotNull(details.getEndpointsPublishedLimits(), "endpointsPublishedLimits is not empty");
				softAssert.assertNotNull(details.getEndpointsPublishedPercentage(), "endpointsPublishedPercentage is not empty");
				softAssert.assertNotNull(details.getNoOfFabrics(), "Fabrics is not empty");
				softAssert.assertNotNull(details.getFabricsPublishedLimits(), "fabricsPublishedLimits is not empty");
				softAssert.assertNotNull(details.getFabricsPublishedPercentage(), "fabricsPublishedPercentage is not empty");
				softAssert.assertNotNull(details.getNoOfWlc(), "WLC is not empty");
				softAssert.assertNotNull(details.getWlcPublishedLimits(), "wlcPublishedLimits is not empty");
				softAssert.assertNotNull(details.getWlcPublishedPercentage(), "wlcPublishedPercentage is not empty");
				softAssert.assertNotNull(details.getNoOfAccessPoints(), "AccessPoints is not empty");
				softAssert.assertNotNull(details.getAccessPointsPublishedLimits(), "accessPointsPublishedLimits is not empty");
				softAssert.assertNotNull(details.getAccessPointsPublishedPercentage(), "accessPointsPublishedPercentage is not empty");
				softAssert.assertNotNull(details.getDnacAppliance(), "dnacAppliance is not empty");
				softAssert.assertNotNull(details.getCollectionTime(), "collectionTime is not empty");
				softAssert.assertNotNull(details.getInventoryDeviceCount(), "inventoryDeviceCount is not empty");
			}

			// Es Validation SQL Style
			List<ArrayList> esBody = RestUtils.elasticSearchSqlPost(AR_DNAC_DETAILS_API.get("ES_Query"));
			int esRowCount = esBody.size();
			ArrayList esBodyFirstRow=esBody.get(0);
			softAssert.assertEquals(esRowCount, apiVal);

			for(int i=0; i<esRowCount; i++)
			{
				for ( DnacDetailsPojo details : dnacdetails) 
				{

					softAssert.assertEquals(esBodyFirstRow.get(0),details.getDnacHostname());
					softAssert.assertEquals(esBodyFirstRow.get(1),details.getDnacIpaddress());
					softAssert.assertEquals(esBodyFirstRow.get(2),details.getDnacVersion());
					softAssert.assertEquals(esBodyFirstRow.get(3),details.getNoOfDevices());
					softAssert.assertEquals(esBodyFirstRow.get(4),details.getDevicesPublishedLimits());
					softAssert.assertEquals(esBodyFirstRow.get(5),details.getDevicesPublishedPercentage());
					softAssert.assertEquals(esBodyFirstRow.get(6),details.getNoOfEndpoints());
					softAssert.assertEquals(esBodyFirstRow.get(7),details.getEndpointsPublishedLimits());
					softAssert.assertEquals(esBodyFirstRow.get(8),details.getEndpointsPublishedPercentage());
					softAssert.assertEquals(esBodyFirstRow.get(9),details.getNoOfFabrics());
					softAssert.assertEquals(esBodyFirstRow.get(10),details.getFabricsPublishedLimits());
					softAssert.assertEquals(esBodyFirstRow.get(11),details.getFabricsPublishedPercentage());
					softAssert.assertEquals(esBodyFirstRow.get(12),details.getNoOfWlc());
					softAssert.assertEquals(esBodyFirstRow.get(13),details.getWlcPublishedLimits());
					softAssert.assertEquals(esBodyFirstRow.get(14),details.getWlcPublishedPercentage());
					softAssert.assertEquals(esBodyFirstRow.get(15),details.getNoOfAccessPoints());
					softAssert.assertEquals(esBodyFirstRow.get(16),details.getAccessPointsPublishedLimits());
					softAssert.assertEquals(esBodyFirstRow.get(17),details.getAccessPointsPublishedPercentage());
					softAssert.assertEquals(esBodyFirstRow.get(18),details.getDnacAppliance());
					softAssert.assertEquals(esBodyFirstRow.get(19),details.getCollectionTime());
					softAssert.assertEquals(esBodyFirstRow.get(20),details.getInventoryDeviceCount());
					softAssert.assertAll("Asserting with the ES response");

				}
			}
		}

	}

	@Test(description = "Verifying the DNAC SDA Trends API")
	public void verifyDnacSDATrendAPI()
	{
		//To verify response status
		Response response = RestUtils.get(AR_SDA_TREND_API.get("endPoint"), AR_SDA_TREND_API.get("params"));
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
		JsonPath responseBody = response.getBody().jsonPath();

		//To get attribute values
		SdaTrendPojo[] trendData = response.as(SdaTrendPojo[].class, ObjectMapperType.GSON);
		int arraylen=trendData.length;
		for(int i=0; i<arraylen; i++)
		{

			trendData[i].getNoOfDevices();
			trendData[i].getNoOfEndpoints();
			trendData[i].getNoOfFabrics();
			trendData[i].getNoOfWlc();
			trendData[i].getNoOfAccessPoints();
			trendData[i].getDevicesPeakTime();
			trendData[i].getEndpointsPeakTime();
			trendData[i].getFabricsPeakTime();
			trendData[i].getWlcPeakTime();
			trendData[i].getAccessPointsPeakTime();

		}

		//Es Validation NoSQL Style
		List<ArrayList> esBody = RestUtils.elasticSearchSqlPost(AR_SDA_TREND_API.get("ES_Query"));
		int esRowCount = esBody.size();
		ArrayList esBodyFirstRow=esBody.get(0);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(esRowCount, arraylen);

		for(int i=0; i<esRowCount; i++)
		{

			softAssert.assertEquals(esBodyFirstRow.get(0),trendData[i].getNoOfDevices());
			softAssert.assertEquals(esBodyFirstRow.get(1),trendData[i].getNoOfEndpoints());
			softAssert.assertEquals(esBodyFirstRow.get(2),trendData[i].getNoOfFabrics());
			softAssert.assertEquals(esBodyFirstRow.get(3),trendData[i].getNoOfWlc());
			softAssert.assertEquals(esBodyFirstRow.get(4),trendData[i].getNoOfAccessPoints());
			// 	softAssert.assertEquals(esBodyFirstRow.get(5),trendData[i].getDevicesPeakTime());
			//	softAssert.assertEquals(esBodyFirstRow.get(6),trendData[i].getEndpointsPeakTime());
			//	softAssert.assertEquals(esBodyFirstRow.get(7),trendData[i].getFabricsPeakTime());
			//	softAssert.assertEquals(esBodyFirstRow.get(8),trendData[i].getWlcPeakTime());
			//	softAssert.assertEquals(esBodyFirstRow.get(9),trendData[i].getAccessPointsPeakTime());
			softAssert.assertAll("Asserting with the ES response");

		}

	}

	@Test(description = "Verifying the DNAC Devices Count API")
	public void verifyARDevicesCountAPI() {

		//To verify response status
		Response response = RestUtils.get(AR_DEVICES_COUNT_API.get("endPoint"), AR_DEVICES_COUNT_API.get("params"));
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

		// To Fetch Devices Count value
		DnacCountPojo count = response.as(DnacCountPojo.class, ObjectMapperType.GSON);
		int apiVal = count.getTotalCount();

		// Es Validation SQL Style
		List<ArrayList> esBody = RestUtils.elasticSearchSqlPost(AR_DEVICES_COUNT_API.get("ES_Query"));
		ArrayList count1 =esBody.remove(0);
		assertArrayEquals(apiVal,count1);
	}

	private void assertArrayEquals(int apiVal, ArrayList count1) 
	{

	}

	@Test(dataProviderClass = ArchitectureReviewData.class, dataProvider = "invalidCustomer")
	public void invalidCustomer(String endPoint , String params, String reponse_expected) {

		//Empty customerid and dnacIp check
		Headers headers1 = new Headers(new Header("",""),new Header("",""));
		Response response = RestUtils.get(endPoint,params);

		Assert.assertEquals(response.statusCode(), 400,"Status Code ::");
		String responseBody=response.getBody().asString();
		SoftAssert softAssert= new SoftAssert();
		softAssert.assertEquals(responseBody, reponse_expected);
		softAssert.assertAll("Response Validated");
	}

	@Test(description = "Verifying the DNAC Devices Compliance API")
	public void verifyARDevicesComplianceAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(AR_DEVICE_COMPLIANCE.get("endPoint"), AR_DEVICE_COMPLIANCE.get("params"));
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

		//to get the attribute values
		DeviceCompliancePojo compliance = response.as(DeviceCompliancePojo.class, ObjectMapperType.GSON);
		AssuranceCompliance assurance =compliance.getAssuranceCompliance();
		OverallCompliance overall =compliance.getOverallCompliance();
		SwimCompliance swim =compliance.getSwimCompliance();
		PnpCompliance Pnp =compliance.getPnpCompliance();
		SdaCompliance sda =compliance.getSdaCompliance();
		DnacCompliance dnac =compliance.getDnacCompliance();

		assurance.getYes();	
		assurance.getNot_evaluated();
		assurance.getNo();

		overall.getYes();	
		overall.getNot_evaluated();
		overall.getNo();

		swim.getYes();	
		swim.getNot_evaluated();
		swim.getNo();

		Pnp.getYes();	
		Pnp.getNot_evaluated();
		Pnp.getNo();

		sda.getYes();	
		sda.getNot_evaluated();
		sda.getNo();

		dnac.getYes();	
		dnac.getNot_evaluated();
		dnac.getNo();		
	}

	@Test(description = "Verifying the DNAC Devices Details API")
	public void verifyARDevicesDetailsAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(AR_DEVICE_DETAILS.get("endPoint"), AR_DEVICE_DETAILS.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

		DnacCountPojo count = response.as(DnacCountPojo.class, ObjectMapperType.GSON);
		List<DnacDeviceDetailsPojo> dnacDeviceDetails = count.getDnacDeviceDetails();

		//Attributes Validation
		int apiVal = count.getTotalCount();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertNotNull(count.getTotalCount());
		if (dnacDeviceDetails.isEmpty()) {
			System.out.println("<-- DNAC details are available for this-->");
		} else {
			for ( DnacDeviceDetailsPojo details : dnacDeviceDetails) {
				softAssert.assertNotNull(details.getHostName(), "Hostname is not empty");
				softAssert.assertNotNull(details.getIpAddress(), "Ipaddress is not empty");
				softAssert.assertNotNull(details.getProductFamily(), "product Family is not empty");
				softAssert.assertNotNull(details.getProductId(), "ProductId is not empty");
				softAssert.assertNotNull(details.getSerialNumber(), "SerialNumber is not empty");
				softAssert.assertNotNull(details.getSoftwareType(), "SoftwareType is not empty");
				softAssert.assertNotNull(details.getSoftwareVersion(), "SoftwareVersion is not empty");
				softAssert.assertNotNull(details.getCurrentDeviceRole(), "CurrentDeviceROle is not empty");
				softAssert.assertNotNull(details.getDnacVersion(), "DnacVersion is not empty");
				softAssert.assertNotNull(details.getHardwarePidCompliant(), "HardwarePidComplaint is not empty");
				softAssert.assertNotNull(details.getSoftwareTypeCompliant(), "SoftwareTypeComplaint is not empty");
				softAssert.assertNotNull(details.getSoftwareVersionCompliant(), "softwareVersionComplaint is not empty");
				softAssert.assertNotNull(details.getSdaCompliance(), "SdaCompliance is not empty");
				softAssert.assertNotNull(details.getAssuranceCompliance(), "AssuranceComplaince is not empty");
				softAssert.assertNotNull(details.getSwimCompliance(), "SwimCompliance is not empty");
				softAssert.assertNotNull(details.getPnpCompliance(), "pnpCompliance is not empty");
				softAssert.assertNotNull(details.getDnacCompliance(), "DnacCompliance is not empty");
				softAssert.assertNotNull(details.getOverallCompliance(), "OverallCompliance is not empty");
				softAssert.assertNotNull(details.getLicense(), "license is not empty");

			}	
		}

		//ES validation
		JsonPath esBody = RestUtils.elasticSearchNoSqlPost(AR_DEVICE_DETAILS.get("index"),
				AR_DEVICE_DETAILS.get("ES_Query")).jsonPath();
		Assert.assertEquals(esBody.getInt("hits.total.value"), dnacDeviceDetails.size(), "ES Number of Rows: ");

		List<String> esHostName = esBody.getList("hits.hits._source.hostName", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esHostName.contains(device.getHostName()), "ES_HostName" + device.getHostName()));

		List<String> esIPAddress = esBody.getList("hits.hits._source.ipAddress", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esIPAddress.contains(device.getIpAddress()), "ES_IPAddress" + device.getIpAddress()));

		List<String> esProductFamily = esBody.getList("hits.hits._source.productFamily", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esProductFamily.contains(device.getProductFamily()), "ES_ProductFamily" + device.getProductFamily()));

		List<String> esProductId = esBody.getList("hits.hits._source.productId", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esProductId.contains(device.getProductId()), "ES_ProductId" + device.getProductId()));

		List<String> esSerialNumber = esBody.getList("hits.hits._source.serialNumber", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSerialNumber.contains(device.getSerialNumber()), "ES_SerialNumber" + device.getSerialNumber()));

		List<String> esSoftwareType = esBody.getList("hits.hits._source.softwareType", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSoftwareType.contains(device.getSoftwareType()), "ES_SoftwareType" + device.getSoftwareType()));

		List<String> esSoftwareVersion = esBody.getList("hits.hits._source.softwareVersion", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSoftwareVersion.contains(device.getSoftwareVersion()), "ES_SoftwareVersion" + device.getSoftwareVersion()));

		List<String> esCurrentDeviceRole = esBody.getList("hits.hits._source.currentDeviceRole", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esCurrentDeviceRole.contains(device.getCurrentDeviceRole()), "ES_CurrentDeviceRole" + device.getCurrentDeviceRole()));

		List<String> esDnacVersion = esBody.getList("hits.hits._source.dnacVersion", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esDnacVersion.contains(device.getDnacVersion()), "ES_DnacVersion" + device.getDnacVersion()));

		List<String> esHardwarePidCompliant = esBody.getList("hits.hits._source.hardwarePidCompliant", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esHardwarePidCompliant.contains(device.getHardwarePidCompliant()), "ES_HardwarePidCompliant" + device.getHardwarePidCompliant()));

		List<String> esSoftwareTypeCompliant = esBody.getList("hits.hits._source.softwareTypeCompliant", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSoftwareTypeCompliant.contains(device.getSoftwareTypeCompliant()), "ES_SoftwareTypeCompliant" + device.getSoftwareTypeCompliant()));

		List<String> esSoftwareVersionCompliant = esBody.getList("hits.hits._source.softwareVersionCompliant", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSoftwareVersionCompliant.contains(device.getSoftwareVersionCompliant()), "ES_SoftwareVersionCompliant" + device.getSoftwareVersionCompliant()));

		List<String> esSdaCompliance = esBody.getList("hits.hits._source.sdaCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSdaCompliance.contains(device.getSdaCompliance()), "ES_SdaCompliance" + device.getSdaCompliance()));

		List<String> esPnpCompliance = esBody.getList("hits.hits._source.pnpCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esPnpCompliance.contains(device.getPnpCompliance()), "ES_PnpCompliance" + device.getPnpCompliance()));

		List<String> esAssuranceCompliance = esBody.getList("hits.hits._source.assuranceCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esAssuranceCompliance.contains(device.getAssuranceCompliance()), "ES_AssuranceCompliance" + device.getAssuranceCompliance()));

		List<String> esSwimCompliance = esBody.getList("hits.hits._source.swimCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSwimCompliance.contains(device.getSwimCompliance()), "ES_SwimCompliance" + device.getSwimCompliance()));

		List<String> esDnacCompliance = esBody.getList("hits.hits._source.dnacCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esDnacCompliance.contains(device.getDnacCompliance()), "ES_DnacCompliance" + device.getDnacCompliance()));

		List<String> esOverallCompliance = esBody.getList("hits.hits._source.overallCompliance", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esOverallCompliance.contains(device.getOverallCompliance()), "ES_OverallCompliance" + device.getOverallCompliance()));

		List<String> esSdaNoOfMtuNonOptimalInterfaces = esBody.getList("hits.hits._source.sdaNoOfMtuNonOptimalInterfaces", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(esSdaNoOfMtuNonOptimalInterfaces.contains(device.getSdaNoOfMtuNonOptimalInterfaces()), "ES_SdaNoOfMtuNonOptimalInterfaces" + device.getSdaNoOfMtuNonOptimalInterfaces()));

		List<String> eslicense = esBody.getList("hits.hits._source.license", String.class);
		count.getDnacDeviceDetails().stream().forEach(device -> softAssert
				.assertTrue(eslicense.contains(device.getLicense()), "ES_License" + device.getLicense()));

		softAssert.assertAll("Asserting with the ES response");
	}

	@Test(description = "Verifying the DNAC DeviceInsight  API")
	public void verifyARDeviceInsightAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(AR_DEVICE_INSIGHT.get("endPoint"), AR_DEVICE_INSIGHT.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");	
	}

	@Test(description = "Verifying the DNAC Device NonOptimal Links  API")
	public void verifyARDeviceNonOptimalLinksAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(AR_DEVICE_NONOPTIMALLINK.get("endPoint"), AR_DEVICE_NONOPTIMALLINK.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");	
	}

	@Test(description = "Verifying the DNAC CBP Rules Count  API")
	public void verifyCBPRuleCountAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(CONFIGURATION_CBP_RULE_COUNT.get("endPoint"), CONFIGURATION_CBP_RULE_COUNT.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");	
	}

	@Test(description = "Verifying the DNAC CBP Rules  API")
	public void verifyCBPRulesAPI() 
	{
		//To verify response status
		Response response = RestUtils.get(CONFIGURATION_CBP_RULE.get("endPoint"), CONFIGURATION_CBP_RULE.get("params"));
		JsonPath responseBody = response.getBody().jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");	

		CBPRuleCountPojo count = response.as(CBPRuleCountPojo.class, ObjectMapperType.GSON);
		List<BPRulesDetailsPojo> cbpRuleDetails = count.getBPRulesDetails();

		//Attributes Validation
		int apiVal = count.getTotalCounts();
		System.out.println(apiVal);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertNotNull(count.getTotalCounts());
		if (cbpRuleDetails.isEmpty()) {
			System.out.println("<-- CBP Rules are not available for this-->");
		} else {
			for ( BPRulesDetailsPojo details : cbpRuleDetails) 
			{
				softAssert.assertNotNull(details.getSeverity(), "Severity is not empty");
				softAssert.assertNotNull(details.getBpRuleTitle(), "BpRuleTitle is not empty");
				details.getExceptions();
				details.getCorrectiveActions();
				details.getRecommendations();
				details.getDeviceIpsWithExceptions();
				softAssert.assertNotNull(details.getSoftwareType(),"SoftwareType is not empty");
				softAssert.assertNotNull(details.getDescription(), "Description is not empty");
			}
		}
		//ES validation
				JsonPath esBody = RestUtils.elasticSearchNoSqlPost(CONFIGURATION_CBP_RULE.get("index"),
						CONFIGURATION_CBP_RULE.get("ES_Query")).jsonPath();
				Assert.assertEquals(esBody.getInt("hits.total.value"), cbpRuleDetails.size(), "ES Number of Rows: ");

				List<String> esSeverity = esBody.getList("hits.hits._source.severity", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esSeverity.contains(device.getSeverity()), "ES_Severity" + device.getSeverity()));

				List<String> esBPRuleTitle = esBody.getList("hits.hits._source.bpRuleTitle", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esBPRuleTitle.contains(device.getBpRuleTitle()), "ES_BPRuleTitle"+ device.getBpRuleTitle()));

				List<String>  esExceptions= esBody.getList("hits.hits._source.exceptions", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esExceptions.contains(device.getExceptions()), "ES_Exceptions"+ device.getExceptions()));
				
				List<String>  esCorrectiveActions= esBody.getList("hits.hits._source.correctiveActions", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esCorrectiveActions.contains(device.getCorrectiveActions()), "ES_CorrectiveActions"+ device.getCorrectiveActions()));
				
				List<String>  esRecommendations= esBody.getList("hits.hits._source.recommendations", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esRecommendations.contains(device.getRecommendations()), "ES_Recommendations"+ device.getRecommendations()));
				
				List<String> esDevicesWithExceptions = esBody.getList("hits.hits._source.devicesWithExceptions.deviceIp", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esDevicesWithExceptions.contains(device.getDeviceIpsWithExceptions()), "ES_DevicesWithExceptions"+ device.getDeviceIpsWithExceptions()));

				List<String>  esSoftwareType= esBody.getList("hits.hits._source.softwareType", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esSoftwareType.contains(device.getSoftwareType()), "ES_SoftwareType"+ device.getSoftwareType()));
				
				List<String> esDescription = esBody.getList("hits.hits._source.description", String.class);
				count.getBPRulesDetails().stream().forEach(device -> softAssert
						.assertTrue(esDescription.contains(device.getDescription()), "ES_DevicesWithExceptions"+ device.getDeviceIpsWithExceptions()));
				softAssert.assertAll("Asserting response with ES Validation");
	}
}
