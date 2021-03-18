package com.cisco.services.api_automation.listeners;

import com.cisco.services.api_automation.config.AllureLoggersSetup;
import com.cisco.services.api_automation.utils.results.TIMSResult;
import com.cisco.services.api_automation.utils.results.TestResults;
import org.testng.IExecutionListener;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ExecutionListener implements IExecutionListener {

    private AllureLoggersSetup loadConfig = new AllureLoggersSetup();

    @Override
    public void onExecutionStart() {
        System.out.println("Loading up the Config");
        loadConfig.deleteLoggers();
        try {
            loadConfig.setEnvVariables();
            loadConfig.allureCategories();
        } catch (Exception e) {
            System.out.println("Unable to set the Environment Variables");
        }
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("On execution finish");

        if (TestResults.isPostResult())
            new TIMSResult().post();

        boolean openReport = Boolean.parseBoolean(System.getProperty("openReport"));
        if (!openReport)
            return;
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "allure serve");
        } else {
            builder.command("sh", "-c", "allure serve");
        }
        builder.directory(new File(System.getProperty("user.dir")));
        Process process = null;
        try {
            process = builder.start();
            InputStream inputStream = process.getInputStream();
            Consumer<String> consumer = System.out::println;
            Executors.newSingleThreadExecutor().submit(()->{
                new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
            });
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
//            System.exit(0);
        }
//        System.exit(0);
    }
}
