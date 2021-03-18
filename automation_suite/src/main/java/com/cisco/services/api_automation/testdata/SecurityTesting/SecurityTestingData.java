package com.cisco.services.api_automation.testdata.SecurityTesting;

import com.cisco.services.api_automation.pojo.response.assets.*;
//import com.cisco.services.api_automation.testdata.assets.AssetsData;
import com.cisco.services.api_automation.testdata.assets.AssetsDataProvider;
import com.cisco.services.api_automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityTestingData {
    static final String USER_DIR = System.getProperty("user.dir");
    static final String DIR = getDIR();
    static final String TestDataExcelFile = DIR + "SecurityTesting.xlsx";
    

    public static String getDIR() {
        String PATH_SEPARATOR = System.getProperty("file.separator");
        String DIR = "test-data" + PATH_SEPARATOR
                + "aws-stage" + PATH_SEPARATOR + "SecurityTesting" + PATH_SEPARATOR;
        //System.out.println("DIR--->" + DIR);
        return DIR;
    }



    @DataProvider(name = "SecurityData")
    public static final Object[][] getExcelData() {
        String[][] excelData = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "security", true);
        return excelData;
    }
    
    @DataProvider(name = "MultiCustIdVerifyData")
    public static final Object[][] getExcelData1() {
        String[][] excelData1 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "MulCustIdSheet", true);
        return excelData1;
    }
    
    @DataProvider(name = "SecurityDataPOST")
    public static final Object[][] getExcelData2() {
        String[][] excelData2 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "newpostrequest", true);
        return excelData2;
    }

    @DataProvider(name = "SuperAdminData")
	public static final Object[][] getExcelDataRBAC() {
		String[][] excelData2 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "SuperAdmin", true);
		return excelData2;
	}
	@DataProvider(name = "AdminData")
	public static final Object[][] getExcelDataRBAC1() {
		String[][] excelData3 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "Admin", true);
		return excelData3;
	}
	@DataProvider(name = "StandardUserData")
	public static final Object[][] getExcelDataRBAC2() {
		String[][] excelData4 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "StandardUSer", true);
		return excelData4;
	}
	@DataProvider(name = "AssetUserData")
	public static final Object[][] getExcelDataRBAC3() {
		String[][] excelData5= ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "AssetUser", true);
		return excelData5;
	}
	@DataProvider(name = "ReadOnlyUserData")
	public static final Object[][] getExcelDataRBAC4() {
		String[][] excelData6 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "ReadOnlyUser", true);
		return excelData6;
	}
	
	 @DataProvider(name = "AuthzChkVerifyData")
	    public static final Object[][] getExcelData7() {
	        String[][] excelData7 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "AuthzCheckSheet", true);
	        return excelData7;
	    }
	 
	 @DataProvider(name = "AdminAPIVerifyData")
	    public static final Object[][] getAdminAPIVerifyData() {
	        String[][] excelData8 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "AdminAPIDataSheet", true);
	        return excelData8;
	    }
	 
	 @DataProvider(name = "SecurityDataInvalid")
	    public static final Object[][] getExcelDataInvalid() {
	        String[][] excelData9 = ExcelReader.readTestData(SecurityTestingData.TestDataExcelFile, "InvalidCustIdSheet", true);
	        return excelData9;
	    }

    
}

