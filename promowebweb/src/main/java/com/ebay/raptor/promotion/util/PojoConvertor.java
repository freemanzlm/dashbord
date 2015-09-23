package com.ebay.raptor.promotion.util;


import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;

public class PojoConvertor {

	private static ObjectMapper mapper = new ObjectMapper();
	
	private static Logger logger = Logger.getInstance(PojoConvertor.class);
	
	public static <T> String convertToJson(T obj) {
		if (obj == null) {
			return null;
		}
		
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "Can't convert the obj to string.", e);
		}
		
		return null;
	}
	
	public static <T> T convertToObject(String jsonBean, Class<T> clazz){
		if(StringUtil.isEmpty(jsonBean)){
			return null;
		}
		try{
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T javaBean = mapper.readValue(jsonBean, clazz);
			return javaBean;
		} catch(Exception e){
			logger.log(LogLevel.ERROR, "Cannt convert JSON string to Java object. JSON:" 
						+ jsonBean + " Class: " + clazz, e);
		}
		return null;
	}
	
	public static <T> T convertToObject(String jsonBean, Class<T> clazz, boolean quoted){
		if(StringUtil.isEmpty(jsonBean)){
			return null;
		}
		try{
			if(!quoted){
				mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
			}
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T javaBean = mapper.readValue(jsonBean, clazz);
			return javaBean;
		} catch(Exception e){
			logger.log(LogLevel.ERROR, "Cannt convert JSON string to Java object. JSON:" 
						+ jsonBean + " Class: " + clazz, e);
		}
		return null;
	}

}
