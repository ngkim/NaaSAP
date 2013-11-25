package com.kt.smnw.process;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.util.RequestClient;
import com.kt.naas.util.RestAPIUtils;
import com.kt.naas.xml.QoS;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestRequestCreateTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestCreateTransportNetwork() {
		RequestCreateTransportNetwork req = new RequestCreateTransportNetwork();
		RestAPIUtils apiUtil = new RestAPIUtils();
		
		try {
			req.setName("myEth");
			req.setDescription("DC to DC tunnel");
			
			req.setRid("requester ID");
			req.setCid("123");
			req.seteType("E-LAN");
			
			ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();
			
			UNIPeer p1 = new UNIPeer();
			p1.setId("switch1-ID");
			p1.setPort("10");
			p1.setVlan("1001");
			
			UNIPeer p2 = new UNIPeer();
			p2.setId("switch2-ID");
			p2.setPort("20");
			p2.setVlan("1002");
			
			UNIPeer p3 = new UNIPeer();
			p3.setId("switch3-ID");
			p3.setPort("30");
			p3.setVlan("1003");
			
			peers.add(p1);
			peers.add(p2);
			peers.add(p3);
			req.setPeers(peers);
			
			QoS qos = new QoS();
			qos.setBandwidth("100M");
			qos.setExceed("10M");
			req.setQos(qos);
			
			String xml = apiUtil.getRequestXML(req);
			
			System.out.println(xml);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
