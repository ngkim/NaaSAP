package com.kt.naas.util;

public class StringUtils {
	public static String makeOpenProductId(String serviceId, String genreId, String contentId, String channelId)
	{
		if (genreId == null) genreId = "";
		if (contentId == null) contentId = "";
		if (channelId == null) channelId = "";
		
		String s = serviceId + genreId + contentId + channelId;
		long hash = s.hashCode();
		if (hash < 0) hash+=4294967296L;
		
		String id = getFixedSizeString(serviceId, '_', 16)
					+ getFixedSizeString(genreId, '_', 8)
					+ getFixedSizeString(contentId, '_', 32)
					+ getFixedSizeString(channelId, '_', 24)
					+ String.format("%010d", hash);
					
		
		
		return id;
	}
	
	public static String getFixedSizeString(String original, char whitespace, int size)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(original);
		
		for (int i = buf.length(); i < size; i++)
			buf.append(whitespace);
		
		return buf.substring(0, size);
	}
	
	public static void main(String[] args) {
		System.out.println(getFixedSizeString("test", '_', 10));
		
		System.out.println(makeOpenProductId("Channel_1313", "ENTER", "kbs_Channel_1313_20110915130005_1236", ""));
		System.out.println(makeOpenProductId("Channel_1313", "ENTER", "kbs_Channel_1313_20110915130005_1239", ""));
		System.out.println(makeOpenProductId("Channel_1313", "REPORT", "kbs_Channel_1313_20110915130005_1241", ""));
		
	}
	
	public static String changeDomain(String url, String ip)
	{
		String protocol = "";
		String domain = "";
		String contents = "";

		int domainPos = url.indexOf("://");
		
		if (domainPos > 0) 
		{
			domainPos += 3;
			protocol = url.substring(0, domainPos);
		}
		else
			domainPos = 0;
		
		int contentsPos = url.indexOf('/', domainPos);
		if (contentsPos == -1)
		{
			System.err.println("비정상적인 URL:[" + url + "]");
			return url;
		}
		domain = url.substring(domainPos, contentsPos);
		contents = url.substring(contentsPos);
		System.out.println(String.format("URL확인\nurl=[%s]\nprotocol=[%s], domain=[%s], contents=[%s]", url, protocol, domain, contents));
		
		return protocol+ip+contents;
	}
	
	
	public static String addDelimiter(String hex)
	{
		if (hex == null) return null;
		if (hex.length() == 0) return "";
		if (hex.length() % 2 != 0)
			hex += "0";
		
		StringBuffer buf = new StringBuffer();
		
		for (int i = 0; i < hex.length(); i++) {
			if (i != 0 && i % 2 == 0)
				buf.append(':');

			buf.append(hex.charAt(i));
		}	
		
		return buf.toString();
	}
}
