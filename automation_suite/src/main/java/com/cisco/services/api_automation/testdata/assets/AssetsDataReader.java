package com.cisco.services.api_automation.testdata.assets;

import com.cisco.services.api_automation.pojo.response.assets.AssetsAPIPojo;
import com.cisco.services.api_automation.utils.ExcelReader;

import io.restassured.response.Response;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class AssetsDataReader {
	static AssetsAPIPojo xlsPojo;
	static Map<String, AssetsAPIPojo> apiMetaData;
	private static String PATH_SEPARATOR = System.getProperty("file.separator");

	public static final String DIR = "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle") + PATH_SEPARATOR
			+ "Assets" + PATH_SEPARATOR;
	public static final String[][] ASSETS_AND_DIAGNOSTICS_ASSETS_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx",
			"assets", true);
	public static final String[][] ASSETS_GROUPS_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx", "assetsGroup",
			true);
	public static final String[][] ASSETS_SORT_FIELDS_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx", "sorting",
			true);
	public static final String[][] ASSETS_SEARCH_FIELDS_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx", "search",
			true);
	public static final String[][] ASSETS_META_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx", "metaData", true);
	public static final String[][] ASSETS_All_ASSETS_TYPEAHEAD = ExcelReader.readTestData(DIR + "Assets.xlsx",
			"typeAhead", true);
	public static final String[][] ASSETS_PAGINATION_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx",
			"pagination", true);

	public static final String[][] NETWORK_DATA_GW_DATA = ExcelReader.readTestData(DIR + "Assets.xlsx", "networkDataGW",
			true);

	public static Map<String, AssetsAPIPojo> dataSetterUI(String[][] excelData) {
		apiMetaData = new HashMap<String, AssetsAPIPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new AssetsAPIPojo();
			xlsPojo.setApiName(excelData[i][0]);
			xlsPojo.setMethod(excelData[i][1]);
			xlsPojo.setEndPoint(excelData[i][2]);
			xlsPojo.setParams(excelData[i][3]);
			xlsPojo.setPayLoad(excelData[i][4]);
			xlsPojo.setStatusCode(excelData[i][5]);
			xlsPojo.setExpectedResponse(excelData[i][6]);
			xlsPojo.setType(excelData[i][7]);
			xlsPojo.setRecordCount(excelData[i][8]);
			xlsPojo.setExpectedResponsePath(excelData[i][9]);
			xlsPojo.setUserRole(excelData[i][10]);

			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}

	public static Map<String, AssetsAPIPojo> metaDataSetter(String[][] excelData) {
		apiMetaData = new HashMap<String, AssetsAPIPojo>();
		for (int i = 0; i < excelData.length; i++) {
			xlsPojo = new AssetsAPIPojo();
			xlsPojo.setApiName(excelData[i][0]);
			xlsPojo.setMethod(excelData[i][1]);
			xlsPojo.setEndPoint(excelData[i][2]);
			xlsPojo.setParams(excelData[i][3]);
			xlsPojo.setSearchFields(excelData[i][4]);
			xlsPojo.setPaginationParams(excelData[i][5]);
			apiMetaData.put(excelData[i][0], xlsPojo);
		}
		return apiMetaData;
	}

	// This will be deprecated
	@DataProvider(name = "AssetsStaticData", parallel = true)
	public static final Object[][] AssetsStaticData() {
		return ExcelReader.readTestData(DIR + "Assets.xlsx", "assetsAll", true);
	}

	@DataProvider(name = "NetworkDataGWStaticData", parallel = true)
	public static final Object[][] NetworkDataGWStaticData() {
		return ExcelReader.readTestData(DIR + "Assets.xlsx", "networkDataGW", true);
	}

	@DataProvider(name = "AssetsTaggingData", parallel = true)
	public static final Object[][] AssetsTaggingData() {
		Object[][] dataProvider = ExcelReader.readTestData(DIR + "Assets.xlsx", "assetsTagging", true);
		System.out.println("dataProvider Length=" + dataProvider.length);
		return dataProvider;
	}

	@DataProvider(name = "AssetsQueryFilterListData", parallel = false)
	public static final Object[][] AssetsQueryFilterListData() {
		Object[][] dataProvider = ExcelReader.readTestData(DIR + "Assets.xlsx", "queryFilters", true);
		System.out.println("dataProvider Length=" + dataProvider.length);
		return dataProvider;
	}
	
	@DataProvider(name = "AssetsQueryFilterOthersData", parallel = false)
	public static final Object[][] AssetsQueryFilterOthersData() {
		Object[][] dataProvider = ExcelReader.readTestData(DIR + "Assets.xlsx", "queryFiltersOthers", true);
		System.out.println("dataProvider Length=" + dataProvider.length);
		return dataProvider;
	}

	public static List<String> getKeyByType(String type, String[][] excel_data) {
		List<String> keyList = new ArrayList<String>();
		Set<String> keys = dataSetterUI(excel_data).keySet();
		Map<String, AssetsAPIPojo> data = dataSetterUI(excel_data);
		for (String key : keys) {
			if (type.equalsIgnoreCase("All")) {
				if (!(data.get(key).getType().equalsIgnoreCase("Ignore"))) {
					keyList.add(key);
				}
			} else {
				if (data.get(key).getType().equalsIgnoreCase(type)) {
					keyList.add(key);
					Collections.sort(keyList);
				}
			}
		}
		return keyList;
	}

	/*
	 * added by vbollimu
	 */
	@DataProvider(name = "GenericKeys", parallel = true)
	public Iterator<String> getGenericData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_AND_DIAGNOSTICS_ASSETS_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	@DataProvider(name = "NDGWGenericKeys")
	public Iterator<String> getNDGWGenericData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, NETWORK_DATA_GW_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	@DataProvider(name = "AssetsGroupData")
	public Iterator<String> getAssetsGroupsData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_GROUPS_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	@DataProvider(name = "AssetsSortFieldsData", parallel = true)
	public Iterator<String> getAssetsSortFieldsData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_SORT_FIELDS_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	@DataProvider(name = "AssetsSearchFieldsData", parallel = true)
	public Iterator<String> getAssetsSearchFieldsData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_SEARCH_FIELDS_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	@DataProvider(name = "AllAssetsTypeAheadData", parallel = true)
	public Iterator<String> getAssetsTypeAheadData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_All_ASSETS_TYPEAHEAD);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}
	
	@DataProvider(name = "PaginationData", parallel = true)
	public Iterator<String> getPaginationData(Method M) {
		String apiType = M.getName().split("__")[1];
		List<String> data = getKeyByType(apiType, ASSETS_PAGINATION_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}

	public static String currentDataTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		return formatter.format(date).replace("/", "_").replace(":", "_").replace(" ", "_");
	}

	public static int getActualRecordCountExportAPIs(Response actualResponse) {
		int actualRowCount = 0, headerRow = 1;
		ArrayList<String> exportResult = new ArrayList<String>(
				Arrays.asList(actualResponse.getBody().asString().split("\\r?\\n")));
		actualRowCount = exportResult.size() - headerRow;
		return actualRowCount;
	}

}
