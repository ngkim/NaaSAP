package com.kt.naas.domain;

public class DomainNetworkEquip {
	private String neid; // neid character varying(64) NOT NULL, -- 장비 ID
	private String dnid; // dnid character varying(64) NOT NULL, -- Domain NW ID
	private String nename; // nename character varying(64), -- 장비 명
	private String netype; // netype character varying(4), -- 장비 타입
	
	public String getNeid() {
		return neid;
	}
	
	public void setNeid(String neid) {
		this.neid = neid;
	}
	
	public String getDnid() {
		return dnid;
	}
	
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	
	public String getNename() {
		return nename;
	}
	
	public void setNename(String nename) {
		this.nename = nename;
	}
	
	public String getNetype() {
		return netype;
	}
	
	public void setNetype(String netype) {
		this.netype = netype;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DomainNetworkEquip [neid=");
		builder.append(neid);
		builder.append(", dnid=");
		builder.append(dnid);
		builder.append(", nename=");
		builder.append(nename);
		builder.append(", netype=");
		builder.append(netype);
		builder.append("]");
		return builder.toString();
	}
	
}
