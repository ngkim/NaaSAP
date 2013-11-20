package com.kt.naas.util;

import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.ResponseCloudNWList;

public class DebugUtils {
	
	public static void printDebugMsg(String debugMsg) {
		if ( GlobalConstants.OP_DEBUG ) 
			System.out.println(debugMsg);
	}
	
	public static void sendResponse(ResponseMessage response, int retCode, String retMsg) {
		if ( GlobalConstants.OP_DEBUG ) System.err.println(retMsg);
		
		response.setResultCode(retCode);
		response.setResultMessage(retMsg);
	}
	
	public static void printResponseCloudNWList(ResponseCloudNWList nwList) {
		System.out.println("RETURN CODE= " + nwList.getReturnCode());
		System.out.println("RETURN MSG= " + nwList.getMessage());
		System.out.println("TENANTNAME= " + nwList.getTenantname());
		System.out.println("TENANTID= " + nwList.getTenantid());

		ArrayList<CloudVirtualNetworkList> list = nwList.getVNIDInfo();
		for (int i = 0; i < list.size(); i++) {
			CloudVirtualNetworkList vnl = list.get(i);
			ArrayList<CloudVirtualNetwork> vnlist = vnl.getVnlist();
			for (int j = 0; j < vnlist.size(); j++) {
				CloudVirtualNetwork item = vnlist.get(j);
				System.out.println("NWNAME= " + item.getName());
				System.out.println("VNID= " + item.getVNID());
				System.out.println("SUBNET= " + item.getSubnet());
			}
		}
	}

}
