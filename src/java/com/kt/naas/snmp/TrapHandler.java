package com.kt.naas.snmp;

public interface TrapHandler {
	void processTrap(TrapNotification noti);
}

