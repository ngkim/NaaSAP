package com.kt.naas.service;

/*
 * 동시실행을 방지하기 위하여 Locking 이 필요한 경우 사용함 
 */

public interface LockingService extends Service {
	public boolean lock(String key);
	public boolean lock(String key, long timeout);
	
	public boolean lock(String key, Object userObject);
	public boolean lock(String key, Object userObject, long timeout);
	public boolean lock(String key, Object userObject, long timeout, long waitTimeout);
	
	public void unlock(String key);
	public Object getUserObject(String key);
}
