package com.kt.naas.util;

import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.ResponseCloudNWList;

public class DebugUtils {

	public static void printDebugMsg(String debugMsg) {
		if (GlobalConstants.OP_DEBUG)
			System.out.println(debugMsg);
	}

	public static void sendResponse(ResponseMessage response, int retCode,
			String retMsg) {
		

		if ( response != null ) {
			if (GlobalConstants.OP_DEBUG)
				System.err.println("Response Msg= " + retMsg);
			response.setResultCode(retCode);
			response.setResultMessage(retMsg);
		} else {
			if (GlobalConstants.OP_DEBUG)
				System.err.println("Response Msg= " + retMsg);
		}
	}
}
