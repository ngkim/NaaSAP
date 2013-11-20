package com.kt.naas.db;

import com.kt.naas.MainContext;

public class DaoFactory {
	
	public static VmIpDao getVmIpDao()
	{
		return (VmIpDao)MainContext.getBean("vmIpDao");
	}
	
	public static DomainNetworkDao getDomainNetworkDao()
	{
		return (DomainNetworkDao)MainContext.getBean("domainNetworkDao");
	}
	
	public static DomainNetworkEquipDao getDomainNetworkEquipDao()
	{
		return (DomainNetworkEquipDao)MainContext.getBean("domainNetworkEquipDao");
	}
	
	public static DomainNetworkPortDao getDomainNetworkPortDao()
	{
		return (DomainNetworkPortDao)MainContext.getBean("domainNetworkPortDao");
	}
	
	public static DomainNetworkLinkDao getDomainNetworkLinkDao()
	{
		return (DomainNetworkLinkDao)MainContext.getBean("domainNetworkLinkDao");
	}
		
	public static ProgressStatusDao getProgressStatusDao()
	{
		return (ProgressStatusDao)MainContext.getBean("progressStatusDao");
	}
	
	public static NetworkServiceDao getNetworkServiceDao()
	{
		return (NetworkServiceDao)MainContext.getBean("networkServiceDao");
	}
	
	public static DCNetworkServiceDao getDCNetworkServiceDao()
	{
		return (DCNetworkServiceDao)MainContext.getBean("dcNetworkServiceDao");
	}
	
	public static PremiseNetworkServiceDao getPremiseNetworkServiceDao()
	{
		return (PremiseNetworkServiceDao)MainContext.getBean("premiseNetworkServiceDao");
	}
	
	public static TransportNetworkServiceDao getTransportNetworkServiceDao()
	{
		return (TransportNetworkServiceDao)MainContext.getBean("transportNetworkServiceDao");
	}
	
	public static ServerDao getServerDao()
	{
		return (ServerDao)MainContext.getBean("serverDao");
	}
	
	public static VmDao getVmDao()
	{
		return (VmDao)MainContext.getBean("vmDao");
	}
	
	public static VmKeyDao getVmKeyDao()
	{
		return (VmKeyDao)MainContext.getBean("vmKeyDao");
	}
		
	public static EmsDao getEmsDao()
	{
		return (EmsDao)MainContext.getBean("emsDao");
	}

	public static CodeDao getCodeDao()
	{
		return (CodeDao)MainContext.getBean("codeDao");
	}
	
	public static PropertyDao getPropertyDao()
	{
		return (PropertyDao)MainContext.getBean("propertyDao");
	}
	
	public static SnLocationDao getSnLocationDao()
	{
		return (SnLocationDao)MainContext.getBean("snLocationDao");
	}
	
	public static EquipDao getEquipDao()
	{
		return (EquipDao)MainContext.getBean("equipDao");
	}
	
	public static SnmpSettingDao getSnmpSettingDao()
	{
		return (SnmpSettingDao)MainContext.getBean("snmpSettingDao");
	}
}
