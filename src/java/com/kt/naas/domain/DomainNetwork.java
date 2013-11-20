package com.kt.naas.domain;

public class DomainNetwork {
	private String dnid; 		// character varying(64) NOT NULL, -- Domain NW ID(=DC/TRANS/CP SVC ID)
	private String dnname;
	private String dntype;		// character varying(2), -- DC/TRANS/PREMISE NW (DC:DC, TR:TRANS, PR:PREMISE)
	private String rootyn;		// rootyn character varying(1), -- Tree 형태에서 Root Node 여부(Y/N)
	private String conntype;		// conntype character varying(16), -- E-Line/E-LAN/E-TREE SVC Type
	
	public String getDnid() {
		return dnid;
	}
	
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	
	public String getDnname() {
		return dnname;
	}

	public void setDnname(String dnname) {
		this.dnname = dnname;
	}

	public String getDntype() {
		return dntype;
	}
	
	public void setDntype(String dntype) {
		this.dntype = dntype;
	}
	
	public String getRootyn() {
		return rootyn;
	}
	
	public void setRootyn(String rootyn) {
		this.rootyn = rootyn;
	}
	
	public String getConntype() {
		return conntype;
	}
	
	public void setConntype(String conntype) {
		this.conntype = conntype;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("DomainNetwork [dnid=");
		builder.append(dnid);
		builder.append(", dntype=");
		builder.append(dntype);
		builder.append(", rootyn=");
		builder.append(rootyn);
		builder.append(", conntype=");
		builder.append(conntype);
		builder.append("]");
		
		return builder.toString();
	}
		  
}
