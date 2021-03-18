package com.cisco.services.api_automation.testdata.insights.compliance;

import com.cisco.services.api_automation.utils.Commons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StaticPaths {

    public static final Map<String, String> COMMON_DATA = Commons.getTestData(getDir() + "Common.xlsx", "common_data", false);

    private static String PATH_SEPARATOR(){
        return System.getProperty("file.separator");
    }

    public static String getDir() {
//        String PATH_SEPARATOR = System.getProperty("file.separator");
        return "test-data" + PATH_SEPARATOR() + System.getenv("niagara_lifecycle")
                + PATH_SEPARATOR() + "Compliance" + PATH_SEPARATOR();
    }

    public static List<TestData> getJSONData(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String path = getDir() + file;
        List<TestData> data = Arrays.asList
                (mapper.readValue(new ClassPathResource(path).getFile(), TestData[].class));
        return data;

    }


}
