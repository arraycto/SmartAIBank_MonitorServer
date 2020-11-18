package com.dcfs.smartaibank.manager.operations.web.batch.reader;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.TradeInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.mapper.CounterDataFieldsMapper;
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
 * 柜面信息导入reader，即交易流水导入的reader，根据现场给的数据格式，指定数据的列数组，长度数组等即可,
 * 指定特定的mapper   CounterDataFieldsMapper
 *
 * @param <T> 数据类型
 * @author yangping
 */
@Component
@StepScope
@Slf4j
public class CounterDataImportReader<T> implements ItemReader<TradeInfoDomain>, InitializingBean {

	private FlatFileItemReader<T> fileIitemReader;
	private String tradeFile;

	public CounterDataImportReader(@Value("#{jobParameters['batch.import.customer.trade.file.path']}")
									   String tradeFile) {
		this.tradeFile = tradeFile;
	}

	/**
	 * @return
	 */
	@Override
	public TradeInfoDomain read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return (TradeInfoDomain) fileIitemReader.read();
	}

	private void init() {
		Assert.hasText(this.tradeFile,
			"import file path[batch.import.customer.trade.file.path] must be not null");
		// 根据现场要求补上客户号字段，长度为21位
		int[] lengthArray = {32, 32, 8, 60, 32,
			32, 32, 32, 32, 32,
			32, 8, 255, 12, 128,
			8, 255, 3, 5, 1,
			22, 35, 255, 35, 255,
			6, 6, 512, 512, 1,
			1, 60, 2, 20, 16,
			3, 60, 19, 8};
		String[] columnArray = {
			"FRT_SER", "TLR_SER", "ACG_DT", "JZ_TASK_ID", "BATCH_SER",
			"CUST_SER", "COMM_SER", "BACK_SER", "AUTH_SER", "VOU_NO",
			"VOU_DATA_NO", "HANDL_TLR_ID", "HANDL_TLR_NM", "HANDL_INST_NO", "INST_ABBRT_NM",
			"TRAN_CODE", "TRAN_NAME", "TRAN_STAT", "CURR", "TRAN_CATS_FLAG",
			"TRAN_AMT", "DR_ACCT", "DR_ACCT_NM", "CR_ACCT", "CR_ACCT_NM",
			"TRAN_ST_TM", "TRAN_END_TM", "TRAN_SCREEN_PATH", "TRAN_DATA_PATH", "ELECT_VOU_FLG",
			"LEDGER_FLG", "DOC_BATCH_ID", "MONTH", "QU_SEQ", "CUST_NO",
			"CERT_TYPE", "CERT_CODE", "SRC_SYS_CD", "DATA_DT"};

		fileIitemReader = new FlatFileItemReader<>();
		fileIitemReader.setResource(new FileSystemResource(this.tradeFile));
		DefaultLineMapper<T> lineMapper = new DefaultLineMapper();
		FieldSetMapper<T> fieldMapper = new CounterDataFieldsMapper();
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
