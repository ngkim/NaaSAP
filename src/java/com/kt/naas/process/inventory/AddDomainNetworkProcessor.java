package com.kt.naas.process.inventory;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.DomainNetworkDao;
import com.kt.naas.domain.DomainNetwork;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.process.RequestProcessor;

public class AddDomainNetworkProcessor extends RequestProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	@Override
	public void processRequest() {
		FieldBuffer inBuf = request.getFieldBuffer();
		
		String dnid = "";
		String dntype = "";
		String rootyn = "";
		String conntype = "";

		// dao
		DomainNetworkDao networkDao = DaoFactory.getDomainNetworkDao();

		// TODO Auto-generated method stub
		try {
			dnid = inBuf.getString("DNID");
			dntype = inBuf.getString("DNTYPE");
			rootyn = inBuf.getString("ROOTYN");
			conntype = inBuf.getString("CONNTYPE");
			
			DomainNetwork temp = null;
			try {
				temp = networkDao.selectDomainNetworkById(dnid);
			} catch (Exception e) {
			}
			
			//RestTemplate restTemplate = context.getBean("restTemplate", RestTemplate.class);
			
			if (temp != null) {
				logger.info("Found an item with " + temp.getDnid());
			}
			
			DomainNetwork network = new DomainNetwork();
			
			network.setDnid(dnid);
			network.setDntype(dntype);
			network.setRootyn(rootyn);
			network.setConntype(conntype);
						
			if (temp == null)
				networkDao.insertDomainNetwork(network);
			else {				
				networkDao.updateDomainNetwork(network);
			}

		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}
	}

}
