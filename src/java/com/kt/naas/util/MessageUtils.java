package com.kt.naas.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

/**
 * ���� ���α׷������� �α�, �����޽��� � ���Ͽ� i18n ó���� ����<BR>
 * Spring MessageSource ������� �޽�¡ ������ �ϱ� ���� Utility.<BR>
 * <BR>
 * MessageUtils�� Spring�� application-context�� ������ �� ����ؾ� �Ѵ�.<BR>
 * <BR>
 * �޽��� ���� ���<BR>
 * messages_ko.properties : �ѱ� �޽���<BR>
 * messages_en.properties : ���� �޽���<BR>
 * messages.properties : JVM Default Locale�� �ش��ϴ� �޽��� ������ ���� ��� ���Ǵ� �⺻ �޽���<BR> 
 * <BR>
 * �޽��� ������ ��δ� message-source.xml�� ���� ���뿡 ���� �޶��� �� �ִ�.<BR>
 * <BR>
 * �����ڵ�� properties ���Ͽ� �Ʒ��� ���� �����Ѵ�.<BR>
 * ERROR.0=success<BR>
 * ERROR.1001=fail to connect to equip(snmp timeout).<BR>
 * ERROR.1002=unknown equipId.....{0}<BR>
 * 
 * @author �Ⱥ���
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
	 * ���� �޽����� �����Ѵ�.
	 * ERROR.errorCode �� ���ǵ� �޽����� �Ķ���͸� �����Ͽ� �޽����� �����Ѵ�. 
	 * @param errorCode �����ڵ�
	 * @param params ���� �޽����� {0}, {1} � ���Ե� ����
	 * @return ������ ���� �޽��� ���ڿ�. �޽��� ���ǵ� ������ ���°�� "undefined error"
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
