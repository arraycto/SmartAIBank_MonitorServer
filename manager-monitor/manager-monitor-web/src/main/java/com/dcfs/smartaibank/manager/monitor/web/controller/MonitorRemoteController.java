package com.dcfs.smartaibank.manager.monitor.web.controller;


import com.dcfs.smartaibank.manager.monitor.web.config.MonitorRemoteConfig;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountDetailParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountRecordParams;
import com.dcfs.smartaibank.manager.monitor.web.param.MonitorRemoteParams;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源控制器
 *
 * @author liangchenglong
 * @date 2019/7/29 10:44
 * @since 1.0.0
 */
@Api(value = "/api/v1/monitor/remote", description = "监控-远程调用")
@RestController
@RequestMapping(value = "/api/v1/monitor/remote")
@Slf4j
public class MonitorRemoteController {

    @Autowired
    private MonitorRemoteConfig monitorRemoteConfig;

    @Autowired
    private MonitorRemoteService monitorRemoteService;


    @ApiOperation(value = "远程调用-设备操作", notes = "远程调用-设备操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monitorRemoteParams", value = "远程调页面参数封装类",
                    dataType = "MonitorRemoteParams", paramType = "body", required = false)
    })
    @PostMapping("/device")
    public Object getRemoteRequest(@Validated @RequestBody MonitorRemoteParams monitorRemoteParams) {
        log.info("远程调用入口参数:{}", monitorRemoteParams);
        Object result;
        if (!monitorRemoteConfig.getOnoff()) {
            //请求管理端
            result = monitorRemoteService.remoteManager(monitorRemoteConfig, monitorRemoteParams);
        } else {
            //区分管理端，三方系统客户端(只有单次调用有，没有走批量需求)
            result = monitorRemoteService.remoteManagerAndClient(monitorRemoteConfig, monitorRemoteParams);
        }
        return result;
    }


    @ApiOperation(value = "远程调用-手工对账", notes = "远程调用-手工对账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountRecordParams", value = "手工对账参数封装",
                    dataType = "AccountRecordParams", paramType = "body", required = false)
    })
    @PostMapping("/account")
    public Object getAccountRecord(@RequestBody AccountRecordParams accountRecordParams) {
        log.info("手工交易对账请求参数:{}", accountRecordParams);
        String result = monitorRemoteService.remoteAccountRecord(monitorRemoteConfig.getUrl(),
                accountRecordParams);
        return result;
    }


    @ApiOperation(value = "远程调用-手工对账详情", notes = "远程调用-手工对账详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountDetailParams", value = "手工对账详情参数封装",
                    dataType = "AccountDetailParams", paramType = "body", required = false)
    })
    @PostMapping("/account/info")
    public Object getAccountInfo(@RequestBody AccountDetailParams accountDetailParams) {
        log.info("手工交易对账详情请求参数:{}", accountDetailParams);
        String result = monitorRemoteService.getAccountInfo(monitorRemoteConfig.getUrl(),
                accountDetailParams);
        return result;
    }
}
