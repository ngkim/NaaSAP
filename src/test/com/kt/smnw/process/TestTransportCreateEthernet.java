package com.kt.smnw.process;
import java.net.URL;
import java.io.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.util.RequestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestTransportCreateEthernet {
	@Autowired
	private RequestClient requestClient;
	
	private static class CustomizedHostnameVerifier implements HostnameVerifier
	{
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	@Test
	public void testRequestCreateTransportNetwork() {
//		String httpsURL = "https://220.123.31.55:7502/createEthernet";
		String httpsURL = "https://211.224.204.144:7502/createEthernet";

		SslConfigurator sslConfig = SslConfigurator.newInstance()
				.trustStoreFile("./trust/truststore_client")
				.trustStorePassword("asdfgh")
				.keyStoreFile("./trust/keystore_client")
				.keyPassword("asdfgh");
		
		Client client = ClientBuilder.newBuilder()
				.hostnameVerifier(new CustomizedHostnameVerifier())
				.sslContext(sslConfig.createSSLContext())
				.build();

		client.register(new HttpBasicAuthFilter("admin", "admin"));
		
		Response res = client.target(httpsURL).request(MediaType.APPLICATION_XML_TYPE).post(null);

		System.out.println("Git Test");
		System.out.println(res.readEntity(String.class));
		System.out.println("Done!!!");

	 
	    client.close();
	}
	
	public static void main(String args[]){
//		String httpsURL = "https://220.123.31.55:7502/createEthernet";
		String httpsURL = "https://211.224.204.144:7502/createEthernet";

		SslConfigurator sslConfig = SslConfigurator.newInstance()
				.trustStoreFile("./trust/truststore_client")
				.trustStorePassword("asdfgh")
				.keyStoreFile("./trust/keystore_client")
				.keyPassword("asdfgh");
		
		System.out.println("1234");

		Client client = ClientBuilder.newBuilder()
				.hostnameVerifier(new CustomizedHostnameVerifier())
				.sslContext(sslConfig.createSSLContext())
				.build();

		client.register(new HttpBasicAuthFilter("admin", "admin"));
		
		Response res = client.target(httpsURL).request(MediaType.APPLICATION_XML_TYPE).post(null);

		System.out.println("Git Test");
		System.out.println(res.readEntity(String.class));
	 
	    client.close();
	}
	
}