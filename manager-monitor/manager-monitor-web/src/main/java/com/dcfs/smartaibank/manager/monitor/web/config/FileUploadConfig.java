package com.dcfs.smartaibank.manager.monitor.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传
 *
 * @author wangjzm
 * @data 2019/08/29 10:36
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "monitor.file.upload")
@Component
public class FileUploadConfig {
    /**
     * 日志抓取存储路径
     */
    private String logUrl;

    /**
     * 设备截屏存储路径
     */
    private String picUrl;

    /**
     * 排队系统日志抓取存储路径
     */
    private String queueLogUrl;
    /**
     * 排队系统设备截屏存储路径
     */
    private String queuePicUrl;
}
