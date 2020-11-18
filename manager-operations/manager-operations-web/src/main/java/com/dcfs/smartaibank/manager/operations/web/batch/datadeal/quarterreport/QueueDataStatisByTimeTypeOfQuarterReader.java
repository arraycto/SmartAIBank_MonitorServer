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
 * 排队情况按照时间类型统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class QueueDataStatisByTimeTypeOfQuarterReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String quarterStr = this.getQuarterStr();
		log.info("排队时间类型数据-->季统计，传入参数：[dateType,quarterStr]=[{},{}]",
			Constant.QUARTER, quarterStr);
		try {
			operationDataStatsDao.queueByTimeStep(Constant.QUARTER, quarterStr);
			operationDataStatsDao.queueByTimeGroup(Constant.QUARTER, quarterStr);
		} catch (Exception e) {
			log.error("排队-时间类型数据季统计发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}
}
