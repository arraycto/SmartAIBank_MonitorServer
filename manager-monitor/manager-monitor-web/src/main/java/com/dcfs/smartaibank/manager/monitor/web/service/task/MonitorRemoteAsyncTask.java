package com.dcfs.smartaibank.manager.monitor.web.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @author liangchenglong
 * @date 2019/7/3 10:46
 * @since 1.0.0
 */

@Component
@Slf4j
public class MonitorRemoteAsyncTask {

    /**
     * http请求完成状态码
     */
    private static final Integer RESPONSE_CODE = 200;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 调用管理端任务实现
     *
     * @param url 请求的url
     * @param t   参数封装对象
     * @return 请求结果
     */
    @Async("monitorRemote")
    public <T> Future<String> managerTask(String url, T t) {
        log.info("线程:{}--管理端远程调用指令参数:{}", Thread.currentThread().getName(),
                t);
        String result = getRemoteResult(url, t);
        log.info("线程:{}--管理端远程调用返回结果:{}", Thread.currentThread().getName(),
                result);
        return new AsyncResult<>(result);
    }

    /**
     * 调用第三方客户端任务实现
     *
     * @param url 请求的url
     * @param t   参数封装对象
     * @return 请求结果
     */
    @Async("monitorRemote")
    public <T> Future<String> clientTask(String url, T t) {
        log.info("线程:{}--客户端远程调用指令参数:{}", Thread.currentThread().getName(),
                t);
        String result = getRemoteResult(url, t);
        log.info("线程:{}--客户端远程调用返回结果:{}", Thread.currentThread().getName(),
                result);
        return new AsyncResult<>(result);
    }

    /**
     * restTemplate post请求封装
     *
     * @param url
     * @param t   参数对象
     * @param <T>
     * @return
     */
    public <T> String getRemoteResult(String url, T t) {
        log.info("线程{}本次调用的url:{}", Thread.currentThread().getName(),
                url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity request = new HttpEntity<>(t, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCodeValue() != RESPONSE_CODE) {
            log.error("http请求异常，请检测远程系统是否启动 ip:{}", url);
        }
        return response.getBody();
    }
}
