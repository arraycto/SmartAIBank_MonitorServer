package com.dcfs.smartaibank.manager.operations.web.batch.reader;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.mapper.PublicCustomerFieldsSetMapper;
import com.dcfs.smartaibank.manager.operations.web.batch.tokenizer.FixedLengthTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 对公客户信息导入reader
 *
 * @param <T> 数据类型
 * @author
 */
@Component
@StepScope
@Slf4j
public class PublicCustDataImportReader<T> implements ItemReader<CustomerInfoDomain>, InitializingBean {

	private FlatFileItemReader<T> fileIitemReader;
	private String tradeFile;

	public PublicCustDataImportReader(@Value("#{jobParameters['batch.import.customer.public.file.path']}")
										  String tradeFile) {
		this.tradeFile = tradeFile;
	}

	/**
	 * @return
	 */
	@Override
	public CustomerInfoDomain read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return (CustomerInfoDomain) fileIitemReader.read();
	}

	private void init() {
		Assert.hasText(this.tradeFile,
			"import file path[batch.import.customer.public.file.path] must be not null");

		int[] lengthArray = {32, 512, 60, 300, 21, 19, 200, 128, 12, 8,
			8, 19, 19, 19, 19, 32, 19, 19, 19, 6,
			6, 6, 6, 6, 6, 19, 19, 19, 128, 128,
			19, 19, 19, 12, 19, 19, 19, 60, 128, 19,
			19, 19, 19, 12, 32, 19, 20, 19, 19, 19,
			20, 19, 19, 8, 8};
		String[] columnArray = {
			"CUST_NO", "FULL_NAME", "NORMAL_NAME", "ENG_NAME", "COMPANY_CD",
			"TRADE_CODE", "HIGT_LN_MANG_NO", "ADMINIS_FOUND_DEPART", "CHN_ACCT_OPN_INST", "OPAC_TLR_NO",
			"SA_OPAC_DT", "UNIT_CHAR_CD", "OWNERSHIP", "UNIT_ECON_TYPE_CD", "UNIT_ECON_ORG_CD",
			"MID_CERD_NO", "TAX_FLAG", "TRADE_TYPE_CD", "ETP_ORG_NO", "STATISTICAL_SCL",
			"MIIT_SCL", "CBRC_SCL", "HUMAN_SCL", "STANDARD_SCL", "ECONOMY_SCL",
			"ENTP_LVL_CD", "BUSINESS_TYPE", "STAFF_NUM", "NATION_TAX_REGIST_NO", "LOCAL_TAX_REGIST_NO",
			"BAD_CUST_FLG", "SAME_CUST_FLAG", "LISTED_COMPANY_IND", "LEAD_BANK", "WHETHER_IMPORNANT_CORP",
			"CUST_STATUS", "CERT_TYPE", "CERT_CODE", "NOTE", "ORIGIN_SYS_CD",
			"GD_FLAG", "CUST_TYPE", "EQTR_PLAT_FLAG", "MAG_INST_NO", "CUST_MAGER_NO",
			"ORGA_INST_TYP", "LAST_UDT_DT", "HLDNG_TYPE", "LAST_UDT_SYS", "CTY_CD",
			"FRGN_LIC_NO", "GOV_FNC_RLTNP", "GOV_LAW_TYP", "BUILD_DT", "FIRM_GROW_STAGE"
		};

		fileIitemReader = new FlatFileItemReader<T>();
		fileIitemReader.setResource(new FileSystemResource(this.tradeFile));
		DefaultLineMapper<T> lineMapper = new DefaultLineMapper();
		FieldSetMapper<T> fieldMapper = new PublicCustomerFieldsSetMapper();
		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer<>();
		tokenizer.setColumnArray(columnArray);
		tokenizer.setLengthArray(lengthArray);
		tokenizer.setNames(columnArray);
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldMapper);
		fileIitemReader.setLineMapper(lineMapper);
		fileIitemReader.open(new ExecutionContext());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
}
