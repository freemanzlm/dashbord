package com.ebay.raptor.promotion.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author pinchen
 *
 */
public class JsonUtils {
	private static final CommonLogger logger = CommonLogger.getInstance(JsonUtils.class);

	/**
	 * convert Object to jsonString
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJsonString(Object obj) {
		ObjectMapper objectMapper = null;
		String resultJson = null;
		try {
			objectMapper = new ObjectMapper();
			objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			resultJson = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Object to Json error", e);
		}
		return resultJson;
	}

	/**
	 * convert jsonString to Object
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T getBeanFromJsonString(String json, Class<T> cls) {
		ObjectMapper objectMapper = null;
		T obj = null;
		try {
			objectMapper = new ObjectMapper();
			if (StringUtil.isEmpty(json)) {
				return obj;
			}
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			obj = (T) objectMapper.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * get value correspond to target key from a jsonString
	 * 
	 * @param jsonText
	 * @param key
	 * @return
	 */
	public static String parseJson(String jsonText, String key) {
		JsonFactory jsonFactory = new MappingJsonFactory();
		JsonParser jsonParser = null;
		HashMap<String, String> map = null;
		try {
			jsonParser = jsonFactory.createJsonParser(jsonText);
			jsonParser.nextToken();
			map = new HashMap<String, String>();
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				jsonParser.nextToken();
				map.put(jsonParser.getCurrentName(), jsonParser.getText());
			}
		} catch (JsonParseException e) {
			logger.error("--json parser error--", e);
		} catch (IOException e) {
			logger.error("--json parser error--", e);
		}
		return map.get(key) == null ? null : map.get(key);
	}

	/**
	 * get a map from a jsonString
	 * 
	 * @param jsonText
	 * @return
	 */
	public static Map<String, Object> parseJson(String jsonText) {
		if (jsonText == null || jsonText.equals("")) {
			return null;
		}

		JsonFactory jsonFactory = new MappingJsonFactory();
		JsonParser jsonParser = null;
		HashMap<String, Object> map = null;
		try {
			jsonParser = jsonFactory.createJsonParser(jsonText);
			jsonParser.nextToken();
			map = new HashMap<String, Object>();
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				jsonParser.nextToken();
				map.put(jsonParser.getCurrentName(), jsonParser.getText());
			}
		} catch (Exception e) {

		}
		return map == null ? null : map;
	}

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("one", "1111");
		map.put("two", "2222");
		map.put("three", "3333");
		String ret = objectToJsonString(map);
		System.out.println(ret);
	}
}
