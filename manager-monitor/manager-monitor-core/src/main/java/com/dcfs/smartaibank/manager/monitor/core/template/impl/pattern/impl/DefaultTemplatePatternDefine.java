package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.PatternIterator;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplatePatternDefine;

/**
 * 默认模式定义实现
 *
 * @author jiazw
 */
public class DefaultTemplatePatternDefine implements TemplatePatternDefine {

	private static final String DEFAULT_PATTERN_STRING = "([$]+[{]+[a-zA-Z0-9[.[_[:[/[#]]]]]]+[}])";
	private static final String DEFAULT_POSTFIX_PATTERN_STRING = "}";
	private static final String DEFAULT_PREFIX_PATTERN_STRING = "${";
	private static final char DEFAULT_SPLIT_CHAR = ':';
	private String prefixPatternString = DEFAULT_PREFIX_PATTERN_STRING;
	private String postfixPatternString = DEFAULT_POSTFIX_PATTERN_STRING;
	private String patternString = DEFAULT_PATTERN_STRING;
	private char splitChar = DEFAULT_SPLIT_CHAR;
	private Pattern pattern;
	private String source;

	@Override
	public void setSource(String source) {
		this.source = source;
		pattern = Pattern.compile(patternString);
	}

	@Override
	public void setPrefixPatternString(String prefixPatternString) {
		this.prefixPatternString = prefixPatternString;
	}

	@Override
	public void setPostfixPatternString(String postfixPatternString) {
		this.postfixPatternString = postfixPatternString;
	}

	@Override
	public void setPatternString(String patternString) {
		this.patternString = patternString;
	}

	@Override
	public String getPureMatchText(String string) {
		int startPos = prefixPatternString.length();
		int endPos = string.length() - postfixPatternString.length();
		return string.substring(startPos, endPos);
	}

	@Override
	public String getFullMatchText(String string) {
		return String.format("%s%s%s", prefixPatternString, string,
			postfixPatternString);
	}

	@Override
	public void setSplitChar(char splitChar) {
		this.splitChar = splitChar;
	}

	@Override
	public char getSplitChar() {
		return splitChar;
	}

	@Override
	public PatternIterator iterator() {
		return new Itr();
	}

	/**
	 * 模式匹配迭代器
	 *
	 * @author jiazw
	 */
	private class Itr implements PatternIterator {
		private Matcher matcher;

		Itr() {
			matcher = pattern.matcher(source);
		}

		@Override
		public boolean hasNext() {
			return matcher.find();
		}

		@Override
		public String next() {
			return getPureMatchText(matcher.group());
		}

		@Override
		public int start() {
			return matcher.start();
		}

		@Override
		public int end() {
			return matcher.end();
		}
	}
}
