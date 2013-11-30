package com.kt.naas.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.NetworkServiceRequest;
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
		
		public SwitchPortPair(String swId, String swPort) {
			this.swId = swId;
			this.swPort = swPort;
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
			
			if (responseXml == null || responseXml.trim().equals("")){
				DebugUtils.sendResponse(response, -1, "No response from Transport SDN API Server.(" + getUrlCreate() + ")");
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
	public RequestCreateTransportNetwork generateRequest(NetworkServiceRequest svcReq) {
		RequestCreateTransportNetwork req = new RequestCreateTransportNetwork();
		
		req.setName("naasEth");
		req.setDescription("POTN connection " + (int)(Math.random() * 1000));
		
		req.setRid("NaaS");
		req.setCid("88888888880");
		req.seteType(svcReq.getConnType());
		
		ArrayList<UNIPeer> peers = new ArrayList<UNIPeer>();
		
		String vlanId = getAvailableVlan(); // check available vlan
				
		ArrayList<TenantNetworkInfo> tnList = svcReq.getNetworklist();
		if (tnList != null) {
			for ( int i = 0; i < tnList.size(); i++ ) {
				TenantNetworkInfo tn = tnList.get(i);
				
				SwitchPortPair sw = getSwitchPortPair(tn);
				
				UNIPeer sw_potn = new UNIPeer();
				sw_potn.setId(sw.getSwId());
				sw_potn.setPort(sw.getSwPort());
				sw_potn.setVlan(vlanId);
				
				peers.add(sw_potn);
			}
		}
		
		req.setPeers(peers);
		
		QoS qos = new QoS();
		
		qos.setBandwidth(BWUtils.bwToStr(svcReq.getBandwidth()));
		qos.setExceed("0");
		
		req.setQos(qos);
		
		return req;
	}
	
	
	// find switch and port information based on tenant name
	public SwitchPortPair getSwitchPortPair(TenantNetworkInfo tn) {
		HashMap<String, SwitchPortPair> tenantSwitchPair = new HashMap<String, SwitchPortPair>();
		
		SwitchPortPair spPairDJ = new SwitchPortPair("dj_ib_aggr_1", "22");
		SwitchPortPair spPairWM = new SwitchPortPair("wm_ib_aggr_1", "26");
		SwitchPortPair spPairDC = new SwitchPortPair("wm_cl_aggr_1", "1");
		
		tenantSwitchPair.put("cloudsdn", spPairDC);
		tenantSwitchPair.put("농협_전민지사", spPairDJ);
		tenantSwitchPair.put("농협_우면지사", spPairWM);
		
		return tenantSwitchPair.get(tn.getTenantName());
	}
	
	public String getAvailableVlan() {
		String vlan = "";
		
		vlan = "100";
		
		return vlan; 
	}
	
	public String getSwitchType(String swId) {
		String result = "";
		
		List<String> swPRList = new ArrayList<String>();
		swPRList.add("dj_ib_aggr_1");
		swPRList.add("wm_ib_aggr_1");
		
		List<String> swDCList = new ArrayList<String>();
		swDCList.add("wm_cl_aggr_1");
		
		if ( swPRList.contains(swId)) {
			result = "PR";
		} else if ( swDCList.contains(swId)) {
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
				if ( u != null ) {
					printUtil
							.printKeyAndValue("Peer " + i + " UNIME Id", u.getId());
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
