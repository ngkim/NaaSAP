package com.kt.naas.service;

import org.opennms.netmgt.ping.PingResponseCallback;

public interface PingService extends Service {
	public void ping(String ip, PingResponseCallback callBack);
}
