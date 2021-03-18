package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.tests.insights.compliance.ViolationSummary;
import com.cisco.services.api_automation.utils.Commons;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice
public class ViolationSummaryData{

	private static String PATH_SEPARATOR(){
		return System.getProperty("file.separator");
	}

	@DataProvider(name="NoFiltersAndSearchData")
	public static Iterator<TestData> noFiltersAndSearchData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "NoFiltersAndSearchData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegTypeFilterData")
	public static Iterator<TestData> regTypeFilterData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegTypeFilterData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="HighSevFilterData")
	public static Iterator<TestData> highSevFilterData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "HighSevFilterData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegTypeAndSevFilterData")
	public static Iterator<TestData> regTypeAndSevFilterData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegTypeAndSevFilterData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="SearchAllData")
	public static Iterator<TestData> searchAllData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "SearchAllData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="SearchDNACData")
	public static Iterator<TestData> searchDNACData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "SearchDNACData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegType_SearchAllData")
	public static Iterator<TestData> regType_SearchAllData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegType_SearchAllData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegType_SearchDNACData")
	public static Iterator<TestData> regType_SearchDNACData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegType_SearchDNACData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="Sev_SearchAllData")
	public static Iterator<TestData> sev_SearchAllData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "Sev_SearchAllData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="Sev_SearchDNACData")
	public static Iterator<TestData> sev_SearchDNACData() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "Sev_SearchDNACData.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegType_Sev_Search")
	public static Iterator<TestData> regType_Sev_Search() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegType_Sev_SearchAll.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="RegType_Sev_DNACSearch")
	public static Iterator<TestData> regType_Sev_DNACSearch() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "RegType_Sev_DNACSearch.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	@DataProvider(name="ErrorScenarios")
	public static Iterator<TestData> errorScenarios() throws IOException {
		String fileName = "ViolationSummary" + PATH_SEPARATOR() + "ErrorScenarios.json";
		return StaticPaths.getJSONData(fileName).iterator();
	}

	/*private static String dataPath(){
		String PATH_SEPARATOR = System.getProperty("file.separator");
		return StaticPaths.getDir() + PATH_SEPARATOR + "ViolationSummary" + PATH_SEPARATOR;
	}

	@DataProvider(name = "vsWithoutFilterData")
	public static final Object[][] vsWithoutFilterData() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "Without Filter & Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asHighestSeverityFilter")
	public static final Object[][] vsHighestSeverityFilter() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "HighestSeverityFilter", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asRegular_Search")
	public static final Object[][] vsRegular_Search() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "Regular Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asSearch_With_DNAC")
	public static final Object[][] vsSearch_With_DNAC() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "Search With DNAC", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "rsSeverity_Search")
	public static final Object[][] vsSeverity_Search() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "Severity_Search", true, StaticPaths.COMMON_DATA);
	}

	@DataProvider(name = "asSeverity_DNACSearch")
	public static final Object[][] vsSeverity_DNACSearch() {
		System.out.println("Inside Data Provider : ");
		return Commons.getTestData(ViolationSummaryData.dataPath() + "ViolationSummary.xlsx", "Severity_DNAC Search", true, StaticPaths.COMMON_DATA);
	}*/

}
