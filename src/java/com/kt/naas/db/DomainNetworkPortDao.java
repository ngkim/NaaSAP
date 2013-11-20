package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.DomainNetworkPort;

@SuppressWarnings("unchecked")

public class DomainNetworkPortDao extends SqlMapClientDaoSupport {
	public void insertDomainNetworkPort(DomainNetworkPort network)
	{
		getSqlMapClientTemplate().insert("insertDomainNetworkPort", network);
	}
	
	public List<DomainNetworkPort> selectDomainNetworkPort() {
		return getSqlMapClientTemplate().queryForList("selectDomainNetworkPort");
	}
	
	public DomainNetworkPort selectDomainNetworkPortById(String portid, String neid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("portid", portid);
		params.put("neid", neid);
		return (DomainNetworkPort) getSqlMapClientTemplate().queryForObject("selectDomainNetworkPortById", params);
	}
	
	public int updateDomainNetworkPort(DomainNetworkPort network)
	{
		return getSqlMapClientTemplate().update("updateDomainNetworkPort", network);
	}
		
	public int deleteDomainNetworkPortById(String portid, String neid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("portid", portid);
		params.put("neid", neid);
		return getSqlMapClientTemplate().delete("deleteDomainNetworkPortById", params);
	}
}
