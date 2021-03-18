package com.cisco.services.api_automation.testdata.insights.compliance;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;

import java.io.IOException;
import java.util.Iterator;

@Guice
public class AssetSummaryData {

	private static String PATH_SEPARATOR(){
		return System.getProperty("file.separator");
	}

	@DataProvider(name="NoFiltersAndSearchData")
	public static Iterator<TestData> noFiltersAndSearchData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "NoFiltersAndSearchData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}
	

	@DataProvider(name="HighSevFilterData")
	public static Iterator<TestData> highSevFilterData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "HighSevFilterData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="SearchAllData")
	public static Iterator<TestData> searchAllData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "SearchAllData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="SearchDNACData")
	public static Iterator<TestData> searchDNACData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "SearchDNACData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}
	

	@DataProvider(name="Sev_SearchAllData")
	public static Iterator<TestData> sev_SearchAllData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "Sev_SearchAllData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="Sev_SearchDNACData")
	public static Iterator<TestData> sev_SearchDNACData() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "Sev_SearchDNACData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="ErrorScenarios")
	public static Iterator<TestData> errorScenarios() throws IOException {
		String fileName = "AssetSummary" + PATH_SEPARATOR() + "ErrorScenarios.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}
	

	/*private static String dataPath(){
		String PATH_SEPARATOR = System.getProperty("file.separator");
		return StaticPaths.getDir() + PATH_SEPARATOR + "AssetSummary" + PATH_SEPARATOR;
	}

	@DataProvider(name = "vsWithoutFilterData")
	public static final Object[][] vsWithoutFilterData() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "Without Filter & Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asHighestSeverityFilter")
	public static final Object[][] vsHighestSeverityFilter() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "HighestSeverityFilter", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asRegular_Search")
	public static final Object[][] vsRegular_Search() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "Regular Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asSearch_With_DNAC")
	public static final Object[][] vsSearch_With_DNAC() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "Search With DNAC", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "rsSeverity_Search")
	public static final Object[][] vsSeverity_Search() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "Severity_Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asSeverity_DNACSearch")
	public static final Object[][] vsSeverity_DNACSearch() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(AssetSummaryData.dataPath() + "AssetSummary.xlsx", "Severity_DNAC Search", true, StaticPaths.COMMON_DATA);
	}*/

}
