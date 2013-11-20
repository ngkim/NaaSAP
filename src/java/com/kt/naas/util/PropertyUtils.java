package com.kt.naas.util;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.PropertyDao;

/**
 * Property ó���� ���� Utility.
 * 
 * PropertyUtils �� DB(tb_property), FILE(application.properties), System Property
 * ������ Property �� �ڵ����� �ε��Ͽ� �����͸� �������ش�.<BR>
 * ������ ������ ���� ��ġ�� ���ԵǴ� ��� DB, FILE, SYSTEM ������ �켱������ ���´�.<BR>
 * PropertyUtils�� ���� ���ٽÿ� �ε��Ǹ�, �ε� �� ����� ������ �ݿ����� �ʴ´�.
 * 
 * @author �Ⱥ���
 *
 */
public class PropertyUtils {
	protected final static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
	
	private static PropertiesConfiguration fileConfig;
	private static SystemConfiguration sysConfig;
	private static PropertiesConfiguration dbConfig;
	
	private static PropertyDao propertyDao = DaoFactory.getPropertyDao();
	
	static {
		init();
	}
	
	/**
	 * Property ���� String ���·� ��ȯ�Ѵ�. ��ϵ� Property �� ���� ��� null �� ��ȯ�Ѵ�.
	 */
	public static String getString(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getString(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getString(key);
		
		return sysConfig.getString(key);
	}

	/**
	 * Property ���� boolean ���·� ��ȯ�Ѵ�.
	 * @exception java.util.NoSuchElementException ��ϵ� Property �� ���� ���
	 */
	public static boolean getBoolean(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getBoolean(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getBoolean(key);
		
		return sysConfig.getBoolean(key);
	}
	
	/**
	 * Property ���� double ���·� ��ȯ�Ѵ�.
	 * @exception java.util.NoSuchElementException ��ϵ� Property �� ���� ���
	 */
	public static double getDouble(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getDouble(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getDouble(key);
		
		return sysConfig.getDouble(key);
	}

	/**
	 * Property ���� float ���·� ��ȯ�Ѵ�.
	 * @exception java.util.NoSuchElementException ��ϵ� Property �� ���� ���
	 */
	public static float getFloat(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getFloat(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getFloat(key);
		
		return sysConfig.getFloat(key);
	}

	/**
	 * Property ���� int ���·� ��ȯ�Ѵ�.
	 * @exception java.util.NoSuchElementException ��ϵ� Property �� ���� ���
	 */
	public static int getInt(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getInt(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getInt(key);
		
		return sysConfig.getInt(key);
	}

	/**
	 * Property ���� long ���·� ��ȯ�Ѵ�.
	 * @exception java.util.NoSuchElementException ��ϵ� Property �� ���� ���
	 */
	public static long getLong(String key)
	{
		if (dbConfig.containsKey(key))
			return dbConfig.getLong(key);
		if (fileConfig.containsKey(key))
			return fileConfig.getLong(key);
		
		return sysConfig.getLong(key);
	}
	
	public static void setProperty(String key, String value)
	{
		sysConfig.setProperty(key, value);
	}

	public static void setFileProperty(String key, String value)
	{
		fileConfig.setProperty(key, value);
	}

	public static void setDbProperty(String key, String value)
	{
		propertyDao.saveProperty(key, value);
		dbConfig.setProperty(key, value);
	}
	
	public static void removeProperty(String key)
	{
		sysConfig.clearProperty(key);
	}
	
	public static void removeFileProperty(String key)
	{
		fileConfig.clearProperty(key);
	}
	
	public static void removeDbProperty(String key)
	{
		propertyDao.deleteProperty(key);
		dbConfig.clearProperty(key);
	}
	
	private static void init()
	{
		System.out.println("init PropertyUtils");
		loadSystemProperties();
		loadFileProperties();
		loadDbProperties();
	}
	
	@SuppressWarnings("rawtypes")
	private static void loadDbProperties()
	{
		List<HashMap> list = propertyDao.getAllProperties();
		dbConfig = new PropertiesConfiguration();
		for (HashMap map : list)
		{
			String key = (String)map.get("propertyName");
			String value = (String)map.get("propertyValue");
			dbConfig.setProperty(key, value);
		}
	}
	
	private static void loadFileProperties()
	{
		try {
			fileConfig = new PropertiesConfiguration("application.properties");
			fileConfig.setAutoSave(true);
		} catch (ConfigurationException e) {
			logger.error("fail to load file-properties.", e);
		}
	}
	
	private static void loadSystemProperties()
	{
		sysConfig = new SystemConfiguration();
	}
}
