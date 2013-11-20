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
import com.kt.naas.xml.QoSAttribute;
import com.kt.naas.xml.QoSAttributeList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponsePremiseNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ResponsePRNetworkProcessorTest {
	@Autowired
	private RequestClient requestClient;
	
	public String getRequestXML(ResponseCreatePremiseNetwork req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCreatePremiseNetwork.class);
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
		
		ResponseCreatePremiseNetwork response = null; 
		try {
			response = new ResponseCreatePremiseNetwork();
			
			response.setReturnCode("200");
			response.setMessage("No Error");
			response.setCpsvcid("CP123456");
			
			ArrayList<QoSAttributeList> attrList = new ArrayList<QoSAttributeList>();
			
			QoSAttributeList qosList = new QoSAttributeList();
			
			ArrayList<QoSAttribute> qaList = new ArrayList<QoSAttribute>();
			
			QoSAttribute attr = new QoSAttribute();
			attr.setAttrName("Bandwidth");
			attr.setPrevValue("100M");
			attr.setUpdateValue("1G");
			
			qaList.add(attr);
			
			QoSAttribute attr1 = new QoSAttribute();
			attr1.setAttrName("CoS");
			attr1.setPrevValue("1");
			attr1.setUpdateValue("2");
			
			qaList.add(attr1);
			
			qosList.setQosAttribute(qaList);
			
			attrList.add(qosList);
			
			response.setAttrList(attrList);
						
			System.out.println("Request XML...");
			System.out.println(getRequestXML(response));
			
//			String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>CSDN000001</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName><NetworkList><NetworkName>NH_PrivateNW1</NetworkName><Subnet>100.100.100.100/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>cvbvxc34653</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>asfhkjas4234</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>bbc11112222</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList></ResponseInfo>";
			
			
//			nwList = process.getResponseObject(responseXml);
//			printResponseCloudNWList(nwList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
