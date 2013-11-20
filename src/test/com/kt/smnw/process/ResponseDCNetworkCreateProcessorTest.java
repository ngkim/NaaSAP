package com.kt.smnw.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.process.RequestDCNetworkProcessor;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.CloudConnectionList;
import com.kt.naas.xml.CloudSwitch;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoCloudSDNConnnection;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ResponseDCNetworkCreateProcessorTest {
	@Autowired
	private RequestClient requestClient;

	public void printResponseCreateCloudNetwork(ResponseCreateCloudNetwork res) {
		try {
			System.out.println("RETURN CODE= " + res.getReturnCode());
			System.out.println("RETURN MSG= " + res.getMessage());
			System.out.println();
			System.out.println("DCSVCID= " + res.getDcsvcid());
			System.out.println("TENANTNAME= " + res.getTenantname());
			System.out.println("TENANTID= " + res.getTenantid());

			ArrayList<CloudVNID> list = res.getVNIDInfo();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					CloudVNID item = list.get(i);
					System.out.println("\tNWNAME= " + item.getName());
					System.out.println("\tVNID= " + item.getVNID());
					System.out.println("\tSUBNET= " + item.getSubnet());
					System.out.println("\tBW= " + item.getBandwidth());

					CloudConnectionList conList = item.getConnectionList();
					if (conList != null) {
						ArrayList<CloudSwitch> swlist = conList
								.getConnectionInfo();
						if (swlist != null) {
							for (int j = 0; j < swlist.size(); j++) {
								CloudSwitch sw = swlist.get(j);
								System.out.println("\t\tSWNAME= "
										+ sw.getName());
								System.out.println("\t\tSWID= " + sw.getDpid());
								System.out.println("\t\tSWType= "
										+ sw.getType());
								System.out.println("\t\tUpPort= "
										+ sw.getUplinkport());
								System.out.println("\t\tDownPort= "
										+ sw.getDownlinkport());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRequestCreateCloudNWXML(ResponseCreateCloudNetwork res) {

		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCreateCloudNetwork.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(res, writer);

			requestXml = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return requestXml;
	}

	public ResponseCreateCloudNetwork getResponseCreateCloudNW(
			String responseXml) {
		ResponseCreateCloudNetwork res = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCreateCloudNetwork.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(responseXml);
			res = (ResponseCreateCloudNetwork) jaxbUnmarshaller
					.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Test
	public void testResponseDCCreateProcessor() {
		try {
			String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
					+ "<ResponseInfo>"
					+ "<ReturnCode>200</ReturnCode>"
					+ "<ReturnCodeDescription>Success</ReturnCodeDescription>"
					+ "<DcSvcId>PSDN000001</DcSvcId>"
					+ "<TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>"
					+ "<TenantName>cloudsdn</TenantName>"
					+ "<NetworkList>	"
					+ "<NetworkName>net_sdn</NetworkName>	"
					+ "<Subnet>10.1.1.0/24</Subnet>	"
					+ "<VLANID>10</VLANID>	"
					+ "<Bandwidth>10Mbps</Bandwidth>	"
					+ "<ConnectionList>	           "
					+ "<Switch>		"
					+ "<SWName>WM-Aggr</SWName>		"
					+ "<SWType>Aggregate Switch</SWType>		"
					+ "<SWID>0xabcde</SWID>		"
					+ "<UpPort>1</UpPort>		"
					+ "<DownPort>5</DownPort>	          "
					+ "</Switch>	          "
					+ "<Switch>		"
					+ "<SWName>WM-TOR</SWName>		"
					+ "<SWType>TOR Switch</SWType>		"
					+ "<SWID>0xdefgh</SWID>		"
					+ "<UpPort>3</UpPort>		"
					+ "<DownPort>4</DownPort>	          "
					+ "</Switch>	"
					+ "</ConnectionList>"
					+ "</NetworkList>"
					+ "</ResponseInfo>";

			ResponseCreateCloudNetwork nwRes = getResponseCreateCloudNW(responseXml);
			printResponseCreateCloudNetwork(nwRes);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRequestDCCreateProcessor() {
		RequestDCNetworkProcessor process = new RequestDCNetworkProcessor();

		/*
		 * + "<NetworkName>net_sdn</NetworkName>	" +
		 * "<Subnet>10.1.1.0/24</Subnet>	" + "<VLANID>10</VLANID>	" +
		 * "<Bandwidth>10</Bandwidth>	"
		 */
		try {
			ResponseCreateCloudNetwork nwRes = new ResponseCreateCloudNetwork();
			nwRes.setReturnCode("200");
			nwRes.setMessage("Success");
			nwRes.setDcsvcid("PSDN000001");
			nwRes.setTenantid("0be2a9fc8a7040d8963c9f67f521bdab");
			nwRes.setTenantname("cloudsdn");

			ArrayList<CloudVNID> vnidlist = new ArrayList<CloudVNID>();
			CloudVNID vn = new CloudVNID();
			vn.setName("net_sdn");
			vn.setSubnet("10.1.1.0/24");
			vn.setVNID("10");
			vn.setBandwidth("10");

			CloudConnectionList conList = new CloudConnectionList();

			ArrayList<CloudSwitch> swlist = new ArrayList<CloudSwitch>();

			CloudSwitch sw = new CloudSwitch();
			sw.setName("Woomyeon Aggregate SW");
			sw.setDpid("0x123456");
			sw.setType("Aggregate SW");
			sw.setUplinkport("3");
			sw.setDownlinkport("5");

			CloudSwitch sw1 = new CloudSwitch();
			sw1.setName("Woomyeon ToR SW");
			sw1.setDpid("0x345678");
			sw1.setType("TOR SW");
			sw1.setUplinkport("5");
			sw1.setDownlinkport("8");

			swlist.add(sw);
			swlist.add(sw1);

			conList.setConnectionInfo(swlist);

			vn.setConnectionList(conList);
			vnidlist.add(vn);
			nwRes.setVNIDInfo(vnidlist);

			String responseXml = getRequestCreateCloudNWXML(nwRes);
			System.out.println(responseXml);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRequestDCNetworkCreateProcessor() {
		RequestDCNetworkProcessor process = new RequestDCNetworkProcessor();

		try {//
			String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
					+ "<ResponseInfo>"
					+ "<ReturnCode>200</ReturnCode>"
					+ "<ReturnCodeDescription>Success</ReturnCodeDescription>"
					+ "<DcSvcId>PSDN000001</DcSvcId>"
					+ "<TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>"
					+ "<TenantName>cloudsdn</TenantName>"
					+ "<NetworkList>	"
					+ "<NetworkName>net_sdn</NetworkName>	"
					+ "<Subnet>10.1.1.0/24</Subnet>	"
					+ "<VLANID>10</VLANID>	"
					+ "<Bandwidth>10</Bandwidth>	"
					+ "<ConnectionList>	           "
					+ "<Switch>		"
					+ "<SWName></SWName>		"
					+ "<SWType></SWType>		"
					+ "<SWID></SWID>		"
					+ "<UpPort></UpPort>		"
					+ "<DownPort></DownPort>	          "
					+ "</Switch>	          "
					+ "<Switch>		"
					+ "<SWName></SWName>		"
					+ "<SWType></SWType>		"
					+ "<SWID></SWID>		"
					+ "<UpPort></UpPort>		"
					+ "<DownPort></DownPort>	          "
					+ "</Switch>	"
					+ "</ConnectionList>"
					+ "</NetworkList>"
					+ "</ResponseInfo>";
			ResponseCreateCloudNetwork nwRes = getResponseCreateCloudNW(responseXml);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
