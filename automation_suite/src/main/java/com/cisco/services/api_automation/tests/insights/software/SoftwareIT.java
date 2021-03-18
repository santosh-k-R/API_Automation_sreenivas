package com.cisco.services.api_automation.tests.insights.software;

import com.cisco.services.api_automation.testdata.insights.software.SoftwareData;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SoftwareIT {

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "updateProfile")
	public void verifyUpdateProfile(String endPoint, String body, String params, String ES_Query, String headers,
			String index, String customerId_exp, String profileName) {
		Response response = RestUtils.put(endPoint, body, headers, params);
		int responseCode = response.getStatusCode();
		if (responseCode == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
			SoftAssert softAssert = new SoftAssert();
			String id = responseBody.get("id");
			if (RestUtils.ES_VALIDATION) {
				JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, ES_Query).jsonPath();
				JsonPath esBodyFirstRow = esBody.setRoot("hits.hits[0]._source");
				softAssert.assertEquals(responseBody.get("customerId"), customerId_exp, "customerId : ");
				softAssert.assertEquals(responseBody.get("profileName"), profileName, "profileName : ");

				if (id == null) {
					System.out.println("Given PID is not available for that customer ID");
				} else {

					softAssert.assertEquals(esBodyFirstRow.getString("customerId"), responseBody.get("customerId"),
							"ES_customerid : ");
					softAssert.assertEquals(esBodyFirstRow.getString("profileName"), responseBody.get("profileName"),
							"ES_profileName : ");
					softAssert.assertEquals(esBodyFirstRow.getString("status"), "active", "ES_active : ");
					softAssert.assertAll("Response Validated");
				}
			} else {
				System.out.println("-------------------ES Connection is not established ------------------");
			}

		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");
		}
	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "cancelProfile")
	public void verifyCancelProfile(String endPoint, String body, String params, String ES_Query, String headers,
			String index, String customerId_exp, String profileName) {
		Response response = RestUtils.put(endPoint, body, headers, params);
		int responseCode = response.getStatusCode();
		if (responseCode == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
			SoftAssert softAssert = new SoftAssert();
			if (RestUtils.ES_VALIDATION) {
				JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, ES_Query).jsonPath();
				JsonPath esBodyFirstRow = esBody.setRoot("hits.hits[0]._source");
				softAssert.assertEquals(responseBody.get("customerId"), customerId_exp, "customerId : ");
				softAssert.assertEquals(responseBody.get("profileName"), profileName, "profileName : ");
				String id = responseBody.get("id");
				if (id == null) {
					System.out.println("Given PID is not available for that customer ID");
				} else {

					softAssert.assertEquals(esBodyFirstRow.getString("customerId"), responseBody.get("customerId"),
							"ES_customerid : ");
					softAssert.assertEquals(esBodyFirstRow.getString("profileName"), responseBody.get("profileName"),
							"ES_profileName : ");
					softAssert.assertEquals(esBodyFirstRow.getString("status"), "canceled", "ES_active : ");
					softAssert.assertAll("Response Validated");
				}

			} else {
				System.out.println("-------------------ES Connection is not established ------------------");
			}

		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");

		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "updateAdminSettings")
	public void verifyUpdateAdminSettings(String endPoint, String body, String params, String ES_Query, String headers,
			String index, String customerId_exp) {
		Response response = RestUtils.put(endPoint, body, headers, params);
		int responseCode = response.getStatusCode();

		if (responseCode == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
			SoftAssert softAssert = new SoftAssert();
			if (RestUtils.ES_VALIDATION) {
				JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, ES_Query).jsonPath();
				JsonPath esBodyFirstRow = esBody.setRoot("hits.hits[0]._source");
				String id = responseBody.get("customerId");
				String esStartDateformatted = null;
				String esLastIntevalChange = null;
				String resStartDate = null;
				String resLastIntervalChange = null;
				try {

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					java.util.Date esStart = df.parse(esBodyFirstRow.getString("startDate"));
					java.util.Date esLastInterval = df.parse(esBodyFirstRow.getString("lastIntervalChangeDate"));
					java.util.Date resStart = df.parse(responseBody.get("startDate").toString());
					java.util.Date resLast = df.parse(responseBody.get("lastIntervalChangeDate").toString());
					esStartDateformatted = new SimpleDateFormat("MMMM dd, yyyy").format(esStart);
					esLastIntevalChange = new SimpleDateFormat("MMMM dd, yyyy").format(esLastInterval);
					resStartDate = new SimpleDateFormat("MMMM dd, yyyy").format(resStart);
					resLastIntervalChange = new SimpleDateFormat("MMMM dd, yyyy").format(resLast);

				} catch (ParseException pe) {
					pe.printStackTrace();
				}

				if (id == null) {
					System.out.println("responsecsto" + responseBody.get("customerId"));

				} else {
					softAssert.assertEquals(esBodyFirstRow.getString("id"), responseBody.get("id"), "ES_id : ");

					softAssert.assertEquals(esBodyFirstRow.getString("dnacId"), responseBody.get("dnacId"),
							"ES_dnacId : ");
					softAssert.assertEquals(esStartDateformatted, resStartDate);
					System.out.println("responce Start Date   " + responseBody.get("startDate"));
					softAssert.assertEquals(esBodyFirstRow.getString("lastRecommendedDate"),
							responseBody.get("lastRecommendedDate"), "ES_lastRecommendedDate : ");
					softAssert.assertEquals(esLastIntevalChange, resLastIntervalChange);
					System.out.println(
							"responce lastIntervalChangeDate Date   " + responseBody.get("lastIntervalChangeDate"));
					softAssert.assertAll("Response Validated");
				}

			} else {
				System.out.println("-------------------ES Connection is not established ------------------");
			}
		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");

		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "adminSettings")
	public void verifyAdminSettings(String endPoint, String params, String ES_Query, String headers, String index) {

		Response response = RestUtils.get(endPoint, headers, params);
		int responseCode = response.getStatusCode();

		if (responseCode == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");

			JsonPath responseBody = response.getBody().jsonPath();
			SoftAssert softAssert = new SoftAssert();
			if (RestUtils.ES_VALIDATION) {
				JsonPath esBody = RestUtils.elasticSearchNoSqlPost(index, ES_Query).jsonPath();
				JsonPath esBodyFirstRow = esBody.setRoot("hits.hits[0]._source");
				String id = responseBody.get("customerId");
				String esStartDateformatted = null;
				String esLastIntevalChange = null;
				String resStartDate = null;
				String resLastIntervalChange = null;
				try {

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					java.util.Date esStart = df.parse(esBodyFirstRow.getString("startDate"));
					java.util.Date esLastInterval = df.parse(esBodyFirstRow.getString("lastIntervalChangeDate"));
					java.util.Date resStart = df.parse(responseBody.get("startDate").toString());
					java.util.Date resLast = df.parse(responseBody.get("lastIntervalChangeDate").toString());
					esStartDateformatted = new SimpleDateFormat("MMMM dd, yyyy").format(esStart);
					esLastIntevalChange = new SimpleDateFormat("MMMM dd, yyyy").format(esLastInterval);
					resStartDate = new SimpleDateFormat("MMMM dd, yyyy").format(resStart);
					resLastIntervalChange = new SimpleDateFormat("MMMM dd, yyyy").format(resLast);

				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				if (id == null) {
					System.out.println("responsecsto" + responseBody.get("customerId"));

				} else {

					softAssert.assertEquals(esBodyFirstRow.getString("id"), responseBody.get("id"), "ES_id : ");

					softAssert.assertEquals(esBodyFirstRow.getString("dnacId"), responseBody.get("dnacId"),
							"ES_dnacId : ");
					softAssert.assertEquals(esStartDateformatted, resStartDate);
					System.out.println("responce Start Date   " + responseBody.get("startDate"));
					softAssert.assertEquals(esBodyFirstRow.getString("lastRecommendedDate"),
							responseBody.get("lastRecommendedDate"), "ES_lastRecommendedDate : ");
					softAssert.assertEquals(esLastIntevalChange, resLastIntervalChange);
					System.out.println(
							"responce lastIntervalChangeDate Date   " + responseBody.get("lastIntervalChangeDate"));
					softAssert.assertAll("Response Validated");
				}

			} else {
				System.out.println("-------------------ES Connection is not established ------------------");
			}
		} else if (responseCode == 400) {
			Assert.assertEquals(response.getStatusCode(), 400, "Status Code:");

		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");

		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "fnDetails")
	public void fnDetails(String endPoint, String params, String headers) {

		Response response = RestUtils.get(endPoint, headers, params);
		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");
		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "bugDetails")
	public void bugDetails(String endPoint, String params, String headers) {

		Response response = RestUtils.get(endPoint, headers, params);
		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");
		}

	}

	@Test(dataProviderClass = SoftwareData.class, dataProvider = "PsirtDetails")
	public void PsirtDetails(String endPoint, String params, String headers) {

		Response response = RestUtils.get(endPoint, headers, params);

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
			JsonPath responseBody = response.getBody().jsonPath();
		} else {
			Assert.assertEquals(response.getStatusCode(), 500, "Status Code:");
		}

	}
}
