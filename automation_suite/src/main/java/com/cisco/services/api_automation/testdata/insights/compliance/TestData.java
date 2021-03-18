package com.cisco.services.api_automation.testdata.insights.compliance;

import org.testng.annotations.Guice;

public class TestData {

    private String scenario;
    private String method;
    private String url;
    private String headers;
    private String params;
    private String body;
    private String statusCode;
    private String expResponse;
    private String userRole;
    private String sortName;
    private String sortOrder;


    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public String getExpResponse() {
        return expResponse;
    }

    public void setExpResponse(String expResponse) {
        this.expResponse = expResponse;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "scenario='" + scenario + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", headers='" + headers + '\'' +
                ", params='" + params + '\'' +
                ", body='" + body + '\'' +
                ", status='" + statusCode + '\'' +
                ", expResponse=" + expResponse +
                '}';
    }
}
