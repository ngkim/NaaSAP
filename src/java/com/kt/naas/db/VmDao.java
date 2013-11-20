package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Vm;
import com.kt.naas.domain.VmConfigInfo;

@SuppressWarnings("unchecked")

public class VmDao extends SqlMapClientDaoSupport {
	
	public void insertVm(Vm vm)
	{
		getSqlMapClientTemplate().insert("insertVm", vm);
	}
	
	public Vm selectVmById(String serverid, String custid, String vmid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("custid", custid);
		params.put("vmid", vmid);
		return (Vm) getSqlMapClientTemplate().queryForObject("selectVmById", params);
	}
	

	
	public int updateVm(Vm vm)
	{
		return getSqlMapClientTemplate().update("updateVm", vm);
	}
	

	
	public int deleteVmById(String serverid, String custid, String vmid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("custid", custid);
		params.put("vmid", vmid);
		return getSqlMapClientTemplate().delete("deleteVmById", params);
	}
	
	
	
	
	// VmConfigInfo
	public void insertVmConfigInfo(VmConfigInfo vmConfigInfo)
	{
		getSqlMapClientTemplate().insert("insertVmConfigInfo", vmConfigInfo);
	}
	
	public VmConfigInfo selectVmConfigInfoById(String serverid, String custid, String vmid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("custid", custid);
		params.put("vmid", vmid);
		return (VmConfigInfo) getSqlMapClientTemplate().queryForObject("selectVmById", params);
	}
	
	public int updateVmConfigInfo(VmConfigInfo vmConfigInfo)
	{
		return getSqlMapClientTemplate().update("updateVmConfigInfo", vmConfigInfo);
	}
	
	public int updateVmStatusById(String serverid, String custid, String vmid,
			String status, String location, String deploystatus, String deploytype, String relay)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("custid", custid);
		params.put("vmid", vmid);		

		params.put("status", status);
		params.put("location", location);
		params.put("deploystatus", deploystatus);
		params.put("deploytype", deploytype);
		params.put("relay", relay);
		return getSqlMapClientTemplate().update("vmid", params);
	}
	
	public int deleteVmConfigInfoById(String serverid, String custid, String vmid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		params.put("custid", custid);
		params.put("vmid", vmid);
		return getSqlMapClientTemplate().delete("deleteVmConfigInfoById", params);
	}
	
	
	
	
	
}
