package com.kt.naas.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.api.PremiseSDNAPI;
import com.kt.naas.api.WMPremiseSDNAPI;
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
import com.kt.naas.util.DebugUtils;
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

	@Autowired
	RequestClient requestClient;

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

	@Override
	public void processRequest() {
		ResponsePremiseNWList res = null;
		try {
			PremiseSDNAPI api = new PremiseSDNAPI(request, response, GlobalConstants.URL_PREMISE_SDN_API_DJ);
			RequestPremiseNWList req = api.recvRequestfromWeb();
			
			res = api.readNetwork(req);
			
			if (res == null) {
				DebugUtils.sendResponse(response, -1, "Error! No NW List...");				
			} else {
				if( GlobalConstants.OP_DEBUG ) api.printResponsePremiseNetwork(res);
				
				// send response to web
				api.sendResponseToWeb(res);	
			}
			
			// TODO: network_service_premise에 정보를 입력
			updateDomainNetworkTable(res);
			updateDomainNetworkTables(res);
			updatePremiseNetworkServiceTable(res);

			api.sendResponseToWeb(res);
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
