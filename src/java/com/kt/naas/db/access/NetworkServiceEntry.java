package com.kt.naas.db.access;

import java.sql.Timestamp;
import java.util.Random;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.NetworkServiceDao;
import com.kt.naas.domain.NetworkService;
import com.kt.naas.domain.NetworkServiceRequest;

public class NetworkServiceEntry {
	NetworkServiceDao networkDao;

	public NetworkServiceEntry() {
		networkDao = DaoFactory.getNetworkServiceDao();
	}

	private NetworkService generateNetworkService(String svcId, NetworkServiceRequest req) throws Exception {
		NetworkService item = new NetworkService();
		String custName = "농협중앙회";

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
		
		Timestamp beginTime = Timestamp.valueOf(req.getFromTime() + ":00");
		Timestamp endTime = Timestamp.valueOf(req.getToTime() + ":00");

		item.setBeginTime(beginTime);
		item.setEndTime(endTime);
		
		return item;
	}

	// Insert received Network Service into DB
	public void insert(String svcId, NetworkServiceRequest req) throws Exception{
		NetworkService item = null;
		try {
			item = generateNetworkService(svcId, req);
			networkDao.insertNetworkService(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
