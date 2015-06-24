package gti350.slalom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormat {
	public String format(long ms) {
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SSS", Locale.CANADA);
		return formatter.format(new Date(ms));
	}
}
