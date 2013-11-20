package com.kt.naas.service;

/*
 * ���ý����� �����ϱ� ���Ͽ� Locking �� �ʿ��� ��� ����� 
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
