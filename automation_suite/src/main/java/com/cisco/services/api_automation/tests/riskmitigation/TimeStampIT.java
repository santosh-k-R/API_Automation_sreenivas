package com.cisco.services.api_automation.tests.riskmitigation;

import com.cisco.services.api_automation.pojo.response.RiskMitigation.FPTimeStampPojo;
import com.cisco.services.api_automation.testdata.riskmitigation.RiskMitigationData;
import com.cisco.services.api_automation.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;

@Feature("FP AND RMC TimeStamp")
public class TimeStampIT {

	@Severity(CRITICAL)
	@Test(groups= {"Severity2"},description = "Verifying the Systems and Crashed Systems Time Stamp API's", dataProvider = "TimeStamp", dataProviderClass = RiskMitigationData.class)
	public void verifyFPAndRmcTimeStampAPI(String endPoint,String headers, String esQuery, String customerID_exp, String status_exp) {
		Headers header = new Headers(new Header("cx-context",headers));
		Response response=	RestUtils.get(endPoint,header);
		System.out.println("API Response----------->"+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
		JsonPath responseBody = response.getBody().jsonPath();

		// Validating response with the expected value from excel sheet

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(responseBody.get("customerId"), customerID_exp, "customerId : ");
		softAssert.assertEquals(responseBody.get("status"), status_exp, "status : ");

		// Validating the response API attributes are not null

		FPTimeStampPojo timeStamp = response.as(FPTimeStampPojo.class, ObjectMapperType.GSON);
		softAssert.assertTrue(!timeStamp.getCustomerId().isEmpty(), "Customer ID is not empty");
		softAssert.assertTrue(!timeStamp.getStartTime().isEmpty(), "startTime is not empty");
		softAssert.assertTrue(!timeStamp.getEndTime().isEmpty(), "endTime is not empty");
		softAssert.assertTrue(!timeStamp.getStatus().isEmpty(), "status is not empty");
		softAssert.assertAll("Asserting API Response Attribute not null");
		if(RestUtils.ES_VALIDATION){
			// Es Validation SQL Style
			List<ArrayList> esBody = RestUtils.elasticSearchSqlPost(esQuery);
			int esRowCount = esBody.size();
			ArrayList esBodyLatUpdatedTime = esBody.get(esRowCount - 1);
			String esStartTimeformatted = null;
			String esEndTimeformatted = null;
			try {
				// Converting the input String to Date
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				// Changing the format of date and storing it in String
				java.util.Date dateObj = df.parse(esBodyLatUpdatedTime.get(0).toString());
				java.util.Date dateObj1 = df.parse(esBodyLatUpdatedTime.get(1).toString());
				esStartTimeformatted = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss").format(dateObj);
				esEndTimeformatted = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss").format(dateObj1);
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
			softAssert.assertTrue(esStartTimeformatted.equalsIgnoreCase(responseBody.get("startTime")));
			softAssert.assertTrue(esEndTimeformatted.equalsIgnoreCase(responseBody.get("endTime")));
			softAssert.assertEquals(esBodyLatUpdatedTime.get(2), responseBody.get("status"));

		}
		softAssert.assertAll("Asserting API Response with Elastic Search");
	}
	@Severity(MINOR)
	@Test(groups= {"Severity3"},description = "Verifying the Crashed Systems Time Stamp API's with Invalid CustomerId")
	public void verifyRMCTimeStampInvalidCustomerId() {
		Response response = RestUtils.get("/rmc/v1/last-successful-execution-info/1070_0");
		Assert.assertEquals(response.getStatusCode(), 401, "Status Code:");

		
	}
}
