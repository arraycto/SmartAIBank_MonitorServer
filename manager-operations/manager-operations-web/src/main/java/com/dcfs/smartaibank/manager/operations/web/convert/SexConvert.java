package com.dcfs.smartaibank.manager.operations.web.convert;

/**
 * @author tanchena
 * @date 2019/12/20 22:03
 */
public class SexConvert implements ConvertFactory<String, String> {

	private static final String SEX_MAN = "1";
	private static final String SEX_WOMAN = "2";

	/**
	 * 性别转换器
	 *
	 * @param sex 性别
	 * @return 返回转换后的性别
	 */
	@Override
	public String convert(String sex) {
		if (SEX_MAN.equals(sex)) {
			return "M";
		} else if (SEX_WOMAN.equals(sex)) {
			return "F";
		} else {
			return "N";
		}
	}

}
