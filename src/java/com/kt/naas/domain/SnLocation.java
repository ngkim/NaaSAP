package com.kt.naas.domain;

import java.util.ArrayList;
import java.util.List;

public class SnLocation extends SnObject {
	
	private static final long serialVersionUID = -4357514655527786425L;
	
	private String emsId;
	private String locationId;
	private String snLocationId;
	private String locationName;
	private String parentId;
	private String parentLocationName;
	private String orgCode;
	private String levelNum;
	private String leaderId;
	private String description;
	private List<String> serviceEngineIds;
	
	public SnLocation()
	{
		serviceEngineIds = new ArrayList<String>();
	}
	
	public String getEmsId() {
		return emsId;
	}

	public void setEmsId(String emsId) {
		this.emsId = emsId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSnLocationId() {
		return snLocationId;
	}

	public void setSnLocationId(String snLocationId) {
		this.snLocationId = snLocationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentLocationName() {
		return parentLocationName;
	}

	public void setParentLocationName(String parentLocationName) {
		this.parentLocationName = parentLocationName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getServiceEngineIds() {
		return serviceEngineIds;
	}

	public void setServiceEngineIds(List<String> serviceEngineIds) {
		this.serviceEngineIds = serviceEngineIds;
	}

	public void addServiceEngineId(String serviceEngineId)
	{
		serviceEngineIds.add(serviceEngineId);
	}
	
	public void addServiceEngineIds(SnList serviceEngineList)
	{
		if ("SNodeConfig".equals(serviceEngineList.getType()))
		{
			for (SnEntry entry : serviceEngineList.getEntries())
				addServiceEngineId(entry.getId());
		}
	}

	public String getKey()
	{
		return emsId + "_" + snLocationId; 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnLocation other = (SnLocation) obj;
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
		if (leaderId == null) {
			if (other.leaderId != null)
				return false;
		} else if (!leaderId.equals(other.leaderId))
			return false;
		if (levelNum == null) {
			if (other.levelNum != null)
				return false;
		} else if (!levelNum.equals(other.levelNum))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (locationName == null) {
			if (other.locationName != null)
				return false;
		} else if (!locationName.equals(other.locationName))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (parentLocationName == null) {
			if (other.parentLocationName != null)
				return false;
		} else if (!parentLocationName.equals(other.parentLocationName))
			return false;
		if (serviceEngineIds == null) {
			if (other.serviceEngineIds != null)
				return false;
		} else if (!serviceEngineIds.equals(other.serviceEngineIds))
			return false;
		if (snLocationId == null) {
			if (other.snLocationId != null)
				return false;
		} else if (!snLocationId.equals(other.snLocationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("SnLocation [emsId=%s, locationId=%s, snLocationId=%s, locationName=%s, parentId=%s, parentLocationName=%s, orgCode=%s, levelNum=%s, leaderId=%s, description=%s, serviceEngineIds=%s]",
						emsId, locationId, snLocationId, locationName,
						parentId, parentLocationName, orgCode, levelNum,
						leaderId, description, serviceEngineIds);
	}
	
}
