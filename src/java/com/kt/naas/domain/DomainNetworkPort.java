package com.kt.naas.domain;

public class DomainNetworkPort {
	private String portid; 		// portid character varying(64) NOT NULL, -- 장비 Port ID
	private String neid;			// neid character varying(64) NOT NULL, -- 장비 ID
	private String portname;		// portname character varying(128), -- Port 명
	private String porttype; 	// porttype character varying(2), -- Port Type-Type 별 코드 정의
	
	public String getPortid() {
		return portid;
	}
	
	public void setPortid(String portid) {
		this.portid = portid;
	}
	
	public String getNeid() {
		return neid;
	}
	
	public void setNeid(String neid) {
		this.neid = neid;
	}
	
	public String getPortname() {
		return portname;
	}
	
	public void setPortname(String portname) {
		this.portname = portname;
	}
	
	public String getPorttype() {
		return porttype;
	}
	
	public void setPorttype(String porttype) {
		this.porttype = porttype;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DomainNetworkPort [portid=");
		builder.append(portid);
		builder.append(", neid=");
		builder.append(neid);
		builder.append(", portname=");
		builder.append(portname);
		builder.append(", porttype=");
		builder.append(porttype);
		builder.append("]");
		return builder.toString();
	}
	
}
