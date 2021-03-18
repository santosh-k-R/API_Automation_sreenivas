package com.cisco.services.api_automation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.Transfer.TransferState;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferProgress;
import com.amazonaws.services.s3.transfer.Upload;
import com.cisco.services.api_automation.tests.riskmitigation.TimeStampIT;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
public class Main {

	/*
	 * @Value("${sync.to.s3}") private static boolean syncToS3;
	 * 
	 * @Value("${sync.to.s3}") public void setSyncToS3(boolean syncToS3) {
	 * Main.syncToS3 = syncToS3; }
	 */

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
		applicationContext.getBean(Launcher.class).testNGRun();
		if (applicationContext.getBean(S3Sync.class).syncToS3) {
			applicationContext.getBean(S3Sync.class).uploadDirectoryToS3Bucket();
		}
        //System.exit(0);
        System.out.println("DONE main");
	}

    @Service
    class Launcher {

        public void testNGRun() {

            TestNG testng = new TestNG();
            //testng.setTestJar("target/api-automation-cxp-1.0.0-SNAPSHOT.jar");
            testng.setTestJar("/app/api-automation-cxp.jar");
            String testngXmlPath = "testng-files/" + System.getenv("niagara_suitename") + "-testng.xml";
            System.out.println("testngXmlPath = " + testngXmlPath);
            testng.setXmlPathInJar(testngXmlPath);
            testng.initializeSuitesAndJarFile();
            testng.run();
        }

        public void runOneTest() {
            TestListenerAdapter tla = new TestListenerAdapter();
            TestNG testng = new TestNG();
            testng.setTestClasses(new Class[]{TimeStampIT.class});
            testng.addListener(tla);
            testng.run();
        }

        public void runSuite() {
            XmlSuite suite = new XmlSuite();
            suite.setName("TmpSuite");

            XmlTest test = new XmlTest(suite);
            test.setName("TmpTest");
            List<XmlClass> classes = new ArrayList<XmlClass>();
            classes.add(new XmlClass("test.failures.Child"));
            test.setXmlClasses(classes);
            List<XmlSuite> suites = new ArrayList<XmlSuite>();
            suites.add(suite);
            TestNG tng = new TestNG();
            tng.setXmlSuites(suites);
            tng.run();

        }
    }
    @Service
	class S3Sync {
		private AmazonS3 amazonS3;
		@Value("${aws.s3.bucket}")
		private String bucketName;
		@Value("${path.prefix}")
		private String pathPrefix;
		@Value("${dir.name}")
		private String dirName;
		@Value("${aws.key.prefix}")
		private String awsKeyPrefix;
		@Value("${sync.to.s3}")
	    private boolean syncToS3;
		@Autowired
		private TransferManager tm;

		public void uploadDirectoryToS3Bucket() throws Exception {
            
			boolean recursive = true;
			String resultPath = pathPrefix + File.separator + dirName;
			System.out.println(resultPath);

			MultipleFileUpload multipleFileUpload = tm.uploadDirectory(bucketName, awsKeyPrefix, new File(resultPath),
					recursive);
			showMultiUploadProgress(multipleFileUpload);
			multipleFileUpload.waitForCompletion();
		}
	}
    
 // Prints progress of a multiple file upload while waiting for it to finish.
    private static void showMultiUploadProgress(MultipleFileUpload multiUpload) {
        System.out.println(multiUpload.getDescription());
        Collection<? extends Upload> subXfers = multiUpload.getSubTransfers();
        do {
            System.out.println("\nSubtransfer progress:\n");
            subXfers.forEach(subXfer -> {
            	eachUploadProgress(subXfer);
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
        } while (multiUpload.isDone() == false);
        TransferState xferState = multiUpload.getState();
        System.out.println("\nMultipleFileUpload " + xferState);
    }
    
    private static void eachUploadProgress(Upload subXfer) {
    	System.out.println("s3Sync sub-transfer description: " + subXfer.getDescription());
    	if(subXfer.isDone()) {
    		TransferState xferState = subXfer.getState();
    		System.out.println("s3Sync sub-transfer state: " + xferState);
    	}else {
    		TransferProgress progress = subXfer.getProgress();
    		System.out.println("s3Sync sub-transfer progress: " + progress.getBytesTransferred());
    	}
    
    }


}
