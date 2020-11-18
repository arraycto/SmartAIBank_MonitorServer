package com.dcfs.smartaibank.manager.operations.web.convert;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

/**
 * @author tanchena
 * @date 2019/12/20 22:15
 */
public class DateConvert implements ConvertFactory<String, LocalDate> {

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
	 * 时间转换器
	 *
	 * @param date 需要转换的时间
	 * @return 转换后的时间
	 */
	@Override
	public LocalDate convert(String date) {
		try {
			return LocalDate.parse(date, LOCAL_DATE_FORMATTER);
		} catch (Exception e) {
			return null;
		}
	}

}
