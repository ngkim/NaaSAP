package com.kt.smnw.process.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestCloudNWList;
import com.kt.naas.xml.RequestCreateCloudNetwork;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseInfoEthernet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestCreateCloudNetwork {
	@Autowired
	private RequestClient requestClient;
	
	public RequestCreateCloudNetwork generateRequest() {
		RequestCreateCloudNetwork reqCloudNW = new RequestCreateCloudNetwork();
		
		reqCloudNW.setTid("b999ba92afa2456287f7fd1a8b07e755");
		reqCloudNW.setVnid("e3c4d6dc-0443-402f-8390-a238cdb5e512");
		reqCloudNW.setBw("100M");
		
		return reqCloudNW;
	}

	@Test
	public void testCreateCloudNetwork() {
		ResponseCreateCloudNetwork res = new ResponseCreateCloudNetwork();
		
		TimeUtils time = new TimeUtils();
		try {
			time.setStartTime();
			
			RequestCreateCloudNetwork req = generateRequest();	
		
			CloudSDNAPI api = new CloudSDNAPI(GlobalConstants.URL_CLOUD_SDN_API);
			res = api.createNetwork(req);
			
			double duration = time.getDuration() / 1000;			
			System.err.println("\nTime for creating a cloud network = " + duration + " seconds.\n");
			
			api.printResponseCreateCloudNetwork(res);
//			api.printResponseInfoEthernet(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
