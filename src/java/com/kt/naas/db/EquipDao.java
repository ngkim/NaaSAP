package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Equip;

@SuppressWarnings("unchecked")
public class EquipDao extends SqlMapClientDaoSupport {

	public List<Equip> selectSnEquip()
	{
		return getSqlMapClientTemplate().queryForList("selectSnEquip");
	}
	
	public List<Equip> selectEquipByEmsId(String emsId)
	{
		return getSqlMapClientTemplate().queryForList("selectSnEquipByEmsId", emsId);
	}
	
	public Equip selectSnEquipById(String emsId, String snEquipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snEquipId", snEquipId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectSnEquipById", params);
	}
	
	public void insertSnEquip(Equip equip)
	{
		getSqlMapClientTemplate().insert("insertSnEquip", equip);
	}
	
	public int updateSnEquip(Equip equip)
	{
		return getSqlMapClientTemplate().update("updateSnEquip", equip);
	}
	
	public int updateSnEquipWorkingStatusByEmsId(String emsId)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().update("updateSnEquipWorkingStatusByEmsId", params);
	}
	
	public int updateSnEquipWorkingStatusById(String emsId, String snEquipId, String equipName)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snEquipId", snEquipId);
		params.put("equipName", equipName);
		return getSqlMapClientTemplate().update("updateSnEquipWorkingStatusById", params);
	}
	
	public int deleteSnEquipById(String emsId, String equipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		return getSqlMapClientTemplate().delete("deleteSnEquipById", params);
	}
	
	public int deleteSnEquipWorkingStatusByEmsId(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return getSqlMapClientTemplate().delete("deleteSnEquipWorkingStatusByEmsId", params);
	}
	
	//---------플랫폼 내부에서 관리하는 테이블 처리
	public List<Equip> selectEquip()
	{
		return getSqlMapClientTemplate().queryForList("selectEquip");
	}
	
	public Equip selectEquipById(String emsId, String equipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectEquipById", params);
	}
	
	public Equip selectEquipBySnId(String emsId, String snEquipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("snEquipId", snEquipId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectEquipBySnId", params);
	}
	
	public Equip selectEquipByIp(String emsId, String masterIp)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("masterIp", masterIp);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectEquipByIp", params);
	}
	
	public Equip selectAcquirerByEms(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectAcquirerByEms", params);
	}
	
	public Equip selectMainCaExceptAcquirerByEms(String emsId, String equipId, String serviceId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		params.put("serviceId", serviceId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectMainCaExceptAcquirerByEms", params);
	}
	
	public Equip  selectEquipSeq(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		return (Equip) getSqlMapClientTemplate().queryForObject("selectEquipSeq", params);
	}
	
	public void insertEquip(Equip equip)
	{
		getSqlMapClientTemplate().insert("insertEquip", equip);
	}
	
	public int updateEquip(Equip equip)
	{
		return getSqlMapClientTemplate().update("updateEquip", equip);
	}
	
	// RC 삭제시 RcId를 할당받고 있는 장비에 대한 RC 정보 초기화 수행
	public int updateEquipInitRcIdByRcId(String rcId)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("rcId", rcId);
		params.put("initId", "");
		return getSqlMapClientTemplate().update("updateEquipInitRcIdByRcId", params);
	}
	
	// Location 삭제시 LocationId를 할당받고 있는 장비에 대한 Location 정보 초기화 수행
	public int updateEquipTargetLocationByLocList(String emsId)
	{		
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("initId", "");
		return getSqlMapClientTemplate().update("updateEquipTargetLocationByLocList", params);
	}
	
	public int deleteEquipById(String emsId, String equipId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("emsId", emsId);
		params.put("equipId", equipId);
		return getSqlMapClientTemplate().delete("deleteEquipById", params);
	}
	
	
	//-------- 이후부터는 배치작업을 위하여 구현해야 함
	public List<Equip> selectAll()
	{
		return selectSnEquip();
	}
	
	public void insertList(List<Equip> list)
	{
		for (Equip equip : list)
		{
			insertSnEquip(equip);
		}
	}
	
	public void updateList(List<Equip> list)
	{
		for (Equip equip : list)
		{
			updateSnEquip(equip);
		}
	}
	
	public void deleteList(List<Equip> list)
	{
		for (Equip equip : list)
		{
			deleteSnEquipById(equip.getEmsId(), equip.getEquipId());
		}
	}

	public List<Equip> selectEquipTest()
	{
		return getSqlMapClientTemplate().queryForList("selectEquipTest");
	}
	
	public Equip selectGetEquipInfo(String equipId)
	{
		return (Equip) getSqlMapClientTemplate().queryForObject("selectGetEquipInfo", equipId);
		
	}
}
