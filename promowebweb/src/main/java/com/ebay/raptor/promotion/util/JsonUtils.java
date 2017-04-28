package com.ebay.raptor.promotion.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
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
//		HashMap< String, String> map = new HashMap<String, String>();
//		map.put("one", "1111");
//			map.put("two", "2222");
//				map.put("three", "3333");
//				String ret = objectToJsonString(map);
//				System.out.println(ret);
//		List<HashMap< String, String>> lsArrayList = new ArrayList<HashMap< String, String>>();
//		lsArrayList.add(map);
//		lsArrayList.add(map);
//		lsArrayList.add(map);
//		System.out.println(objectToJsonString(lsArrayList));
		
//		String content = 
//		JSONArray jsonArray = JSONArray.fromObject(content);
//		List<SubsidyCustomField> fields = (List<SubsidyCustomField>) JSONArray.toCollection(jsonArray,SubsidyCustomField.class);
		SubsidyCustomField field = new SubsidyCustomField();
		field.setDisplayLabel("姓名");
		field.setKey("userName");
		field.setValue("");
		field.setInput(false);
		field.setUpload(false);
		field.setRequired(false);
		SubsidyCustomField field1 = new SubsidyCustomField();
		field1.setDisplayLabel("性别");
		field1.setKey("gender");
		field1.setValue("");
		field1.setInput(true);
		field1.setUpload(false);
		field1.setRequired(true);
		SubsidyCustomField field2 = new SubsidyCustomField();
		field1.setDisplayLabel("class");
		field1.setKey("class");
		field1.setValue("");
		field1.setInput(true);
		field1.setUpload(false);
		field1.setRequired(true);
		List<SubsidyCustomField> list = new ArrayList<SubsidyCustomField>();
		list.add(field);
		list.add(field1);
		list.add(field2);
		String retString = objectToJsonString(list);
		System.out.println(retString);
	}
}
