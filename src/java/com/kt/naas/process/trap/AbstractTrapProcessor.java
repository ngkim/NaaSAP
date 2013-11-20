package com.kt.naas.process.trap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.kt.naas.process.Processor;
import com.kt.naas.snmp.TrapMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

public abstract class AbstractTrapProcessor implements Processor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected Map<String, String> 	oidNames 		= new LinkedHashMap<String, String>();
	protected Map<String, Variable>	trapVars		= new LinkedHashMap<String, Variable>();
	
	@Override
	public void process(Object message) {
		TrapMessage msg = (TrapMessage)message;
		
		makeVars(msg.getPdu());
		processTrap(msg);
	}

	public abstract void processTrap(TrapMessage message);
	
	public Map<String, String> getOidNames() {
		return oidNames;
	}

	public void setOidNames(Map<String, String> oidNames) {
		this.oidNames = oidNames;
	}
	
	@SuppressWarnings("rawtypes")
	public void makeVars(PDU trap)
	{
		Vector v = trap.getVariableBindings();
		for (int i = 0; i < v.size(); i++) {
			VariableBinding vb = (VariableBinding)v.elementAt(i);
			String vbName = oidNames.get(vb.getOid().toString());
			if (vbName == null) 
			{
				// OID 매칭이 안되면 맨 뒤 instance 번호를 버리고 비교
				OID oid = new OID(vb.getOid());
				oid.removeLast();
				vbName = oidNames.get(oid.toString());
				
				if (vbName == null)
					vbName = vb.getOid().toString();
			}
			trapVars.put(vbName, vb.getVariable());
		}
	}
}
