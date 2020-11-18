package com.dcfs.smartaibank.manager.operations.web.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户基本信息
 *
 * @author
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerInfoDomain {
	private String customerNo;
	private String customerName;
	private Integer customerAgeStep;
	private String customerSex;
	private String customerType;
	private String customerLevel;
	private String identifyType;
	private String identifyNo;
	private String accountNo;
	private String cardNo;
	private String branchNo;
	private String birthday;
	private String amtBal;
	private Integer customerBalanceStep;
}
