package model;

import java.util.Calendar;

public class CalendarManager {
	private static final String[] MONTH_NAMES = {"Janurary", "Feburary", "March", "April", "May", "June", "July", "August", 
			"September", "October", "November", "December"};
	private static final String[] AM_PM = {"AM", "PM"};
	
	/**
	 * Returns a string corresponding to the current date using default time zone.
	 * @return String "YEAR, MONTH DAY_OF_MONTH, HOUR:MINUTE:SECOND AM/PM"
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		
		int year = + c.get(Calendar.YEAR);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		
		int hour = c.get(Calendar.HOUR);
		if (hour == 0) hour = 12;
		
		int minute = c.get(Calendar.MINUTE);
		String minuteString;
		if (minute <= 9) {
			minuteString = "0" + minute;
		}else {
			minuteString = "" + minute;
		}
		
		int second = c.get(Calendar.SECOND);
		String secondString;
		if (second <= 9) {
			secondString = "0" + second;
		}else {
			secondString = "" + second;
		}
		
		String meridiem = AM_PM[c.get(Calendar.AM_PM)];
		String month = MONTH_NAMES[c.get(Calendar.MONTH)];
		
		String date = year + ", " + month + " " + dayOfMonth + ", " + hour + ":" + minuteString + ":" + secondString + " " + meridiem;
		
		return date;
	}
	
}
