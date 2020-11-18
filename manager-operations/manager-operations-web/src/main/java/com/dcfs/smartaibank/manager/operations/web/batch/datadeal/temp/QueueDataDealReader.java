package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.temp;

import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
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
 * 排队系统数据整形,包括数据从排队交易历史表导入到temp_queue表
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class QueueDataDealReader extends AbstractTasklet {
	@Autowired
	private DataPreDealDao dataPreDealDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("正在执行排队数据整形处理reader");

		String date = DateUtils.getLastDay(super.getDate());
		Map<String, Object> paramMap = new HashMap<>(2);
		paramMap.put("date", date);
		paramMap.put("message", "");
		try {
			dataPreDealDao.queueDataDeal(paramMap);
		} catch (Exception e) {
			log.error("排队数据处理出现异常", e);
			throw e;
		}
		log.info("排队数据整形处理完成，返回消息：{}", paramMap.get("message"));
		return RepeatStatus.FINISHED;
	}

}
