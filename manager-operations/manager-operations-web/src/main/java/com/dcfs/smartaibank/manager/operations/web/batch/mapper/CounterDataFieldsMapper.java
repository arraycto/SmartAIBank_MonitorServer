package com.dcfs.smartaibank.manager.operations.web.batch.mapper;


import com.dcfs.smartaibank.manager.operations.web.batch.domain.TradeInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;

/**
 * 根据tokenizer返回的fieldSet组装TradeInfoDomain对象。
 *
 * @author yangpingf
 */
public class CounterDataFieldsMapper implements FieldSetMapper {

	/**
	 * 第三方联机自助设备模式
	 */
	private static final String SELF_DEVICE_MODE = "2";

	@Override
	public Object mapFieldSet(FieldSet fieldSet) throws BindException {
		TradeInfoDomain tradeInfo = new TradeInfoDomain();
		// 根据现场要求补上客户号字段，长度为21位
		tradeInfo.setCutomerId(fieldSet.readString("CUST_NO"));
		tradeInfo.setTradeCode(fieldSet.readString("TRAN_CODE"));
		tradeInfo.setTradeName(fieldSet.readString("TRAN_NAME"));
		tradeInfo.setBranchNo(fieldSet.readString("HANDL_INST_NO"));
		tradeInfo.setUserNo(fieldSet.readString("HANDL_TLR_ID"));
		tradeInfo.setIdentifyType(fieldSet.readString("CERT_TYPE"));
		tradeInfo.setIdentifyNo(fieldSet.readString("CERT_CODE"));
		// 设置成柜面交易流水
		tradeInfo.setChannelType("E");
		//校验交易时间是否为正确的时间格式,是否为合法的年月日类型
		String tempTradeDate = fieldSet.readString("DATA_DT");
		tradeInfo.setTradeDate(DateUtils.checkLocalDate(tempTradeDate) != null ? Integer.valueOf(tempTradeDate) : null);
		//校验开始时间是否为正确的时间格式,如果不合法设置为null
		setStartTimeAndEndTime(tradeInfo, tempTradeDate + fieldSet.readString("TRAN_ST_TM"),
			tempTradeDate + fieldSet.readString("TRAN_END_TM"));
		return tradeInfo;
	}

	/**
	 * 设置渠道类型
	 * A-自助 B-排队 C-移动厅堂 D-填单系统  E-高柜-柜面 F-低柜
	 *
	 * @param elecdockind 1-第三方联机网银模式,2-第三方联机自助设备模式,3-第三方控件形态，4-日终批量 5-第三方联机网贷模式
	 */
	private void setChannelType(TradeInfoDomain tradeinfo, String elecdockind) {
		if (elecdockind == null || elecdockind.trim().isEmpty()) {
			tradeinfo.setChannelType("E");
		} else if (SELF_DEVICE_MODE.equals(elecdockind)) {
			tradeinfo.setChannelType("A");
		}
	}

	/**
	 * 校验开始结束时间，如果开始结束时间日期不规范，且开始时间比结束时间大,则赋null
	 *
	 * @param tradeInfo TradeInfoDomain实体
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 */
	private void setStartTimeAndEndTime(TradeInfoDomain tradeInfo, String startTime, String endTime) {
		LocalDateTime start = DateUtils.checkLocalDateTime(startTime);
		LocalDateTime end = DateUtils.checkLocalDateTime(endTime);
		if (start != null && end != null && start.isBefore(end)) {
			tradeInfo.setStartTime(startTime);
			//校验结束时间是否为正确的时间格式,如果不合法设置为null
			tradeInfo.setEndTime(endTime);
		} else {
			tradeInfo.setStartTime(null);
			//校验结束时间是否为正确的时间格式,如果不合法设置为null
			tradeInfo.setEndTime(null);
		}
	}

}
