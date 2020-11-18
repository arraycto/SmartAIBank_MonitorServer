package com.dcfs.smartaibank.manager.monitor.core.executor;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 执行器类型定义
 *
 * @author jiazw
 */
public enum ExecutorType implements CodeDescEnum<String, String> {
	/**
	 * MVEL表达式
	 */
	MVEL("MVEL", "MVEL表达式"),
	/**
	 * cron执行器
	 */
	CRON("CRON", "cron执行器"),
	/**
	 * 自定义
	 */
	CUSTOMER("CUSTOMER", "自定义"),
	/**
	 * Bean
	 */
	BEAN("BEAN", "Bean");

	String code;
	String desc;

	ExecutorType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
