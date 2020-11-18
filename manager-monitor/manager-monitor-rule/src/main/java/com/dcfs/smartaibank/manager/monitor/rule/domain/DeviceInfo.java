package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangjzm
 * @data 2019/08/19 16:23
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeviceInfo {
	/**
	 * 监控类型
	 */
	private String monitorType;

	/**
	 * 监控状态
	 */
	private Integer status;

	/**
	 * 数据接收时间
	 */
	private String receiveTime;

}
