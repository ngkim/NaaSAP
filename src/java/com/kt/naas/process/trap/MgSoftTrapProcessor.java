package com.kt.naas.process.trap;

import org.snmp4j.smi.Variable;

import com.kt.naas.snmp.TrapMessage;

public class MgSoftTrapProcessor extends AbstractTrapProcessor {

	@Override
	public void processTrap(TrapMessage message) {
		Variable message1 = trapVars.get("message1");
		Variable message2 = trapVars.get("message2");
		
		logger.info("=========================================");
		logger.info("ip=" + message.getSourceIp());
		logger.info("-----------------------------------------");
		logger.info("message1=" + message1.toString());
		logger.info("message2=" + message2.toString());
		logger.info("=========================================");
	}

}
