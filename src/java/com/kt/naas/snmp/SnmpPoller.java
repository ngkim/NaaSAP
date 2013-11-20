package com.kt.naas.snmp;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Target;
import org.snmp4j.asn1.BER;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.VariableBinding;

public class SnmpPoller {
	private final static Logger logger = LoggerFactory.getLogger(SnmpPoller.class);
	
	private static int 	DEFAULT_MAX_REPETITIONS = 10;
	private static int		DEFAULT_NON_REPEATERS = 0;
	
	public SnmpPoller()
	{
		BER.setCheckSequenceLength(false);
	}

    public static PDU get(SnmpPeer peer, List<VariableBinding> vbs) throws IOException
    {
    	return send(peer, PDU.GET, vbs);
    }

    public static PDU getNext(SnmpPeer peer, List<VariableBinding> vbs) throws IOException
    {
    	return send(peer, PDU.GETNEXT, vbs);
    }

    public static PDU getBulk(SnmpPeer peer, List<VariableBinding> vbs) throws IOException
    {
    	return send(peer, PDU.GETBULK, vbs);
    }
    
    public static PDU set(SnmpPeer peer, List<VariableBinding> vbs) throws IOException
    {
    	return send(peer, PDU.SET, vbs);
    }
    
    
    public static PDU send(SnmpPeer peer, int pduType, List<VariableBinding> vbs) throws IOException {
    	return send(peer, pduType, vbs, DEFAULT_MAX_REPETITIONS, DEFAULT_NON_REPEATERS);
    }
    
    public static PDU send(SnmpPeer peer, int pduType, List<VariableBinding> vbsReq, int maxRepetitions, int nonRepeaters) throws IOException {
		PDU request = createPDU(peer, pduType);
		
		Target target = null;
		if (pduType == PDU.SET)
			target = peer.getWriteTarget();
		else
			target = peer.getReadTarget();
		
		if (request.getType() == PDU.GETBULK) {
			request.setMaxRepetitions(maxRepetitions);
			request.setNonRepeaters(nonRepeaters);
		}
		
		if (peer.isLogging())
			logger.debug("[SNMP]Send Request to " + target);
		
		for (VariableBinding vb: vbsReq) {
			request.add(vb);
			if (peer.isLogging())
				logger.debug("[SNMP]" + vb.getOid() + "=" + vb.getVariable());
		}

		PDU response = null;

		ResponseEvent responseEvent;
		long startTime = System.currentTimeMillis();
		responseEvent = peer.getSession().send(request, target);
		if (responseEvent != null) {
			response = responseEvent.getResponse();

			if (response != null)
			{
				@SuppressWarnings("rawtypes")
				List vbsRes = response.getVariableBindings();
				
				if ((peer.isLogging() || response.getErrorStatus() > 0) && vbsRes != null)
				{
					logger.debug("[SNMP]Receive Response from " + target);
					logger.debug("[SNMP]ErrorIndex=" + response.getErrorIndex() 
							+ ", ErrorStatus=" + response.getErrorStatus() );
					for (int i = 0; i < vbsRes.size(); i++) {
						logger.debug("[SNMP]" + vbsRes.get(i));
					}
				}
			}
			if (peer.isLogging())
				logger.debug("[SNMP]Received response after " + (System.currentTimeMillis() - startTime) + " millis");
		}
		
		return response;
	}
    
	public static PDU createPDU(SnmpPeer peer, int pduType) {
		PDU request;

		if (peer.getSnmpVersion() == SnmpConstants.version3) {
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
		
		request.setType(pduType);
		request.setNonRepeaters(0);
		request.setMaxRepetitions(DEFAULT_MAX_REPETITIONS);
		return request;
	}
}
