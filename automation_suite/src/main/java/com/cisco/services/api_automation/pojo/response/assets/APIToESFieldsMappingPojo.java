package com.cisco.services.api_automation.pojo.response.assets;

public class APIToESFieldsMappingPojo {
	
	private String esIndex,apiFieldName,esFieldName,dataType;

	public String getEsIndex() {
		return esIndex;
	}

	public void setEsIndex(String esIndex) {
		this.esIndex = esIndex;
	}

	public String getApiFieldName() {
		return apiFieldName;
	}

	public void setApiFieldName(String apiFieldName) {
		this.apiFieldName = apiFieldName;
	}

	public String getEsFieldName() {
		return esFieldName;
	}

	public void setEsFieldName(String esFieldName) {
		this.esFieldName = esFieldName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	
	
	
	/*@Override
	public String toString() {
		return track+","+"tIMSId";
	}*/
	
}
