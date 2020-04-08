/**    
 * 版本信息：    腾云GAME 
 * Copyright 足下 Corporation 2012     
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.model.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static boolean isSameWeek(int year, int week, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return (year == cal.get(1)) && (week == cal.get(3));
	}

	public static boolean isSameWeek(Date time, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return isSameWeek(cal.get(1), cal.get(3), firstDayOfWeek);
	}

	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(7);

		if (day == firstDayOfWeek)
			day = 0;
		else if (day < firstDayOfWeek)
			day += 7 - firstDayOfWeek;
		else if (day > firstDayOfWeek) {
			day -= firstDayOfWeek;
		}

		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);

		cal.add(5, -day);
		return cal.getTime();
	}

	/**
	 * 判断是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		Date end = cal.getTime();
		cal.add(14, -1);
		cal.add(5, -1);
		Date start = cal.getTime();
		return (date.after(start)) && (date.before(end));
	}

	public static String date2String(Date theDate, String datePattern) {
		if (theDate == null) {
			return "";
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.format(theDate);
		} catch (Exception e) {
			log.info("时间转换异常：" + e);
		}
		return "";
	}

	public static Date string2Date(String dateString, String datePattern) {
		if ((dateString == null) || (dateString.trim().isEmpty())) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			log.error("ParseException in Converting String to date: " + e.getMessage());
		}

		return null;
	}

	public static long toMillisSecond(long[] seconds) {
		long millis = 0L;
		if ((seconds != null) && (seconds.length > 0)) {
			long[] arrayOfLong = seconds;
			int j = seconds.length;
			for (int i = 0; i < j; i++) {
				long time = arrayOfLong[i];
				millis += time * 1000L;
			}
		}
		return millis;
	}

	public static long toSecond(long... millis) {
		long second = 0L;
		if ((millis != null) && (millis.length > 0)) {
			long[] arrayOfLong = millis;
			int j = millis.length;
			for (int i = 0; i < j; i++) {
				long time = arrayOfLong[i];
				second += time / 1000L;
			}
		}
		return second;
	}

	public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(5, addDays);

		if ((hour >= 0) && (hour <= 24)) {
			cal.set(11, hour);
		}
		if ((minute >= 0) && (minute <= 60)) {
			cal.set(12, minute);
		}
		if ((second >= 0) && (second <= 60)) {
			cal.set(13, second);
		}

		return cal.getTime();
	}

	public static Date add(Date theDate, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(11, addHours);
		cal.add(12, addMinutes);
		cal.add(13, addSecond);

		return cal.getTime();
	}

	public static int dayOfWeek(Date theDate) {
		if (theDate == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		return cal.get(7);
	}

	public static int getNextDay(Date theDate) {
		if (theDate == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		return cal.get(1);
	}

	/**
	 * 根据传来的天数 获取一个新时间
	 * 
	 * @param theDate
	 *            当前时间
	 * @param day
	 *            天数
	 * @return
	 */
	public static Date getByDayNewDay(Date theDate, int day) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(5, day);

		return cal.getTime();
	}

	/**
	 * 获取当前时间凌晨时间
	 * 
	 * @param theDate
	 * @return
	 */
	public static Date getDate0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5)).getTime();
	}

	public static Date getNextDay0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(theDate.getTime() + 86400000L);
		return new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5)).getTime();
	}

	public static Date getThisDay2359PM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		long millis = theDate.getTime() + 86400000L - 1000L;
		cal.setTimeInMillis(millis);
		Date date = new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5)).getTime();
		return new Date(date.getTime() - 1000L);
	}

	/**
	 * 获取两个时间相隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calc2DateTDOADays(Date startDate, Date endDate) {
		if ((startDate == null) || (endDate == null)) {
			return 0;
		}
		Date startDate0AM = getDate0AM(startDate);
		Date endDate0AM = getDate0AM(endDate);
		long v1 = startDate0AM.getTime() - endDate0AM.getTime();
		return Math.abs((int) Tools.divideAndRoundUp(v1, 86400000.0D, 0));
	}

	/**
	 * 获取两个时间相隔的小时数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calc2DateTDOAHours(Date startDate, Date endDate) {
		if ((startDate == null) || (endDate == null)) {
			return 0;
		}
		long v1 = startDate.getTime() - endDate.getTime();
		return Math.abs((int) Tools.divideAndRoundDown(v1, 3600000.0D, 0));
	}

	public static Date changeDateTime(Date theDate, int hour) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		if ((hour >= -24) && (hour <= 24)) {
			cal.add(Calendar.HOUR, hour);
		}
		return cal.getTime();
	}

	public static Date getNextMonday(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate0AM(date));
		cal.set(7, 2);

		Calendar nextMondayCal = Calendar.getInstance();
		nextMondayCal.setTimeInMillis(cal.getTimeInMillis() + 604800000L);
		return nextMondayCal.getTime();
	}

	public static Date add(int addDay, boolean to0AM) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, addDay);
		Date time = calendar.getTime();
		return to0AM ? getDate0AM(time) : time;
	}

	public static long getCurrentSecond() {
		return toSecond(new long[] { System.currentTimeMillis() });
	}

	// 获取当前月第一天
	public static String getMonthFisrtDay(Date theTime , String datePattern) {
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar c = Calendar.getInstance();
		c.setTime(theTime);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(c.getTime());
	}

	// 获取当前月最后一天
	public static String getMonthLastDay(Date theTime ,String datePattern) {
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar ca = Calendar.getInstance();
		ca.setTime(theTime);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(ca.getTime());
	}

}
