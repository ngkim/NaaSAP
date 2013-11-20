package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyDao extends SqlMapClientDaoSupport {
	public List<HashMap> getAllProperties() {
		return getSqlMapClientTemplate().queryForList("getAllProperties");
	}
	
	public String getProperty(String propertyName)
	{
		List<HashMap> list = getSqlMapClientTemplate().queryForList("getProperty", propertyName);
		if (list == null || list.size() == 0)
			return null;
		return (String)list.get(0).get("propertyValue");
	}
	
	public void insertProperty(String propertyName, String propertyValue)
	{
		HashMap<String, String> parms = new HashMap<String, String>();
 		parms.put("propertyName", propertyName);
 		parms.put("propertyValue", propertyValue);
		getSqlMapClientTemplate().insert("insertProperty", parms);
	}
	
	public int updateProperty(String propertyName, String propertyValue)
	{
		HashMap<String, String> parms = new HashMap<String, String>();
 		parms.put("propertyName", propertyName);
 		parms.put("propertyValue", propertyValue);
		return getSqlMapClientTemplate().update("updateProperty", parms);
	}
	
	public int deleteProperty(String propertyName)
	{
		return getSqlMapClientTemplate().delete("deleteProperty", propertyName);
	}
	
	public void saveProperty(String propertyName, String propertyValue)
	{
		String oldValue = getProperty(propertyName);
		if (oldValue == null)
			insertProperty(propertyName, propertyValue);
		else
			updateProperty(propertyName, propertyValue);
	}
}
