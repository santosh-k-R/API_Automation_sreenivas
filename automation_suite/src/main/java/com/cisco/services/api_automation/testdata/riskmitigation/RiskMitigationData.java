package com.cisco.services.api_automation.testdata.riskmitigation;

import com.cisco.services.api_automation.utils.Commons;
import org.testng.annotations.DataProvider;

import java.util.Map;

public class RiskMitigationData {

	private static final String DIR = getDIR();

	 public static String getDIR() {
	        String PATH_SEPARATOR = System.getProperty("file.separator");
	        String DIR = "test-data" + PATH_SEPARATOR + System.getenv("niagara_lifecycle")
	                + PATH_SEPARATOR + "RiskMitigation" + PATH_SEPARATOR;
	        return DIR;
	    }
	    //*********************************************************RiskMitigation Data*****************************************************************
	  public static final Map<String, String> COMMON_DATA = Commons.getTestData(DIR + "Common.xlsx", "common_data", false);
	   public static final Map<String, String> RISK_MITIGATION_FP_TIMESTAMP = Commons.getTestData(DIR + "RiskMitigation.xlsx", "TimeStamp", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_CRASHEDETAIL_API = Commons.getTestData(DIR + "RiskMitigation.xlsx", "CrashDetailAPI", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_INVALID_CUSTOMER = Commons.getTestData(DIR + "RiskMitigation.xlsx", "InvalidCustomer", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_MULTI_DNAC_SEARCH = Commons.getTestData(DIR + "RiskMitigation.xlsx", "Search", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_DNAC_SEARCH = Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPDnacSearch", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_CRASH_RISK_DEVICES = Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPCrashRiskDevices", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_DEVICES_DETAILS = Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPDeviceDetails", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FREQUENT_CRASH_DETAILS = Commons.getTestData(DIR + "RiskMitigation.xlsx", "FrequentCrashdetails360", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_WITHOUT_HEADERS = Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPWithOutHeader", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_INVALID_TIMEPERIOD = Commons.getTestData(DIR + "RiskMitigation.xlsx", "InvalidTimePeriod", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_DNAC_SEARCH = Commons.getTestData(DIR + "RiskMitigation.xlsx", "RMCDnacSearch", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_FP_SIMILARDEVICES = Commons.getTestData(DIR + "RiskMitigation.xlsx", "Similardevices", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_RMC_FP_SIMILAR_DEVICES_INVALID = Commons.getTestData(DIR + "RiskMitigation.xlsx", "SimilardeviceInvalid", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_COMPARE_IN_VALID_DEVICES = Commons.getTestData(DIR + "RiskMitigation.xlsx", "CompareInvalidDevices", COMMON_DATA);
	   public static final Map<String, String> RISK_MITIGATION_FP_COMPARE_DEVICES = Commons.getTestData(DIR + "RiskMitigation.xlsx", "CompareDevices", COMMON_DATA);
	   

	   @DataProvider(name = "FPList")
	   public static final Object[][] FPListProductFamily() {
	   return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPList" , true,COMMON_DATA);
	   }
	   @DataProvider(name = "FPListinvalid")
	   public static final Object[][] FPListinvalid() {
	   return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPListinvalid" , true,COMMON_DATA);
	   }
	   @DataProvider(name = "FPTimetaken")
	   public static final Object[][] FPTimeTaken() {
	   return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPTimetaken" , true,COMMON_DATA);
	   }
	   @DataProvider(name = "FPidListwithoutHeader") public static final Object[][]
	   FPidListwithoutHeader() { return Commons.getTestData(DIR +
	   "RiskMitigation.xlsx", "FPidListwithoutHeader" , true,COMMON_DATA); }
	   @DataProvider(name = "Similardevices")
	   public static final Object[][] Similardevices() {
	   return Commons.getTestData(DIR + "RiskMitigation.xlsx", "Similardevices" , true,COMMON_DATA);
	   }
	   @DataProvider(name = "SimilardeviceInvalid")
	   public static final Object[][] SimilardevicesInvalid() {
	   return Commons.getTestData(DIR + "RiskMitigation.xlsx", "SimilardeviceInvalid" , true,COMMON_DATA);
	   }
	   @DataProvider(name = "CompareDevicesFP")
	    public static final Object[][] CompareDevicesFP() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "CompareDevices", true,COMMON_DATA);
	    }
	   @DataProvider(name = "CompareDevicesInvalidFP")
	    public static final Object[][] CompareInvalidDevicesFP() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "CompareInvalidDevices", true,COMMON_DATA);
	    }
	   @DataProvider(name = "RMCDNACSearch")
	    public static final Object[][] RMCMultiDnacSearch() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "RMCDnacSearch", true,COMMON_DATA);
	    }
	    @DataProvider(name = "TIMESTAMP")
	    public static final Object[][] TimeStamp() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "TimeStamp", true,COMMON_DATA);
	    }
	    
	    @DataProvider(name = "RMCMultiDnacSearch")
	    public static final Object[][] RMCDnacSearch() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "Search", true,COMMON_DATA);
	    }
	    @DataProvider(name = "FPMultiDnacSearch")
	    public static final Object[][] FPDnacSearch() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPDnacSearch", true,COMMON_DATA);
	    }
	    @DataProvider(name = "CrashDetails")
	    public static final Object[][] CrashDetails() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "CrashDetailAPI", true,COMMON_DATA);
	    }
	    @DataProvider(name = "CrashRiskDeviceDetails")
	    public static final Object[][] CrashRiskDevice() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FPCrashRiskDevices", true,COMMON_DATA);
	    }
	    @DataProvider(name = "CrashDetailsUsecase")
	    public static final Object[][] CrashDetailsWithUsecase() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "CrashDetailsWithUsecase", true,COMMON_DATA);
	    }
	    @DataProvider(name = "FrequentCrashDetails")
	    public static final Object[][] FrequentCrashdetails360() {
	        return Commons.getTestData(DIR + "RiskMitigation.xlsx", "FrequentCrashdetails360", true,COMMON_DATA);
	    }
	   
	    @DataProvider(name = "RiskMitigationStaticData")
	    public static final Object[][] RiskMitigationStaticData() {
	        return Commons.getTestData(DIR + "RiskMitigationStatic.xlsx", "riskmitigation", true,COMMON_DATA);
	    }
	    
	    @DataProvider(name = "RiskMitigationInvalidStaticData")
	    public static final Object[][] RiskMitigationInvalidStaticData() {
	        return Commons.getTestData(DIR + "RiskMitigationStatic.xlsx", "riskmitigationinvalid", true,COMMON_DATA);
	    }
	 
	 
}
