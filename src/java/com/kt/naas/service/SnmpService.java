package com.kt.naas.service;

import java.util.List;
import java.util.Map;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.kt.naas.snmp.SnmpPeer;

public interface SnmpService extends Service {
	public VariableBinding get(SnmpPeer peer, String oid);
	
	public Map<String, VariableBinding> get(SnmpPeer peer, Map<String, String> oidPrefix, String oidIndex);
	
	public List<VariableBinding> set(SnmpPeer peer, List<VariableBinding> vb);
	
	public Map<OID, Map<String, VariableBinding>> walk(SnmpPeer peer, Map<String, String> snmpOids);
	public Map<OID, VariableBinding> walk(SnmpPeer peer, String snmpOid);

//	public Map<OID, Map<String, VariableBinding>> walk(Target target, Map<String, String> snmpOids, String startIndex);
//	public Map<OID, VariableBinding> walk(Target target, String snmpOid, String startIndex);
	
	public VariableBinding getNext(SnmpPeer peer, String oid);
	
	public Map<OID, Map<String, VariableBinding>> walkSelectedIndex(SnmpPeer peer, Map<String, String> snmpOids, List<String> indexList);
}
