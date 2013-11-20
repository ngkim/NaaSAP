package com.kt.naas.service;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockingServiceProcessor implements LockingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static int DEFAULT_LOCK_TIMEOUT = 30000; // 30√ 
	private final static int DEFAULT_WAIT_TIMEOUT = 10000;  //10√ 
	private final static int DEFAULT_CLEAN_INTERVAL = 1000;  // 1√ 
	
	private int lockTimeout = DEFAULT_LOCK_TIMEOUT;
	private int waitTimeout = DEFAULT_WAIT_TIMEOUT;
	private int cleanInterval = DEFAULT_CLEAN_INTERVAL;
	
	private Object	lock = new Object();
	private Map<String, Object> lockRequestMap = new ConcurrentHashMap<String, Object>();
	private Map<String, Long> 	lockTimeoutMap = new ConcurrentHashMap<String, Long>();
	private Timer scheduler;
	private String serviceName;
	
	public LockingServiceProcessor()
	{
	}
	
	@Override
	public void start() {
		logger.info("start " + serviceName + "...");
		scheduler = new Timer("Timer-" + serviceName);
        scheduler.schedule(new LockCleanTask(), cleanInterval, cleanInterval);
        logger.info(serviceName + " initialized.");
	}
	
	@Override
	public void stop() {
		logger.info("stop " + serviceName + "...");
		scheduler.cancel();
	}
	
	@Override
	public Object getUserObject(String key) {
		return lockRequestMap.get(key);
	}
	
	@Override
	public boolean lock(String key)
	{
		return lock(key, new Object(), lockTimeout);
	}
	
	@Override
	public boolean lock(String key, long timeout)
	{
		return lock(key, new Object(), timeout);
	}

	@Override
	public boolean lock(String key, Object userObject) {
		return lock(key, userObject, lockTimeout);
	}

	@Override
	public boolean lock(String key, Object userObject, long timeout) {
		return lock(key, userObject, timeout, waitTimeout);
	}

	@Override
	public boolean lock(String key, Object userObject, long timeout, long wait)
	{
		Object o = null;
		long startTime = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		
		if (userObject == null) userObject = new Object();
		
		do
		{
			synchronized(lock)
			{
				o = lockRequestMap.get(key);
				if (o == null)
				{
					lockRequestMap.put(key, userObject);
					lockTimeoutMap.put(key, new Long(now + timeout));
					logger.debug("lock ok. key=[" + key + "]");
				}
				else
				{
					try {
						logger.debug("wait unlock : " + key);
						lock.wait(cleanInterval);
					} catch (InterruptedException e) {
					}
				}
			}
			
			now = System.currentTimeMillis();
		} while (o != null && now - startTime <= wait);
		
		if (o == null) 
			return true;
		else 
			return false;
	}
	
	@Override
	public void unlock(String key) {
		synchronized(lock)
		{
			lockRequestMap.remove(key);
			lockTimeoutMap.remove(key);
			logger.debug("unlock. key=" + key);
			lock.notify();
		}
	}

	// ---- getter & setter
	public int getLockTimeout() {
		return lockTimeout;
	}

	public void setLockTimeout(int lockTimeout) {
		this.lockTimeout = lockTimeout;
	}

	public int getWaitTimeout() {
		return waitTimeout;
	}

	public void setWaitTimeout(int waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	public int getCleanInterval() {
		return cleanInterval;
	}

	public void setCleanInterval(int cleanInterval) {
		this.cleanInterval = cleanInterval;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	private class LockCleanTask extends TimerTask {
        public void run(){
        	
        	for (String key : lockTimeoutMap.keySet()) {
				Long lockTime = lockTimeoutMap.get(key);
				if (System.currentTimeMillis() > lockTime.longValue())
				{
					logger.info("lock timeout. key=[" + key + "]");
					unlock(key);
				}
			}
        }
    }
}
