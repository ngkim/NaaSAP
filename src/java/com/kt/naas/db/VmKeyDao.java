package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Vm;
import com.kt.naas.domain.VmIp;
import com.kt.naas.domain.VmKey;

@SuppressWarnings("unchecked")

public class VmKeyDao extends SqlMapClientDaoSupport {
	
	public void insertKey(VmKey key)
	{
		getSqlMapClientTemplate().insert("insertKey", key);
	}
	
	public VmKey selectKeyById(String serverid, String keyid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("keyid", keyid);
		return (VmKey) getSqlMapClientTemplate().queryForObject("selectKeyById", params);
	}
	
	public VmKey selectKeyByServerId(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return (VmKey) getSqlMapClientTemplate().queryForObject("selectKeyByServerId", params);
	}
	
	public int updateKey(VmKey key)
	{
		return getSqlMapClientTemplate().update("updateKey", key);
	}
	
	public int deleteKeyById(String serverid, String keyid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("keyid", keyid);
		return getSqlMapClientTemplate().delete("deleteKeyById", params);
	}
	
	public int deleteKeyByServerId(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return getSqlMapClientTemplate().delete("deleteKeyByServerId", params);
	}
}