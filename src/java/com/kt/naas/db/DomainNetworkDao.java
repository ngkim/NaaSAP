package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.DomainNetwork;

@SuppressWarnings("unchecked")

public class DomainNetworkDao extends SqlMapClientDaoSupport {
	public void insertDomainNetwork(DomainNetwork network)
	{
		getSqlMapClientTemplate().insert("insertDomainNetwork", network);
	}
	
	public List<DomainNetwork> selectDomainNetwork() {
		return getSqlMapClientTemplate().queryForList("selectDomainNetwork");
	}
	
	public DomainNetwork selectDomainNetworkById(String networkid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dnid", networkid);
		return (DomainNetwork) getSqlMapClientTemplate().queryForObject("selectDomainNetworkById", params);
	}
	
	public int updateDomainNetwork(DomainNetwork network)
	{
		return getSqlMapClientTemplate().update("updateDomainNetwork", network);
	}
		
	public int deleteDomainNetworkById(String networkid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dnid", networkid);
		return getSqlMapClientTemplate().delete("deleteDomainNetworkById", params);
	}
}
