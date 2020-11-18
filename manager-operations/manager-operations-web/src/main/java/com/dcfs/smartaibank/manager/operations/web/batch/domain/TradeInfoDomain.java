package com.dcfs.smartaibank.manager.operations.web.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 柜面交易信息
 *
 * @author
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TradeInfoDomain {

	private String tradeCode;

	private String tradeName;

	private String channelType;

	private String branchNo;

	private String branchName;

	private String cutomerId;

	private String customerName;

	private String accountNo;

	private String customerLevel;

	private String identifyType;

	private String identifyNo;

	private String cardNo;

	private Float tradeAmt;

	private String userNo;

	private String userName;

	private String tickNo;

	private String startTime;

	private String endTime;

	private Integer tradeDate;
}
