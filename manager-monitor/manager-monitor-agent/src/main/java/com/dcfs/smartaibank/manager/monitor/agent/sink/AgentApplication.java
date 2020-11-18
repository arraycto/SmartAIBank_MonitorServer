package com.dcfs.smartaibank.manager.monitor.agent.sink;

import com.dcfs.smartaibank.core.util.Loader;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerEngine;
import com.google.common.eventbus.EventBus;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.flume.ChannelFactory;
import org.apache.flume.SinkFactory;
import org.apache.flume.SourceFactory;
import org.apache.flume.lifecycle.LifecycleAware;
import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.flume.node.Application;
import org.apache.flume.node.PollingPropertiesFileConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据采集应用
 *
 * @author jiazw
 */
public class AgentApplication {
	private static final String AGENT_NAME = "agent";
	private static final String AGENT_EVENT_BUS_NAME = AGENT_NAME + "-evnet-bus";
	private static final String METHOD_GET_CALSS = "getClass";
	private static final String METHOD_CREATE = "create";

	private volatile Application application;
	private EventBus eventBus;
	@Autowired
	private AnalyzerEngine analyzerEngine;

	/**
	 * 启动
	 */
	public void start() {
		Assert.notNull(this.analyzerEngine, "analyzerEngine must be not null");
		ProcessSink.setHandler(analyzerEngine);
		this.eventBus = new EventBus(AGENT_EVENT_BUS_NAME);
		List<LifecycleAware> lifecycleAwareList = createLifecycleAwares(eventBus);
		this.application = new Application(lifecycleAwareList);
		this.eventBus.register(application);
		this.application.start();
	}

	/**
	 * 停止
	 */
	public void stop() {
		if (this.eventBus != null) {
			this.eventBus.unregister(this.application);
		}
		if (this.application != null) {
			this.application.stop();
			this.application = null;
		}
	}

	private List<LifecycleAware> createLifecycleAwares(EventBus eventBus) {
		List<LifecycleAware> lifecycleAwares = new ArrayList<>();

		File configFile;
		// agent配置文件
		try {
			configFile = ResourceUtils.getFile("classpath:agent.properties");
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("[agent.properties] doesn't exist.", e);
		}

		if (!configFile.exists()) {
			throw new IllegalStateException("[agent.properties] doesn't exist.");
		}

		PollingPropertiesFileConfigurationProvider provider = new PollingPropertiesFileConfigurationProvider(
			AGENT_NAME, configFile, eventBus, 30);
		forceSetFactory(provider);

		lifecycleAwares.add(provider);

		return lifecycleAwares;
	}

	private void forceSetFactory(AbstractConfigurationProvider provider) {
		try {
			Field sourceFactoryField = AbstractConfigurationProvider.class
				.getDeclaredField("sourceFactory");
			Field sinkFactoryField = AbstractConfigurationProvider.class
				.getDeclaredField("sinkFactory");
			Field channelFactoryField = AbstractConfigurationProvider.class
				.getDeclaredField("channelFactory");

			sourceFactoryField.setAccessible(true);
			sinkFactoryField.setAccessible(true);
			channelFactoryField.setAccessible(true);

			sourceFactoryField.set(
				provider,
				createFactoryProxy(SourceFactory.class,
					sourceFactoryField.get(provider)));
			sinkFactoryField.set(
				provider,
				createFactoryProxy(SinkFactory.class,
					sinkFactoryField.get(provider)));
			channelFactoryField.set(
				provider,
				createFactoryProxy(ChannelFactory.class,
					channelFactoryField.get(provider)));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Object createFactoryProxy(Class<?> factoryInterface,
									  final Object defaultFlumeFactory) {
		return Proxy.newProxyInstance(factoryInterface.getClassLoader(),
			new Class<?>[]{factoryInterface}, new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method,
									 Object[] args) throws Throwable {
					try {
						if (METHOD_GET_CALSS.equals(method.getName())) {
							return Loader.loadClass((String) args[0]);
						} else if (METHOD_CREATE.equals(method.getName())) {
							Object target = Loader.loadClass((String) args[1]).newInstance();
							MethodUtils.invokeMethod(target, "setName",
								args[0]);
							return target;
						}
					} catch (Exception e) {
						return method.invoke(defaultFlumeFactory, args);
					}
					return null;
				}
			});
	}
}
