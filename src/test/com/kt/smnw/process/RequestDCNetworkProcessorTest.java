package com.kt.smnw.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.util.RequestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class RequestDCNetworkProcessorTest {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestDCNetworkProcessor() {
		requestClient.init();
		RequestMessage request = new RequestMessage();

		request.setServiceName("REQ_DC_NW_LIST");

		FieldBuffer buf = request.getFieldBuffer();

		buf.putString("DCID", "목동DC");
		buf.putString("TENANTNAME", "My Tenant");
		
		String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId><TenantName>cloudsdn</TenantName><VirtualNetworkList><VirtualNetworkName>net_sdn</VirtualNetworkName><VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID><Subnet>10.1.1.0/24</Subnet></VirtualNetworkList></ResponseInfo>";

		requestClient.requestToAp(request);
		try {
			System.out.println("press enter to exit...");
			(new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
