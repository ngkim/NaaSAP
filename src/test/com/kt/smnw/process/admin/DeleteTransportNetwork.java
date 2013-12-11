package com.kt.smnw.process.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.db.CodeDao;
import com.kt.naas.domain.Code;
import com.kt.naas.util.CodeUtils;
import com.kt.naas.util.PrintUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;

public class DeleteTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	private static RequestDeleteTransportNetwork generateRequest(String svcId) {
		RequestDeleteTransportNetwork req = new RequestDeleteTransportNetwork();

		req.setName("naasEth");
		req.setDescription("DJ POTN connection test");

		req.setRid("NaaS");
		req.setCid("88888888880");
		req.setEid(svcId);

		return req;
	}

	public static void delete(String svcId) {
		ResponseDeleteTransportNetwork res = null;
		TimeUtils time = new TimeUtils();

		try {
			time.setStartTime();

			RequestDeleteTransportNetwork req = generateRequest(svcId);
			TransportSDNAPI api = new TransportSDNAPI(
					GlobalConstants.URL_TRANSPORT_SDN_API);
			res = api.deleteNetwork(req);

			double duration = time.getDuration() / 1000;

			System.err.println("Time for deleting a transport network = "
					+ duration);

			PrintUtils printUtil = new PrintUtils();
			printUtil.printKeyAndValue("EID", res.getId());
			printUtil.printKeyAndValue("Status", res.getStatus().getResult());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test-context.xml");
		getAllCodes(context);
		System.out.println("==========================");
		System.out.println("DOCU=" + CodeUtils.getCodeValue("GENREID", "DOCU"));
		System.out.println();

		String svcId = args[0];
		
		if (svcId == null ) {
			System.out.println("Error!!! No Transport Svc ID");
		} else {
			System.out.println("Delete Transport Svc= " + svcId);
			delete(svcId);
		}
		

	}

	public static void getAllCodes(ApplicationContext context) {
		CodeDao dao = (CodeDao) context.getBean("codeDao");
		List<Code> codes = dao.getAllCodes();

		System.out.println(codes.size() + " items selected.");
		for (Code code : codes) {
			System.out.println(code);
		}
	}
}
