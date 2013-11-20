package com.kt.smnw.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.db.PropertyDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
@SuppressWarnings("rawtypes")
public class PropertyDaoTest {

	@Autowired
	private PropertyDao propertyDao;
	
	private String key = "fjdIODHIOjlfd";
	private String value1 = "DFJKLFjiofefdskljD1";
	private String value2 = "DFJKLFjiodfkl_hdfjD2";
	
	@Test
	public void testGetAllProperties() {
		List<HashMap> properties = propertyDao.getAllProperties();
		
		System.out.println(properties.size() + " items selected.");
		for (HashMap map : properties)
		{
			System.out.println(map);
		}
	}
	
	@Test
	public void testPropertyDao()
	{
		// 일단지우고
		propertyDao.deleteProperty(key);
		
		propertyDao.insertProperty(key, value1);
		
		assertEquals(value1, propertyDao.getProperty(key));
		
		propertyDao.updateProperty(key, value2);
		assertEquals(value2, propertyDao.getProperty(key));
		
		propertyDao.saveProperty(key, value1);
		assertEquals(value1, propertyDao.getProperty(key));
		
		propertyDao.deleteProperty(key);
		assertNull(propertyDao.getProperty(key));
	}
}
