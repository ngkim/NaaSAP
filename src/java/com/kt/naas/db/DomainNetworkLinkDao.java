package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.DomainNetworkLink;

@SuppressWarnings("unchecked")

public class DomainNetworkLinkDao extends SqlMapClientDaoSupport {
	public void insertDomainNetworkLink(DomainNetworkLink network)
	{
		getSqlMapClientTemplate().insert("insertDomainNetworkLink", network);
	}
	
	public List<DomainNetworkLink> selectDomainNetworkLink() {
		return getSqlMapClientTemplate().queryForList("selectDomainNetworkLink");
	}
	
	public DomainNetworkLink selectDomainNetworkLinkById(String linkid, String a_neid, String a_portid, String z_neid, String z_portid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("linkid", linkid);
		params.put("a_neid", a_neid);
		params.put("a_portid", a_portid);
		params.put("z_neid", z_neid);
		params.put("z_portid", z_portid);
		return (DomainNetworkLink) getSqlMapClientTemplate().queryForObject("selectDomainNetworkLinkById", params);
	}
	
	public int updateDomainNetworkLink(DomainNetworkLink network)
	{
		return getSqlMapClientTemplate().update("updateDomainNetworkLink", network);
	}
		
	public int deleteDomainNetworkLinkById(String linkid, String a_neid, String a_portid, String z_neid, String z_portid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("linkid", linkid);
		params.put("a_neid", a_neid);
		params.put("a_portid", a_portid);
		params.put("z_neid", z_neid);
		params.put("z_portid", z_portid);
		return getSqlMapClientTemplate().delete("deleteDomainNetworkLinkById", params);
	}
}
