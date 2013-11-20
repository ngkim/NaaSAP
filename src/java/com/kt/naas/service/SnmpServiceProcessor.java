package com.kt.naas.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.RetrievalEvent;
import org.snmp4j.util.TreeEvent;

import com.kt.naas.error.SnmpException;
import com.kt.naas.error.SnmpProcessingException;
import com.kt.naas.error.SnmpTimeoutException;
import com.kt.naas.snmp.SnmpPeer;
import com.kt.naas.snmp.SnmpPoller;
import com.kt.naas.snmp.SnmpWalker;

/*
 *  변경 이력 :
 *  	2011.05.17 walk 수행을 getNext로 수행해서 장비에 부하를 초래하고 수집 속도가 느림
 *  		GETBULK로 변경 하고 한번에 수집 개수는 10개
 */
public class SnmpServiceProcessor implements SnmpService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String serviceName;
	
	@Override
	public VariableBinding get(SnmpPeer peer, String oid) {
		ArrayList<VariableBinding> vbs = new ArrayList<VariableBinding>();
		vbs.add(new VariableBinding(new OID(oid)));
		try {
			PDU response = SnmpPoller.get(peer, vbs);
			if (response == null)
				throw new SnmpTimeoutException();

			if (response.getErrorStatus() != RetrievalEvent.STATUS_OK)
				throw new SnmpException(response.getErrorIndex(), response.getErrorStatus(), response.getErrorStatusText());
			
			@SuppressWarnings("rawtypes")
			Vector resVbs = response.getVariableBindings();

			if (resVbs.size() > 0)
				return (VariableBinding)resVbs.get(0);
			else
				throw SnmpProcessingException.VBSIZE_MISMATCH;
		} catch (IOException e) {
			throw new SnmpProcessingException(e.getMessage());
		}
		
	}

	@Override
	public VariableBinding getNext(SnmpPeer peer, String oid) {
		ArrayList<VariableBinding> vbs = new ArrayList<VariableBinding>();
		vbs.add(new VariableBinding(new OID(oid)));
		
		try {
			PDU response = SnmpPoller.getNext(peer, vbs);
			if (response == null)
				throw new SnmpTimeoutException();

			if (response.getErrorStatus() != RetrievalEvent.STATUS_OK)
				throw new SnmpException(response.getErrorIndex(), response.getErrorStatus(), response.getErrorStatusText());
			
			@SuppressWarnings("rawtypes")
			Vector resVbs = response.getVariableBindings();

			if (resVbs.size() > 0)
				return (VariableBinding)resVbs.get(0);
			else
				throw SnmpProcessingException.VBSIZE_MISMATCH;
		} catch (IOException e) {
			throw new SnmpProcessingException(e.getMessage());
		}
	}
	
	@Override
	public Map<String, VariableBinding> get(SnmpPeer peer, Map<String, String> oidPrefix, String oidIndex) {
		ArrayList<VariableBinding> vbs = new ArrayList<VariableBinding>();
		HashMap<String, VariableBinding> result = new HashMap<String, VariableBinding>();
		ArrayList<String> keyList = new ArrayList<String>(oidPrefix.keySet());
		
		for (String key : keyList) 
		{
			String sOid = oidPrefix.get(key);
			OID oid = new OID(sOid + "." + oidIndex);
			vbs.add(new VariableBinding(oid));
		}
		
		try {
			PDU response = SnmpPoller.get(peer, vbs);
			if (response == null)
				throw new SnmpTimeoutException();

			if (response.getErrorStatus() != RetrievalEvent.STATUS_OK)
				throw new SnmpException(response.getErrorIndex(), response.getErrorStatus(), response.getErrorStatusText());

			@SuppressWarnings("rawtypes")
			Vector resVbs = response.getVariableBindings();

			for (int i = 0; i < resVbs.size(); i++) {
				VariableBinding resVb = (VariableBinding)resVbs.get(i);
				result.put(keyList.get(i), resVb);
			}
			return result;
		} catch (IOException e) {
			throw new SnmpProcessingException(e.getMessage());
		}
	}


	@Override
	public List<VariableBinding> set(SnmpPeer peer, List<VariableBinding> vbs) {
		ArrayList<VariableBinding> result = new ArrayList<VariableBinding>();
		
		try {
			PDU response = SnmpPoller.set(peer, vbs);
			if (response == null)
				throw new SnmpTimeoutException();

			if (response.getErrorStatus() != RetrievalEvent.STATUS_OK)
				throw new SnmpException(response.getErrorIndex(), response.getErrorStatus(), response.getErrorStatusText());

			@SuppressWarnings("rawtypes")
			Vector resVbs = response.getVariableBindings();

			for (int i = 0; i < resVbs.size(); i++) {
				VariableBinding resVb = (VariableBinding)resVbs.get(i);
				result.add(resVb);
			}
			return result;
		} catch (IOException e) {
			throw new SnmpProcessingException(e.getMessage());
		}
	}

	@Override
	public Map<OID, Map<String, VariableBinding>> walk(SnmpPeer peer, Map<String, String> snmpOids) {
		return walk(peer, snmpOids, "");
	}

	@Override
	public Map<OID, VariableBinding> walk(SnmpPeer peer, String snmpOid) {
		return walk(peer, snmpOid, "");
	}

//	@Override
	public Map<OID, Map<String, VariableBinding>> walk(SnmpPeer peer, Map<String, String> snmpOids, String startIndex) {
		Map<OID, Map<String, VariableBinding>> outMap = new HashMap<OID, Map<String, VariableBinding>>();
		
		for (String key : snmpOids.keySet()) {
			
			String oid = snmpOids.get(key);
			
			Map<OID, VariableBinding> walkResult = walk(peer, oid, startIndex);
			
			for (OID oidIndex : walkResult.keySet()) {
				Map<String, VariableBinding> rowMap = getRowMap(outMap, oidIndex);
				rowMap.put(key, walkResult.get(oidIndex));
			}
		}
		return outMap;
	}

//	@Override
//	public Map<OID, VariableBinding> walk(SnmpPeer peer, String snmpOid, String startIndex) {
//		return walk(peer, snmpOid,startIndex, logging, logging, logging);
//	}

	@Override
	public Map<OID, Map<String, VariableBinding>> walkSelectedIndex(SnmpPeer peer, Map<String, String> snmpOids, List<String> indexList) {
		Map<OID, Map<String, VariableBinding>> outMap = new HashMap<OID, Map<String, VariableBinding>>();
		for (int i = 0; i < indexList.size(); i++) {
			Map<String, VariableBinding> rowData = get(peer, snmpOids, indexList.get(i));
			
			OID index = new OID(indexList.get(i));
			outMap.put(index, rowData);
		}
		return outMap;
	}
	
	
	public Map<OID, VariableBinding> walk(SnmpPeer peer, String snmpOid, String startIndex)
	{
		SnmpWalker walker = new SnmpWalker(peer);
		
		//walker.setWalkPduType(PDU.GETNEXT);
		walker.setWalkOID(snmpOid);
		walker.setStartIndex(startIndex);
		
		OID oidPrefix = new OID(snmpOid);
		Map<OID, VariableBinding> outMap = new HashMap<OID, VariableBinding>();
		
		try {
			walker.walk();
			TreeEvent event = walker.getListener().getFinishEvent();
			
			List<VariableBinding> varList = walker.getListener().getVarList();
			
			if (event.getStatus() == TreeEvent.STATUS_TIMEOUT)
				throw new SnmpTimeoutException();
			
			if (event.getStatus() != TreeEvent.STATUS_OK 
					&& event.getStatus() != 5)
			{
				if (event.getStatus() == RetrievalEvent.STATUS_EXCEPTION
						&& event.getErrorMessage().equals("Snmp session has been closed"))
					throw new SnmpTimeoutException();
				
				if (event.getStatus() == RetrievalEvent.STATUS_EXCEPTION)
				{
					throw new SnmpProcessingException(event.getErrorMessage());
				}
				
				throw new SnmpException(1, event.getStatus(), event.getErrorMessage());
			}
			
			for (int i = 0; i < varList.size(); i++) {
				VariableBinding vb = varList.get(i);
				OID oid = vb.getOid();

				OID index = getIndex(oidPrefix, oid);
				outMap.put(index, vb);
			}
			
			return outMap;
		} catch (SnmpException se) {
			throw se;
		} catch (SnmpProcessingException e)
		{
			throw e;
		} catch (SnmpTimeoutException e) {
			throw e;
		} catch (Exception ex) {
			logger.error("exception in process snmp.", ex);
			throw new SnmpProcessingException(ex.getClass().getSimpleName() + "@" + ex.getMessage());
		}
	}
	
	private static OID getIndex(OID prefixOid, OID fullOid){
		int prefixSize = prefixOid.size();
		int fullSize = fullOid.size();
		
		int indexSize = fullSize - prefixSize;
		
		int[] fullOidRaw = fullOid.toIntArray();
		int[] indexRaw = new int[indexSize];
		
		System.arraycopy(fullOidRaw, prefixSize, indexRaw, 0, indexSize);
		
		OID retOid = new OID(indexRaw);
		
		return retOid;
	}
	
	private Map<String, VariableBinding> getRowMap(Map<OID, Map<String, VariableBinding>> mainMap, OID key)
	{
		Map<String, VariableBinding> retMap = mainMap.get(key);
		if (retMap == null)
		{
			retMap = new HashMap<String, VariableBinding>();
			mainMap.put(key, retMap);
		}
		return retMap;
	}

	@Override
	public void start() {
		logger.info("start " + serviceName + "...");
//		ServiceContext.setSnmpService(this);
//		poller = new SnmpPoller();
//		poller.setLogging(logging);
		
//		if (logging)
//			ServiceContext.setSnmpService(this);
//		else
//			ServiceContext.setSnmpServiceWithoutLogging(this);
		
		logger.info(serviceName + "[" + this + "] initialized.");
	}
	
	@Override
	public void stop() {
		logger.info("stop " + serviceName + "[" + this + "]...");
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
