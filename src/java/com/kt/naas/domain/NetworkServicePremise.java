package com.kt.naas.domain;

public class NetworkServicePremise {
	// CONSTRAINT t_nw_service_premise_pk PRIMARY KEY (cpsvcid, connid, svcid)
	private String cpsvcid; // cpsvcid character varying(11) NOT NULL, --
							// Premise NW connection에 대한 식별자(Premise SDN에서 생성)
	private String connid; // connid character varying(64) NOT NULL, -- Network
							// 내 연결 ID
	private String svcid; // svcid character varying(64) NOT NULL, -- 소속 서비스
							// ID(t_nw_service 테이블 svcid)
	private String nwname; // nwname character varying(64), -- Premise Network 명
	private String tenantid; // tenantid character varying(64), -- Tenant ID
	private String tenantname; // tenantname character varying(64), -- Tenant 명
	private String subnet; // subnet character varying(18), -- Subnet

	private String transequipid; // transequipid character varying(64), -- 연결된
									// Transport 장비 ID
	private String transequipportid; // transequipportid character varying(64),
										// -- 연결된 Transport 장비 Port ID

	private String aggrid; // aggrid character varying(64), -- Aggregate SW ID
	private String aggrname; // aggrname character varying(64), -- Aggregate SW
								// 명
	private String aggrip; // aggrip character varying(15), -- Aggregate SW IP
							// Address
	private String aggrupportid; // aggrupportid character varying(64), --
									// Aggregate SW up port ID
	private String aggrdownportid; // aggrdownportid character varying(64), --
									// Aggregate SW down port ID
	private String aggrvlanid; // aggrvlanid character varying(64), -- Aggregate
								// SW Vlan ID
	private int aggrbw; // aggrbw numeric(10,0), -- Aggregate SW 대역폭(bps)

	private String l2id; // l2id character varying(64), -- L2 SW ID
	private String l2name; // l2name character varying(64), -- L2 SW 명
	private String l2ip; // l2ip character varying(15), -- L2 SW IP Address
	private String l2upportid; // l2upportid character varying(64), -- L2 SW up
								// port ID
	private String l2downportid; // l2downportid character varying(64), -- L2 SW
									// down port ID
	private String l2vlanid; // l2vlanid character varying(64), -- L2 SW Vlan ID
	private int l2bw; // l2bw numeric(10,0), -- L2 SW 대역폭(bps)

	private String endpointid; // endpointid character varying(64), -- Endpoint
								// SW ID
	private String endpointname; // endpointname character varying(64), --
									// Endpoint SW 명
	private String endpointip; // endpointip character varying(15), -- Endpoint
								// SW IP Address
	private String endpointupportid; // endpointupportid character varying(64),
										// -- Endpoint SW up port ID
	private String endpointvlanid; // endpointvlanid character varying(64), --
									// Endpoint SW Vlan ID
	private String endpointdownportid; // endpointdownportid character
										// varying(64), -- Endpoint SW up port
										// ID
	private int endpointbw; // endpointbw numeric(10,0), -- Endpoint SW 대역폭(bps)

	private int status; // status numeric(1,0) DEFAULT 0, -- 처리 결과 코드(-1:fail,
						// 0:성공, 1:진행중)
	private String resultmsg; // resultmsg character varying(128), -- 처리 결과 메시지

	public String getCpsvcid() {
		return cpsvcid;
	}

	public void setCpsvcid(String cpsvcid) {
		this.cpsvcid = cpsvcid;
	}

	public String getConnid() {
		return connid;
	}

	public void setConnid(String connid) {
		this.connid = connid;
	}

	public String getSvcid() {
		return svcid;
	}

	public void setSvcid(String svcid) {
		this.svcid = svcid;
	}

	public String getNwname() {
		return nwname;
	}

	public void setNwname(String nwname) {
		this.nwname = nwname;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getTransequipid() {
		return transequipid;
	}

	public void setTransequipid(String transequipid) {
		this.transequipid = transequipid;
	}

	public String getTransequipportid() {
		return transequipportid;
	}

	public void setTransequipportid(String transequipportid) {
		this.transequipportid = transequipportid;
	}

	public String getAggrid() {
		return aggrid;
	}

	public void setAggrid(String aggrid) {
		this.aggrid = aggrid;
	}

	public String getAggrname() {
		return aggrname;
	}

	public void setAggrname(String aggrname) {
		this.aggrname = aggrname;
	}

	public String getAggrip() {
		return aggrip;
	}

	public void setAggrip(String aggrip) {
		this.aggrip = aggrip;
	}

	public String getAggrupportid() {
		return aggrupportid;
	}

	public void setAggrupportid(String aggrupportid) {
		this.aggrupportid = aggrupportid;
	}

	public String getAggrdownportid() {
		return aggrdownportid;
	}

	public void setAggrdownportid(String aggrdownportid) {
		this.aggrdownportid = aggrdownportid;
	}

	public String getAggrvlanid() {
		return aggrvlanid;
	}

	public void setAggrvlanid(String aggrvlanid) {
		this.aggrvlanid = aggrvlanid;
	}

	public int getAggrbw() {
		return aggrbw;
	}

	public void setAggrbw(int aggrbw) {
		this.aggrbw = aggrbw;
	}

	public String getL2id() {
		return l2id;
	}

	public void setL2id(String l2id) {
		this.l2id = l2id;
	}

	public String getL2name() {
		return l2name;
	}

	public void setL2name(String l2name) {
		this.l2name = l2name;
	}

	public String getL2ip() {
		return l2ip;
	}

	public void setL2ip(String l2ip) {
		this.l2ip = l2ip;
	}

	public String getL2upportid() {
		return l2upportid;
	}

	public void setL2upportid(String l2upportid) {
		this.l2upportid = l2upportid;
	}

	public String getL2downportid() {
		return l2downportid;
	}

	public void setL2downportid(String l2downportid) {
		this.l2downportid = l2downportid;
	}

	public String getL2vlanid() {
		return l2vlanid;
	}

	public void setL2vlanid(String l2vlanid) {
		this.l2vlanid = l2vlanid;
	}

	public int getL2bw() {
		return l2bw;
	}

	public void setL2bw(int l2bw) {
		this.l2bw = l2bw;
	}

	public String getEndpointid() {
		return endpointid;
	}

	public void setEndpointid(String endpointid) {
		this.endpointid = endpointid;
	}

	public String getEndpointname() {
		return endpointname;
	}

	public void setEndpointname(String endpointname) {
		this.endpointname = endpointname;
	}

	public String getEndpointip() {
		return endpointip;
	}

	public void setEndpointip(String endpointip) {
		this.endpointip = endpointip;
	}

	public String getEndpointupportid() {
		return endpointupportid;
	}

	public void setEndpointupportid(String endpointupportid) {
		this.endpointupportid = endpointupportid;
	}

	public String getEndpointvlanid() {
		return endpointvlanid;
	}

	public void setEndpointvlanid(String endpointvlanid) {
		this.endpointvlanid = endpointvlanid;
	}

	public String getEndpointdownportid() {
		return endpointdownportid;
	}

	public void setEndpointdownportid(String endpointdownportid) {
		this.endpointdownportid = endpointdownportid;
	}

	public int getEndpointbw() {
		return endpointbw;
	}

	public void setEndpointbw(int endpointbw) {
		this.endpointbw = endpointbw;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

}
