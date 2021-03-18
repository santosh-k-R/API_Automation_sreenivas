package com.cisco.services.api_automation.testdata.assets;

import com.cisco.services.api_automation.pojo.response.assets.*;
import com.cisco.services.api_automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssetsData {
	static final String USER_DIR = System.getProperty("user.dir");
	static final String DIR = getDIR();
	static final String TestDataExcelFile = DIR + "AssetsData.xlsx";

	public static String getDIR() {
		String PATH_SEPARATOR = System.getProperty("file.separator");
		String DIR ="test-data" + PATH_SEPARATOR
				+ "common" + PATH_SEPARATOR;
		System.out.println("DIR--->"+DIR);
		return DIR;
	}

//	public static String CUSTOMERID = "112192_0";

	public static final Map<String, APIsMetaDataPojo> ASSETS_GET_APIS = AssetsDataProvider
			.dataSetterAPIsMetaData("GetAPIs");
	public static final Map<String, APIsMetaDataPojo> ASSETS_HEAD_APIS = AssetsDataProvider
			.dataSetterAPIsMetaData("HeadAPIs");
//	public static final Map<String, ExcelColumnPojo> ASSETS_POST_APIS = AssetsDataProvider
//			.dataSetterAPIsMetaData("PostAPIs");
	public static final Map<String, ESQueriesPojo> ASSETS_ES_QUERIES = AssetsDataProvider
			.dataSetterESQueries("ESQueries");
//	public static final Map<String, SolutionUseCasePojo> ASSETS_SOLUTION_USECASES = AssetsDataProvider
//			.dataSetterSolutionUseCases("Solution_UseCase");
	public static final Map<String, ESIndexMappingsPojo> ASSETS_ES_INDEX_MAPPINGS = AssetsDataProvider
			.dataSetterESIndexMappings("ESIndexMappings");
	public static final Set<String> ASSETS_INDEX_TO_BE_DELETED = AssetsDataProvider
			.dataSetterIndexesToBeDeleted("indexesToBeDeleted");

	public static final Map<String, APIToESFieldsMappingPojo> API_TO_ES_FIELDSMAPPING = AssetsDataProvider
			.dataSetterAPIToESFieldsMapping("APIToESFieldsMapping");
	public static final Map<String, ParamWithValuesPojo> PARAM_WITH_VALUES = AssetsDataProvider
			.dataSetterParamWithValues("ParamWithValues");
	public static final List<ParamWithValuesPojo> PARAM_WITH_VALUES_LIST = AssetsDataProvider
			.dataSetterParamWithValuesList("ParamWithValues");
	public static final List<SolutionUseCasePojo> SOLUTION_USECASE_LIST = AssetsDataProvider
			.dataSetterSolutionUseCaseList("Solution_UseCase");
	public static final Map<String, String> ASSETS_SQL_QUERIES = AssetsDataProvider
			.dataSetterMySqlQueries("SqlQueries");
	public static final Map<String, APIsMetaDataPojo> ASSETS_AMP_GET_APIS = AssetsDataProvider
			.dataSetterAPIsMetaData("GetAPIsAMP");
	public static final Map<String, APIsMetaDataPojo> ASSETS_TAG_APIS = AssetsDataProvider
			.dataSetterAPIsMetaData("TaggingAPIs");

//	

	@DataProvider(name = "TimeStamp")
	public static final Object[][] searchTerm1() {
		return ExcelReader.readTestData(TestDataExcelFile, "TimeStamp", true);
	}

	@DataProvider(name = "GetAPIsEndPointList")
	public static final Object[][] getGetAPIsEndPointList() {
		String[][] apisEndPointList = new String[ASSETS_GET_APIS.size()][2];
		int i = 0;
		for (Map.Entry<String, APIsMetaDataPojo> entry : ASSETS_GET_APIS.entrySet()) {
			apisEndPointList[i][0] = entry.getKey();
			apisEndPointList[i][1] = entry.getValue().getEndPointUrl();
			i++;
		}
		return apisEndPointList;
	}

	@DataProvider(name = "HeadAPIsEndPointList")
	public static final Object[][] getHeadAPIsEndPointList() {
		String[][] apisEndPointList = new String[ASSETS_HEAD_APIS.size()][2];
		int i = 0;
		for (Map.Entry<String, APIsMetaDataPojo> entry : ASSETS_HEAD_APIS.entrySet()) {
			apisEndPointList[i][0] = entry.getKey();
			apisEndPointList[i][1] = entry.getValue().getEndPointUrl();
			i++;
		}
		return apisEndPointList;
	}

	@DataProvider(name = "GetOptionalParamsList")
	public static final Object[] getGetOptionalParams(Method M) {

		String apiKey = M.getName().split("__")[1];
		System.out.println("apiKey---> " + apiKey);
		String[] optionalParamList = ASSETS_GET_APIS.get(apiKey).getOptionalParams().split(",");
		return optionalParamList;
	}

	@DataProvider(name = "GetParamsWithValues")
	public static final Object[][] getGetParamsWithValues(Method M) {
		String apiKey = M.getName().split("__")[1];
		String endPoint = ASSETS_GET_APIS.get(apiKey).getEndPointUrl();
		int numberOfRecords = 0;
		for (ParamWithValuesPojo element : PARAM_WITH_VALUES_LIST) {
			if (element.getEndPoint().equalsIgnoreCase(endPoint)) {
				numberOfRecords++;
			}
		}
		String[][] paramsWithValues = new String[numberOfRecords][2];
		int i = 0;
		for (ParamWithValuesPojo element : PARAM_WITH_VALUES_LIST) {
			if (element.getEndPoint().equalsIgnoreCase(endPoint)) {

				paramsWithValues[i][0] = element.getParam();
				paramsWithValues[i][1] = element.getValue();
				i++;
			}
		}

		return paramsWithValues;
	}

	@DataProvider(name = "GetSolutionUseCaseForAllApplicableAPIs")
	public static final Object[][] getGetSolutionUseCaseForAllApplicableAPIs() {
		int solutionUseCaseLength = SOLUTION_USECASE_LIST.size();
		int numberOfRecords = 0;
		for (Map.Entry<String, APIsMetaDataPojo> entry : ASSETS_GET_APIS.entrySet()) {
			if (entry.getValue().getSolutionUseCaseApplicable().equalsIgnoreCase("Yes")) {
				numberOfRecords = numberOfRecords + solutionUseCaseLength;

			}
		}
		String[][] solutionUseCases = new String[numberOfRecords][3];
		int i = 0;
		for (Map.Entry<String, APIsMetaDataPojo> entry : ASSETS_GET_APIS.entrySet()) {
			if (entry.getValue().getSolutionUseCaseApplicable().equalsIgnoreCase("Yes")) {
				for (SolutionUseCasePojo element : SOLUTION_USECASE_LIST) {
					solutionUseCases[i][0] = entry.getKey();
					solutionUseCases[i][1] = element.getSolution();
					solutionUseCases[i][2] = element.getUseCase();
					i++;

				}
				numberOfRecords = numberOfRecords + solutionUseCaseLength;

			}
		}

		return solutionUseCases;
	}
	
	@DataProvider(name = "GetSolutionUseCase")
	public static final Object[][] getGetSolutionUseCase() {
		String[][] excelData = ExcelReader.readTestData(AssetsData.TestDataExcelFile, "Solution_UseCase", true);
		return excelData;
	}

}
