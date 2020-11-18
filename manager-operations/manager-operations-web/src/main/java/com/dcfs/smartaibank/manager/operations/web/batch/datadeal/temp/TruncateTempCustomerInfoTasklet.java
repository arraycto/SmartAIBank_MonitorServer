package com.dcfs.smartaibank.manager.operations.web.batch.datadeal.temp;

import com.dcfs.smartaibank.manager.operations.web.batch.datadeal.AbstractTasklet;
import com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 删除客户信息临时表中数据
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Component
@Slf4j
@StepScope
public class TruncateTempCustomerInfoTasklet extends AbstractTasklet {

	@Autowired
	private DataPreDealDao dataPreDealDao;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		try {
			dataPreDealDao.truncateTempCustomerInfo();
		} catch (DataAccessException e) {
			log.error("清空客户信息出现异常", e);
			throw e;
		}
		return RepeatStatus.FINISHED;
	}
}
