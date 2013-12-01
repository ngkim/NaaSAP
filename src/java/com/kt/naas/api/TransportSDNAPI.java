package com.kt.naas.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.kt.naas.GlobalConstants;
import com.kt.naas.db.access.PremiseNetworkServiceEntry;
import com.kt.naas.domain.NetworkServiceRequest;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.domain.TenantNetworkInfo;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.xml.QoS;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;
import com.kt.naas.xml.ResponseInfoEthernet;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;
import com.kt.naas.util.BWUtils;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.PrintUtils;

public class TransportSDNAPI extends SDNAPI {

	class SwitchPortPair {
		String swId;
		String swPort;
		String vlan;

		public SwitchPortPair(String swId, String swPort) {
			this.swId = swId;
			this.swPort = swPort;
		}

		public SwitchPortPair(String swId, String swPort, String vlan) {
			this.swId = swId;
			this.swPort = swPort;
			this.vlan = vlan;
		}

		public String getSwId() {
			return swId;
		}

		public void setSwId(String swId) {
			this.swId = swId;
		}

		public String getSwPort() {
			return swPort;
		}

		public void setSwPort(String swPort) {
			this.swPort = swPort;
		}

		public String getVlan() {
			return vlan;
		}

		public void setVlan(String vlan) {
			this.vlan = vlan;
		}
	}

	public TransportSDNAPI(String url) {
		super(url);

		this.setUrlCreate("/createEthernet");
		this.setUrlRead("/infoEthernet");
		this.setUrlDelete("/deleteEthernet");
	}

	public TransportSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
		// TODO Auto-generated constructor stub

		this.setUrlCreate("/createEthernet");
		this.setUrlRead("/infoEthernet");
		this.setUrlDelete("/deleteEthernet");
	}

	public ResponseCreateTransportNetwork createNetwork(
			RequestCreateTransportNetwork req) {
		ResponseCreateTransportNetwork resTransportNW = new ResponseCreateTransportNetwork();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLCreate", getUrlCreate());

			String responseXml = apiUtil.requestToAPIServerHttps(
					getUrlCreate(), getRequestXML(req));

			if (responseXml == null || responseXml.trim().equals("")) {
				DebugUtils.sendResponse(response, -1,
						"No response from Transport SDN API Server.("
								+ getUrlCreate() + ")");
			} else {
				resTransportNW = xmlToResponse(responseXml, resTransportNW);
			}
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resTransportNW;
	}

	public ResponseInfoEthernet readNetwork(RequestInfoEthernet req) {
		ResponseInfoEthernet resInfoEthernet = new ResponseInfoEthernet();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLRead", getUrlRead());

			String responseXml = apiUtil.requestToAPIServerHttps(getUrlRead(),
					getRequestXML(req));

			resInfoEthernet = xmlToResponse(responseXml, resInfoEthernet);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resInfoEthernet;
	}

	public ResponseDeleteTransportNetwork deleteNetwork(
			RequestDeleteTransportNetwork req) {
		ResponseDeleteTransportNetwork resTransportNW = new ResponseDeleteTransportNetwork();

		try {
			String responseXml = apiUtil.requestToAPIServerHttps(
					getUrlDelete(), getRequestXML(req));
			resTransportNW = xmlToResponse(responseXml, resTransportNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resTransportNW;
	}

	// generate NetworkServiceRequest from request info received from portal
	public RequestCreateTransportNetwork generateRequest(String svcId,
			NetworkServiceRequest svcReq) {
		RequestCreateTransportNetwork req = new RequestCreateTransportNetwork();

		Random random = new Random();
		int ethId = random.nextInt(10000);
		req.setName("naasEth-" + ethId);
		req.setDescription("POTN connection " + ethId);

		req.setRid("NaaS");
		req.setCid("88888888880");
		req.seteType(svcReq.getTopologyType());

		ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();

		ArrayList<TenantNetworkInfo> tnList = svcReq.getNetworklist();
		if (tnList != null) {
			for (int i = 0; i < tnList.size(); i++) {
				TenantNetworkInfo tn = tnList.get(i);

				// TODO: to be removed
				if (tn.getNwType().trim().equals("DC"))
					continue;

				SwitchPortPair spv = getSwitchPortVlan(svcId, tn);

				if (spv != null)
					System.err.println("swid= " + spv.getSwId());
				else
					System.err.println("No result for " + svcId + ", "
							+ tn.getTenantName() + ", " + tn.getNwName());

				UNIPeer sw_potn = new UNIPeer();
				sw_potn.setId(getNEID(spv.getSwId()));
				sw_potn.setPort(spv.getSwPort());
				sw_potn.setVlan(spv.getVlan());

				peers.add(sw_potn);

				if (GlobalConstants.OP_DEBUG) {
					System.out
							.println("[TransportSDNAPI-generateRequest] TENANT= "
									+ tn.getTenantName()
									+ ", UNIPeer= "
									+ sw_potn.getId());
				}
			}
		}
		req.setPeers(peers);

		QoS qos = new QoS();
		qos.setBandwidth(BWUtils.bwToStr(svcReq.getBandwidth()));
		qos.setExceed("0");

		req.setQos(qos);

		return req;
	}

	// return associated NEID of a switch
	private String getNEID(String swId) {
		HashMap<String, String> NEIDs = new HashMap<String, String>();

		NEIDs.put("dj_ib_aggr_1", "L2SW00011");
		NEIDs.put("wm_cl_aggr_1", "L2SW00013");
		NEIDs.put("wm_ib_aggr_1", "L2SW00003");

		return NEIDs.get(swId);
	}

	// return associated NEID of a switch
	public String getAssociatedSWID(String swId) {
		HashMap<String, String> NEIDs = new HashMap<String, String>();

		NEIDs.put("L2SW00011", "dj_ib_aggr_1");
		NEIDs.put("L2SW00013", "wm_cl_aggr_1");
		NEIDs.put("L2SW00003", "wm_ib_aggr_1");

		return NEIDs.get(swId);
	}

	// retrieve aggregate swid, upport, and vlan id of a tenant with a svcId
	public SwitchPortPair getSwitchPortVlan(String svcId, TenantNetworkInfo tn) {
		SwitchPortPair spv = null;

		if (tn.getNwType().trim().equals("PR")) {
			PremiseNetworkServiceEntry pnsEntry = new PremiseNetworkServiceEntry();

			PremiseNetworkService pns = pnsEntry.selectByTenantname(svcId,
					tn.getTenantName(), tn.getNwName());
			if (pns != null) {
				spv = new SwitchPortPair(pns.getAggrid(),
						pns.getAggrupportid(), pns.getAggrvlanid());
			}
		} else if (tn.getNwType().trim().equals("DC")) {
			// TODO: need to retrieve cloud network information and return
			// SwitchPortPair for it
		}

		return spv;
	}

	// get available vlan id
	public String getAvailableVlan() {
		Random random = new Random();
		int vid = 213 + random.nextInt(1024);

		return Integer.toString(vid);
	}

	public String getSwitchType(String swId) {
		String result = "";

		List<String> swPRList = new ArrayList<String>();
		swPRList.add("L2SW00011");
		swPRList.add("L2SW00003");

		List<String> swDCList = new ArrayList<String>();
		swDCList.add("L2SW00013");

		if (swPRList.contains(swId)) {
			result = "PR";
		} else if (swDCList.contains(swId)) {
			result = "DC";
		}

		return result;
	}

	public void printRequestCreateTransportNetwork(
			RequestCreateTransportNetwork req) {
		printUtil.printKeyAndValue("Name", req.getName());
		printUtil.printKeyAndValue("Description", req.getDescription());

		printUtil.printKeyAndValue("Rid", req.getRid());
		printUtil.printKeyAndValue("Cid", req.getCid());
		printUtil.printKeyAndValue("eType", req.geteType());

		ArrayList<UNIPeer> peers = req.getPeers();
		if (peers != null) {
			for (int i = 0; i < peers.size(); i++) {
				UNIPeer peer = peers.get(i);

				printUtil.printKeyAndValue("Peer " + i + " Id", peer.getId());
				printUtil.printKeyAndValue("Peer " + i + " Port",
						peer.getPort());
				printUtil.printKeyAndValue("Peer " + i + " Vlan",
						peer.getVlan());
			}
		}

		QoS qos = req.getQos();
		if (qos != null) {
			printUtil.printKeyAndValue("Qos BW", qos.getBandwidth());
			printUtil.printKeyAndValue("Qos Exceed", qos.getExceed());
		}
	}

	public void printResponseCreateTransportNetwork(
			ResponseCreateTransportNetwork res) {
		printUtil.printKeyAndValue("ID", res.getId());
		printUtil.printKeyAndValue("Name", res.getName());
		printUtil.printKeyAndValue("Description", res.getDescription());

		printUtil.printKeyAndValue("Status", res.getStatus());
		printUtil.printKeyAndValue("Rid", res.getRid());
		printUtil.printKeyAndValue("Cid", res.getCid());
		printUtil.printKeyAndValue("eType", res.geteType());

		ArrayList<UNIPeer> peers = res.getPeers();
		if (peers != null) {
			for (int i = 0; i < peers.size(); i++) {
				UNIPeer peer = peers.get(i);

				printUtil.printKeyAndValue("Peer " + i + " Id", peer.getId());
				printUtil.printKeyAndValue("Peer " + i + " Port",
						peer.getPort());
				printUtil.printKeyAndValue("Peer " + i + " Vlan",
						peer.getVlan());

				UNIME u = peer.getU();
				if (u != null) {
					printUtil.printKeyAndValue("Peer " + i + " UNIME Id",
							u.getId());
					printUtil.printKeyAndValue("Peer " + i + " UNIME Port",
							u.getPort());
					printUtil.printKeyAndValue("Peer " + i + " UNIME Name",
							u.getName());
				}
			}
		}

		QoS qos = res.getQos();
		if (qos != null) {
			printUtil.printKeyAndValue("Qos BW", qos.getBandwidth());
			printUtil.printKeyAndValue("Qos Exceed", qos.getExceed());
		}
	}

	public void printResponseInfoEthernet(ResponseInfoEthernet res) {
		printUtil.printKeyAndValue("Name", res.getName());
		printUtil.printKeyAndValue("Description", res.getDescription());

		printUtil.printKeyAndValue("Status", res.getStatus());
		printUtil.printKeyAndValue("Rid", res.getRid());
		printUtil.printKeyAndValue("Cid", res.getCid());
		printUtil.printKeyAndValue("Eid", res.getEid());
	}

}
