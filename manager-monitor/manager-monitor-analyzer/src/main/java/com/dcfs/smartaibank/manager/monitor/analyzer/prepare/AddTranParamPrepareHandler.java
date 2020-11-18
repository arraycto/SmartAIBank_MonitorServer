package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.CommonService;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加交易参数预处理器
 *
 * @author jiazw
 */
@Slf4j
public class AddTranParamPrepareHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {
	private static final String DEFAULT_TRAN_CODE = "000000";
	private RuleCenterAccess ruleCenter;

	private CommonService commonService;

	private String defaultTranCode = DEFAULT_TRAN_CODE;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//是否为交易监控数据
		boolean cond = ContextHelper.isSameType(context, TYPE_TRAN);

		if (cond) {
			String tranCode = context.getBody(Constants.TRAN_CODE);
			String tranRetCode = context.getBody("TRAN_RET_CODE");
			TranCode code = ruleCenter.getTranCode(tranCode, tranRetCode);

			//添加默认交易码和交易状态返回码对应处理
			if (code == null && defaultTranCode != null) {
				code = ruleCenter.getTranCode(defaultTranCode, tranRetCode);
			}

			if (code != null && code.isActive()) {
				String type = context.getHeader(TYPE);

				//计算该交易返回码连续出现的次数
				int count = code.getCount();

				Map<String, Object> param = new HashMap<>(4);
				param.put("M_KEY", type);
				param.put(Constants.M_TYPE, type);
				param.put("M_VALUE", tranRetCode);
				param.put("CCOUNT", count);
				boolean flag = commonService.isExpectCount(param);
				//连续出现相同返回码的次数达到要求
				if (flag) {
					//添加交易代码信息
					context.put(Constants.TRAN_CODE_PARAM, code);
				}
				//添加交易信息
				context.put(Constants.TRAN_RET_DESC, context.getBody("TRAN_RET_DESC"));
			} else {
				log.warn("[{}]没有查到交易返回码信息！[TRAN_CODE:{},TRAN_RET_CODE:{}]", context.getUUID());
				context.put(Constants.TRAN_RET_DESC, context.getBody(Constants.TRAN_RET_DESC));
			}
		}

		return Status.CONTINUE;
	}

	/**
	 * 设置规则中心
	 *
	 * @param ruleCenter 规则中心
	 */
	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	/**
	 * 设置服务
	 *
	 * @param service 服务
	 */
	public void setCommonService(CommonService service) {
		this.commonService = service;
	}

	/**
	 * 设置成功交易码
	 *
	 * @param tranCode 成功交易码
	 */
	public void setDefaultTranCode(String tranCode) {
		this.defaultTranCode = tranCode;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
		Assert.notNull(this.commonService, "commonService must be not null");
	}
}
