package com.ebay.raptor.promotion.util;

public class StringUtil {

	public static boolean isEmpty(String content){
		return null == content || content.isEmpty();
	}
	
	/**
	 * Repeat a segment of text and return the result.
	 * @param source
	 * @param times
	 * @return
	 */
	public static String repeat(String source, int times) {
		StringBuffer buffer = new StringBuffer();
		while(times > 0) {
			buffer.append(source);
			times--;
		}
		
		return buffer.toString();
	}
	
}
