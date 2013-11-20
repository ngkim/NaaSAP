package com.kt.naas.error;

public interface ErrorCodes {
	public final static int	SUCCESS					= 0;
	
	public final static int	SNMPERR_TOO_BIG 		= 1;
	public final static int	SNMPERR_NO_SUCH_NAME 	= 2;
	public final static int	BAD_VALUE 				= 3;
	public final static int	READ_ONLY 				= 4;
	public final static int	GEN_ERROR 				= 5;
	public final static int	NO_ACCESS 				= 6;
	public final static int	WRONG_TYPE 				= 7;
	public final static int	WRONG_LENGTH 			= 8;
	public final static int	WRONG_ENCODING 			= 9;
	public final static int	WRONG_VALUE 			= 10;
	public final static int	NO_CREATION 			= 11;
	public final static int	INCONSISTENT_VALUE 		= 12;
	public final static int	RESOURCE_UNAVALIABLE 	= 13;
	public final static int	COMMIT_FAILED 			= 14;
	public final static int	UNDO_FAILED 			= 15;
	public final static int	AUTHORIZATION_ERROR 	= 16;
	public final static int	NOT_WRITABLE 			= 17;
	public final static int	INCONSISTENT_NAME 		= 18;
	
	public final static int SSL_INIT_FAIL			= 100;
	public final static int EMS_PROC_ERROR			= 101;
	public final static int EMS_COMM_FAIL			= 102;
	public final static int EMS_MSG_DECODING_FAIL	= 103;
	public final static int UNKNOWN_EMS				= 104;
	public final static int NO_MORE_DATA			= 105;
	public final static int DATATYPE_MISMATCH		= 106;
	public final static int SR_COMM_FAIL			= 107;
	
	public final static int INTERNAL_EXCEPTION		= 9999;
	public final static int SNMP_TIMEOUT			= 1001;
	public final static int UNKNOWN_EQUIPID			= 1002;
	public final static int INVALID_REQUEST_DATA	= 1003;
	public final static int UNDEFINED_VALUE			= 1004;
	public final static int SNMP_PROCESSING_ERROR	= 1005;
	public final static int UNKNOWN_DEVICE_TYPE		= 1006;
	public final static int SNMP_INIT_ERROR			= 1007;
	
	
	public final static int	PORT_NOT_EXISTS			= 2001;
	
	public final static int	DUPLICATED_PROFILE_NAME	= 3001;
	public final static int	PROFILE_IN_USE			= 3002;
	public final static int DUPLICATED_EQUIP_IP		= 3003;
	public final static int DUPLICATED_EQUIP_ID		= 3004;
	
}
