package com.dcfs.smartaibank.manager.operations.web.batch.mapper;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * 对公客户信息导入的mapper
 *
 * @author
 */
public class PublicCustomerFieldsSetMapper implements FieldSetMapper {
	@Override
	public CustomerInfoDomain mapFieldSet(FieldSet fieldSet) throws BindException {
		CustomerInfoDomain customer = new CustomerInfoDomain();
		customer.setCustomerNo(fieldSet.readString("CUST_NO"));
		customer.setCustomerName(fieldSet.readString("FULL_NAME"));
		// 客户类型C表示对公客户
		customer.setCustomerType("C");
		customer.setIdentifyNo(fieldSet.readString("CERT_CODE"));
		customer.setIdentifyType(fieldSet.readString("CERT_TYPE"));
		customer.setBranchNo(fieldSet.readString("CHN_ACCT_OPN_INST"));
		// 设置客户资产默认为8-未识别，只在插入操作中更新
		customer.setCustomerBalanceStep(8);
		return customer;
	}
}
