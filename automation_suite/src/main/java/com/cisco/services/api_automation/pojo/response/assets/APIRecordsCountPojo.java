package com.cisco.services.api_automation.pojo.response.assets;

import io.restassured.response.Response;

public class APIRecordsCountPojo {
	
	private Response responseAPI;
	private Response responseES;
	private int countOfRecordsFromAPI;
	private int countOfRecordsFromES;
	private int countOfRecordsFromDB;

	public Response getResponseAPI() {
		return responseAPI;
	}
	public void setResponseAPI(Response responseAPI) {
		this.responseAPI = responseAPI;
	}
	public Response getResponseES() {
		return responseES;
	}
	public void setResponseES(Response responseES) {
		this.responseES = responseES;
	}
	public int getCountOfRecordsFromAPI() {
		return countOfRecordsFromAPI;
	}
	public void setCountOfRecordsFromAPI(int countOfRecordsFromAPI) {
		this.countOfRecordsFromAPI = countOfRecordsFromAPI;
	}
	public int getCountOfRecordsFromES() {
		return countOfRecordsFromES;
	}
	public void setCountOfRecordsFromES(int countOfRecordsFromES) {
		this.countOfRecordsFromES = countOfRecordsFromES;
	}
	public int getCountOfRecordsFromDB() {
		return countOfRecordsFromDB;
	}
	public void setCountOfRecordsFromDB(int countOfRecordsFromDB) {
		this.countOfRecordsFromDB = countOfRecordsFromDB;
	}

}
