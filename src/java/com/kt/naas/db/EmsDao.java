package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Ems;

@SuppressWarnings("unchecked")
public class EmsDao extends SqlMapClientDaoSupport {
	public List<Ems> selectEms() {
		return getSqlMapClientTemplate().queryForList("selectEms");
	}
	
	public List<Ems> selectEmsServiceProvisioning() {
		return getSqlMapClientTemplate().queryForList("selectEmsServiceProvisioning");
	}

	public Ems selectEmsById(String emsId) {
		return (Ems)getSqlMapClientTemplate().queryForObject("selectEmsById", emsId);
	}
	
	public void insertEms(Ems ems)
	{
		getSqlMapClientTemplate().insert("insertEms", ems);
	}
	
	public int updateEms(Ems ems)
	{
		return getSqlMapClientTemplate().update("updateEms", ems);
	}
	
	public int deleteEmsById(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().delete("deleteEmsById", params);
	}
}
