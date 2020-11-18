package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.temp;

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
 * 删除交易临时表中指定日期的临时数据
 *
 * @author yangpingf
 */
@Component
@Slf4j
@StepScope
public class DeleteTradeDataReader extends AbstractTasklet {

	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String dateStr = DateUtils.getLastDay(this.getDate());
		try {
			operationDataStatsDao.deleteTradeDataByDate(dateStr);
		} catch (Exception e) {
			log.error("删除{}日交易数据出现异常", DateUtils.getLastDay(this.getDate()), e);
			throw e;
		}
		return RepeatStatus.FINISHED;
	}
}
