package com.ebay.raptor.promotion.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	// CSAPI time format
	private static DateFormat csAPIDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	/**
	 * Return the absolute time lag between two dates.
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long timeLag(Date date1, Date date2) {
		return Math.abs(date1.getTime() - date2.getTime());
	}
	
	/**
	 * Parse the date from CSAPI response.
	 * @param source
	 * @return
	 */
	public static Date parseCSAPIDate(String source) {
		try {
			return csAPIDateFormat.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
