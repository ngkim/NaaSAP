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
import com.kt.naas.xml.RequestCreatePremiseNetwork;
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
	
	private ResponseCreatePremiseNetwork generateResponse() {
		ResponseCreatePremiseNetwork resPremiseNetwork = new ResponseCreatePremiseNetwork();
		
		resPremiseNetwork.setReturnCode("200");
		resPremiseNetwork.setMessage("No Error");
		resPremiseNetwork.setCpsvcid("CP123456");
		
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
		
		resPremiseNetwork.setAttrList(attrList);
		
		return resPremiseNetwork;
	}
	
	private RequestCreatePremiseNetwork generateRequest() {
		RequestCreatePremiseNetwork reqPremiseNetwork = new RequestCreatePremiseNetwork();
		
		return reqPremiseNetwork;
	}
	
	@Test
	public void testRequestPRNetworkProcessor() {
		ResponseCreatePremiseNetwork response = null; 
		try {
			response = generateResponse();
						
			System.out.println("Request XML...");
			System.out.println(getRequestXML(response));
			
//			nwList = process.getResponseObject(responseXml);
//			printResponseCloudNWList(nwList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
