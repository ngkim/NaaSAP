package com.kt.naas.domain;

public class Server {
	private String	serverid;
	private String	servername;
	private String	masterip;
	private int	port;
	private String	model;
	private String	serveros;
	private String	hostname;
	private String	servicetype;
	private String	status;
	private String	userid;
	private String	passwd;
	private String	description;
	@Override
	public String toString() {
		return String
				.format("Server [serverid=%s, servername=%s, masterip=%s, port=%s, model=%s, serveros=%s, hostname=%s, servicetype=%s, status=%s, userid=%s, passwd=%s, description=%s]",
						serverid, servername, masterip, port, model, serveros,
						hostname, servicetype, status, userid, passwd,
						description);
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
	public String getMasterip() {
		return masterip;
	}
	public void setMasterip(String masterip) {
		this.masterip = masterip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getServeros() {
		return serveros;
	}
	public void setServeros(String serveros) {
		this.serveros = serveros;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
