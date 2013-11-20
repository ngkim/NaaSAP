package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Server;

@SuppressWarnings("unchecked")

public class ServerDao extends SqlMapClientDaoSupport {
	
	public void insertServer(Server server)
	{
		getSqlMapClientTemplate().insert("insertServer", server);
	}
	
	public List<Server> selectServer() {
		return getSqlMapClientTemplate().queryForList("selectServer");
	}
	
	public Server selectServerById(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return (Server) getSqlMapClientTemplate().queryForObject("selectServerById", params);
	}
	

	
	public int updateServer(Server server)
	{
		return getSqlMapClientTemplate().update("updateServer", server);
	}
	

	
	public int deleteServerById(String serverid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("serverid", serverid);
		return getSqlMapClientTemplate().delete("deleteServerById", params);
	}
}
