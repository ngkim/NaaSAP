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
import com.kt.naas.process.RequestCreateNetworkProcessor;
import com.kt.naas.process.RequestDCNetworkProcessor;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.ResponseCloudNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class RequestVlanSwapTest {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestPRNetworkProcessor() {
		RequestCreateNetworkProcessor process = new RequestCreateNetworkProcessor();

		try {
			process.vlanSwap("10.10.65.3", "10001", 11);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		RequestCreateNetworkProcessor process = new RequestCreateNetworkProcessor();

		try {
			System.out.println("Run vlan swap test...");
			process.vlanSwap("10.10.65.3", "10001", 11);
			System.out.println("Done...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
