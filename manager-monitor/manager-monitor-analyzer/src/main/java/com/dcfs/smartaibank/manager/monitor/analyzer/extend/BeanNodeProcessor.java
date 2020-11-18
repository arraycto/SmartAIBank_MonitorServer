package com.dcfs.smartaibank.manager.monitor.analyzer.extend;

import com.dcfs.smartaibank.autoconfiguration.xml.AttrOrElementNode;
import com.dcfs.smartaibank.autoconfiguration.xml.XmlParser;
import com.dcfs.smartaibank.autoconfiguration.xml.processor.AbstractXmlNodeProcessor;
import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;

/**
 * Bean节点处理器
 *
 * @author jiazw
 */
public class BeanNodeProcessor extends AbstractXmlNodeProcessor {

	/**
	 * 节点处理器，将一个节点对象转成一个目标对象
	 *
	 * @param obj  目标对象
	 * @param node 节点对象
	 * @return 目标对象
	 * @throws Exception 转换异常
	 */
	@Override
	public Object process(Object obj, XmlParser.Node node) throws Exception {
		AttrOrElementNode aoeNode = new AttrOrElementNode(node, this.configuration.getProcessor(), "Id",
			"Name", "Class");
		String id = aoeNode.getString("Id");
		String name = aoeNode.getString("Name");
		String clazz = aoeNode.getString("Class");

		Object value = null;
		if (SpringContextUtil.containsBean(name)) {
			if (clazz == null || "".equals(clazz)) {
				value = SpringContextUtil.getBean(name);
			} else {
				Class<?> cl = Class.forName(clazz);
				value = SpringContextUtil.getBean(name, cl);
			}
		}

		// Set value if ID set
		if (id != null) {
			this.configuration.getIdMap().put(id, value);
		}
		return value;
	}
}
