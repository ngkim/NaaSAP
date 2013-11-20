package com.kt.naas.domain;

public class Vm {
	private String	serverid;
	private String	servername;
	private String	custid;
	private String	vmid;
	private String	vmname;
	private String	localip;
	private String	publicip;
	private String	imageid;
	private String	flavorid;
	private String	key;
	private String	clid;
	private String	status;
	private String	location;
	private String	deploystatus;
	private String	deploytype;
	private String	description;
	private String	relay; // R:Relay, E:Edge
	@Override
	public String toString() {
		return String
				.format("Vm [serverid=%s, servername=%s, custid=%s, vmid=%s, vmname=%s, localip=%s, publicip=%s, imageid=%s, flavorid=%s, key=%s, clid=%s, status=%s, location=%s, deploystatus=%s, deploytype=%s, description=%s, relay=%s]",
						serverid, servername, custid, vmid, vmname, localip,
						publicip, imageid, flavorid, key, clid, status,
						location, deploystatus, deploytype, description, relay);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
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
	public String getLocalip() {
		return localip;
	}
	public void setLocalip(String localip) {
		this.localip = localip;
	}
	public String getPublicip() {
		return publicip;
	}
	public void setPublicip(String publicip) {
		this.publicip = publicip;
	}
	public String getImageid() {
		return imageid;
	}
	public void setImageid(String imageid) {
		this.imageid = imageid;
	}
	public String getFlavorid() {
		return flavorid;
	}
	public void setFlavorid(String flavorid) {
		this.flavorid = flavorid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getClid() {
		return clid;
	}
	public void setClid(String clid) {
		this.clid = clid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeploystatus() {
		return deploystatus;
	}
	public void setDeploystatus(String deploystatus) {
		this.deploystatus = deploystatus;
	}
	public String getDeploytype() {
		return deploytype;
	}
	public void setDeploytype(String deploytype) {
		this.deploytype = deploytype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRelay() {
		return relay;
	}
	public void setRelay(String relay) {
		this.relay = relay;
	}
	
}
