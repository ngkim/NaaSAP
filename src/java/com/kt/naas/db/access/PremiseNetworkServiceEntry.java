package com.kt.naas.db.access;

import java.util.List;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.domain.TenantNetworkInfo;

public class PremiseNetworkServiceEntry {
	PremiseNetworkServiceDao dao;
	
	public PremiseNetworkServiceEntry() {
		dao = DaoFactory.getPremiseNetworkServiceDao();		
	}
	
	public List<PremiseNetworkService> selectByTenantname(String svcId, String tenantName, String nwName){
		List<PremiseNetworkService> pnsList;
		
		pnsList = dao.selectPremiseNetworkServiceByTenantName(svcId, tenantName, nwName);
		
		return pnsList;
	}
	
	public void update(TenantNetworkInfo tn, String svcId, String custId) throws Exception {
		String tmpSvcId = "svc-test";
		
		updateById(tn, tmpSvcId, svcId, custId);
	}
	
	private void updateById(TenantNetworkInfo tn, String oldSvcId, String newSvcId, String custId) throws Exception {
		List<PremiseNetworkService> serviceList;
		PremiseNetworkService item, item1;
		
		System.err.println("CUSTID= " + custId + " SVCID= " + oldSvcId + ", TENANT= " + tn.getTenantName() + ", NW= " + tn.getNwName());
		
		if( custId.trim().equals("FromApp") ) {
			oldSvcId = "svc-tp";
			serviceList = dao.selectPremiseNetworkServiceByTenantName(oldSvcId, tn.getTenantName(), tn.getNwName());
			if (serviceList != null) {
				for ( int i = 0; i < serviceList.size(); i++ ) {
					item = serviceList.get(i);
					if (item != null) {
						item.setSvcid(newSvcId);
						dao.insertPremiseNetworkService(item);
						break;
					} else {
						throw new NullPointerException("요청하신 Enterprise 사이트 정보 조회에 실패하였습니다. (" + oldSvcId + ")");
					}
				}
			}			
		} else {
			serviceList = dao.selectPremiseNetworkServiceByTenantName(oldSvcId, tn.getTenantName(), tn.getNwName());
			if (serviceList != null) {
				for ( int i = 0; i < serviceList.size(); i++ ) {
					item = serviceList.get(i);
					if (item != null) {
						System.out.println("요청하신 Enterprise 사이트 정보 조회에 성공하였습니다. (SVC ID= " + item.getSvcid() + ")");
					
						item1 = item;
						dao.deletePremiseNetworkServiceById(item.getCpsvcid(), item.getConnid(), item.getSvcid());
					
						item1.setSvcid(newSvcId);
						dao.insertPremiseNetworkService(item1);
					
						System.out.println("요청하신 Enterprise 사이트 정보를 업데이트하였습니다. (SVC ID= " + item1.getSvcid() + ")");
						break;
					} else {
						throw new NullPointerException("요청하신 Enterprise 사이트 정보 조회에 실패하였습니다. (" + oldSvcId + ")");
					}
				}
			}			
		}
		
	}

}
