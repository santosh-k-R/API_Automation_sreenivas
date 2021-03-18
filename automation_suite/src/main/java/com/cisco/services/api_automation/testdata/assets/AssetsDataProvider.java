package com.cisco.services.api_automation.testdata.assets;

import com.cisco.services.api_automation.pojo.response.assets.*;
import com.cisco.services.api_automation.utils.ExcelReader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.*;

public class AssetsDataProvider {

//	public Utility util= new Utility();
	public static XSSFWorkbook apiBook;
	public static String[][] excelData;
	static APIsMetaDataPojo xlsPojo;
	static ESQueriesPojo esQueryPojo;
	static SolutionUseCasePojo solutionUseCasePojo;
	static ESIndexMappingsPojo esIndexMappingsPojo;
	static APIToESFieldsMappingPojo apiToESFieldsMappingPojo;
	static Map<String, APIsMetaDataPojo> apiMetaData;
	static Map<String, ESQueriesPojo> esQueries;
	static Map<String, SolutionUseCasePojo> solutionUseCase;
	static Map<String, ESIndexMappingsPojo> esIndexMappings;
	static Set<String> indexesToBeDeleted;
	static Map<String, APIToESFieldsMappingPojo> apiToESFieldsMapping;
	static Map<String, ParamWithValuesPojo> paramWithValuesMapping;
	static ParamWithValuesPojo paramWithValuesPojo;
	static List<ParamWithValuesPojo> paramWithValuesPojoList;
	static List<SolutionUseCasePojo> solutionUseCasePojoPojoList;

	public AssetsDataProvider() throws IOException {
//		apiBook=util.readExcelFile("SanitySuiteAPIList");
//		excelData=ExcelReader.readTestData(TestDataExcelFile, "TimeStamp", true);

	}

	// to get max row count including header
	public int getMaxRows(String sheetName) {
		int maxRowCount = apiBook.getSheet(sheetName).getPhysicalNumberOfRows();
		return maxRowCount;
	}

	// To fetch endPoint URL
	public String getData(String sheetName, int row, int columnIndex) {
		String excelData = apiBook.getSheet(sheetName).getRow(row).getCell(columnIndex).getStringCellValue();
		return excelData;
	}

	public static Map<String, APIsMetaDataPojo> dataSetterAPIsMetaData(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		apiMetaData = new HashMap<String, APIsMetaDataPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new APIsMetaDataPojo();

			xlsPojo.setaPIName(excelData[i][0]);
			xlsPojo.setEndPointUrl(excelData[i][1]);
			xlsPojo.setMethodType(excelData[i][2]);
			xlsPojo.setAPIType(excelData[i][3]);
			xlsPojo.setMandatoryParams(excelData[i][4]);
			xlsPojo.setOptionalParams(excelData[i][5]);
			xlsPojo.setPriority(excelData[i][6]);
			xlsPojo.seteSIndex(excelData[i][7]);
			xlsPojo.setSolutionUseCaseApplicable(excelData[i][8]);
			xlsPojo.setInputType(excelData[i][9]);
			xlsPojo.setAPICountParam(excelData[i][10]);
			xlsPojo.setESJsonPath(excelData[i][11]);
			xlsPojo.setESType(excelData[i][12]);
			if(sheetName.equals("TaggingAPIs"))
				xlsPojo.setPayload(excelData[i][14]);

			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}

	public static Map<String, ESQueriesPojo> dataSetterESQueries(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		esQueries = new HashMap<String, ESQueriesPojo>();
		for (int i = 0; i < excelData.length; i++) {
			esQueryPojo = new ESQueriesPojo();
			esQueryPojo.setESQueryName(excelData[i][0]);
			esQueryPojo.setQuery(excelData[i][1]);
			esQueries.put(excelData[i][0], esQueryPojo);
		}
		return esQueries;
	}

//	public static Map<String, SolutionUseCasePojo> dataSetterSolutionUseCases(String sheetName) {
//		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
//		System.out.println("Sheet Name: " + sheetName);
//		solutionUseCase = new HashMap<String, SolutionUseCasePojo>();
//		for (int i = 0; i < excelData.length; i++) {
//			solutionUseCasePojo = new SolutionUseCasePojo();
//			solutionUseCasePojo.setUseCaseNumber(excelData[i][0]);
//			solutionUseCasePojo.setSolution(excelData[i][1]);
//			solutionUseCasePojo.setUseCase(excelData[i][2]);
//
//			solutionUseCase.put(excelData[i][0], solutionUseCasePojo);
//		}
//		return solutionUseCase;
//	}

	public static Map<String, ESIndexMappingsPojo> dataSetterESIndexMappings(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		esIndexMappings = new HashMap<String, ESIndexMappingsPojo>();
		for (int i = 0; i < excelData.length; i++) {
			esIndexMappingsPojo = new ESIndexMappingsPojo();
			esIndexMappingsPojo.setIndexName(excelData[i][0]);
			esIndexMappingsPojo.setFinalIndex(excelData[i][1]);
			esIndexMappingsPojo.setTempIndex(excelData[i][2]);

			esIndexMappings.put(excelData[i][0], esIndexMappingsPojo);
		}
		return esIndexMappings;
	}

	public static Set<String> dataSetterIndexesToBeDeleted(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		indexesToBeDeleted = new HashSet<String>();
		for (int i = 0; i < excelData.length; i++) {

			indexesToBeDeleted.add(excelData[i][0]);

		}
		return indexesToBeDeleted;
	}

	public static Map<String, APIToESFieldsMappingPojo> dataSetterAPIToESFieldsMapping(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		apiToESFieldsMapping = new HashMap<String, APIToESFieldsMappingPojo>();
		for (int i = 0; i < excelData.length; i++) {
			apiToESFieldsMappingPojo = new APIToESFieldsMappingPojo();
			apiToESFieldsMappingPojo.setEsIndex(excelData[i][0]);
			apiToESFieldsMappingPojo.setApiFieldName(excelData[i][1]);
			apiToESFieldsMappingPojo.setEsFieldName(excelData[i][2]);
			apiToESFieldsMappingPojo.setDataType(excelData[i][3]);

			apiToESFieldsMapping.put(excelData[i][0] + "_" + excelData[i][1], apiToESFieldsMappingPojo);
		}
		return apiToESFieldsMapping;
	}

	public static Map<String, ParamWithValuesPojo> dataSetterParamWithValues(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		paramWithValuesMapping = new HashMap<String, ParamWithValuesPojo>();
		for (int i = 0; i < excelData.length; i++) {
			paramWithValuesPojo = new ParamWithValuesPojo();
			paramWithValuesPojo.setEndPoint(excelData[i][0]);
			paramWithValuesPojo.setParam(excelData[i][1]);
			paramWithValuesPojo.setValue(excelData[i][2]);

			paramWithValuesMapping.put(excelData[i][0] + "_" + excelData[i][1], paramWithValuesPojo);
		}
		return paramWithValuesMapping;
	}

	public static List<ParamWithValuesPojo> dataSetterParamWithValuesList(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		paramWithValuesPojoList = new ArrayList<ParamWithValuesPojo>();
		System.out.println("Sheet Name: " + sheetName);
		paramWithValuesMapping = new HashMap<String, ParamWithValuesPojo>();
		for (int i = 0; i < excelData.length; i++) {
			paramWithValuesPojo = new ParamWithValuesPojo();
			paramWithValuesPojo.setEndPoint(excelData[i][0]);
			paramWithValuesPojo.setParam(excelData[i][1]);
			paramWithValuesPojo.setValue(excelData[i][2]);

			paramWithValuesPojoList.add(paramWithValuesPojo);
		}
		return paramWithValuesPojoList;
	}

	public static List<SolutionUseCasePojo> dataSetterSolutionUseCaseList(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		solutionUseCasePojoPojoList = new ArrayList<SolutionUseCasePojo>();
		System.out.println("Sheet Name: " + sheetName);
		paramWithValuesMapping = new HashMap<String, ParamWithValuesPojo>();
		for (int i = 0; i < excelData.length; i++) {
			solutionUseCasePojo = new SolutionUseCasePojo();
			solutionUseCasePojo.setSolution(excelData[i][0]);
			solutionUseCasePojo.setUseCase(excelData[i][1]);
			solutionUseCasePojoPojoList.add(solutionUseCasePojo);
		}
		return solutionUseCasePojoPojoList;
	}
	
	public static Map<String, String> dataSetterMySqlQueries(String sheetName) {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, sheetName, true);
		System.out.println("Sheet Name: " + sheetName);
		Map<String, String> mysqlQueries= new HashMap<String, String>();
		for (int i = 0; i < excelData.length; i++) {
			mysqlQueries.put(excelData[i][0],excelData[i][1]);
		}
//		System.out.println("mysqlQueries: "+mysqlQueries);
		return mysqlQueries;
	}

}
