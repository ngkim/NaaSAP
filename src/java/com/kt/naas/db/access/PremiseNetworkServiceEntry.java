package com.kt.naas.db.access;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.domain.TenantNetworkInfo;

public class PremiseNetworkServiceEntry {
	PremiseNetworkServiceDao dao;
	
	public PremiseNetworkServiceEntry() {
		dao = DaoFactory.getPremiseNetworkServiceDao();		
	}
	
	public void update(TenantNetworkInfo tn, String svcId) throws Exception {
		String tmpSvcId = "svc-test";
		
		if(tn.getNwName().trim().startsWith("농협_전민지사")) {
			updateById("PSDN000001", "0", tmpSvcId, svcId);
			updateById("PSDN000001", "1", tmpSvcId, svcId);
		} else if(tn.getNwName().trim().startsWith("농협_우면지사")) {
			updateById("PSDN000002", "0", tmpSvcId, svcId);
			updateById("PSDN000002", "1", tmpSvcId, svcId);
			updateById("PSDN000002", "2", tmpSvcId, svcId);
		}
	}
	
	private void updateById(String cpSvcId, String connId, String oldSvcId, String newSvcId) throws Exception {
		PremiseNetworkService item, item1;
		
		item = dao.selectPremiseNetworkServiceById(cpSvcId, connId, oldSvcId);
		if (item == null) {
			throw new NullPointerException("요청하신 Enterprise 사이트 정보 조회에 실패하였습니다.");
		} else {
			System.out.println("요청하신 Enterprise 사이트 정보 조회에 성공하였습니다. (SVC ID= " + item.getSvcid() + ")");
			
			item1 = item;
			dao.deletePremiseNetworkServiceById(cpSvcId, connId, oldSvcId);
			
			if (cpSvcId.trim().equals("PSDN000001")) {
				item1.setAddr("(전민지사) 대전광역시 유성구 전민동 463-1");
			} else if  (cpSvcId.trim().equals("PSDN000002")) {
				item1.setAddr("(우면지사) 서울시 서초구 우면동 17");
			}
			
			item1.setSvcid(newSvcId);
			dao.insertPremiseNetworkService(item1);
			
			System.out.println("요청하신 Enterprise 사이트 정보를 업데이트하였습니다. (SVC ID= " + item1.getSvcid() + ")");
		}
	}

}
