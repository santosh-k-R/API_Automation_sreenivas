package com.cisco.services.api_automation.pojo.response.assets;

public class APIsMetaDataPojo {
	private String track, aPIName, endPointUrl, methodType, mandatoryParams, priority, eSIndex, tIMSId;
	private String APIType, apiCountParam, ESJsonPath, ESType;
	private String InputType;
	private String optionalParams;
	private String solutionUseCaseApplicable;
	private String payload;

	public String getOptionalParams() {
		return optionalParams;
	}
	public String getSolutionUseCaseApplicable() {
		return solutionUseCaseApplicable;
	}
	public void setSolutionUseCaseApplicable(String solutionUseCaseApplicable) {
		this.solutionUseCaseApplicable = solutionUseCaseApplicable;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}
	public String getInputType() {
		return InputType;
	}
	public void setInputType(String inputType) {
		InputType = inputType;
	}
	public String getAPIType() {
		return APIType;
	}
	public void setAPIType(String aPIType) {
		APIType = aPIType;
	}
	public void setAPICountParam(String aPICountParam) {
		apiCountParam = aPICountParam;
	}
	public String getAPICountParam() {
		return apiCountParam;
	}
	public String getESJsonPath() {
		return ESJsonPath;
	}
	public void setESJsonPath(String eSJsonPath) {
		ESJsonPath = eSJsonPath;
	}
	public String getESType() {
		return ESType;
	}
	public void setESType(String eSType) {
		ESType = eSType;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getaPIName() {
		return aPIName;
	}
	public void setaPIName(String aPIName) {
		this.aPIName = aPIName;
	}
	public String getEndPointUrl() {
		return endPointUrl;
	}
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getMandatoryParams() {
		return mandatoryParams;
	}
	public void setMandatoryParams(String mandatoryParams) {
		this.mandatoryParams = mandatoryParams;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String geteSIndex() {
		return eSIndex;
	}
	public void seteSIndex(String eSIndex) {
		this.eSIndex = eSIndex;
	}
	public String gettIMSId() {
		return tIMSId;
	}
	public void settIMSId(String tIMSId) {
		this.tIMSId = tIMSId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	/*@Override
	public String toString() {
		return track+","+"tIMSId";
	}*/
	
}
