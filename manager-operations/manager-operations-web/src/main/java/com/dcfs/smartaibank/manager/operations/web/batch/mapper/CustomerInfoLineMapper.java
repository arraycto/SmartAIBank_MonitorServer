package com.dcfs.smartaibank.manager.operations.web.batch.mapper;

import com.dcfs.smartaibank.manager.operations.web.batch.tokenizer.FixedLengthTokenizer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 客户信息处理mapper
 *
 * @param <T> 转换后的额类型
 * @author
 */
public class CustomerInfoLineMapper<T> implements LineMapper, InitializingBean {
	private LineTokenizer tokenizer;
	private FieldSetMapper<T> fieldSetMapper;

	public CustomerInfoLineMapper() {
		this.tokenizer = new FixedLengthTokenizer();
		this.fieldSetMapper = new CustomerFieldsSetMapper();
	}

	/**
	 * @param line
	 * @param lineNumber
	 * @return
	 * @throws Exception
	 */
	public T mapLine(String line, int lineNumber) throws Exception {
		return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
	}

	public void setLineTokenizer(LineTokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.tokenizer, "The LineTokenizer must be set");
		Assert.notNull(this.fieldSetMapper, "The FieldSetMapper must be set");
	}
}
