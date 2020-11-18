package com.dcfs.smartaibank.manager.operations.web.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户资产信息
 *
 * @author
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerAssetsDomain {
	private String customerNo;

	private String amtBal;

	private Integer customerBalanceStep;
}
