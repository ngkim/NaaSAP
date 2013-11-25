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
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestCreateTransportNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestCreateTransportNetwork generateRequest() {
		RequestCreateTransportNetwork req = new RequestCreateTransportNetwork();
		
		req.setName("myEth");
		req.setDescription("DJ POTN connection test");
		
		req.setRid("NaaS");
		req.setCid("88888888880");
		req.seteType("E-LINE");
		
		ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();
		
		UNIPeer sw_potn = new UNIPeer();
		sw_potn.setId("L2SW00001");
		sw_potn.setPort("8");
		sw_potn.setVlan("100");
		peers.add(sw_potn);
		
		UNIPeer sw_naas = new UNIPeer();
		sw_naas.setId("L2SW00011");
		sw_naas.setPort("22");
		sw_naas.setVlan("100");
					
		peers.add(sw_naas);
		req.setPeers(peers);
		
		QoS qos = new QoS();
		
		qos.setBandwidth("100M");
		qos.setExceed("10M");
		
		req.setQos(qos);
		
		return req;
	}

	@Test
	public void testCreateTransportNetwork() {
		ResponseCreateTransportNetwork res = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			
			RequestCreateTransportNetwork req = generateRequest();
			TransportSDNAPI api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API);
			res = api.createNetwork(req);
			
			double duration = time.getDuration();
			
			System.err.println("\nTime for creating a transport network = " + duration + " ms");
			
			api.printResponseCreateTransportNetwork(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
