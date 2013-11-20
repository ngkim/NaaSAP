package com.kt.naas.domain;

public class VmConfigInfo {
	private String	serverid;
	private String	custid;
	private String	vmid;
	private String	vmname;
	private String	originid;
	private String	originip;
	private String	domain;
	private String	relayid;
	private String	relayip;
	private String	edgeid;
	private String	edgeip;
	private String	location; //서비스 위치(local/global)
	@Override
	public String toString() {
		return String
				.format("VmConfigInfo [serverid=%s, custid=%s, vmid=%s, vmname=%s, originid=%s, originip=%s, domain=%s, relayid=%s, relayip=%s, edgeid=%s, edgeip=%s, location=%s]",
						serverid, custid, vmid, vmname, originid, originip,
						domain, relayid, relayip, edgeid, edgeip, location);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getVmid() {
		return vmid;
	}
	public void setVmid(String vmid) {
		this.vmid = vmid;
	}
	public String getVmname() {
		return vmname;
	}
	public void setVmname(String vmname) {
		this.vmname = vmname;
	}
	public String getOriginid() {
		return originid;
	}
	public void setOriginid(String originid) {
		this.originid = originid;
	}
	public String getOriginip() {
		return originip;
	}
	public void setOriginip(String originip) {
		this.originip = originip;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRelayid() {
		return relayid;
	}
	public void setRelayid(String relayid) {
		this.relayid = relayid;
	}
	public String getRelayip() {
		return relayip;
	}
	public void setRelayip(String relayip) {
		this.relayip = relayip;
	}
	public String getEdgeid() {
		return edgeid;
	}
	public void setEdgeid(String edgeid) {
		this.edgeid = edgeid;
	}
	public String getEdgeip() {
		return edgeip;
	}
	public void setEdgeip(String edgeip) {
		this.edgeip = edgeip;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
