package com.kt.naas.domain;

import java.util.ArrayList;
import java.util.List;

public class Equip extends SnObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 443965024561001007L;

	private String equipId;
	private String equipName;
	private String emsId;
	private String snEquipId;
	private String orgCode;
	private String rcId;	
	private String masterIp;
	private String equipType;
	private String modelName;
	private String vendor;
	private int snmpVersion;
	private int snmpPort;
	private String readCommunity;
	private String writeCommunity;
	private String swVersion;
	private String hwVersion;
	private String serialNumber;
	private String gwIp;
	private String hostName;
	private String status;
	private String sNodeId;
	private long maxVodDisk;
	private String description;
	private List<String> deliveryServiceIds;
	
	public Equip()
	{
		deliveryServiceIds = new ArrayList<String>();
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getEmsId() {
		return emsId;
	}

	public void setEmsId(String emsId) {
		this.emsId = emsId;
	}

	public String getSnEquipId() {
		return snEquipId;
	}

	public void setSnEquipId(String snEquipId) {
		this.snEquipId = snEquipId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRcId() {
		return rcId;
	}

	public void setRcId(String rcId) {
		this.rcId = rcId;
	}

	public String getMasterIp() {
		return masterIp;
	}

	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public int getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
	}

	public String getReadCommunity() {
		return readCommunity;
	}

	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	public String getWriteCommunity() {
		return writeCommunity;
	}

	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	public String getSwVersion() {
		return swVersion;
	}

	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	public String getHwVersion() {
		return hwVersion;
	}

	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getGwIp() {
		return gwIp;
	}

	public void setGwIp(String gwIp) {
		this.gwIp = gwIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getsNodeId() {
		return sNodeId;
	}

	public void setsNodeId(String sNodeId) {
		this.sNodeId = sNodeId;
	}

	public long getMaxVodDisk() {
		return maxVodDisk;
	}

	public void setMaxVodDisk(long maxVodDisk) {
		this.maxVodDisk = maxVodDisk;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDeliveryServiceIds() {
		return deliveryServiceIds;
	}

	public void setDeliveryServiceIds(List<String> deliveryServiceIds) {
		this.deliveryServiceIds = deliveryServiceIds;
	}

	public String getKey()
	{
		return emsId + "_" + snEquipId; 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equip other = (Equip) obj;
		if (deliveryServiceIds == null) {
			if (other.deliveryServiceIds != null)
				return false;
		} else if (!deliveryServiceIds.equals(other.deliveryServiceIds))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (emsId == null) {
			if (other.emsId != null)
				return false;
		} else if (!emsId.equals(other.emsId))
			return false;
		if (equipId == null) {
			if (other.equipId != null)
				return false;
		} else if (!equipId.equals(other.equipId))
			return false;
		if (equipName == null) {
			if (other.equipName != null)
				return false;
		} else if (!equipName.equals(other.equipName))
			return false;
		if (equipType == null) {
			if (other.equipType != null)
				return false;
		} else if (!equipType.equals(other.equipType))
			return false;
		if (gwIp == null) {
			if (other.gwIp != null)
				return false;
		} else if (!gwIp.equals(other.gwIp))
			return false;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (hwVersion == null) {
			if (other.hwVersion != null)
				return false;
		} else if (!hwVersion.equals(other.hwVersion))
			return false;
		if (rcId == null) {
			if (other.rcId != null)
				return false;
		} else if (!rcId.equals(other.rcId))
			return false;
		if (masterIp == null) {
			if (other.masterIp != null)
				return false;
		} else if (!masterIp.equals(other.masterIp))
			return false;
		if (maxVodDisk != other.maxVodDisk)
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (readCommunity == null) {
			if (other.readCommunity != null)
				return false;
		} else if (!readCommunity.equals(other.readCommunity))
			return false;
		if (sNodeId == null) {
			if (other.sNodeId != null)
				return false;
		} else if (!sNodeId.equals(other.sNodeId))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (snEquipId == null) {
			if (other.snEquipId != null)
				return false;
		} else if (!snEquipId.equals(other.snEquipId))
			return false;
		if (snmpPort != other.snmpPort)
			return false;
		if (snmpVersion != other.snmpVersion)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (swVersion == null) {
			if (other.swVersion != null)
				return false;
		} else if (!swVersion.equals(other.swVersion))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		if (writeCommunity == null) {
			if (other.writeCommunity != null)
				return false;
		} else if (!writeCommunity.equals(other.writeCommunity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("Equip [equipId=%s, equipName=%s, emsId=%s, snEquipId=%s, orgCode=%s, rcId=%s, masterIp=%s, equipType=%s, modelName=%s, vendor=%s, snmpVersion=%s, snmpPort=%s, readCommunity=%s, writeCommunity=%s, swVersion=%s, hwVersion=%s, serialNumber=%s, gwIp=%s, hostName=%s, status=%s, sNodeId=%s, maxVodDisk=%s, description=%s, deliveryServiceIds=%s]",
						equipId, equipName, emsId, snEquipId, orgCode,
						rcId, masterIp, equipType, modelName, vendor,
						snmpVersion, snmpPort, readCommunity, writeCommunity,
						swVersion, hwVersion, serialNumber, gwIp, hostName,
						status, sNodeId, maxVodDisk, description,
						deliveryServiceIds);
	}	
	
}