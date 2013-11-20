package com.kt.naas.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kt.naas.GlobalConstants;
import com.kt.naas.MainContext;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EmsDao;
import com.kt.naas.domain.Ems;
import com.kt.naas.domain.SnLocation;
import com.kt.naas.domain.SnObject;
import com.kt.naas.domain.decoder.GetLocationsDecoder;
import com.kt.naas.service.EmsService;

public class EmsUtils {
	private static EmsService ems = (EmsService)MainContext.getBean("emsService");
	
	public static List<SnLocation> getLocations(String emsId)
	{
		Map<String, String> params = new HashMap<String, String>();	
		
		EmsDao emsDao = DaoFactory.getEmsDao();
		Ems existItem = null;
		existItem = emsDao.selectEmsById(emsId);
		
		params.put("param", "all");
		
		String result = ems.polling(emsId, "com.cisco.unicorn.ui.ListApiServlet", "getLocations", params);
		
		GetLocationsDecoder decoder = new GetLocationsDecoder();
		
		List<SnLocation> locations = decoder.parse(result);
		setEmsId(emsId, locations);
		return locations;
	}
	
	
	
	
	

	public static void setEmsId(String emsId, SnObject obj)
	{
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			if (field.getName().equals("emsId") && field.getType().getSimpleName().equals("String"))
			{
				try {
					Method setMethod = obj.getClass().getMethod("setEmsId", String.class);
					setMethod.invoke(obj, emsId);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void setEmsId(String emsId, List list)
	{
		for (Object item : list)
		{
			if (item instanceof SnObject)
				setEmsId(emsId, (SnObject)item);
		}
	}
}
