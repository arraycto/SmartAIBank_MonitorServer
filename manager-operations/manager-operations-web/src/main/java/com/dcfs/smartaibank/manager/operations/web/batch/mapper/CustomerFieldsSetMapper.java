package com.dcfs.smartaibank.manager.operations.web.batch.mapper;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.convert.AgeConvert;
import com.dcfs.smartaibank.manager.operations.web.convert.ConvertFactory;
import com.dcfs.smartaibank.manager.operations.web.convert.DateConvert;
import com.dcfs.smartaibank.manager.operations.web.convert.SexConvert;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;

/**
 * 根据tokenizer解析定长文件后生成 CustomerFieldsSetMapper
 *
 * @author yangpinga
 */
public class CustomerFieldsSetMapper implements FieldSetMapper {

	private static final Integer DEFAULTAGESTEP = 0;

	@Override
	public CustomerInfoDomain mapFieldSet(FieldSet fieldSet) throws BindException {
		CustomerInfoDomain customer = new CustomerInfoDomain();
		customer.setCustomerNo(fieldSet.readString("CUST_NO"));
		customer.setCustomerName(fieldSet.readString("CUST_NAME"));
		String customerClass = fieldSet.readString("CUST_CLAS");
		//P对私普通 V对私VIP客户
		if (Constant.FORTY.equals(customerClass)) {
			customer.setCustomerType("P");
		} else if (Constant.SIXTY.equals(customerClass)) {
			customer.setCustomerType("V");
		} else {
			customer.setCustomerType(customerClass);
		}
		//客户级别 vip/普通
		customer.setCustomerLevel(fieldSet.readString("CUST_TYPE"));
		customer.setCustomerSex(sexFunction(fieldSet.readString("SEX_CODE"),
			new SexConvert())
		);
		customer.setIdentifyNo(fieldSet.readString("CERT_CODE"));
		customer.setIdentifyType(fieldSet.readString("CERT_TYPE"));
		customer.setBranchNo(fieldSet.readString("OPAC_INST_NO"));
		//校验生日格式是否正确，如果不正确，就赋值null，这样判断年龄段的时候 将其判断为未识别。
		String tempBirthday = fieldSet.readString("BIRDY_N");
		LocalDate localDate = birthdayFunction(tempBirthday, new DateConvert());
		customer.setBirthday(localDate != null ? tempBirthday : null);
		customer.setCustomerAgeStep(localDate != null ? ageFunction(localDate, new AgeConvert()) : DEFAULTAGESTEP);
		// 设置客户资产默认为8-未识别，只在插入操作中更新
		customer.setCustomerBalanceStep(8);
		return customer;
	}

	/**
	 * 转换方法，提供function函数方法作为参数
	 *
	 * @param str      需要转换的参数
	 * @param function 转换逻辑
	 * @return 转换后的值
	 */
	public String sexFunction(String str, ConvertFactory<String, String> function) {
		return function.convert(str);
	}

	/**
	 * 转换方法，提供function函数方法作为参数
	 *
	 * @param str      需要转换的参数
	 * @param function 转换逻辑
	 * @return 转换后的值
	 */
	public LocalDate birthdayFunction(String str, ConvertFactory<String, LocalDate> function) {
		return function.convert(str);
	}

	/**
	 * 转换方法，提供function函数方法作为参数
	 *
	 * @param localDate 需要转换的参数
	 * @param function  转换逻辑
	 * @return 转换后的值
	 */
	public Integer ageFunction(LocalDate localDate, ConvertFactory<LocalDate, Integer> function) {
		return function.convert(localDate);
	}

}
