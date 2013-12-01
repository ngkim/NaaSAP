package com.kt.naas.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetworkSwapUtils {
	String swId;
	String floodlightServer;
	
	boolean debug;
	
	public NetworkSwapUtils(String floodlightServer, String swId){
		this.floodlightServer = floodlightServer;
		this.swId = swId;				
	}
	
	public void enableDebug() {
		this.debug = true;
	}
	
	public void disableDebug() {
		this.debug = false;
	}

	public void addStaticFlow(String ruleName, String inPort, String outPort) {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/json";
		String apiParam = "{\"switch\":\"" + swId + "\",\"name\":\"" + ruleName + "\",\"cookie\":\"0\",\"priority\":\"32768\",\"ingress-port\":\"" + inPort + "\",\"active\":\"true\",\"actions\":\"output=" + outPort + "\"}";
		
		callAPI(apiUrl, apiParam, 1);
		
	}

	public void addStaticFlowFromHost(String ruleName, String inPort, String outPort, String hostIp) {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/json";
		String apiParam = "{\"switch\":\"" + this.swId + "\",\"name\":\"" + ruleName + "\",\"cookie\":\"0\",\"priority\":\"32768\",\"ingress-port\":\"" + inPort + "\",\"src-ip\":\"" + hostIp + "\",\"active\":\"true\",\"actions\":\"output=" + outPort + "\"}";
		
		callAPI(apiUrl, apiParam, 1);
	}
	
	public void addDropFlow(String ruleName, String portToDrop) {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/json";
		String apiParam = "{\"switch\":\"" + this.swId + "\",\"name\":\"" + ruleName + "\",\"cookie\":\"0\",\"priority\":\"32768\",\"ingress-port\":\"" + portToDrop + "\",\"active\":\"true\",\"actions\":\"\"}";
	
		callAPI(apiUrl, apiParam, 1);
	}
	
	public void deleteStaticFlow(String ruleName) {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/json";
		String apiParam = "{\"name\":\"" + ruleName + "\"}";
		
		callAPI(apiUrl, apiParam, 2);
	}
	
	public void clearFlows() {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/clear/" + this.swId + "/json";
		
		callAPI(apiUrl, "", 0);
	}
	
	public void listFlows() {
		String apiUrl = "http://" + this.floodlightServer + "/wm/staticflowentrypusher/list/" + this.swId + "/json";
		
		callAPI(apiUrl, "", 0);
	}
	
	public void callAPI(String url, String param, int mode) {
		Process p;
		String s = null;
		String cmd = "", optStr = "";
		
		if (mode == 0) { 	// List
			optStr = "";			
		} else if (mode == 1) { 	// Add
			optStr = "-d";
		} else if (mode == 2) { 	// Delete
			optStr = "-X DELETE -d";
		}
		
		cmd = "curl " + optStr + " " + param + " " + url;
		if (debug) System.out.println("CMD= " + cmd);
		try {
			p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((s = br.readLine()) != null)
				System.out.println("line: " + s);
			p.waitFor();

			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
}
