package com.dcfs.smartaibank.manager.monitor.analyzer.engine;

import com.dcfs.smartaibank.autoconfiguration.xml.XmlConfiguration;
import com.dcfs.smartaibank.autoconfiguration.xml.processor.XmlNodeProcessor;
import com.dcfs.smartaibank.manager.monitor.analyzer.extend.BeanNodeProcessor;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;


/**
 * 分析引擎工厂类
 *
 * @author jiazw
 */
@Slf4j
public final class AnalyzerEngineFactory implements FactoryBean<AnalyzerEngine> {
	@Autowired
	private XmlNodeProcessor appPropertyNodeProcessor;

	public AnalyzerEngineFactory() {
	}

	/**
	 * 创建分析引擎
	 *
	 * @return 分析引擎
	 */
	@Override
	public AnalyzerEngine getObject() throws Exception {
		try {
			File file = ResourceUtils.getFile("classpath:analyzerEngine.xml");
			XmlConfiguration conf = new XmlConfiguration(new FileInputStream(file));
			conf.registerNodeProcessor("Bean", new BeanNodeProcessor());
			conf.registerNodeProcessor("AppProperty", appPropertyNodeProcessor);
			AnalyzerEngine engine = (AnalyzerEngine) conf.configure();
			//启动分析引擎,必须启动否则无法处理
			engine.start();
			return engine;
		} catch (Exception e) {
			log.error("初始化分析引擎发生异常！", e);
			throw e;
		}
	}

	@Override
	public Class<?> getObjectType() {
		return AnalyzerEngine.class;
	}
}
