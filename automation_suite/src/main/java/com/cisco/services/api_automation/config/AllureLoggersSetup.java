package com.cisco.services.api_automation.config;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class AllureLoggersSetup {

    private String systemPath() {
        return System.getProperty("user.dir");
    }

    private String fileSeparator() {
        return System.getProperty("file.separator");
    }

    public void deleteLoggers() {

        File directory = new File(systemPath() + fileSeparator() + "log");
        if (!directory.exists())
            directory.mkdir();

        File[] Files = directory.listFiles();

        if (Files.length > 0) {
            for (File file : Files)
                file.delete();
        }
    }

    public void setEnvVariables() throws Exception {

        String fileDir = systemPath() + fileSeparator() + "allure-results";
        String fileName = "environment.properties";

        File file = new File(fileDir);
        if (!file.exists())
            file.mkdir();

        String filePath = fileDir + fileSeparator() + fileName;
        file = new File(filePath);
        if (!file.exists())
            file.createNewFile();

        Properties prop = new Properties();
        String env = System.getenv("niagara_lifecycle");
        String threads = System.getenv("niagara_threadcount");
        String groups = System.getenv("niagara_groups");
        String maxretryCount = System.getenv("niagara_retrycount");

        env = env == null ? "" : env;
        threads = threads == null ? "" : threads;
        groups = groups == null ? "" : groups;
        maxretryCount = maxretryCount == null ? "" : maxretryCount;

        prop.setProperty("Env", env);
        prop.setProperty("Threads", threads);
        prop.setProperty("Groups", groups);
        prop.setProperty("maxretryCount", maxretryCount);
        try (OutputStream op = Files.newOutputStream(file.toPath())) {
            prop.store(op, null);
        }
        try (InputStream ip = Files.newInputStream(Paths.get(filePath))) {
            prop.load(ip);
        }

    }

    public void allureCategories() throws Exception {
        String srcDir = systemPath() + fileSeparator() + "src" + fileSeparator() + "main" + fileSeparator() + "resources" + fileSeparator() + "categories.json";
        String destDir = systemPath() + fileSeparator() + "allure-results";
        File srcFile = new File(srcDir);
        File destFile = new File(destDir);
        if (srcFile.exists())
            FileUtils.copyFileToDirectory(srcFile, destFile);

    }
}
