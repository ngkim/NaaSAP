package com.kt.naas.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.SnmpSetting;

public class SnmpSettingDao extends SqlMapClientDaoSupport {
	public SnmpSetting selectSnmpSettingById(String emsId, String equipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		
		return (SnmpSetting)getSqlMapClientTemplate().queryForObject("selectSnmpSettingById", params);
	}	

	public SnmpSetting selectSnmpSettingFromEquipById(String emsId, String equipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		
		return (SnmpSetting)getSqlMapClientTemplate().queryForObject("selectSnmpSettingFromEquipById", params);
	}
}
