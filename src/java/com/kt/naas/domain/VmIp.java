package com.kt.naas.domain;

public class VmIp {
	private String	serverid;
	private String	ip;
	private String	clid;
	private String	useyn;
	@Override
	public String toString() {
		return String.format("VmIp [serverid=%s, ip=%s, clid=%s, useyn=%s]",
				serverid, ip, clid, useyn);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getClid() {
		return clid;
	}
	public void setClid(String clid) {
		this.clid = clid;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	
}
