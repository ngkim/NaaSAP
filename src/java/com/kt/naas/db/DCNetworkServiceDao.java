package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.DCNetworkService;

@SuppressWarnings("unchecked")

public class DCNetworkServiceDao extends SqlMapClientDaoSupport {
	public void insertDCNetworkService(DCNetworkService network)
	{
		getSqlMapClientTemplate().insert("insertDCNetworkService", network);
	}
	
	public List<DCNetworkService> selectDCNetworkService() {
		return getSqlMapClientTemplate().queryForList("selectDCNetworkService");
	}
	
	public DCNetworkService selectDCNetworkServiceById(String dcsvcid, String connid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dcsvcid", dcsvcid);
		params.put("connid", connid);
		params.put("svcid", svcid);
		return (DCNetworkService) getSqlMapClientTemplate().queryForObject("selectDCNetworkServiceById", params);
	}
	
	public DCNetworkService selectDCNetworkServiceByTenant(String svcid, String tenantName)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", svcid);
		params.put("tenantname", tenantName);
		return (DCNetworkService) getSqlMapClientTemplate().queryForObject("selectDCNetworkServiceByTenant", params);
	}
	
	public List<DCNetworkService> selectDCNetworkServiceBySvcId(String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", svcid);
		return (List<DCNetworkService>) getSqlMapClientTemplate().queryForList("selectDCNetworkServiceBySvcId", params);
	}
	
	public int updateDCNetworkService(DCNetworkService network)
	{
		return getSqlMapClientTemplate().update("updateDCNetworkService", network);
	}
		
	public int deleteDCNetworkServiceById(String dcsvcid, String connid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dcsvcid", dcsvcid);
		params.put("connid", connid);
		params.put("svcid", svcid);
		return getSqlMapClientTemplate().delete("deleteDCNetworkServiceById", params);
	}
	
	public int deleteDCNetworkServiceBySvcId(String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", svcid);
		return getSqlMapClientTemplate().delete("deleteDCNetworkServiceBySvcId", params);
	}
}
