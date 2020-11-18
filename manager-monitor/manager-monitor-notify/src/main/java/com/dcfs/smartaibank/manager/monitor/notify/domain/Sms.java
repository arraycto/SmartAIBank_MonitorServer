package com.dcfs.smartaibank.manager.monitor.notify.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 短信实体
 *
 * @author jiazw
 */
@Getter
@Setter
@NoArgsConstructor
public class Sms {
	private String phone;
	private String context;
	private String branchNo;
	private String type;
}
