package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern;

/**
 * 模式匹配处理接口
 *
 * @author jiazw
 */
public interface TemplatePatternDefine {

	/**
	 * 设置模板
	 * @param source 模板
	 */
	void setSource(String source);

	/**
	 * 设置前缀
	 *
	 * @param prefixPatternString 前缀
	 */
	void setPrefixPatternString(String prefixPatternString);

	/**
	 * 设置后缀
	 *
	 * @param postfixPatternString 后缀
	 */
	void setPostfixPatternString(String postfixPatternString);

	/**
	 * 设置正则表达式中间部分
	 *
	 * @param patternString 正则表达式中间部分
	 */
	void setPatternString(String patternString);

	/**
	 * 返回正文匹配部分
	 *
	 * @param string 正文部分
	 * @return 匹配部分
	 */
	String getPureMatchText(String string);

	/**
	 * 根据正文返回完整部分
	 *
	 * @param string 正文
	 * @return 完整匹配部分
	 */
	String getFullMatchText(String string);

	/**
	 * 设置域分隔符
	 *
	 * @param splitChar 分隔符
	 */
	void setSplitChar(char splitChar);

	/**
	 * 返回分隔符
	 *
	 * @return 分隔符
	 */
	char getSplitChar();

	/**
	 * 获取迭代器
	 *
	 * @return 迭代器
	 */
	PatternIterator iterator();
}
