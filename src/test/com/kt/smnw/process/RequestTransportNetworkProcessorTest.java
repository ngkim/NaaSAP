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
public class RequestTransportNetworkProcessorTest {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestPRNetworkProcessor() {
		requestClient.init();
		RequestMessage request = new RequestMessage();

		request.setServiceName("REQ_PR_NW_LIST");

		FieldBuffer buf = request.getFieldBuffer();
		
		buf.putString("TENANTNAME", "My Tenant");
		
		requestClient.requestToAp(request);
		try {
			System.out.println("press enter to exit...");
			(new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
