package com.kt.naas.snmp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Target;
import org.snmp4j.asn1.BER;
import org.snmp4j.log.JavaLogFactory;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.CounterSupport;
import org.snmp4j.mp.DefaultCounterListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.util.PDUFactory;

public class SnmpWalker implements PDUFactory {
	static {
		LogFactory.setLogFactory(new JavaLogFactory());
		BER.setCheckSequenceLength(false);
	}
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public SnmpWalker(SnmpPeer peer)
	{
		this.peer = peer;
		CounterSupport.getInstance().addCounterListener(new DefaultCounterListener());
	}
	
	SnmpPeer	peer = null;
	String 	walkOID = null;
	String 	startIndex = null;
	
	int 	walkPduType = PDU.GETBULK;
	int 	maxRepetitions = 10;
	
	MyTreeListener listener = new MyTreeListener();
	
	public String getWalkOID() {
		return walkOID;
	}

	public void setWalkOID(String walkOID) {
		this.walkOID = walkOID;
	}

	public String getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}

	public int getWalkPduType() {
		return walkPduType;
	}

	public void setWalkPduType(int walkPduType) {
		this.walkPduType = walkPduType;
	}

	public int getMaxRepetitions() {
		return maxRepetitions;
	}

	public void setMaxRepetitions(int maxRepetitions) {
		this.maxRepetitions = maxRepetitions;
	}

	public MyTreeListener getListener() {
		return listener;
	}

	public void setListener(MyTreeListener listener) {
		this.listener = listener;
	}
	
	public SnmpPeer getPeer() {
		return peer;
	}

	public void setPeer(SnmpPeer peer) {
		this.peer = peer;
	}

	public void walk() throws IOException {
		listener = new MyTreeListener();
		listener.setRequestLogging(peer.isLogging());
		listener.setResponseLogging(peer.isLogging());
		listener.setSummaryLogging(peer.isLogging());
		
		if (peer.isLogging())
			logger.debug("[SNMP]" + walkOID + ":RequestStartIndex=[" + startIndex +"]");
		
		OID 	rootOID = new OID(walkOID);
		OID 	startOID = new OID(walkOID);;
		
		if (startIndex.length() > 0)
			startOID.append(new OID(startIndex));

		MyTreeUtils treeUtils = new MyTreeUtils(peer.getSession(), this);

		synchronized (listener) {
			treeUtils.setIgnoreLexicographicOrder(true);
			treeUtils.getSubtree(peer.getReadTarget(), rootOID, startOID, null, listener);
			try {
				listener.wait();
			} catch (InterruptedException ex) {
				logger.error("Tree retrieval interrupted: " + ex.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public PDU createPDU(Target target) {
		PDU request;

		if (target.getVersion() == SnmpConstants.version3) {
			request = new ScopedPDU();
		    ScopedPDU scopedPDU = (ScopedPDU)request;
		    if (peer.getContextEngineId() != null) {
		    	scopedPDU.setContextEngineID(peer.getContextEngineId());
		    }
		    if (peer.getContextName() != null) {
		        scopedPDU.setContextName(peer.getContextName());
		    }
		}
		else {
	        request = new PDU();
		}
		
		request.setType(walkPduType);
		request.setNonRepeaters(0);
		request.setMaxRepetitions(maxRepetitions);
		return request;
	}
	
	public static void main(String[] args) {
//		try {
//			AbstractTransportMapping transport;
//			transport = new DefaultUdpTransportMapping();
//			Snmp snmp = new Snmp(transport);
//			OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
//			
//			((MPv3)snmp.getMessageProcessingModel(MPv3.ID)).setLocalEngineID(localEngineID.getValue());
//			
//			int engineBootCount = 0;
//			OctetString securityName = new OctetString("insoft");
//            OID authProtocol = AuthMD5.ID;
//            OctetString authPassphrase = new OctetString("insoft123");
//            OID privProtocol = PrivDES.ID;
//            OctetString privPassphrase = new OctetString("insoft12345");;
//						
//			USM usm = new USM(SecurityProtocols.getInstance(), localEngineID, engineBootCount);
//			SecurityModels.getInstance().addSecurityModel(usm);
//						
//			UsmUser user = new UsmUser(securityName, authProtocol, authPassphrase, privProtocol, privPassphrase);
//			//usm.addUser(securityName, user);
//			snmp.getUSM().addUser(securityName, user);
//			
//			UserTarget target1 = new UserTarget();
//			target1.setSecurityLevel(SecurityLevel.AUTH_PRIV);
//			target1.setSecurityName(securityName);
//			target1.setAddress(new UdpAddress("112.160.66.24" + "/" + 161));
//			target1.setRetries(5);
//			target1.setTimeout(5000);
//			target1.setVersion(SnmpConstants.version3);
//			target1.setMaxSizeRequestPDU(65535);
//			
//			CommunityTarget target2 = new CommunityTarget();
//		    target2.setCommunity(new OctetString("public"));
//			target2.setAddress(new UdpAddress("112.160.66.24" + "/" + 161));
//			target2.setRetries(10);
//			target2.setTimeout(5000);
//			target2.setVersion(SnmpConstants.version2c);
//			
//			snmp.listen();
//			
//			SnmpWalker walker = new SnmpWalker();
//			
//
//			
//			walker.setWalkPduType(PDU.GETNEXT);
//			walker.setSnmpSession(snmp);
//			walker.setTarget(target1);
//			walker.setWalkOID("1.3.6.1.2.1.2.2.1.2");
//			walker.setStartIndex("");
//			walker.setSummaryLogging(true);
//			walker.setRequestLogging(true);
//			walker.setResponseLogging(true);
//			
//			walker.walk();
//			
//			walker.setTarget(target2);
//			walker.walk();
//			
//			snmp.close();
//			System.out.println("end.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
}
