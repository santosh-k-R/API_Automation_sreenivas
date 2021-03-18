package com.cisco.services.api_automation.pojo.response.syslogs;

public class SyslogsInputDataPojo {

	private String ApiName, endPointUrl, methodType, params, payload, userrole;;
	private String cxContext,response;
	private String regex,msgType,msgDesc,suggestion;
	private String signature;


	public String getApiName() {
		return ApiName;
	}
	public void setApiName(String ApiName) {
		this.ApiName = ApiName;
	}
	
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	
	public String getEndPointUrl() {
		return endPointUrl;
	}
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public String getCxcontext() {
		return cxContext;
	}
	public void setCxcontext(String cxContext) {
		this.cxContext = cxContext;
		
	}
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getResponse() {
		return response;
		
	}
	public void setResponse(String response) {
		this.response = response;
		
	}
	public String getUserRole() {
		return userrole;
	}
	
	public void setUserRole(String userrole) {
		this.userrole = userrole;
		
	}
	
	public String getRegex() {
		return regex;
	}
	
	public void setRegex(String regex) {
		this.regex = regex;	
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;	
	}
	
	public String getMsgDesc() {
		return msgDesc;
	}
	
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;	
	}

	public String getSuggestion() {
		return suggestion;
	}
	
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;	
	}
	
	public String getSign() {
		return signature;
	}
	public void setSign(String signature) {
		this.signature=signature;
	}

	
	
}
