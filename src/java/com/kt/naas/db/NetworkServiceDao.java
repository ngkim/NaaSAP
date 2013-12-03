package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.NetworkService;

@SuppressWarnings("unchecked")

public class NetworkServiceDao extends SqlMapClientDaoSupport {
	public void insertNetworkService(NetworkService network)
	{
		getSqlMapClientTemplate().insert("insertNetworkService", network);
	}
	
	public List<NetworkService> selectNetworkService() {
		return getSqlMapClientTemplate().queryForList("selectNetworkService");
	}
	
	public List<NetworkService> selectNetworkServicesDeleted() {
		return getSqlMapClientTemplate().queryForList("selectNetworkServicesDeleted");
	}
	
	public NetworkService selectNetworkServiceById(String id)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", id);
		return (NetworkService) getSqlMapClientTemplate().queryForObject("selectNetworkServiceById", params);
	}
	
	public int updateNetworkService(NetworkService network)
	{
		return getSqlMapClientTemplate().update("updateNetworkService", network);
	}
		
	public int deleteNetworkServiceById(String id)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("svcid", id);
		return getSqlMapClientTemplate().delete("deleteNetworkServiceById", params);
	}
}
