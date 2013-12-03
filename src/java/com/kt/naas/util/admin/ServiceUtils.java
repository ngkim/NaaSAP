package com.kt.naas.util.admin;

import java.text.SimpleDateFormat;
import java.util.List;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.db.DCNetworkServiceDao;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.NetworkServiceDao;
import com.kt.naas.db.PremiseNetworkServiceDao;
import com.kt.naas.db.TransportNetworkServiceDao;
import com.kt.naas.domain.DCNetworkService;
import com.kt.naas.domain.NetworkService;
import com.kt.naas.domain.PremiseNetworkService;
import com.kt.naas.domain.TransportNetworkService;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;

public class ServiceUtils {

	public void printPremiseSvcId(String svcId) {
		// 1. Select latest service id
		PremiseNetworkServiceDao dao = DaoFactory.getPremiseNetworkServiceDao();

		List<PremiseNetworkService> nsList = dao
				.selectPremiseNetworkServiceBySvcId(svcId);

		String premiseSvcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				PremiseNetworkService ns = nsList.get(i);
				premiseSvcId = ns.getCpsvcid();

				System.out.println("SVCID= " + svcId + " PREMISE SVC ID= "
						+ premiseSvcId);
			}
		}
	}

	public String getPremiseSvcId(String svcId) {
		// 1. Select latest service id
		PremiseNetworkServiceDao dao = DaoFactory.getPremiseNetworkServiceDao();

		List<PremiseNetworkService> nsList = dao
				.selectPremiseNetworkServiceBySvcId(svcId);

		String premiseSvcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				PremiseNetworkService ns = nsList.get(i);
				premiseSvcId = ns.getCpsvcid();

				System.out.println();
				if (premiseSvcId != null)
					break;
			}
		}
		System.err.println("SVCID= " + svcId + " PREMISE SVC ID= "
				+ premiseSvcId);

		return premiseSvcId;
	}

	public void printCloudSvcId(String svcId) {
		// 1. Select latest service id
		DCNetworkServiceDao dao = DaoFactory.getDCNetworkServiceDao();

		List<DCNetworkService> nsList = dao
				.selectDCNetworkServiceBySvcId(svcId);

		String dcSvcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				DCNetworkService ns = nsList.get(i);
				dcSvcId = ns.getDcsvcid();

				System.out
						.println("SVCID= " + svcId + " DC SVC ID= " + dcSvcId);
			}
		}

	}

	public String getCloudSvcId(String svcId) {
		// 1. Select latest service id
		DCNetworkServiceDao dao = DaoFactory.getDCNetworkServiceDao();

		List<DCNetworkService> nsList = dao
				.selectDCNetworkServiceBySvcId(svcId);

		String dcSvcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				DCNetworkService ns = nsList.get(i);
				dcSvcId = ns.getDcsvcid();

				System.out.println();
				if (dcSvcId != null)
					break;
			}
		}
		System.err.println("SVCID= " + svcId + " DC SVC ID= " + dcSvcId);

		return dcSvcId;
	}

	public void printTransportSvcId(String svcId, boolean delete) {
		// 1. Select latest service id
		TransportNetworkServiceDao dao = DaoFactory
				.getTransportNetworkServiceDao();

		List<TransportNetworkService> nsList = dao
				.selectTransportNetworkServiceBySvcId(svcId);

		String transportSvcId = null;
		if (nsList != null) {
			String oldId = "";
			for (int i = 0; i < nsList.size(); i++) {
				TransportNetworkService ns = nsList.get(i);
				transportSvcId = ns.getTransvcid();
				
				if (transportSvcId.trim().equals(oldId.trim())) break; // to avoid sending delete request twice for a transport service
				
				if( delete ) {
					System.out.print("!!! DELETE !!! SVCID= " + svcId + " TRANS SVC ID= "
							+ transportSvcId);
					deleteTransportNetwork(transportSvcId);
					deleteTransportSvcbySvcId(svcId);
				} else {
					System.out.println("SVCID= " + svcId + " TRANS SVC ID= "
						+ transportSvcId);
				}
				
				oldId = transportSvcId;
			}
		}
	}

	public String getTransportSvcId(String svcId) {
		// 1. Select latest service id
		TransportNetworkServiceDao dao = DaoFactory
				.getTransportNetworkServiceDao();

		List<TransportNetworkService> nsList = dao
				.selectTransportNetworkServiceBySvcId(svcId);

		String transportSvcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				TransportNetworkService ns = nsList.get(i);
				transportSvcId = ns.getTransvcid();

				System.out.println();
				if (transportSvcId != null)
					break;
			}
		}
		System.out.println("SVCID= " + svcId + " TRANS SVC ID= "
				+ transportSvcId);

		return transportSvcId;
	}
	
	public void listSvcDeleted(boolean delete){
		NetworkServiceDao dao = DaoFactory.getNetworkServiceDao();
		List<NetworkService> nsList = dao.selectNetworkServicesDeleted();

		String svcId = null;
		if (nsList != null) {
			System.out.println("TOTAL= " + nsList.size() + " services.\n");
			for (int i = 0; i < nsList.size(); i++) {
				NetworkService ns = nsList.get(i);
				svcId = ns.getSvcId();

				String regdate = new SimpleDateFormat("MM/dd HH:MM").format(ns.getRegdate());
				if( delete ) {
					System.out.printf("!!! DELETE !!! %s SVCNAME= %-20s SVCID= %s\n", regdate, ns.getSvcName(), svcId);
					clearService(svcId);
				} else {
					System.out.printf("%s SVCNAME= %-20s SVCID= %s\n", regdate, ns.getSvcName(), svcId);
				}
			}
		}
		
	}

	public String getSvcId() {
		// 1. Select latest service id
		NetworkServiceDao dao = DaoFactory.getNetworkServiceDao();

		if (dao == null) {
			System.err.println("Error in getting DaoFactory!!!");
			System.exit(-1);
		}

		List<NetworkService> nsList = dao.selectNetworkService();

		String svcId = null;
		if (nsList != null) {
			for (int i = 0; i < nsList.size(); i++) {
				NetworkService ns = nsList.get(i);
				svcId = ns.getSvcId();

				if (svcId != null) {
					String regdate = new SimpleDateFormat("MM/dd HH:MM").format(ns.getRegdate());
					System.out.printf("%s SVCNAME= %-20s SVCID= %s\n", regdate, ns.getSvcName(), svcId);
					break;
				}
			}
		}

		return svcId;
	}

	public void deleteDCSvcbySvcId(String svcId) {

		// clear database entries with svcId
		DCNetworkServiceDao dcDao = DaoFactory.getDCNetworkServiceDao();
		dcDao.deleteDCNetworkServiceBySvcId(svcId);
	}

	public void deletePremiseSvcbySvcId(String svcId) {

		// clear database entries with svcId
		PremiseNetworkServiceDao prDao = DaoFactory
				.getPremiseNetworkServiceDao();
		prDao.deletePremiseNetworkServiceBySvcId(svcId);
	}

	public void deleteTransportSvcbySvcId(String svcId) {

		// clear database entries with svcId
		TransportNetworkServiceDao tDao = DaoFactory
				.getTransportNetworkServiceDao();
		tDao.deleteTransportNetworkServiceBySvcId(svcId);
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
					GlobalConstants.URL_TRANSPORT_SDN_API_TEST);
			res = api.deleteNetwork(req);

			double duration = time.getDuration() / 1000;

			System.err.println("Time for deleting a transport network = "
					+ duration);

			System.out.println("Delete Result=" + res.getStatus().getResult());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteSvcbySvcId(String svcId) {
		// clear database entries with svcId
		NetworkServiceDao dao = DaoFactory.getNetworkServiceDao();
		dao.deleteNetworkServiceById(svcId);
	}
	
	public void clearService(String svcId) {
		System.out.println("\n\n*** SVC ID= " + svcId);
		System.out.println();

		boolean delete = true;
		System.out.println("=== Transport Services ===");
		printTransportSvcId(svcId, delete);
		
		System.out.println("\n=== Premise Services ===");
		printPremiseSvcId(svcId);
		if (delete) {
			deletePremiseSvcbySvcId(svcId);
		}

		System.out.println("\n=== Cloud Services ===");
		printCloudSvcId(svcId);
		if (delete) {
			deleteDCSvcbySvcId(svcId);
		}
		System.out.println();

		if (delete) {
			deleteSvcbySvcId(svcId);
		}
	}

	public void clearEntries(String svcId) {
		try {
			System.out.println("*** 1. Delete DC Network Service entries...");
			// clear database entries with svcId
			deleteDCSvcbySvcId(svcId);

			System.out.println("*** 2. Delete PR Network Service entries...");
			deletePremiseSvcbySvcId(svcId);

			System.out
					.println("*** 3. Delete Transport Network Service entries...");
			deleteTransportSvcbySvcId(svcId);

			System.out.println("*** 4-4. Delete Network Service entries...");
			deleteSvcbySvcId(svcId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
