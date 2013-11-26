package com.kt.naas;

public interface GlobalConstants {
	public final static byte	NULL_BYTE = -1;
	public final static short	NULL_SHORT = -1;
	public final static int		NULL_INT = -1;
	public final static long	NULL_LONG = -1L;
	
	public final static boolean OP_DEMO_POTN = false;
	public final static boolean OP_DEMO_CLOUD = true;
	public final static boolean OP_DEMO_PREMISE_DJ = false;
	public final static boolean OP_DEMO_PREMISE_WM = false;
	public final static boolean OP_DEMO_SNMP_DJ = true;
	public final static boolean OP_DEMO_SNMP_WM = true;
	
	
//	public final static String URL_CLOUD_SDN_API = "http://210.183.241.184:5000";
	public final static String URL_CLOUD_SDN_API = "http://211.224.204.144:5000";
//	public final static String URL_PREMISE_SDN_API_WM = "http://211.224.204.157:8080/APIServer/psdnRetrieveNWList";
	public final static String URL_PREMISE_SDN_API_WM = "http://211.241.161.153:9091/api";
	public final static String URL_PREMISE_SDN_API_DJ = "http://211.224.204.137:8080/NaaS";
	public final static String URL_TRANSPORT_SDN_API = "https://211.224.204.144:7502";
	
	public final static int HTTP_GET = 0x11;
	public final static int HTTP_POST = 0x12;
		
	public final static boolean OP_DEBUG = true;

	public final static short	RESULTCODE_SUCCESS = 0;
	public final static String	RESULTMSG_SUCCESS = "success.";
	
	public final static int		PING_SUCCESS = 0;
	public final static int		PING_ERROR = 1;
	public final static int		PING_TIMEOUT = 2;
	
	public final static int		SNMP_VER1 = 0;
	public final static int		SNMP_VER2C = 1;
	public final static int		SNMP_VER3 = 3;
	
	public final static long	MAX_COUNTER32 = 4294967295L;
	
	public final static String	ACTOR_WEBUI 	= "WEBUI";
	public final static String	ACTOR_WEBSVC	= "WEBSVC";
	public final static String	ACTOR_AP		= "AP";
	
	public final static String	MSGTYPE_REQUEST 	= "REQUEST";
	public final static String	MSGTYPE_RESPONSE 	= "RESPONSE";
	public final static String	MSGTYPE_NOTIFY 		= "NOTIFY";
	
	public final static long	COUNTER32_MAXVALUE = 4294967295L;
	
	public final static int		HTTP_STATUS_SUCCESS = 200;
	
	
	
	

	public final static String	CLOUD_EXEC_SERVER_IP	= "211.224.204.154";
	public final static String	CLOUD_EXEC_SERVER_ID	= "root";
	public final static String	CLOUD_EXEC_SERVER_PW	= "root";	

	public final static String	CLOUD_CONN_ID_OPS	= "user_one";
	public final static String	CLOUD_CONN_ID_AWS	= "BBMC";

	public final static String	CLOUD_AWS_CONN_DOMAIN	= "ec2.amazonaws.com";
	
	public final static String	CLOUD_CONFIG_DIR	= "d:/dev_control/config.dat"; // /home/openstack/dev_control/config.dat
	
	
	
	
}

