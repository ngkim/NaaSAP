package com.kt.smnw.process.transport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestDeleteTransportNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestDeleteTransportNetwork generateRequest() {
		RequestDeleteTransportNetwork req = new RequestDeleteTransportNetwork();
		
		req.setName("naasEth");
		req.setDescription("DJ POTN connection test");
		
		req.setRid("NaaS");
		req.setCid("88888888880");
		req.setEid("NS_37203");
				
		return req;
	}

	@Test
	public void testDeleteTransportNetwork() {
		ResponseDeleteTransportNetwork res = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			
			RequestDeleteTransportNetwork req = generateRequest();
			TransportSDNAPI api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API_TEST);
			res = api.deleteNetwork(req);
			
			double duration = time.getDuration() / 1000;
			
			System.err.println("Time for deleting a transport network = " + duration);
			
			api.printResponseDeleteTransportNetwork(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
