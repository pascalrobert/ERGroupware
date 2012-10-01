package er.groupware.exchange.ews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author probert
 *
 */
public class DataTypeBinder {

	private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
	private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd"); 

	public static Calendar unmarshalDate(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		Date d = null;

		try {
			d = date.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	public static  String marshalDate(Calendar value) {
		if (value == null) {
			return null;
		}

		return date.format(value.getTime());
	}   

	public static  String marshalDateTime(Calendar value) {
		if (value == null) {
			return null;
		}

		return dateTime.format(value.getTime());
	}  

	public static Calendar unmarshalDateTime(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		Date d = null;

		try {
			d = dateTime.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

}
