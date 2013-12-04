package com.kt.naas.db.access;

import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.TransportNetworkServiceDao;
import com.kt.naas.domain.TransportNetworkService;
import com.kt.naas.util.BWUtils;
import com.kt.naas.xml.QoS;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;

public class TransportNetworkServiceEntry {
	TransportNetworkServiceDao networkDao;
	TransportSDNAPI api;

	public TransportNetworkServiceEntry() {
		networkDao = DaoFactory.getTransportNetworkServiceDao();
		api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API);
	}

	/* 
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	 * <Ethernet name="naasEth" id="NS_20061" description="DJ POTN connection test">
	 * <cid>88888888880</cid>
	 * <QoS exceed="0" bandwidth="100M"/>
	 * <rid>NaaS</rid>
	 * <status>OK</status>
	 * <UNIPeer vlan="200" port="24" id="L2SW00001">
	 * 		<UNIME port="1-3-5" name="Z77-A" id="NE0000035"/>
	 * </UNIPeer>
	 * <UNIPeer vlan="200" port="22" id="L2SW00011">
	 * 		<UNIME port="1-4-5" name="Z77-B" id="NE0000036"/>
	 * </UNIPeer>
	 * <eType>E-LINE</eType>
	 * </Ethernet>
	 */
	public void insert(String svcId, ResponseCreateTransportNetwork res) {
		String strBw = "", strExceed = "";
				
		QoS qos = res.getQos();
		if (qos != null) {
			strBw = qos.getBandwidth();
			strExceed = qos.getExceed();
		}
		
		ArrayList<UNIPeer> peers = res.getPeers();
		if (peers != null) {
			for (int i = 0; i < peers.size(); i++) {
				UNIPeer peer = peers.get(i);
				
				TransportNetworkService tnSvc = new TransportNetworkService();
				
				tnSvc.setStatus(0);
				tnSvc.setResultmsg(res.getStatus());

				tnSvc.setSvcid(svcId);
				tnSvc.setTransvcid(res.getId());
				tnSvc.setNwname("kt Transport Network (" + res.getName()+")");
				
				if (!strBw.trim().equals("")) {
					tnSvc.setQosofcir(strBw);
					tnSvc.setQosofeir(strExceed);
					tnSvc.setEquipbw(BWUtils.bwToInt(strBw)); // 100M
				}
				
				tnSvc.setAssociatedswid(api.getAssociatedSWID(peer.getId()));
				tnSvc.setAssociatedswtype(api.getSwitchType(peer.getId()));
				tnSvc.setEquipoutboundportid(peer.getPort());
				tnSvc.setEquipvlanid(peer.getVlan());
				
				UNIME u = peer.getU();
				if ( u != null ) {
					tnSvc.setEquipid(u.getId());
					tnSvc.setEquipname(u.getName());
					tnSvc.setEquipinboundportid(u.getPort());
				}
				
				// Fixed Value
				tnSvc.setEthtype("DE");
				tnSvc.setConntype("ETH");
				
				networkDao.insertTransportNetworkService(tnSvc);
				System.out.println(svcId + "TransportSDN - Add new entry - "
						+ tnSvc.getNwname() + "/" + tnSvc.getAssociatedswid());
			}
		}
	}

}
