package com.kt.naas.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import org.opennms.netmgt.ping.PingResponseCallback;
import org.opennms.netmgt.ping.Pinger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingServiceProcessor implements PingService {
	private static final int MAX_SEQ_VALUE = 32767;
    private static final int MIN_SEQ_VALUE = 1;
    private static AtomicInteger currentSeq = new AtomicInteger(MIN_SEQ_VALUE);
    
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String serviceName;
	private int timeout = 5000;
	private int retries = 5;
	private String jicmp = "/usr/lib/libjicmp.so";
	
	@Override
	public void start() {
		logger.info("start " + serviceName + "...");
		System.setProperty("opennms.library.jicmp", jicmp);
		logger.info("opennms.library.jicmp=" + System.getProperty("opennms.library.jicmp"));
		
		try {
			Pinger.initialize();
			logger.info(serviceName + "[" + this + "] initialized.");
		} catch (IOException e) {
			logger.error(serviceName + "[" + this + "] initialize fail.", e);
		}
	}

	@Override
	public void stop() {
		logger.info("stop " + serviceName + "[" + this + "]...");
	}

	@Override
	public synchronized void ping(String ip, PingResponseCallback callBack) {
		try {
			InetAddress target = InetAddress.getByName(ip);
			Pinger.ping(target, timeout, retries, getNextSeqId(), callBack);
		} catch (UnknownHostException e) {
			logger.error("PING FAIL. IP=["+ip+"]. " + e.getMessage());
		} catch (IOException e) {
			logger.error("PING FAIL. IP=["+ip+"]. " + e.getMessage());		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getJicmp() {
		return jicmp;
	}

	public void setJicmp(String jicmp) {
		this.jicmp = jicmp;
	}

	public static short getNextSeqId(){
        int seq = currentSeq.incrementAndGet();

        if(seq == MAX_SEQ_VALUE){
        	currentSeq = new AtomicInteger(MIN_SEQ_VALUE);
        }

        return (short)seq;
    }
}
