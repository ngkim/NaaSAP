package com.kt.smnw.process.admin;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.db.CodeDao;
import com.kt.naas.domain.Code;
import com.kt.naas.util.CodeUtils;

public class GoBackToInternet {
	
	public static void backToInternet() {
		// back to internet
		System.out.println("*** Go back to internet...");
		DJPremiseSDNAPI api = new DJPremiseSDNAPI(
				GlobalConstants.URL_PREMISE_SDN_API_DJ);
		boolean result = api.swapNetworkToInternet();
		if (result) {
			System.out.println("- Successfully come back to internet...");
		} else {
			System.out.println("- Failed to go back to internet !!!");
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test-context.xml");
		getAllCodes(context);
		System.out.println("==========================");
		System.out.println("DOCU=" + CodeUtils.getCodeValue("GENREID", "DOCU"));
		System.out.println();

		boolean back = true;
		
		if (back) backToInternet();
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
