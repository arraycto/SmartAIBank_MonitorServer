package com.dcfs.smartaibank.manager.monitor.web.controller;


import com.dcfs.smartaibank.manager.monitor.web.domian.ErrorAccountInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.ErrorAccountService;
import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangtingo
 * @date 2019/8/12 9:48
 * @since
 */
@Api(value = "/api/v1/alarm/account/error", description = "错账登记信息")
@RestController
@RequestMapping(value = "/api/v1/alarm/account/error")
public class ErrorAccountController {

    @Autowired
    private ErrorAccountService errorAccountService;

    @ApiOperation(value = "检测是否可以进行错账登记", notes = "检测是否可以进行错账登记")
    @ApiImplicitParam(name = "cycleId", value = "加钞周期Id", dataType = "string",
            paramType = "path", required = true)
    @GetMapping(value = "/check/{cycleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean check(@PathVariable(value = "cycleId", required = true) String cycleId) {
        return errorAccountService.check(cycleId);
    }

    @ApiOperation(value = "错账登记信息", notes = "错账登记信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "errorAccountInfo", value = "参数实体",
                    dataType = "ErrorAccountInfo", paramType = "body", required = false)
    })
    @PostMapping(value = {"/register"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(@Validated({InsertOrUpdate.class}) @RequestBody ErrorAccountInfo errorAccountInfo) {
        errorAccountService.register(errorAccountInfo);
    }

    @ApiOperation(value = "错账处理查询", notes = "错账处理查询")
    @ApiImplicitParam(name = "cycleId", value = "加钞周期", dataType = "string",
            paramType = "path", required = true)
    @GetMapping(value = "/query/{cycleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorAccountInfo queryChecking(
            @PathVariable(value = "cycleId", required = true) String cycleId) {
        return errorAccountService.queryChecking(cycleId);
    }

}
