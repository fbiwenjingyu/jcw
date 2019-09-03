package com.ovit.jcw.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class Utils {
	public static String DefaultValue = "defaultValue";

	public static String builderRandomString(int digit, String range) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digit; i++) {
			int number = random.nextInt(range.length());
			sb.append(range.charAt(number));
		}
		return sb.toString();
	}

	public static String decimalFormat(Object object, String pattern) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(object);
	}

	public static String buildTableName(String prefix, String ecid, String suffix) {
		StringBuffer tableName = new StringBuffer();

		tableName.append(prefix);
		tableName.append("_");
		tableName.append(ecid);
		if (suffix == null) {
			tableName.append("_");
			tableName.append(DateUtil.getYesterdayTableNameDate());
		} else if (suffix.length() != 0) {
			tableName.append("_");
			tableName.append(DateUtil.getSpecifiedDateTableNameDate(suffix));
		}
		return tableName.toString();
	}

	public static String buildTableNameByDay(String prefix, String ecid, int day) {
		StringBuffer tableName = new StringBuffer();
		tableName.append(prefix);
		tableName.append("_");
		tableName.append(ecid);
		tableName.append("_");
		tableName.append(DateUtil.getDayTableNameDate(day));
		return tableName.toString();
	}

	public static String buildTableNameByMonth(String prefix) {
		StringBuffer tableName = new StringBuffer();
		tableName.append(prefix);
		tableName.append("_");
		tableName.append(getMonthTableName());
		return tableName.toString();
	}

	public static String getMonthTableName() {
		String tableNameByMonth = null;
		try {
			Date systemCurrentDate = new SimpleDateFormat("yyyyMM").parse(getDateStringByPattern("yyyyMM"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemCurrentDate);
			tableNameByMonth = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableNameByMonth;
	}

	public static String getDateStringByPattern(String pattern) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		sDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		return sDateFormat.format(new Date());
	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		return Pattern.compile("[0-9]*").matcher(str.trim()).matches();
	}

	public static Map<String, Object> toMapFromObject(Object obj) {
		Map<String, Object> reMap = new HashMap();
		if (obj == null) {
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}

	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static String listToString(List<String> list, String separator) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			if ((s != null) && (!"".equals(s))) {
				sb.append(s).append(separator);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String formatTime(Date date) {
		String time = "";
		if (date != null) {
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
			time = sm.format(date);
		}
		return time;
	}

	public static void main(String[] args) {
	}

	public static long compareTime(String time1, String time2) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date1 = sf.parse(time1);
			Date date2 = sf.parse(time2);
			long t1 = date1.getTime();
			long t2 = date2.getTime();
			return (t1 - t2) / 1000L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public static String formatTime(String time) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sf.parse(time);
			return sf2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parsePercent(float number) {
		DecimalFormat df = new DecimalFormat("0.000");
		String s = df.format(number);
		return s;
	}

	public static Connection getConnection(String url, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static String getCal(int ind) {
		Calendar cal = Calendar.getInstance();
		Date curr = null;
		SimpleDateFormat dateFormater_t = new SimpleDateFormat("yyyyMMdd");
		cal.set(5, cal.get(5) + ind);
		curr = cal.getTime();
		String dayStr = dateFormater_t.format(curr);
		return dayStr;
	}

	public static String randomStringByUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static Boolean chargeMapBlack(Map map) {
		Boolean flag = Boolean.valueOf(false);
		Iterator values = map.values().iterator();
		while (values.hasNext()) {
			String value = (String) values.next();
			if (StringUtils.isNoneEmpty(new CharSequence[]{value})) {
				flag = Boolean.valueOf(true);
			}
		}
		return flag;
	}
}