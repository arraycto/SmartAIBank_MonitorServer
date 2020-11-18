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
 * 机构汇总统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class OrganizationSumStatisOfDayReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String date = DateUtils.getLastDay(this.date);
		log.info("机构汇总数据-->日统计，传入参数：[dateType,date]=[{},{}]",
			Constants.DAY, date);
		try {
			operationDataStatsDao.orgGather(Constants.DAY, date);
		} catch (Exception e) {
			log.error("机构汇总数据日统计发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}

}
