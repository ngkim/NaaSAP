package com.kt.naas.db.access;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.TransportNetworkServiceDao;
import com.kt.naas.domain.TransportNetworkService;

public class TransportNetworkServiceEntry {
	TransportNetworkServiceDao networkDao;

	public TransportNetworkServiceEntry() {
		networkDao = DaoFactory.getTransportNetworkServiceDao();
	}
	
	private TransportNetworkService generateSvc1(String svcId, String transportSvcId) {
		TransportNetworkService item_pr1 = new TransportNetworkService();
		
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
		
		return item_pr1;
	}
	
	private TransportNetworkService generateSvc2(String svcId, String transportSvcId) {
		TransportNetworkService item_pr2 = new TransportNetworkService();
		
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
		
		return item_pr2;
	}
	
	private TransportNetworkService generateSvcDc(String svcId, String transportSvcId) {
		TransportNetworkService item_dc = new TransportNetworkService();
		
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
		
		return item_dc;
	}

	public void insert(String svcId, String transportSvcId) {
		TransportNetworkService item_pr1, item_pr2, item_dc;

		item_pr1 = generateSvc1(svcId, transportSvcId);
		networkDao.insertTransportNetworkService(item_pr1);
		System.out.println(svcId + "TransportSDN - Add new entry - "
				+ item_pr1.getNwname() + "/" + item_pr1.getAssociatedswid());

		item_pr2 = generateSvc2(svcId, transportSvcId);
		networkDao.insertTransportNetworkService(item_pr2);
		System.out.println(svcId + "TransportSDN - Add new entry - "
				+ item_pr2.getNwname() + "/" + item_pr2.getAssociatedswid());


		item_dc = generateSvcDc(svcId, transportSvcId);
		networkDao.insertTransportNetworkService(item_dc);
		System.out.println(svcId + "TransportSDN - Add new entry - "
				+ item_dc.getNwname() + "/" + item_dc.getAssociatedswid());
	}

}
