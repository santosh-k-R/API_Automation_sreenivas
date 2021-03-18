package com.cisco.services.api_automation.testdata.syslogs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import com.cisco.services.api_automation.pojo.response.syslogs.SyslogsInputDataPojo;
import com.cisco.services.api_automation.utils.Commons;
import com.cisco.services.api_automation.utils.ExcelReader;

public class SyslogsData {
	private static final String DIR = getDIR();
	private static String systemPath;

	public final static Map<String, SyslogsInputDataPojo> FAULT_DATA = SyslogsDataProvider.dataSetterFaultssDetails(DIR,"Syslogs.xlsx","faultsapi");
	public final static Map<String, SyslogsInputDataPojo> SYSLOG_DATA = SyslogsDataProvider.dataSetterAPIsMetaData(DIR,"Syslogs.xlsx","syslogapi");
	public final static Map<String, SyslogsInputDataPojo> SYSLOG_DETAILS = SyslogsDataProvider.dataSetterSyslogsDetails(DIR,"Syslogs.xlsx","SyslogDetails");
	//public final static Map<String, SyslogsInputDataPojo> TAC_DATA = SyslogsDataProvider.dataSetterFaultssDetails(DIR,"tac.xlsx","tacapi");
	//public final static Map<String, SyslogsInputDataPojo> LAST_RECEIVED_DATA = SyslogsDataProvider.dataSetterAPIsMetaData(DIR,"Syslogs.xlsx","LastReceivedAPI");
	
    @DataProvider(name = "FaultGlobalCountWithUC")
    public static final Object[][] FaultGlobalCountWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "FaultGlobalCountWithUC",true);
    }
    
    @DataProvider(name = "AllFaultCountsWithUC")
    public static final Object[][] AllFaultCountsWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "AllFaultCountsWithUC",true);
    }
    
    @DataProvider(name = "AllFaultGridWithUC")
    public static final Object[][] AllFaultGridWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "AllFaultGridWithUC",true);
    }
    
    @DataProvider(name = "SyslogGlobalCountWithUC")
    public static final Object[][] syslogGlobalCountWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "SyslogGlobalCountWithUC",true);
    }
    
    @DataProvider(name = "SyslogTimeFilterCountWithUC")
    public static final Object[][] syslogTimeFilterCountWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "SyslogTimeCountWithUC",true);
    }
	
    @DataProvider(name = "SyslogSevFilterCountWithUC")
    public static final Object[][] syslogSevFilterCountWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "SyslogSevCountWithUC",true);
    }
    
    @DataProvider(name = "SyslogDataFilterByUC")
    public static final Object[][] SyslogDataFilterByUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "SyslogDataUC",true);
    }

    @DataProvider(name = "AffSystemFilterByUC")
    public static final Object[][] AffSystemFilterByUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "AffectedSystemsWithUC",true);
    }
    
    @DataProvider(name = "FilterDataWithUC")
    public static final Object[][] FilterDataWithUC() {
        return ExcelReader.readTestData(DIR + "Syslogs.xlsx", "FilterDatawithUC",true);
    }
    
    @DataProvider(name = "EnableCaseAutomation")
    public static final Object[][] EnableCaseAutomation() {
        return ExcelReader.readTestData(DIR + "tac.xlsx", "EnableCaseAutomation",true);
    }
    
    public static String getDIR() {
    	String PATH_SEPARATOR = System.getProperty("file.separator");
		String DIR = "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle") + PATH_SEPARATOR + "Syslogs"
				+ PATH_SEPARATOR;
		System.out.println("File DIR--->" + DIR);
		return DIR;
	}
    
	/*
	 * public static String getFullPath() { systemPath =
	 * System.getProperty("user.dir") + PATH_SEPARATOR + "src" + PATH_SEPARATOR +
	 * "main" + PATH_SEPARATOR + "resources" + PATH_SEPARATOR + getDIR(); return
	 * systemPath; }
	 */ 
	public static Object readJson(String staticResponse) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		InputStream filename = Commons.getResourceFile( DIR+staticResponse);
		InputStreamReader reader = new InputStreamReader(filename);
	    return jsonParser.parse(reader);
		
	}
	
	
    
}
