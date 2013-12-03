package com.kt.naas.db.access;

import java.util.ArrayList;

import com.kt.naas.db.DCNetworkServiceDao;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.domain.DCNetworkService;
import com.kt.naas.domain.TenantNetworkInfo;
import com.kt.naas.util.BWUtils;
import com.kt.naas.xml.CloudConnectionList;
import com.kt.naas.xml.CloudSwitch;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.ResponseCreateCloudNetwork;

public class DCNetworkServiceEntry {
	DCNetworkServiceDao dao = null;
	boolean isFromApp;

	public DCNetworkServiceEntry() {
		dao = DaoFactory.getDCNetworkServiceDao();
		
		this.isFromApp = false;
	}
	
	public DCNetworkServiceEntry(boolean isFromApp) {
		dao = DaoFactory.getDCNetworkServiceDao();
		
		this.isFromApp = isFromApp;
	}

	public void insert(TenantNetworkInfo tn, String svcId,
			ResponseCreateCloudNetwork nwRes) {
		DCNetworkService item = new DCNetworkService();

		item.setDcsvcid(nwRes.getDcsvcid());

		item.setTenantid(nwRes.getTenantid());
		item.setTenantname(nwRes.getTenantname());

		item.setTransequipid("T0001");
		item.setTransequipportid("2");

		ArrayList<CloudVNID> cvList = nwRes.getVNIDInfo();
		if (cvList != null) {
			for (int i = 0; i < cvList.size(); i++) {
				CloudVNID cv = cvList.get(i);

				item.setNwname(cv.getName());
				item.setConnid(Integer.toString(i));
				item.setSvcid(svcId);

				item.setSubnet(cv.getSubnet());

				CloudConnectionList ccl = cv.getConnectionList();
				ArrayList<CloudSwitch> cswList = ccl.getConnectionInfo();
				if (cswList != null) {
					for (int j = 0; j < cswList.size(); j++) {
						CloudSwitch sw = cswList.get(j);

						if (sw.getType().trim().startsWith("Aggregate")) {
							item.setL2id(sw.getDpid());
							item.setL2name(sw.getName());
							item.setL2ip("10.1.1.55");
							item.setL2upportid(sw.getUplinkport());
							item.setL2downportid(sw.getDownlinkport());
							item.setL2bw(BWUtils.bwToInt(cv.getBandwidth()));
							if ( isFromApp ) item.setL2vlanid("11"); // Use Vlan 11 for TP demo
							else item.setL2vlanid(cv.getVNID()); // Set
						} else if (sw.getType().trim().startsWith("ToR")) {
							item.setTorid(sw.getDpid());
							item.setTorname(sw.getName());
							item.setTorip("10.1.1.56");
							item.setTorupportid(sw.getUplinkport());
							item.setTordownportid(sw.getDownlinkport());
							item.setTorbw(BWUtils.bwToInt((cv.getBandwidth())));
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

	public DCNetworkService selectByTenantname(String svcId, String tenantName) {
		DCNetworkService service = dao.selectDCNetworkServiceByTenant(svcId, tenantName);
		return service;
	}
}
