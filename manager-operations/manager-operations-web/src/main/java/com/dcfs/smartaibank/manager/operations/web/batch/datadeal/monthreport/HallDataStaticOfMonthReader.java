package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.monthreport;

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
 * 厅堂数据月统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class HallDataStaticOfMonthReader extends AbstractTasklet {

	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String monthStr = this.getMonthStr();
		String quarter = this.getQuarterStr();
		log.info("厅堂数据按照机构统计-->月统计，传入参数：[dateType,monthStr,quarter]=[{},{},{}]",
			Constant.MONTH, monthStr, quarter);
		try {
			operationDataStatsDao.hallMarketStats(Constant.MONTH, monthStr, quarter);
		} catch (Exception e) {
			log.error("厅堂机构数据月统计发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}

}
