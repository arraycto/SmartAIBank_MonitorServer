package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * 规则定义
 *
 * @author wanglqb
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("serial")
public class Rule implements Serializable {

	/**
	 * ID
	 */
	protected String id;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 描述
	 */
	protected String description;

	/**
	 * 类型
	 */
	protected RuleType type;

	/**
	 * 是否激活
	 */
	protected boolean active;

	/**
	 * 执行器
	 */
	protected ExecuteDefine executor;

	/**
	 * 创建时间
	 */
	protected Date createTime;

	/**
	 * 更新时间
	 */
	protected Date updateTime;
}
