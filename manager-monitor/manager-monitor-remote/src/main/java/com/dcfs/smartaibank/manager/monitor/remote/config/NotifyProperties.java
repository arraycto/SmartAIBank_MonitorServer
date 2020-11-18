package com.dcfs.smartaibank.manager.monitor.remote.config;

import com.dcfs.smartaibank.springboot.integration.http.config.HttpOutboundProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知参数配置
 *
 * @author jiazw
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "monitor.notify")
public class NotifyProperties {

	/**
	 * 移动通知参数配置
	 */
	private MobileNotifyProperties mobile = new MobileNotifyProperties();

	/**
	 * 邮件通知参数配置
	 */
	private MailNotifyProperties mail = new MailNotifyProperties();

	/**
	 * 短信通知参数配置
	 */
	private SmsNotifyProperties sms = new SmsNotifyProperties();

	/**
	 * 移动通知参数配置
	 *
	 * @author jiazw
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	public static class MobileNotifyProperties extends HttpOutboundProperties {
		/**
		 * 移动端通知是否启动，true:启用，false：关闭
		 */
		private Boolean enabled;

		/**
		 * 通道队列大小
		 */
		private Integer queueSize = 1000;

		/**
		 * 消息头信息
		 */
		private Map<String, String> headers = new HashMap<>(16);
	}

	/**
	 * 邮件通知参数配置
	 *
	 * @author jiazw
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	public static class MailNotifyProperties {
		/**
		 * 邮件通知是否启动，true:启用，false：关闭
		 */
		private Boolean enabled;
		/**
		 * 是否开启数据度量，true:开启，false:关闭
		 */
		private Boolean metricsEnabled = false;
		/**
		 * 通道队列大小
		 */
		private Integer queueSize = 1000;

		/**
		 * 邮件协议
		 */
		private String procotol;

		/**
		 * 邮件服务器地址
		 */
		private String host;
		/**
		 * 邮件服务器端口
		 */
		private int port;
		/**
		 * 用户名
		 */
		private String username;
		/**
		 * 密码
		 */
		private String password;

		/**
		 * 编码
		 */
		private String encoding;

		/**
		 * 是否进行用户名密码校验
		 */
		private Boolean auth = false;

		/**
		 * 超时时间
		 */
		private Long timeOut = 5000L;

		/**
		 * 发件人
		 */
		private String from;
	}

	/**
	 * 短信通知参数配置
	 *
	 * @author jiazw
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	public static class SmsNotifyProperties {

	}
}
