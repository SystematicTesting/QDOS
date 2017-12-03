package com.systematictesting.automation.core.reporting;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class TestUploadFileOnAWSS3 {
	private static final String SUFFIX = "/";
	private static final String bucketName = "systematic-testing";

	public static void main(String[] args) {
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess for
		// this example to work
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJ2UAFV3BV4LMDQ3Q", "0OIxN/TsZSyYrZ8jxO3qT0SWfRBkwo9ikn97iaMk");
		// create a client connection based on credentials
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion("eu-west-2").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
		

		// upload file to folder and set it to public
		String fileName = "testFolder" + SUFFIX + "mandir.jpg";
		s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("/Users/sharadkumar/Desktop/monika/DSC_0506.JPG")).withCannedAcl(CannedAccessControlList.PublicRead));
		
		System.out.println("FILE ONE UPLOADED SUCCESSFULLY");
		
		fileName = "testFolder" + SUFFIX + "passport.jpg";
		s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("/Users/sharadkumar/Desktop/monika/Monika_Pasport.pdf")).withCannedAcl(CannedAccessControlList.PublicRead));
		
		System.out.println("FILE TWO UPLOADED SUCCESSFULLY");

	}
}
