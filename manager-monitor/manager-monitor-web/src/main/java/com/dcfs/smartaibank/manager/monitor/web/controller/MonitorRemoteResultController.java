package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.service.MonitorRemoteService;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorRemoteClientRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 接收第三方机具请求返回值
 *
 * @author liangchenglong
 * @date 2019/7/29 10:44
 * @since 1.0.0
 */
@Api(value = "监控-远程调用-接收调用结果", description = "监控-远程调用-接收调用结果")
@RestController
@Slf4j
public class MonitorRemoteResultController {

    @Autowired
    private MonitorRemoteService monitorRemoteService;

    @ApiOperation(value = "监控-远程调用-接收调用结果", notes = "监控-远程调用-接收调用结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monitorRemote", value = "监控-远程调用-接收调用结果数据封装对象",
                    dataType = "MonitorRemoteClientRequest", paramType = "body", required = false)
    })
    @PostMapping("/result")
    public Object receiveClientResponse(@RequestBody MonitorRemoteClientRequest monitorRemote) {
        //处理操作日志
        monitorRemoteService.getClientReceive(monitorRemote);
        return monitorRemote;
    }
}
