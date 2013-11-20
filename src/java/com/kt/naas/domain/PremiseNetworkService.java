package com.kt.naas.domain;

public class PremiseNetworkService {

	// cpsvcid character varying(11) NOT NULL, -- Premise NW connection에 대한
	// 식별자(Premise SDN에서 생성)
	// connid character varying(64) NOT NULL, -- Network 내 연결 ID
	// svcid character varying(64) NOT NULL, -- 소속 서비스 ID(t_nw_service 테이블
	// svcid)

	// CONSTRAINT t_nw_service_premise_pk PRIMARY KEY (cpsvcid, connid, svcid)
	
	private String cpsvcid;
	private String connid;
	private String svcid;

	// nwname character varying(64), -- Premise Network 명
	// subnet character varying(18), -- Subnet

	private String nwname;
	private String subnet;
	private String addr;

	// tenantid character varying(64), -- Tenant ID
	// tenantname character varying(64), -- Tenant 명

	private String tenantid;
	private String tenantname;

	// transequipid character varying(64), -- 연결된 Transport 장비 ID
	// transequipportid character varying(64), -- 연결된 Transport 장비 Port ID

	private String transequipid;
	private String transequipportid;

	// aggrid character varying(64), -- Aggregate SW ID
	// aggrname character varying(64), -- Aggregate SW 명
	// aggrip character varying(15), -- Aggregate SW IP Address
	// aggrupportid character varying(64), -- Aggregate SW up port ID
	// aggrdownportid character varying(64), -- Aggregate SW down port ID
	// aggrbw numeric(10,0), -- Aggregate SW 대역폭(bps)
	// aggrvlanid character varying(64), -- Aggregate SW Vlan ID

	private String aggrid;
	private String aggrname;
	private String aggrip;
	private String aggrupportid;
	private String aggrdownportid;
	private int aggrbw;
	private String aggrvlanid;

	// l2id character varying(64), -- L2 SW ID
	// l2name character varying(64), -- L2 SW 명
	// l2ip character varying(15), -- L2 SW IP Address
	// l2upportid character varying(64), -- L2 SW up port ID
	// l2downportid character varying(64), -- L2 SW down port ID
	// l2bw numeric(10,0), -- L2 SW 대역폭(bps)
	// l2vlanid character varying(64), -- L2 SW Vlan ID

	private String l2id;
	private String l2name;
	private String l2ip;
	private String l2upportid;
	private String l2downportid;
	private int l2bw;
	private String l2vlanid;

	// endpointid character varying(64), -- Endpoint SW ID
	// endpointname character varying(64), -- Endpoint SW 명
	// endpointip character varying(15), -- Endpoint SW IP Address
	// endpointupportid character varying(64), -- Endpoint SW up port ID
	// endpointbw numeric(10,0), -- Endpoint SW 대역폭(bps)
	// endpointvlanid character varying(64), -- Endpoint SW Vlan ID

	private String endpointid;
	private String endpointname;
	private String endpointip;
	private String endpointupportid;
	private String endpointdownportid;
	private int endpointbw;
	private String endpointvlanid;

	// status numeric(1,0) DEFAULT 0, -- 처리 결과 코드(-1:fail, 0:성공, 1:진행중)
	// resultmsg character varying(128), -- 처리 결과 메시지

	private int status;
	private String resultmsg;

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

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
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

	public int getAggrbw() {
		return aggrbw;
	}

	public void setAggrbw(int aggrbw) {
		this.aggrbw = aggrbw;
	}

	public String getAggrvlanid() {
		return aggrvlanid;
	}

	public void setAggrvlanid(String aggrvlanid) {
		this.aggrvlanid = aggrvlanid;
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

	public int getL2bw() {
		return l2bw;
	}

	public void setL2bw(int l2bw) {
		this.l2bw = l2bw;
	}

	public String getL2vlanid() {
		return l2vlanid;
	}

	public void setL2vlanid(String l2vlanid) {
		this.l2vlanid = l2vlanid;
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

	public String getEndpointvlanid() {
		return endpointvlanid;
	}

	public void setEndpointvlanid(String endpointvlanid) {
		this.endpointvlanid = endpointvlanid;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
}
