package com.cisco.services.api_automation.utils.results;

import com.cisco.automation.tims.TIMSTest;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TIMSResult implements TestResults {

    private String timsConfiguration;
    private String timsProject;
    private String timsResultFolder;
    private String timsUser;
    private String timsToken;

    public TIMSResult() {
        System.out.println("Setting up TIMS Auth");
        Properties props = new Properties();
        try (final FileReader propsReader = new FileReader(new File("src/main/java/com/cisco/services/api_automation/utils/results/result.properties"))) {
            props.load(propsReader);
            timsConfiguration = System.getenv("timsConfiguration");
            timsProject = props.getProperty("timsProject");
            timsResultFolder = System.getenv("timsResultFolder");
            timsUser = System.getenv("timsUser");
            timsToken = System.getenv("timsToken");
            Preconditions.checkNotNull(timsConfiguration, timsProject, timsResultFolder, timsUser, timsToken);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Test Property File not found");
        }
    }

    @Override
    public void post() {
        try {
            System.out.println("Posting Results to TIMS ...");
            ExecutorService executorService = Executors.newCachedThreadPool();
            setupTims();
            if (RESULT_BODIES.size() == 0) System.out.println("No Test IDS Found to Pos the results");
            RESULT_BODIES.forEach(resultBody -> executorService.execute(() -> updateTIMS(resultBody)));
            executorService.shutdown();
            executorService.awaitTermination(15, TimeUnit.MINUTES);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Failed to post the Results to TIMS");
        }
    }

    public void setupTims() throws IOException {
        TIMSTest.setTIMSParam(timsConfiguration, timsUser, timsToken, timsProject, timsResultFolder);
        if (!TIMSTest.getMember()) {
            System.err.println("TIMS Authentication failed, check username and token");
        }
    }

    public void updateTIMS(ResultBody resultBody) {
        String title = "Automation- Result For : " + resultBody.getTitle();
        boolean status = false;

        if (resultBody.getStatus().toString().equalsIgnoreCase("Passed"))
            status = TIMSTest.updateResultPass(resultBody.getId(), title);
        else if (resultBody.getStatus().toString().equalsIgnoreCase("Failed"))
            status = TIMSTest.updateResultFail(resultBody.getId(), title);

        printStatus(status, title);
    }

    public static void printStatus(boolean status, String title) {
        if (status)
            System.out.println("Result Updated Successfully for " + title.split(":")[1]);
        else
            System.out.println("Failed to update the result for " + title.split(":")[1]);

    }
}
