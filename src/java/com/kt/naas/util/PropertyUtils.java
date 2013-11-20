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
 * Property 처리를 위한 Utility.
 * 
 * PropertyUtils 는 DB(tb_property), FILE(application.properties), System Property
 * 세가지 Property 를 자동으로 로딩하여 데이터를 제공해준다.<BR>
 * 동일한 내용이 여러 위치에 포함되는 경우 DB, FILE, SYSTEM 순으로 우선순위를 갖는다.<BR>
 * PropertyUtils는 최초 접근시에 로딩되며, 로딩 후 변경된 내용은 반영되지 않는다.
 * 
 * @author 안병규
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
	 * Property 값을 String 형태로 반환한다. 등록된 Property 가 없는 경우 null 을 반환한다.
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
	 * Property 값을 boolean 형태로 반환한다.
	 * @exception java.util.NoSuchElementException 등록된 Property 가 없는 경우
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
	 * Property 값을 double 형태로 반환한다.
	 * @exception java.util.NoSuchElementException 등록된 Property 가 없는 경우
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
	 * Property 값을 float 형태로 반환한다.
	 * @exception java.util.NoSuchElementException 등록된 Property 가 없는 경우
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
	 * Property 값을 int 형태로 반환한다.
	 * @exception java.util.NoSuchElementException 등록된 Property 가 없는 경우
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
	 * Property 값을 long 형태로 반환한다.
	 * @exception java.util.NoSuchElementException 등록된 Property 가 없는 경우
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
