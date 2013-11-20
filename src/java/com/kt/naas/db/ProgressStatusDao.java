package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.ProgressStatus;

@SuppressWarnings("unchecked")

public class ProgressStatusDao extends SqlMapClientDaoSupport {
	public void insertProgressStatus(ProgressStatus network)
	{
		getSqlMapClientTemplate().insert("insertProgressStatus", network);
	}
	
	public List<ProgressStatus> selectProgressStatus() {
		return getSqlMapClientTemplate().queryForList("selectProgressStatus");
	}
	
	public ProgressStatus selectProgressStatusById(String custid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custid", custid);
		return (ProgressStatus) getSqlMapClientTemplate().queryForObject("selectProgressStatusById", params);
	}
	
	public int updateProgressStatus(ProgressStatus network)
	{
		return getSqlMapClientTemplate().update("updateProgressStatus", network);
	}
		
	public int deleteProgressStatusById(String custid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custid", custid);
		return getSqlMapClientTemplate().delete("deleteProgressStatusById", params);
	}
}
