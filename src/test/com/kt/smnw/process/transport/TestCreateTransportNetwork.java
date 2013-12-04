package com.kt.smnw.process.transport;

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
		
		req.setName("naasEth-375");
		req.setDescription("POTN connection test");
		
		req.setRid("NaaS");
		req.setCid("88888888880");
		req.seteType("E-LAN");
		
		ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();
		
		UNIPeer sw_cloud = new UNIPeer();
		sw_cloud.setId("L2SW00013");
		sw_cloud.setPort("1");
		sw_cloud.setVlan("10");
					
		peers.add(sw_cloud);
		
		UNIPeer sw_dj = new UNIPeer();
		sw_dj.setId("L2SW00011");
		sw_dj.setPort("22");
		sw_dj.setVlan("10");
		peers.add(sw_dj);
		
		UNIPeer sw_wm = new UNIPeer();
		sw_wm.setId("L2SW00003");
		sw_wm.setPort("26");
		sw_wm.setVlan("10");
					
		peers.add(sw_wm);
		
		req.setPeers(peers);
		
		QoS qos = new QoS();
		
		qos.setBandwidth("100M");
		qos.setExceed("0");
		
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
			TransportSDNAPI api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API_TEST);
			res = api.createNetwork(req);
			
			double duration = time.getDuration() / 1000;
			
			System.err.println("\nTime for creating a transport network = " + duration + " seconds.\n");
			
			api.printResponseCreateTransportNetwork(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
