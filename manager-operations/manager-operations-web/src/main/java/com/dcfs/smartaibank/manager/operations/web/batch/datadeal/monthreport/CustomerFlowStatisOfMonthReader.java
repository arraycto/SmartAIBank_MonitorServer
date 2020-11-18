package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.monthreport;

import com.dcfs.smartaibank.manager.operations.web.batch.common.Constants;
import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.dao.OperationDataStatsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 客流量按照客户年龄 性别  时间  资产  四个维度月统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class CustomerFlowStatisOfMonthReader extends AbstractTasklet {

	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String monthStr = this.getMonthStr();
		String quarter = this.getQuarterStr();
		log.info("客流数据四维度-->月统计，传入参数：[monthStr,dateType,quarter]=[{},{},{}]",
			monthStr, Constants.MONTH, quarter);
		try {
			operationDataStatsDao.cusFlowByAge(monthStr, Constants.MONTH, quarter);
			operationDataStatsDao.cusFlowByAsset(monthStr, Constants.MONTH, quarter);
			operationDataStatsDao.cusFlowBySex(monthStr, Constants.MONTH, quarter);
			operationDataStatsDao.cusFlowByTime(monthStr, Constants.MONTH, quarter);
		} catch (Exception e) {
			log.error("客流数据四维度-->月统计，发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}
}
