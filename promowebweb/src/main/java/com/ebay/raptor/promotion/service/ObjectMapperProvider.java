package com.ebay.raptor.promotion.service;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * GingerClient used Jersey implementation. For JSON support, it used jackson's org.codehaus.jackson.map.ObjectMapper.
 * 
 * @author lyan2
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
	final ObjectMapper defaultObjectMapper;
	
	public ObjectMapperProvider() {
		defaultObjectMapper = createDefaultMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	private static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return result;
    }
}
