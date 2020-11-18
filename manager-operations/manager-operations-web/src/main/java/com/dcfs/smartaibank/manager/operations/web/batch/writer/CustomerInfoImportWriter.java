package com.dcfs.smartaibank.manager.operations.web.batch.writer;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.stereotype.Component;

/**
 * 客户信息导入写入
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Component
@Slf4j
public class CustomerInfoImportWriter extends MyBatisBatchItemWriter<CustomerInfoDomain> {
	public CustomerInfoImportWriter(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
		super.setStatementId("com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao.insertCustomerInfo");
	}
}
