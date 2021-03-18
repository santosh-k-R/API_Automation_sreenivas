package com.cisco.services.api_automation.tests.assets;

import org.testng.annotations.BeforeSuite;

public class BeforeTestSuiteClassIT {

	public static String customerId;
	public static String partyId = System.getenv("partyId");
	public static String partyIdAuth=System.getenv("api_party_id");

	public static String cxcontext = "[{\"saId\":<saId>,\"vaId\":\"0\",\"customerId\":\"<customerid>\",\"solution\":\"IBN\",\"cxLevel\":\"3\"}]";
	public static String headers;

	@BeforeSuite
	public void setSystemProperty() {

		System.out.println("**************** Running Before Test Suite Method");

		if (partyId == null) {
			partyId = "112192";
		}

		customerId = partyId + "_0";
		cxcontext = cxcontext.replace("<saId>", partyIdAuth).replace("<customerid>", partyIdAuth+"_0");
		headers = "cx-context," + cxcontext;

	}

}
