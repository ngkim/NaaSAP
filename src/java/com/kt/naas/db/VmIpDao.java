package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.VmImage;
import com.kt.naas.domain.VmIp;

@SuppressWarnings("unchecked")

public class VmIpDao extends SqlMapClientDaoSupport {
	
	public void insertIp(VmIp ip)
	{
		getSqlMapClientTemplate().insert("insertIp", ip);
	}
	
	public VmIp selectIpById(String serverid, String ip)//, String clid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("ip", ip);
		//params.put("clid", clid);
		return (VmIp) getSqlMapClientTemplate().queryForObject("selectIpById", params);
	}
	
	public VmIp selectUnUseIpByServerId(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return (VmIp) getSqlMapClientTemplate().queryForObject("selectUnUseIpByServerId", params);
	}
	
	public int updateIp(VmIp ip)
	{
		return getSqlMapClientTemplate().update("updateIp", ip);
	}
	
	public int updateIpById(String serverid, String ip)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("ip", ip);
		return getSqlMapClientTemplate().update("updateIpById", params);
	}
	
	public int updateIpUseById(String serverid, String ip, String useyn)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("ip", ip);
		params.put("useyn", useyn);
		return getSqlMapClientTemplate().update("updateIpUseById", params);
	}
	
	public int deleteIpById(String serverid, String ip, String clid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("ip", ip);
		params.put("clid", clid);
		return getSqlMapClientTemplate().delete("deleteIpById", params);
	}
	
	public int deleteIpByServerId(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return getSqlMapClientTemplate().delete("deleteIpByServerId", params);
	}
	
	
	public void insertList(List<VmIp> list)
	{
		for (VmIp vmIp : list)
		{
			insertIp(vmIp);
		}
	}
}