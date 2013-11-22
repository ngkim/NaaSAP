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
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestResponseCreateTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestCreateTransportNetwork() {
		ResponseCreateTransportNetwork res = new ResponseCreateTransportNetwork();
		RestAPIUtils apiUtil = new RestAPIUtils();
		
		try {
			res.setId("E-ID");
			res.setName("myEth");
			res.setDescription("DC to DC tunnel");
			
			res.setStatus("OK");
			res.setRid("requester ID");
			res.setCid("123");
			res.seteType("E-LAN");
			
			ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();
			
			UNIPeer p1 = new UNIPeer();
			p1.setId("switch1-ID");
			p1.setPort("10");
			p1.setVlan("1001");
			
			UNIME u1 = new UNIME();
			u1.setId("POTN-ME-ID1");
			u1.setPort("3");
			u1.setName("POTN-ME-NAME1");
			
			p1.setU(u1);
			
			UNIPeer p2 = new UNIPeer();
			p2.setId("switch2-ID");
			p2.setPort("20");
			p2.setVlan("1002");
			
			UNIME u2 = new UNIME();
			u2.setId("POTN-ME-ID2");
			u2.setPort("4");
			u2.setName("POTN-ME-NAME2");
			
			p2.setU(u2);
			
			UNIPeer p3 = new UNIPeer();
			p3.setId("switch3-ID");
			p3.setPort("30");
			p3.setVlan("1003");
			
			UNIME u3 = new UNIME();
			u3.setId("POTN-ME-ID3");
			u3.setPort("5");
			u3.setName("POTN-ME-NAME3");
			
			p3.setU(u3);
			
			peers.add(p1);
			peers.add(p2);
			peers.add(p3);
			res.setPeers(peers);
			
			QoS qos = new QoS();
			qos.setBandwidth(1000 * 1000);
			qos.setExceed(1000);
			res.setQos(qos);
			
			String xml = apiUtil.getRequestXML(res);
			
			System.out.println(xml);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
