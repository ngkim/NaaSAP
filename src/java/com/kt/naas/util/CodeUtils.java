package com.kt.naas.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.kt.naas.db.CodeDao;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.domain.Code;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

/**
 * DB에 정의된 code 값들을 쉽게 mapping 할 수 있도록 하기 위하여 사용하는 Utility
 * @author 안병규
 */
public class CodeUtils {
	private static final Logger logger = LoggerFactory.getLogger(CodeUtils.class);
	
	private static Map<String, Map<String, String>>	codeValueMaps;
	private static Map<String, Map<String, String>>	valueCodeMaps;
	
	static {
		init();
	}
	
	/**
	 * codeType과 codeName으로 codeValue 값을 가져온다.
	 * @param codeType
	 * @param codeName
	 * @return codeValue 값. 등록된 코드가 없는 경우 null
	 */
	public static String getCodeValue(String codeType, String codeName)
	{
		if (codeType == null || codeName == null) return null;
		Map<String, String> map = codeValueMaps.get(codeType);
		return (map == null) ? null : map.get(codeName);
	}
	
	/**
	 * codeType과 codeValue 값으로 codeName을 가져온다.
	 * 여러 codeName이 동일한 codeValue 인 경우 어떠한 값이 리턴될 지 알 수 없다.
	 * @param codeType
	 * @param codeValue
	 * @return codeName 값. 등록된 코드가 없는 경우 null
	 */
	public static String getCodeName(String codeType, String codeValue)
	{
		if (codeType == null || codeValue == null) return null;
		Map<String, String> map = valueCodeMaps.get(codeType);
		return (map == null) ? null : map.get(codeValue);
	}
	
	/**
	 * codeType과 codeName(intValue)로 codeValue 값을 가져온다.
	 * snmp enum 값을 코드로 등록한 경우에 사용한다.
	 * ex)ifAdminStatus : 1 --> UP
	 * @param codeType
	 * @param intValue 
	 * @param defaultValue 
	 * @return codeValue 값. 등록된 코드가 없는 경우 defaultValue
	 */
	public static String getSnmpEnumString(String codeType, int intValue, String defaultValue)
	{
		String s = getCodeValue(codeType, "" + intValue);
		return (s == null) ? defaultValue : s;
	}
	
	/**
	 * codeType과 codeValue(stringValue)로 codeName 값을 가져온다.
	 * snmp enum 값을 코드로 등록한 경우에 사용한다.
	 * ex)ifAdminStatus : UP --> 1
	 * @param codeType
	 * @param stringValue
	 * @param defaultValue
	 * @return codeName 값. 등록된 코드가 없는 경우 defaultValue
	 */
	public static int getSnmpEnumInt(String codeType, String stringValue, int defaultValue)
	{
		String s = getCodeValue(codeType, stringValue);
		if (s == null) return defaultValue;
		try {
			
			int i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * codeType과 codeValue(stringValue)로 codeName 값을 가져온다.
	 * snmp enum 값을 코드로 등록한 경우에 사용한다.
	 * ex)ifAdminStatus : UP --> 1
	 * @param codeType
	 * @param stringValue
	 * @return codeName 값. 
	 * @exception EmsException 등록된 코드가 없는 경우 
	 */
	public static int getSnmpEnumInt(String codeType, String stringValue)
	{
		String s = getCodeValue(codeType, stringValue);
		if (s == null) throw ExceptionFactory.getSNPException(ErrorCodes.UNDEFINED_VALUE, String.format("[%s:%s]", codeType, stringValue));
		try {
			
			int i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.UNDEFINED_VALUE, String.format("[%s:%s]", codeType, stringValue));
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void init()
	{
		codeValueMaps = new HashMap<String, Map<String, String>>();
		valueCodeMaps = new HashMap<String, Map<String, String>>();
		
		try {
			CodeDao dao = DaoFactory.getCodeDao();
			List codeList = dao.getAllCodes();
			
			for (Iterator iterator = codeList.iterator(); iterator.hasNext();) {
				Code code = (Code) iterator.next();
				
				Map<String, String> codeValueMap = getSubMap(codeValueMaps, code.getCodeType());
				Map<String, String> valueCodeMap = getSubMap(valueCodeMaps, code.getCodeType());
				
				codeValueMap.put(code.getCodeName(), code.getCodeValue());
				valueCodeMap.put(code.getCodeValue(), code.getCodeName());
				
				String log = String.format("[CODE]%s:%s=[%s]", code.getCodeType(), code.getCodeName(), code.getCodeValue());
				logger.debug(log);
			}
		} catch (DataAccessException e) {
			logger.error("fail to loading CodeTable", e);
		}
	}
	
	private static Map<String, String> getSubMap(Map<String, Map<String, String>> mainMap, String codeType)
	{
		Map<String, String> ret = mainMap.get(codeType);
		if (ret == null) {
			ret = new HashMap<String, String>();
			mainMap.put(codeType, ret);
		}
		
		return ret;
	}
}
