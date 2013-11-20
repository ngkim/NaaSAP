package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.TransportNetworkService;

@SuppressWarnings("unchecked")

public class TransportNetworkServiceDao extends SqlMapClientDaoSupport {
	public void insertTransportNetworkService(TransportNetworkService network)
	{
		getSqlMapClientTemplate().insert("insertTransportNetworkService", network);
	}
	
	public List<TransportNetworkService> selectTransportNetworkService() {
		return getSqlMapClientTemplate().queryForList("selectTransportNetworkService");
	}
	
	public TransportNetworkService selectTransportNetworkServiceById(String transvcid, String equipid, String associatedswid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("transvcid", transvcid);
		params.put("equipid", equipid);
		params.put("associatedswid", associatedswid);
		params.put("svcid", svcid);
		
		return (TransportNetworkService) getSqlMapClientTemplate().queryForObject("selectTransportNetworkServiceById", params);
	}
	
	public int updateTransportNetworkService(TransportNetworkService network)
	{
		return getSqlMapClientTemplate().update("updateTransportNetworkService", network);
	}
		
	public int deleteTransportNetworkServiceById(String transvcid, String equipid, String associatedswid, String svcid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("transvcid", transvcid);
		params.put("equipid", equipid);
		params.put("associatedswid", associatedswid);
		params.put("svcid", svcid);
		return getSqlMapClientTemplate().delete("deleteTransportNetworkServiceById", params);
	}
}
