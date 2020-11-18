package com.dcfs.smartaibank.manager.monitor.web.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 二级表头下半部分信息
 *
 * @author wangjzm
 * @data 2019/08/24 10:33
 * @since 1.0.0
 */
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderColInfo {
	private String key;

	private String value;

}

