package com.ebay.raptor.promotion.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class WebDateSerializer extends JsonSerializer<Date> {

	private static Date EMPTY_DATE = new Date(0l);
	
	private static String EMPTY_DATE_STR = "-";
	
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		if(value == EMPTY_DATE){
			jgen.writeString(EMPTY_DATE_STR);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		jgen.writeString(sdf.format(value));
	}

}
