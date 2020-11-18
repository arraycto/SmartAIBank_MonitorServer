package com.dcfs.smartaibank.manager.operations.web.batch.reader;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.mapper.CustomerFieldsSetMapper;
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
 * 客户信息导入reader
 * 只要是导入定长文件的reader,都可以参照这个reader的模式，注入列名columnArray数组，
 * 每一列的长度lengthArray数组，设置特定的mapper即可。当然，该类还有继续重构的空间。
 *
 * @param <T> 数据类型
 * @author yangping
 */
@Component
@StepScope
@Slf4j
public class CustomerInfoImportReader<T> implements ItemReader<CustomerInfoDomain>, InitializingBean {

	private FlatFileItemReader<T> fileIitemReader;
	private String tradeFile;

	public CustomerInfoImportReader(@Value("#{jobParameters['batch.import.customer.private.file.path']}")
										String tradeFile) {
		this.tradeFile = tradeFile;
	}

	@Override
	public CustomerInfoDomain read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return (CustomerInfoDomain) fileIitemReader.read();
	}

	private void init() {
		Assert.hasText(this.tradeFile,
			"import file path[batch.import.customer.private.file.path] must be not null");

		int[] lengthArray = {21, 255, 255, 60, 19, 19, 19, 19, 19, 8, 19, 19, 19, 19, 19, 128,
			19, 2, 19, 22, 4, 19, 60, 12, 8, 19, 19, 12, 32, 32, 32, 19, 19, 8};
		String[] columnArray = {
			"CUST_NO", "CUST_NAME", "FORMER_NAME", "ENG_NAME", "SEX_CODE",
			"PPL_CODE", "REG_COD", "NATION", "CUST_CLAS", "BIRDY_N", "STOCK_FLAG",
			"MANAGER_FLAG", "MRG_COND", "EDUCATIONAL_LEVEL", "OCCUPATION", "WORK_UNIT_NAME",
			"UNIT_TYPE_CD", "WORKING_YEARS", "INDUSTRY_TYPE", "MN_INCOM", "CUST_STATUS",
			"CERT_TYPE", "CERT_CODE", "OPAC_INST_NO", "OPAC_DT", "CHYRD_RESID_FLAG", "ORIGIN_SYS_CD",
			"MAG_INST_NO", "CUST_MAGER_NO", "AUM_INST_NO", "ACC_INST_NO", "CUST_TYPE", "SRC_SYS_CD", "DATA_DT"};

		fileIitemReader = new FlatFileItemReader<T>();
		fileIitemReader.setResource(new FileSystemResource(this.tradeFile));
		DefaultLineMapper<T> lineMapper = new DefaultLineMapper();
		FieldSetMapper<T> fieldMapper = new CustomerFieldsSetMapper();
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
