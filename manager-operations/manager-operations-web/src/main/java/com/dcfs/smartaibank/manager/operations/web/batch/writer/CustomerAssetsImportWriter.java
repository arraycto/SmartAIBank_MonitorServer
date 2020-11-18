package com.dcfs.smartaibank.manager.operations.web.batch.writer;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerAssetsDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.stereotype.Component;


/**
 * 客户资产导入写入
 *
 * @author
 */
@Component
@Slf4j
public class CustomerAssetsImportWriter extends MyBatisBatchItemWriter<CustomerAssetsDomain> {

	public CustomerAssetsImportWriter(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
		super.setStatementId("com.dcfs.smartaibank.manager.operations.web.dao.DataPreDealDao.insertCustomerAssetInfo");
	}
}
