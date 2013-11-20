package com.kt.naas.domain;

import java.io.Serializable;

import com.kt.naas.GlobalConstants;

public class TargetResource implements GlobalConstants, Serializable {
	private static final long serialVersionUID = -5874419953837243231L;

	private String	emsId = "";
	private String	equipId = "";
	private String 	equipNm = "";
	private String 	snEquipId = "";
	private String	masterIp = "";
	private int		snmpPort = 161;
	private String	community = "public";
	private int		targetIndex = 0;
	
	@Override
	public String toString() {
		return String
				.format("TargetResource [emsId=%s, equipId=%s, equipNm=%s, snEquipId=%s, masterIp=%s, snmpPort=%s, community=%s, targetIndex=%s]",
						emsId, equipId, masterIp, snmpPort, community, targetIndex);
	}
	
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
	
	public String getEquipNm() {
		return equipNm;
	}
	public void setEquipNm(String equipNm) {
		this.equipNm = equipNm;
	}
	
	public String getSnEquipId() {
		return snEquipId;
	}
	public void setSnEquipId(String snEquipId) {
		this.snEquipId = snEquipId;
	}
	
	public String getMasterIp() {
		return masterIp;
	}
	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
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
	public int getTargetIndex() {
		return targetIndex;
	}
	public void setTargetIndex(int index) {
		this.targetIndex = index;
	}
}
