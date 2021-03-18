package com.cisco.services.api_automation.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.testng.ITestContext;

@JsonInclude(JsonInclude.Include.NON_NULL) 	//  ignore all null fields
public class LifecycleDataPojo {
    private String name;
    private String component;
    private String method;
    private String url;
    private String params;
    private String headers;
    private String body;
    private String statusCode;
    private String output;
    private String mode;
    private String userRole;
    private String responseFormat;
    private String schema;
    private ITestContext context;
    private String scenario;
    private String testId;
    //common data
    private String $ccoIdL2;
    private String $customerIdL2;
    private String $vaIdL2;
    private String $saIdL2;
    private String $customerIdL1;

    public String getTestId() {
        return null == testId ? "" : testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    public String get$ccoIdL2() {
        return $ccoIdL2;
    }

    public void set$ccoIdL2(String $ccoIdL2) {
        this.$ccoIdL2 = $ccoIdL2;
    }

    public String get$customerIdL2() {
        return $customerIdL2;
    }

    public void set$customerIdL2(String $customerIdL2) {
        this.$customerIdL2 = $customerIdL2;
    }

    public String get$vaIdL2() {
        return $vaIdL2;
    }

    public void set$vaIdL2(String $vaIdL2) {
        this.$vaIdL2 = $vaIdL2;
    }

    public String get$saIdL2() {
        return $saIdL2;
    }

    public void set$saIdL2(String $saIdL2) {
        this.$saIdL2 = $saIdL2;
    }

    public String get$buIdL2() {
        return $buIdL2;
    }

    public void set$buIdL2(String $buIdL2) {
        this.$buIdL2 = $buIdL2;
    }

    private String $buIdL2;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String get$customerIdL1() {
        return $customerIdL1;
    }

    public void set$customerIdL1(String $customerIdL1) {
        this.$customerIdL1 = $customerIdL1;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public ITestContext getContext() {
        return context;
    }

    public void setContext(ITestContext context) {
        this.context = context;
    }

    public String getScenario() {
        return scenario;
    }

    @Override
    public String toString() {
        return getTestId() + ":" + getName();
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
