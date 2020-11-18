package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.temp;

import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户动线数据预处理，从 交易临时比，排队临时表，厅堂临时表，找到客户的操作记录。整理到动线临时表中
 *
 * @author yangpingf
 */
@Component
@Slf4j
@StepScope
public class CustRoutesDataDealReader extends AbstractTasklet {
	@Autowired
	private DataPreDealDao dataPreDealDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("正在执行客户路线数据预处理reader");
		String date = DateUtils.getLastDay(super.getDate());
		Map<String, Object> paramMap = new HashMap<>(2);
		paramMap.put("date", date);
		paramMap.put("message", "");
		try {
			dataPreDealDao.custRoutesDataDeal(paramMap);

		} catch (Exception e) {
			log.error("客户路线数据预处理出现异常", e);
			throw e;
		}
		log.info("客户路线数据预处理完成，返回消息：{}", paramMap.get("message"));
		return RepeatStatus.FINISHED;
	}
}
