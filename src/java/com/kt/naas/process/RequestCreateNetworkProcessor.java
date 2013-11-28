package com.kt.naas.process;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.PremiseSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.db.access.DCNetworkServiceEntry;
import com.kt.naas.db.access.NetworkServiceEntry;
import com.kt.naas.db.access.PremiseNetworkServiceEntry;
import com.kt.naas.db.access.ProgressStatusEntry;
import com.kt.naas.db.access.TransportNetworkServiceEntry;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.domain.NetworkServiceRequest;
import com.kt.naas.domain.TenantNetworkInfo;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.UUIDUtil;
import com.kt.naas.xml.RequestCreateCloudNetwork;
import com.kt.naas.xml.RequestCreatePremiseNetwork;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestInfoCloudSDNConnnection;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;

public class RequestCreateNetworkProcessor extends RequestProcessor {
	private int totalCnt;

	@Autowired
	RequestClient requestClient;

	@Override
	public void processRequest() {
		// generate a new id
		String svcId = UUIDUtil.generateUUID();
		String transportSvcId = UUIDUtil.generateUUID();
		
		// Receive request from WAS and Send it to API Server
		NetworkServiceRequest svcReq = null;
		ProgressStatusEntry progress = null;
		try {
			svcReq = recvRequestfromWeb();
			progress = new ProgressStatusEntry(totalCnt);
			
			NetworkServiceEntry nsEntry = new NetworkServiceEntry();
			nsEntry.insert(svcId, svcReq);

			// Notify current progress to Web
			progress.update(svcReq.getCustId(), "네트워크 생성 요청을 준비 중입니다.");

			ArrayList<TenantNetworkInfo> tnList = svcReq.getNetworklist();
			if(GlobalConstants.OP_DEBUG)
				System.out.println("tnList.size" + tnList.size());
			
			for (int i = 0; i < tnList.size(); i++) {
				TenantNetworkInfo tn = tnList.get(i);

				// Request Create Cloud Network
				if (tn.getDcId() != null) {
					if(GlobalConstants.OP_DEBUG)
						System.out.println("*** Request create Cloud Network");
					
					CloudSDNAPI api = new CloudSDNAPI(request, response, GlobalConstants.URL_CLOUD_SDN_API);

					RequestCreateCloudNetwork req = new RequestCreateCloudNetwork();
					req.setTid("b999ba92afa2456287f7fd1a8b07e755");
					req.setVnid("e3c4d6dc-0443-402f-8390-a238cdb5e512");
					req.setBw(Integer.toString(svcReq.getBandwidth()));
					
					progress.update(svcReq.getCustId(), "Cloud SDN에 Network 생성을 요청하였습니다.");					
					ResponseCreateCloudNetwork nwRes = api.createNetwork(req);					
					progress.update(svcReq.getCustId(), "Cloud SDN에 요청한 Network가 생성되었습니다.");
					
					if(GlobalConstants.OP_DEBUG)
						api.printResponseCreateCloudNetwork(nwRes);
					
					// Insert into tables using nwRes
					DCNetworkServiceEntry dcNsEntry = new DCNetworkServiceEntry();
					dcNsEntry.insert(tn, svcId, nwRes);
					
				} else {
					if(GlobalConstants.OP_DEBUG)
						System.out.println("*** Request create Premise Network");

					PremiseNetworkServiceEntry prNsEntry = new PremiseNetworkServiceEntry();
					prNsEntry.update(tn, svcId);
					
					progress.update(svcReq.getCustId(), "Premise SDN에 Network 생성을 요청하였습니다.");
					
					PremiseSDNAPI api = new PremiseSDNAPI(request, response, GlobalConstants.URL_PREMISE_SDN_API_DJ);
					
					RequestCreatePremiseNetwork req = new RequestCreatePremiseNetwork();
					
					req.setNetworkname(tn.getNwName());
					req.setTenantName(tn.getTenantName());
					req.setBandwidth(svcReq.getBandwidth());

					ResponseCreatePremiseNetwork nwRes = api.createNetwork(req);
					progress.update(svcReq.getCustId(), "Premise SDN에 요청한 Network가 생성되었습니다. (" + nwRes.getCpsvcid() + ")");
				}
			}

			if(GlobalConstants.OP_DEBUG)
				System.out.println("*** Request create Transport Network");
		
			TransportSDNAPI api = new TransportSDNAPI(request, response, GlobalConstants.URL_TRANSPORT_SDN_API);
			RequestCreateTransportNetwork reqTransNW = api.generateRequest();
			
			TransportNetworkServiceEntry transNsEntry = new TransportNetworkServiceEntry(); 
			transNsEntry.insert(svcId, transportSvcId);
			
			progress.update(svcReq.getCustId(), "Transport SDN에 Network 생성을 요청하였습니다.");
			ResponseCreateTransportNetwork resTransNW = api.createNetwork(reqTransNW);
			progress.update(svcReq.getCustId(), "Transport SDN에 요청한 Network가 생성되었습니다. (" + resTransNW.getId() +")");

			progress.update(svcReq.getCustId(), "요청한 Network 생성이 완료 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}
	
	private NetworkServiceRequest recvRequestfromWeb() {
		NetworkServiceRequest nsReq = null;
		totalCnt = 4; // prepare + transport create + transport delete + finish
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
				totalCnt += 2; // add 2 for each item
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
