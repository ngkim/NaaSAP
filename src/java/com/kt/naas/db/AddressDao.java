package com.kt.naas.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.kt.naas.domain.Address;

@SuppressWarnings("unchecked")

public class AddressDao extends SqlMapClientDaoSupport {
	public void insertAddress(Address address)
	{
		getSqlMapClientTemplate().insert("insertAddress", address);
	}
	
	public List<Address> selectAddress() {
		return getSqlMapClientTemplate().queryForList("selectAddress");
	}
	
	public Address selectAddressByCustname(String custName)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custname", custName);
		return (Address) getSqlMapClientTemplate().queryForObject("selectAddressByCustname", params);
	}
	
	public int deleteAddressById(int num)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("num", Integer.toString(num));
		return getSqlMapClientTemplate().delete("deleteAddressById", params);
	}
}
