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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户数据整形处理，包括客户资产段，年龄段的，性别等字段的完善
 *
 * @author yangpingf
 */
@Component
@Slf4j
@StepScope
public class CustDataDealReader extends AbstractTasklet {
	@Autowired
	private DataPreDealDao dataPreDealDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("正在执行客户数据整形处理");
		String date = DateUtils.getLastDay(super.getDate());
		Map<String, Object> paramMap = new HashMap<>(2);
		paramMap.put("date", date);
		paramMap.put("message", "");
		try {
			dataPreDealDao.custDataDeal(paramMap);
		} catch (DataAccessException e) {
			log.error("客户数据整形处理出现异常", e);
			throw e;
		}
		log.info("客户数据整形处理完成，返回消息：{}", paramMap.get("message"));
		return RepeatStatus.FINISHED;
	}
}
