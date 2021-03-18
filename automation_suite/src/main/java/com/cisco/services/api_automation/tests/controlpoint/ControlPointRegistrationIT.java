package com.cisco.services.api_automation.tests.controlpoint;


import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.auth.User;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;

public class ControlPointRegistrationIT {

    private String replaceCustomerIdString = "<REPLACE_customerId_STRING>";

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/controlpoint/ctrlpt/v1/registration/ie/{customerId}")
    public void verifyRegistraionOfCustomer() {

        Response response  = RestUtils.get(registrationDataDp.getEndPoint().replace(replaceCustomerIdString,registrationDataDp.getCustomerId()),new User("MACHINE"));
        JsonPath jsonPath=response.getBody().jsonPath();
        System.out.println("Response:"+response.getBody().asString());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue((jsonPath.getList("dnacs").size())>0  ,"DNAC info not available, day 0 is not completed");
        softAssert.assertEquals(response.getStatusCode(),healthStatusDataDp.getResponseCode());
        softAssert.assertEquals(jsonPath.getList("customerId").get(0).toString(),registrationDataDp.getCustomerId(),"CustomerId not matching");
        softAssert.assertTrue(((boolean)jsonPath.getList("ieSetupCompleted").get(0)),"ieSetUp is not completed for customer");

        softAssert.assertAll();
    }


    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "controlpoint/ctrlpt/v1/ie/health-status/GZqEJtZygXHKNZ")
    public void verifyHealthStatus() {

        Response response  = RestUtils.get(healthStatusDataDp.getEndPoint().replace(replaceCustomerIdString,healthStatusDataDp.getCustomerId()),new User("MACHINE"));
        JsonPath jsonPath=response.getBody().jsonPath();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(jsonPath.getList("$").size()>0,"Health Status is empty");
        softAssert.assertEquals(response.getStatusCode(),healthStatusDataDp.getResponseCode());

        softAssert.assertAll();

    }

}
