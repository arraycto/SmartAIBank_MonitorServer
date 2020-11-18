package com.dcfs.smartaibank.manager.monitor.web.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * http请求配置
 *
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */
@Configuration
public class MonitorRestTemplate {

    /**
     * 请求模板
     *
     * @return RestTemplate
     */
    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory =
                new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        RestTemplate restTemplate = MonitorRestTemplate.getInstance("utf-8");
        return restTemplate;
    }

    /**
     * 远程调用线程池配置
     *
     * @return ExecutorService
     */
    @Bean("monitorRemote")
    public ExecutorService monitorRemotePool() {
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("monitor-remote-pool-%d")
                .build();
        ExecutorService executorService
                = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 20,
                60, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(100),
                factory, new ThreadPoolExecutor.AbortPolicy());
        return executorService;
    }

    /**
     * 根据制定字符编码生成RestTemplate
     *
     * @param charset 字符集字符串
     * @return RestTemplate
     */
    public static RestTemplate getInstance(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(charset));
                break;
            }
        }
        return restTemplate;
    }
}
