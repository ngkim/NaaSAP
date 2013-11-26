package com.kt.naas.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Target;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.kt.naas.GlobalConstants;

/**
 * SNMP VariableBinding 에서 값을 추출하는 등<BR>
 * SNMP 라이브러리 사용과 관련하여 반복적인 코딩을 줄이기 위한 Utility.<BR>
 * <BR>
 * 주의<BR>
 * SNMP Variable의 Syntex(Value Type)별 값의 범위를 명확히 확인하고 사용해야 한다.<BR>
 * gauge 값을 int 로 받을 경우 데이터의 손실 가능성이 있다. <BR>
 * <BR>
 * INT32(-2147483648 ~ 2147483647) -> int<BR>
 * UINT32, COUNTER32, GAUGE(0 ~ 4294967295) -> long<BR>
 * COUNTER64 -> double<BR>
 * <BR>
 * 
 * @author 안병규
 * 
 */
public class SnmpUtils {

	// Execute VLAN Swapping for a given port to a target vlan
	public static void vlanSwap(String snmp_agent, String port_id_to_swap,
			int target_vlan) {

		String cmd_vlan_swap = "";
		cmd_vlan_swap = "snmpset -v 2c -c private " + snmp_agent + " vmVlan."
				+ port_id_to_swap + " integer " + target_vlan;

		Process p;
		String s = null;
		try {
			p = Runtime.getRuntime().exec(cmd_vlan_swap);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((s = br.readLine()) != null)
				System.out.println("line: " + s);
			p.waitFor();

			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * SNMP Agent 연결을 위한 Community 기반의 연결 Target을 생성한다.
	 * 
	 * @param ip
	 *            agent IP주소
	 * @param port
	 *            agent 포트번호
	 * @param community
	 *            agent community
	 * @return SNMP 연결을 위한 Target
	 */
	public static Target getCommunityTarget(String ip, int port,
			String community) {
		CommunityTarget target = new CommunityTarget();
		target.setVersion(GlobalConstants.SNMP_VER2C);
		target.setCommunity(new OctetString(community));
		target.setAddress(new UdpAddress(ip + "/" + port));
		target.setRetries(PropertyUtils.getInt("SNMP.READRETRY"));
		target.setTimeout(PropertyUtils.getInt("SNMP.READTIMEOUT"));
		return target;
	}

	/**
	 * VariableBinding 의 value part 를 문자열로 변환한다. SNMP4J 라이브러리의 toString() 에서는
	 * DisplayString이 아닌 경우 HEXA 코드로 리턴된다. 본 함수를 이용할 경우 DisplayString이 아니더라도 원래의
	 * String을 리턴한다. 비 영어권 문자열이 포함된 경우 의도하지 않게 hexa로 출력되는 문제를 해결하기 위해 사용한다. 또한
	 * VariableBinding의 value part가 SNMP NULL 인경우 null String을 리턴한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @return 문자열 값, vb의 value part가 snmp null 인 경우 null String
	 */
	public static String getStringValue(VariableBinding vb) {
		return getStringValue(vb, null);
	}

	/**
	 * VariableBinding 의 value part 를 문자열로 변환한다. SNMP4J 라이브러리의 toString() 에서는
	 * DisplayString이 아닌 경우 HEXA 코드로 리턴된다. 본 함수를 이용할 경우 DisplayString이 아니더라도 원래의
	 * String을 리턴한다. 비 영어권 문자열이 포함된 경우 의도하지 않게 hexa로 출력되는 문제를 해결하기 위해 사용한다. 또한
	 * VariableBinding의 value part가 SNMP NULL 인경우 null String을 리턴한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @param defaultValue
	 *            value part가 snmp null 인 경우 리턴될 기본값
	 * @return 문자열 값, vb의 value part가 snmp null 인 경우 defaultValue
	 */
	public static String getStringValue(VariableBinding vb, String defaultValue) {
		if (vb == null)
			return defaultValue;
		Variable v = vb.getVariable();

		if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL
				|| v.getSyntax() >= 128)
			return defaultValue;

		switch (v.getSyntax()) {
		case SMIConstants.SYNTAX_OCTET_STRING:
			OctetString o = (OctetString) v;
			byte[] buf = o.toByteArray();
			return new String(buf).trim();
		case SMIConstants.SYNTAX_NULL:
			return defaultValue;

		default:
			return v.toString();
		}
	}

	/**
	 * VariableBinding 의 value part 를 int 값으로 변환한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @return int 값
	 */
	public static int getIntValue(VariableBinding vb) {
		return getIntValue(vb, GlobalConstants.NULL_INT);
	}

	/**
	 * VariableBinding 의 value part 를 int 값으로 변환한다. value part 가 snmp null 인 경우
	 * defaultValue를 리턴한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @param defaultValue
	 * @return int 값
	 */
	public static int getIntValue(VariableBinding vb, int defaultValue) {
		if (vb == null)
			return defaultValue;
		Variable v = vb.getVariable();

		if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL
				|| v.getSyntax() >= 128)
			return defaultValue;

		return v.toInt();
	}

	/**
	 * VariableBinding 의 value part 를 long 값으로 변환한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @return long 값
	 */
	public static long getLongValue(VariableBinding vb) {
		return getLongValue(vb, GlobalConstants.NULL_LONG);
	}

	/**
	 * VariableBinding 의 value part 를 long 값으로 변환한다. value part 가 snmp null 인 경우
	 * defaultValue를 리턴한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @param defaultValue
	 * @return long 값
	 */
	public static long getLongValue(VariableBinding vb, long defaultValue) {
		if (vb == null)
			return defaultValue;
		Variable v = vb.getVariable();

		if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL
				|| v.getSyntax() >= 128)
			return defaultValue;

		return v.toLong();
	}

	/**
	 * VariableBinding 의 value part 를 long 값으로 변환한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @return long 값
	 */
	/***
	 * public static double getDoubleValue(VariableBinding vb) { return
	 * getDoubleValue(vb, GlobalConstants.NULL_DOUBLE); }
	 * 
	 * /** VariableBinding 의 value part 를 long 값으로 변환한다. value part 가 snmp null
	 * 인 경우 defaultValue를 리턴한다.
	 * 
	 * @param vb
	 *            데이터를 추출한 VariableBinding
	 * @param defaultValue
	 * @return long 값
	 */

	/***
	 * public static double getDoubleValue(VariableBinding vb, long
	 * defaultValue) { if (vb == null) return defaultValue; Variable v =
	 * vb.getVariable();
	 * 
	 * if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL ||
	 * v.getSyntax() >= 128) return defaultValue;
	 * 
	 * return v.toLong(); }
	 */

	public static String getHexString(VariableBinding vb) {
		if (vb == null)
			return null;
		Variable v = vb.getVariable();

		if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL
				|| v.getSyntax() >= 128)
			return null;

		int syntax = v.getSyntax();
		if (syntax == SMIConstants.SYNTAX_OCTET_STRING
				|| syntax == SMIConstants.SYNTAX_OPAQUE
				|| syntax == SMIConstants.SYNTAX_IPADDRESS
				|| syntax == SMIConstants.SYNTAX_BITS) {
			OctetString o = (OctetString) v;
			return o.toHexString();
		}
		return null;

	}

	public static List<OID> getInstanceOids(
			Map<OID, Map<String, VariableBinding>> resultMap) {
		List<OID> ret = new ArrayList<OID>();
		if (resultMap == null || resultMap.size() == 0)
			return ret;

		SortedSet<OID> oids = new TreeSet<OID>(resultMap.keySet());
		for (OID oid : oids) {
			ret.add(oid);
		}

		return ret;
	}

	public static OID getOidFromString(String src) {
		OctetString oct = new OctetString(src);
		byte[] bArray = oct.toByteArray();
		int[] iArray = new int[bArray.length];
		for (int i = 0; i < bArray.length; i++) {
			int iValue = bArray[i];
			if (iValue < 0)
				iValue += 256;
			iArray[i] = iValue;
		}
		return new OID(iArray);
	}

	public static String getStringFromOid(OID src) {
		return new String(src.toByteArray());
	}

	/*
	 * Description : OctetString 형태로 되어 있는 값을 a.b.c.d 형태의 IP 주소 문자열로 변환한다.
	 * 70.A0.42.16 -> 112.160.66.22
	 */
	public static String getInetAddress(VariableBinding vb) {
		return getInetAddress(vb, null);
	}

	/*
	 * Description : OctetString 형태로 되어 있는 값을 a.b.c.d 형태의 IP 주소 문자열로 변환한다.
	 * 70.A0.42.16 -> 112.160.66.22
	 */
	public static String getInetAddress(VariableBinding vb, String defaultValue) {
		if (vb == null)
			return defaultValue;
		Variable v = vb.getVariable();

		if (v == null || v.getSyntax() == SMIConstants.SYNTAX_NULL
				|| v.getSyntax() >= 128)
			return defaultValue;

		switch (v.getSyntax()) {

		case SMIConstants.SYNTAX_OCTET_STRING:
			OctetString o = (OctetString) v;
			byte[] buf = o.toByteArray();
			return new IpAddress(buf).toString();

		case SMIConstants.SYNTAX_NULL:
			return defaultValue;

		default:
			return v.toString();
		}
	}
}
