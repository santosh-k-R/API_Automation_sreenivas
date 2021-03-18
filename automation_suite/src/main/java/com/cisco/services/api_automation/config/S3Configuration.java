package com.cisco.services.api_automation.config;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

@Configuration
public class S3Configuration {
	@Value("${multipart.upload.threshold}")
	private  long multipartUploadThreshold ;
	@Value("${numthreads}")
	private  int numThreads;
	@Value("${aws.access_key_id}")
	private String accessKeyId;
	@Value("${aws.secret_access_key}")
	private String secretAccessKey;
	@Value("${aws.s3.region}")
	private String region;

	@Bean
	public AmazonS3 getAmazonS3Cient() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		// Get AmazonS3 client and return the s3Client object.
		return AmazonS3ClientBuilder
				.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
				.build();
	}
	
	@Bean
	public TransferManager getAmazonTransferManager() {
		return buildTransferManager();
	}

	private TransferManager buildTransferManager() {
		return TransferManagerBuilder.standard().withS3Client(getAmazonS3Cient())
				.withMultipartUploadThreshold(multipartUploadThreshold)
				.withExecutorFactory(() -> Executors.newFixedThreadPool(numThreads)).build();
		
	}

}
