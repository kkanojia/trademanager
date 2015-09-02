package com.kanokun.cts.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class CustomDateDeserialize extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException,
			JsonProcessingException {

		String str = paramJsonParser.getText().trim();

		if (str == null || "".equalsIgnoreCase(str)) {
			return null;
		}
		Date dt = null;
		try {
			dt = DateUtil.parseDate(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (dt != null) {
			return dt;
		}

		return paramDeserializationContext.parseDate(str);
	}
}