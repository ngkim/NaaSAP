package com.kt.naas.domain;

public class DomainNetworkLink {
	private String linkid;		// linkid character varying(64) NOT NULL, -- Domain Network 장비간 Link ID
	private String a_neid;		// a_neid character varying(64) NOT NULL, -- A측 Domain Network 장비 ID
	private String a_portid;		// a_portid character varying(64) NOT NULL, -- A측 Domain Network Port ID
	private String z_neid;		// z_neid character varying(64) NOT NULL, -- Z측 Domain Network 장비 ID
	private String z_portid;		// z_portid character varying(64) NOT NULL, -- Z측 Domain Network Port ID
	private String vlanid;		// vlanid character varying(64), -- Link에 부여되는 Vlan ID
	private int bandwidth;		// bandwidth numeric(10,0), -- Link의 대역폭(bps)
	private String cos;			// cos character varying(2), -- Link의 CoS
	private String direction;	// direction character varying(2), -- Link의 Direction(Uni(W->E,E->W)/Bi)
	
	public String getLinkid() {
		return linkid;
	}
	
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	
	public String getA_neid() {
		return a_neid;
	}
	
	public void setA_neid(String a_neid) {
		this.a_neid = a_neid;
	}
	
	public String getA_portid() {
		return a_portid;
	}
	
	public void setA_portid(String a_portid) {
		this.a_portid = a_portid;
	}
	
	public String getZ_neid() {
		return z_neid;
	}
	
	public void setZ_neid(String z_neid) {
		this.z_neid = z_neid;
	}
	
	public String getZ_portid() {
		return z_portid;
	}
	
	public void setZ_portid(String z_portid) {
		this.z_portid = z_portid;
	}
	
	public String getVlanid() {
		return vlanid;
	}
	
	public void setVlanid(String vlanid) {
		this.vlanid = vlanid;
	}
	
	public int getBandwidth() {
		return bandwidth;
	}
	
	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public String getCos() {
		return cos;
	}
	
	public void setCos(String cos) {
		this.cos = cos;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DomainNetworkLink [linkid=");
		builder.append(linkid);
		builder.append(", a_neid=");
		builder.append(a_neid);
		builder.append(", a_portid=");
		builder.append(a_portid);
		builder.append(", z_neid=");
		builder.append(z_neid);
		builder.append(", z_portid=");
		builder.append(z_portid);
		builder.append(", vlanid=");
		builder.append(vlanid);
		builder.append(", bandwidth=");
		builder.append(bandwidth);
		builder.append(", cos=");
		builder.append(cos);
		builder.append(", direction=");
		builder.append(direction);
		builder.append("]");
		return builder.toString();
	}
	
}
