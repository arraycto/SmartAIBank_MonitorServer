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
 * 业务效率按照机构统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class BusEfficStatisByChannelOfQuarterReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String quarterStr = this.getQuarterStr();
		String year = quarterStr.substring(0, 4);
		log.info("业务效率按照机构统计-->季度统计，传入参数：[quarterStr,dateType,year]=[{},{},{}]",
			quarterStr, Constant.QUARTER, year);
		try {
			operationDataStatsDao.busEffic(quarterStr, Constant.QUARTER, year);
		} catch (Exception e) {
			log.error("业务效率按照机构统计-->季度统计，发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}

}
