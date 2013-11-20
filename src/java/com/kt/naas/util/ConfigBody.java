package com.kt.naas.util;

import com.kt.naas.domain.OpenstackBody;

public class ConfigBody {

	public static String makeBody(String objectType, Object message) {
		String body = "";
		
		if (message == null) return null;
		
		OpenstackBody openstackBody = (OpenstackBody)message;
		
		if(objectType.equals("TENANTCREATE")) // TENANT CREATE
		{
			body = "{" +
			"\"tenant\": {" +
			"\"name\": \"" + openstackBody.getTenantName() + "\"," +
			"\"description\": \"" + openstackBody.getDescription() + "\"," +
			"\"enabled\": true" +
			"}" +
			"}";
		}
		
		if(objectType.equals("GETTOKENS")) // Get Tokens
		{
			body = "{" +
			"\"auth\":{" +
			"\"tenantName\":\"" + openstackBody.getTenantName() + "\",\"passwordCredentials\":{" +
			"\"username\": \"" + openstackBody.getUserName() + "\", \"password\": \"" + openstackBody.getPassword() + "\"" +
			"}" +
			"}" +
			"}";
		}
		else if(objectType.equals("VMCREATE")) // VM Create
		{
			body = "{ " +
			"\"server\": { " +
			"\"flavorRef\": \"" + openstackBody.getFlavorRef() + "\", " +
			"\"imageRef\": \"" + openstackBody.getImageRef() + "\", " +
			"\"name\": \"" + openstackBody.getVmName() + "\" " +
			"} " +
			"}";
		}
		else if(objectType.equals("VMUPDATE")) // VM Create
		{
			body = "{ " +
			"\"server\": { " +
			"\"name\": \"" + openstackBody.getVmName() + "\" " +
			"} " +
			"}";
		}
		else if(objectType.equals("FLAVORCREATE")) // Flavor Create
		{
			body = "{ " +
			"\"flavor\": { " +
			"\"name\": \"" + openstackBody.getFlavorName() + "\", " +
			"\"ram\": " + openstackBody.getRam() + ", " +
			"\"vcpus\": " + openstackBody.getVcpus() + ", " +
			"\"disk\": " + openstackBody.getDisk() + ", " +
			"\"id\": \"" + openstackBody.getFlavorId() + "\" " +  // 泅犁 积己等 Max + 1 利侩
			"} " +
			"}";
		}
		else if(objectType.equals("ALLOCATEFLOATINGIP")) // Floating IP Create
		{
			body = "{ " +
			"\"pool\": \"" + openstackBody.getPool() + "\" " +
			"}";
		}
		else if(objectType.equals("ADDFLOATINGIP")) // Floating IP Add
		{
			body = "{ " +
			"\"addFloatingIp\": { " +
			"\"address\": \"" + openstackBody.getAddress() + "\" " +
			"} " +
			"}";
		}
		else if(objectType.equals("REMOVEFLOATINGIP")) // Floating IP Remove
		{
			body = "{ " +
			"\"removeFloatingIp\": { " +
			"\"address\": \"" + openstackBody.getAddress() + "\" " +
			"} " +
			"}";
		}
		else if(objectType.equals("SERVERPAUSE")) // Server Pause
		{
			body = "{ " +
			"\"pause\": null" +
			"}";
		}
		else if(objectType.equals("SERVERUNPAUSE")) // Server UnPause
		{
			body = "{ " +
			"\"unpause\": null" +
			"}";
		}
		
		return body;
	}

}
