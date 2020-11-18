package com.dcfs.smartaibank.manager.monitor.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author jiazw wangjzm
 */
@Slf4j
public final class DateUtil {
    private DateUtil() {
    }

    private static long timeOffset = Calendar.getInstance().getTimeZone().getRawOffset() / 1000;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String SIMPLE_DATE_FORMAT = "yyyy/MM/dd";
    private static final String DATE_SPLIT_FORMAT = "yyyy-MM-dd";
    private static final String DATE_CHINESE_FORMAT = "yyyy年MM月dd日";
    /**
     * 冒号分隔符
     */
    private static final String COLON_SEPARATOR = ":";

    /**
     * 获取毫秒数时间（0时区）
     *
     * @param date 当前时区的时间
     * @return 毫秒数时间
     */
    public static long getTime(Date date) {
        return date.getTime() + timeOffset;
    }

    /**
     * 格式化时间为yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间
     * @return
     */
    public static String getStrTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 格式化日期为yyyyMMdd
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String getStrDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期为yyyy-MM-dd
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String getStrSplitDate(Date date) {
        SimpleDateFormat dateSplitFormat = new SimpleDateFormat(DATE_SPLIT_FORMAT);
        return dateSplitFormat.format(date);
    }

    /**
     * 格式化日期yyyyMMdd为yyyy年MM月dd日
     *
     * @param dateStr 日期
     * @return 日期字符串
     */
    public static String getStrChineseDate(String dateStr) {
        Date date = null;
        try {
            if (!StringUtils.isEmpty(dateStr)) {
                date = new SimpleDateFormat(SIMPLE_DATE_FORMAT).parse(dateStr);
                return new SimpleDateFormat(DATE_CHINESE_FORMAT).format(date);
            }

        } catch (ParseException e) {
            log.error("日期转换错误，传入的字符串为：{}", dateStr);
        }
        return "";
    }
    /**
     * 格式化日期yyyyMMdd为yyyyMMdd
     *
     * @param dateStr 日期
     * @return 日期字符串
     */
    public static String getStrChineseDates(String dateStr) {
        Date date = null;
        try {
            if (!StringUtils.isEmpty(dateStr)) {
                date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
                return new SimpleDateFormat(DATE_CHINESE_FORMAT).format(date);
            }

        } catch (ParseException e) {
            log.error("日期转换错误，传入的字符串为：{}", dateStr);
        }
        return "";
    }
    /**
     * 返回当前日期，格式为：yyyy年MM月dd日
     *
     * @return 当前日期，格式为：yyyy年MM月dd日
     */
    public static String getCurrentStrChineseDate() {
        return new SimpleDateFormat(DATE_CHINESE_FORMAT).format(new Date());
    }

    /**
     * 获取当月的第一天，最后一天
     *
     * @return 日期字符串数组
     */
    public static String[] getLocalMonthDate() {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        String[] result = {firstDay.toString() + " 00:00:00",
                lastDay.toString() + " 23:59:59"};
        return result;
    }

    /**
     * 获取当前和前一月的日期
     *
     * @return 日期字符串数组
     */
    public static String[] getLocalDateOfOne() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate nowDate = LocalDate.now();
        LocalDate beforeDate = nowDate.minusMonths(1);
        String[] result = {nowDate.format(dateTimeFormatter), beforeDate.format(dateTimeFormatter)};
        return result;
    }

    /**
     * 获取当前日期时间和三个月前的日期时间
     *
     * @return 日期时间字符串数组
     */
    public static String[] getLocalDateTimeOfThree() {
        LocalDateTime nowTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String formatNowTime = nowTime.format(format);
        LocalDate beforeTime = LocalDate.now().minusMonths(3);
        String[] result = {formatNowTime, beforeTime.toString() + " 00:00:00"};
        return result;
    }

    /**
     * 获取当前日期和三个月前的日期
     *
     * @return 日期字符串数组
     */
    public static String[] getLocalDateOfThree() {
        LocalDate nowDate = LocalDate.now();
        LocalDate beforeDate = nowDate.minusMonths(3);
        String[] result = {nowDate.toString(), beforeDate.toString()};
        return result;
    }

    /**
     * 根据时间间隔和结束时间计算小于endTime最并且最近的时间点
     * 例如：传入interval=5，endTime="10:04:10"，返回值为 10:00:00
     *
     * @param interval 时间间隔
     * @param endTime  结束时间
     * @return 小于endTime最并且最近的时间点
     */
    public static String getEndTime(int interval, String endTime) {
        String date = DateUtil.getStrSplitDate(new Date());
        //传入时间合法
        if (endTime != null && !"".equals(endTime) && endTime.indexOf(COLON_SEPARATOR) > 0) {
            String[] arr = endTime.split(COLON_SEPARATOR);
            String minuteStr = arr[1];
            int minute = 0;
            try {
                minute = Integer.parseInt(minuteStr);
            } catch (Exception e) {
                log.error("传入时间参数分钟不为数字！");
            }

            minute = minute - minute % interval;
            return String.format("%s %s:%02d:00", date, arr[0], minute);
        } else {
            log.error("传入时间参数为空或者格式不为HH24:MI:SS");
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }


}
