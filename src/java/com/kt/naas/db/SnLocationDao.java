package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.SnLocation;

@SuppressWarnings("unchecked")
public class SnLocationDao extends SqlMapClientDaoSupport {

	public List<SnLocation> selectSnLocation()
	{
		return getSqlMapClientTemplate().queryForList("selectSnLocation");
	}
	
	public List<SnLocation> selectSnLocationByEmsId(String emsId)
	{
		return getSqlMapClientTemplate().queryForList("selectSnLocationByEmsId", emsId);
	}
	
	public SnLocation selectSnLocationById(String emsId, String snLocationId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snLocationId", snLocationId);
		return (SnLocation) getSqlMapClientTemplate().queryForObject("selectSnLocationById", params);
	}
	
	public void insertSnLocation(SnLocation location)
	{
		getSqlMapClientTemplate().insert("insertSnLocation", location);
	}
	
	public int updateSnLocation(SnLocation location)
	{
		return getSqlMapClientTemplate().update("updateSnLocation", location);
	}
	
	public int updateSnLocationWorkingStatusByEmsId(String emsId)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().update("updateSnLocationWorkingStatusByEmsId", params);
	}
	
	public int updateSnLocationWorkingStatusById(String emsId, String snLocatonId, String locationName, String parentId, String levelNum)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snLocationId", snLocatonId);
		params.put("locationName", locationName);
		params.put("parentId", parentId);
		params.put("levelNum", levelNum);
		return getSqlMapClientTemplate().update("updateSnLocationWorkingStatusById", params);
	}
	
	public int deleteSnLocationById(String emsId, String locationId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("locationId", locationId);
		return getSqlMapClientTemplate().delete("deleteSnLocationById", params);
	}
	
	public int deleteSnLocationWorkingStatusByEmsId(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().delete("deleteSnLocationWorkingStatusByEmsId", params);
	}
	
	
	
	
	
	
	
	
	//---------플랫폼 내부에서 관리하는 테이블 처리
	public SnLocation selectLocationById(String emsId, String locationId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("locationId", locationId);
		return (SnLocation) getSqlMapClientTemplate().queryForObject("selectLocationById", params);
	}

	public SnLocation selectLocationBySnId(String emsId, String snLocationId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snLocationId", snLocationId);
		return (SnLocation) getSqlMapClientTemplate().queryForObject("selectLocationBySnId", params);
	}
	
	public void insertLocation(SnLocation location)
	{
		getSqlMapClientTemplate().insert("insertLocation", location);
	}
	
	public void insertLocationByAutoDiscovery(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		getSqlMapClientTemplate().insert("insertLocationByAutoDiscovery", params);
	}
	
	public int updateLocation(SnLocation location)
	{
		return getSqlMapClientTemplate().update("updateLocation", location);
	}
	
	public int deleteLocationById(String emsId, String locationId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("locationId", locationId);
		return getSqlMapClientTemplate().delete("deleteLocationById", params);
	}
	
	public int deleteLocationByAutoDiscovery(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().delete("deleteLocationByAutoDiscovery", params);
	}
	
	public SnLocation  selectLocationSeq(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return (SnLocation) getSqlMapClientTemplate().queryForObject("selectLocationSeq", params);
	}
	
	
	//-------- 이후부터는 배치작업을 위하여 구현해야 함
	public List<SnLocation> selectAll()
	{
		return selectSnLocation();
	}
	
	public void insertList(List<SnLocation> list)
	{
		for (SnLocation location : list)
		{
			insertSnLocation(location);
		}
	}
	
	public void updateList(List<SnLocation> list)
	{
		for (SnLocation location : list)
		{
			updateSnLocation(location);
		}
	}
	
	public void deleteList(List<SnLocation> list)
	{
		for (SnLocation location : list)
		{
			deleteSnLocationById(location.getEmsId(), location.getLocationId());
		}
	}

}