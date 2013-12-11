package com.kt.smnw.process.admin;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.db.CodeDao;
import com.kt.naas.domain.Code;
import com.kt.naas.util.CodeUtils;
import com.kt.naas.util.admin.ServiceUtils;

public class DeleteEmptyServices {

	public DeleteEmptyServices() {

	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test-context.xml");
		getAllCodes(context);
		System.out.println("==========================");
		System.out.println("DOCU=" + CodeUtils.getCodeValue("GENREID", "DOCU"));
		System.out.println();

		boolean delete = true;
		boolean deleteAll = false;
		
		// delete = true;
		printServices(delete, deleteAll);	
		
	}

	private static void printServices(boolean delete, boolean deleteAll) {
		ServiceUtils database = new ServiceUtils();

		if (deleteAll) {
			database.listSvcDeleted(delete);
		} else {
			String svcId = database.getSvcId();
			System.out.flush();

			System.out.println("=== Transport Services ===");
			database.printTransportSvcId(svcId, delete);
			
			System.out.println("\n=== Premise Services ===");
			database.printPremiseSvcId(svcId);
			if (delete) {
				database.deletePremiseSvcbySvcId(svcId);
			}

			System.out.println("\n=== Cloud Services ===");
			database.printCloudSvcId(svcId);
			if (delete) {
				database.deleteDCSvcbySvcId(svcId);
			}
			System.out.println();

			if (delete) {
				database.deleteSvcbySvcId(svcId);
			}
		}
		System.out.println();

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
