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
 * SNMP VariableBinding ���� ���� �����ϴ� ��<BR>
 * SNMP ���̺귯�� ���� �����Ͽ� �ݺ����� �ڵ��� ���̱� ���� Utility.<BR>
 * <BR>
 * ����<BR>
 * SNMP Variable�� Syntex(Value Type)�� ���� ������ ��Ȯ�� Ȯ���ϰ� ����ؾ� �Ѵ�.<BR>
 * gauge ���� int �� ���� ��� �������� �ս� ���ɼ��� �ִ�. <BR>
 * <BR>
 * INT32(-2147483648 ~ 2147483647) -> int<BR>
 * UINT32, COUNTER32, GAUGE(0 ~ 4294967295) -> long<BR>
 * COUNTER64 -> double<BR>
 * <BR>
 * 
 * @author �Ⱥ���
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
	 * SNMP Agent ������ ���� Community ����� ���� Target�� �����Ѵ�.
	 * 
	 * @param ip
	 *            agent IP�ּ�
	 * @param port
	 *            agent ��Ʈ��ȣ
	 * @param community
	 *            agent community
	 * @return SNMP ������ ���� Target
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
	 * VariableBinding �� value part �� ���ڿ��� ��ȯ�Ѵ�. SNMP4J ���̺귯���� toString() ������
	 * DisplayString�� �ƴ� ��� HEXA �ڵ�� ���ϵȴ�. �� �Լ��� �̿��� ��� DisplayString�� �ƴϴ��� ������
	 * String�� �����Ѵ�. �� ����� ���ڿ��� ���Ե� ��� �ǵ����� �ʰ� hexa�� ��µǴ� ������ �ذ��ϱ� ���� ����Ѵ�. ����
	 * VariableBinding�� value part�� SNMP NULL �ΰ�� null String�� �����Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @return ���ڿ� ��, vb�� value part�� snmp null �� ��� null String
	 */
	public static String getStringValue(VariableBinding vb) {
		return getStringValue(vb, null);
	}

	/**
	 * VariableBinding �� value part �� ���ڿ��� ��ȯ�Ѵ�. SNMP4J ���̺귯���� toString() ������
	 * DisplayString�� �ƴ� ��� HEXA �ڵ�� ���ϵȴ�. �� �Լ��� �̿��� ��� DisplayString�� �ƴϴ��� ������
	 * String�� �����Ѵ�. �� ����� ���ڿ��� ���Ե� ��� �ǵ����� �ʰ� hexa�� ��µǴ� ������ �ذ��ϱ� ���� ����Ѵ�. ����
	 * VariableBinding�� value part�� SNMP NULL �ΰ�� null String�� �����Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @param defaultValue
	 *            value part�� snmp null �� ��� ���ϵ� �⺻��
	 * @return ���ڿ� ��, vb�� value part�� snmp null �� ��� defaultValue
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
	 * VariableBinding �� value part �� int ������ ��ȯ�Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @return int ��
	 */
	public static int getIntValue(VariableBinding vb) {
		return getIntValue(vb, GlobalConstants.NULL_INT);
	}

	/**
	 * VariableBinding �� value part �� int ������ ��ȯ�Ѵ�. value part �� snmp null �� ���
	 * defaultValue�� �����Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @param defaultValue
	 * @return int ��
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
	 * VariableBinding �� value part �� long ������ ��ȯ�Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @return long ��
	 */
	public static long getLongValue(VariableBinding vb) {
		return getLongValue(vb, GlobalConstants.NULL_LONG);
	}

	/**
	 * VariableBinding �� value part �� long ������ ��ȯ�Ѵ�. value part �� snmp null �� ���
	 * defaultValue�� �����Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @param defaultValue
	 * @return long ��
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
	 * VariableBinding �� value part �� long ������ ��ȯ�Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @return long ��
	 */
	/***
	 * public static double getDoubleValue(VariableBinding vb) { return
	 * getDoubleValue(vb, GlobalConstants.NULL_DOUBLE); }
	 * 
	 * /** VariableBinding �� value part �� long ������ ��ȯ�Ѵ�. value part �� snmp null
	 * �� ��� defaultValue�� �����Ѵ�.
	 * 
	 * @param vb
	 *            �����͸� ������ VariableBinding
	 * @param defaultValue
	 * @return long ��
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
	 * Description : OctetString ���·� �Ǿ� �ִ� ���� a.b.c.d ������ IP �ּ� ���ڿ��� ��ȯ�Ѵ�.
	 * 70.A0.42.16 -> 112.160.66.22
	 */
	public static String getInetAddress(VariableBinding vb) {
		return getInetAddress(vb, null);
	}

	/*
	 * Description : OctetString ���·� �Ǿ� �ִ� ���� a.b.c.d ������ IP �ּ� ���ڿ��� ��ȯ�Ѵ�.
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
