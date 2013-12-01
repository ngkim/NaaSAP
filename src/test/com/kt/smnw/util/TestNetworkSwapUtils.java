package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.GlobalConstants;
import com.kt.naas.util.NetworkSwapUtils;

public class TestNetworkSwapUtils {
	String swId = "00:00:00:1c:73:4f:32:d7";
	String server = "211.224.204.144:8080";
	
//	add_drop_flow DROP_POTN 1
//	add_static_flow INTERNET_INBOUND 9 17
//	add_static_flow_from_host INTERNET_OUTBOUND 17 9 210.183.241.77

	@Test
	public void testListFlows() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.listFlows();
	}
	
	@Test
	public void testClearFlows() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.clearFlows();
	}
	
	@Test
	public void testAddFlows() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.addStaticFlow("INTERNET_INBOUND", "9", "17");
	}
	
	@Test
	public void testAddFlowsFromHost() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.addStaticFlowFromHost("INTERNET_INBOUND", "9", "17", "211.224.204.144");
		
		System.out.println();
		util.listFlows();
	}
	
	@Test
	public void testAddDropFlows() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.addDropFlow("DROP_INTERNET", "9");
	}
	
	@Test
	public void testDeleteFlows() {
		NetworkSwapUtils util = new NetworkSwapUtils(server, swId);
		util.enableDebug();
		
		util.deleteStaticFlow("INTERNET_INBOUND");
		
		System.out.println();
		util.listFlows();
	}
	
	@Test
	public void testSwapToInternet() {
		NetworkSwapUtils util = new NetworkSwapUtils(
				GlobalConstants.DJ_FLOODLIGHT,
				GlobalConstants.SW_DJ_OPENFLOW);

		util.deleteStaticFlow("POTN_INBOUND");
		util.deleteStaticFlow("POTN_OUTBOUND");
		util.deleteStaticFlow("DROP_INTERNET");

		util.addDropFlow("DROP_POTN", "1");
		util.addStaticFlow("INTERNET_INBOUND", "9", "17");
		util.addStaticFlowFromHost("INTERNET_OUTBOUND", "17", "9",
				"210.183.241.77");
	}
	
	@Test
	public void testSwapToPotn() {
		NetworkSwapUtils util = new NetworkSwapUtils(
				GlobalConstants.DJ_FLOODLIGHT,
				GlobalConstants.SW_DJ_OPENFLOW);

		util.deleteStaticFlow("INTERNET_INBOUND");
		util.deleteStaticFlow("INTERNET_OUTBOUND");
		util.deleteStaticFlow("DROP_POTN");

		util.addDropFlow("DROP_INTERNET", "9");
		util.addStaticFlow("POTN_INBOUND", "1", "17");
		util.addStaticFlowFromHost("POTN_OUTBOUND", "17", "1",
				"210.183.241.77");
	}

}
