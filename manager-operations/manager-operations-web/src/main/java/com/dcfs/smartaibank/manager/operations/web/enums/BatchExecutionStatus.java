package com.dcfs.smartaibank.manager.operations.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * batch执行状态
 * <p>
 * Spring Batch官网状态描述：
 * 运行时：BatchStatus#STARTED。
 * 失败：BatchStatus#FAILED。
 * 成功完成：BatchStatus#COMPLETED。
 * </p>
 *
 * @author wangjzm
 * @see javax.batch.runtime.BatchStatus
 * @since 1.0.0
 */
public enum BatchExecutionStatus implements CodeDescEnum<String, String> {
	/**
	 * 正在启动
	 */
	STARTING("STARTING", "正在启动"),

	/**
	 * 正在运行
	 */
	STARTED("STARTED", "正在运行"),

	/**
	 * 正在停止
	 */
	STOPPING("STOPPING", "正在停止"),

	/**
	 * 已停止
	 */
	STOPPED("STOPPED", "已停止"),

	/**
	 * 执行失败
	 */
	FAILED("FAILED", "执行失败"),

	/**
	 * 执行完成
	 */
	COMPLETED("COMPLETED", "执行完成"),

	/**
	 * 放弃执行
	 */
	ABANDONED("ABANDONED", "放弃执行");

	String code;
	String desc;

	BatchExecutionStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
