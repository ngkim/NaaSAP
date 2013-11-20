package com.kt.naas.domain;

public class SnmpSetting {
	private String	emsId;
	private String	equipId;
	private int		snmpPort;
	private String	community;
	
	public String getEmsId() {
		return emsId;
	}
	public void setEmsId(String emsId) {
		this.emsId = emsId;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public int getSnmpPort() {
		return snmpPort;
	}
	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	
	@Override
	public String toString() {
		return String
				.format("SnmpSetting [emsId=%s, equipId=%s, snmpPort=%s, community=%s]",
						emsId, equipId, snmpPort, community);
	}
}
