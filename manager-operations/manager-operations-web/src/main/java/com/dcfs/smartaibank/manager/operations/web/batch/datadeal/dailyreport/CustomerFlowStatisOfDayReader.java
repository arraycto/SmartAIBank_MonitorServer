package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.dailyreport;

import com.dcfs.smartaibank.manager.operations.web.batch.common.Constants;
import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import com.dcfs.smartaibank.manager.operations.web.dao.OperationDataStatsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 客流量按照客户年龄 性别  时间  资产  四个维度日统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class CustomerFlowStatisOfDayReader extends AbstractTasklet {

	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String date = DateUtils.getLastDay(this.date);
		String month = date.substring(0, 6);
		log.info("客流数据四维度-->日统计，传入参数：[date,dateType,month]=[{},{},{}]",
			date, Constants.DAY, month);
		try {
			operationDataStatsDao.cusFlowByAge(date, Constants.DAY, month);
			operationDataStatsDao.cusFlowByTime(date, Constants.DAY, month);
			operationDataStatsDao.cusFlowByAsset(date, Constants.DAY, month);
			operationDataStatsDao.cusFlowBySex(date, Constants.DAY, month);
		} catch (Exception e) {
			log.error("客流数据四维度-->日统计，发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}
}
