package com.dcfs.smartaibank.manager.operations.web.utils;

/**
 * Unicode工具类
 *
 * @author jiazw
 */
public final class UnicodeUtil {

	private UnicodeUtil() {
	}

	/**
	 * 根据Unicode编码完美的判断中文汉字和符号
	 *
	 * @return
	 */
	public static boolean isChinese(String a) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(a.charAt(0));
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
			|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
			|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
			|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

}
