package com.kt.naas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 시간과 관련된 문자열 처리를 위한 Utility
 * @author 안병규
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
	// 문자열 패턴 참조
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
	
	/**
	 * milliseconds long 값을 [2010-11-01 11:22:33] 형태의 String 으로 변환
	 */
	public static String getDateTimeString(long ltime)
	{
		return DATE_TIME_FORMAT.format(new Date(ltime));
	}

	/**
	 * milliseconds long 값을 [2010-11-01] 형태의 String 으로 변환
	 */
	public static String getDateString(long ltime)
	{
		return DATE_FORMAT.format(new Date(ltime));
	}

	/**
	 * milliseconds long 값을 [11:22:33] 형태의 String 으로 변환
	 */
	public static String getTimeString(long ltime)
	{
		return TIME_FORMAT.format(new Date(ltime));
	}
	
	/**
	 * milliseconds long 값에서 날짜 부분을 int 형태로 변환(20100101)
	 */
	public static int getDateInt(long ltime)
	{
		return Integer.parseInt(DATENUM_FORMAT.format(new Date(ltime)));
	}

	/**
	 * milliseconds long 값에서 시간을 int 형태로 변환
	 */
	public static int getHourInt(long ltime)
	{
		return Integer.parseInt(HOUR_FORMAT.format(new Date(ltime)));
	}

	/**
	 * milliseconds long 값에서 분을 int 형태로 변환
	 */
	public static int getMinuteInt(long ltime)
	{
		return Integer.parseInt(MIN_FORMAT.format(new Date(ltime)));
	}
	
	/**
	 * milliseconds long 값에서 초를 int 형태로 변환
	 */
	public static int getSecondInt(long ltime)
	{
		return Integer.parseInt(SEC_FORMAT.format(new Date(ltime)));
	}
	
	/**
	 * [2010-11-01 11:22:33] 형태의 String 을 milliseconds long 값으로 변환
	 * @param stime 2010-11-01 11:22:33 형태의 문자열
	 * @return 문자열에 해당하는 miliseconds 값(long), 문자열 파싱을 실해한 경우에는 -1L
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
	 * 입력받은 날짜(전, 후)를 증감시켜 YYYY-MM-DD 형태로 돌려주기
	 * nowDate 가 널이면 현재시간 이용
	 * ex) getChangeDay( 7,"2011-04-04 00:00:00") 라고 하면 2011-04-11 값이 리턴
	 * ex) getChangeDay(-7,"2011-04-04 00:00:00") 라고 하면 2011-03-28 값이 리턴
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

		// 하루 전 날짜로 계산 하기 위한 메소드
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
	 * 두 날짜 사이의 값을 리턴한다.
	 * ex) fromDate = "2011-04-04 00:00:00"
	 *     toDate   = "2011-03-04 00:00:00" 이면 결과는 31 을 리턴한다.
	 * @author james
	 * @param fromDate (yyyy-mm-dd hh24:mi:ss)
	 * @param toDate (yyyy-mm-dd hh24:mi:ss)
	 * @return
	 */
	public static int getDayDiff(String fromDate, String toDate) {
		// 날짜는 long으로 표현됩니다. 두 날짜 사이의 시간을 구합니다.
		long between = -1;
		try {
			between = getDateTimeLong(fromDate) - getDateTimeLong(toDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 두 날짜 사이의 일 수를 구합니다. 하루는 86400초이고 1초는 1000밀리초이기 때문에
		// 위에서 구한 between을 86400000으로 나눕니다.
		// System.out.println("result: " + (between / 86400000));

		return (int) (between / 86400000);
	}
}
