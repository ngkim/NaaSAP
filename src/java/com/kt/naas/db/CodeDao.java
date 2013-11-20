package com.kt.naas.db;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Code;

public class CodeDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<Code> getAllCodes() {
		return getSqlMapClientTemplate().queryForList("getAllCodes");
	}
}
