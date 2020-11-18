package com.dcfs.smartaibank.manager.operations.web.utils;


import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 参数校验
 *
 * @author
 */
public final class ParamValidator {
	private static Logger logger = LoggerFactory.getLogger(ParamValidator.class);

	private ParamValidator() {
	}

	/**
	 * @param inputFieldName
	 * @param input
	 * @param shouldValue
	 */
	public static void containsValidate(String inputFieldName, String input, String shouldValue) {
		if (!shouldValue.contains(input)) {
			String message = "参数'" + inputFieldName + "'格式异常，应该在" + shouldValue
				+ "范围,但是请求的参数值是'" + input + "'";
			logger.error(message);
			throw new BusinessException("parameter.format.error", message);
		}
	}

	/**
	 * 根据制定日期计算一定范围内的日期
	 *
	 * @param datetime 实际日期，格式为：日-yyyyMMdd,月-yyyyMM，季-yyyyMM，年-yyyy
	 * @param type     支持day\month\quarter\year
	 * @param value    时间跨度，支持正负数，例如3，往后推3个维度；-3，往前推3个维度
	 * @return
	 */
	public static String dateFormat(String datetime, String type, int value) {
		String format = "yyyyMMdd";
		int typeCt = Calendar.DATE;
		if (Constant.MONTH.equals(type)) {
			format = "yyyyMM";
			typeCt = Calendar.MONTH;
		} else if (Constant.YEAR.equals(type)) {
			format = "yyyy";
			typeCt = Calendar.YEAR;
		} else if (Constant.QUARTER.equals(type)) {
			String year = datetime.substring(0, 4);
			String quarter = datetime.substring(4, 6);
			int gap = Integer.valueOf(quarter) + Integer.valueOf(value);
			if (gap > 0 && gap < Constant.FIVE) {
				return year + "0" + gap;
			} else if (gap < 0 || gap == 0) {
				return Integer.valueOf(year) - 1 + "0" + (4 + gap);
			} else {
				return Integer.valueOf(year) + 1 + "0" + (gap - 4);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(typeCt, value);
		date = cl.getTime();
		return sdf.format(date);
	}

}
