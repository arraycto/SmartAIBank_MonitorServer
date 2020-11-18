package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.dailyreport;

import com.dcfs.smartaibank.manager.operations.web.batch.common.Constants;
import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.dao.OperationDataStatsDao;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 厅堂数据日统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class HallDataStaticOfDayReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String date = DateUtils.getLastDay(this.date);
		String month = date.substring(0, 6);
		log.info("厅堂数据按照机构统计-->日统计，传入参数：[dateType,date,month]=[{},{},{}]",
			Constants.DAY, date, month);
		try {
			operationDataStatsDao.hallMarketStats(Constants.DAY, date, month);
		} catch (Exception e) {
			log.error("厅堂机构数据日统计发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}

}
