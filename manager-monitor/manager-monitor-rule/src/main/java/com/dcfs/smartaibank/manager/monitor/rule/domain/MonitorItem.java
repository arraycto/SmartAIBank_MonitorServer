package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 监控项
 *
 * @author wanglqb
 */
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("serial")
public class MonitorItem implements Serializable {
	/**
	 * ID
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 分组
	 */
	private String group;

	/**
	 * 是否激活
	 */
	private boolean active;

	/**
	 * 是否预警
	 */
	private boolean alarm;

	/**
	 * 新增时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

}
