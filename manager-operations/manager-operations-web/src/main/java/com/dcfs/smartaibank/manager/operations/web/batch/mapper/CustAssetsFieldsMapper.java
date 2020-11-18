package com.dcfs.smartaibank.manager.operations.web.batch.mapper;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerAssetsDomain;
import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * 根据tokenizer返回的fieldSet组装CustomerAssetsDomain对象。
 *
 * @author yangpingf
 */
public class CustAssetsFieldsMapper implements FieldSetMapper {
	@Override
	public Object mapFieldSet(FieldSet fieldSet) throws BindException {
		CustomerAssetsDomain custAssetsDomain = new CustomerAssetsDomain();
		custAssetsDomain.setCustomerNo(fieldSet.readString("CUST_NO"));
		custAssetsDomain.setAmtBal(fieldSet.readString("AUM_BAL"));
		dealAssetsDomain(custAssetsDomain);
		return custAssetsDomain;
	}

	private void dealAssetsDomain(CustomerAssetsDomain custAssetsDomain) {
		String amtStr = custAssetsDomain.getAmtBal();
		if (amtStr == null || amtStr.isEmpty()) {
			custAssetsDomain.setCustomerBalanceStep(8);
			return;
		}
		float amt;
		//客户资产段描述 : 资产段含义：1-5万以下,2-5~10万,3-10~30万 ,4-30~100万 5-100~300万 6-300~1000万 7-1000万以上 8-未识别
		try {
			amt = Float.parseFloat(amtStr);
		} catch (Exception e) {
			custAssetsDomain.setCustomerBalanceStep(8);
			return;
		}
		if (amt > Constant.TEN_MILLION) {
			custAssetsDomain.setCustomerBalanceStep(7);
		} else if (amt > Constant.THREE_MILLION && amt <= Constant.TEN_MILLION) {
			custAssetsDomain.setCustomerBalanceStep(6);
		} else if (amt > Constant.ONE_MILLION && amt <= Constant.THREE_MILLION) {
			custAssetsDomain.setCustomerBalanceStep(5);
		} else if (amt > Constant.THREE_HUNDRED_THOUSAND && amt <= Constant.ONE_MILLION) {
			custAssetsDomain.setCustomerBalanceStep(4);
		} else if (amt > Constant.ONE_HUNDRED_THOUSAND && amt <= Constant.THREE_HUNDRED_THOUSAND) {
			custAssetsDomain.setCustomerBalanceStep(3);
		} else if (amt > Constant.FIFTY_THOUSAND && amt <= Constant.ONE_HUNDRED_THOUSAND) {
			custAssetsDomain.setCustomerBalanceStep(2);
		} else if (amt <= Constant.FIFTY_THOUSAND) {
			custAssetsDomain.setCustomerBalanceStep(1);
		}
	}
}
