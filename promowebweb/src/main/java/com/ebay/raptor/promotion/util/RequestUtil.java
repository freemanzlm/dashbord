package com.ebay.raptor.promotion.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	public static String getFullRequestUrl (HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();

		Map <String, String[]> params = request.getParameterMap();
		
		if (params.size() > 0) {
			sb.append('?');
		}
		
		boolean isFirst = true;
		
		for (Map.Entry<String, String[]> param : params.entrySet()) {
			String paramKey = param.getKey();
			String [] paramValues = param.getValue();
			
			if (!StringUtil.isEmpty(paramKey) && paramValues.length > 0) {
				for (String value : paramValues) {
					if (!isFirst) {
						sb.append('&');
					} else {
						isFirst = false;
					}
					
					sb.append(paramKey).append('=').append(value);
				}
			}
		}
		
		return sb.toString();
	}
}
