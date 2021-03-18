package com.cisco.services.api_automation.utils;

import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.testng.SkipException;

import java.io.*;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.testng.SkipException;

import com.cisco.services.api_automation.utils.auth.User;
import com.cisco.services.api_automation.utils.customassert.Assert;
import com.cisco.services.api_automation.utils.customassert.SoftAssert;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Commons {

	private static String zipFilePath;
    private static String destDir;
    private static String tmpdir = System.getProperty("java.io.tmpdir");

    public static Map<String, String> getTestData(String file, String sheet, Boolean multiline) {
        String[][] excelData = ExcelReader.readTestData(file, sheet, multiline);
        Map<String, String> data = new ConcurrentHashMap<>();
        if (multiline) {
            for (int i = 0; i < excelData[0].length; i++) {
                for (int j = 0; j < excelData.length; j++) {
                    data.put(excelData[0][i], excelData[j + 1][i]);
                }
            }
        } else {
            for (int i = 0; i < excelData[0].length; i++) {
                data.put(excelData[0][i], excelData[1][i]);
            }

        }
        return data;
    }

    public static Map<String, String> getTestData(String file, String sheet, Map<String, String> commonDataToReplaceWith) {
        String[][] excelData = ExcelReader.readTestData(file, sheet, false);
        Map<String, String> data = new ConcurrentHashMap<>();
        for (int i = 0; i < excelData[0].length; i++) {
            data.put(excelData[0][i], excelData[1][i]);
        }
        return fillCommonData(data, commonDataToReplaceWith);
    }

    public static String encode(String actualValue) {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(actualValue.getBytes());
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(999999);
    }


    public static void AssertResponse(JsonPath response, String[] attr, String[] expVal) {
        SoftAssert sAssert = new SoftAssert();
        for (int i = 0; i < attr.length; i++) {
            sAssert.assertEquals(response.get(attr[i]), expVal[i], String.format(" %s", attr[i]));
        }
        sAssert.assertAll("All Assertion Passed");
    }

    public static Map<String, String> constructParams(String params) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramArray = params.split("&");
       /* if (paramArray.length % 2 != 0)
            throw new IllegalArgumentException("Either Key or Value missing in the parameters");*/

        for (String s : paramArray) {
            String[] temp = s.split("=");
            parameters.put(temp[0], temp[1]);
        }
        return parameters;
    }

    public static Map<String, String> constructHeader(String headers) {
        Map<String, String> headersMap = new HashMap<>();
        if (headers.contains("cx-context")) {
            int h = headers.indexOf("cx-context");
            int h1 = headers.indexOf("[", h);
            int v1 = headers.indexOf("]", h) + 1;
            headersMap.put("cx-context", headers.substring(h1, v1));
            headers = headers.replace(headers.substring(h, v1), "");
        }
        List<String> paramArray = Arrays.stream(headers.split(",")).filter(h -> !h.equals("")).collect(Collectors.toList());
        if (paramArray.size() % 2 != 0)
            throw new IllegalArgumentException("Either Key or Value missing in the headers");
        for (int i = 0; i < paramArray.size(); i = i + 2) {
            headersMap.put(paramArray.get(i), paramArray.get(i + 1));
        }
        return headersMap;
    }

    public static String[][] fillCommonData(String[][] data, Map<String, String> dataToReplaceWith) {
        dataToReplaceWith.entrySet().forEach(commonData -> {
            for (int i = 0, dataLength = data.length; i < dataLength; i++) {
                String[] row = data[i];
                for (int j = 0, rowLength = row.length; j < rowLength; j++) {
                    String cell = row[j];
                    if (cell.contains(commonData.getKey()))
                        data[i][j] = cell.replace(commonData.getKey(), commonData.getValue());
                }
            }
        });
        return data;
    }

    public static Map<String, String> fillCommonData(Map<String, String> data, Map<String, String> dataToReplaceWith) {
        dataToReplaceWith.entrySet().forEach(commonData -> {
            data.entrySet().forEach(item -> {
                if (item.getValue().contains(commonData.getKey()))
                    item.setValue(item.getValue().replace(commonData.getKey(), commonData.getValue()));
            });
        });
        return data;
    }

    public static String[][] getTestData(String file, String sheetName, boolean multiline, Map<String, String> dataToFillIn) {
        String[][] data = ExcelReader.readTestData(file, sheetName, multiline);
        return fillCommonData(data, dataToFillIn);
    }

    public static Headers getHeaderForCxContext(String cxContext) {
        return new Headers(new Header("cx-context", cxContext));
    }

    public static InputStream getResourceFile(String filepath) {
        try {
            return new ClassPathResource(filepath).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                if (columnValue == null) {
                    columnValue = "null";
                }
                if (obj.has(columnName)) {
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    public static Response getAPIResponse(String method, String url, String headers, String params, String body) {
        Response response = null;
        if (method.isEmpty() || url.isEmpty()) throw new SkipException("Method or URL cannot be blank");

        switch (method.toUpperCase()) {

            case "GET":
            case "HEAD":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.get(url);
                else if (params.isEmpty())
                    response = RestUtils.get(url, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.get(url, params);
                else
                    response = RestUtils.get(url, headers, params);
                break;

            case "POST":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.post(url, body);
                else if (params.isEmpty())
                    response = RestUtils.post(url, body, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.post(url, body, params);
                else
                    response = RestUtils.post(url, body, headers, params);
                break;

            case "PUT":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.put(url, body);
                else if (params.isEmpty())
                    response = RestUtils.put(url, body, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.put(url, body, params);
                else
                    response = RestUtils.put(url, body, headers, params);
                break;
        }

        return response;
    }

    public static Response getAPIResponse(String method, String url, String headers, String params, String body, String userRole) {
        Response response = null;
        if (method.isEmpty() || url.isEmpty()) throw new SkipException("Method or URL cannot be blank");

        switch (method.toUpperCase()) {

            case "GET":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.get(url, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.get(url, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.get(url, params, new User(userRole));
                else
                    response = RestUtils.get(url, headers, params, new User(userRole));
                break;

            case "POST":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.post(url, body, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.post(url, body, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.post(url, body, params, new User(userRole));
                else
                    response = RestUtils.post(url, body, headers, params, new User(userRole));
                break;

            case "PUT":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.put(url, body, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.put(url, body, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.put(url, body, params, new User(userRole));
                else
                    response = RestUtils.put(url, body, headers, params, new User(userRole));
                break;

            case "DELETE":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.delete(url, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.delete(url, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.delete(url, params, new User(userRole));
                else
                    response = RestUtils.delete(url, headers, params, new User(userRole));
                break;
            case "POST_FORM":
                response = RestUtils.post(url, constructParams(params), new User(userRole));
        }
        return response;
    }

    /**
     * <p>This method is to get desired, back date from now (i.e., suppose todays date is 17 and if we want to get yesterday date , then pass "1" so that we can get 16, in UTC format.</p>
     *
     * @param duration , pass the number to get back date from now.
     * @return back date along with time in UTC format (i.e., 2020-03-17T06:25:37.894Z )
     * @author ankumalv
     */
    @Step("Return date")
    public static String date(int duration) {
        return OffsetDateTime.now(ZoneOffset.UTC).minus(duration, ChronoUnit.DAYS).toString();
    }

    public static void readZipFile(Response response, File outputFile) throws IOException {
        if (outputFile.exists()) {
            outputFile.delete();
        }

        System.out.println("Downloaded an " + response.getHeader("Content-Type"));
        // get the contents of the file
        byte[] fileContents = response.getBody().asByteArray();
        // output contents to file
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(outputFile);
            outStream.write(fileContents);

        } catch (Exception e) {
            System.out.println("Error writing file " + outputFile.getAbsolutePath());
        } finally {

            if (outStream != null) {
                outStream.close();
            }
        }
    }

    //Added by Vinay
    public static void unzip(String zipFilePath, String destDir) {

        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void validateCSPHeader(Response response) {
        String cspHeader = "default-src 'self'; base-uri 'self'; frame-ancestors 'self'; block-all-mixed-content";
        if (!cspHeader.equals(response.getHeader("Content-Security-Policy")))
            Assert.fail("Response does not contain the CSP Header/or value does not match with CSP header");
    }

	public static void writeJsonFile(String filename, String data) {
		try {
			File jsonFile = new File(tmpdir, filename);
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object readJsonFile(String filename) throws Exception {
	    FileReader reader = new FileReader(tmpdir+"/"+filename);
	    JSONParser jsonParser = new JSONParser();
	    return jsonParser.parse(reader);
	}
}
