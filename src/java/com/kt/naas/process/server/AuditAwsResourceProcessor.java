package com.kt.naas.process.server;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.codec.binary.Base64;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.VmIpDao;
import com.kt.naas.domain.Server;
import com.kt.naas.domain.VmIp;
import com.kt.naas.process.Processor;
import com.kt.naas.util.JsonResponseUtils;

public class AuditAwsResourceProcessor implements Processor {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void process(Object message) {
		if (message == null) return;
		
		Server server = (Server)message;

		// Dao
		VmIpDao vmIpDao = DaoFactory.getVmIpDao();
		
		String accessKey = "";
		accessKey = "#Insert your AWS Credentials from http://aws.amazon.com/security-credentials\n" +
				"#Thu Sep 01 09:21:23 PDT 2011\n" +
				"accessKey=AKIAIUVHLNRD45I2IVPQ\n" +
				"secretKey=j1UhkS87f/ZoKLsrb6bryWZBL8pdtECSpc3/dMVg";
		

		try {
			
			// CONNECT TO EC2
			InputStream credentialsAsStream = new ByteArrayInputStream(accessKey.toString().getBytes("UTF-8"));
			AWSCredentials credentials = new PropertiesCredentials(credentialsAsStream);
	
			AmazonEC2 ec2 = new AmazonEC2Client(credentials);
			//ec2.setEndpoint("ec2.eu-west-1.amazonaws.com");
			ec2.setEndpoint("ec2.amazonaws.com");

			
			//--------------------------------
			// DescribeAddress
			// - Elastic IP 리스트를 조회한다.
			//--------------------------------
			DescribeAddressesRequest describeAddressesRequest = new DescribeAddressesRequest();	
			DescribeAddressesResult describeAddresses = ec2.describeAddresses();			
			System.out.println("======describeAddresses :  "	+ describeAddresses);
			
			List<VmIp> vmIps = JsonResponseUtils.getAwsIps(describeAddresses.toString());		
			System.out.println("=== vmIps : " + vmIps);
			
			vmIpDao.deleteIpByServerId(server.getServerid());
			for (VmIp vmIp : vmIps)
			{			
				//insert
				System.out.println("=== vmIp : " + vmIp);
				vmIp.setServerid(server.getServerid());
				vmIpDao.insertIp(vmIp);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}