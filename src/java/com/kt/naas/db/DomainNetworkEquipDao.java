package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.DomainNetworkEquip;

@SuppressWarnings("unchecked")

public class DomainNetworkEquipDao extends SqlMapClientDaoSupport {
	public void insertDomainNetworkEquip(DomainNetworkEquip network)
	{
		getSqlMapClientTemplate().insert("insertDomainNetworkEquip", network);
	}
	
	public List<DomainNetworkEquip> selectDomainNetworkEquip() {
		return getSqlMapClientTemplate().queryForList("selectDomainNetworkEquip");
	}
	
	public DomainNetworkEquip selectDomainNetworkEquipById(String dnid, String neid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dnid", dnid);
		params.put("neid", neid);
		return (DomainNetworkEquip) getSqlMapClientTemplate().queryForObject("selectDomainNetworkEquipById", params);
	}
	
	public int updateDomainNetworkEquip(DomainNetworkEquip network)
	{
		return getSqlMapClientTemplate().update("updateDomainNetworkEquip", network);
	}
		
	public int deleteDomainNetworkEquipById(String dnid, String networkid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("dnid", dnid);
		params.put("neid", networkid);
		return getSqlMapClientTemplate().delete("deleteDomainNetworkEquipById", params);
	}
}
