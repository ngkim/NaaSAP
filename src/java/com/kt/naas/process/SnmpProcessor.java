package com.kt.naas.process;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.kt.naas.service.SnmpService;
import com.kt.naas.snmp.SnmpPeer;

public abstract class SnmpProcessor implements Processor {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SnmpService	snmpService = null;
	
	// snmp peer
	protected SnmpPeer  peer		= null;
	
	// snmp request data
	protected Map<String, String> 	getRequests 	= new LinkedHashMap<String, String>();
	protected Map<String, String> 	walkRequests 	= new LinkedHashMap<String, String>();
	protected List<VariableBinding> setRequests		= new ArrayList<VariableBinding>();
	
	// 
	protected Map<String, String> 	oidPool 		= new LinkedHashMap<String, String>();
	protected List<String>		 	loopIndexes		= new ArrayList<String>();
	protected String				instanceIndex	= "0";


	protected abstract void initSnmp();
	
	protected void stopSnmp()
	{
		if (peer != null)
		{
			Snmp session = peer.getSession();
			if (session != null)
			{
				try {
					session.close();	
				} catch (Exception e) {
					// do nothing.
				}
			}
		}
	}
	
	protected VariableBinding get(String oid)
	{
		return snmpService.get(peer, oid);
	}
	
	protected Map<String, VariableBinding> get()
	{
		return snmpService.get(peer, getRequests, instanceIndex);
	}
	
	protected List<VariableBinding> set()
	{
		return snmpService.set(peer, setRequests);
	}
	
	protected Map<OID, Map<String, VariableBinding>> walk()
	{
		return snmpService.walk(peer, walkRequests);
	}
	
	protected Map<OID, VariableBinding> walk(String snmpOid)
	{
		return snmpService.walk(peer, snmpOid);
	}

	protected VariableBinding getNext(String oid)
	{
		return snmpService.getNext(peer, oid);
	}
	
	protected Map<OID, Map<String, VariableBinding>> walkSelectedIndex(Map<String, String> snmpOids, List<String> indexList)
	{
		return snmpService.walkSelectedIndex(peer, snmpOids, indexList);
	}
	
	
	public List<OID> getInstanceOids(Map<OID, Map<String, VariableBinding>> resultMap)
	{
		List<OID> ret = new ArrayList<OID>();
		if (resultMap == null || resultMap.size() == 0) 
			return ret;
		
		SortedSet<OID> oids = new TreeSet<OID>(resultMap.keySet());
		for (OID oid : oids)
		{
			ret.add(oid);
		}
		
		return ret;
	}
	
	// ----- getters & setters
	public Map<String, String> getGetRequests() {
		return getRequests;
	}

	public void setGetRequests(Map<String, String> getRequests) {
		this.getRequests = getRequests;
	}

	public Map<String, String> getWalkRequests() {
		return walkRequests;
	}

	public void setWalkRequests(Map<String, String> walkRequests) {
		this.walkRequests = walkRequests;
	}

	public List<VariableBinding> getSetRequests() {
		return setRequests;
	}

	public void setSetRequests(List<VariableBinding> setRequests) {
		this.setRequests = setRequests;
	}

	public Map<String, String> getOidPool() {
		return oidPool;
	}

	public void setOidPool(Map<String, String> oidPool) {
		this.oidPool = oidPool;
	}

	public List<String> getLoopIndexes() {
		return loopIndexes;
	}

	public void setLoopIndexes(List<String> loopIndexes) {
		this.loopIndexes = loopIndexes;
	}

	public String getInstanceIndex() {
		return instanceIndex;
	}

	public void setInstanceIndex(String instanceIndex) {
		this.instanceIndex = instanceIndex;
	}

	public SnmpService getSnmpService() {
		return snmpService;
	}

	public void setSnmpService(SnmpService snmpService) {
		this.snmpService = snmpService;
	}
}
