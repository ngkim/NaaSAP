package com.kt.naas.process.inventory;

import com.kt.naas.GlobalConstants;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EquipDao;
import com.kt.naas.db.ServerDao;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.domain.Server;
import com.kt.naas.domain.Equip;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.process.ProcessorFactory;
import com.kt.naas.process.RequestProcessor;


public class AddServerProcessor extends RequestProcessor {
	
	private ProcessorFactory processorFactory = null;

	@Override
	public void processRequest() {
		
		ResponseMessage result = null; 
		FieldBuffer inBuf = request.getFieldBuffer();
		FieldBuffer outBuf = response.getFieldBuffer();		
		
		String serverid = "";
		String servername = "";	
		String masterip = "";
		int port = 0;	
		String model = "";
		String serveros = "";
		String hostname = "";
		String servicetype = "";
		String status = "";
		String userid = "";
		String passwd = "";
		String description = "";
		String detailMsg = "";
		
		//dao
		EquipDao dao = DaoFactory.getEquipDao();
		ServerDao serverDao = DaoFactory.getServerDao();
		
		
		try {

			//serverid = inBuf.getString("SERVERID");
			servername = inBuf.getString("SERVERNAME");		
			masterip = inBuf.getString("MASTERIP");
			port = inBuf.getInt("PORT");	
			model = inBuf.getString("MODEL");
			serveros = inBuf.getString("SERVEROS");
			hostname = inBuf.getString("HOSTNAME");
			servicetype = inBuf.getString("SERVICETYPE");
			status = inBuf.getString("STATUS");
			userid = inBuf.getString("USERID");
			passwd = inBuf.getString("PASSWD");
			description = inBuf.getString("DESCRIPTION");

			// 장비 신규 등록시 플랫폼 내 유일한 ID를 생성하기 위한 SEQUENCE를 얻어온다.
			Equip seqNum = null;
			try {
				seqNum = dao.selectEquipSeq("-");
			} catch (Exception e) {
			}
			
			System.out.println(seqNum);
			// 장비 타입별 플랫폼 내 유일한 ID를 생성한다.
			serverid = "SV_OP" + "." + seqNum.getEquipId();		
			
			Server existItem = null;
			try {
				existItem = serverDao.selectServerById(serverid);
			} catch (Exception e) {
			}
				
			Server server = new Server();
			
			server.setServerid(serverid);
			server.setServername(servername);
			server.setMasterip(masterip);
			server.setPort(port);
			server.setModel(model);
			server.setServeros(serveros);
			server.setHostname(hostname);
			server.setServicetype(servicetype);
			server.setStatus(status);
			server.setUserid(userid);
			server.setPasswd(passwd);
			server.setDescription(description);
			
			if (existItem == null)
				serverDao.insertServer(server);
			else
				serverDao.updateServer(server);
		
			
			outBuf.putInt("count", 1);

		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
			//return;
		} finally {
		}
		
	} // end of processRequest()
}