package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 用户
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
public class User {
	/**
	 * 用户ID
	 */
	private String id;

	/**
	 * 角色
	 */
	private List<String> roles;

	/**
	 * 所属机构
	 */
	private String branchNo;

	/**
	 * 所属机构是否为总行
	 */
	private boolean isHead;

	/**
	 * 所属机构的上级机构
	 */
	private String superior;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 微信号
	 */
	private String weixin;
}
