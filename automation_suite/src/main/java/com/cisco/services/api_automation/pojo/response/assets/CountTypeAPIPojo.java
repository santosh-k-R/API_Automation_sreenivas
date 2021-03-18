package com.cisco.services.api_automation.pojo.response.assets;

import java.util.HashMap;
import java.util.Map;

public class CountTypeAPIPojo {
	private Map<String, Integer> esResponseObject = new HashMap<String, Integer>(); 
	
	private Map<String, Integer> apiResponseObject = new HashMap<String, Integer>();
	
	public Map<String, Integer> getEsResponseObject() {
		return esResponseObject;
	}
	public void setEsResponseObject(Map<String, Integer> esResponseObject) {
		this.esResponseObject = esResponseObject;
	}
	public Map<String, Integer> getApiResponseObject() {
		return apiResponseObject;
	}
	public void setApiResponseObject(Map<String, Integer> apiResponseObject) {
		this.apiResponseObject = apiResponseObject;
	}
}
