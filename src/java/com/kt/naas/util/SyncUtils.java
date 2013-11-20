package com.kt.naas.util;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SyncUtils<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private SqlMapClientDaoSupport dao;
	
	public SyncUtils(SqlMapClientDaoSupport dao)
	{
		this.dao = dao;
	}
	
	public void batchSync(List<T> nList)
	{
		List<T> newList = new ArrayList<T>(nList);
		List<T> oldList = selectAll();
		Map<String, T> newMap = list2map(newList);
		Map<String, T> oldMap = list2map(oldList);
		
		List<T> insertList = new ArrayList<T>();
		List<T> updateList = new ArrayList<T>();
		List<T> deleteList = null;
		
		for (String key : newMap.keySet())
		{
			T newItem = newMap.get(key);
			T oldItem = oldMap.get(key);
			
			if (oldItem != null)	// OLD, NEW 모두 포함된 ITEM
			{
				if (!newItem.equals(oldItem))
					updateList.add(newItem);	// 키만 같고 값이 다르면 update 대상으로 포함
				
				oldMap.remove(key);
			} else
			{
				insertList.add(newItem);
			}
		}
		deleteList = new ArrayList<T>(oldMap.values());
		
		try {
			dao.getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			dao.getSqlMapClientTemplate().getSqlMapClient().startBatch();
			
			insertList(insertList);
			updateList(updateList);
			deleteList(deleteList);
			
			dao.getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			
			dao.getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
			int changed = insertList.size() + updateList.size() + deleteList.size();
			if (changed > 0)
				logger.info(String.format("sync completed. dao=%s, insert=%s, update=%s, delete=%s"
						, dao.getClass().getSimpleName(), insertList.size(), updateList.size(), deleteList.size()));
			else
				logger.info(String.format("sync completed. dao=%s, no data changed."
						, dao.getClass().getSimpleName()));
		} catch (Exception e) {
			logger.error("exeption in batch sync", e);
		} finally {
			try {
				dao.getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("End Transaction fail", e);
			}
		}
	}
	
	public Map<String, T> list2map(List<T> list)
	{
		Map<String, T> map = new HashMap<String, T>();
		
		for (T item : list)
		{
			String key = item.toString();
			try {
				
				Method m = item.getClass().getMethod("getKey", new Class[0]);
				key = "" + m.invoke(item, new Object[0]);
			} catch (Exception e) {
			}
			map.put(key, item);
		}
		return map;
	}
	
	public void batchInsert(List<T> list)
	{
		try {
			dao.getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			dao.getSqlMapClientTemplate().getSqlMapClient().startBatch();
			
			insertList(list);
			int total = dao.getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			dao.getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
			if (total > 0)
				logger.debug(total + " rows inserted");
		} catch (Exception e) {
			logger.error("exeption in batch insert", e);
		} finally {
			try {
				dao.getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("End Transaction fail", e);
			}
		}
	}
	
	public void batchUpdate(List<T> list)
	{
		try {
			dao.getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			dao.getSqlMapClientTemplate().getSqlMapClient().startBatch();
			
			updateList(list);
			int total = dao.getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			dao.getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
			if (total > 0)
				logger.debug(total + " rows updated");
		} catch (Exception e) {
			logger.error("exeption in batch update", e);
		} finally {
			try {
				dao.getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("End Transaction fail", e);
			}
		}
	}
	
	public void batchDelete(List<T> list)
	{
		try {
			dao.getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			dao.getSqlMapClientTemplate().getSqlMapClient().startBatch();
			
			deleteList(list);
			int total = dao.getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			dao.getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
			if (total > 0)
				logger.debug(total + " rows deleted");
		} catch (Exception e) {
			logger.error("exeption in batch delete", e);
		} finally {
			try {
				dao.getSqlMapClientTemplate().getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("End Transaction fail", e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<T> selectAll()
	{
		Method method = findDaoMethod("selectAll");
		try {
			return (List<T>) method.invoke(dao, new Object[0]);
		} catch (Exception e) {
			logger.error("insert list fail.", e);
		}
		return null;
	}
	
	private void insertList(List<T> list)
	{
		Method method = findDaoMethod("insertList");
		try {
			method.invoke(dao, list);
		} catch (Exception e) {
			logger.error("insert list fail.", e);
		}
	}
	
	private void updateList(List<T> list)
	{
		Method method = findDaoMethod("updateList");
		try {
			method.invoke(dao, list);
		} catch (Exception e) {
			logger.error("update list fail.", e);
		}
	}
	
	private void deleteList(List<T> list)
	{
		Method method = findDaoMethod("deleteList");
		try {
			method.invoke(dao, list);
		} catch (Exception e) {
			logger.error("delete list fail.", e);
		}
	}
	
	private Method findDaoMethod(String methodName)
	{
		Method[] mList = dao.getClass().getMethods();
		for (Method method : mList)
		{
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}
}
