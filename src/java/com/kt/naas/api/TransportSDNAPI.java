package com.kt.naas.api;

import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
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
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.PrintUtils;

public class TransportSDNAPI extends SDNAPI {

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
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			 resTransportNW = xmlToResponse(responseXml, resTransportNW);
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
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

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
				printUtil
						.printKeyAndValue("Peer " + i + " UNIME Id", u.getId());
				printUtil.printKeyAndValue("Peer " + i + " UNIME Port",
						u.getPort());
				printUtil.printKeyAndValue("Peer " + i + " UNIME Name",
						u.getName());
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
