package com.kt.naas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * �ð��� ��õ� ���ڿ� ó���� ���� Utility
 * @author �Ⱥ���
 */
public class TimeUtils {
	public final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public final static SimpleDateFormat DATENUM_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH");
	public final static SimpleDateFormat MIN_FORMAT = new SimpleDateFormat("mm");
	public final static SimpleDateFormat SEC_FORMAT = new SimpleDateFormat("ss");
	
	/* 
	// ���ڿ� ���� ����
	Letter	Date or Time Component	Presentation		Examples
	--------------------------------------------------------------------
	G		Era Designator			Text				Ad
	y		Year					Year				1999; 96
	M		Month in year			Month				July; Jul; 07
	w		Week in year			Number				27
	W		Week in month			Number				2
	D		Day in year				Number				189
	d		Day in month			Number				10
	F		Day of week in month	Number				2
	E		Day in week				Text				Tuesday; Tue
	a		Am/pm marker			Text				PM
	H		Hour in day (0-23)		Number				0
	k		Hour in day (1-24)		Number				24
	K		Hour in am/pm (0-11)	Number				0
	h		Hour in am/pm (1-12)	Number				12
	m		Minute in hour			Number				30
	s		Second in minute		Number				55
	S		Millisecond				Number				978
	z		Time zone				General time zone	Pacific Standard Time; PST; GMT-08:00
	Z		Time zone				RFC 822 time zone	-0800
	--------------------------------------------------------------------
	*/
	
	private long start_time; 
	
	public void setStartTime() {
		start_time =  System.nanoTime();
	}
	
	public double getDuration() {
		long end_time = System.nanoTime();
		
		double duration = (end_time - start_time)/1e6;
		
		return duration;
	}
	
	/**
	 * milliseconds long ���� [2010-11-01 11:22:33] ������ String ���� ��ȯ
	 */
	public static String getDateTimeString(long ltime)
	{
		return DATE_TIME_FORMAT.format(new Date(ltime));
	}

	/**
	 * milliseconds long ���� [2010-11-01] ������ String ���� ��ȯ
	 */
	public static String getDateString(long ltime)
	{
		return DATE_FORMAT.format(new Date(ltime));
	}

	/**
	 * milliseconds long ���� [11:22:33] ������ String ���� ��ȯ
	 */
	public static String getTimeString(long ltime)
	{
		return TIME_FORMAT.format(new Date(ltime));
	}
	
	/**
	 * milliseconds long ������ ��¥ �κ��� int ���·� ��ȯ(20100101)
	 */
	public static int getDateInt(long ltime)
	{
		return Integer.parseInt(DATENUM_FORMAT.format(new Date(ltime)));
	}

	/**
	 * milliseconds long ������ �ð��� int ���·� ��ȯ
	 */
	public static int getHourInt(long ltime)
	{
		return Integer.parseInt(HOUR_FORMAT.format(new Date(ltime)));
	}

	/**
	 * milliseconds long ������ ���� int ���·� ��ȯ
	 */
	public static int getMinuteInt(long ltime)
	{
		return Integer.parseInt(MIN_FORMAT.format(new Date(ltime)));
	}
	
	/**
	 * milliseconds long ������ �ʸ� int ���·� ��ȯ
	 */
	public static int getSecondInt(long ltime)
	{
		return Integer.parseInt(SEC_FORMAT.format(new Date(ltime)));
	}
	
	/**
	 * [2010-11-01 11:22:33] ������ String �� milliseconds long ������ ��ȯ
	 * @param stime 2010-11-01 11:22:33 ������ ���ڿ�
	 * @return ���ڿ��� �ش��ϴ� miliseconds ��(long), ���ڿ� �Ľ��� ������ ��쿡�� -1L
	 */
	public static long getDateTimeLong(String stime)
	{
		try {
			Date d = DATE_TIME_FORMAT.parse(stime);
			return d.getTime();
		} catch (ParseException e) {
			return -1L;
		}
	}
	
	/**
	 * �Է¹��� ��¥(��, ��)�� �������� YYYY-MM-DD ���·� �����ֱ�
	 * nowDate �� ���̸� ����ð� �̿�
	 * ex) getChangeDay( 7,"2011-04-04 00:00:00") ��� �ϸ� 2011-04-11 ���� ����
	 * ex) getChangeDay(-7,"2011-04-04 00:00:00") ��� �ϸ� 2011-03-28 ���� ����
	 * @author james
	 * @param tmpDay
	 * @param nowDate (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getChangeDay(int tmpDay, String nowDate) {
		java.util.Date trialTime = null;
		Calendar cal = Calendar.getInstance ();
		if(nowDate == null || nowDate.equals("")){
			trialTime = new java.util.Date();
		}else{
			try {
				trialTime = DATE_TIME_FORMAT.parse(nowDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cal.setTime(trialTime);

		// �Ϸ� �� ��¥�� ��� �ϱ� ���� �޼ҵ�
		cal.add(Calendar.DATE, tmpDay);
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String date = String.valueOf(cal.get(Calendar.DATE));
		if (month.length() < 2)
			month = "0" + month;
		if (date.length() < 2)
			date = "0" + date;

		return year + "-" + month + "-" + date;
	}
	
	/**
	 * �� ��¥ ������ ���� �����Ѵ�.
	 * ex) fromDate = "2011-04-04 00:00:00"
	 *     toDate   = "2011-03-04 00:00:00" �̸� ���� 31 �� �����Ѵ�.
	 * @author james
	 * @param fromDate (yyyy-mm-dd hh24:mi:ss)
	 * @param toDate (yyyy-mm-dd hh24:mi:ss)
	 * @return
	 */
	public static int getDayDiff(String fromDate, String toDate) {
		// ��¥�� long���� ǥ���˴ϴ�. �� ��¥ ������ �ð��� ���մϴ�.
		long between = -1;
		try {
			between = getDateTimeLong(fromDate) - getDateTimeLong(toDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �� ��¥ ������ �� ���� ���մϴ�. �Ϸ�� 86400���̰� 1�ʴ� 1000�и����̱� ������
		// ������ ���� between�� 86400000���� �����ϴ�.
		// System.out.println("result: " + (between / 86400000));

		return (int) (between / 86400000);
	}
}
