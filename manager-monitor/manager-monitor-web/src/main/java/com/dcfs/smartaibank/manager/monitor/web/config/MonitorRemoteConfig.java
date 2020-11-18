package com.dcfs.smartaibank.manager.monitor.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  远程调配置类
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@Data
@ConfigurationProperties(prefix = "monitor.remote-control.t9")
@Component
public class MonitorRemoteConfig {

    private Boolean onoff;

    private Integer port;

    private List<String> device;

    private String url;
}
