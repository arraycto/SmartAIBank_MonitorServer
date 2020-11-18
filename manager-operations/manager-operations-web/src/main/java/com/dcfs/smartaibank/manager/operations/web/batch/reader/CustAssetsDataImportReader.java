package com.dcfs.smartaibank.manager.operations.web.batch.reader;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerAssetsDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.mapper.CustAssetsFieldsMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 客户资产信息导入reader,策略及其配置方式同CounterDataImportReader，需要指定自己的mapper,
 * 即CustAssetsFieldsMapper
 *
 * @param <T> 数据类型
 * @author yangpingf
 */
@Component
@StepScope
@Slf4j
public class CustAssetsDataImportReader<T> implements ItemReader<CustomerAssetsDomain>, InitializingBean {

	private FlatFileItemReader<T> fileIitemReader;
	private String tradeFile;

	@Autowired
	private Environment env;

	public CustAssetsDataImportReader(@Value("#{jobParameters['batch.import.customer.private.assets.file.path']}")
										  String tradeFile) {
		this.tradeFile = tradeFile;
	}

	/**
	 * @return
	 */
	@Override
	public CustomerAssetsDomain read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return (CustomerAssetsDomain) fileIitemReader.read();
	}

	private void init() {
		Assert.hasText(this.tradeFile,
			"import file path[batch.import.customer.private.assets.file.path] must be not null");

		int[] lengthArray = {32, 19, 8, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
			22, 22, 22, 22, 22};
		String[] columnArray = {
			"CUST_NO", "SRC_SYS_CD", "DATA_DT",
			"CK_BAL_CNY", "CK_MONTH_AVG_CNY", "CK_QURT_AVG_CNY", "CK_YEAR_AVG_CNY",
			"DQCK_BAL_CNY", "DQCK_MONTH_AVG_CNY", "DQCK_QURT_AVG_CNY", "DQCK_YEAR_AVG_CNY",
			"HQCK_BAL_CNY", "HQCK_MONTH_AVG_CNY", "HQCK_QURT_AVG_CNY", "HQCK_YEAR_AVG_CNY",
			"DK_BAL_CNY", "DK_MONTH_AVG_CNY", "DK_QURT_AVG_CNY", "DK_YEAR_AVG_CNY",
			"PUDK_BAL_CNY", "PUDK_MONTH_AVG_CNY", "PUDK_QURT_AVG_CNY", "PUDK_YEAR_AVG_CNY",
			"XWDK_BAL_CNY", "XWDK_MONTH_AVG_CNY", "XWDK_QURT_AVG_CNY", "XWDK_YEAR_AVG_CNY",
			"WTDK_BAL_CNY", "WTDK_MONTH_AVG_CNY", "WTDK_QURT_AVG_CNY", "WTDK_YEAR_AVG_CNY",
			"FBLC_BAL_CNY", "FBLC_MONTH_AVG_CNY", "FBLC_QURT_AVG_CNY", "FBLC_YEAR_AVG_CNY",
			"KFLC_BAL_CNY", "KFLC_MONTH_AVG_CNY", "KFLC_QURT_AVG_CNY", "KFLC_YEAR_AVG_CNY",
			"FUND_BAL_CNY", "FUND_MONTH_AVG_CNY", "FUND_QURT_AVG_CNY", "FUND_YEAR_AVG_CNY",
			"THIRD_BAL_CNY", "THIRD_MONTH_AVG_CNY", "THIRD_QURT_AVG_CNY", "THIRD_YEAR_AVG_CNY",
			"TRUST_BAL_CNY", "TRUST_MONTH_AVG_CNY", "TRUST_QURT_AVG_CNY", "TRUST_YEAR_AVG_CNY",
			"METAL_BAL_CNY", "METAL_MONTH_AVG_CNY", "METAL_QURT_AVG_CNY", "METAL_YEAR_AVG_CNY",
			"GWK_BAL_CNY", "GWK_MONTH_AVG_CNY", "GWK_QURT_AVG_CNY", "GWK_YEAR_AVG_CNY",
			"AUM_BAL", "TOT_ASSET_BAL", "TOT_ASSET_M_AVG_BAL", "TOT_ASSET_S_AVG_BAL", "TOT_ASSET_Y_AVG_BAL",
			"INSURANCE_BAL_CNY", "INSURANCE_MONTH_AVG_CNY", "INSURANCE_QURT_AVG_CNY", "INSURANCE_YEAR_AVG_CNY",
			"CREDIT_CARD_BAL_CNY", "CREDIT_CARD_MONTH_AVG_CNY", "CREDIT_CARD_QURT_AVG_CNY", "CREDIT_CARD_YEAR_AVG_CNY",
			"AUM_MONTH_AVG_CNY", "AUM_QURT_AVG_CNY", "AUM_YEAR_AVG_CNY"
		};
		fileIitemReader = new FlatFileItemReader<T>();
		fileIitemReader.setResource(new FileSystemResource(this.tradeFile));
		DefaultLineMapper<T> lineMapper = new DefaultLineMapper();
		FieldSetMapper<T> fieldMapper = new CustAssetsFieldsMapper();
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
