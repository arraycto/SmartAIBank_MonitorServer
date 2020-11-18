package com.dcfs.smartaibank.manager.operations.web.convert;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author tanchena
 * @date 2019/12/20 22:13
 */
public class AgeConvert implements ConvertFactory<LocalDate, Integer> {

	private static final Integer AGE_EIGHTEEN = 18;
	private static final Integer AGE_TWENTYTWO = 22;
	private static final Integer AGE_THIRTY = 30;
	private static final Integer AGE_FORTYFIVE = 45;
	private static final Integer AGE_SIXTY = 60;


	/**
	 * 年龄转换器
	 *
	 * @param birthday 生日
	 * @return 年龄段
	 */
	@Override
	public Integer convert(LocalDate birthday) {
		LocalDate now = LocalDate.now();
		Integer age = Math.toIntExact(birthday.until(now, ChronoUnit.YEARS));
		if (age <= AGE_EIGHTEEN) {
			return 1;
		} else if (age > AGE_EIGHTEEN && age <= AGE_TWENTYTWO) {
			return 2;
		} else if (age > AGE_TWENTYTWO && age <= AGE_THIRTY) {
			return 3;
		} else if (age > AGE_THIRTY && age <= AGE_FORTYFIVE) {
			return 4;
		} else if (age > AGE_FORTYFIVE && age <= AGE_SIXTY) {
			return 5;
		} else if (age > AGE_SIXTY) {
			return 6;
		}
		return 0;
	}
}
