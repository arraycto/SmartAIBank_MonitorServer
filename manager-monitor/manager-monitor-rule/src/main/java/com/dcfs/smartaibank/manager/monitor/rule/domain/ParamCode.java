package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 监控预警配置
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParamCode {
	/**
	 * 类型
	 */
	private String typeNo;

	/**
	 * 代码项
	 */
	private String codeNo;

	/**
	 * 代码值
	 */
	private String codeValue;

	/**
	 * 代码描述
	 */
	private String codeDesc;

	/**
	 * 序号
	 */
	private int sortNo;
}
