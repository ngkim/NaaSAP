package com.kt.smnw.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.util.RequestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class GetOriginProcessorTest {
	@Autowired
	private RequestClient requestClient;
	
	@Test
	public void testProcessRequest()
	{
		requestClient.init();
		RequestMessage request = new RequestMessage();
	
		
		// Openstack Test
//		//request.setServiceName("CREATE_VM");
//		request.setServiceName("DELETE_VM");
//		FieldBuffer buf = request.getFieldBuffer();	
//		
//		buf.putString("SERVERID", "0000001");	//AWS: SV_AWS.10000 / OP: 0000001
//		//buf.putString("SERVERNAME", "Openstack(DaeJeon)");
//		//buf.putString("CUSTID", "user_one");	//AWS: BBMC / OP: user_one
//		buf.putString("VMID", "VM_10035");
//		buf.putString("VMNAME", "Openstack_test1");	//AWS: T-instance1 / OP: Openstack_test1
//		//buf.putString("LOCALIP", "1.1.1.1");
//		//buf.putString("PUBLICIP", "221.145.180.69");
//		buf.putString("IMAGEID", "ff777a8e-83cb-41bc-8d21-fdee2e1231d1");	//AWS: ami-60c0bc09 / OP: ff777a8e-83cb-41bc-8d21-fdee2e1231d1
//		buf.putString("FLAVORID", "2");	// AWS: 1 / OP: 2
//		//buf.putString("KEY", "test");
//		//buf.putString("CLID", "----");
//		//buf.putString("STATUS", "----");
//		//buf.putString("DEPLOYTYPE", "----");
//		//buf.putString("DESCRIPTION", "vm �� �׽�Ʈ");
		
		
		
		// AWS Test
		//request.setServiceName("CREATE_VM");
		request.setServiceName("CREATE_SERVER");
		FieldBuffer buf = request.getFieldBuffer();	
		
		buf.putString("SERVERNAME", "Openstack_test");	//AWS: SV_AWS.10000 / OP: 0000001
		//buf.putString("CUSTID", "BBMC");	//AWS: BBMC / OP: user_one
		buf.putString("VMID", "VM_10042");
		buf.putString("VMNAME", "T-instance1");	//AWS: T-instance1 / OP: Openstack_test1
		buf.putString("IMAGEID", "ami-60c0bc09");	//AWS: ami-60c0bc09 / OP: ff777a8e-83cb-41bc-8d21-fdee2e1231d1
		buf.putString("FLAVORID", "1");	// AWS: 1 / OP: 2
		
		
		
		
		
		
		
//		request.setServiceName("CACHE_CONFIG");
//		FieldBuffer buf = request.getFieldBuffer();	
//		
//		buf.putString("SERVERID", "0000001");
//		buf.putString("CUSTID", "user_one");
//		buf.putString("VMID", "VM_10030");
//		buf.putString("CONFIGTYPE", "CACHE");
//		buf.putString("ORIGIN", "1.1.1.1");
//		buf.putString("DOMAIN", "test.cloud.sn.kt.com");
		
		
		
		requestClient.requestToAp(request);
		
		try {
			System.out.println("press enter to exit...");
			(new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
