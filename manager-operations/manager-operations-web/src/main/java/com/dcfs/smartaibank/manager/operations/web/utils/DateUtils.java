package com.dcfs.smartaibank.manager.operations.web.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Date;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

/**
 * 日期工具类
 *
 * @author
 */
public final class DateUtils {
	private DateUtils() {
	}

	private static final int TWO = 2;
	private static final int FOUR = 4;
	private static final int SEVEN = 7;
	private static final int TEN = 10;
	private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER =
		new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(YEAR, 4)
			.appendValue(MONTH_OF_YEAR, 2).appendValue(DAY_OF_MONTH, 2)
			.appendValue(HOUR_OF_DAY, 2).appendValue(MINUTE_OF_HOUR, 2)
			.appendValue(SECOND_OF_MINUTE, 2)
			.optionalStart().toFormatter()
			.withResolverStyle(ResolverStyle.STRICT)
			.withChronology(IsoChronology.INSTANCE);
	private static final DateTimeFormatter LOCAL_DATE_FORMATTER =
		new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(YEAR, 4)
			.appendValue(MONTH_OF_YEAR, 2).appendValue(DAY_OF_MONTH, 2)
			.optionalStart().toFormatter()
			.withResolverStyle(ResolverStyle.STRICT)
			.withChronology(IsoChronology.INSTANCE);

	/**
	 *  判断指定日期日否为这个月的第一天
	 * @param date 时间
	 * @return
	 */
	public static boolean isFirstDayOfMonth(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return localDateTime.getDayOfMonth() == 1;
	}

	/**
	 * 判断指定日期日否为这个季度的第一天
	 * @param date 时间
	 * @return
	 */
	public static boolean isFirstDayOfQuarter(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		int month = localDateTime.getMonth().getValue();
		if (month == 1 || month == FOUR || month == SEVEN || month == TEN) {
			return localDateTime.getDayOfMonth() == 1;
		} else {
			return false;
		}
	}

	/**
	 * 判断指定日期日否为这一年的第一天
	 * @param date 时间
	 * @return
	 */
	public static boolean isFirstDayOfYear(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return localDateTime.getDayOfYear() == 1;
	}

	/**
	 * 将字符串日期转换为日期对象
	 *
	 * @param dateStr 字符串日期
	 * @return 日期对象
	 */
	public static Date getDate(String dateStr) {
		Date date = null;
		try {
			if (dateStr == null || dateStr.trim().isEmpty()) {
				date = new Date();
			} else {
				LocalDate parse = LocalDate.parse(dateStr, LOCAL_DATE_FORMATTER);
				Instant instant = parse.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				date = Date.from(instant);
			}
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}

	/**
	 * @param format 默认yyyymmdd
	 * @param count  当前日期前进几天
	 * @return
	 */
	public static String getLastDay(Date date, String format, int count) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return DateTimeFormatter.ofPattern(format).format(localDateTime.plusDays(count));
	}

	/**
	 * 获取日期的下一天
	 *
	 * @param date 日期
	 * @return 字符串日期
	 */
	public static String getLastDay(Date date) {
		return getLastDay(date, "yyyyMMdd", -1);
	}

	/**
	 * 获取日期的下一个月
	 *
	 * @param date 日期
	 * @return 字符串日期
	 */
	public static String getLastMonth(Date date) {
		return getLastMonth(date, "yyyyMMdd", -1);
	}

	/**
	 * 获取日期的下一个季度
	 *
	 * @param date 日期
	 * @return 字符串日期
	 */
	public static String getLastQuarter(Date date) {
		return getLastMonth(date, "yyyyMMdd", -3);
	}

	/**
	 * 获取日期的下一年
	 *
	 * @param date 日期
	 * @return 字符串日期
	 */
	public static String getLastYear(Date date) {
		return getLastMonth(date, "yyyyMMdd", -12);
	}

	/**
	 * 获取日期的下几个月
	 *
	 * @param date   日期
	 * @param format 日期串格式
	 * @param count  月数
	 * @return 字符串日期
	 */
	public static String getLastMonth(Date date, String format, int count) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return DateTimeFormatter.ofPattern(format).format(localDateTime.plusMonths(count));
	}

	/**
	 * 获取当前季度
	 *
	 * @param date   字符串日期
	 * @param format 日期格式
	 * @return 字符串日期
	 */
	public static String getCurrentQuarter(String date, String format) throws ParseException {
		LocalDate localDate = LocalDate.parse(date, LOCAL_DATE_FORMATTER);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		int month = localDate.getMonth().getValue();
		int monthMod = month % 3;
		if (monthMod == 0) {
			localDate.plusMonths(-2);
		}
		if (monthMod == TWO) {
			localDate.plusMonths(-1);
		}
		return DateTimeFormatter.ofPattern(format).format(localDate);
	}

	/**
	 * 校验年月日日期是否为合法的日期
	 *
	 * @param localDate 当前年月日日期
	 * @return boolean值
	 */
	public static LocalDate checkLocalDate(String localDate) {
		try {
			return LocalDate.parse(localDate, LOCAL_DATE_FORMATTER);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 校验年月日时分秒是否为合法的日期
	 *
	 * @param localDateTime 当前年月日时分秒日期
	 * @return boolean值
	 */
	public static LocalDateTime checkLocalDateTime(String localDateTime) {
		try {
			return LocalDateTime.parse(localDateTime, LOCAL_DATE_TIME_FORMATTER);
		} catch (Exception e) {
			return null;
		}
	}

}
