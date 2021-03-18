package com.cisco.services.api_automation.testdata.syslogs;

import java.util.HashMap;
import java.util.Map;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.utils.ExcelReader;

public class SyslogsDataProvider {
	public static Map<String, SyslogsInputDataPojo> apiMetaData;
	private static SyslogsInputDataPojo xlsPojo;

	public static Map<String, SyslogsInputDataPojo> dataSetterAPIsMetaData(String path,String filename ,String sheetName) {
		String[][] excelData = ExcelReader.readTestData(path + filename, sheetName, true);
		apiMetaData = new HashMap<String, SyslogsInputDataPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new SyslogsInputDataPojo();
			xlsPojo.setApiName(excelData[i][0]);
			xlsPojo.setMethodType(excelData[i][1]);
			xlsPojo.setEndPointUrl(excelData[i][2]);
			xlsPojo.setCxcontext(excelData[i][3]);
			xlsPojo.setParams(excelData[i][4]);
			xlsPojo.setPayload(excelData[i][5]);
			xlsPojo.setResponse(excelData[i][6]);
			xlsPojo.setUserRole(excelData[i][7]);
			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}
	
	public static Map<String, SyslogsInputDataPojo> dataSetterSyslogsDetails(String path,String filename ,String sheetName) {
		String[][] excelData = ExcelReader.readTestData(path + filename, sheetName, true);
		apiMetaData = new HashMap<String, SyslogsInputDataPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new SyslogsInputDataPojo();
			xlsPojo.setApiName(excelData[i][0]);
			xlsPojo.setMethodType(excelData[i][1]);
			xlsPojo.setEndPointUrl(excelData[i][2]);
			xlsPojo.setCxcontext(excelData[i][3]);
			xlsPojo.setParams(excelData[i][4]);
			xlsPojo.setPayload(excelData[i][5]);
			xlsPojo.setResponse(excelData[i][6]);
			xlsPojo.setUserRole(excelData[i][7]);
			xlsPojo.setRegex(excelData[i][8]);
			//xlsPojo.setMsgType(excelData[i][9]);
			//xlsPojo.setMsgDesc(excelData[i][10]);
			//xlsPojo.setSuggestion(excelData[i][11]);
			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}

	public static Map<String, SyslogsInputDataPojo> dataSetterFaultssDetails(String path,String filename ,String sheetName) {
		String[][] excelData = ExcelReader.readTestData(path + filename, sheetName, true);
		apiMetaData = new HashMap<String, SyslogsInputDataPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new SyslogsInputDataPojo();
			xlsPojo.setApiName(excelData[i][0]);
			xlsPojo.setMethodType(excelData[i][1]);
			xlsPojo.setEndPointUrl(excelData[i][2]);
			xlsPojo.setCxcontext(excelData[i][3]);
			xlsPojo.setParams(excelData[i][4]);
			xlsPojo.setPayload(excelData[i][5]);
			xlsPojo.setResponse(excelData[i][6]);
			xlsPojo.setUserRole(excelData[i][7]);
			xlsPojo.setSign(excelData[i][8]);
			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}


	
	
	

}
