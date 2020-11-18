package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.annualreport;

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
 * 客流量按照客户年龄 性别  时间  资产  四个维度年统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class CustomerFlowStatisOfYearReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String year = this.getYearStr();
		log.info("客流数据四维度-->年统计，传入参数：[year,dateType]=[{},{}]",
			year, Constants.YEAR);
		try {
			operationDataStatsDao.cusFlowByTime(year, Constants.YEAR, "");
			operationDataStatsDao.cusFlowBySex(year, Constants.YEAR, "");
			operationDataStatsDao.cusFlowByAge(year, Constants.YEAR, "");
			operationDataStatsDao.cusFlowByAsset(year, Constants.YEAR, "");
		} catch (Exception e) {
			log.error("客流数据四维度-->年统计，发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}
}
