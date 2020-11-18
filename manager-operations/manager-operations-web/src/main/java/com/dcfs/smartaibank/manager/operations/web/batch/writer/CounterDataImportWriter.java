package com.dcfs.smartaibank.manager.operations.web.batch.writer;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.TradeInfoDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.stereotype.Component;

/**
 * 柜面数据导入写入
 * <p>
 *    使用MyBatisBatchItemWriter实现批量插入操作
 * </p>
 * @author yangping, wangjz
 */
@Component
@Slf4j
public class CounterDataImportWriter extends MyBatisBatchItemWriter<TradeInfoDomain> {
	public CounterDataImportWriter(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
		super.setStatementId("com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao.insertTradeinfo");
	}

}
