package com.kt.naas.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.DomainNetworkDao;
import com.kt.naas.db.DomainNetworkEquipDao;
import com.kt.naas.db.DomainNetworkPortDao;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.domain.DomainNetwork;
import com.kt.naas.domain.DomainNetworkEquip;
import com.kt.naas.domain.DomainNetworkPort;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.PremiseNetwork;
import com.kt.naas.xml.PremiseSwitch;
import com.kt.naas.xml.PremiseSwitchList;
import com.kt.naas.xml.RequestPremiseNWList;
import com.kt.naas.xml.ResponsePremiseNWList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class RequestPRNetworkProcessor extends RequestProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String nmsId;
	private String url = "http://211.224.204.145:8080/APIServer/psdnRetrieveNWList";
	private String url_wm_nms = "http://211.224.204.157:8080/APIServer/psdnRetrieveNWList";
	private String url_dj_nms = "http://211.224.204.137:8080/NaaS/api.retrievePremiseSDNConnection";
	
	private String djPremiseResponseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>PSDN000001</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName>"
			+ "<NetworkList><NetworkName>농협_전민지사_사내망_1</NetworkName><Subnet>221.145.180.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>jm_endpoint_sw_01</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>jm_l2_sw_01</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>jm_aggr_sw_01</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_전민지사_사내망_2</NetworkName><Subnet>221.145.200.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>jm_endpoint_sw_02</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>jm_l2_sw_02</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>jm_aggr_sw_02</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "</ResponseInfo>";
	private String wmPremiseResponseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>PSDN000002</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_1</NetworkName><Subnet>210.183.240.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_01</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_01</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_01</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_2</NetworkName><Subnet>210.183.241.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_02</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_02</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_02</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_3</NetworkName><Subnet>210.183.242.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_03</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_03</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_03</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "</ResponseInfo>";

	@Autowired
	RequestClient requestClient;

	public void sendResponse() {

	}

	public void updatePremiseNetworkServiceTable(ResponsePremiseNWList nwList) {
		PremiseNetworkService item = null;
		PremiseNetworkServiceDao dao = DaoFactory.getPremiseNetworkServiceDao();
		try {
			String svcId = "svc-test";

			String returnCode = nwList.getReturnCode();
			String returnCodeDescription = nwList.getMessage();
			String cpSvcid = nwList.getCpsvcid();
			String tenantId = nwList.getTenantid();
			String tenantName = nwList.getTenantname();

			ArrayList<PremiseNetwork> pnList = nwList.getVnidlist();
			if (pnList != null) {
				for (int i = 0; i < pnList.size(); i++) {
					PremiseNetwork pn = pnList.get(i);
					ArrayList<PremiseSwitchList> psList = pn
							.getConnectionList();

					if (psList != null) {
						for (int j = 0; j < psList.size(); j++) {
							// select existing item from PremiseNetworkService
							// table
							item = dao.selectPremiseNetworkServiceById(
									nwList.getCpsvcid(), Integer.toString(i),
									svcId);

							boolean isNew = false;
							if (item == null) {
								System.out.println("*** NGKIM *** : New Premise Network...");
								isNew = true;
								item = new PremiseNetworkService();
							}

							item.setCpsvcid(cpSvcid);
							item.setConnid(Integer.toString(i));
							item.setSvcid(svcId);
							item.setTenantid(tenantId);
							item.setTenantname(tenantName);

							item.setStatus(Integer.parseInt(returnCode));
							item.setResultmsg(returnCodeDescription);

							item.setNwname(pn.getName());
							item.setSubnet(pn.getSubnet());

							int bw = 100 * 1000 * 1000;
							item.setAggrbw(bw);
							item.setL2bw(bw);
							item.setEndpointbw(bw);

							item.setAggrvlanid(pn.getVlanid());
							item.setL2vlanid(pn.getVlanid());
							item.setEndpointvlanid(pn.getVlanid());

							PremiseSwitchList psl = psList.get(j);
							ArrayList<PremiseSwitch> pSwitch = psl.getSw();
							if (pSwitch != null) {
								for (int k = 0; k < pSwitch.size(); k++) {
									PremiseSwitch sw = pSwitch.get(k);

									if (sw.getType().trim()
											.equals("Aggregate_Switch")) {
										item.setAggrname(sw.getName());
										item.setAggrid(sw.getSwid());
										item.setAggrip(sw.getIp());
										item.setAggrupportid(sw.getUpport());
										item.setAggrdownportid(sw.getDownport());
									} else if (sw.getType().trim()
											.equals("L2_Switch")) {
										item.setL2name(sw.getName());
										item.setL2id(sw.getSwid());
										item.setL2ip(sw.getIp());
										item.setL2upportid(sw.getUpport());
										item.setL2downportid(sw.getDownport());
									} else if (sw.getType().trim()
											.equals("End-Point_Switch")) {
										item.setEndpointname(sw.getName());
										item.setEndpointid(sw.getSwid());
										item.setEndpointip(sw.getIp());
										item.setEndpointupportid(sw.getUpport());
										item.setEndpointdownportid(sw
												.getDownport());
									}
								}
							}

							if (isNew)
								dao.insertPremiseNetworkService(item);
							else
								dao.updatePremiseNetworkService(item);
						}
					}
				}
			}
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	public void updateDomainNetworkTables(ResponsePremiseNWList nwList) {
		ArrayList<PremiseNetwork> pnList = nwList.getVnidlist();
		if (pnList != null) {
			for (int i = 0; i < pnList.size(); i++) {
				PremiseNetwork pn = pnList.get(i);
				ArrayList<PremiseSwitchList> psList = pn.getConnectionList();

				if (psList != null) {
					for (int j = 0; j < psList.size(); j++) {
						PremiseSwitchList psl = psList.get(j);
						ArrayList<PremiseSwitch> pSwitch = psl.getSw();

						if (pSwitch != null) {
							for (int k = 0; k < pSwitch.size(); k++) {
								PremiseSwitch sw = pSwitch.get(k);
								// update DomainNetworkEquip table
								updateDomainNetworkEquipTable(
										nwList.getCpsvcid(), sw);

								String swId = sw.getSwid();
								// update DomainNetworkPort table for both
								// upport and downport (default porttyp is L2)
								updateDomainNetworkPortTable(swId,
										sw.getUpport(),
										swId + "/" + sw.getUpport(), "L2");
								updateDomainNetworkPortTable(swId,
										sw.getDownport(),
										swId + "/" + sw.getDownport(), "L2");
							}
						}
					}
				}

				// TODO: update DomainNetworkLink
			}
		}

	}

	// Insert received Domain Network into DB
	public void updateDomainNetworkTable(ResponsePremiseNWList nwList) {
		DomainNetwork item = null;
		DomainNetworkDao networkDao = DaoFactory.getDomainNetworkDao();
		try {
			String networkId = nwList.getCpsvcid();
			item = networkDao.selectDomainNetworkById(networkId);

			if (item == null) {
				item = new DomainNetwork();

				item.setDnid(nwList.getCpsvcid());
				item.setDnname("Premise Network"); // TODO:
													// item.setDnname(nwList.getNetworkname());
				item.setDntype("PR");
				item.setRootyn("N");
				item.setConntype("E-LAN"); // Premise NW's default connection
											// type is E-LAN.

				networkDao.insertDomainNetwork(item);
			} else {
				item.setDnname("Premise Network");
				item.setDntype("PR");
				item.setRootyn("N");
				item.setConntype("E-LAN"); // Premise NW's default connection
											// type is E-LAN.

				networkDao.updateDomainNetwork(item);
			}
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	// Perform XML Unmarsharling of Response
	public ResponsePremiseNWList getResponseObject(String responseXml) {

		ResponsePremiseNWList res = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponsePremiseNWList.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(responseXml);
			res = (ResponsePremiseNWList) jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return res;
	}

	public HttpResponse requestToAPIServer(String requstXml) {

		HttpResponse res = null;
		try {
			HttpClient client = new DefaultHttpClient();

			HttpGet req = new HttpGet(url);
			// HttpPost req = new HttpPost(url);
			// StringEntity entity = new StringEntity(requestXml);
			// req.setEntity(entity);

			// Get Response
			res = client.execute(req);
		} catch (java.net.NoRouteToHostException noroute) {
			String retMsg = "No Route To Host Exception: API Server (" + url + ") is not responding";
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return res;

	}

	public String getResponseXml(HttpResponse res) {
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

	public void sendPRNWResponseToWeb(ResponsePremiseNWList nwList) {
		FieldBuffer buf = null;

		try {
			buf = new FieldBuffer();

			buf.putString("TENANTNAME", nwList.getTenantname());
			 
			 ArrayList<PremiseNetwork> pnList = nwList.getVnidlist();
			 if (pnList != null) {
				 for (int i = 0; i < pnList.size(); i++) {
					 PremiseNetwork pn = pnList.get(i);
					 
					 buf.putString("NWNAME", pn.getName());
					 buf.putString("SUBNET", pn.getSubnet());					 
				 }
			 }
//			buf.putString("NWNAME", "Premise Network"); // TODO: NGKIM - Retrieve from nwList
//			buf.putString("SUBNET", "10.10.1.0/24");

			// Send response to Web
			response.setFieldBuffer(buf);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	// Perform XML Marsharling of Request
	public String getRequestXML(RequestPremiseNWList req) {

		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RequestPremiseNWList.class);
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

		return requestXml;
	}

	// Get Messages and Create Request
	public RequestPremiseNWList recvRequestfromWeb() {
		String tenantname = "";
		
		try {
			FieldBuffer inBuf = request.getFieldBuffer();

			tenantname = inBuf.getString("TENANTNAME");
			nmsId = inBuf.getString("NMSID");

			if (nmsId != null) {
				if (nmsId.trim().equals("1"))
					url = url_dj_nms;
				else if (nmsId.trim().equals("2"))
					url = url_wm_nms;
				else
					url = url_dj_nms;
			}
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		RequestPremiseNWList req = null;
		try {
			req = new RequestPremiseNWList();
			req.setTenantName(tenantname);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return req;
	}

	@Override
	public void processRequest() {
		// Receive request from WAS and Send it to API Server
		HttpResponse res = null;
		try {
			RequestPremiseNWList req = recvRequestfromWeb();

			String requestXml = getRequestXML(req);
			logger.debug(requestXml);

//			res = requestToAPIServer(requestXml);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		// Send response to WAS
		try {
//			String responseXml = getResponseXml(res);
			String responseXml = "";
			if (nmsId != null) {
				if (nmsId.trim().equals("1")) {
					url = url_dj_nms;
					responseXml = djPremiseResponseXml;
				} else if (nmsId.trim().equals("2")) {
					url = url_wm_nms;
					responseXml = wmPremiseResponseXml;
				} else
					url = url_dj_nms;
			}
			
			System.out.println(responseXml);

			ResponsePremiseNWList nwList = getResponseObject(responseXml);
			Thread.sleep(1000); // NGKIM - to be removed...
			if (nwList == null) {
				String errMsg = "Error! No NW List...";
				System.err.println(errMsg);

				response.setResultCode(-1);
				response.setResultMessage(errMsg);

				return;
			}

			// TODO: network_service_premise에 정보를 입력
			updateDomainNetworkTable(nwList);

			updateDomainNetworkTables(nwList);

			updatePremiseNetworkServiceTable(nwList);

			sendPRNWResponseToWeb(nwList);
		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

	}

	private void updateDomainNetworkPortTable(String swId, String portId,
			String portName, String portType) {
		DomainNetworkPort item = null;
		DomainNetworkPortDao dao = DaoFactory.getDomainNetworkPortDao();
		try {
			item = dao.selectDomainNetworkPortById(portId, swId);

			if (item == null) {
				item = new DomainNetworkPort();

				item.setNeid(swId);
				item.setPortid(portId);
				item.setPortname(portName);
				item.setPorttype(portType);

				dao.insertDomainNetworkPort(item);
			} else {
				item.setPortname(portName);
				item.setPorttype(portType);

				dao.updateDomainNetworkPort(item);
			}
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

	private void updateDomainNetworkEquipTable(String nwId, PremiseSwitch sw) {
		DomainNetworkEquip item = null;
		DomainNetworkEquipDao dao = DaoFactory.getDomainNetworkEquipDao();
		try {
			String swId = sw.getSwid();
			item = dao.selectDomainNetworkEquipById(nwId, swId);

			if (item == null) {
				item = new DomainNetworkEquip();

				item.setDnid(nwId);
				item.setNeid(swId);
				item.setNename("[" + swId + "]" + sw.getName());
				item.setNetype(sw.getName());

				dao.insertDomainNetworkEquip(item);
			} else {
				item.setNename("[" + swId + "]" + sw.getName());
				item.setNetype(sw.getName());

				dao.updateDomainNetworkEquip(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

}
