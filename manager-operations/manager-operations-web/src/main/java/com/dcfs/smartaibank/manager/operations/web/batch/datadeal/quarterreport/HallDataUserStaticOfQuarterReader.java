package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.quarterreport;

import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.dao.OperationDataStatsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 厅堂数据季度统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class HallDataUserStaticOfQuarterReader extends AbstractTasklet {

	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String quarterStr = this.getQuarterStr();
		String year = quarterStr.substring(0, 4);
		log.info("厅堂用户数据-->季统计，传入参数：[dateType,quarterStr,year]=[{},{},{}]",
			Constant.QUARTER, quarterStr, year);
		try {
			operationDataStatsDao.hallMarketUserStats(Constant.QUARTER, quarterStr, year);
		} catch (Exception e) {
			log.error("厅堂用户数据季统计发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}
}
