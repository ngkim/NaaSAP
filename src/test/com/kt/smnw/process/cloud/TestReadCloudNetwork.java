package com.kt.smnw.process.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.RequestCloudNWList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseInfoEthernet;

import java.util.List;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestReadCloudNetwork {
	@Autowired
	private RequestClient requestClient;
	
	public RequestInfoCloudSDN generateRequest() {
		RequestInfoCloudSDN reqCloudNW = new RequestInfoCloudSDN();
//		RequestCloudNWList reqCloudNW = new RequestCloudNWList();
		
		reqCloudNW.setDcid("dn_0001");
//		reqCloudNW.setTenantName("e84ea728cd134dea9110df0c2e9398b0");
		reqCloudNW.setTid("b999ba92afa2456287f7fd1a8b07e755");
		
		return reqCloudNW;
	}
	
	public ResponseCloudNWList getSampleResponse() {
		ResponseCloudNWList res = new ResponseCloudNWList();
		
		res.setMessage("Success");
		res.setReturnCode("200");
		
		ArrayList<CloudVirtualNetwork> vnidlist = new ArrayList<CloudVirtualNetwork>();
		CloudVirtualNetwork vn1 = new CloudVirtualNetwork();
		vn1.setName("net_cloudsdn");
		vn1.setSubnet("e3c4d6dc-0443-402f-8390-a238cdb5e512");
		vn1.setVNID("210.183.241.0/24");
		vnidlist.add(vn1);
		
		CloudVirtualNetwork vn2 = new CloudVirtualNetwork();
		vn2.setName("net_cloudsdn1");
		vn2.setSubnet("e3c4d6dc-0443-402f-8390-a238cdb5e513");
		vn2.setVNID("210.183.242.0/24");
		vnidlist.add(vn2);
		
		CloudVirtualNetwork vn3 = new CloudVirtualNetwork();
		vn3.setName("net_cloudsdn2");
		vn3.setSubnet("e3c4d6dc-0443-402f-8390-a238cdb5e514");
		vn3.setVNID("210.183.243.0/24");
		vnidlist.add(vn3);
		
		res.setVNIDInfo(vnidlist);
		
		res.setTenantid("b999ba");
		res.setTenantname("cloudsdn");
		
		return res;
	}

	@Test
	public void testReadCloudNetwork() {
		ResponseCloudNWList res = new ResponseCloudNWList();
		
		TimeUtils time = new TimeUtils();
		try {
			time.setStartTime();
			
			RequestInfoCloudSDN req = generateRequest();	
		
			CloudSDNAPI api = new CloudSDNAPI(GlobalConstants.URL_CLOUD_SDN_API_TEST);
			res = api.readNetwork(req);
			
			double duration = time.getDuration();			
			System.err.println("\nTime for reading a cloud network = " + duration + " ms\n");
			
			api.printResponseCloudNWList(res);
						
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
