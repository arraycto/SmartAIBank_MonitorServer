package com.dcfs.smartaibank.manager.monitor.analyzer.extend;

import com.dcfs.smartaibank.autoconfiguration.xml.AttrOrElementNode;
import com.dcfs.smartaibank.autoconfiguration.xml.XmlParser;
import com.dcfs.smartaibank.autoconfiguration.xml.processor.AbstractXmlNodeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AppProperty节点处理器
 *
 * @author jiazw
 */
@Component
public class AppPropertyNodeProcessor extends AbstractXmlNodeProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppPropertyNodeProcessor.class);

	@Autowired
	private Environment env;

	/**
	 * 节点处理器，将一个节点对象转成一个目标对象
	 *
	 * @param obj  目标对象
	 * @param node 节点对象
	 * @return 目标对象
	 * @throws Exception 转换异常
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object process(Object obj, XmlParser.Node node) throws Exception {
		AttrOrElementNode aoeNode = new AttrOrElementNode(node, this.configuration.getProcessor(), "Id",
			"Name", "Deprecated", "Default");
		String id = aoeNode.getString("Id");
		String name = aoeNode.getString("Name", true);
		List<Object> deprecated = aoeNode.getList("Deprecated");

		String dftValue = aoeNode.getString("Default");

		// Look for a value
		String value = env.getProperty(name);

		// Look for a deprecated name value
		String alternate = null;
		if (!deprecated.isEmpty()) {
			for (Object d : deprecated) {
				String v = env.getProperty(String.valueOf(d));
				if (v != null) {
					if (value == null) {
						LOGGER.warn(
							"AppProperty '{}' is deprecated, use '{}' instead",
							d, name);
					} else {
						LOGGER.warn(
							"AppProperty '{}' is deprecated, value from '{}' used",
							d, name);
					}
				}
				if (alternate == null) {
					alternate = v;
				}
			}
		}

		// use alternate from deprecated
		if (value == null) {
			value = alternate;
		}

		// use default value
		if (value == null) {
			value = dftValue;
		}

		// Set value if ID set
		if (id != null) {
			this.configuration.getIdMap().put(id, value);
		}

		return value;
	}
}
