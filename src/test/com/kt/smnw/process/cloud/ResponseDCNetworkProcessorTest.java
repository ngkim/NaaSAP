package com.kt.smnw.process.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.process.RequestDCNetworkProcessor;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.RestAPIUtils;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ResponseDCNetworkProcessorTest {
	@Autowired
	private RequestClient requestClient;
	
	public void printResponseCloudNWList(ResponseCloudNWList nwList) {
		System.out.println("RETURN CODE= " + nwList.getReturnCode());
		System.out.println("RETURN MSG= " + nwList.getMessage());
		System.out.println("TENANTNAME= " + nwList.getTenantname());
		System.out.println("TENANTID= " + nwList.getTenantid());

		ArrayList<CloudVirtualNetworkList> list = nwList.getVNIDInfo();
		for (int i = 0; i < list.size(); i++) {
			CloudVirtualNetworkList vnl = list.get(i);
			ArrayList<CloudVirtualNetwork> vnlist = vnl.getVnlist();
			for (int j = 0; j < vnlist.size(); j++) {
				CloudVirtualNetwork item = vnlist.get(j);
				System.out.println("NWNAME= " + item.getName());
				System.out.println("VNID= " + item.getVNID());
				System.out.println("SUBNET= " + item.getSubnet());
			}
		}
	}
	
	public String getRequestXML(ResponseCloudNWList req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCloudNWList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(req, writer);

			requestXml = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return requestXml;
	}

	@Test
	public void testRequestPRNetworkProcessor() {
		RequestDCNetworkProcessor process = new RequestDCNetworkProcessor();
		RestAPIUtils api = new RestAPIUtils();
		
		ResponseCloudNWList nwList = null; 
		try {
//			String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><ResponseInfo>   <ReturnCode>200</ReturnCode>   <ReturnCodeDescription>Success</ReturnCodeDescription>   <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   <TenantName>cloudsdn</TenantName>   <VirtualNetworkList>	<VirtualNetwork>	<VirtualNetworkName>net_sdn</VirtualNetworkName>		<VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>10.1.1.0/24</Subnet>	</VirtualNetwork>   </VirtualNetworkList></ResponseInfo>";
			
			nwList = new ResponseCloudNWList();
			nwList.setReturnCode("200");
			nwList.setMessage("No Error");
			nwList.setTenantid("abcde");
			nwList.setTenantname("cloudsdn");
			
			ArrayList<CloudVirtualNetworkList> vnlist = new ArrayList<CloudVirtualNetworkList>();
			ArrayList<CloudVirtualNetwork> list = new ArrayList<CloudVirtualNetwork>();
			
			CloudVirtualNetworkList cvnList = new CloudVirtualNetworkList();
			CloudVirtualNetwork vnid = new CloudVirtualNetwork();
			vnid.setName("vn_1");
			vnid.setSubnet("10.10.10.0/24");
			vnid.setVNID("vnid-1");
			
			CloudVirtualNetwork vnid1 = new CloudVirtualNetwork();
			vnid1.setName("vn_2");
			vnid1.setSubnet("10.10.20.0/24");
			vnid1.setVNID("vnid-2");
			
			list.add(vnid);
			list.add(vnid1);
			
			cvnList.setVnlist(list);
			vnlist.add(cvnList);
			
			nwList.setVNIDInfo(vnlist);
			
			System.out.println("Request XML...");
			System.out.println(getRequestXML(nwList));
			
			String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?> <ResponseInfo>   <ReturnCode>200</ReturnCode>   <ReturnCodeDescription>Success</ReturnCodeDescription>   <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   <TenantName>cloudsdn</TenantName>   <VirtualNetworkList>	<VirtualNetwork>	<VirtualNetworkName>net_sdn</VirtualNetworkName>		<VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>10.1.1.0/24</Subnet>	</VirtualNetwork>   <VirtualNetwork>	<VirtualNetworkName>net_sdn1</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>10.1.2.0/24</Subnet>	</VirtualNetwork>   </VirtualNetworkList> </ResponseInfo>";
			nwList = api.getResponseObject(responseXml, new ResponseCloudNWList());
			printResponseCloudNWList(nwList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
