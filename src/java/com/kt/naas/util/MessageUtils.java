package com.kt.naas.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

/**
 * 응용 프로그램내에서 로그, 에러메시지 등에 대하여 i18n 처리를 위해<BR>
 * Spring MessageSource 기반으로 메시징 생성을 하기 위한 Utility.<BR>
 * <BR>
 * MessageUtils는 Spring의 application-context에 설정한 후 사용해야 한다.<BR>
 * <BR>
 * 메시지 파일 경로<BR>
 * messages_ko.properties : 한글 메시지<BR>
 * messages_en.properties : 영문 메시지<BR>
 * messages.properties : JVM Default Locale에 해당하는 메시지 파일이 없는 경우 사용되는 기본 메시지<BR> 
 * <BR>
 * 메시지 파일의 경로는 message-source.xml의 설정 내용에 따라 달라질 수 있다.<BR>
 * <BR>
 * 에러코드는 properties 파일에 아래와 같이 정의한다.<BR>
 * ERROR.0=success<BR>
 * ERROR.1001=fail to connect to equip(snmp timeout).<BR>
 * ERROR.1002=unknown equipId.....{0}<BR>
 * 
 * @author 안병규
 *
 */
public class MessageUtils implements MessageSourceAware {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Locale			locale = Locale.getDefault();
	private static MessageSource	messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		logger.info("message source initialized.");
		MessageUtils.messageSource = messageSource;
	}
	
	/**
	 * 에러 메시지를 생성한다.
	 * ERROR.errorCode 로 정의된 메시지에 파라미터를 삽입하여 메시지를 생성한다. 
	 * @param errorCode 에러코드
	 * @param params 에러 메시지에 {0}, {1} 등에 삽입될 내용
	 * @return 생성된 에러 메시지 문자열. 메시지 정의된 내용이 없는경우 "undefined error"
	 */
	public static String getErrorMessage(int errorCode, String ...params)
	{
		String key = "ERROR." + errorCode;
		return getMessage(key, params, "undefined error");
	}

	public static String getMessage(String key, Object[] data, String defaultMsg)
	{
		return getMessage(key, data, defaultMsg, locale);
	}
	
	public static String getMessage(String key, Object[] data, String defaultMsg, Locale locale)
	{
		return messageSource.getMessage(key, data, defaultMsg, locale);
	}
	
	public static String getMessage(String key, Object[] data) throws NoSuchMessageException
	{
		return messageSource.getMessage(key, data, locale);
	}
	
	public static String getMessage(String key, Object[] data, Locale locale) throws NoSuchMessageException
	{
		return messageSource.getMessage(key, data, locale);
	}
}
