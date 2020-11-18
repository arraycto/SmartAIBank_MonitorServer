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
 * 客流量按照客户年龄 性别  时间  资产  四个维度日统计
 *
 * @author
 */
@Component
@Slf4j
@StepScope
public class CustomerFlowStatisOfQuarterReader extends AbstractTasklet {
	@Autowired
	private OperationDataStatsDao operationDataStatsDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String quarterStr = this.getQuarterStr();
		String year = quarterStr.substring(0, 4);
		log.info("客流数据四维度-->季度统计，传入参数：[quarterStr,dateType,year]=[{},{},{}]",
			quarterStr, Constant.QUARTER, year);
		try {
			operationDataStatsDao.cusFlowByAge(quarterStr, Constant.QUARTER, year);
			operationDataStatsDao.cusFlowByTime(quarterStr, Constant.QUARTER, year);
			operationDataStatsDao.cusFlowByAsset(quarterStr, Constant.QUARTER, year);
			operationDataStatsDao.cusFlowBySex(quarterStr, Constant.QUARTER, year);
		} catch (Exception e) {
			log.error("客流数据四维度-->季度统计，发生异常", e);
		}
		return RepeatStatus.FINISHED;
	}

}
