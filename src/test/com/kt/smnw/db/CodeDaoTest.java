package com.kt.smnw.db;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.db.CodeDao;
import com.kt.naas.domain.Code;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class CodeDaoTest {
	
	@Autowired
	private CodeDao codeDao;
	
	@Test
	public void testGetAllCodes() {
		List<Code> codes = codeDao.getAllCodes();
		
		System.out.println(codes.size() + " items selected.");
		for (Code code : codes)
		{
			System.out.println(code);
		}
	}

}
