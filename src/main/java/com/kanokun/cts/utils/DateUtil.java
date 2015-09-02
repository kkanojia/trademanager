package com.kanokun.cts.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	public static String[] ALLOWED_FORMATS = new String[] { "yyyy-MM-dd","dd-MMM-yyyy", "dd-MM-yyyy", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
			"dd-MM-yyyy'T'HH:mm:ss.SSSXXX" };

	@SuppressWarnings("deprecation")
	public static Date parseDate(String str) throws ParseException {
		if (str == null || "".equalsIgnoreCase(str.trim())) {
			return null;
		}

		Date dt = null;
		for (String format : ALLOWED_FORMATS) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(format);
				dateFormat.setLenient(false);
				dt = dateFormat.parse(str);
				break;
			} catch (ParseException ex) {
			}
		}

		if (dt == null) {
			LOG.error("Error Parsing Date in date util " + str);
			throw new ParseException("Could not convert date " + str, 0);
		}

		return dt;

	}

}