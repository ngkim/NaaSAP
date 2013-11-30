package com.kt.naas.process;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.api.PremiseSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.api.WMPremiseSDNAPI;
import com.kt.naas.db.access.DCNetworkServiceEntry;
import com.kt.naas.db.access.NetworkServiceEntry;
import com.kt.naas.db.access.PremiseNetworkServiceEntry;
import com.kt.naas.db.access.ProgressStatusEntry;
import com.kt.naas.db.access.TransportNetworkServiceEntry;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.domain.NetworkServiceRequest;
import com.kt.naas.domain.TenantNetworkInfo;
import com.kt.naas.util.DebugUtils;
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

	// Progress table entry
	ProgressStatusEntry progress;

	private boolean createTransportNetwork(String svcId, String transportSvcId,
			NetworkServiceRequest svcReq) {
		boolean result = false;

		try {
			if (GlobalConstants.OP_DEBUG)
				System.out.println("*** Request create Transport Network");

			TransportSDNAPI api = new TransportSDNAPI(request, response, GlobalConstants.URL_TRANSPORT_SDN_API);
			RequestCreateTransportNetwork reqTransNW = api.generateRequest(svcReq);

			progress.update(svcReq.getCustId(), "Transport SDN에 Network 생성을 요청하였습니다.");
			ResponseCreateTransportNetwork resTransNW = api.createNetwork(reqTransNW);
			
			TransportNetworkServiceEntry transNsEntry = new TransportNetworkServiceEntry();
			transNsEntry.insert(svcId, transportSvcId, resTransNW);

			progress.update(svcReq.getCustId(), "Transport SDN에 요청한 Network가 생성되었습니다. (" + resTransNW.getId() + ")");

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.getMessage());
			result = false;
		}

		return result;
	}

	private boolean createCloudNetwork(String svcId,
			NetworkServiceRequest svcReq, TenantNetworkInfo tn) {
		boolean result = false;

		try {
			if (GlobalConstants.OP_DEBUG)
				System.out.println("*** Request create Cloud Network");

			CloudSDNAPI api = new CloudSDNAPI(request, response,
					GlobalConstants.URL_CLOUD_SDN_API);

			RequestCreateCloudNetwork req = new RequestCreateCloudNetwork();
			req.setTid("b999ba92afa2456287f7fd1a8b07e755"); // TODO: To be
															// replace to tenant
															// Id
			req.setVnid("e3c4d6dc-0443-402f-8390-a238cdb5e512"); // TODO: vnid
			req.setBw(Integer.toString(svcReq.getBandwidth()));

			progress.update(svcReq.getCustId(),
					"Cloud SDN에 Network 생성을 요청하였습니다.");

			ResponseCreateCloudNetwork nwRes = api.createNetwork(req);
			if (nwRes != null)
				progress.update(svcReq.getCustId(),
						"Cloud SDN에 요청한 Network가 생성되었습니다.");
			else
				progress.update(svcReq.getCustId(),
						"Cloud SDN에 요청한 Network 생성에 실패하였습니다.");

			if (GlobalConstants.OP_DEBUG)
				api.printResponseCreateCloudNetwork(nwRes);

			// Insert into tables using nwRes
			DCNetworkServiceEntry dcNsEntry = new DCNetworkServiceEntry();
			dcNsEntry.insert(tn, svcId, nwRes);

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.getMessage());
			result = false;
		}

		return result;
	}
	
	// return whether to call Transport SDN API or Not
	// if callOrNot == false, it will return false;
	private boolean isFromApp(String custId) {
		return custId.trim().equals("FromApp"); 		
	}

	@Override
	public void processRequest() {
		boolean result = true;

		// generate a new id
		String svcId = UUIDUtil.generateUUID();
		String transportSvcId = UUIDUtil.generateUUID();

		// Receive request from WAS and Send it to API Server
		NetworkServiceRequest svcReq = null;

		try {
			svcReq = recvRequestfromWeb();
			progress = new ProgressStatusEntry(totalCnt);

			NetworkServiceEntry nsEntry = new NetworkServiceEntry();
			nsEntry.insert(svcId, svcReq);

			// Notify current progress to Web
			progress.update(svcReq.getCustId(), "네트워크 생성 요청을 준비 중입니다.");

			ArrayList<TenantNetworkInfo> tnList = svcReq.getNetworklist();
			if (GlobalConstants.OP_DEBUG)
				System.out.println("tnList.size" + tnList.size());

			for (int i = 0; i < tnList.size(); i++) {
				TenantNetworkInfo tn = tnList.get(i);

				if (tn.getDcId() != null) {
					// Create Cloud Network
					createCloudNetwork(svcId, svcReq, tn);
				} else {
					// Create Premise Network
					createPremiseNetwork(svcId, svcReq, tn);
				}
			}

			if(!isFromApp(svcReq.getCustId())){
				result = createTransportNetwork(svcId, transportSvcId, svcReq);
			}

			if (result )	progress.update(svcReq.getCustId(), "요청한 Network 생성이 완료 되었습니다.");
			else	progress.update(svcReq.getCustId(), "요청한 Network 생성에 실패하였습니다.");

		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.getMessage());
		}
	}

	private boolean createPremiseNetwork(String svcId,
			NetworkServiceRequest svcReq, TenantNetworkInfo tn) {
		boolean result = false;
		
		if (GlobalConstants.OP_DEBUG)
			System.out.println("*** Request create Premise Network");

		PremiseNetworkServiceEntry prNsEntry = new PremiseNetworkServiceEntry();
		try {
			prNsEntry.update(tn, svcId);

			progress.update(svcReq.getCustId(),
					"Premise SDN에 Network 생성을 요청하였습니다.");

			PremiseSDNAPI api = null;
			if (tn.getTenantName().trim().equals("농협_전민지사")) {
				api = new DJPremiseSDNAPI(request, response,
					GlobalConstants.URL_PREMISE_SDN_API_DJ);
			} else if (tn.getTenantName().trim().equals("농협_우면지사")) {
				api = new WMPremiseSDNAPI(request, response,
						GlobalConstants.URL_PREMISE_SDN_API_WM);
			}

			RequestCreatePremiseNetwork req = new RequestCreatePremiseNetwork();

			req.setNetworkname(tn.getNwName());
			req.setTenantName(tn.getTenantName());
			req.setBandwidth(svcReq.getBandwidth());
			req.setCpSvcId(tn.getTenantName()); // TODO: need to updated...

			ResponseCreatePremiseNetwork nwRes = api.createNetwork(req);
			
			progress.update(
					svcReq.getCustId(),
					"Premise SDN에 요청한 Network가 생성되었습니다. ("
							+ nwRes.getCpsvcid() + ")");
			
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.getMessage());
			result = false;
		}
		
		return result;
	}

	private NetworkServiceRequest recvRequestfromWeb() {
		NetworkServiceRequest nsReq = null;
		
		try {
			nsReq = new NetworkServiceRequest();

			FieldBuffer inBuf = request.getFieldBuffer();

			// if custId is FromApp, no transport request, dj vlan swap only, no cloud vlan swap
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
			
			if (!isFromApp(custId)) totalCnt = 4; // prepare + transport create + transport delete + finish
			else totalCnt = 2;

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
