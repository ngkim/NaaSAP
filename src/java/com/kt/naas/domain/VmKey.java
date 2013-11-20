package com.kt.naas.domain;

public class VmKey {
	private String	serverid;
	private String	keyid;
	private String	key;
	@Override
	public String toString() {
		return String.format("VmKey [serverid=%s, keyid=%s, key=%s]", serverid,
				keyid, key);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
