package com.kt.naas.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kt.naas.error.DatatypeMismatchException;
import com.kt.naas.error.NoMoreDataException;

public class FieldBuffer implements Serializable {
	private static final long serialVersionUID = 8217696808768146216L;

	protected HashMap<String, ArrayList<Object>> myData = new HashMap<String, ArrayList<Object>>(); 
	
	public void putString(String fieldName, String value)
	{
		getList(fieldName).add(value);
	}
	
	public void putLong(String fieldName, long value)
	{
		getList(fieldName).add(new Long(value));
	}

	public void putInt(String fieldName, int value)
	{
		getList(fieldName).add(new Integer(value));
	}

	public void putDouble(String fieldName, double value)
	{
		getList(fieldName).add(new Double(value));
	}
	
	public int getCount(String fieldName)
	{
		ArrayList<Object> list = myData.get(fieldName);
		if (list == null)
			return 0;
		else return getList(fieldName).size();
	}
	
	public String getString(String fieldName)
	{
		Object item = getItem(fieldName);

		if (item instanceof String)
			return (String)item;
		else
			throw new DatatypeMismatchException(fieldName, item);
	}

	public long getLong(String fieldName)
	{
		Object item = getItem(fieldName);

		if (item instanceof Long)
			return ((Long)item).longValue();
		else
			throw new DatatypeMismatchException(fieldName, item);
	}

	public int getInt(String fieldName)
	{
		Object item = getItem(fieldName);

		if (item instanceof Integer)
			return ((Integer)item).intValue();
		else
			throw new DatatypeMismatchException(fieldName, item);
	}
	
	public double getDouble(String fieldName)
	{
		Object item = getItem(fieldName);

		if (item instanceof Double)
			return ((Double)item).doubleValue();
		else
			throw new DatatypeMismatchException(fieldName, item);
	}
	
	public String getString(String fieldName, String defaultValue)
	{
		try {
			return getString(fieldName);
		} catch (NoMoreDataException e) {
			return defaultValue;
		}
	}

	public long getLong(String fieldName, long defaultValue)
	{
		try {
			return getLong(fieldName);
		} catch (NoMoreDataException e) {
			return defaultValue;
		}
	}

	public int getInt(String fieldName, int defaultValue)
	{
		try {
			return getInt(fieldName);
		} catch (NoMoreDataException e) {
			return defaultValue;
		}
	}
	
	public double getDouble(String fieldName, double defaultValue)
	{
		try {
			return getDouble(fieldName);
		} catch (NoMoreDataException e) {
			return defaultValue;
		}
	}	
	
	@SuppressWarnings("rawtypes")
	public void putList(List<Map> list)
	{
		for (Map map : list)
		{
			putMap(map);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void putMap(Map map)
	{
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			String sKey = key.toString();
			
			Object value = map.get(key);
			if (value instanceof String)
				putString(sKey, (String)value);
			else if (value instanceof Integer)
				putInt(sKey, ((Integer)value).intValue());
			else if (value instanceof Long)
				putLong(sKey, ((Long)value).longValue());
			else if (value instanceof Double)
				putDouble(sKey, ((Double)value).doubleValue());
			else if (value instanceof Float)
				putDouble(sKey, ((Float)value).doubleValue());
			else if (value instanceof BigInteger)
				putLong(sKey, ((BigInteger)value).longValue());
		}
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		
		
		int colSize = myData.keySet().size();
		int maxSize = 0;
		
		ArrayList<ArrayList<Object>> columns = new ArrayList<ArrayList<Object>>();
		
		String[] keys = myData.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			if (i > 0)
				buf.append(",");
			buf.append(keys[i]);
			
			int size = myData.get(keys[i]).size();
			if (size > maxSize) maxSize = size;
			
			columns.add(myData.get(keys[i]));
		}
		buf.append("\n");
		
		for (int i = 0; i < maxSize; i++)
		{
			buf.append(i).append(":");
			for (int j = 0; j < colSize; j++)
			{
				if (j > 0)
					buf.append(",");
				
				ArrayList<Object> list = columns.get(j);
				if (i < list.size())
					buf.append(list.get(i));
			}
			buf.append("\n");
		}
		
		return buf.toString();
	}

	private ArrayList<Object> getList(String fieldName)
	{
		ArrayList<Object> list = myData.get(fieldName);
		if (list == null)
		{
			list = new ArrayList<Object>();
			myData.put(fieldName, list);
		}

		return list;
	}
	
	private Object getItem(String fieldName)
	{
		ArrayList<Object> list = myData.get(fieldName);
		if (list == null)
			throw new NoMoreDataException(fieldName);
		
		return list.remove(0);
	}
}
