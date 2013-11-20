package com.kt.smnw.process;

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
import com.kt.naas.xml.CloudSwitch;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.PremiseNetwork;
import com.kt.naas.xml.PremiseSwitch;
import com.kt.naas.xml.PremiseSwitchList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponsePremiseNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ResponsePRCreateNetworkProcessorTest {
	@Autowired
	private RequestClient requestClient;
	
	public String getRequestXML(ResponsePremiseNWList req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponsePremiseNWList.class);
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
	
	public String getRequestXML(PremiseSwitch req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(PremiseSwitch.class);
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
	
	public String getRequestXML(PremiseNetwork req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(PremiseNetwork.class);
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
		
		ResponsePremiseNWList nwList = null; 
		try {
			nwList = new ResponsePremiseNWList();
			
			nwList.setReturnCode("200");
			nwList.setMessage("No Error");
			nwList.setTenantid("abcde");
			nwList.setTenantname("cloudsdn");
			nwList.setCpsvcid("CP123456");
			
			ArrayList<PremiseNetwork> list = new ArrayList<PremiseNetwork>();
			
			PremiseNetwork vnid = new PremiseNetwork();
			vnid.setName("vn_1");
			vnid.setSubnet("10.10.10.0/24");
			vnid.setBandwidth("100M");
			vnid.setVlanid("10");
			
			ArrayList<PremiseSwitchList> swList = new ArrayList<PremiseSwitchList>();
			
			ArrayList<PremiseSwitch> pswList = new ArrayList<PremiseSwitch>();
						
			PremiseSwitch sw = new PremiseSwitch();
			sw.setName("4F Partition");
			sw.setType("End-Point_Switch");
			sw.setSwid("cvbvxc34653");
			sw.setIp("30.30.30.30");
			sw.setUpport("1");
			sw.setDownport("2");
			
			pswList.add(sw);
			
			PremiseSwitch sw1 = new PremiseSwitch();
			sw1.setName("4F Partition");
			sw1.setType("End-Point_Switch");
			sw1.setSwid("cvbvxc34653");
			sw1.setIp("30.30.30.30");
			sw1.setUpport("1");
			sw1.setDownport("2");
			
			pswList.add(sw1);
			
			PremiseSwitchList psw = new PremiseSwitchList();
			psw.setSw(pswList);
			swList.add(psw);			
			vnid.setConnectionList(swList);
			
			list.add(vnid);
			nwList.setVnidlist(list);
			
			System.out.println("Request XML...");
			System.out.println(getRequestXML(nwList));
			
//			String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>CSDN000001</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName><NetworkList><NetworkName>NH_PrivateNW1</NetworkName><Subnet>100.100.100.100/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>cvbvxc34653</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>asfhkjas4234</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>bbc11112222</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList></ResponseInfo>";
			
			
//			nwList = process.getResponseObject(responseXml);
//			printResponseCloudNWList(nwList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
