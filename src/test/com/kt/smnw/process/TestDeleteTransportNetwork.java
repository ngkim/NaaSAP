package com.kt.smnw.process;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.RestAPIUtils;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.QoS;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestDeleteTransportNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestDeleteTransportNetwork generateRequest() {
		RequestDeleteTransportNetwork req = new RequestDeleteTransportNetwork();
		
		req.setRid("NaaS");
		req.setCid("88888888880");
		req.setEid("1234");
		
		return req;
	}

	@Test
	public void testDeleteTransportNetwork() {
		ResponseDeleteTransportNetwork res = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			
			RequestDeleteTransportNetwork req = generateRequest();
			TransportSDNAPI api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API);
			res = api.deleteNetwork(req);
			
			double duration = time.getDuration();
			
			System.out.println("Time for creating a transport network = " + duration);
			
//			api.printResponseDeleteTransportNetwork(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
