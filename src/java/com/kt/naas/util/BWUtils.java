package com.kt.naas.util;

public class BWUtils {

	public static int bwToInt(String strBw) {
		int bw = 0;

		String lowerStrBw = strBw.trim().toLowerCase();
		if (lowerStrBw.endsWith("gbps")) {
			bw = 1000 * 1000;
		} else if (lowerStrBw.endsWith("mbps")) {
			String strBwValue = lowerStrBw.substring(0,
					lowerStrBw.indexOf("mbps"));

			System.out.println(strBwValue);
			int bwValue = Integer.parseInt(strBwValue);
			bw = bwValue * 1000;
		}
		return bw;
	}

	public static String bwToStr(int bytes) {
		String strBw = "";

		int unit = 1000;
		if (bytes < unit)
			return bytes + " B";
		
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = ("KMGTPE").charAt(exp - 1) + "";
		strBw = String.format("%f%s", bytes / Math.pow(unit, exp), pre);

		return strBw;
	}

}
