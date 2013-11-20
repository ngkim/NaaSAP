package com.kt.naas.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kt.naas.domain.Vm;
import com.kt.naas.domain.VmFlavor;
import com.kt.naas.domain.VmImage;
import com.kt.naas.domain.VmIp;

public class JsonResponseUtils {
	
	//--------------------------------------------
	// �Ƹ��� AWS API ��� 
	//--------------------------------------------
	public static List<VmIp> getAwsIps(String jsonStr)  {
		
	    //{Addresses: [{InstanceId: i-3eb6e157,PublicIp: 50.16.204.61,Domain: standard,}]}
		//{Addresses: [{InstanceId: ,PublicIp: 23.23.107.213,Domain: standard,}, {InstanceId: i-3eb6e157,PublicIp: 50.16.204.61,Domain: standard,}]}
	    
		// �Ľ� ���� ���� �ذ�
		// ": ," -> ": null,"
		jsonStr = jsonStr.replace(": ,", ": null,");
		
	    List<VmIp> vmIps = new ArrayList<VmIp>();	    
	    
	    
		try {

		    JSONObject obj = new JSONObject(jsonStr);
		    JSONArray data = obj.getJSONArray("Addresses");
		    int n = data.length();
		    
		    for (int i = 0; i < n; ++i) {
		    	
				final JSONObject item = data.getJSONObject(i);
				
				VmIp vmIp = new VmIp();

				vmIp.setServerid("SV_AWS.10000");
				vmIp.setIp(item.getString("PublicIp"));
				vmIp.setClid("-");
				
				String instanceId = item.getString("InstanceId");
				
				if(instanceId.equals(null) || instanceId.equals("null")) // null �̸�
					vmIp.setUseyn("N"); // VM�� �Ҵ� ����
				else
					vmIp.setUseyn("Y"); // VM���� �̹� �����
				
				vmIps.add(vmIp);
		    }

		   
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("vmIps : " + vmIps);
	    
	    return vmIps;
	}


}
