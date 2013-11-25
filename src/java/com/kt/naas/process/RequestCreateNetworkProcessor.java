package com.kt.naas.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.db.DCNetworkServiceDao;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.NetworkServiceDao;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.db.ProgressStatusDao;
import com.kt.naas.db.TransportNetworkServiceDao;
import com.kt.naas.domain.DCNetworkService;
import com.kt.naas.domain.NetworkService;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.domain.NetworkServiceRequest;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.domain.ProgressStatus;
import com.kt.naas.domain.TenantNetworkInfo;
import com.kt.naas.domain.TransportNetworkService;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.UUIDUtil;
import com.kt.naas.xml.CloudConnectionList;
import com.kt.naas.xml.CloudSwitch;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.RequestCreateCloudNetwork;
import com.kt.naas.xml.RequestCreatePremiseNetwork;
import com.kt.naas.xml.RequestInfoCloudSDNConnnection;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;

public class RequestCreateNetworkProcessor extends RequestProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String cloudUrl = "http://210.183.241.184:5000/createCloudSDNConnection";
	private String premiseUrl = "http://211.224.204.137:8080/NaaS/api.updatePremiseSDNConnection?CpSvcId=CSDN000001";
	private String transportUrl = "http://211.224.204.137/NaaS/api.createTransportEthernetConnection";

	private String cloudKorenUrl = "http://210.183.241.184:5000/ConnectKoren";
	private String cloudInternetUrl = "http://210.183.241.184:5000/ConnectInternet";
	
	private String wmCloudResponseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><DcSvcId>PSDN000001</DcSvcId><TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId><TenantName>cloudsdn</TenantName>"
			+ "<NetworkList><NetworkName>net_sdn</NetworkName>	<Subnet>210.183.241.0/24</Subnet>	<VLANID>10</VLANID>	<Bandwidth>10000</Bandwidth>	"
			+ "<ConnectionList>	           <Switch>		<SWName>WM-Aggr</SWName>		<SWType>Aggregate Switch</SWType>		<SWID>wm_aggr_sw_01</SWID>		<UpPort>1</UpPort>		<DownPort>5</DownPort>	          </Switch>	          <Switch>		<SWName>WM-TOR</SWName>		<SWType>TOR Switch</SWType>		<SWID>wm_tor_sw_01</SWID>		<UpPort>3</UpPort>		<DownPort>4</DownPort>	          </Switch>	"
			+ "</ConnectionList></NetworkList></ResponseInfo>"; 
			
	private String transportRequestXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><Ethernet name=\"myEth\" description=\"DC to DC tunnel\">    <rid>REQ-123456</rid>    <cid>CUSTOMER-abcdefg</cid>    <eType>E-LAN</eType>    <UNIPeer id=\"switch1-ID\" port=\"10\" vlan=\"1001\"/>    <UNIPeer id=\"switch2-ID\" port=\"20\" vlan=\"1002\"/>    <UNIPeer id=\"switch3-ID\" port=\"30\" vlan=\"1003\"/>    <QoS bandwidth=\"1G\" exceed=\"100M\"/></Ethernet>";
	
	@Autowired
	RequestClient requestClient;

	RequestCreateCloudNetwork reqCloud;
	RequestCreatePremiseNetwork reqPremise;

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

	public void dcVlanSwap(String url) {
		// HttpResponse res = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet req = new HttpGet(url);
			client.execute(req);

			// HttpPost req = new HttpPost(url);
			// StringEntity entity = new StringEntity(requestXml);
			// req.setEntity(entity);
			// res = client.execute(req);

		} catch (java.net.NoRouteToHostException noroute) {
			String retMsg = "No Route To Host Exception: API Server is not responding";
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	// Execute VLAN Swapping for a given port to a target vlan
	public void vlanSwap(String snmp_agent, String port_id_to_swap,
			int target_vlan) {

		String cmd_vlan_swap = "";
		cmd_vlan_swap = "snmpset -v 2c -c private " + snmp_agent + " vmVlan."
				+ port_id_to_swap + " integer " + target_vlan;

		Process p;
		String s = null;
		try {
			p = Runtime.getRuntime().exec(cmd_vlan_swap);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((s = br.readLine()) != null)
				System.out.println("line: " + s);
			p.waitFor();

			p.destroy();
		} catch (Exception e) {
		}

	}

	public void revertDJVlans() {
		vlanSwap("10.10.65.5", "10001", 21);
		vlanSwap("10.10.65.5", "10020", 11);
	}

	public void swapDJVlans() {
		vlanSwap("10.10.65.5", "10020", 21);
		vlanSwap("10.10.65.5", "10001", 11);
	}
	
	private void updatePremiseNetworkServiceId(String cpSvcId, String connId, String oldSvcId, String newSvcId) {
		PremiseNetworkService item = null;
		PremiseNetworkService item1 = null;
		
		PremiseNetworkServiceDao dao = DaoFactory.getPremiseNetworkServiceDao();
		
		item = dao.selectPremiseNetworkServiceById(cpSvcId, connId, oldSvcId);
		if (item == null) {
			String retMsg = "요청하신 Enterprise 사이트 정보 조회에 실패하였습니다.";
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
			return;
		} else {
			System.out.println("요청하신 Enterprise 사이트 정보 조회에 성공하였습니다. (SVC ID= " + item.getSvcid() + ")");
			
			item1 = item;
			dao.deletePremiseNetworkServiceById(cpSvcId, connId, oldSvcId);
			
			if (cpSvcId.trim().equals("PSDN000001")) {
				item1.setAddr("(전민지사) 대전광역시 유성구 전민동 463-1");
			} else if  (cpSvcId.trim().equals("PSDN000002")) {
				item1.setAddr("(우면지사) 서울시 서초구 우면동 17");
			}
			
			item1.setSvcid(newSvcId);
//			dao.updatePremiseNetworkService(item);
			dao.insertPremiseNetworkService(item1);
			
			System.out.println("요청하신 Enterprise 사이트 정보를 업데이트하였습니다. (SVC ID= " + item1.getSvcid() + ")");
		}
	}
	
	
	private void insertDCNetworkServiceTable(TenantNetworkInfo tn, String svcId, ResponseCreateCloudNetwork nwRes) {
		DCNetworkService item = new DCNetworkService();
		DCNetworkServiceDao dao = DaoFactory.getDCNetworkServiceDao();
		
		item.setDcsvcid(nwRes.getDcsvcid());
		
		item.setTenantid(nwRes.getTenantid());
		item.setTenantname(nwRes.getTenantname());
		
		item.setTransequipid("T0001");
		item.setTransequipportid("2");
		
		ArrayList<CloudVNID> cvList = nwRes.getVNIDInfo();
		if (cvList != null) {
			for ( int i = 0; i < cvList.size(); i++) {
				CloudVNID cv = cvList.get(i);
				
				item.setNwname(cv.getName());
				item.setConnid(svcId);
				item.setSvcid(svcId);
		
				item.setSubnet(cv.getSubnet());
				
				CloudConnectionList ccl = cv.getConnectionList();
				ArrayList<CloudSwitch> cswList = ccl.getConnectionInfo();
				if (cswList != null){
					for (int j = 0; j < cswList.size(); j++){
						CloudSwitch sw = cswList.get(j);
						
						if (sw.getType().trim().startsWith("Aggregate")) {
							item.setL2id(sw.getDpid());
							item.setL2name(sw.getName());
							item.setL2ip("10.1.1.55");
							item.setL2upportid(sw.getUplinkport());
							item.setL2downportid(sw.getDownlinkport());
							item.setL2bw(Integer.parseInt(cv.getBandwidth()));
							item.setL2vlanid(cv.getVNID());
						} else if (sw.getType().trim().startsWith("TOR")) {
							item.setTorid(sw.getDpid());
							item.setTorname(sw.getName());
							item.setTorip("10.1.1.56");
							item.setTorupportid(sw.getUplinkport());
							item.setTordownportid(sw.getDownlinkport());
							item.setTorbw(Integer.parseInt(cv.getBandwidth()));
							item.setTorvlanid(cv.getVNID());						
						}
					}
				}
			}
		}
		
		item.setStatus(Integer.parseInt(nwRes.getReturnCode()));
		item.setResultmsg(nwRes.getMessage());
		
		item.setDcid(tn.getDcId());
		
		dao.insertDCNetworkService(item);
				
	}
	
	private void updatePremiseNetworkServiceTable(TenantNetworkInfo tn, String svcId) {
		String tmpSvcId = "svc-test";
		
		if(tn.getNwName().trim().startsWith("농협_전민지사")) {
			updatePremiseNetworkServiceId("PSDN000001", "0", tmpSvcId, svcId);
			updatePremiseNetworkServiceId("PSDN000001", "1", tmpSvcId, svcId);
		} else if(tn.getNwName().trim().startsWith("농협_우면지사")) {
			updatePremiseNetworkServiceId("PSDN000002", "0", tmpSvcId, svcId);
			updatePremiseNetworkServiceId("PSDN000002", "1", tmpSvcId, svcId);
			updatePremiseNetworkServiceId("PSDN000002", "2", tmpSvcId, svcId);
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private ResponseCreatePremiseNetwork requestCreatePremiseNetwork(TenantNetworkInfo tn, NetworkServiceRequest svcReq) {
		int reqDomain = 2;
		
		RequestCreatePremiseNetwork req = new RequestCreatePremiseNetwork();
		req.setNetworkname(tn.getNwName());
		req.setTenantName(tn.getTenantName());
		req.setBandwidth(svcReq.getBandwidth());

		HttpResponse res = null;
		ResponseCreatePremiseNetwork nwRes = null;
		try {
			String requestXml = getRequestCreatePremiseNWXML(req);
			System.out.println("Request XML");
			System.out.println(requestXml);
	
			res = requestToAPIServer(reqDomain, requestXml);
	
			String responseXml = getResponseXml(res);
			System.out.println("Response XML");
			System.out.println(responseXml);
			nwRes = getResponseCreatePremiseNW(responseXml);
			
			logger.info(nwRes.getReturnCode());
	
			// TODO: Insert into tables using nwRes
		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
		
		return nwRes;
	}

	@Override
	public void processRequest() {
		// generate a new id
		String svcId = UUIDUtil.generateUUID();
		String transportSvcId = UUIDUtil.generateUUID();

		int currentCnt = 0;
		int totalCnt = 13;

		// Receive request from WAS and Send it to API Server
		HttpResponse res = null;
		NetworkServiceRequest svcReq = null;
		try {
			svcReq = recvRequestfromWeb();
			updateNetworkServiceTable(svcId, svcReq);

			// Notify current progress to Web
			updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
					totalCnt, "네트워크 생성 요청을 준비 중입니다.");

			int reqDomain = 0; // 1: DC, 2: Premise
			String requestXml = "";
			ArrayList<TenantNetworkInfo> tnList = svcReq.getNetworklist();
			System.out.println("tnList.size" + tnList.size());
			for (int i = 0; i < tnList.size(); i++) {
				TenantNetworkInfo tn = tnList.get(i);

				// Request Create Cloud Network
				if (tn.getDcId() != null) {
					reqDomain = 1; // Set Request Domain as DC
					System.out.println("Request create Cloud Network");

					// RequestCreateCloudNetwork req = new
					// RequestCreateCloudNetwork();
					// req.setTenantName(tn.getTenantName());
					// req.setQos(svcReq.getBandwidth()); // QoS로 설정할 수 있는 값이
					// BW뿐임
					// req.setVnid("1024"); // TODO: DC 선택 시에 Web으로부터 VNID가 넘어와야
					// 함

					RequestInfoCloudSDNConnnection req = new RequestInfoCloudSDNConnnection();

					// TODO: ngkim - Tid and Vnid should be changed into human
					// readable format
					req.setTid("e84ea728cd134dea9110df0c2e9398b0");
					req.setVnid("c59c6de5-9a6e-4f30-bc58-3e66d2f95649");
					req.setBw(svcReq.getBandwidth());

					requestXml = getRequestCreateCloudNWXML(req);

					// res = requestToAPIServer(reqDomain, requestXml);

					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Cloud SDN에 Network 생성을 요청하였습니다.");

					// NGKIM - temp comment
					// String responseXml = getResponseXml(res);
					String responseXml = wmCloudResponseXml;
					System.out.println("Response from Cloud");
					sleep();
					System.out.println(responseXml);
					ResponseCreateCloudNetwork nwRes = getResponseCreateCloudNW(responseXml);

					// TODO: csdnCreateNWConnection 의 결과를 network_service_dc
					// 테이블에 입력
					insertDCNetworkServiceTable(tn, svcId, nwRes);
					// TODO: Insert into tables using nwRes
					printResponseCreateCloudNetwork(nwRes);
					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Cloud SDN에 요청한 Network가 생성되었습니다.");

					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Cloud SDN에 VLAN Swapping을 요청합니다.");
					sleep();
//					dcVlanSwap(cloudKorenUrl);

					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Cloud SDN에 요청한 VLAN Swapping이 완료되었습니다.");

				} else {
					reqDomain = 2; // Set Request Domain as Premise

					updatePremiseNetworkServiceTable(tn, svcId);
					System.out.println("Request create Premise Network");
					
					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Premise SDN에 Network 생성을 요청하였습니다.");
					
					ResponseCreatePremiseNetwork nwRes = requestCreatePremiseNetwork(tn, svcReq);
					sleep();
					updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
							totalCnt, "Premise SDN에 요청한 Network가 생성되었습니다.");
				}
			}

			updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
					totalCnt, "DJ Premise SDN VLAN Swapping을 수행합니다.");
			sleep();
//			swapDJVlans();

			reqDomain = 3; // Set Request Domain as Transport
			System.out.println("Request create Transport Network");
			requestXml = transportRequestXml;
			
			res = requestToAPIServer(reqDomain, requestXml);

			updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
					totalCnt, "Transport SDN에 Network 생성을 요청하였습니다.");

			String responseXml = getResponseXml(res);

			System.out.println("TransportSDN: ResponseXML");
			System.out.println(responseXml);

			// Transport Network Service
			TransportNetworkService item_pr1, item_pr2, item_dc;
			TransportNetworkServiceDao networkDao = DaoFactory
					.getTransportNetworkServiceDao();

			item_pr1 = new TransportNetworkService();

			item_pr1.setStatus(0);
			item_pr1.setResultmsg("success");

			item_pr1.setTransvcid(transportSvcId);
			item_pr1.setNwname("kt Transport Network");
			item_pr1.setSvcid(svcId);

			item_pr1.setEquipid("T0002");
			item_pr1.setEquipname("Trans #2");
			item_pr1.setEquipinboundportid("0");
			item_pr1.setEquipoutboundportid("1");
			item_pr1.setEquipbw(200 * 1000 * 1000);
			item_pr1.setEquipvlanid("vlan04");

			item_pr1.setEthtype("DE");
			item_pr1.setConntype("ETH");

			item_pr1.setAssociatedswid("jm_aggr_sw_01");
			item_pr1.setAssociatedswtype("PR");
			item_pr1.setQosofeir("50");
			item_pr1.setQosofcir("50");

			networkDao.insertTransportNetworkService(item_pr1);
			System.out.println(svcId + "TransportSDN - Add new entry - " + item_pr1.getNwname() + "/" + item_pr1.getAssociatedswid());
		
			item_pr2 = new TransportNetworkService();

			item_pr2.setStatus(0);
			item_pr2.setResultmsg("success");

			item_pr2.setTransvcid(transportSvcId);
			item_pr2.setNwname("kt Transport Network");
			item_pr2.setSvcid(svcId);

			item_pr2.setEquipid("T0003");
			item_pr2.setEquipname("Trans #3");
			item_pr2.setEquipinboundportid("0");
			item_pr2.setEquipoutboundportid("1");
			item_pr2.setEquipbw(200 * 1000 * 1000);
			item_pr2.setEquipvlanid("vlan04");

			item_pr2.setEthtype("DE");
			item_pr2.setConntype("ETH");

			item_pr2.setAssociatedswid("wm_aggr_sw_01");
			item_pr2.setAssociatedswtype("PR");
			item_pr2.setQosofeir("50");
			item_pr2.setQosofcir("50");
			
			networkDao.insertTransportNetworkService(item_pr2);
			System.out.println(svcId + "TransportSDN - Add new entry - " + item_pr2.getNwname() + "/" + item_pr2.getAssociatedswid());
			
			item_dc = new TransportNetworkService();

			item_dc.setStatus(0);
			item_dc.setResultmsg("success");

			item_dc.setTransvcid(transportSvcId);
			item_dc.setNwname("kt Transport Network");
			item_dc.setSvcid(svcId);

			item_dc.setEquipid("T0001");
			item_dc.setEquipname("Trans #1");
			item_dc.setEquipinboundportid("0");
			item_dc.setEquipoutboundportid("1");
			item_dc.setEquipbw(200 * 1000 * 1000);
			item_dc.setEquipvlanid("vlan04");

			item_dc.setEthtype("DE");
			item_dc.setConntype("ETH");

			item_dc.setAssociatedswid("wm_aggr_sw_01");
			item_dc.setAssociatedswtype("DC");
			item_dc.setQosofeir("50");
			item_dc.setQosofcir("50");

			networkDao.insertTransportNetworkService(item_dc);
			System.out.println(svcId + "TransportSDN - Add new entry - " + item_dc.getNwname() + "/" + item_dc.getAssociatedswid());

			updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
					totalCnt, "Transport SDN에 요청한 Network가 생성되었습니다.");

			updateProgressStatusTable(svcReq.getCustId(), ++currentCnt,
					totalCnt, "요청한 Network 생성이 완료 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	private ResponseCreatePremiseNetwork getResponseCreatePremiseNW(
			String responseXml) {
		ResponseCreatePremiseNetwork res = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCreatePremiseNetwork.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(responseXml);
			res = (ResponseCreatePremiseNetwork) jaxbUnmarshaller
					.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
		return res;
	}

	private ResponseCreateCloudNetwork getResponseCreateCloudNW(
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
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return res;
	}

	private String getResponseXml(HttpResponse res) {
		String responseXml = "";

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(res
					.getEntity().getContent()));

			String line = "";
			StringBuffer jb = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				jb.append(line);
			}
			responseXml = jb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseXml;
	}

	private HttpResponse requestToAPIServer(int reqDomain, String requestXml) {
		String url = "";

		if (reqDomain == 1) {
			url = cloudUrl;
		} else if (reqDomain == 2) {
			url = premiseUrl;
		} else if (reqDomain == 3) {
			url = transportUrl;
		}

		System.out.println("URL= " + url);
		HttpResponse res = null;
		try {
			HttpClient client = new DefaultHttpClient();

			if (reqDomain == 2) {
				HttpGet req = new HttpGet(url);
				// Get Response
				res = client.execute(req);
			} else {
				HttpPost req = new HttpPost(url);
				StringEntity entity = new StringEntity(requestXml);
				req.setEntity(entity);
				// Get Response
				res = client.execute(req);
			}

		} catch (java.net.NoRouteToHostException noroute) {
			String retMsg = "No Route To Host Exception: API Server is not responding";
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return res;
	}

	// Perform XML Marsharling of Request
	public String getRequestCreateCloudNWXML(RequestInfoCloudSDNConnnection req) {

		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RequestInfoCloudSDNConnnection.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(req, writer);

			requestXml = writer.toString();
		} catch (JAXBException e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		logger.debug(requestXml);
		return requestXml;
	}

	public String getRequestCreatePremiseNWXML(RequestCreatePremiseNetwork req) {

		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RequestCreatePremiseNetwork.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(req, writer);

			requestXml = writer.toString();
		} catch (JAXBException e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		logger.debug(requestXml);
		return requestXml;
	}

	// Insert received Network Service into DB
	public void updateNetworkServiceTable(String svcId,
			NetworkServiceRequest req) {
		String custName = "농협중앙회";

		NetworkService item = null;
		NetworkServiceDao networkDao = DaoFactory.getNetworkServiceDao();
		try {
			item = new NetworkService();

			// Customer Info
			item.setCustId(req.getCustId());
			item.setCustName(custName); // TODO: Customer Name should be passed
										// from Web or DB
			item.setContactPoint("010-xxx-xxxx"); // TODO: Customer contact
													// Point should be passed
													// from Web or DB
			item.setAddress("대전시 유성구 전민동 463-1"); // TODO: Customer address
													// should be passed from Web
													// or DB

			// Service Request
			item.setSvcId(svcId);
			item.setState("C"); // Set state as running

			Random random = new Random();
			item.setSvcName(custName + "-" + random.nextInt(1000));
			item.setSvctype(req.getServiceType());
			item.setTopologyType(req.getTopologyType());
			item.setConnType(req.getConnType());
			item.setBandwidth(req.getBandwidth());

			System.out.println("*** NGKIM Network Service= " + item.getSvcName());
			logger.info(req.getFromTime());
			Timestamp beginTime = Timestamp.valueOf(req.getFromTime() + ":00");
			Timestamp endTime = Timestamp.valueOf(req.getToTime() + ":00");

			item.setBeginTime(beginTime);
			item.setEndTime(endTime);

			networkDao.insertNetworkService(item);
		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	// Insert received Network Service into DB
	private void updateProgressStatusTable(String custId, int currentCnt,
			int totalCnt, String msg) {
		ProgressStatusDao progressDao = DaoFactory.getProgressStatusDao();

		try {
			ProgressStatus progress = null;
			progress = progressDao.selectProgressStatusById(custId);

			if (progress == null) {
				progress = new ProgressStatus();

				progress.setCustid(custId);
				progress.setCurrentcnt(currentCnt);
				progress.setTotalcnt(totalCnt);
				progress.setProcessmsg(msg);

				progressDao.insertProgressStatus(progress);
			} else {
				progress.setCurrentcnt(currentCnt);
				progress.setTotalcnt(totalCnt);
				progress.setProcessmsg(msg);

				progressDao.updateProgressStatus(progress);
			}
			Thread.sleep(1000); // TODO: Remove later...
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	private NetworkServiceRequest recvRequestfromWeb() {
		NetworkServiceRequest nsReq = null;
		try {
			nsReq = new NetworkServiceRequest();

			FieldBuffer inBuf = request.getFieldBuffer();

			String custId = inBuf.getString("CUSTID");
			String serviceType = inBuf.getString("SERVICETYPE");
			String topologyType = inBuf.getString("TOPOLOGYTYPE");
			String connType = inBuf.getString("CONNTYPE");
			String fromTime = inBuf.getString("FROMTIME");
			String toTime = inBuf.getString("TOTIME");
			String bandwidth = inBuf.getString("BANDWIDTH");

			nsReq.setCustId(custId);
			nsReq.setServiceType(serviceType);
			nsReq.setTopologyType(topologyType);
			nsReq.setConnType(connType);
			nsReq.setFromTime(fromTime);
			nsReq.setToTime(toTime);
			nsReq.setBandwidth(Integer.parseInt(bandwidth));

			ArrayList<TenantNetworkInfo> tnInfoList = new ArrayList<TenantNetworkInfo>();

			int itemCnt = inBuf.getCount("GUBUN");
			System.out.println("Total number of items selected: " + itemCnt);
			for (int i = 0; i < itemCnt; i++) {
				TenantNetworkInfo tnInfo = new TenantNetworkInfo();

				tnInfo.setTenantName(inBuf.getString("TENANTNAME"));
				tnInfo.setNwName(inBuf.getString("NWNAME"));

				try {
					String dcId;
					String dcName;
					if ((dcId = inBuf.getString("DCID")) != null) {
						tnInfo.setDcId(dcId);
					}
					if ((dcName = inBuf.getString("DCNAME")) != null) {
						tnInfo.setDcName(dcName);
					}
				} catch (IndexOutOfBoundsException ioobe) {
					System.out.println(ioobe.getMessage());
				}

				if (tnInfoList == null) {
					System.out.println("tnInfoList is null");
				}

				tnInfoList.add(tnInfo);
				System.out.println("tnINfoList - add new tnInfo: "
						+ tnInfo.getNwName());
			}

			nsReq.setNetworklist(tnInfoList);

		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return nsReq;
	}
}
