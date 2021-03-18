package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.pojo.request.UserRolesPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;


@Feature("HealthStatus")
public class CustomerOnboardingIT {

    private static final String DIR = ControlPointData.getDIR();
    private List<UserRolesPojo> userRolesPojoList;
    private String superAdmin = "SUPERADMIN";
    private String admin = "ADMIN";

    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyCustomersGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/{customerId}/{ccoId}}")
    public void VerifyCustomersGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyCustomersGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"regression"},dataProvider = "VerifyCustomersGETInvalidCustomerIdDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/{InvalidcustomerId}/{ccoId}}")
    public void VerifyCustomersGETInvalidCustomerId(ControlPointRequestPojo.data data){

        Response response = RestUtils.get(VerifyCustomersGETInvalidCustomerIdDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }

    @Test(enabled=true, groups = {"regression"},dataProvider = "VerifyCustomersGETInvalidccoIdDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/{validcustomerId}/{invalid ccoId}}")
    public void VerifyCustomersGETInvalidccoId(ControlPointRequestPojo.data data){

        Response response = RestUtils.get(VerifyCustomersGETInvalidccoIdDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();
        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAllCustomersGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/all/{validCustomerId}")
    public void VerifyAllCustomersGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAllCustomersGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAllCustomersCAVIDGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/all/{validcavId}")
    public void VerifyAllCustomersCAVIDGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAllCustomersCAVIDGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"regression"}, dataProvider = "VerifyAllCustomersInvalidCAVIDGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/all/{InvalidcavId}")
    public void VerifyAllCustomersInvalidCAVIDGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAllCustomersInvalidCAVIDGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAllCustomersCavBuIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/all/{validcavBuId}")
    public void VerifyAllCustomersCavBuIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAllCustomersCavBuIdGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }
    @Test(enabled=true, groups = {"sanity"}, dataProvider = "VerifyAllCustomersInvalidCavBuIdGETAPIDp",
            dataProviderClass = ControlPointData.class, description = "customerservice/v1/customers/all/{InvalidcavBuId}")
    public void VerifyAllCustomersInvalidCavBuIdGETAPI(ControlPointRequestPojo.data data) {

        Response response = RestUtils.get(VerifyAllCustomersInvalidCavBuIdGETAPIDp.getEndPoint(),
                Commons.getHeaderForCxContext(controlPointCommonData.getHeaders()));
        Assert.assertEquals(response.getStatusCode(), 404, "Status Code:");
        JsonPath responseBody = response.getBody().jsonPath();

        System.out.println("API response is :- " + response.then().log().all());
    }

}



