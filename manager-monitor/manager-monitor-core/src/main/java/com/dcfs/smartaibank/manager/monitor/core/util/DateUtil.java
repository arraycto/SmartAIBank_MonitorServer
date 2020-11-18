package com.dcfs.smartaibank.manager.monitor.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author jiazw
 */
public final class DateUtil {
	private DateUtil() {
	}

	private static long timeOffset = Calendar.getInstance().getTimeZone().getRawOffset() / 1000;
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String DATE_SPLIT_FORMAT = "yyyy-MM-dd";

	/**
	 * 获取毫秒数时间（0时区）
	 * @param date 当前时区的时间
	 * @return 毫秒数时间
	 */
	public static long getTime(Date date) {
		return date.getTime() + timeOffset;
	}

	/**
	 * 格式化时间为yyyy-MM-dd HH:mm:ss
	 * @param date 时间
	 * @return
	 */
	public static String getStrTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
		return format.format(date);
	}

	/**
	 * 格式化日期为yyyyMMdd
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String getStrDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(date);
	}

	/**
	 * 格式化日期为yyyy-MM-dd
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String getStrSplitDate(Date date) {
		SimpleDateFormat dateSplitFormat = new SimpleDateFormat(DATE_SPLIT_FORMAT);
		return dateSplitFormat.format(date);
	}
}
