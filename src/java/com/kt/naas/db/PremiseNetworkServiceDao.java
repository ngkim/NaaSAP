package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.PremiseNetworkService;

@SuppressWarnings("unchecked")

public class PremiseNetworkServiceDao extends SqlMapClientDaoSupport {
	public void insertPremiseNetworkService(PremiseNetworkService network)
	{
		getSqlMapClientTemplate().insert("insertPremiseNetworkService", network);
	}
	
	public List<PremiseNetworkService> selectPremiseNetworkService() {
		return getSqlMapClientTemplate().queryForList("selectPremiseNetworkService");
	}
	
	public PremiseNetworkService selectPremiseNetworkServiceById(String cpsvcid, String connid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("cpsvcid", cpsvcid);
		params.put("connid", connid);
		params.put("svcid", svcid);
		return (PremiseNetworkService) getSqlMapClientTemplate().queryForObject("selectPremiseNetworkServiceById", params);
	}
	
	public PremiseNetworkService selectPremiseNetworkServiceByTenantName(String svcid, String tenantname, String nwName)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", svcid);
		params.put("tenantname", tenantname);
		params.put("nwname", nwName);
		return (PremiseNetworkService) getSqlMapClientTemplate().queryForObject("selectPremiseNetworkServiceByTenantName", params);
	}
	
	public int updatePremiseNetworkService(PremiseNetworkService network)
	{
		return getSqlMapClientTemplate().update("updatePremiseNetworkService", network);
	}
		
	public int deletePremiseNetworkServiceById(String cpsvcid, String connid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("cpsvcid", cpsvcid);
		params.put("connid", connid);
		params.put("svcid", svcid);
		return getSqlMapClientTemplate().delete("deletePremiseNetworkServiceById", params);
	}
}
