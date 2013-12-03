package com.kt.smnw.process.admin;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.db.CodeDao;
import com.kt.naas.db.DCNetworkServiceDao;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.NetworkServiceDao;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.db.TransportNetworkServiceDao;
import com.kt.naas.domain.Code;
import com.kt.naas.domain.NetworkService;
import com.kt.naas.domain.TransportNetworkService;
import com.kt.naas.util.CodeUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.util.admin.ServiceUtils;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ResetConfiguration {
	@Autowired
	private RequestClient requestClient;
	
	public ResetConfiguration() {

	}
	
	private RequestDeleteTransportNetwork generateRequest(String transportSvcId) {
		RequestDeleteTransportNetwork req = new RequestDeleteTransportNetwork();

		req.setName("naasEth");
		req.setDescription("DJ POTN connection test");

		req.setRid("NaaS");
		req.setCid("88888888880");
		req.setEid(transportSvcId);

		return req;
	}

	public void deleteTransportNetwork(String transportSvcId) {
		ResponseDeleteTransportNetwork res = null;
		TimeUtils time = new TimeUtils();

		try {
			time.setStartTime();

			RequestDeleteTransportNetwork req = generateRequest(transportSvcId);
			TransportSDNAPI api = new TransportSDNAPI(
					GlobalConstants.URL_TRANSPORT_SDN_API);
			res = api.deleteNetwork(req);

			double duration = time.getDuration() / 1000;

			System.err.println("Time for deleting a transport network = "
					+ duration);

			System.out.println("Delete Result=" + res.getStatus().getResult());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearEntries(String svcId) {
		try {
			System.out.println("*** 4-1. DC Network Service entries...");
			// clear database entries with svcId
			DCNetworkServiceDao dcDao = DaoFactory.getDCNetworkServiceDao();
			dcDao.deleteDCNetworkServiceBySvcId(svcId);

			System.out.println("*** 4-2. PR Network Service entries...");
			PremiseNetworkServiceDao prDao = DaoFactory
					.getPremiseNetworkServiceDao();
			prDao.deletePremiseNetworkServiceBySvcId(svcId);

			System.out.println("*** 4-3. Transport Network Service entries...");
			TransportNetworkServiceDao tDao = DaoFactory
					.getTransportNetworkServiceDao();
			tDao.deleteTransportNetworkServiceBySvcId(svcId);

			System.out.println("*** 4-4. Network Service entries...");
			NetworkServiceDao dao = DaoFactory.getNetworkServiceDao();
			dao.deleteNetworkServiceById(svcId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext 	context = new ClassPathXmlApplicationContext("test-context.xml");
		getAllCodes(context);
		System.out.println("==========================");
		System.out.println("DOCU=" + CodeUtils.getCodeValue("GENREID", "DOCU"));
		
		resetConfigurations();
	}
	
	public static void getAllCodes(ApplicationContext context)
	{
		CodeDao dao = (CodeDao)context.getBean("codeDao");
		List<Code> codes = dao.getAllCodes();
		
		System.out.println(codes.size() + " items selected.");
		for (Code code : codes)
		{
			System.out.println(code);
		}
	}

	public static void resetConfigurations() {
		// TODO Auto-generated method stub
		ResetConfiguration reset = new ResetConfiguration();
		ServiceUtils database = new ServiceUtils();

		System.out.println("*** 1. Retrieve Service ID...");

		String svcId = database.getSvcId();
		
		// back to internet
		System.out.println("*** 3. Go back to internet...");
		DJPremiseSDNAPI api = new DJPremiseSDNAPI(
				GlobalConstants.URL_PREMISE_SDN_API_DJ);
		boolean result = api.swapNetworkToInternet();
		if (result) {
			System.out
					.println("*** 3-1. Successfully come back to internet...");
		} else {
			System.err.println("*** 3-2. Failed to go back to internet...");
		}

		System.out.println("*** 4. Clear service entries...");
		reset.clearEntries(svcId);
		System.out
				.println("*** 4-5. Successfully cleared all service entries...");
		
		System.out.println("Done...");
		

	}

}
