package com.dcfs.smartaibank.manager.operations.web.batch.tokenizer;

import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.AbstractLineTokenizer;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 不同于spring-batch框架本身提供的FixedLengthTokenizer<T>类，FixedLengthTokenizer继承于AbstractLineTokenizer
 * 重写了doTokenize(String s)方法，该方法的内部重点在于解析一个定长文件行为List<String> ，定长文件的格式是
 * 多样的，spring-batch的定长指的是指定字段的长度范围，数据行长度固定，而该tokenizer需要解决的是数据总长度固定，字段所占的
 * 长度是变化的。
 *
 * @param <T>
 * @author yangpingf
 */
@Slf4j
public class FixedLengthTokenizer<T> extends AbstractLineTokenizer implements InitializingBean {
	private String[] columnArray;
	private int[] lengthArray;

	@Override
	protected List<String> doTokenize(String s) {

		// 将列转化为List
		List<String> tokens = parsColumn(s);
		return tokens;
	}

	@Override
	public void afterPropertiesSet() {

	}

	public void setColumnArray(String[] columnArray) {
		this.columnArray = columnArray;
	}

	public void setLengthArray(int[] lengthArray) {
		this.lengthArray = lengthArray;
	}

	private List<String> parsColumn(String item) {
		List<String> tokenList = new ArrayList<>();
		try {
			byte[] allBytes = item.getBytes(Constant.UTF8);
			int index = 0;
			int begin = 0;
			while (index < columnArray.length) {
				byte[] bytes = Arrays.copyOfRange(allBytes, begin, begin + lengthArray[index]);
				tokenList.add(new String(bytes, "UTF-8").trim());
				begin += lengthArray[index];
				index++;
			}
		} catch (Exception e) {
			log.error("当前行数据解析失败！");
		}
		return tokenList;
	}
}
