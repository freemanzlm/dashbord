package com.ebay.raptor.promotion.util;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Convert JSON string to object or convert object into JSON string.
 * @author lyan2
 */
public class PojoConvertor {

	private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
	}
	
	private static Logger logger = Logger.getInstance(PojoConvertor.class);
	
	/**
	 * Serialize a object into JSON string.
	 * @param obj
	 * @return
	 */
	public static <T> String convertToJson(T obj) {
		try {
			return obj == null ? null : mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "Can't convert the obj to string.", e);
		}
		
		return null;
	}
	
	/**
	 * Deserialize a JSON string into a object.
	 * @param jsonBean
	 * @param clazz
	 * @return
	 */
	public static <T> T convertToObject(String jsonBean, Class<T> clazz){
		try{
			return (jsonBean == null || jsonBean.isEmpty()) ? null : mapper.readValue(jsonBean, clazz);
		} catch(Exception e){
			logger.log(LogLevel.ERROR, "Cannt convert JSON string to Java object. JSON:" 
						+ jsonBean + " Class: " + clazz, e);
		}
		return null;
	}

}
