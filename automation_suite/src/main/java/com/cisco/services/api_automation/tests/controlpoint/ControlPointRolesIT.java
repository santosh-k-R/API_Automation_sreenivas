package com.cisco.services.api_automation.tests.controlpoint;

import com.cisco.services.api_automation.pojo.request.ControlPointRequestPojo;
import com.cisco.services.api_automation.pojo.request.UserRolesPojo;
import com.cisco.services.api_automation.testdata.controlpoint.ControlPointData;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.RestUtils;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.cisco.services.api_automation.testdata.controlpoint.ControlPointData.*;
import static com.cisco.services.api_automation.testdata.lifecycle.LifeCycleData.ACC_GET_INVALID_PARAMS;
import static com.cisco.services.api_automation.utils.RestUtils.BASE_URI;
import static com.cisco.services.api_automation.utils.auth.AuthUtils.getXMasheryHandshake;
import static io.restassured.RestAssured.given;





@Feature("HealthStatus")
public class ControlPointRolesIT {
    private String replaceAssignString = "<REPLACE_assign_STRING>";


    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/v1/nrs/roles-permissions/roles?assign=boolean",dataProvider = "verifyRolesDp",dataProviderClass = ControlPointData.class)
    public void verifyAllAvailableRolesForCXCP(ControlPointRequestPojo.data data) {
        SoftAssert softAssert = new SoftAssert();
        Response res =  RestUtils.get(data.getEndPoint(),data.getPathParams().replace(replaceAssignString,data.getAssign()));

        System.out.println(data.getEndPoint()+"?"+data.getPathParams().replace(replaceAssignString,data.getAssign()));
        if(data.getTestType().equals("valid")) {
             softAssert.assertEquals(res.getStatusCode(),data.getResponseCode(),"API Status code is not matching");
             softAssert.assertEquals(res.getBody().jsonPath().getList("findAll{it.assignable==true}.roleName").size(),3,"Assignable Roles Not matching");
        } else {
            softAssert.assertEquals(res.getStatusCode(),data.getResponseCode(),"API Status code is not matching");
          //  softAssert.assertEquals(res.getBody().jsonPath().getList("findAll{it.assignable==true}.roleName"),5,"Assignable Roles Not matching");
        }

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "/v1/nrs/roles-permissions/roles")
    public void verifyAllRoles() {
        SoftAssert softAssert = new SoftAssert();
        Response res =  RestUtils.get(allRolesDp.getEndPoint());

        List<String> rolesList= res.getBody().jsonPath().getList("roleName");
        softAssert.assertEquals(rolesList.size(),5,"Roles Count not matching, some of the roles are missing");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(enabled = true, groups = {"regression"},description = "controlpoint/ctrlpt/v1/nrs/roles-permissions")
    public void verifyDataForRoles() {
        SoftAssert softAssert = new SoftAssert();
        Response res= RestUtils.get(dataForRolesDp.getEndPoint());
        softAssert.assertEquals(res.getStatusCode(),dataForRolesDp.getResponseCode(),"DataforRoles api /v1/nrs/roles-permissions is not working");
    }




}
