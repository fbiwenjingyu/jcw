package com.ovit.jcw.utils;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_MONTH_PATTERN = "yyyy-MM";

	public static boolean comparisonDateTime(String data1, String data2, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try {
			calendar1.setTime(dateFormat.parse(data1));
			calendar2.setTime(dateFormat.parse(data2));

			int result = calendar1.compareTo(calendar2);
			if (result == 0) {
				return false;
			}
			if (result < 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getDateStringByPattern(String pattern) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		sDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		return sDateFormat.format(new Date());
	}

	public static String formatStringDateByPattern(String date, String pattern) {
		try {
			if (date == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

			return sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}

	public static String getYesterday() {
		String yesterday = null;
		try {
			Date systemCurrentDate = new SimpleDateFormat("yyyyMMdd").parse(getDateStringByPattern("yyyyMMdd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemCurrentDate);
			calendar.set(11, -1);
			yesterday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yesterday;
	}

	public static String getYesterdayTableNameDate() {
		String tableNameDateByYesterday = null;
		try {
			Date systemCurrentDate = new SimpleDateFormat("yyyyMMdd").parse(getDateStringByPattern("yyyyMMdd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemCurrentDate);
			calendar.set(11, -1);
			tableNameDateByYesterday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableNameDateByYesterday;
	}

	public static String getSpecifiedDateTableNameDate(String specifiedDate) {
		String yesterday = getYesterdayTableNameDate();
		String specifiedDate_ = specifiedDate.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$1$2$3");
		if (!comparisonDateTime(specifiedDate_, yesterday, "yyyyMMdd")) {
			return yesterday;
		}
		return specifiedDate_;
	}

	public static String getDayTableNameDate(int day) {
		String tableNameDateByYesterday = null;
		try {
			Date systemCurrentDate = new SimpleDateFormat("yyyyMMdd").parse(getDateStringByPattern("yyyyMMdd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemCurrentDate);
			calendar.add(5, day);
			tableNameDateByYesterday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableNameDateByYesterday;
	}

	public static String getBefore3th(int day) {
		String tableNameDateByYesterday = null;
		try {
			Date systemCurrentDate = new SimpleDateFormat("yyyy-MM-dd").parse(getDateStringByPattern("yyyy-MM-dd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemCurrentDate);
			calendar.add(5, day);
			tableNameDateByYesterday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableNameDateByYesterday;
	}

	public static boolean isFirstDayMonth() {
		Calendar c = Calendar.getInstance();
		int date = c.get(5);
		if ((date > 0) && (date == 1)) {
			return true;
		}
		return false;
	}

	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(2, -1);
		Date date = cal.getTime();
		return getDate(date, "yyyy-MM");
	}

	public static void main(String[] args) {
		String s = getDate(new Date(), "YMM");
		System.out.println(s);
	}
}