package com.cisco.services.api_automation.pojo.response.assets;

public class AssetsAPIPojo {
    private String apiName;
    private String method;
    private String endPoint;
    private String params;
    private String payLoad;
    private String statusCode;
    private String expectedResponse;
    private String type;
    private String recordCount;
    private String expectedResponsePath;
    private String searchFields;
    private String userRole;
    private String paginationParams;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getExpectedResponse() {
        return expectedResponse;
    }

    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public String getExpectedResponsePath() {
        return expectedResponsePath;
    }

    public void setExpectedResponsePath(String expectedResponsePath) {
        this.expectedResponsePath = expectedResponsePath;
    }

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getPaginationParams() {
		return paginationParams;
	}

	public void setPaginationParams(String paginationParams) {
		this.paginationParams = paginationParams;
	}

}
